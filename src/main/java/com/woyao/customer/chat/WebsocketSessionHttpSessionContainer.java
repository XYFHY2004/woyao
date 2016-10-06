package com.woyao.customer.chat;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.woyao.customer.dto.ChatterDTO;

@Component("websocketSessionHttpSessionContainer")
public class WebsocketSessionHttpSessionContainer {

	public static final String SESSION_ATTR_HTTPSESSION_ID = HttpSessionHandshakeInterceptor.HTTP_SESSION_ID_ATTR_NAME;
	public static final String SESSION_ATTR_CHATTER_ID = "CHATTER_ID";
	public static final String SESSION_ATTR_CHATTER = "CHATTER";
	public static final String SESSION_ATTR_CHATROOM_ID = "CHATROOM_ID";

	/**
	 * websocketSession id为key， httpSession id 为value
	 */
	private Map<String, String> sessionContainer = new ConcurrentHashMap<>();

	/**
	 * websocketSession id为key， websocketSession 为value
	 */
	private Map<String, WebSocketSession> wsSessionMap = new ConcurrentHashMap<>();

	/**
	 * chatRoom id为key， websocketSession ids 为value
	 */
	private Map<Long, Set<String>> chatRoomWsSessionMap = new ConcurrentHashMap<>();

	/**
	 * chatter id为key， websocketSession ids 为value
	 */
	private Map<Long, Set<String>> chatterWsSessionMap = new ConcurrentHashMap<>();

	private Map<Long, ReentrantReadWriteLock> chatterLockMap = new ConcurrentHashMap<>();
	private ReentrantLock chatterLock = new ReentrantLock();

	private Map<Long, ReentrantReadWriteLock> chatRoomLockMap = new ConcurrentHashMap<>();
	private ReentrantLock chatRoomLock = new ReentrantLock();

	public void wsEnabled(WebSocketSession wsSession, HttpSession httpSession) {
		wsSession.getAttributes().put(SESSION_ATTR_CHATTER, httpSession.getAttribute(SESSION_ATTR_CHATTER));
		String wsSessionId = wsSession.getId();
		this.sessionContainer.put(wsSessionId, httpSession.getId());
		this.wsSessionMap.put(wsSessionId, wsSession);
		Long chatterId = (Long) httpSession.getAttribute(SESSION_ATTR_CHATTER_ID);
		wsSession.getAttributes().put(SESSION_ATTR_CHATTER_ID, chatterId);
		ReentrantReadWriteLock chatterLk = this.getChatterLock(chatterId);
		try {
			chatterLk.writeLock().lock();
			Set<String> wsSessionIds = this.chatterWsSessionMap.get(chatterId);
			if (wsSessionIds == null) {
				wsSessionIds = new HashSet<>();
				this.chatterWsSessionMap.put(chatterId, wsSessionIds);
			}
			wsSessionIds.add(wsSessionId);
		} finally {
			chatterLk.writeLock().unlock();
		}

		Long chatRoomId = (Long) httpSession.getAttribute(SESSION_ATTR_CHATROOM_ID);
		wsSession.getAttributes().put(SESSION_ATTR_CHATROOM_ID, chatRoomId);
		ReentrantReadWriteLock roomLk = this.getChatterLock(chatRoomId);
		try {
			roomLk.writeLock().lock();
			Set<String> wsSessionIds = this.chatRoomWsSessionMap.get(chatRoomId);
			if (wsSessionIds == null) {
				wsSessionIds = new HashSet<>();
				this.chatRoomWsSessionMap.put(chatRoomId, wsSessionIds);
			}
			wsSessionIds.add(wsSessionId);
		} finally {
			roomLk.writeLock().unlock();
		}
	}

	public void wsClosed(String sessionId) {
		WebSocketSession wsSession = this.wsSessionMap.get(sessionId);
		Long chatterId = this.getChatterId(wsSession);
		ReentrantReadWriteLock chatterLk = this.getChatterLock(chatterId);
		try {
			chatterLk.writeLock().lock();
			Set<String> wsSessionIds = this.chatterWsSessionMap.get(chatterId);
			if (wsSessionIds == null) {
				this.removeChatterLock(chatterId);
				return;
			}
			wsSessionIds.remove(sessionId);
			if (wsSessionIds.isEmpty()) {
				this.chatterWsSessionMap.remove(chatterId);
				this.removeChatterLock(chatterId);
			}
		} finally {
			chatterLk.writeLock().unlock();
		}

		Long chatRoomId = this.getChatRoomId(wsSession);
		ReentrantReadWriteLock roomLk = this.getChatRoomLock(chatRoomId);
		try {
			roomLk.writeLock().lock();
			Set<String> wsSessionIds = this.chatRoomWsSessionMap.get(chatRoomId);
			if (wsSessionIds == null) {
				this.removeChatRoomLock(chatRoomId);
				return;
			}
			wsSessionIds.remove(sessionId);
			if (wsSessionIds.isEmpty()) {
				this.chatRoomWsSessionMap.remove(chatterId);
				this.removeChatRoomLock(chatterId);
			}
		} finally {
			roomLk.writeLock().unlock();
		}
		this.sessionContainer.remove(sessionId);
		this.wsSessionMap.remove(sessionId);
	}

	public Set<WebSocketSession> getWsSessionOfChatter(long chatterId) {
		Set<String> sessionIds = this.chatterWsSessionMap.get(chatterId);
		Set<WebSocketSession> rs = new HashSet<>();
		if (sessionIds != null && !sessionIds.isEmpty()) {
			for (String sessionId : sessionIds) {
				rs.add(this.wsSessionMap.get(sessionId));
			}
		}
		return rs;
	}

	public Set<WebSocketSession> getWsSessionOfRoom(long chatRoomId) {
		Set<String> sessionIds = this.chatRoomWsSessionMap.get(chatRoomId);
		Set<WebSocketSession> rs = new HashSet<>();
		if (sessionIds != null && !sessionIds.isEmpty()) {
			for (String sessionId : sessionIds) {
				rs.add(this.wsSessionMap.get(sessionId));
			}
		}
		return rs;
	}

	public static ChatterDTO getChatter(WebSocketSession wsSession) {
		return (ChatterDTO) wsSession.getAttributes().get(SESSION_ATTR_CHATTER);
	}

	public static Long getChatterId(WebSocketSession wsSession) {
		return (Long) wsSession.getAttributes().get(SESSION_ATTR_CHATTER_ID);
	}

	public static Long getChatRoomId(WebSocketSession wsSession) {
		return (Long) wsSession.getAttributes().get(SESSION_ATTR_CHATROOM_ID);
	}

	private ReentrantReadWriteLock getChatterLock(Long chatterId) {
		try {
			this.chatterLock.lock();
			ReentrantReadWriteLock l = this.chatterLockMap.get(chatterId);
			if (l == null) {
				l = new ReentrantReadWriteLock();
				this.chatterLockMap.put(chatterId, l);
			}
			return l;
		} finally {
			this.chatterLock.unlock();
		}
	}

	private void removeChatterLock(Long chatterId) {
		try {
			this.chatterLock.lock();
			this.chatterLockMap.remove(chatterId);
		} finally {
			this.chatterLock.unlock();
		}
	}

	private ReentrantReadWriteLock getChatRoomLock(Long chatRoomId) {
		try {
			this.chatRoomLock.lock();
			ReentrantReadWriteLock l = this.chatRoomLockMap.get(chatRoomId);
			if (l == null) {
				l = new ReentrantReadWriteLock();
				this.chatRoomLockMap.put(chatRoomId, l);
			}
			return l;
		} finally {
			this.chatRoomLock.unlock();
		}
	}

	private void removeChatRoomLock(Long chatRoomId) {
		try {
			this.chatRoomLock.lock();
			this.chatRoomLockMap.remove(chatRoomId);
		} finally {
			this.chatRoomLock.unlock();
		}
	}
}
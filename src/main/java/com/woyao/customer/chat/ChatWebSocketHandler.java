package com.woyao.customer.chat;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.woyao.customer.chat.handler.MsgHandler;
import com.woyao.customer.dto.chat.in.Inbound;
import com.woyao.customer.service.IChatService;
import com.woyao.security.SharedHttpSessionContext;

public class ChatWebSocketHandler extends AbstractWebSocketHandler {

	private Log log = LogFactory.getLog(this.getClass());

	@Resource(name = "unifiedMsgHandler")
	private MsgHandler<Inbound> msgHandler;

	@Resource(name = "chatService")
	private IChatService chatService;

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		this.chatService.leave(session);
		log.debug("connectionClosed:" + session.getId());
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
		String payload = message.getPayload();
		if (log.isDebugEnabled()) {
			log.debug("Recieved msg: " + payload);
		}
		if (StringUtils.isBlank(payload)) {
			return;
		}
		Inbound inbound = Inbound.parse(payload);
		msgHandler.handle(session, inbound);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession wsSession) throws Exception {
		try {
			String httpSessionId = SessionUtils.getHttpSessionId(wsSession);
			HttpSession httpSession = SharedHttpSessionContext.getSession(httpSessionId);
			chatService.newChatter(wsSession, httpSession);

			if (log.isDebugEnabled()) {
				String msg = String.format("connectionEstablished, wsSessionId: %s, httpSessionId: %s", wsSession.getId(),
						httpSession.getId());
				log.debug(msg);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}

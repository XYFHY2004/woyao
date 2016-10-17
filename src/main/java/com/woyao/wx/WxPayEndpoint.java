package com.woyao.wx;

import javax.annotation.Resource;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.woyao.utils.JaxbUtils;
import com.woyao.wx.dto.UnifiedOrderRequest;
import com.woyao.wx.dto.UnifiedOrderResponse;

@Component("wxPayEndpoint")
public class WxPayEndpoint {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${wx.api.pay.unifiedOrder.url}")
	private String wxUnifiedOrderUrl;

	@Resource(name = "wxJerseyClient")
	private Client client;

	public UnifiedOrderResponse unifiedOrder(UnifiedOrderRequest request) {
		logger.debug("Start to call unified order...");
		WebTarget target = client.target(this.wxUnifiedOrderUrl);
		String body = null;
		try {
			body = JaxbUtils.marshall(request);
		} catch (JAXBException e1) {
			throw new RuntimeException(e1);
		}
		Entity<String> entity = Entity.entity(body, MediaType.TEXT_PLAIN);
		Response resp = createXmlRequestBuilder(target).post(entity);

		if (!validateResponse(resp)) {
			return null;
		}

		try {
			String responseBody = resp.readEntity(String.class);
			UnifiedOrderResponse orderResponse = JaxbUtils.unmarshall(UnifiedOrderResponse.class, responseBody);
			logger.debug("Unified order called!");
			return orderResponse;
		} catch (Exception e) {
			String msg = "Can not parse response!";
			logger.error(msg, e);
		}
		return null;
	}

	private Builder createXmlRequestBuilder(WebTarget target) {
		return target.request(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML_TYPE, MediaType.TEXT_PLAIN_TYPE)
				.header("User-Agent", MediaType.APPLICATION_XML);
	}

	private boolean validateResponse(Response resp) throws RuntimeException {
		if (resp.getStatus() >= Response.Status.BAD_REQUEST.getStatusCode()) {
			try {
				String error = resp.readEntity(String.class);
				logger.error("Error response: " + error);
			} catch (Exception e) {
				String msg = "Can not parse error response!";
				logger.error(msg, e);
			}
			return false;
		}
		return true;
	}

	public String getWxUnifiedOrderUrl() {
		return wxUnifiedOrderUrl;
	}

	public void setWxUnifiedOrderUrl(String wxUnifiedOrderUrl) {
		this.wxUnifiedOrderUrl = wxUnifiedOrderUrl;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}

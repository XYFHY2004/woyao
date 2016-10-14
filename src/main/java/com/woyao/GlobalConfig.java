package com.woyao;

public class GlobalConfig {

	private String verifyToken;

	private String appId;

	private String appSecret;

	private String authorizeUrl;

	private String authorizeParamFormat;

	// https://{wxUrl}?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect
	private String authorizeFormat;

	private String mchId;

	private String payApiKey;

	// server env
	private String host;

	private String payNotifyUrl;

	private int submitOrderConsumerNum = 10;

	// 3rd party
	private String thirdAppId;

	private String thirdMsgToken;

	private String thirdEncodingAesKey;

	private double earthRadius;

	private double latitudeRadius;

	private double longitudeRadius;

	private double shopAvailableDistance;

	public String getVerifyToken() {
		return verifyToken;
	}

	public void setVerifyToken(String verifyToken) {
		this.verifyToken = verifyToken;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getAuthorizeUrl() {
		return authorizeUrl;
	}

	public void setAuthorizeUrl(String authorizeUrl) {
		this.authorizeUrl = authorizeUrl;
	}

	public String getAuthorizeParamFormat() {
		return authorizeParamFormat;
	}

	public void setAuthorizeParamFormat(String authorizeParamFormat) {
		this.authorizeParamFormat = authorizeParamFormat;
	}

	public String getAuthorizeFormat() {
		return authorizeFormat;
	}

	public void generateAuthorizeFormat() {
		this.authorizeFormat = this.authorizeUrl + "?" + this.authorizeParamFormat;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getPayApiKey() {
		return payApiKey;
	}

	public void setPayApiKey(String payApiKey) {
		this.payApiKey = payApiKey;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPayNotifyUrl() {
		return payNotifyUrl;
	}

	public void setPayNotifyUrl(String payNotifyUrl) {
		this.payNotifyUrl = payNotifyUrl;
	}

	public int getSubmitOrderConsumerNum() {
		return submitOrderConsumerNum;
	}

	public void setSubmitOrderConsumerNum(int submitOrderConsumerNum) {
		this.submitOrderConsumerNum = submitOrderConsumerNum;
	}
	
	// 3rd

	public String getThirdAppId() {
		return thirdAppId;
	}

	public void setThirdAppId(String thirdAppId) {
		this.thirdAppId = thirdAppId;
	}

	public String getThirdMsgToken() {
		return thirdMsgToken;
	}

	public void setThirdMsgToken(String thirdMsgToken) {
		this.thirdMsgToken = thirdMsgToken;
	}

	public String getThirdEncodingAesKey() {
		return thirdEncodingAesKey;
	}

	public void setThirdEncodingAesKey(String thirdEncodingAesKey) {
		this.thirdEncodingAesKey = thirdEncodingAesKey;
	}

	public double getEarthRadius() {
		return earthRadius;
	}

	public void setEarthRadius(double earthRadius) {
		this.earthRadius = earthRadius;
	}

	public double getLatitudeRadius() {
		return latitudeRadius;
	}

	public void setLatitudeRadius(double latitudeRadius) {
		this.latitudeRadius = latitudeRadius;
	}

	public double getLongitudeRadius() {
		return longitudeRadius;
	}

	public void setLongitudeRadius(double longitudeRadius) {
		this.longitudeRadius = longitudeRadius;
	}

	public double getShopAvailableDistance() {
		return shopAvailableDistance;
	}

	public void setShopAvailableDistance(double shopAvailableDistance) {
		this.shopAvailableDistance = shopAvailableDistance;
	}

}

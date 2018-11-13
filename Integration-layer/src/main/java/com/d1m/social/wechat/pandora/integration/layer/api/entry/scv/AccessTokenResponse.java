package com.d1m.social.wechat.pandora.integration.layer.api.entry.scv;

public class AccessTokenResponse {

	private String token_type;
	private long expires_in;
	private int ext_expires_in;
	private long expires_on;
	private long not_before;
	private String resource;
	private String access_token;
	
	
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public long getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(long expires_in) {
		this.expires_in = expires_in;
	}
	public int getExt_expires_in() {
		return ext_expires_in;
	}
	public void setExt_expires_in(int ext_expires_in) {
		this.ext_expires_in = ext_expires_in;
	}
	public long getExpires_on() {
		return expires_on;
	}
	public void setExpires_on(long expires_on) {
		this.expires_on = expires_on;
	}
	public long getNot_before() {
		return not_before;
	}
	public void setNot_before(long not_before) {
		this.not_before = not_before;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	
	
}

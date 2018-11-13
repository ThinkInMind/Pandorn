package com.d1m.social.wechat.pandora.integration.layer.api.model;

public class AccessToken implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2673049080445295527L;
	private String id;
	private String wechat_id;
	private String appid;
	private String appscret;
	private String token;
	private String created_time;
	private String updated_time;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWechat_id() {
		return wechat_id;
	}
	public void setWechat_id(String wechat_id) {
		this.wechat_id = wechat_id;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getAppscret() {
		return appscret;
	}
	public void setAppscret(String appscret) {
		this.appscret = appscret;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getCreated_time() {
		return created_time;
	}
	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}
	public String getUpdated_time() {
		return updated_time;
	}
	public void setUpdated_time(String updated_time) {
		this.updated_time = updated_time;
	}
}

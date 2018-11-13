package com.d1m.social.wechat.pandora.integration.layer.api.model;

import java.io.Serializable;

/**
 * token生成规则为前缀: normal, vip , fastpass ，后缀为id
 * 例如 id为e21ce51bee3445ef84815ecc73b1d2c7
 * normale21ce51bee3445ef84815ecc73b1d2c7
 * vipe21ce51bee3445ef84815ecc73b1d2c7
 * fastpasse21ce51bee3445ef84815ecc73b1d2c7
 */
public class GuestNormalProfile implements Serializable {
	
	private static final long serialVersionUID = -4388997373752117737L;
	
	
	private String 
	Id,
	firstname,
	lastname,
	gender,
	mobile_phone,
	email,
	subscribe,
	source,
	channel,
	openid,
	unionid,
	created_time,
	updated_time,
	token;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobile_phone() {
		return mobile_phone;
	}

	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "GuestNormalProfile [Id=" + Id + ", firstname=" + firstname + ", lastname=" + lastname + ", gender="
				+ gender + ", mobile_phone=" + mobile_phone + ", email=" + email + ", subscribe=" + subscribe
				+ ", source=" + source + ", channel=" + channel + ", openid=" + openid + ", unionid=" + unionid
				+ ", created_time=" + created_time + ", updated_time=" + updated_time + ", token=" + token + "]";
	}
}

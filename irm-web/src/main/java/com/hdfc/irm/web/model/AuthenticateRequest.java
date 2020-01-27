package com.hdfc.irm.web.model;

import com.hdfc.irm.engine.utils.IrmUtils;

public class AuthenticateRequest {
	private String userid;
	private String password;
	private String source;
	private String device_id;
	private String build_version_code;
	private String channel_id;
	private String os;

	public String getUserid() {
		return userid;
	}

	@Override
	public String toString() {
		return "AuthenticateRequest [userid=" + userid + ", password=" + IrmUtils.encodeString(password) + ", source="
				+ source + ", device_id=" + device_id + ", build_version_code=" + build_version_code + ", channel_id="
				+ channel_id + ", os=" + os + "]";
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getBuild_version_code() {
		return build_version_code;
	}

	public void setBuild_version_code(String build_version_code) {
		this.build_version_code = build_version_code;
	}

	public String getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

}

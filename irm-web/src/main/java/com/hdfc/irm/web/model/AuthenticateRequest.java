package com.hdfc.irm.web.model;

import com.hdfc.irm.engine.utils.IrmUtils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticateRequest {
	private String userid;
	private String password;
	private String source;
	private String device_id;
	private String build_version_code;
	private String channel_id;
	private String os;


	@Override
	public String toString() {
		return "AuthenticateRequest [userid=" + userid + ", password=" + IrmUtils.encodeString(password) + ", source="
				+ source + ", device_id=" + device_id + ", build_version_code=" + build_version_code + ", channel_id="
				+ channel_id + ", os=" + os + "]";
	}
}

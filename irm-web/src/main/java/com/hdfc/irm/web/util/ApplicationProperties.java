package com.hdfc.irm.web.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {
	@Value("${auth.request.source:}")
	private String authRequestSource;
	@Value("${auth.request.deviceid:}")
	private String authRequestDeviceId;
	@Value("${auth.request.buildversion:}")
	private String authRequestBuildVersionCode;
	@Value("${auth.request.channelid:}")
	private String authRequestChannelId;
	@Value("${auth.request.os:}")
	private String authRequestOs;

	public String getAuthRequestSource() {
		return authRequestSource;
	}

	public String getAuthRequestDeviceId() {
		return authRequestDeviceId;
	}

	public String getAuthRequestBuildVersionCode() {
		return authRequestBuildVersionCode;
	}

	public String getAuthRequestChannelId() {
		return authRequestChannelId;
	}

	public String getAuthRequestOs() {
		return authRequestOs;
	}

}

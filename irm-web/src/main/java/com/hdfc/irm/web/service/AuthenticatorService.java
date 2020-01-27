package com.hdfc.irm.web.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hdfc.irm.engine.service.RestUtilService;
import com.hdfc.irm.engine.utils.LoggerUtils;
import com.hdfc.irm.web.exceptions.IRMAuthenticateException;
import com.hdfc.irm.web.model.AuthenticateRequest;
import com.hdfc.irm.web.model.AuthenticateResponse;
import com.hdfc.irm.web.util.ApplicationProperties;

@Service
public class AuthenticatorService {

	private static Logger logger = Logger.getLogger(AuthenticatorService.class);

	@Value("${hdfc.life.authenticate.url}")
	private String uri;

	@Autowired
	ApplicationProperties properties;

	@Autowired
	RestUtilService restUtilService;

	public AuthenticateResponse authenticate(AuthenticateRequest request) throws IRMAuthenticateException {
		AuthenticateResponse response;
		try {
			addConfigurations(request);
			logger.info("Login request received from:" + request.getUserid());
			LoggerUtils.debug(logger, "Request::" + request);

			response = (AuthenticateResponse) restUtilService.callRestService(request, AuthenticateResponse.class, uri);
			logger.info(
					"Authenticate response status" + (response == null ? "Response is empty" : response.getStatus()));
			LoggerUtils.debug(logger, "Response::" + response);

		} catch (Exception ce) {
			logger.error(LoggerUtils.getStackStrace(ce), ce);
			throw new IRMAuthenticateException("Unable to connect to LDAP");
		}
		return response;
	}

	private void addConfigurations(AuthenticateRequest request) {
		request.setSource(properties.getAuthRequestSource());
		request.setBuild_version_code(properties.getAuthRequestBuildVersionCode());
		request.setChannel_id(properties.getAuthRequestChannelId());
		request.setDevice_id(properties.getAuthRequestDeviceId());
		request.setOs(properties.getAuthRequestOs());
	}
}

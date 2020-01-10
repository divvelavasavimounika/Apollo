package com.hdfc.irm.web.service;

import java.util.Collections;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

	public AuthenticateResponse authenticate(AuthenticateRequest request) throws IRMAuthenticateException {
		AuthenticateResponse response;
		try {
			addConfigurations(request);
			logger.info("Login request received from:" + request.getUserid());
			LoggerUtils.debug(logger, "Request::" + request);

			RestTemplate restTemplate = new RestTemplate();
			if (uri != null && uri.startsWith("https")) {
				enableSSL();
			}

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			HttpEntity<AuthenticateRequest> entity = new HttpEntity<>(request, headers);

			response = restTemplate.postForObject(uri, entity, AuthenticateResponse.class);
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

	private void enableSSL() {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
		}
	}
}

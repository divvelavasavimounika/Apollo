package com.hdfc.irm.engine.service;

import java.util.Collections;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hdfc.irm.engine.utils.LoggerUtils;

@Service
public class RestUtilService {

	private static Logger logger = Logger.getLogger(RestUtilService.class);

	public Object callRestService(Object request, Class<? extends Object> clazz, String url) {
		Object response;

		RestTemplate restTemplate = new RestTemplate();
		logger.info("url::" + url);
		if (url.startsWith("https")) {
			logger.info("calling EnableSSSL ");
			enableSSL();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<Object> entity = new HttpEntity<>(request, headers);

		response = restTemplate.postForObject(url, entity, clazz);

		return response;
	}

	public void enableSSL() {
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
			logger.warn(LoggerUtils.getStackStrace(e));
		}

	}
}

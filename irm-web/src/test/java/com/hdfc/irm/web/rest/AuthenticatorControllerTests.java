package com.hdfc.irm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.hdfc.irm.IrmWebApplication;
import com.hdfc.irm.engine.model.DecisionResponse;
import com.hdfc.irm.web.exceptions.IRMAuthenticateException;
import com.hdfc.irm.web.model.AuthenticateRequest;
import com.hdfc.irm.web.model.AuthenticateResponse;
import com.hdfc.irm.web.service.AuthenticatorService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IrmWebApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthenticatorControllerTests {
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	private AuthenticateRequest request;
	@Autowired
	private Environment environment;

	@Autowired
	AuthenticatorController authenticationController;
	
	@Mock
	@Autowired
	AuthenticatorService service;

	@InjectMocks
	@Autowired
	AuthenticatorController controller;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Before
	public void setup() {
		request = new AuthenticateRequest();
		request.setUserid("10793");
		request.setPassword("HDFC@123");
	}

	@Test
	public void authenticateTest() throws IRMAuthenticateException {
		AuthenticateResponse response = new AuthenticateResponse();
		when(service.authenticate(request)).thenReturn(response);

		final String baseUrl = "http://localhost:" + port + "/" + environment.getProperty("server.servlet.context-path")
				+ "/authenticate/";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<AuthenticateRequest> entitty = new HttpEntity<AuthenticateRequest>(request, headers);
		ResponseEntity<DecisionResponse> result = this.restTemplate.postForEntity(baseUrl, entitty,
				DecisionResponse.class);
		assertThat(result.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
	}

}

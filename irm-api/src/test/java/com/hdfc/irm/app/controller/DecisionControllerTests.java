package com.hdfc.irm.app.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

import com.hdfc.irm.IrmApplication;
import com.hdfc.irm.app.service.DecisionService;
import com.hdfc.irm.engine.constants.Decision;
import com.hdfc.irm.engine.constants.NBAccountType;
import com.hdfc.irm.engine.constants.OTPStatus;
import com.hdfc.irm.engine.constants.WalkinType;
import com.hdfc.irm.engine.model.DecisionRequest;
import com.hdfc.irm.engine.model.DecisionResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IrmApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class DecisionControllerTests {
	@LocalServerPort
	private int port;

	DecisionRequest request;
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private Environment environment;

	@Mock
	DecisionService service;

	@Before
	public void setup() {
		request = new DecisionRequest();
		request.setCustomerID("testID");
		request.setEmployeeNTId("employeeNTId");
		request.setEmployeeUserName("employeeUserName");
		request.setPaymentAmount(10);
		request.setPolicyHolderName("policyHolderName");
		request.setPolicyID("policyID");
		request.setNbAccountType(NBAccountType.DIFFERENT);
		request.setOtpStatus(OTPStatus.DID_NOT_MATCH);
		request.setWalkinType(WalkinType.CUSTOMER);

	}

	@InjectMocks
	DecisionController controller;

	@Test
	public void deciosionControllerTest() {
		DecisionResponse response = new DecisionResponse();
		response.setDecision(Decision.STP);
		when(service.calculateDecision(any())).thenReturn(response);

		final String baseUrl = "http://localhost:" + port + "/" + environment.getProperty("server.servlet.context-path")
				+ "/decision/";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<DecisionRequest> entitty = new HttpEntity<DecisionRequest>(request, headers);
		ResponseEntity<DecisionResponse> result = this.restTemplate.postForEntity(baseUrl, entitty,
				DecisionResponse.class);

		assertThat(result.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());

	}

	@Test
	public void deciosionControllerTest_MethodArgumentNotValidException() {
		DecisionResponse response = new DecisionResponse();
		response.setDecision(Decision.STP);
		when(service.calculateDecision(any())).thenReturn(response);
		request.setOtpStatus(null);
		final String baseUrl = "http://localhost:" + port + "/" + environment.getProperty("server.servlet.context-path")
				+ "/decision/";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<DecisionRequest> entitty = new HttpEntity<DecisionRequest>(request, headers);
		ResponseEntity<DecisionResponse> result = this.restTemplate.postForEntity(baseUrl, entitty,
				DecisionResponse.class);

		assertThat(result.getStatusCodeValue()).isEqualTo(HttpStatus.BAD_REQUEST.value());

	}

}

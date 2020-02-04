package com.hdfc.irm.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hdfc.irm.engine.constants.Decision;
import com.hdfc.irm.engine.constants.NBAccountType;
import com.hdfc.irm.engine.constants.NameMatchType;
import com.hdfc.irm.engine.constants.OTPStatus;
import com.hdfc.irm.engine.constants.WalkinType;
import com.hdfc.irm.engine.entities.DecisionRequestEntity;
import com.hdfc.irm.engine.model.DecisionRequest;
import com.hdfc.irm.engine.model.DecisionResponse;
import com.hdfc.irm.engine.repository.AuditDecisionRepository;
import com.hdfc.irm.engine.service.IrmRuleEngine;
import com.hdfc.irm.engine.service.NameMatcher;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DecisionServiceTests {
	@Mock
	IrmRuleEngine irmRuleEngine;
	@Mock
	NameMatcher nameMatcher;
	@Mock
	AuditDecisionRepository auditDecisionRepository;

	@InjectMocks
	@Autowired
	DecisionService decisionService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCalculateDecision() {

		// mock penny drop call here
		when(irmRuleEngine.parseAndGetResult(any(Object.class))).thenReturn(Decision.NO_DECISION);
		when(auditDecisionRepository.save(any(DecisionRequestEntity.class))).thenReturn(new DecisionRequestEntity());
		when(nameMatcher.performNameMatch(any(String.class), any(String.class))).thenReturn(NameMatchType.FULL_MATCH);

		DecisionRequest request = new DecisionRequest();
		request.setCustomerID("testID");
		request.setEmployeeNTId("employeeNTId");
		request.setEmployeeUserName("employeeUserName");
		request.setPaymentAmount(10);
		request.setPolicyHolderName("policyHolderName");
		request.setPolicyID("policyID");
		request.setNbAccountType(NBAccountType.DIFFERENT);
		request.setOtpStatus(OTPStatus.DID_NOT_MATCH);
		request.setWalkinType(WalkinType.CUSTOMER);

		DecisionResponse response = decisionService.calculateDecision(request);
		assertThat(response).isNotNull();
		assertThat(response.getDecision()).isEqualTo(Decision.NO_DECISION);

	}

}

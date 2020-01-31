package com.hdfc.irm.engine.validation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hdfc.irm.engine.constants.NBAccountType;
import com.hdfc.irm.engine.constants.OTPStatus;
import com.hdfc.irm.engine.constants.WalkinType;
import com.hdfc.irm.engine.model.DecisionRequest;

public class AllowedValuesConstraintValidatorTests {

	static Validator validator;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testValidation_invalidCase() {
		DecisionRequest request = new DecisionRequest();
		request.setCustomerID("testID");
		request.setEmployeeNTId("employeeNTId");
		request.setEmployeeUserName("employeeUserName");
		request.setPaymentAmount(10);
		request.setPolicyHolderName("policyHolderName");
		request.setPolicyID("policyID");
		request.setNbAccountType(null);
		request.setOtpStatus("otpStatus");
		request.setWalkinType("walkinType");

		Set<ConstraintViolation<DecisionRequest>> violations = validator.validate(request);
		assertThat(violations).hasSize(3);

	}

	@Test
	public void testValidation_Success() {
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

		Set<ConstraintViolation<DecisionRequest>> violations = validator.validate(request);
		assertThat(violations.isEmpty()).isEqualTo(true);
	}

}

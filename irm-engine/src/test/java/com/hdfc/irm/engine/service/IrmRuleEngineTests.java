package com.hdfc.irm.engine.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;

import com.hdfc.irm.engine.constants.NBAccountType;
import com.hdfc.irm.engine.constants.NameMatchType;
import com.hdfc.irm.engine.constants.OTPStatus;
import com.hdfc.irm.engine.constants.WalkinType;
import com.hdfc.irm.engine.entities.DecisionRequestEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IrmRuleEngineTests {

	@Autowired
	IrmRuleEngine ruleEngine;

	@Test
	public void testParseAndGetResult_STP() {
		DecisionRequestEntity entity = new DecisionRequestEntity();

		entity.setWalkinType(WalkinType.CUSTOMER);
		entity.setNbAccountType(NBAccountType.SAME);
		entity.setOtpStatus(OTPStatus.NOT_REQUIRED);
		entity.setNameMatchStatus(NameMatchType.FULL_MATCH);
		entity.setPaymentAmount(50000);
		entity.setLowerBoundAmount(60000);
		entity.setUpperBoundAmount(70000);

		assertThat(ruleEngine.parseAndGetResult(entity)).isEqualTo("STP");
		ruleEngine.init();
	}

	@Test
	public void testParseAndGetResult_NO_DECISION() {
		DecisionRequestEntity entity = new DecisionRequestEntity();

		entity.setWalkinType("DUMMY");
		entity.setNbAccountType(NBAccountType.SAME);
		entity.setOtpStatus(OTPStatus.NOT_REQUIRED);
		entity.setNameMatchStatus(NameMatchType.FULL_MATCH);
		entity.setPaymentAmount(50000);
		entity.setLowerBoundAmount(60000);
		entity.setUpperBoundAmount(70000);

		assertThat(ruleEngine.parseAndGetResult(entity)).isEqualTo("NO_DECISION");
		ruleEngine.init();
	}

}

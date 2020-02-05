package com.hdfc.irm.engine.entities;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hdfc.irm.engine.constants.Decision;
import com.hdfc.irm.engine.constants.NBAccountType;
import com.hdfc.irm.engine.constants.NameMatchType;
import com.hdfc.irm.engine.constants.OTPStatus;
import com.hdfc.irm.engine.constants.WalkinType;
import com.hdfc.irm.engine.repository.AuditDecisionRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DecisionRequestEntityTests {
	
	@Autowired
	private AuditDecisionRepository auditDecisionRepository;
	
	@Test
    public void testSaveAuditDecisionRepository() {

		DecisionRequestEntity decReqEntity = new DecisionRequestEntity();	
		decReqEntity.setRequestId("12345");
		decReqEntity.setEmployeeNTId("empnetid123");
		decReqEntity.setEmployeeUserName("empusrname");		
		decReqEntity.setCustomerID("custid123");
		decReqEntity.setPolicyID("policyid123");
		decReqEntity.setPolicyHolderName("Suresh");
		decReqEntity.setWalkinType(WalkinType.CUSTOMER);
		decReqEntity.setNbAccountType(NBAccountType.SAME);
		decReqEntity.setOtpStatus(OTPStatus.NOT_REQUIRED);
		decReqEntity.setPayoutBranchID("branchid123");
		decReqEntity.setPaymentAmount(300000);
		decReqEntity.setLowerBoundAmount(500000);
		decReqEntity.setUpperBoundAmount(700000);
		decReqEntity.setNameMatchStatus(NameMatchType.FULL_MATCH);
		decReqEntity.setDecision(Decision.STP);
		decReqEntity.setAccountValidationFlag(false);			
		auditDecisionRepository.save(decReqEntity);
		Optional<DecisionRequestEntity> decReqEnt = auditDecisionRepository.findById(decReqEntity.getRequestId());
		assertNotNull(decReqEntity);		
		assertEquals(decReqEnt.get().getRequestId(), decReqEntity.getRequestId());		
		auditDecisionRepository.deleteById(decReqEntity.getRequestId());
	
    }
	
	@Test
    public void testFindAllAuditDecisionRepository() {
		
		DecisionRequestEntity reqEntity = new DecisionRequestEntity();	
		reqEntity.setRequestId("3456");
		reqEntity.setEmployeeNTId("empnetid345");
		reqEntity.setEmployeeUserName("empusrname");		
		reqEntity.setCustomerID("custid345");
		reqEntity.setPolicyID("policyid345");
		reqEntity.setPolicyHolderName("Rajesh");
		reqEntity.setWalkinType(WalkinType.CUSTOMER);
		reqEntity.setNbAccountType(NBAccountType.SAME);
		reqEntity.setOtpStatus(OTPStatus.NOT_REQUIRED);
		reqEntity.setPayoutBranchID("branchid345");
		reqEntity.setPaymentAmount(300000);
		reqEntity.setLowerBoundAmount(500000);
		reqEntity.setUpperBoundAmount(700000);
		reqEntity.setNameMatchStatus(NameMatchType.FULL_MATCH);
		reqEntity.setDecision(Decision.STP);
		reqEntity.setAccountValidationFlag(false);			
		auditDecisionRepository.save(reqEntity);
        assertNotNull(auditDecisionRepository.findAll());   
        List<DecisionRequestEntity> list=auditDecisionRepository.findAll();        
        assertEquals(list.get(0).getRequestId(),reqEntity.getRequestId() );       
        assertEquals(list.get(0).getEmployeeNTId(),reqEntity.getEmployeeNTId());  
        assertEquals(list.get(0).getEmployeeUserName(),reqEntity.getEmployeeUserName());  
        assertEquals(list.get(0).getPolicyID(),reqEntity.getPolicyID());         
        auditDecisionRepository.deleteById(reqEntity.getRequestId());
    }
	
	


	
	



	
	 


	

}


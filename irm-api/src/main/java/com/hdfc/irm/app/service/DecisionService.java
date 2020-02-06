package com.hdfc.irm.app.service;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hdfc.irm.engine.entities.DecisionRequestEntity;
import com.hdfc.irm.engine.model.DecisionRequest;
import com.hdfc.irm.engine.model.DecisionResponse;
import com.hdfc.irm.engine.repository.AuditDecisionRepository;
import com.hdfc.irm.engine.service.IrmRuleEngine;
import com.hdfc.irm.engine.service.NameMatcher;
import com.hdfc.irm.engine.utils.ApplicationProperties;
import com.hdfc.irm.engine.utils.IrmUtils;
import com.hdfc.irm.engine.utils.LoggerUtils;

@Service
public class DecisionService {

	private static Logger logger = Logger.getLogger(DecisionService.class);

	@Autowired
	IrmRuleEngine irmRuleEngine;
	@Autowired
	NameMatcher nameMatcher;
	@Autowired
	AuditDecisionRepository auditDecisionRepository;

	@Autowired
	ApplicationProperties properties;

	public DecisionResponse calculateDecision(DecisionRequest request) {
		logger.info("Requests recieved with ntID:" + request.getEmployeeNTId());
		LoggerUtils.debug(logger, request.toString());
		DecisionResponse response = null;

		// get cust details from db against policy id/custid
		String nameFromDB = "";
		callPennyDropApi();
		String nameFromPNYApi = "";
		// calculate name match and set
		String nameMatchStatus = nameMatcher.performNameMatch(nameFromDB, nameFromPNYApi);
		logger.info("Calculated Name match status:" + nameMatchStatus);
		DecisionRequestEntity entity = new DecisionRequestEntity();
		entity.setNameMatchStatus(nameMatchStatus);

		populateBranchLimits(entity);
		entity.setRequestId(IrmUtils.uuId());
		BeanUtils.copyProperties(request, entity);

		String decision = irmRuleEngine.parseAndGetResult(entity);

		response = buildResponse(decision);
		response.setRequestId(entity.getRequestId());
		entity.setDecision(decision);
		// audit request in data base
		logger.info("Saving decision request");
		auditDecisionRepository.save(entity);

		logger.info("Requests processed of ntID:" + request.getEmployeeNTId() + ": decision:" + decision);
		return response;
	}

	private void populateBranchLimits(DecisionRequestEntity entity) {
		entity.setLowerBoundAmount(properties.getLowerBoundAmount());
		entity.setUpperBoundAmount(properties.getUpperBoundAmount());
	}

	public void callPennyDropApi() {
		// penny drop api call
		// audit penny drop request and response
	}

	private DecisionResponse buildResponse(String decision) {
		DecisionResponse response = new DecisionResponse();
		response.setDecision(decision);
		response.setMessage(decision);

		return response;
	}

}

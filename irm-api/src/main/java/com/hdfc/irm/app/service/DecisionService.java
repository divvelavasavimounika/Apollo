package com.hdfc.irm.app.service;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hdfc.irm.engine.BranchPayoutLimitRepository;
import com.hdfc.irm.engine.IrmRuleEngine;
import com.hdfc.irm.engine.constants.NameMatchType;
import com.hdfc.irm.engine.entities.BranchPayoutLimit;
import com.hdfc.irm.engine.entities.DecisionRequestEntity;
import com.hdfc.irm.engine.exception.PayoutLimitNotSetException;
import com.hdfc.irm.engine.model.DecisionRequest;
import com.hdfc.irm.engine.model.DecisionResponse;
import com.hdfc.irm.engine.utils.IrmUtils;
import com.hdfc.irm.engine.utils.LoggerUtils;

@Service
public class DecisionService {

	private static Logger logger = Logger.getLogger(DecisionService.class);

	@Autowired
	IrmRuleEngine irmRuleEngine;

	// @Autowired
	// BranchPayoutLimitRepository payoutLimitRepository;

	// considering full match if account validation false
	private String nameMatchStatus = NameMatchType.FULL_MATCH.toString();

	public DecisionResponse calculateDecision(DecisionRequest request) throws PayoutLimitNotSetException {
		logger.info("Requests recieved with ntID:" + request.getEmployeeNTId());
		LoggerUtils.debug(logger, request.toString());
		DecisionResponse response = null;

		if (request.isAccountValidationFlag()) {
			// penny drop api call
			// audit penny drop request and response
			// calculate name match and set
		}

		// get lower, upper bound of the branch, calculate nameMatch status and set
		DecisionRequestEntity entity = new DecisionRequestEntity();
		entity.setNameMatchStatus(nameMatchStatus);
		populateBranchLimits(entity, request.getPayoutBranchID());
		entity.setRequestId(IrmUtils.uuId());
		BeanUtils.copyProperties(request, entity);
		String decision = irmRuleEngine.parseAndGetResult(entity);

		response = buildResponse(decision);
		response.setRequestId(entity.getRequestId());
		// audit request in data base

		logger.info("Requests processed of ntID:" + request.getEmployeeNTId() + ": decision:" + decision);
		return response;
	}

	private DecisionResponse buildResponse(String decision) {
		DecisionResponse response = new DecisionResponse();
		response.setDecision(decision);
		response.setMessage(decision);

		return response;
	}

	private void populateBranchLimits(DecisionRequestEntity entity, String payoutBranchID)
			throws PayoutLimitNotSetException {

		// Optional<BranchPayoutLimit> optional =
		// payoutLimitRepository.findById(payoutBranchID);

		// temporary purpose
		BranchPayoutLimit pl = new BranchPayoutLimit();
		pl.setLowerBoundAmount(500000);
		pl.setUpperBoundAmount(700000);
		Optional<BranchPayoutLimit> optional = Optional.of(pl);

		if (optional.isPresent()) {
			BranchPayoutLimit limit = optional.get();
			entity.setLowerBoundAmount(limit.getLowerBoundAmount());
			entity.setUpperBoundAmount(limit.getUpperBoundAmount());
		} else {
			logger.info(
					"Not processing request: Payment amount lower and upper limit is not set, please set the limits for Branch:"
							+ payoutBranchID);
			throw new PayoutLimitNotSetException(
					"Payment amount lower and upper limit is not set, please set the limits for Branch:"
							+ payoutBranchID);
		}
	}
}

package com.hdfc.irm.app.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hdfc.irm.app.service.DecisionService;
import com.hdfc.irm.engine.exception.PayoutLimitNotSetException;
import com.hdfc.irm.engine.model.DecisionRequest;
import com.hdfc.irm.engine.model.DecisionResponse;

@RestController
public class DecisionController {
	private static Logger logger = Logger.getLogger(DecisionController.class);

	@Autowired
	DecisionService service;

	@PostMapping("/decision")
	public DecisionResponse calculateDecission(@RequestBody DecisionRequest request) throws PayoutLimitNotSetException {

		logger.info("Request recieved from :" + request.getEmployeeNTId() + ": policy id:" + request.getPolicyID());
		// validate here

		DecisionResponse response = service.calculateDecision(request);
		logger.info("Request processed of employeeNTID :" + request.getEmployeeNTId() + ": policy id:"
				+ request.getPolicyID() + ":Decision:" + response.getDecision());
		return response;
	}
}

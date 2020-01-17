package com.hdfc.irm.app.controller;

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

	@Autowired
	DecisionService service;

	@PostMapping("/decision")
	public DecisionResponse calculateDecission(@RequestBody DecisionRequest request) throws PayoutLimitNotSetException{

		// validate here

		// delegate to service
		DecisionResponse response = service.calculateDecision(request);

		return response;
	}
}

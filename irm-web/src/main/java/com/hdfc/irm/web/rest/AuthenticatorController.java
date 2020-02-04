package com.hdfc.irm.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hdfc.irm.web.exceptions.IRMAuthenticateException;
import com.hdfc.irm.web.model.AuthenticateRequest;
import com.hdfc.irm.web.model.AuthenticateResponse;
import com.hdfc.irm.web.service.AuthenticatorService;
@RestController
public class AuthenticatorController {

	// need to validate request for optional parameters
		
	@Autowired
	AuthenticatorService service;

	@CrossOrigin(origins = "*")
	@PostMapping("/authenticate")
	public AuthenticateResponse authenticate(@RequestBody AuthenticateRequest request) throws IRMAuthenticateException {
		
		AuthenticateResponse response = service.authenticate(request);
		
		return response;
	}
}

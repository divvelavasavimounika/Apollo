package com.hdfc.irm.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hdfc.irm.engine.constants.NameMatchType;
import com.hdfc.irm.engine.service.NameMatcher;


@RestController
public class NameMatchController {
	@Autowired
	NameMatcher nameMatcher;

	@GetMapping("/namematch/{status}")
	public void changeNameMatchStatus(@PathVariable("status") int status) {
		switch (status) {
		case 1:
			nameMatcher.setNameMatchStatus(NameMatchType.NO_MATCH);
			break;
		case 2:
			nameMatcher.setNameMatchStatus(NameMatchType.PARTIAL_MATCH);
			break;
		case 3:
			nameMatcher.setNameMatchStatus(NameMatchType.FULL_MATCH);
			break;
		default:
			nameMatcher.setNameMatchStatus(NameMatchType.NOT_REQUIRED);
			break;
		}
	}
}

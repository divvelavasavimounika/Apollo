package com.hdfc.irm.app.service;

import org.springframework.stereotype.Service;

import com.hdfc.irm.engine.constants.NameMatchType;

@Service
public class NameMatcher {

	public String performNameMatch() {
//		return NameMatchType.FULL_MATCH;
		return NameMatchType.PARTIAL_MATCH;
	}
}

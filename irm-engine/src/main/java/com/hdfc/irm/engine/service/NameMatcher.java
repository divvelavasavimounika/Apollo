package com.hdfc.irm.engine.service;

import org.springframework.stereotype.Service;

import com.hdfc.irm.engine.constants.NameMatchType;

@Service
public class NameMatcher {

	public String performNameMatch() {
//		return NameMatchType.FULL_MATCH;
		return NameMatchType.NO_MATCH;
	}
}

package com.hdfc.irm.engine.service;

import org.springframework.stereotype.Service;

import com.hdfc.irm.engine.constants.NameMatchType;

@Service
public class NameMatcher {

	public String performNameMatch() {
		// return NameMatchType.FULL_MATCH;
		return getNameMatchStatus();
	}

	private String nameMatchStatus = NameMatchType.FULL_MATCH;

	public String getNameMatchStatus() {
		return nameMatchStatus;
	}

	public void setNameMatchStatus(String nameMatchStatus) {
		this.nameMatchStatus = nameMatchStatus;
	}

}

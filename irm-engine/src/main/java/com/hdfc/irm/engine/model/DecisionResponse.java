package com.hdfc.irm.engine.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DecisionResponse {
	private String requestId;
	private String decision;
	private String message;
}

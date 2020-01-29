package com.hdfc.irm.web.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticateResponse {
	private String status;
	private String message;
	private String lastloggedintime;
	private String emailValidationFlag;
	private String usertype;

	@Override
	public String toString() {
		return "AuthenticateResponse [status=" + status + ", message=" + message + ", lastloggedintime="
				+ lastloggedintime + ", emailValidationFlag=" + emailValidationFlag + ", usertype=" + usertype + "]";
	}

}

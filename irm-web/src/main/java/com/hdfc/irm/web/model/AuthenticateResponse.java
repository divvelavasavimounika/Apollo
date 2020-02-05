package com.hdfc.irm.web.model;

import lombok.Getter;

@Getter
public class AuthenticateResponse {
	private String status;
	private String message;
	private String lastloggedintime;
	private String emailValidationFlag;
	private String usertype;

	@Override
	public String toString() {
		return "AuthenticateResponse [status=" + getStatus() + ", message=" + getMessage()+ ", lastloggedintime="
				+ getLastloggedintime()+ ", emailValidationFlag=" + getEmailValidationFlag()+ ", usertype=" + getUsertype()+ "]";
	}

}

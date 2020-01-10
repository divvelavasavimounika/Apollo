package com.hdfc.irm.web.model;

public class AuthenticateResponse {
	private String status;
	private String message;
	private String lastloggedintime;
	private String emailValidationFlag;
	private String usertype;

	public String getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "AuthenticateResponse [status=" + status + ", message=" + message + ", lastloggedintime="
				+ lastloggedintime + ", emailValidationFlag=" + emailValidationFlag + ", usertype=" + usertype + "]";
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLastloggedintime() {
		return lastloggedintime;
	}

	public void setLastloggedintime(String lastloggedintime) {
		this.lastloggedintime = lastloggedintime;
	}

	public String getEmailValidationFlag() {
		return emailValidationFlag;
	}

	public void setEmailValidationFlag(String emailValidationFlag) {
		this.emailValidationFlag = emailValidationFlag;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

}

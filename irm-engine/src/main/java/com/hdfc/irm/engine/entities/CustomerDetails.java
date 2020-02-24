package com.hdfc.irm.engine.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerDetails {
	private String customerName;
	private String ifscCode;
	private String bankAccountNumber;
	private String policyNumber;

	public CustomerDetails() {
		super();
	}

	public CustomerDetails(String customerName, String ifscCode, String bankAccountNumber, String policyNumber) {
		super();
		this.customerName = customerName;
		this.ifscCode = ifscCode;
		this.bankAccountNumber = bankAccountNumber;
		this.policyNumber = policyNumber;
	}

	@Override
	public String toString() {
		return "CustmerDetailsEntity [customerName=" + getCustomerName() + ", ifscCode=" + getIfscCode()
				+ ", bankAccountNumber=" + getBankAccountNumber() + ", policyNumber=" + getPolicyNumber() + "]";
	}

}

package com.hdfc.irm.engine.entities;

public class DecisionRequestEntity {
	private String requestId;
	private String employeeNTId;
	private String employeeUserName;
	private String customerID;
	private String policyID;
	private String policyHolderName;
	private String walkinType;
	private String nbAccountType;
	private String otpStatus;
	private String payoutBranchID;

	private double paymentAmount;
	private double lowerBoundAmount;
	private double upperBoundAmount;
	private String nameMatchStatus;
	private boolean accountValidationFlag;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public boolean isAccountValidationFlag() {
		return accountValidationFlag;
	}

	public void setAccountValidationFlag(boolean accountValidationFlag) {
		this.accountValidationFlag = accountValidationFlag;
	}

	public String getEmployeeNTId() {
		return employeeNTId;
	}

	public void setEmployeeNTId(String employeeNTId) {
		this.employeeNTId = employeeNTId;
	}

	public String getEmployeeUserName() {
		return employeeUserName;
	}

	public void setEmployeeUserName(String employeeUserName) {
		this.employeeUserName = employeeUserName;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getPolicyID() {
		return policyID;
	}

	public void setPolicyID(String policyID) {
		this.policyID = policyID;
	}

	public String getPolicyHolderName() {
		return policyHolderName;
	}

	public void setPolicyHolderName(String policyHolderName) {
		this.policyHolderName = policyHolderName;
	}

	public String getWalkinType() {
		return walkinType;
	}

	public void setWalkinType(String walkinType) {
		this.walkinType = walkinType;
	}

	public String getNbAccountType() {
		return nbAccountType;
	}

	public void setNbAccountType(String nbAccountType) {
		this.nbAccountType = nbAccountType;
	}

	public String getOtpStatus() {
		return otpStatus;
	}

	public void setOtpStatus(String otpStatus) {
		this.otpStatus = otpStatus;
	}

	public String getPayoutBranchID() {
		return payoutBranchID;
	}

	public void setPayoutBranchID(String payoutBranchID) {
		this.payoutBranchID = payoutBranchID;
	}

	public double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public double getLowerBoundAmount() {
		return lowerBoundAmount;
	}

	public void setLowerBoundAmount(double lowerBoundAmount) {
		this.lowerBoundAmount = lowerBoundAmount;
	}

	public double getUpperBoundAmount() {
		return upperBoundAmount;
	}

	public void setUpperBoundAmount(double upperBoundAmount) {
		this.upperBoundAmount = upperBoundAmount;
	}

	public String getNameMatchStatus() {
		return nameMatchStatus;
	}

	public void setNameMatchStatus(String nameMatchStatus) {
		this.nameMatchStatus = nameMatchStatus;
	}

}

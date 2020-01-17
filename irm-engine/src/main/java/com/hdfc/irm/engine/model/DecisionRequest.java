package com.hdfc.irm.engine.model;

public class DecisionRequest {
	private String employeeNTId;
	private String employeeUserName;
	private String customerID;
	private String policyID;
	private String policyHolderName;
	private String walkinType;
	private String nbAccountType;
	private String otpStatus;
	private double paymentAmount;
	private String payoutBranchID;
	private boolean accountValidationFlag;

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

	public double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getPayoutBranchID() {
		return payoutBranchID;
	}

	public void setPayoutBranchID(String payoutBranchID) {
		this.payoutBranchID = payoutBranchID;
	}

	public boolean isAccountValidationFlag() {
		return accountValidationFlag;
	}

	public void setAccountValidationFlag(boolean accountValidationFlag) {
		this.accountValidationFlag = accountValidationFlag;
	}

	@Override
	public String toString() {
		return "DecisionRequest [employeeNTId=" + employeeNTId + ", employeeUserName=" + employeeUserName
				+ ", customerID=" + customerID + ", policyID=" + policyID + ", policyHolderName=" + policyHolderName
				+ ", walkinType=" + walkinType + ", nbAccountType=" + nbAccountType + ", otpStatus=" + otpStatus
				+ ", paymentAmount=" + paymentAmount + ", payoutBranchID=" + payoutBranchID + ", accountValidationFlag="
				+ accountValidationFlag + "]";
	}

}

package com.hdfc.irm.engine.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hdfc.irm.engine.constants.NBAccountType;
import com.hdfc.irm.engine.constants.OTPStatus;
import com.hdfc.irm.engine.constants.WalkinType;
import com.hdfc.irm.engine.validation.AllowedValues;

public class DecisionRequest {
	@NotBlank(message = "employeeNTId should not be blank")
	private String employeeNTId;
	@NotBlank(message = "employeeUserName should not be blank")
	private String employeeUserName;
	@NotBlank(message = "customerID should not be blank")
	private String customerID;
	@NotBlank(message = "policyID should not be blank")
	private String policyID;
	@NotBlank(message = "policyHolderName should not be blank")
	private String policyHolderName;

	@AllowedValues(value = { WalkinType.CUSTOMER,
			WalkinType.THIRD_PARTY }, message = "Allowed values for walkinType are " + WalkinType.CUSTOMER + ","
					+ WalkinType.THIRD_PARTY)
	private String walkinType;
	@AllowedValues(value = { NBAccountType.SAME,
			NBAccountType.DIFFERENT }, message = "Allowed values for nbAccountType are " + NBAccountType.SAME + ","
					+ NBAccountType.DIFFERENT)
	private String nbAccountType;
	@AllowedValues(value = { OTPStatus.MATCH, OTPStatus.DID_NOT_MATCH,
			OTPStatus.NOT_REQUIRED }, message = "Allowed values for otpStatus are " + OTPStatus.MATCH + ","
					+ OTPStatus.DID_NOT_MATCH + "," + OTPStatus.NOT_REQUIRED)
	private String otpStatus;
	@NotNull(message = "paymentAmount is required")
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

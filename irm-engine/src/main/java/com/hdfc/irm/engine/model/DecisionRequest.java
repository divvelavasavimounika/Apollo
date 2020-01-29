package com.hdfc.irm.engine.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hdfc.irm.engine.constants.NBAccountType;
import com.hdfc.irm.engine.constants.OTPStatus;
import com.hdfc.irm.engine.constants.WalkinType;
import com.hdfc.irm.engine.validation.AllowedValues;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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

	@Override
	public String toString() {
		return "DecisionRequest [employeeNTId=" + employeeNTId + ", employeeUserName=" + employeeUserName
				+ ", customerID=" + customerID + ", policyID=" + policyID + ", policyHolderName=" + policyHolderName
				+ ", walkinType=" + walkinType + ", nbAccountType=" + nbAccountType + ", otpStatus=" + otpStatus
				+ ", paymentAmount=" + paymentAmount + ", payoutBranchID=" + payoutBranchID + ", accountValidationFlag="
				+ accountValidationFlag + "]";
	}

}

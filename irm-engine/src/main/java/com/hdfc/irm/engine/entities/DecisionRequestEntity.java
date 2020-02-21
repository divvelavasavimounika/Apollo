package com.hdfc.irm.engine.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "AUDIT_DECISION")
@Getter
@Setter
public class DecisionRequestEntity implements Serializable {

	private static final long serialVersionUID = -8426076057721123981L;

	@Id
	@Column(name = "DECISION_REQUEST_ID")
	private String requestId;
	@Column(name = "EMP_NTID")
	private String employeeNTId;
	@Column(name = "EMP_USERNAME")
	private String employeeUserName;
	@Column(name = "CUSTOMER_ID")
	private String customerID;
	@Column(name = "CUSTOMER_POLICY_ID")
	private String policyID;
	@Column(name = "POLICY_HOLDER_NAME")
	private String policyHolderName;
	@Column(name = "WALKIN_TYPE")
	private String walkinType;
	@Column(name = "NB_ACCOUNT_TYPE")
	private String nbAccountType;
	@Column(name = "OTP_STATUS")
	private String otpStatus;
	@Column(name = "PAYOUT_BRANCH_ID")
	private String payoutBranchID;

	@Column(name = "PAYMENT_AMOUNT")
	private double paymentAmount;
	@Column(name = "LOWER_BOUND_AMOUNT")
	private double lowerBoundAmount;
	@Column(name = "UPPER_BOUND_AMOUNT")
	private double upperBoundAmount;
	@Column(name = "NAME_MATCH_STATUS")
	private String nameMatchStatus;
	@Column(name = "DECISION")
	private String decision;

	@Transient
	private boolean accountValidationFlag;
}

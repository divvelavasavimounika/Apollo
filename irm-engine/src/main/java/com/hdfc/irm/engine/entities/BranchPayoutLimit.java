package com.hdfc.irm.engine.entities;

public class BranchPayoutLimit {
	//id should be assigned here
	private String payoutBranchID;
	private double upperBoundAmount;
	private double lowerBoundAmount;

	
	public String getPayoutBranchID() {
		return payoutBranchID;
	}

	public void setPayoutBranchID(String payoutBranchID) {
		this.payoutBranchID = payoutBranchID;
	}

	public double getUpperBoundAmount() {
		return upperBoundAmount;
	}

	public void setUpperBoundAmount(double upperBoundAmount) {
		this.upperBoundAmount = upperBoundAmount;
	}

	public double getLowerBoundAmount() {
		return lowerBoundAmount;
	}

	public void setLowerBoundAmount(double lowerBoundAmount) {
		this.lowerBoundAmount = lowerBoundAmount;
	}

}

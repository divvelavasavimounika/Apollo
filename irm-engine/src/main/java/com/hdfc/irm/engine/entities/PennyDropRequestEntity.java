package com.hdfc.irm.engine.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "AUDIT_PENNY_DROP")
@Getter
@Setter
public class PennyDropRequestEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;
	@Column(name = "DECISION_REQUEST_ID")
	private String requestId;
	@Column(name = "MERCHANT_IDENTIFIER")
	private String merchantIdentifier;
	@Column(name = "MERCHANT_TXNPGM_ID")
	private String merchantTxnPgmId;
	@Column(name = "TXN_REFERENCE")
	private long TxnReference;
	@Column(name = "RECEIVER_IFSC")
	private String receiverIFSC;
	@Column(name = "RECEIVER_IDENTIFIER")
	private String receiverIdentifier;
	@Column(name = "CONSUMER_MOBILE")
	private String consumerMobile;
	@Column(name = "CONSUMER_EMAIL")
	private String consumerEmail;
	@Column(name = "TXN_ERROR_CODE")
	private String txnErrorCode;
	@Column(name = "TXN_ERROR_DESC")
	private String txnErrorDesc;
	@Column(name = "CUSTOMER_NAME")
	private String customerName;

	@Override
	public String toString() {
		return "PennyDropRequestEntity [id=" + getId() + ", requestId=" + getRequestId() + ", merchantIdentifier="
				+ getMerchantIdentifier() + ", merchantTxnPgmId=" + getMerchantTxnPgmId() + ", TxnReference="
				+ getTxnReference() + ", receiverIFSC=" + getReceiverIFSC() + ", receiverIdentifier="
				+ getReceiverIdentifier() + ", consumerMobile=" + getConsumerMobile() + ", consumerEmail="
				+ getConsumerEmail() + ", txnErrorCode=" + getTxnErrorCode() + ", txnErrorDesc=" + getTxnErrorDesc()
				+ ", customerName=" + getCustomerName() + "]";
	}

}

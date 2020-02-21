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
@Table(name = "AUDIT_PENNY_TXN_REFERENCE")
@Getter
@Setter
public class PennyTxnReferenceEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TXN_REFERENCE_ID")
	private Long txnReferenceId;

	@Column(name = "REQUEST_ID")
	private String requestId;
}

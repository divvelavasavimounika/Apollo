package com.hdfc.irm.engine.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class ApplicationProperties {
	@Value("${auth.request.source:}")
	private String authRequestSource;
	@Value("${auth.request.deviceid:}")
	private String authRequestDeviceId;
	@Value("${auth.request.buildversion:}")
	private String authRequestBuildVersionCode;
	@Value("${auth.request.channelid:}")
	private String authRequestChannelId;
	@Value("${auth.request.os:}")
	private String authRequestOs;

	@Value("${payout.lowerBoundAmount:0}")
	private double lowerBoundAmount;
	@Value("${payout.upperBoundAmount:0}")
	private double upperBoundAmount;

	@Value("${penny.api.url:}")
	private String pennyApiUrl;
	@Value("${penny.merchant.identifier:}")
	private String pennyMerchantId;
	@Value("${penny.merchant.txnpgmid:}")
	private String pennyMerchantTxnId;
	@Value("${penny.consumer.mobile:}")
	private String pennyConsumerMobile;
	@Value("${penny.consumer.email:}")
	private String pennyConsumerEmail;

	@Value("${customer.details.query:}")
	private String customerDetatilsQuery;

}

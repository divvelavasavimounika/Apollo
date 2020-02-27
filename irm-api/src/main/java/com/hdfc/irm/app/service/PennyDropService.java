package com.hdfc.irm.app.service;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hdfc.irm.app.exception.PennyDropApiException;
import com.hdfc.irm.engine.entities.PennyDropRequestEntity;
import com.hdfc.irm.engine.entities.PennyTxnReferenceEntity;
import com.hdfc.irm.engine.repository.AuditPennyDropRepository;
import com.hdfc.irm.engine.repository.PennyTxnReferenceRepository;
import com.hdfc.irm.engine.service.RestUtilService;
import com.hdfc.irm.engine.utils.ApplicationProperties;
import com.hdfc.irm.engine.utils.IrmUtils;
import com.hdfc.irm.engine.utils.LoggerUtils;

@Component
public class PennyDropService {
	private static Logger logger = Logger.getLogger(PennyDropService.class);
	private String pennyDropRequestString;

	@Autowired
	private RestUtilService restService;
	@Autowired
	private AuditPennyDropRepository pennyDropRepository;
	@Autowired
	private PennyTxnReferenceRepository txnReferenceRepository;
	@Autowired
	private ApplicationProperties properties;

	@PostConstruct
	private void init() throws IOException {
		try {
			logger.info("Loading penny drop request string from config folder");
			pennyDropRequestString = IrmUtils.readFromFile("config/PennyRequest.json");
		} catch (Exception e) {
			logger.info("Loading penny drop request string from resources folder");
			pennyDropRequestString = IrmUtils
					.readFromFile(this.getClass().getClassLoader().getResource("PennyRequest.json").getFile());
		}
		pennyDropRequestString = StringUtils.replace(pennyDropRequestString, "@@IDENTIFIER@@",
				properties.getPennyMerchantId());
		pennyDropRequestString = StringUtils.replace(pennyDropRequestString, "@@TXNPGMID@@",
				properties.getPennyMerchantTxnId());
		pennyDropRequestString = StringUtils.replace(pennyDropRequestString, "@@MOBILENUMBER@@",
				properties.getPennyConsumerMobile());
		pennyDropRequestString = StringUtils.replace(pennyDropRequestString, "@@EMAIL@@",
				properties.getPennyConsumerEmail());
	}

	public String callPennyDropApi(String ifsc, String accountNumber, String requestId) throws PennyDropApiException {
		String customerName = "";
		PennyDropRequestEntity requestEntity = new PennyDropRequestEntity();
		try {
			requestEntity.setConsumerEmail(properties.getPennyConsumerEmail());
			requestEntity.setConsumerMobile(properties.getPennyConsumerMobile());
			requestEntity.setMerchantIdentifier(properties.getPennyMerchantId());
			requestEntity.setMerchantTxnPgmId(properties.getPennyMerchantTxnId());
			requestEntity.setRequestId(requestId);
			requestEntity.setReceiverIdentifier(accountNumber);
			requestEntity.setReceiverIFSC(ifsc);

			fetchTxnReferenceId(requestId, requestEntity);
			logger.info("Penny drop request details set");

			String request = pennyDropRequestString;
			request = StringUtils.replace(request, "@@IFSC@@", ifsc);
			request = StringUtils.replace(request, "@@RECEIVERID@@", accountNumber);
			request = StringUtils.replace(request, "@@REFNO@@", "" + requestEntity.getTxnReference());
			LoggerUtils.debug(logger, "penny drop request::" + request);
			String response = (String) restService.callRestService(request, String.class, properties.getPennyApiUrl());
			LoggerUtils.debug(logger, "penny drop response::" + response);
			retriveCustomerName(requestEntity, response);

			if (!requestEntity.getTxnErrorCode().equals("PNYERR000")) {
				throw new PennyDropApiException("Penny drop api returned " + requestEntity.getTxnErrorCode() + ":"
						+ requestEntity.getTxnErrorCode());
			} else {
				customerName = requestEntity.getCustomerName();
			}
		} catch (Exception e) {
			logger.error(LoggerUtils.getStackStrace(e));
			throw new PennyDropApiException(e.getMessage());
		} finally {
			// audit penny drop request and response
			pennyDropRepository.save(requestEntity);
			LoggerUtils.debug(logger, requestEntity.toString());
		}
		return customerName;
	}

	public void fetchTxnReferenceId(String requestId, PennyDropRequestEntity requestEntity) {
		PennyTxnReferenceEntity txnEntity = new PennyTxnReferenceEntity();
		txnEntity.setRequestId(requestId);
		txnEntity = txnReferenceRepository.save(txnEntity);
		requestEntity.setTxnReference(txnEntity.getTxnReferenceId());
	}

	public void retriveCustomerName(PennyDropRequestEntity requestEntity, String response)
			throws JsonMappingException, JsonProcessingException, PennyDropApiException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode transactionNode = mapper.readTree(response).path("transaction");

		if (!transactionNode.isMissingNode()) {
			JsonNode txnErrorCodeNode = transactionNode.findPath("txnErrorCode");
			if (!txnErrorCodeNode.isMissingNode()) {
				requestEntity.setTxnErrorCode(txnErrorCodeNode.asText());
			} else {
				throw new PennyDropApiException("txnErrorCode tag is missing in response json");
			}

			JsonNode txnErrorDescNode = transactionNode.findPath("txnErrorDesc");
			if (!txnErrorDescNode.isMissingNode()) {
				requestEntity.setTxnErrorDesc(txnErrorDescNode.asText());
			} else {
				throw new PennyDropApiException("txnErrorDesc tag is missing in response json");
			}
			JsonNode nameNode = transactionNode.path("payment").path("receiver").path("name");
			if (!nameNode.isMissingNode()) {
				requestEntity.setCustomerName(nameNode.asText());
			} else {
				throw new PennyDropApiException("name tag is missing in response json");
			}
		} else {
			logger.warn(response);
			throw new PennyDropApiException("transaction tag is missing in response json");
		}
		logger.info("Penny drop response details set");
	}
}

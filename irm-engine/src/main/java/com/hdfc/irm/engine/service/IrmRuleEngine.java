package com.hdfc.irm.engine.service;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.mvel2.ParserContext;
import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRuntime;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hdfc.irm.engine.constants.Decision;
import com.hdfc.irm.engine.constants.NBAccountType;
import com.hdfc.irm.engine.constants.NameMatchType;
import com.hdfc.irm.engine.constants.OTPStatus;
import com.hdfc.irm.engine.constants.WalkinType;
import com.hdfc.irm.engine.utils.LoggerUtils;

@Service
public class IrmRuleEngine {
	private static Logger logger = Logger.getLogger(IrmRuleEngine.class);

	private static final String template = "@if{walkinType=='" + WalkinType.CUSTOMER + "' && nbAccountType== '"
			+ NBAccountType.SAME + "' && otpStatus=='" + OTPStatus.NOT_REQUIRED + "'  && nameMatchStatus=='"
			+ NameMatchType.FULL_MATCH + "' && paymentAmount <=lowerBoundAmount}" + Decision.STP + "@else{walkinType=='"
			+ WalkinType.CUSTOMER + "' && nbAccountType== '" + NBAccountType.SAME + "' && otpStatus=='"
			+ OTPStatus.NOT_REQUIRED + "' && nameMatchStatus=='" + NameMatchType.PARTIAL_MATCH
			+ "' && paymentAmount <=lowerBoundAmount}" + Decision.EXPERT_INITIAL + "@else{walkinType=='"
			+ WalkinType.CUSTOMER + "' && nbAccountType== '" + NBAccountType.SAME + "' && otpStatus=='"
			+ OTPStatus.NOT_REQUIRED + "' && nameMatchStatus=='" + NameMatchType.FULL_MATCH
			+ "' && paymentAmount > upperBoundAmount}" + Decision.EXPERT_INITIAL + "@else{walkinType=='"
			+ WalkinType.CUSTOMER + "' && nbAccountType== '" + NBAccountType.SAME + "' && otpStatus=='"
			+ OTPStatus.NOT_REQUIRED + "' && nameMatchStatus=='" + NameMatchType.PARTIAL_MATCH
			+ "' && paymentAmount > upperBoundAmount}" + Decision.EXPERT_EXPERT + "@else{walkinType=='"
			+ WalkinType.CUSTOMER + "' && nbAccountType== '" + NBAccountType.SAME + "' && otpStatus=='"
			+ OTPStatus.NOT_REQUIRED + "' && nameMatchStatus=='" + NameMatchType.NO_MATCH
			+ "' && paymentAmount <=lowerBoundAmount}" + Decision.RMCU + "@else{walkinType=='" + WalkinType.CUSTOMER
			+ "' && nbAccountType== '" + NBAccountType.SAME + "' && otpStatus=='" + OTPStatus.NOT_REQUIRED
			+ "' && nameMatchStatus=='" + NameMatchType.NO_MATCH + "' && paymentAmount  > upperBoundAmount}"
			+ Decision.RMCU + "@else{walkinType=='" + WalkinType.CUSTOMER + "' && nbAccountType== '"
			+ NBAccountType.DIFFERENT + "' && otpStatus=='" + OTPStatus.DID_NOT_MATCH + "' && nameMatchStatus=='"
			+ NameMatchType.FULL_MATCH + "' && paymentAmount <=lowerBoundAmount}" + Decision.UPDATE_CONTACT_DETAILS
			+ "@else{walkinType=='" + WalkinType.CUSTOMER + "' && nbAccountType== '" + NBAccountType.DIFFERENT
			+ "' && otpStatus=='" + OTPStatus.MATCH + "' && nameMatchStatus=='" + NameMatchType.FULL_MATCH
			+ "' && paymentAmount <=lowerBoundAmount}" + Decision.EXPERT_INITIAL + "@else{walkinType=='"
			+ WalkinType.CUSTOMER + "' && nbAccountType== '" + NBAccountType.DIFFERENT + "' && otpStatus=='"
			+ OTPStatus.DID_NOT_MATCH + "' && nameMatchStatus=='" + NameMatchType.PARTIAL_MATCH
			+ "' && paymentAmount <=lowerBoundAmount}" + Decision.UPDATE_CONTACT_DETAILS + "@else{walkinType=='"
			+ WalkinType.CUSTOMER + "' && nbAccountType== '" + NBAccountType.DIFFERENT + "' && otpStatus=='"
			+ OTPStatus.MATCH + "' && nameMatchStatus=='" + NameMatchType.PARTIAL_MATCH
			+ "' && paymentAmount <=lowerBoundAmount}" + Decision.EXPERT_EXPERT + "@else{walkinType=='"
			+ WalkinType.CUSTOMER + "' && nbAccountType== '" + NBAccountType.DIFFERENT + "' && otpStatus=='"
			+ OTPStatus.DID_NOT_MATCH + "' && nameMatchStatus=='" + NameMatchType.FULL_MATCH
			+ "' && paymentAmount  > upperBoundAmount}" + Decision.UPDATE_CONTACT_DETAILS + "@else{walkinType=='"
			+ WalkinType.CUSTOMER + "' && nbAccountType== '" + NBAccountType.DIFFERENT + "' && otpStatus=='"
			+ OTPStatus.MATCH + "' && nameMatchStatus=='" + NameMatchType.FULL_MATCH
			+ "' && paymentAmount  > upperBoundAmount}" + Decision.EXPERT_EXPERT + "@else{walkinType=='"
			+ WalkinType.CUSTOMER + "' && nbAccountType== '" + NBAccountType.DIFFERENT + "' && otpStatus=='"
			+ OTPStatus.DID_NOT_MATCH + "' && nameMatchStatus=='" + NameMatchType.PARTIAL_MATCH
			+ "' && paymentAmount  > upperBoundAmount}" + Decision.UPDATE_CONTACT_DETAILS + "@else{walkinType=='"
			+ WalkinType.CUSTOMER + "' && nbAccountType== '" + NBAccountType.DIFFERENT + "' && otpStatus=='"
			+ OTPStatus.MATCH + "' && nameMatchStatus=='" + NameMatchType.PARTIAL_MATCH
			+ "' && paymentAmount  > upperBoundAmount}" + Decision.EXPERT_EXPERT + "@else{walkinType=='"
			+ WalkinType.CUSTOMER + "' && nbAccountType== '" + NBAccountType.DIFFERENT + "' && otpStatus=='"
			+ OTPStatus.DID_NOT_MATCH + "' && nameMatchStatus=='" + NameMatchType.NO_MATCH
			+ "' && paymentAmount <=lowerBoundAmount}" + Decision.UPDATE_CONTACT_DETAILS + "@else{walkinType=='"
			+ WalkinType.CUSTOMER + "' && nbAccountType== '" + NBAccountType.DIFFERENT + "' && otpStatus=='"
			+ OTPStatus.MATCH + "' && nameMatchStatus=='" + NameMatchType.NO_MATCH
			+ "' && paymentAmount <=lowerBoundAmount}" + Decision.RMCU + "@else{walkinType=='" + WalkinType.CUSTOMER
			+ "' && nbAccountType== '" + NBAccountType.DIFFERENT + "' && otpStatus=='" + OTPStatus.DID_NOT_MATCH
			+ "' && nameMatchStatus=='" + NameMatchType.NO_MATCH + "' && paymentAmount  > upperBoundAmount}"
			+ Decision.UPDATE_CONTACT_DETAILS + "@else{walkinType=='" + WalkinType.CUSTOMER + "' && nbAccountType== '"
			+ NBAccountType.DIFFERENT + "' && otpStatus=='" + OTPStatus.MATCH + "' && nameMatchStatus=='"
			+ NameMatchType.NO_MATCH + "' && paymentAmount  > upperBoundAmount}" + Decision.RMCU + "@else{walkinType=='"
			+ WalkinType.THIRD_PARTY + "' && nbAccountType== '" + NBAccountType.SAME + "' && otpStatus=='"
			+ OTPStatus.MATCH + "' && nameMatchStatus=='" + NameMatchType.FULL_MATCH
			+ "' && paymentAmount <=lowerBoundAmount}" + Decision.STP + "@else{walkinType=='" + WalkinType.THIRD_PARTY
			+ "' && nbAccountType== '" + NBAccountType.SAME + "' && otpStatus=='" + OTPStatus.DID_NOT_MATCH
			+ "' && nameMatchStatus=='" + NameMatchType.FULL_MATCH + "' && paymentAmount <=lowerBoundAmount}"
			+ Decision.CUSTOMER_WALKIN_REQUIRED
			// special case
			+ "@else{walkinType=='" + WalkinType.THIRD_PARTY + "' && nbAccountType== '" + NBAccountType.DIFFERENT
			+ "' && otpStatus=='" + OTPStatus.NOT_REQUIRED + "' && nameMatchStatus=='NOT_REQUIRED'}"
			+ Decision.CUSTOMER_WALKIN_REQUIRED + "@else{walkinType=='" + WalkinType.THIRD_PARTY
			+ "' && nbAccountType== '" + NBAccountType.SAME + "' && otpStatus=='" + OTPStatus.MATCH
			+ "' && nameMatchStatus=='" + NameMatchType.PARTIAL_MATCH + "' && paymentAmount <=lowerBoundAmount}"
			+ Decision.EXPERT_INITIAL + "@else{walkinType=='" + WalkinType.THIRD_PARTY + "' && nbAccountType== '"
			+ NBAccountType.SAME + "' && otpStatus=='" + OTPStatus.DID_NOT_MATCH + "' && nameMatchStatus=='"
			+ NameMatchType.PARTIAL_MATCH + "' && paymentAmount <=lowerBoundAmount}" + Decision.CUSTOMER_WALKIN_REQUIRED
			+ "@else{walkinType=='" + WalkinType.THIRD_PARTY + "' && nbAccountType== '" + NBAccountType.SAME
			+ "' && otpStatus=='" + OTPStatus.MATCH + "' && nameMatchStatus=='" + NameMatchType.FULL_MATCH
			+ "' && paymentAmount > upperBoundAmount}" + Decision.EXPERT_INITIAL + "@else{walkinType=='"
			+ WalkinType.THIRD_PARTY + "' && nbAccountType== '" + NBAccountType.SAME + "' && otpStatus=='"
			+ OTPStatus.DID_NOT_MATCH + "' && nameMatchStatus=='" + NameMatchType.FULL_MATCH
			+ "' && paymentAmount > upperBoundAmount}" + Decision.CUSTOMER_WALKIN_REQUIRED + "@else{walkinType=='"
			+ WalkinType.THIRD_PARTY + "' && nbAccountType== '" + NBAccountType.SAME + "' && otpStatus=='"
			+ OTPStatus.MATCH + "' && nameMatchStatus=='" + NameMatchType.PARTIAL_MATCH
			+ "' && paymentAmount > upperBoundAmount}" + Decision.EXPERT_EXPERT + "@else{walkinType=='"
			+ WalkinType.THIRD_PARTY + "' && nbAccountType== '" + NBAccountType.SAME + "' && otpStatus=='"
			+ OTPStatus.DID_NOT_MATCH + "' && nameMatchStatus=='" + NameMatchType.PARTIAL_MATCH
			+ "' && paymentAmount > upperBoundAmount}" + Decision.CUSTOMER_WALKIN_REQUIRED + "@else{walkinType=='"
			+ WalkinType.THIRD_PARTY + "' && nbAccountType== '" + NBAccountType.SAME + "' && otpStatus=='"
			+ OTPStatus.MATCH + "' && nameMatchStatus=='" + NameMatchType.NO_MATCH
			+ "' && paymentAmount <=lowerBoundAmount}" + Decision.RMCU + "@else{walkinType=='" + WalkinType.THIRD_PARTY
			+ "' && nbAccountType== '" + NBAccountType.SAME + "' && otpStatus=='" + OTPStatus.DID_NOT_MATCH
			+ "' && nameMatchStatus=='" + NameMatchType.NO_MATCH + "' && paymentAmount <=lowerBoundAmount}"
			+ Decision.CUSTOMER_WALKIN_REQUIRED + "@else{walkinType=='" + WalkinType.THIRD_PARTY
			+ "' && nbAccountType== '" + NBAccountType.SAME + "' && otpStatus=='" + OTPStatus.MATCH
			+ "' && nameMatchStatus=='" + NameMatchType.NO_MATCH + "' && paymentAmount > upperBoundAmount}"
			+ Decision.RMCU + "@else{walkinType=='" + WalkinType.THIRD_PARTY + "' && nbAccountType== '"
			+ NBAccountType.SAME + "' && otpStatus=='" + OTPStatus.DID_NOT_MATCH + "' && nameMatchStatus=='"
			+ NameMatchType.NO_MATCH + "' && paymentAmount > upperBoundAmount}" + Decision.CUSTOMER_WALKIN_REQUIRED
			+ "@else{walkinType=='" + WalkinType.CUSTOMER + "' && nbAccountType== '" + NBAccountType.SAME
			+ "' && otpStatus=='" + OTPStatus.NOT_REQUIRED + "' && nameMatchStatus=='" + NameMatchType.FULL_MATCH
			+ "' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount  }"
			+ Decision.EXPERT_INITIAL + "@else{walkinType=='" + WalkinType.CUSTOMER + "' && nbAccountType== '"
			+ NBAccountType.SAME + "' && otpStatus=='" + OTPStatus.NOT_REQUIRED + "' && nameMatchStatus=='"
			+ NameMatchType.PARTIAL_MATCH
			+ "' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount}" + Decision.EXPERT_INITIAL
			+ "@else{walkinType=='" + WalkinType.CUSTOMER + "' && nbAccountType== '" + NBAccountType.SAME
			+ "' && otpStatus=='" + OTPStatus.NOT_REQUIRED + "' && nameMatchStatus=='" + NameMatchType.NO_MATCH
			+ "' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount}" + Decision.RMCU
			+ "@else{walkinType=='" + WalkinType.CUSTOMER + "' && nbAccountType== '" + NBAccountType.DIFFERENT
			+ "' && otpStatus=='" + OTPStatus.DID_NOT_MATCH + "' && nameMatchStatus=='" + NameMatchType.FULL_MATCH
			+ "' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount}"
			+ Decision.UPDATE_CONTACT_DETAILS + "@else{walkinType=='" + WalkinType.CUSTOMER + "' && nbAccountType== '"
			+ NBAccountType.DIFFERENT + "' && otpStatus=='" + OTPStatus.MATCH + "' && nameMatchStatus=='"
			+ NameMatchType.FULL_MATCH + "' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount}"
			+ Decision.EXPERT_INITIAL + "@else{walkinType=='" + WalkinType.CUSTOMER + "' && nbAccountType== '"
			+ NBAccountType.DIFFERENT + "' && otpStatus=='" + OTPStatus.DID_NOT_MATCH + "' && nameMatchStatus=='"
			+ NameMatchType.PARTIAL_MATCH
			+ "' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount}"
			+ Decision.UPDATE_CONTACT_DETAILS + "@else{walkinType=='" + WalkinType.CUSTOMER + "' && nbAccountType== '"
			+ NBAccountType.DIFFERENT + "' && otpStatus=='" + OTPStatus.MATCH + "' && nameMatchStatus=='"
			+ NameMatchType.PARTIAL_MATCH
			+ "' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount}" + Decision.EXPERT_EXPERT
			+ "@else{walkinType=='" + WalkinType.CUSTOMER + "' && nbAccountType== '" + NBAccountType.DIFFERENT
			+ "' && otpStatus=='" + OTPStatus.DID_NOT_MATCH + "' && nameMatchStatus=='" + NameMatchType.NO_MATCH
			+ "' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount}"
			+ Decision.UPDATE_CONTACT_DETAILS + "@else{walkinType=='" + WalkinType.CUSTOMER + "' && nbAccountType== '"
			+ NBAccountType.DIFFERENT + "' && otpStatus=='" + OTPStatus.MATCH + "' && nameMatchStatus=='"
			+ NameMatchType.NO_MATCH + "' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount}"
			+ Decision.RMCU + "@else{walkinType=='" + WalkinType.THIRD_PARTY + "' && nbAccountType== '"
			+ NBAccountType.SAME + "' && otpStatus=='" + OTPStatus.MATCH + "' && nameMatchStatus=='"
			+ NameMatchType.FULL_MATCH + "' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount}"
			+ Decision.EXPERT_INITIAL + "@else{walkinType=='" + WalkinType.THIRD_PARTY + "' && nbAccountType== '"
			+ NBAccountType.SAME + "' && otpStatus=='" + OTPStatus.DID_NOT_MATCH + "' && nameMatchStatus=='"
			+ NameMatchType.FULL_MATCH + "' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount}"
			+ Decision.CUSTOMER_WALKIN_REQUIRED + "@else{walkinType=='" + WalkinType.THIRD_PARTY
			+ "' && nbAccountType== '" + NBAccountType.SAME + "' && otpStatus=='" + OTPStatus.MATCH
			+ "' && nameMatchStatus=='" + NameMatchType.PARTIAL_MATCH
			+ "' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount}" + Decision.EXPERT_INITIAL
			+ "@else{walkinType=='" + WalkinType.THIRD_PARTY + "' && nbAccountType== '" + NBAccountType.SAME
			+ "' && otpStatus=='" + OTPStatus.DID_NOT_MATCH + "' && nameMatchStatus=='" + NameMatchType.PARTIAL_MATCH
			+ "' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount}"
			+ Decision.CUSTOMER_WALKIN_REQUIRED + "@else{walkinType=='" + WalkinType.THIRD_PARTY
			+ "' && nbAccountType== '" + NBAccountType.SAME + "' && otpStatus=='" + OTPStatus.MATCH
			+ "' && nameMatchStatus=='" + NameMatchType.NO_MATCH
			+ "' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount}" + Decision.RMCU
			+ "@else{walkinType=='" + WalkinType.THIRD_PARTY + "' && nbAccountType== '" + NBAccountType.SAME
			+ "' && otpStatus=='" + OTPStatus.DID_NOT_MATCH + "' && nameMatchStatus=='" + NameMatchType.NO_MATCH
			+ "' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount}"
			+ Decision.CUSTOMER_WALKIN_REQUIRED + "@else{}" + Decision.NO_DECISION + "@end{}";

	private ParserContext pctx = new ParserContext();
	private CompiledTemplate compiled = null;

	@PostConstruct
	public void init() {
		compiled = TemplateCompiler.compileTemplate(template, pctx);
		logger.trace("template:::" + template);
	}

	public String parseAndGetResult(Object object) {
		Map<String, Object> inputVars = convertObjectToMap(object);
		LoggerUtils.debug(logger, "Input values:" + inputVars);
		String output = (String) TemplateRuntime.execute(compiled, inputVars);
		return output;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> convertObjectToMap(Object object) {
		ObjectMapper oMapper = new ObjectMapper();
		return oMapper.convertValue(object, Map.class);
	}
}

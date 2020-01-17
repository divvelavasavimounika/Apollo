package com.hdfc.irm.engine;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.mvel2.ParserContext;
import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRuntime;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class IrmRuleEngine {
	private static Logger logger = Logger.getLogger(IrmRuleEngine.class);

	private static final String template = "@if{walkinType=='CUSTOMER' && nbAccountType== 'SAME' && otpStatus=='NOT_REQUIRED'  && nameMatchStatus=='FULL_MATCH' && paymentAmount <=lowerBoundAmount}STP"
			+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'SAME' && otpStatus=='NOT_REQUIRED' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount <=lowerBoundAmount}EXPERT_INITIAL"
			+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'SAME' && otpStatus=='NOT_REQUIRED' && nameMatchStatus=='FULL_MATCH' && paymentAmount > upperBoundAmount}EXPERT_INITIAL"
			+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'SAME' && otpStatus=='NOT_REQUIRED' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount > upperBoundAmount}EXPERT_EXPERT"
			+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'SAME' && otpStatus=='NOT_REQUIRED' && nameMatchStatus=='NO_MATCH' && paymentAmount <=lowerBoundAmount}RMCU"
			+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'SAME' && otpStatus=='NOT_REQUIRED' && nameMatchStatus=='NO_MATCH' && paymentAmount  > upperBoundAmount}RMCU"
			+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='FULL_MATCH' && paymentAmount <=lowerBoundAmount}UPDATE_CONTACT_DETAILS"
			+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='MATCH' && nameMatchStatus=='FULL_MATCH' && paymentAmount <=lowerBoundAmount}EXPERT_INITIAL"
			+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount <=lowerBoundAmount}UPDATE_CONTACT_DETAILS"
			+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='MATCH' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount <=lowerBoundAmount}EXPERT_EXPERT"
			+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='FULL_MATCH' && paymentAmount  > upperBoundAmount}UPDATE_CONTACT_DETAILS"
			+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='MATCH' && nameMatchStatus=='FULL_MATCH' && paymentAmount  > upperBoundAmount}EXPERT_EXPERT"
			+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount  > upperBoundAmount}UPDATE_CONTACT_DETAILS"
			+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='MATCH' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount  > upperBoundAmount}EXPERT_EXPERT"
			+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='NO_MATCH' && paymentAmount <=lowerBoundAmount}UPDATE_CONTACT_DETAILS"
			+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='MATCH' && nameMatchStatus=='NO_MATCH' && paymentAmount <=lowerBoundAmount}RMCU"
			+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='NO_MATCH' && paymentAmount  > upperBoundAmount}UPDATE_CONTACT_DETAILS"
			+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='MATCH' && nameMatchStatus=='NO_MATCH' && paymentAmount  > upperBoundAmount}RMCU"
			+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='MATCH' && nameMatchStatus=='FULL_MATCH' && paymentAmount <=lowerBoundAmount}STP"
			+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='FULL_MATCH' && paymentAmount <=lowerBoundAmount}CUSTOMER_WALKIN_REQUIRED"
			// special case
			+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'DIFFERENT' && otpStatus=='NOT_REQUIRED' && nameMatchStatus=='NOT_REQUIRED'}CUSTOMER_WALKIN_REQUIRED"
			+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='MATCH' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount <=lowerBoundAmount}EXPERT_INITIAL"
			+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount <=lowerBoundAmount}CUSTOMER_WALKIN_REQUIRED"
			+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='MATCH' && nameMatchStatus=='FULL_MATCH' && paymentAmount > upperBoundAmount}EXPERT_INITIAL"
			+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='FULL_MATCH' && paymentAmount > upperBoundAmount}CUSTOMER_WALKIN_REQUIRED"
			+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='MATCH' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount > upperBoundAmount}EXPERT_EXPERT"
			+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount > upperBoundAmount}CUSTOMER_WALKIN_REQUIRED"
			+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='MATCH' && nameMatchStatus=='NO_MATCH' && paymentAmount <=lowerBoundAmount}RMCU"
			+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='NO_MATCH' && paymentAmount <=lowerBoundAmount}CUSTOMER_WALKIN_REQUIRED"
			+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='MATCH' && nameMatchStatus=='NO_MATCH' && paymentAmount > upperBoundAmount}RMCU"
			+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='NO_MATCH' && paymentAmount > upperBoundAmount}CUSTOMER_WALKIN_REQUIRED"
			+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'SAME' && otpStatus=='NOT_REQUIRED' && nameMatchStatus=='FULL_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount  }EXPERT_INITIAL"
			+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'SAME' && otpStatus=='NOT_REQUIRED' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount}EXPERT_INITIAL"
			+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'SAME' && otpStatus=='NOT_REQUIRED' && nameMatchStatus=='NO_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount}RMCU"
			+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='FULL_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount}UPDATE_CONTACT_DETAILS"
			+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='MATCH' && nameMatchStatus=='FULL_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount}EXPERT_INITIAL"
			+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount}UPDATE_CONTACT_DETAILS"
			+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='MATCH' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount}EXPERT_EXPERT"
			+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='NO_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount}UPDATE_CONTACT_DETAILS"
			+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='MATCH' && nameMatchStatus=='NO_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount}RMCU"
			+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='MATCH' && nameMatchStatus=='FULL_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount}EXPERT_INITIAL"
			+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='FULL_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmountt}CUSTOMER_WALKIN_REQUIRED"
			+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='MATCH' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount}EXPERT_INITIAL"
			+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount}CUSTOMER_WALKIN_REQUIRED"
			+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='MATCH' && nameMatchStatus=='NO_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmountt}RMCU"
			+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='NO_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount}CUSTOMER_WALKIN_REQUIRED'"
			+ "@else{}NO_DECISION@end{}";

	private ParserContext pctx = new ParserContext();
	private CompiledTemplate compiled = null;

	@PostConstruct
	public void init() {
		compiled = TemplateCompiler.compileTemplate(template);
		logger.trace("template:::" + template);
	}

	public String parseAndGetResult(Object object) {
		Map<String, Object> inputVars = convertObjectToMap(object);
		logger.info("Input values:" + inputVars);
		String output = (String) TemplateRuntime.execute(compiled, inputVars);
		return output;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> convertObjectToMap(Object object) {
		// Map<String, Object> map = new HashMap<>();
		ObjectMapper oMapper = new ObjectMapper();
		return oMapper.convertValue(object, Map.class);
	}
}

package com.hdfc.irm.engine;

import java.util.HashMap;
import java.util.Map;

import org.mvel2.ParserContext;
import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRuntime;

public class MvelTest {
	public static void main(String[] args) {
//		String template = "@if{a >= b } working @else{b==b} bbb  @end{}";
//		String template="@if{ a=='r'}if @else{} @if{a==b} else if @end{}";
		    final String template = "@if{walkinType=='CUSTOMER' && nbAccountType== 'SAME' && otpStatus=='NOT_REQUIRED'  && nameMatchStatus=='FULL_MATCH' && paymentAmount <=lowerBoundAmount} 'STP' "
				+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'SAME' && otpStatus=='NOT_REQUIRED' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount <=lowerBoundAmount} 'EXPERT_INITIAL'"
				+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'SAME' && otpStatus=='NOT_REQUIRED' && nameMatchStatus=='FULL_MATCH' && paymentAmount > upperBoundAmount} 'EXPERT_INITIAL' "
				+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'SAME' && otpStatus=='NOT_REQUIRED' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount > upperBoundAmount} 'EXPERT_EXPERT' "
				+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'SAME' && otpStatus=='NOT_REQUIRED' && nameMatchStatus=='NO_MATCH' && paymentAmount <=lowerBoundAmount} 'RMCU' "
				+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'SAME' && otpStatus=='NOT_REQUIRED' && nameMatchStatus=='NO_MATCH' && paymentAmount  > upperBoundAmount} 'RMCU' "
				+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='FULL_MATCH' && paymentAmount <=lowerBoundAmount} 'UPDATE_CONTACT_DETAILS' "
				+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='MATCH' && nameMatchStatus=='FULL_MATCH' && paymentAmount <=lowerBoundAmount} 'EXPERT_INITIAL' "
				+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount <=lowerBoundAmount} 'UPDATE_CONTACT_DETAILS' "
				+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='MATCH' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount <=lowerBoundAmount} 'EXPERT_EXPERT' "
				+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='FULL_MATCH' && paymentAmount  > upperBoundAmount} 'UPDATE_CONTACT_DETAILS' "
				+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='MATCH' && nameMatchStatus=='FULL_MATCH' && paymentAmount  > upperBoundAmount} 'EXPERT_EXPERT' "
				+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount  > upperBoundAmount} 'UPDATE_CONTACT_DETAILS' "
				+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='MATCH' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount  > upperBoundAmount} 'EXPERT_EXPERT' "
				+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='NO_MATCH' && paymentAmount <=lowerBoundAmount} 'UPDATE_CONTACT_DETAILS' "
				+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='MATCH' && nameMatchStatus=='NO_MATCH' && paymentAmount <=lowerBoundAmount} 'RMCU' "
				+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='NO_MATCH' && paymentAmount  > upperBoundAmount} 'UPDATE_CONTACT_DETAILS' "
				+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='MATCH' && nameMatchStatus=='NO_MATCH' && paymentAmount  > upperBoundAmount} 'RMCU' "
				+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='MATCH' && nameMatchStatus=='FULL_MATCH' && paymentAmount <=lowerBoundAmount} 'STP' "
				+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='FULL_MATCH' && paymentAmount <=lowerBoundAmount} 'CUSTOMER_WALKIN_REQUIRED' "
//				+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'DIFFERENT' && otpStatus=='NOT_REQUIRED' && nameMatchStatus=='NOT_REQUIRED'} 'CUSTOMER_WALKIN_REQUIRED' "
				+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='MATCH' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount <=lowerBoundAmount} 'EXPERT_INITIAL' " 
				+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount <=lowerBoundAmount} 'CUSTOMER_WALKIN_REQUIRED' "
				+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='MATCH' && nameMatchStatus=='FULL_MATCH' && paymentAmount > upperBoundAmount} 'EXPERT_INITIAL' "
				+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='FULL_MATCH' && paymentAmount > upperBoundAmount} 'CUSTOMER_WALKIN_REQUIRED' "
				+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='MATCH' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount > upperBoundAmount} 'EXPERT_EXPERT' "
				+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount > upperBoundAmount} 'CUSTOMER_WALKIN_REQUIRED' "
				+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='MATCH' && nameMatchStatus=='NO_MATCH' && paymentAmount <=lowerBoundAmount} 'RMCU' " 
				+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='NO_MATCH' && paymentAmount <=lowerBoundAmount} 'CUSTOMER_WALKIN_REQUIRED' "
				+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='MATCH' && nameMatchStatus=='NO_MATCH' && paymentAmount > upperBoundAmount} 'RMCU' "
				+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='NO_MATCH' && paymentAmount > upperBoundAmount} 'CUSTOMER_WALKIN_REQUIRED' "
				+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'SAME' && otpStatus=='NOT_REQUIRED' && nameMatchStatus=='FULL_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount  } 'EXPERT_INITIAL' " 
				+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'SAME' && otpStatus=='NOT_REQUIRED' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount} 'EXPERT_INITIAL' "
				+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'SAME' && otpStatus=='NOT_REQUIRED' && nameMatchStatus=='NO_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount} 'RMCU' "
				+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='FULL_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount} 'UPDATE_CONTACT_DETAILS' "
				+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='MATCH' && nameMatchStatus=='FULL_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount} 'EXPERT_INITIAL' "
				+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount} 'UPDATE_CONTACT_DETAILS' "
				+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='MATCH' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount} 'EXPERT_EXPERT' "
				+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='NO_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount} 'UPDATE_CONTACT_DETAILS' "
				+ "@else{walkinType=='CUSTOMER' && nbAccountType== 'DIFFERENT' && otpStatus=='MATCH' && nameMatchStatus=='NO_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount} 'RMCU' "
				+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='MATCH' && nameMatchStatus=='FULL_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount} 'EXPERT_INITIAL' "
				+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='FULL_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmountt} 'CUSTOMER_WALKIN_REQUIRED' "
				+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='MATCH' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount} 'EXPERT_INITIAL' "
				+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='PARTIAL_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount} 'CUSTOMER_WALKIN_REQUIRED' "
				+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='MATCH' && nameMatchStatus=='NO_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmountt} 'RMCU' "
				+ "@else{walkinType=='THIRD_PARTY' && nbAccountType== 'SAME' && otpStatus=='DID_NOT_MATCH' && nameMatchStatus=='NO_MATCH' && paymentAmount >  lowerBoundAmount && paymentAmount <=  upperBoundAmount} 'CUSTOMER_WALKIN_REQUIRED' @end{}";

		ParserContext pctx = new ParserContext();

		// compile the template
		CompiledTemplate compiled = TemplateCompiler.compileTemplate(template);
//		Map<String, Object> m = new HashMap<String, Object>();
//		m.put("a", 10);
//		m.put("b", 20);
//		// execute the template
//		String output = (String) TemplateRuntime.execute(compiled, m);
//		System.out.println(output);

	}
}

package com.hdfc.irm.engine.utils;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.hdfc.irm.BeanJnuitTest;
import com.hdfc.irm.engine.exception.ApiError;
import com.hdfc.irm.engine.model.DecisionRequest;
import com.hdfc.irm.engine.model.DecisionResponse;

public class BeanSetterGetterTests {

	@Test
	public void testAllBeansSetter_Getter() throws InstantiationException, IllegalAccessException {
		BeanJnuitTest beanTest = new BeanJnuitTest(ApplicationProperties.class);
		beanTest.test();
		BeanJnuitTest beanTest1 = new BeanJnuitTest(DecisionRequest.class);
		beanTest1.test();
		BeanJnuitTest beanTest2 = new BeanJnuitTest(DecisionResponse.class);
		beanTest2.test();
		BeanJnuitTest beanTest3 = new BeanJnuitTest(ApiError.class);
		beanTest3.test();
		ApiError apiError = new ApiError(HttpStatus.ACCEPTED, "ACCEPTED");
	}

}

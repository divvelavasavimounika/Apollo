package com.hdfc.irm.engine.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RestUtilServiceTests {

	RestUtilService restUtilService = new RestUtilService();

	@Test
	public void testCallRestService_withHTTPS() {

		String testJSON = "{\"testkey\":\"test value\"}";
		String restResponse = (String) restUtilService.callRestService(testJSON, String.class,
				"https://postman-echo.com/post");
		assertThat(restResponse).isNotNull();
	}

}

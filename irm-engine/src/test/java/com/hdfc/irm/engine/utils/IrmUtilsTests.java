package com.hdfc.irm.engine.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IrmUtilsTests {

	@Test
	public void test_uuiId_notNull() {
		assertThat(IrmUtils.uuId()).isNotNull();
	}

	@Test
	public void test_encodeString_notNull() {
		assertThat(IrmUtils.encodeString("testTring")).isNotNull();
	}

	@Test
	public void test_encodeString_Null() {
		assertThat(new IrmUtils().encodeString(null)).isNull();
	}
}

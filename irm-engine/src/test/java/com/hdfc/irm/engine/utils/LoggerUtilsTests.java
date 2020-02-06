package com.hdfc.irm.engine.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class LoggerUtilsTests {

	@Test
	public void testGetStackStrace_NotNull() {
		assertThat(LoggerUtils.getStackStrace(new Exception("test exception"))).isNotNull();
		assertThat(new LoggerUtils().getStackStrace(new Exception("test exception"))).isNotEmpty();
	}

}

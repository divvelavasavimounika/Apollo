package com.hdfc.irm.engine.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hdfc.irm.engine.constants.NameMatchType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NameMatcherTests {

	@Autowired
	NameMatcher nameMatcher;

	@Test
	public void testPerformNameMatch() {
		nameMatcher.setNameMatchStatus(NameMatchType.FULL_MATCH);
		assertThat(nameMatcher.performNameMatch("dummy1", "dummy2")).isEqualTo(NameMatchType.FULL_MATCH);
	}

}

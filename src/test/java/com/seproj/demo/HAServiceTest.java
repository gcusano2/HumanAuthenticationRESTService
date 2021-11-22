package com.seproj.demo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import com.seproj.demo.models.HumanAuthenticator;
import com.seproj.demo.service.HAService;

class HAServiceTest {
	
	@Test
	void buildHAChallenge() {
		HAService hc = new HAService();
		HumanAuthenticator haChallenge = hc.buildHAChallenge();
		System.out.println(haChallenge);
		assertThat(haChallenge.getFullChallenge(), CoreMatchers.containsString("Please sum the following numbers: "));
	};

}

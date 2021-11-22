package com.seproj.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.seproj.demo.service.HAService;

@SpringBootTest
class HumanAuthenticatorRESTApplicationTest {

	@Autowired
	private HAService controller;
	
	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

}

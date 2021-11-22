package com.seproj.demo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.seproj.demo.dao.HARepo;
import com.seproj.demo.models.ClientResponse;
import com.seproj.demo.models.HumanAuthenticator;
import com.seproj.demo.service.HAService;
import com.fasterxml.jackson.databind.ObjectMapper;



@ExtendWith(SpringExtension.class)
@WebMvcTest(HAService.class)
class HAServiceIntTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private HARepo repo;
	
	//Tests that the get request is received and verifies the challenge without
	//the 3 randomly generated numbers
	@Test
	void requestTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/");
		MvcResult result = mvc.perform(request).andReturn();
		assertThat(result.getResponse().getContentAsString(), CoreMatchers.containsString("Please sum the following numbers: "));
	}
	
	//Tests that Post with bad sum returns a HTTP 400
	@Test
	public void badSum() throws Exception {
		HAService hc = new HAService();
		HumanAuthenticator haChallenge = hc.buildHAChallenge();
		
		ClientResponse cr = new ClientResponse();
		cr.setReturnedChallenge(haChallenge.getFullChallenge());
		cr.setReturnedSum(haChallenge.getSum()+1);
		
		
		mvc.perform(post("/")
				.content(asJsonString(cr))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}

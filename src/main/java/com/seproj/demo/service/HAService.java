package com.seproj.demo.service;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.seproj.demo.dao.HARepo;
import com.seproj.demo.models.ClientResponse;
import com.seproj.demo.models.HumanAuthenticator;

@RestController
public class HAService {
	
	
	private static final String CHALLENGE_PROMPT = "Please sum the following numbers: ";
	@Autowired
	HARepo repo;
	
	@RequestMapping("/")
	@ResponseBody
	public String getChallenge()
	{
		
		HumanAuthenticator challenge = buildHAChallenge();
		
		//The object is stored in an H2 database for verification after receiving the client's response
		repo.save(challenge);
		
		//The full challenge is sent to the client
		//ex. "Please sum the following numbers: [7, 9, 2]"
		return challenge.getFullChallenge();
	}

	
	@PostMapping("/")
	public ResponseEntity<String> challengeResponse(@RequestBody ClientResponse cr)
	{
		
		try {
			HumanAuthenticator ha = repo.findByFullChallenge(cr.getReturnedChallenge());
			
			//If the client returnedChallenge or returnedSum is incorrect,
			//the findByFullChallenge query should return null
			if (ha == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect. Please try again");
			}
			
			//Returns HTTP 200 if the returnedChallenge and returnedSum match the original
			//values in the database

			else if (ha.getFullChallenge().equals(cr.getReturnedChallenge()) &&
					ha.getSum() == cr.getReturnedSum()) {
				return ResponseEntity.ok("Correct. You are human");
			}
			else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect. Please try again");
			}
		}
		catch (DataAccessException excpetion) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect. Please try again");
		}
		
	}
	
	public HumanAuthenticator buildHAChallenge() {
		HumanAuthenticator ha = new HumanAuthenticator();
		
		//When the client sends a request for a challenge, a new HumanAuthenticator object is
		//created with 3 random integers to sum.
		List<Integer> nums = new ArrayList<Integer>();
		Random rand = new Random();
		for (int i=0; i < 3; i++) {
			nums.add(rand.nextInt(30));
		}
			
		ha.setNumbers(nums);
		ha.setFullChallenge(CHALLENGE_PROMPT + String.join(", ", ha.getNumbers().toString()));
		ha.setSum(nums.stream().mapToInt(Integer::intValue).sum());
		return ha;
	}
	
	

}

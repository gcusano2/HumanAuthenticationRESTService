package com.seproj.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seproj.demo.models.HumanAuthenticator;

public interface HARepo extends JpaRepository<HumanAuthenticator, Integer>{
	
	HumanAuthenticator findByFullChallenge(String fullChallenge);

}

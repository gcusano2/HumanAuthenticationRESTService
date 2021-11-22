package com.seproj.demo.models;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;


import org.springframework.stereotype.Component;

@Component
@Entity
public class HumanAuthenticator {

	@Id
	private String fullChallenge;
	@ElementCollection
	@CollectionTable(name="listOfNumbers")
	private List<Integer> numbers;
	private int sum;

	
	public HumanAuthenticator() {
		super();
	}
	
	public String getFullChallenge() {
		return fullChallenge;
	}
	
	public void setFullChallenge(String fullChallenge) {
		this.fullChallenge = fullChallenge;
	}

	public List<Integer> getNumbers() {
		return numbers;
	}

	public void setNumbers(List<Integer> numbers) {
		this.numbers = numbers;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}
	
}

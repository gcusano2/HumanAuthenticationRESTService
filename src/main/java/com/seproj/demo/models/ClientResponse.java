package com.seproj.demo.models;

public class ClientResponse {

	private String returnedChallenge;
	private int returnedSum;
	
	public ClientResponse() {
		super();
	}
	
	public String getReturnedChallenge() {
		return returnedChallenge;
	}
	public void setReturnedChallenge(String returnedChallenge) {
		this.returnedChallenge = returnedChallenge;
	}
	public int getReturnedSum() {
		return returnedSum;
	}
	public void setReturnedSum(int returnedSum) {
		this.returnedSum = returnedSum;
	}
	
}

package com.quicky.objects;

import java.security.SecureRandom;

import org.json.JSONObject;

import com.quicky.aws.AWSDynamoDBHandler;

public class QuickyEnrollment {
	
	private String iban;
	private String quickyID;
	private String enrollmentJson;
	private int activationCode;
	
	public QuickyEnrollment(JSONObject enrollmentJson){
		
		
		this.iban = enrollmentJson.getString("iban");
		this.quickyID = enrollmentJson.getString("quickyID");
		if(enrollmentJson.isNull("activationCode")){
		this.activationCode = 100000 + new SecureRandom().nextInt(900000);
		enrollmentJson.put("activationCode" , activationCode);
		} else {
			this.activationCode = enrollmentJson.getInt("activationCode");
		}
		enrollmentJson.put("activationCode" , activationCode);
		this.enrollmentJson = enrollmentJson.toString();
		
		
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public String getQuickyID() {
		return quickyID;
	}
	public void setQuickyID(String quickyID) {
		this.quickyID = quickyID;
	}
	public String getEnrollmentJson() {
		return enrollmentJson;
	}
	public void setEnrollmentJson(String enrollmentJson) {
		this.enrollmentJson = enrollmentJson;
	}
	public int getActivationCode() {
		return activationCode;
	}
	public void setActivationCode(int activationCode) {
		this.activationCode = activationCode;
	}


}

package com.quicky.objects;

import java.security.SecureRandom;

import org.json.JSONObject;

public class QuickyClient {

	private String iban;
	private String quickyID;
	
	private String encryptedpin;
	private String status;

	private boolean pinAlwaysRequired;

	private String clientInfoJsonString;

	private String sukString1;
	private String sukString2;
	private String sukString3;
	
	public QuickyClient(){
		
	}

	public QuickyClient(JSONObject enrollmentJson) {

		this.iban = enrollmentJson.getString("iban");
		this.quickyID = enrollmentJson.getString("quickyID");
		this.encryptedpin = enrollmentJson.getString("encryptedpin");
		this.pinAlwaysRequired = enrollmentJson.getBoolean("pinAlwaysRequired");
		this.clientInfoJsonString = enrollmentJson.toString();
		this.sukString1 = Integer.toHexString(new SecureRandom().nextInt(900000));
		this.sukString2 = Integer.toHexString(new SecureRandom().nextInt(900000));
		this.sukString3 = Integer.toHexString(new SecureRandom().nextInt(900000));

	}

	public QuickyClient(JSONObject clientInfo, JSONObject sukStringJson) {
		this.iban = clientInfo.getString("iban");
		this.quickyID = clientInfo.getString("quickyID");
		this.encryptedpin = clientInfo.getString("encryptedpin");
		this.pinAlwaysRequired = clientInfo.getBoolean("pinAlwaysRequired");
		this.clientInfoJsonString = clientInfo.toString();
		this.sukString1 = sukStringJson.getString("sukString1");
		this.sukString2 = sukStringJson.getString("sukString2");
		this.sukString3 = sukStringJson.getString("sukString3");
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

	public String getEncryptedPin() {
		return encryptedpin;
	}

	public void setEncryptedPin(String encryptedPin) {
		this.encryptedpin = encryptedPin;
	}

	public String getEncryptedpin() {
		return encryptedpin;
	}

	public void setEncryptedpin(String encryptedpin) {
		this.encryptedpin = encryptedpin;
	}

	public boolean isPinAlwaysRequired() {
		return pinAlwaysRequired;
	}

	public void setPinAlwaysRequired(boolean pinAlwaysRequired) {
		this.pinAlwaysRequired = pinAlwaysRequired;
	}

	public String getClientInfoJsonString() {
		return clientInfoJsonString;
	}

	public void setClientInfoJsonString(String clientInfoJsonString) {
		this.clientInfoJsonString = clientInfoJsonString;
	}

	public String getSukString1() {
		return sukString1;
	}

	public void setSukString1(String sukString1) {
		this.sukString1 = sukString1;
	}

	public String getSukString2() {
		return sukString2;
	}

	public void setSukString2(String sukString2) {
		this.sukString2 = sukString2;
	}

	public String getSukString3() {
		return sukString3;
	}

	public void setSukString3(String sukString3) {
		this.sukString3 = sukString3;
	}
}

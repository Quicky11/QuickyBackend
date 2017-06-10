package com.quicky.objects;

import java.math.BigInteger;

import org.json.JSONObject;

public class PaymentRequest {

	private String iban;
	private String paymentToken;
	private String vendorId;
	private float amount;
	private String quickyId;

	public PaymentRequest() {

	}

	public PaymentRequest(JSONObject requestJson) {
		this.iban = requestJson.getString("iban");
		this.paymentToken = requestJson.getString("paymentToken");
		this.vendorId = requestJson.getString("vendorId");
		this.amount = requestJson.getBigInteger("amount").floatValue()/100;
		this.quickyId = requestJson.getString("quickyId");

	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getPaymentToken() {
		return paymentToken;
	}

	public void setPaymentToken(String paymentToken) {
		this.paymentToken = paymentToken;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getQuickyId() {
		return quickyId;
	}

	public void setQuickyId(String quickyId) {
		this.quickyId = quickyId;
	}

}

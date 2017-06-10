package com.quicky.rest.client;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.json.JSONObject;

import com.quicky.objects.PaymentRequest;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;

public class PaymentHandler {

	public static JSONObject initiatePayment(PaymentRequest paymentRequest, String token) {
		
		final String THIRD_PARTY_IBAN = "NL15ABNA0430202662";
		
		Client client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		
		WebResource webResource = client.resource("https://api-sandbox.abnamro.com/v1/pis/thirdpartypaymentinstruction");
		JSONObject paymentValues = new JSONObject();
		paymentValues.put("initiatingPartyAccountNumber", paymentRequest.getIban());
		paymentValues.put("counterpartyAccountNumber", THIRD_PARTY_IBAN);
		paymentValues.put("counterpartyName", paymentRequest.getVendorId());
		paymentValues.put("amount", paymentRequest.getAmount());
		paymentValues.put("remittanceInfo", "TestTransaction");
		paymentValues.put("executionDate", "2017-06-10");
		//paymentVales need to be nested under "thirdPartyPaymentInstruction"
		String thirdPartyPaymentInstruction = new JSONObject().put("thirdPartyPaymentInstruction", paymentValues).toString();
		
		String authorizationKey = "Bearer "+ token;
		ClientResponse response = webResource.header("authorization", authorizationKey).type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, thirdPartyPaymentInstruction);
		response.bufferEntity();
		JSONObject responseJson = new JSONObject(response.getEntity(String.class));
		
		return responseJson;
		
	}

}

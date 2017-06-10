package com.quicky.rest.client;

import java.util.Locale;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.json.JSONObject;

import com.quicky.objects.PaymentRequest;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;

public class BalanceChecker {
	
	final static String CURRENCY = "EUR";
	final static String URI_T0_FORMAT = "https://api-sandbox.abnamro.com/v1/ais/fundsavailability?accountNumber=%1s&currency=%2s&amount=%.2f";
	
	public static boolean checkBalance(PaymentRequest request, String token){
		Client client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		
		String authorizationKey = "Bearer "+ token;
		//Setting Locale to UK to not use comma's as decimal seperator
		String formattedURI = String.format(Locale.UK, URI_T0_FORMAT, request.getIban(), CURRENCY, request.getAmount() );

		WebResource webResource = client.resource(formattedURI);
		
		ClientResponse response = webResource.header("authorization", authorizationKey).get(ClientResponse.class);
		response.bufferEntity();
		return new JSONObject(response.getEntity(String.class).toString()).getBoolean("isFundsAvailable");
	}

}

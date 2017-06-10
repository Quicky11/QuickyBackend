package com.quicky.rest.client;

import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;


public class IbanChecker {
	
	public static boolean checkIban(String iban, String token){
		
		Client client = Client.create();
		client.addFilter(new LoggingFilter(System.out));
		
		String authorizationKey = "Bearer "+ token;

		WebResource webResource = client.resource("https://api-sandbox.abnamro.com/v1/iban/"+iban+ "/validity");
		ClientResponse response = webResource.header("authorization", authorizationKey).get(ClientResponse.class);
		response.bufferEntity();
		String entity = response.getEntity(String.class);
		
		JSONObject jsonObject = new JSONObject(entity);
		boolean isValidIban = jsonObject.getBoolean("isValid");
		
		
		return isValidIban;
		
	}

}

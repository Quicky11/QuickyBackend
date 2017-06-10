package com.quicky.rest.client;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;

public class OAuthToken {

	static Logger LOG = Logger.getGlobal();

	private final static String API_KEY = "ROcqCcctPdt0AcW4ALXGEi0Xe7ab5mJC";
	private final static String API_SECRET = "EZhnKfbUlP7kiw1q";
	private final static String STRING_TO_ENCODE = API_KEY + ":" + API_SECRET;
	private final static String TEST_IBAN = "NL82ABNA0236536203";

	public static String getToken(String iban)  {
		String encodedKeySecret = "";
		try {
			encodedKeySecret = Base64.getEncoder().encodeToString(STRING_TO_ENCODE.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			LOG.log(Level.WARNING, e.getMessage());

		}
		try {

			Client client = Client.create();
			client.addFilter(new LoggingFilter(System.out));

			WebResource webResource = client.resource("https://api-sandbox.abnamro.com/v1/oauth/token");

			MultivaluedMap<String, String> valueMap = new MultivaluedHashMap<String, String>();
			valueMap.add("grant_type", "client_credentials");
			valueMap.add("accountNumber", iban);

			ClientResponse response = webResource.header("authorization", encodedKeySecret)
					.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class, valueMap);
			response.bufferEntity();

			JSONObject jsonObject = new JSONObject(response.getEntity(String.class));
			String token = jsonObject.getString("access_token");

			return token;
		} finally {
		}
	}
}

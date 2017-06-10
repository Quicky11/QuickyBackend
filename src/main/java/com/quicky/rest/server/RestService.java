package com.quicky.rest.server;

import java.io.UnsupportedEncodingException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.quicky.aws.AWSDynamoDBHandler;
import com.quicky.handler.EnrollmentHandler;
import com.quicky.handler.PaymentRequestHandler;
import com.quicky.objects.QuickyClient;
import com.quicky.rest.client.IbanChecker;
import com.quicky.rest.client.OAuthToken;

@Path("/")
public class RestService {

	@GET
	@Path("/checkIban/{param}")
	public Response getMsg(@PathParam("param") String iban) throws UnsupportedEncodingException {

		String token = OAuthToken.getToken(iban);

		boolean isValidIban = IbanChecker.checkIban(iban, token);
		String output = "IBAN: " + iban + " Token: " + token + " Valid Iban = " + isValidIban;
		return Response.status(200).entity(output).build();

	}

	@POST

	@Path("/enrollment/initialize")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response enrollQuickyClient(String json) {
		JSONObject responseJson = new JSONObject();
		/* PaymentRequestHandler.handlePayment(json) */;
		try {

			EnrollmentHandler.handleEnrollment(json);
		} catch (Exception E) {
			responseJson.put("isSuccesfull", false);
			return Response.status(400).entity(responseJson.toString()).build();
		}

		// return HTTP response 200 in case of success
		responseJson.put("isSuccesfull", true);
		return Response.status(200).entity(responseJson.toString()).build();
	}

	@POST

	@Path("/enrollment/validate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response validateQuickyClient(String json) {
		JSONObject responseJson = new JSONObject();
		QuickyClient quickyClient = new QuickyClient();
		try {
			quickyClient = EnrollmentHandler.handleValidation(json);
		} catch (Exception e) {
			responseJson.put("validationSuccesful", false);
			responseJson.put("errorMessage", e.getMessage());
			return Response.status(403).entity(responseJson.toString()).build();
		}
		// return HTTP response 200 in case of success
		responseJson.put("validationSuccesful", true);
		responseJson.put("sukString1", quickyClient.getSukString1());
		responseJson.put("sukString2", quickyClient.getSukString2());
		responseJson.put("sukString3", quickyClient.getSukString3());

		return Response.status(200).entity(responseJson.toString()).build();
	}

	@POST
	@Path("/payment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response makePayment(String json) {
		JSONObject responseJson = new JSONObject();
		try {
			responseJson = PaymentRequestHandler.handlePayment(json);
		} catch (Exception e) {
			responseJson.put("errorMessage", e.getMessage());
			return Response.status(400).entity(responseJson.toString()).build();
		}

		return Response.status(200).entity(responseJson.toString()).build();

	}

}
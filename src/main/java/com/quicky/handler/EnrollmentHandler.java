package com.quicky.handler;

import org.json.JSONObject;

import com.quicky.aws.Persistence;
import com.quicky.objects.QuickyEnrollment;
import com.quicky.objects.QuickyClient;
import com.quicky.rest.client.IbanChecker;
import com.quicky.rest.client.OAuthToken;

public class EnrollmentHandler {

	public static void handleEnrollment(String json) throws Exception {

		JSONObject enrollmentJson = new JSONObject(json);
		QuickyEnrollment enrollmentRequest = new QuickyEnrollment(enrollmentJson);

		if (IbanChecker.checkIban(enrollmentRequest.getIban(), OAuthToken.getToken(enrollmentRequest.getIban()))) {
			Persistence.persistRequest(enrollmentRequest);

		} else {
			throw new Exception ("Invalid Iban");
		}
		

	}

	public static QuickyClient handleValidation(String json) throws Exception {
		
		JSONObject validationJson = new JSONObject(json);
		QuickyClient quickyClient = new QuickyClient(validationJson);
		int activationCode = validationJson.getInt("activationCode");

		if (validateEnrollment(quickyClient, activationCode)) {
			Persistence.persistClient(quickyClient);
		} else {
			throw new Exception("Validation Failed");
		}
		return quickyClient;
	}

	private static boolean validateEnrollment(QuickyClient quickyClient, int activationCode) {
		QuickyEnrollment quickyEnrollment = Persistence.getQuickyEnrollment(quickyClient.getQuickyID());
		if (activationCode == (quickyEnrollment.getActivationCode())) {
			return true;
		}
		return false;

	}

}

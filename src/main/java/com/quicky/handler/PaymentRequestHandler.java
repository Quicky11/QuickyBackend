package com.quicky.handler;

import java.security.SecureRandom;

import org.json.JSONObject;

import com.quicky.aws.Persistence;
import com.quicky.objects.PaymentRequest;
import com.quicky.objects.QuickyClient;
import com.quicky.rest.client.BalanceChecker;
import com.quicky.rest.client.OAuthToken;
import com.quicky.rest.client.PaymentHandler;

public class PaymentRequestHandler {

	public static JSONObject handlePayment(String json) throws Exception {
		JSONObject responseJson = null;
		PaymentRequest paymentRequest = new PaymentRequest(new JSONObject(json));
		QuickyClient quickyClient = Persistence.getQuickyClient(paymentRequest.getQuickyId());

		if(!validatePaymentRequest(paymentRequest, quickyClient)){
			throw new Exception("Verification Failed");
		}
		// get token for payment
		String token = OAuthToken.getToken(paymentRequest.getIban());
		// check funds
		boolean fundsAvailable = BalanceChecker.checkBalance(paymentRequest, token);

		if (fundsAvailable) {
			// Start payment
			responseJson = PaymentHandler.initiatePayment(paymentRequest, token);
		} else {
			throw new Exception ("not enough available funds");
		}
		//everything succesful, generate new suks and persist
		quickyClient = generateNewSuks(quickyClient);
		return responseJson;
	}

	private static QuickyClient generateNewSuks(QuickyClient quickyClient) {
		quickyClient.setSukString1(Integer.toHexString(new SecureRandom().nextInt(900000)));
		quickyClient.setSukString2(Integer.toHexString(new SecureRandom().nextInt(900000)));
		quickyClient.setSukString3(Integer.toHexString(new SecureRandom().nextInt(900000)));
		Persistence.updateQuickyClient(quickyClient);
		return quickyClient;
	}

	private static boolean validatePaymentRequest(PaymentRequest paymentRequest, QuickyClient quickyClient) {
		return true;
		
	}

}

package com.quicky.aws;

import org.json.JSONObject;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.quicky.objects.QuickyClient;
import com.quicky.objects.QuickyEnrollment;

public class Persistence {

	public static void persistClient(QuickyClient quickyClient) {
		JSONObject sukStringJson = new JSONObject();
		sukStringJson.put("sukString1", quickyClient.getSukString1());
		sukStringJson.put("sukString2", quickyClient.getSukString2());
		sukStringJson.put("sukString3", quickyClient.getSukString3());

		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_WEST_1).build();

		DynamoDB dynamoDB = new DynamoDB(client);
		String tableName = "QuickyClients";
		try {

			Table table = dynamoDB.getTable(tableName);
			table.putItem(new Item().withPrimaryKey("quickyid", quickyClient.getQuickyID())
					.withJSON("clientinfo", quickyClient.getClientInfoJsonString())
					.withJSON("sukStrings", sukStringJson.toString()));
			System.out.println("put succesful");
		} catch (Exception e) {
			System.err.println("Unable to create table: ");
			System.err.println(e.getMessage());

		}
	}

	public static void persistRequest(QuickyEnrollment quickyEnrollment) {
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_WEST_1).build();

		DynamoDB dynamoDB = new DynamoDB(client);
		String tableName = "QuickyEnrollments";
		try {

			Table table = dynamoDB.getTable(tableName);
			table.putItem(new Item().withPrimaryKey("quickyid", quickyEnrollment.getQuickyID())
					.withJSON("enrollmentInfo", quickyEnrollment.getEnrollmentJson()));
			System.out.println("put succesful");
		} catch (Exception e) {
			System.err.println("Unable to put enrollment");
			System.err.println(e.getMessage());

		}
	}

	public static QuickyEnrollment getQuickyEnrollment(String quickyId) {
		QuickyEnrollment quickyEnrollment = null;
		Item item = null;
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_WEST_1).build();

		DynamoDB dynamoDB = new DynamoDB(client);
		String tableName = "QuickyEnrollments";

		Table table = dynamoDB.getTable(tableName);

		GetItemSpec spec = new GetItemSpec().withPrimaryKey("quickyid", quickyId);
		try {
			item = table.getItem(spec);
			System.out.println("GetItem succeeded: " + item);
		} catch (Exception e) {
			System.err.println("Unable get enrollmentItem");
			System.err.println(e.getMessage());

		}
		String enrollmentInfo = item.getJSON("enrollmentInfo");
		JSONObject enrollmentJson = new JSONObject(enrollmentInfo);
		quickyEnrollment = new QuickyEnrollment(enrollmentJson);
		return quickyEnrollment;

	}

	public static QuickyClient getQuickyClient(String quickyId) {
		QuickyClient quickyClient = null;
		Item item = null;
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_WEST_1).build();

		DynamoDB dynamoDB = new DynamoDB(client);
		String tableName = "QuickyClients";

		Table table = dynamoDB.getTable(tableName);

		GetItemSpec spec = new GetItemSpec().withPrimaryKey("quickyid", quickyId);
		try {
			item = table.getItem(spec);
			System.out.println("GetItem succeeded: " + item);
		} catch (Exception e) {
			System.err.println("Unable get enrollmentItem");
			System.err.println(e.getMessage());

		}
		JSONObject clientInfo = new JSONObject(item.getJSON("clientinfo"));
		JSONObject sukStringJson = new JSONObject(item.getJSON("sukStrings"));

		quickyClient = new QuickyClient(clientInfo, sukStringJson);
		return quickyClient;

	}

	public static void updateQuickyClient(QuickyClient quickyClient) {
		Item item = null;
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_WEST_1).build();

		DynamoDB dynamoDB = new DynamoDB(client);
		String tableName = "QuickyClients";

		Table table = dynamoDB.getTable(tableName);

		UpdateItemSpec spec = new UpdateItemSpec().withPrimaryKey("quickyid", quickyClient.getQuickyID()).withUpdateExpression("set sukStrings.sukString1 = :r, sukStrings.sukString2=:p, sukStrings.sukString3 =:a")
	            .withValueMap(new ValueMap().withString(":r", quickyClient.getSukString1()).withString(":p", quickyClient.getSukString1()).withString(":a", quickyClient.getSukString3()));
		
		try{
			table.updateItem(spec);
			System.out.println("Update succesful");
		} catch(Exception e){
			System.err.println("Update Unsuccesful");
		}
	}

}

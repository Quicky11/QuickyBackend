package com.quicky.aws;

import java.util.Arrays;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;


/*
 * 
 * Creates two database tables for enrollment purposes
 * Is never called after tables have been created
 */
public class AWSDynamoDBHandler {

	public static void makeTables() {

		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_WEST_1).build();

		DynamoDB dynamoDB = new DynamoDB(client);
		String tableName = "QuickyClients";
	
		try {
		Table table = dynamoDB.createTable(tableName,  Arrays.asList(new KeySchemaElement("quickyid", KeyType.HASH)), Arrays.asList(new AttributeDefinition("quickyid", ScalarAttributeType.S)), new ProvisionedThroughput(10L, 10L));
		System.out.println(table.getDescription().toString());

		} catch (Exception e) {
			System.err.println("Unable to create table: ");
			System.err.println(e.getMessage());
		}
		
		
		try {
		Table table = dynamoDB.createTable(tableName,  Arrays.asList(new KeySchemaElement("quickyid", KeyType.HASH)), Arrays.asList(new AttributeDefinition("quickyid", ScalarAttributeType.S)), new ProvisionedThroughput(10L, 10L));
		System.out.println(table.getDescription().toString());

		} catch (Exception e) {
			System.err.println("Unable to create table: ");
			System.err.println(e.getMessage());
		}

	}
}

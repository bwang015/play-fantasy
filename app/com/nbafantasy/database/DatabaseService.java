package com.nbafantasy.database;

import java.util.concurrent.CompletionStage;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;

public interface DatabaseService {
	<T extends Object> void save(T model);

	CompletionStage<PutItemOutcome> putItem(String id, String name);

	CompletionStage<Item> getItemFromName(String name);

	CompletionStage<Item> getItemFromID(String id);
	
	DynamoDB getDynamoDBObject();
}

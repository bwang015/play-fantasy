package com.nbafantasy.database;

import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;

import java.util.concurrent.CompletionStage;

public interface DatabaseService {
	CompletionStage<PutItemOutcome> putItem(String id, String name);

	CompletionStage<Item> getItemFromName(String name);

	CompletionStage<Item> getItemFromID(String id);
}

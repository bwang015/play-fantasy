package com.nbafantasy.database;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.internal.IteratorSupport;
import com.nbafantasy.util.Configuration;
import play.Logger;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class DatabaseServiceImpl implements DatabaseService {

	@Inject
	DynamoDBService dynamoDBService;

	@Inject
	Configuration config;

	private DynamoDB dynamoDB;

	@Override
	public <T> void save(T model) {
		dynamoDBService.getDBMapper().save(model);
	}

	@Override
	public CompletionStage<Item> getItem(String name) {
		Table table = getDynamoDBObject().getTable(config.getPlayerTable());

		Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
		expressionAttributeValues.put(":name", name);

		Map<String, String> expressionAttributeNames = new HashMap<String, String>();
		expressionAttributeNames.put("#Name", "Name");

		ItemCollection<ScanOutcome> items = table.scan("#Name = :name",
				"ID",
				expressionAttributeNames,
				expressionAttributeValues);

		Iterator<Item> iterator = items.iterator();
		if(iterator.hasNext())
			return CompletableFuture.completedFuture(iterator.next());

		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletionStage<PutItemOutcome> putItem(String id, String name) {
		Table table = getDynamoDBObject().getTable(config.getPlayerTable());
		Item item = table.getItem(new PrimaryKey("ID", id));

		if (item != null)
			return CompletableFuture.completedFuture(null);

		item = new Item()
				.withPrimaryKey("ID", id)
				.withString("Name", name);
		return CompletableFuture.completedFuture(table.putItem(item));
	}

	@Override
	public DynamoDB getDynamoDBObject() {
		if(dynamoDB == null)
			this.dynamoDB = new DynamoDB(dynamoDBService.getAsyncDynamoClient());
		return dynamoDB;
	}
}

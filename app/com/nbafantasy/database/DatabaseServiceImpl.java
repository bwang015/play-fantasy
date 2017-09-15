package com.nbafantasy.database;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.nbafantasy.util.Configuration;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class DatabaseServiceImpl<T> implements DatabaseService {

	@Inject
	DynamoDBService dynamoDBService;

	@Inject
	Configuration config;

	@Override
	public CompletionStage<Item> getItemFromID(String id) {
		Table table = dynamoDBService.getDB().getTable(config.getPlayerTable());
		QuerySpec spec = new QuerySpec()
				.withKeyConditionExpression("ID = :id")
				.withValueMap(new ValueMap()
						.withString(":id", id));

		ItemCollection<QueryOutcome> items = table.query(spec);
		Iterator<Item> iterator = items.iterator();
		if(iterator.hasNext())
			return CompletableFuture.completedFuture(iterator.next());
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletionStage<Item> getItemFromName(String name) {
		Table table = dynamoDBService.getDB().getTable(config.getPlayerTable());

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
		Table table = dynamoDBService.getDB().getTable(config.getPlayerTable());
		Item item = table.getItem(new PrimaryKey("ID", id));

		if (item != null)
			return CompletableFuture.completedFuture(null);

		item = new Item()
				.withPrimaryKey("ID", id)
				.withString("Name", name);
		return CompletableFuture.completedFuture(table.putItem(item));
	}
}

package com.nbafantasy.database;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public interface DatabaseService {
	<T extends Object> void save(T model);
	
	DynamoDB getDynamoDBObject();
}

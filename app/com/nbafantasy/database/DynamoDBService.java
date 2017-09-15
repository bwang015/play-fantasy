package com.nbafantasy.database;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public interface DynamoDBService {
	DynamoDB getDB();
	DynamoDBMapper getDBMapper();
	AmazonDynamoDBAsync getAsyncDynamoClient();
}

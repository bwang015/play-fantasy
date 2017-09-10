package com.nbafantasy.database;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public interface DynamoDBService {
	DynamoDBMapper getDBMapper();
	AmazonDynamoDBAsync getAsyncDynamoClient();
}

package com.nbafantasy.database;

import javax.inject.Inject;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public class DatabaseServiceImpl implements DatabaseService {

	@Inject
	DynamoDBService dynamoDBService;
	
	DynamoDB dynamoDB;

	@Override
	public <T> void save(T model) {
		dynamoDBService.getDBMapper().save(model);
	}

	@Override
	public DynamoDB getDynamoDBObject() {
		if(dynamoDB == null)
			this.dynamoDB = new DynamoDB(dynamoDBService.getAsyncDynamoClient());
		return dynamoDB;
	}
}

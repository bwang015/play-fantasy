package com.nbafantasy.test.database;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.nbafantasy.database.DatabaseService;
import com.nbafantasy.database.DynamoDBService;

import java.util.concurrent.CompletionStage;

/**
 * Created by bwang on 9/10/17.
 */
public class LocalDatabaseService implements DatabaseService {

    private DynamoDBService dynamoDBService;
    private DynamoDB dynamoDB;

    public LocalDatabaseService(DynamoDBService dynamoDBService) {
        this.dynamoDBService = dynamoDBService;
    }

    @Override
    public <T> void save(T model) {
        dynamoDBService.getDBMapper().save(model);
    }

    @Override
    public CompletionStage<PutItemOutcome> putItem(String id, String name) {
        return null;
    }

    @Override
    public CompletionStage<Item> getItem(String name) {
        return null;
    }

    @Override
    public DynamoDB getDynamoDBObject() {
        if(dynamoDB == null)
            this.dynamoDB = new DynamoDB(dynamoDBService.getAsyncDynamoClient());
        return dynamoDB;
    }
}

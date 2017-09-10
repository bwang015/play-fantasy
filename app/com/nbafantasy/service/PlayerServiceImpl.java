package com.nbafantasy.service;

import com.amazonaws.services.dynamodbv2.document.*;
import com.nbafantasy.database.DatabaseService;
import com.nbafantasy.util.Configuration;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by bwang on 9/9/17.
 */
public class PlayerServiceImpl implements PlayerService {

    @Inject
    DatabaseService dbService;

    @Inject
    Configuration config;

    @Override
    public CompletionStage<Integer> createPlayerIDFromName(String id, String name) {
        DynamoDB dynamoDB = dbService.getDynamoDBObject();
        Table table = dynamoDB.getTable(config.getPlayerTable());
        Item item = table.getItem(new PrimaryKey("ID", id));
        if (item != null) {
            return CompletableFuture.completedFuture(208); //Already exists
        }
        item = new Item()
                .withPrimaryKey("ID", id)
                .withString("Name", name);
        PutItemOutcome result = table.putItem(item);
        int status = result.getPutItemResult().getSdkHttpMetadata().getHttpStatusCode();
        return CompletableFuture.completedFuture(status);
    }
}

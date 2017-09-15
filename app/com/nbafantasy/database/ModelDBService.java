package com.nbafantasy.database;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import play.Logger;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by bwang on 9/13/17.
 */
public abstract class ModelDBService<T> {

    private final DynamoDBService dynamoDBService;

    @Inject
    public ModelDBService(DynamoDBService dynamoDBService) {
        this.dynamoDBService = dynamoDBService;
    }

    protected abstract Class<T> getClassType();

    public CompletionStage<PaginatedQueryList<T>> query(DynamoDBQueryExpression<T> queryExpression) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return dynamoDBService.getDBMapper().query(getClassType(),
                        queryExpression);
            }
            catch (AmazonServiceException ex) {
                String message = "Failed to perform query on object "
                        + getClassType().getSimpleName();
                Logger.error(message, ex);
                return null;
            }
        });
    }

    public CompletionStage<Void> save(T model, DynamoDBSaveExpression saveExpression) {
        return CompletableFuture.runAsync(() -> {
            try {
                dynamoDBService.getDBMapper().save(model, saveExpression);
            } catch (AmazonServiceException ex) {
                String message = "Error encountered while saving object "
                        + model.getClass().getSimpleName();
                Logger.error(message, ex);
            }
        });
    }
}

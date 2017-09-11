package com.nbafantasy.service;

import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.nbafantasy.database.DatabaseService;
import com.nbafantasy.exception.ResourceAlreadyExistsException;
import play.Logger;
import play.mvc.Http;
import play.mvc.Results;

/**
 * Created by bwang on 9/9/17.
 */
public class PlayerServiceImpl implements PlayerService {

    private DatabaseService dbService;

    @Inject
    public PlayerServiceImpl(DatabaseService dbService){
        this.dbService = dbService;
    }

    @Override
    public CompletionStage<String> getPlayerIDFromName(String name) {
        return dbService.getItem(name).thenApplyAsync(item -> {
            if(item == null)
                return null;
            return item.getString("ID");
        });
    }

    @Override
    public CompletionStage<Integer> createPlayerIDFromName(String id, String name) {
        return dbService.putItem(id, name).thenApply(result -> {
            int status = result.getPutItemResult().getSdkHttpMetadata().getHttpStatusCode();
            return status;
        }).exceptionally(throwable -> {
            if(throwable.getCause() instanceof ResourceAlreadyExistsException) {
                Logger.warn("Resource already exists!");
                return 208; //Already exists
            }
            return Http.Status.INTERNAL_SERVER_ERROR;
        });
    }
}

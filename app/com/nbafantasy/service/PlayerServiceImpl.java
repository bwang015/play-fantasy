package com.nbafantasy.service;

import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.nbafantasy.database.DatabaseService;

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
    public CompletionStage<PutItemOutcome> createPlayerIDFromName(String id, String name) {
        return dbService.putItem(id, name);
    }
}

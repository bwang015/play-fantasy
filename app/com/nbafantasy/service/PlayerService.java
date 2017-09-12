package com.nbafantasy.service;

import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;

import java.util.concurrent.CompletionStage;

/**
 * Created by bwang on 9/9/17.
 */
public interface PlayerService {
    CompletionStage<PutItemOutcome> createPlayerIDFromName(String id, String name);

    CompletionStage<String> getPlayerIDFromName(String name);
}

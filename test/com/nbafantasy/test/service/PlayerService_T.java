package com.nbafantasy.test.service;

import com.nbafantasy.database.DatabaseService;
import com.nbafantasy.database.DynamoDBService;
import com.nbafantasy.database.DynamoDBServiceConfig;
import com.nbafantasy.service.PlayerService;
import com.nbafantasy.service.PlayerServiceImpl;
import com.nbafantasy.test.database.LocalDatabaseService;
import com.nbafantasy.test.database.LocalDynamoDBService;
import com.nbafantasy.util.Configuration;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by bwang on 9/10/17.
 */
public class PlayerService_T {

    private PlayerService playerService;
    private DatabaseService dbService;
    private DynamoDBService dynamoDBService;
    private Configuration config;
    private DynamoDBServiceConfig dbConfig;

    @Before
    public void Setup() {
        this.dbConfig = mock(DynamoDBServiceConfig.class);
        this.dynamoDBService = new LocalDynamoDBService(dbConfig);
        this.dbService = new LocalDatabaseService(dynamoDBService);
        this.config = mock(Configuration.class);
        this.playerService = new PlayerServiceImpl(config, dbService);
    }

    @Test
    public void testCreatePlayerIDFromNameSuccess() throws ExecutionException, InterruptedException {
        String name = "Wilt Chamberlain";
        String id = "chambwi01";

        when(config.getPlayerTable()).thenReturn("PlayerName_T");
        when(dbConfig.getHost()).thenReturn("Host");
        when(dbConfig.getRegion()).thenReturn("Region");

        int status_code = doAsync(playerService.createPlayerIDFromName(id, name));
        assertEquals(200, status_code);
    }

    private <T> T doAsync(CompletionStage<T> completionStage)
            throws ExecutionException, InterruptedException {
        return completionStage.toCompletableFuture().get();
    }
}

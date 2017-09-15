package com.nbafantasy.service;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.nbafantasy.database.DatabaseService;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
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

    @Before
    public void Setup() {
        this.dbService = mock(DatabaseService.class);
        this.playerService = new PlayerServiceImpl(dbService);
    }

    @Test
    public void testCreatePlayerIDFromNameSuccess() throws ExecutionException, InterruptedException {
        String name = "Wilt Chamberlain";
        String id = "chambwi01";

        PutItemOutcome itemOutcome = new PutItemOutcome(new PutItemResult());

        when(dbService.putItem(id, name)).thenReturn(CompletableFuture.completedFuture(itemOutcome));

        PutItemOutcome response = doAsync(playerService.createPlayerIDFromName(id, name));
        assertEquals(itemOutcome, response);
    }

    @Test
    public void testCreatePlayerIDFromNameFailure() throws ExecutionException, InterruptedException {
        String name = "Allen Iverson";
        String id = "iversal01";

        PutItemOutcome itemOutcome = new PutItemOutcome(new PutItemResult());

        when(dbService.putItem(id, name)).thenReturn(CompletableFuture.completedFuture(null));

        PutItemOutcome response = doAsync(playerService.createPlayerIDFromName(id, name));
        assertEquals(null, response);
    }

    @Test
    public void testGetPlayerIDFromNameSuccess() throws ExecutionException, InterruptedException {
        String name = "Michael Jordan";
        String id = "jordami01";

        Item item = new Item()
                .with("ID", id);

        when(dbService.getItemFromName(name)).thenReturn(CompletableFuture.completedFuture(item));

        String response = doAsync(playerService.getPlayerIDFromName(name));
        assertEquals(id, response);
    }

    @Test
    public void testGetPlayerIDFromNameFailure() throws ExecutionException, InterruptedException {
        String name = "Jason Kidd";
        String id = "kiddja01";

        when(dbService.getItemFromName(name)).thenReturn(CompletableFuture.completedFuture(null));

        String response = doAsync(playerService.getPlayerIDFromName(name));
        assertEquals(null, response);
    }

    @Test
    public void testGetPlayerNameFromIDSuccess() throws ExecutionException, InterruptedException {
        String name = "Tyronn Lue";
        String id = "luety01";

        Item item = new Item()
                .with("Name", name);

        when(dbService.getItemFromID(id)).thenReturn(CompletableFuture.completedFuture(item));

        String response = doAsync(playerService.getPlayerNameFromID(id));
        assertEquals(name, response);
    }

    @Test
    public void testGetPlayerNameFromIDFailure() throws ExecutionException, InterruptedException {
        String name = "Karl Malone";
        String id = "malonka01";

        when(dbService.getItemFromID(id)).thenReturn(CompletableFuture.completedFuture(null));

        String response = doAsync(playerService.getPlayerNameFromID(id));
        assertEquals(null, response);
    }

    private <T> T doAsync(CompletionStage<T> completionStage)
            throws ExecutionException, InterruptedException {
        return completionStage.toCompletableFuture().get();
    }
}

package com.nbafantasy.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nbafantasy.database.DynamoDBService;
import com.nbafantasy.exception.ResourceNotFoundException;
import com.nbafantasy.models.PlayerInfo;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by bwang on 9/14/17.
 */
public class UploadService_T {

    private UploadService uploadService;
    private PlayerService playerService;
    private DynamoDBService dynamoDBService;

    @Before
    public void Setup() {
        this.playerService = mock(PlayerService.class);
        this.dynamoDBService = mock(DynamoDBService.class);
        uploadService = new UploadServiceImpl(dynamoDBService, playerService);
    }

    @Test
    public void testParsePlayerGameInformationNotFound() throws ExecutionException, InterruptedException {
        String name = "Steve Nash";
        String id = "nashst01";

        when(playerService.getPlayerNameFromID(id)).thenReturn(CompletableFuture.completedFuture(null));

        PlayerInfo player = doAsync(uploadService.parsePlayerGameInformation(createJsonNode("Name", name), id)
                .exceptionally(throwable -> {
                    if(throwable.getCause() instanceof ResourceNotFoundException)
                        return new PlayerInfo(id, name);
                    return null;
                }));

        assertEquals(name, player.getName());
        assertEquals(id, player.getId());
    }

    @Test
    public void testParsePlayerGameInformationSuccess() throws ExecutionException, InterruptedException {
        String name = "Shaquille O'Neal";
        String id = "onealsh01";

        DynamoDBMapper modelMapper = mock(DynamoDBMapper.class);
        PaginatedQueryList resultList = mock(PaginatedQueryList.class);

        Set<String> set = new HashSet<String>();
        set.add("ORL");
        set.add("LAL");
        set.add("MIA");
        set.add("PHO");
        set.add("CLE");
        set.add("BOS");

        when(playerService.getPlayerNameFromID(id)).thenReturn(CompletableFuture.completedFuture(name));
        when(dynamoDBService.getDBMapper()).thenReturn(modelMapper);
        when(modelMapper.query(any(), any())).thenReturn(resultList);
        when(resultList.isEmpty()).thenReturn(true);

        PlayerInfo player = doAsync(uploadService.parsePlayerGameInformation(createJsonNodeArray(set), id));

        assertEquals(name, player.getName());
        assertEquals(id, player.getId());
        assertEquals("CLE", player.getTeamName()); //set doesn't order them by first put
    }

    private JsonNode createJsonNode(String key, String value) {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put(key, value);
        return node;
    }

    private JsonNode createJsonNodeArray(Set<String> map) {
        ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
        for(String value: map){
            ObjectNode node = JsonNodeFactory.instance.objectNode();
            node.put("Team", value);
            node.put("Date", new Date().toString());
            arrayNode.add(node);
        }
        return arrayNode;
    }

    private <T> T doAsync(CompletionStage<T> completionStage)
            throws ExecutionException, InterruptedException {
        return completionStage.toCompletableFuture().get();
    }
}

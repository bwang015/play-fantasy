package com.nbafantasy.test.controllers;

import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import com.nbafantasy.injection.NBAInjector;
import com.nbafantasy.service.PlayerService;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static play.inject.Bindings.bind;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.route;

/**
 * Created by bwang on 9/9/17.
 */
public class PlayerController_T extends WithApplication {

    private PlayerService playerService;

    @Override
    protected Application provideApplication() {
        playerService = mock(PlayerService.class);
        return new GuiceApplicationBuilder()
                .configure("play.modules.disabled", Lists.newArrayList(NBAInjector.class.getName()))
                .bindings(bind(PlayerService.class).toInstance(playerService))
                .build();
    }

    @Test
    public void testCreatePlayerIDFromNameSuccess() throws IOException {
        String name = "Kareem Abdul-Jabbar";
        String id = "abdulka01";

        PutItemOutcome item = new PutItemOutcome(new PutItemResult());
        when(playerService.createPlayerIDFromName(id, name)).thenReturn(CompletableFuture.completedFuture(item));
        Result result = route(app, createRequest("PUT", "/player/" + id, name));
        assertEquals(Http.Status.CREATED, result.status());
    }

    @Test
    public void testCreatePlayerIDFromNameFailure() throws IOException {
        String name = ""; //Kobe Bryant
        String id = "bryanko01";

        Result result = route(app, createRequest("PUT", "/player/" + id, name));
        assertEquals(Http.Status.BAD_REQUEST, result.status());
    }

    @Test
    public void testCreatePlayerIDFromNameAlreadyExists() throws IOException {
        String name = "Clyde Drexler";
        String id = "drexlcl01";

        when(playerService.createPlayerIDFromName(id, name)).thenReturn(CompletableFuture.completedFuture(null));
        Result result = route(app, createRequest("PUT", "/player/" + id, name));
        assertEquals(208, result.status());
    }

    @Test
    public void testGetIDFromPlayerNameSuccess() throws IOException {
        String name = "Julius Erving";
        String id = "ervinju01";

        when(playerService.getPlayerIDFromName(name)).thenReturn(CompletableFuture.completedFuture(id));
        Result result = route(app, createRequest("POST", "/player", name));
        assertEquals(200, result.status());
    }

    @Test
    public void testGetIDFromPlayerNameNotFound() throws IOException {
        String name = "Steve Francis";
        String id = "francst01";

        when(playerService.getPlayerIDFromName(name)).thenReturn(CompletableFuture.completedFuture(null));
        Result result = route(app, createRequest("POST", "/player", name));
        assertEquals(404, result.status());
    }

    private Http.RequestBuilder createRequest(String method, String uri, String name) {
        Http.RequestBuilder request = fakeRequest(method, uri);
        request.header("Content-Type", "application/json");
        request.bodyJson(createJsonNode(name));
        return request;
    }

    private JsonNode createJsonNode(String name) {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("name", name);
        return node;
    }
}

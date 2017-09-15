package com.nbafantasy.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import com.google.inject.name.Names;
import com.nbafantasy.database.*;
import com.nbafantasy.exception.ResourceNotFoundException;
import com.nbafantasy.injection.NBAInjector;
import com.nbafantasy.models.PlayerInfo;
import com.nbafantasy.service.PlayerService;
import com.nbafantasy.service.PlayerServiceImpl;
import com.nbafantasy.service.UploadService;
import com.nbafantasy.service.UploadServiceImpl;
import com.nbafantasy.util.Configuration;
import com.nbafantasy.util.ConfigurationImpl;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static play.inject.Bindings.bind;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.route;

/**
 * Created by bwang on 9/12/17.
 */
public class UploadController_T extends WithApplication {

    private UploadService uploadService;

    @Override
    protected Application provideApplication() {
        uploadService = mock(UploadService.class);
        return new GuiceApplicationBuilder()
                .configure("play.modules.disabled", Lists.newArrayList(NBAInjector.class.getName()))
                .bindings(bind(UploadService.class).toInstance(uploadService),
                        bind(PlayerService.class).to(PlayerServiceImpl.class),
                        bind(DatabaseService.class).to(DatabaseServiceImpl.class),
                        bind(Configuration.class).to(ConfigurationImpl.class),
                        bind(DynamoDBService.class).to(DynamoDBServiceImpl.class),
                        bind(DynamoDBServiceConfig.class).to(DynamoDBServiceConfigImpl.class))
                .build();
    }

    @Test
    public void testUploadPlayerNotFound() throws IOException {
        String id = "garneke01";
        String name = "Kevin Garnett";

        CompletionException e = new CompletionException("Some message", new ResourceNotFoundException());
        CompletableFuture<PlayerInfo> future = new CompletableFuture<>();
        future.completeExceptionally(e);

        when(uploadService.parsePlayerGameInformation(any(), any())).thenReturn(future);

        Result result = route(app, createRequest("PUT", "/upload/player/" + id, name));
        assertEquals(Http.Status.NOT_FOUND, result.status());
    }

    @Test
    public void testUploadPlayerSuccess() throws IOException {
        String id = "hardaan01";
        String name = "Anfernee Hardaway";

        when(uploadService.parsePlayerGameInformation(any(), any())).thenReturn(CompletableFuture.completedFuture(new PlayerInfo(id, name)));

        Result result = route(app, createRequest("PUT", "/upload/player/" + id, name));
        assertEquals(Http.Status.OK, result.status());
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

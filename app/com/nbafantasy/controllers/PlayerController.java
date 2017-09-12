package com.nbafantasy.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.nbafantasy.exception.ErrorHandler;
import com.nbafantasy.service.PlayerService;
import com.nbafantasy.util.StatusResult;
import org.springframework.util.StringUtils;
import play.Logger;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by bwang on 9/2/17.
 */
public class PlayerController extends Controller {

	@Inject
	PlayerService playerService;

	@BodyParser.Of(value = BodyParser.Json.class)
    public CompletionStage<Result> putPlayerName(String id) {
		JsonNode jsonNode = request().body().asJson();
		String name = jsonNode.get("name").asText();

		System.out.println(name);

		if(name == null || id == null || StringUtils.isEmpty(name))
			return CompletableFuture.completedFuture(badRequest("Either name or id is empty in your request!"));

		return playerService.createPlayerIDFromName(id, name).thenApply(itemOutcome -> {
			if(itemOutcome == null)
				return status(ErrorHandler.RESOURCE_ALREADY_EXISTS);
			Logger.info("Successfully added " + name + " into database");
			return created(StatusResult.CREATED);
		});
    }

    @BodyParser.Of(value = BodyParser.Json.class)
    public CompletionStage<Result> getIDFromPlayerName() {
		JsonNode jsonNode = request().body().asJson();
		String name = jsonNode.get("name").asText();
		return playerService.getPlayerIDFromName(name).thenApply(id -> {
			if(id == null) {
				Logger.error("Cannot find ID from " + name);
				return Results.notFound();
			}
			return ok(id);
		});
	}
}
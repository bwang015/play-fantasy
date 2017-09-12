package com.nbafantasy.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.nbafantasy.exception.ErrorHandler;
import com.nbafantasy.exception.ResourceAlreadyExistsException;
import com.nbafantasy.service.PlayerService;
import com.nbafantasy.util.StatusResult;
import play.Logger;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;
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
		return playerService.createPlayerIDFromName(id, name).thenApply(itemOutcome -> {
			Logger.info("Successfully added " + name + " into database");
			return created(StatusResult.CREATED);
		}).exceptionally(throwable -> {
			if(throwable.getCause() instanceof ResourceAlreadyExistsException) {
				return status(ErrorHandler.RESOURCE_ALREADY_EXISTS);
			}
			return Results.internalServerError();
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
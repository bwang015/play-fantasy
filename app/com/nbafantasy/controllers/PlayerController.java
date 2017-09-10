package com.nbafantasy.controllers;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.nbafantasy.service.PlayerService;
import com.nbafantasy.util.StatusResult;
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
		return playerService.createPlayerIDFromName(id, name).thenComposeAsync(code -> {
			if(code == 200) {
				Logger.info("Successfully added " + name + " into database");
				return CompletableFuture.completedFuture(created(StatusResult.CREATED));
			} else if (code == 208) {
				Logger.warn(name + " already exists inside the database");
				return CompletableFuture.completedFuture(status(208, StatusResult.ALREADY_EXISTS));
			}
			return CompletableFuture.completedFuture(Results.internalServerError());
		}).exceptionally(throwable -> {
			if (throwable.getCause() instanceof ResourceNotFoundException){
				Logger.error("Cannot perform action on a table that doesn't exist");
				return badRequest(throwable.getMessage());
			}
			Logger.error("Error in adding Player " + name + ": ", throwable);
			return Results.internalServerError();
		});
    }
}
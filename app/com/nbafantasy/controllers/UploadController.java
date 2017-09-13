package com.nbafantasy.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.nbafantasy.exception.ResourceNotFoundException;
import com.nbafantasy.models.PlayerInfo;
import com.nbafantasy.service.UploadService;
import play.Logger;
import play.mvc.*;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class UploadController extends Controller {

	@Inject
	UploadService uploadService;

	@BodyParser.Of(value = BodyParser.Json.class)
	public CompletionStage<Result> uploadPlayer(String id) {
		JsonNode jsonNode = request().body().asJson();
		return uploadService.parsePlayerGameInformation(jsonNode, id).handle((player, throwable) -> {
			if(throwable != null){
				if(throwable.getCause() instanceof ResourceNotFoundException) {
					Logger.error("Requested resource is not found!", throwable);
					return Results.notFound();
				}
				Logger.error("Something went wrong!", throwable);
				return Results.internalServerError();
			}

			return Results.ok();
		});
	}
}

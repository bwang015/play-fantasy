package com.nbafantasy.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;

public class UploadController extends Controller {

	@BodyParser.Of(value = BodyParser.Json.class)
	public CompletionStage<Result> uploadPlayer(String id) {
		JsonNode jsonNode = request().body().asJson();
		return null;
	}
}

package com.nbafantasy.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.nbafantasy.models.PlayerInfo;

import java.util.concurrent.CompletionStage;

public interface UploadService {
    CompletionStage<PlayerInfo> parsePlayerGameInformation(JsonNode jsonNode, String id);
}

package com.nbafantasy.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.nbafantasy.exception.ResourceNotFoundException;
import com.nbafantasy.models.GameStatistics;
import com.nbafantasy.models.PlayerInfo;

import javax.inject.Inject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletionStage;

/**
 * Created by bwang on 9/12/17.
 */
public class UploadServiceImpl implements UploadService {

    @Inject
    PlayerService playerService;

    @Override
    public CompletionStage<PlayerInfo> parsePlayerGameInformation(JsonNode jsonNode, String id) {
        return playerService.getPlayerNameFromID(id).thenApply(name -> {
            if(name == null)
                throw new ResourceNotFoundException();
            PlayerInfo player = new PlayerInfo(id, name);
            for(int i = 0; i < jsonNode.size(); i++) {
                GameStatistics gameStats = new GameStatistics();
                JsonNode gameinfo = jsonNode.get(i);

                player.setTeamName(gameinfo.get("Team").asText()); //Sets the player with the last team they played for
                if(player.setLastGameDate(convertDateFromString(gameinfo.get("Date").asText()))) {
                    //dynamodb.save gameStats
                }
            }
            return null;
        });
    }

    private Date convertDateFromString(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        try {
            startDate = df.parse(date);
        } catch (ParseException e) {
            //Swallow this exception
        }
        return startDate;
    }
}

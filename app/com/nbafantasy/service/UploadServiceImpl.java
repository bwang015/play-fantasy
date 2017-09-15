package com.nbafantasy.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.nbafantasy.database.DynamoDBService;
import com.nbafantasy.database.ModelDBService;
import com.nbafantasy.exception.ResourceNotFoundException;
import com.nbafantasy.models.GameStatistics;
import com.nbafantasy.models.PlayerInfo;
import org.apache.commons.lang3.StringUtils;
import play.libs.Json;

import javax.inject.Inject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;

/**
 * Created by bwang on 9/12/17.
 */
public class UploadServiceImpl implements UploadService {

    private PlayerInfoService playerInfoService;
    private PlayerService playerService;

    @Inject
    public UploadServiceImpl(DynamoDBService dynamoDBService, PlayerService playerService) {
        playerInfoService = new PlayerInfoService(dynamoDBService);
        this.playerService = playerService;
    }

    @Override
    public CompletionStage<PlayerInfo> parsePlayerGameInformation(JsonNode jsonNode, String id) {
        return playerService.getPlayerNameFromID(id).thenCompose(name -> {
            if (name == null)
                throw new ResourceNotFoundException();

            Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
            eav.put(":id", new AttributeValue().withS(id));

            DynamoDBQueryExpression<PlayerInfo> queryExpression = new DynamoDBQueryExpression<PlayerInfo>()
                    .withKeyConditionExpression("ID = :id")
                    .withExpressionAttributeValues(eav)
                    .withLimit(1);   // player ids are unique, so this will always return at most 1 item

            return playerInfoService.query(queryExpression).thenApply(resultList -> {
                PlayerInfo player = resultList.isEmpty() ? new PlayerInfo(id, name) : resultList.get(0);
                String team = player.getTeamName() == null ? "FA" : player.getTeamName(); //gets the latest team player played for
                for (int i = 0; i < jsonNode.size(); i++) {
                    JsonNode gameinfo = jsonNode.get(i);

                    GameStatistics gameStatistics = Json.fromJson(gameinfo, GameStatistics.class);
                    gameStatistics.setId(player.getId());
                    gameStatistics.setName(player.getId());

                    String lastTeam = gameinfo.get("Team").asText();

                    if (!StringUtils.equals(team, lastTeam)) {
                        team = gameinfo.get("Team").asText();
                        player.setTeamName(gameinfo.get("Team").asText()); //Sets the player with the last team they played for
                    }

                    if (player.setLastGameDate(convertDateFromString(gameinfo.get("Date").asText()))) {
                        //dynamodb.save gameStats
                    }
                }
                DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression()
                        .withExpectedEntry("ID", new ExpectedAttributeValue().withExists(false));

//                playerInfoService.save(player, saveExpression);
                return player;
            });
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

    private static class PlayerInfoService extends ModelDBService<PlayerInfo> {

        @Inject
        public PlayerInfoService(DynamoDBService dynamoDBService) {
            super(dynamoDBService);
        }

        @Override
        protected Class<PlayerInfo> getClassType() {
            return PlayerInfo.class;
        }
    }
}

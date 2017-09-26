package com.nbafantasy.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbafantasy.database.DynamoDBService;
import com.nbafantasy.database.ModelDBService;
import com.nbafantasy.exception.ResourceNotFoundException;
import com.nbafantasy.models.GameStatistics;
import com.nbafantasy.models.PlayerInfo;
import org.apache.commons.lang3.StringUtils;

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
    private GameStatisticsService gameService;
    private ObjectMapper mapper = new ObjectMapper();

    @Inject
    public UploadServiceImpl(DynamoDBService dynamoDBService, PlayerService playerService) {
        playerInfoService = new PlayerInfoService(dynamoDBService);
        gameService = new GameStatisticsService(dynamoDBService);
        this.playerService = playerService;
    }

    @Override
    public CompletionStage<PlayerInfo> parsePlayerGameInformation(JsonNode jsonNode, String id) {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":id", new AttributeValue().withS(id));

        DynamoDBQueryExpression<PlayerInfo> queryExpression = new DynamoDBQueryExpression<PlayerInfo>()
                .withKeyConditionExpression("ID = :id")
                .withExpressionAttributeValues(eav)
                .withLimit(1);   // player ids are unique, so this will always return at most 1 item

        return playerInfoService.query(queryExpression).thenCombine(playerService.getPlayerNameFromID(id), (resultList, name) -> {
            if(name == null)
                throw new ResourceNotFoundException();

            PlayerInfo player = resultList.isEmpty() ? new PlayerInfo(id, name) : resultList.get(0);
            String team = player.getTeamName() == null ? "FA" : player.getTeamName(); //gets the latest team player played for
            for (int i = 0; i < jsonNode.size(); i++) {
                JsonNode gameinfo = jsonNode.get(i);
                GameStatistics gameStatistics = new GameStatistics();
                try {
                    gameStatistics = mapper.treeToValue(gameinfo, GameStatistics.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                gameStatistics.setId(player.getId() + gameStatistics.getGameNumber());
                gameStatistics.setName(player.getName());
                gameStatistics.setDatePlayed(convertDateFromString(gameinfo.get("Date").asText()));
                gameStatistics.setIsHome(gameinfo.get("Date").asText() != null ? true : false);

                String lastTeam = gameinfo.get("Team").asText();

                if (!StringUtils.equals(team, lastTeam)) {
                    team = gameinfo.get("Team").asText();
                    player.setTeamName(gameinfo.get("Team").asText()); //Sets the player with the last team they played for
                }

                if (player.setLastGameDate(convertDateFromString(gameinfo.get("Date").asText()))) {
                    player.setGamesPlayed(gameStatistics.getGamesPlayed());
                    player.setTotalPts(player.getTotalPts() + gameStatistics.getPts());
                    player.setAvgPts(player.getTotalPts() / player.getGamesPlayed());
                    gameService.save(gameStatistics, null);
                }
            }

            playerInfoService.save(player, null);
            return player;
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

    private static class GameStatisticsService extends ModelDBService<GameStatistics> {

        @Inject
        public GameStatisticsService(DynamoDBService dynamoDBService) {
            super(dynamoDBService);
        }

        @Override
        protected Class<GameStatistics> getClassType() {
            return GameStatistics.class;
        }
    }
}

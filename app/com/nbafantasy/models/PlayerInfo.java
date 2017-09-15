package com.nbafantasy.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Date;

/**
 * Created by bwang on 9/12/17.
 */
@DynamoDBTable(tableName = "player_info_data")
public class PlayerInfo {

    private String id;
    private String name;
    private String teamName;
    private Date last_game;

    public PlayerInfo(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @DynamoDBHashKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Date getLastGameDate() {
        return last_game;
    }

    public boolean setLastGameDate(Date last_game) {
        if(last_game == null)
            return false;

        //Prevents duplication of games
        if(this.last_game == null || this.last_game.before(last_game)) {
            this.last_game = last_game;
            return true;
        }

        return false;
    }
}

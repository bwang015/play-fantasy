package com.nbafantasy.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
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
    private double avgPts;
    private double totalPts;
    private double gamesPlayed;

    public PlayerInfo(){
        gamesPlayed = 0;
    }

    public PlayerInfo(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @DynamoDBHashKey(attributeName = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "Team")
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @DynamoDBAttribute(attributeName = "Last Played Game")
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

    @DynamoDBAttribute(attributeName = "Avg PPG")
    public double getAvgPts() {
        return avgPts;
    }

    public void setAvgPts(double avgPts) {
        this.avgPts = avgPts;
    }

    @DynamoDBAttribute(attributeName = "Total Points")
    public double getTotalPts() {
        return totalPts;
    }

    public void setTotalPts(double totalPts) {
        this.totalPts = totalPts;
    }

    @DynamoDBAttribute(attributeName = "Games Played")
    public double getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(double gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }
}

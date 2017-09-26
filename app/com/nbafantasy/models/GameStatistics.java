package com.nbafantasy.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by bwang on 9/12/17.
 */
@JsonIgnoreProperties({"Date", "Away"})
@DynamoDBTable(tableName = "game_info_data")
public class GameStatistics {
    private String id;
    private String name;
    private int gameNumber;
    private Date datePlayed;
    private String team;
    private String opponent;
    private int gamesPlayed;
    private String minutes;
    private int fgm;
    private int fga;
    private int tpm;
    private int tpa;
    private int ftm;
    private int fta;
    private int offReb;
    private int defReb;
    private int assists;
    private int stls;
    private int blks;
    private int to;
    private int pf;
    private int pts;
    private int pm;
    private boolean isHome;

    @JsonProperty("Game Number")
    @DynamoDBAttribute(attributeName = "Game Number")
    public int getGameNumber() {
        return gameNumber;
    }

    public void setGameNumber(int gameNumber) {
        this.gameNumber = gameNumber;
    }

    @DynamoDBAttribute(attributeName = "isHomeGame")
    public Boolean isHome() {
        return isHome;
    }

    public void setIsHome(boolean isHome) {
        this.isHome = isHome;
    }

    @DynamoDBAttribute(attributeName = "Date Last Played")
    public Date getDatePlayed() {
        return datePlayed;
    }

    public void setDatePlayed(Date datePlayed) {
        this.datePlayed = datePlayed;
    }

    @JsonProperty("Team")
    @DynamoDBAttribute(attributeName = "Team")
    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    @JsonProperty("Opponent")
    @DynamoDBAttribute(attributeName = "Opponent")
    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    @JsonProperty("Games Played")
    @DynamoDBAttribute(attributeName = "Games Played")
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    @JsonProperty("Minutes Played")
    @DynamoDBAttribute(attributeName = "Minutes Played")
    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    @JsonProperty("Field Goal Made")
    @DynamoDBAttribute(attributeName = "Field Goal Made")
    public int getFgm() {
        return fgm;
    }

    public void setFgm(int fgm) {
        this.fgm = fgm;
    }

    @JsonProperty("Field Goal Attempted")
    @DynamoDBAttribute(attributeName = "Field Goal Attempted")
    public int getFga() {
        return fga;
    }

    public void setFga(int fga) {
        this.fga = fga;
    }

    @JsonProperty("Three Pointers Made")
    @DynamoDBAttribute(attributeName = "Three Pointers Made")
    public int getTpm() {
        return tpm;
    }

    public void setTpm(int tpm) {
        this.tpm = tpm;
    }

    @JsonProperty("Three Pointers Attempted")
    @DynamoDBAttribute(attributeName = "Three Pointers Attempted")
    public int getTpa() {
        return tpa;
    }

    public void setTpa(int tpa) {
        this.tpa = tpa;
    }

    @JsonProperty("Free Throws Made")
    @DynamoDBAttribute(attributeName = "Free Throws Made")
    public int getFtm() {
        return ftm;
    }

    public void setFtm(int ftm) {
        this.ftm = ftm;
    }

    @JsonProperty("Free Throws Attempted")
    @DynamoDBAttribute(attributeName = "Free Throws Attempted")
    public int getFta() {
        return fta;
    }

    public void setFta(int fta) {
        this.fta = fta;
    }

    @JsonProperty("Offensive Rebounds")
    @DynamoDBAttribute(attributeName = "Offensive Rebounds")
    public int getOffReb() {
        return offReb;
    }

    public void setOffReb(int offReb) {
        this.offReb = offReb;
    }

    @JsonProperty("Defensive Rebounds")
    @DynamoDBAttribute(attributeName = "Defensive Rebounds")
    public int getDefReb() {
        return defReb;
    }

    public void setDefReb(int defReb) {
        this.defReb = defReb;
    }

    @JsonProperty("Assists")
    @DynamoDBAttribute(attributeName = "Assists")
    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    @JsonProperty("Steals")
    @DynamoDBAttribute(attributeName = "Steals")
    public int getStls() {
        return stls;
    }

    public void setStls(int stls) {
        this.stls = stls;
    }

    @JsonProperty("Blocks")
    @DynamoDBAttribute(attributeName = "Blocks")
    public int getBlks() {
        return blks;
    }

    public void setBlks(int blks) {
        this.blks = blks;
    }

    @JsonProperty("Turnovers")
    @DynamoDBAttribute(attributeName = "Turnovers")
    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    @JsonProperty("Personal Fouls")
    @DynamoDBAttribute(attributeName = "Personal Fouls")
    public int getPf() {
        return pf;
    }

    public void setPf(int pf) {
        this.pf = pf;
    }

    @JsonProperty("Points")
    @DynamoDBAttribute(attributeName = "Points")
    public int getPts() {
        return pts;
    }

    public void setPts(int pts) {
        this.pts = pts;
    }

    @JsonProperty("Plus Minus")
    @DynamoDBAttribute(attributeName = "Plus Minus")
    public int getPm() {
        return pm;
    }

    public void setPm(int pm) {
        this.pm = pm;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "PlayerID")
    public String getName() {

        return name;
    }

    @DynamoDBHashKey(attributeName = "ID")
    public String getId() {

        return id;
    }
}

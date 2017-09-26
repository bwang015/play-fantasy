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
    private double totalRebs;
    private double avgRebs;
    private double totalAsts;
    private double avgAsts;
    private double totalStls;
    private double avgStls;
    private double totalBlks;
    private double avgBlks;
    private double totalFGM;
    private double totalFGA;
    private double FGP;
    private double totalFTM;
    private double totalFTA;
    private double FTP;
    private double totalTPM;
    private double totalTO;
    private double avgTO;

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

    @DynamoDBAttribute(attributeName = "Total Rebounds")
    public double getTotalRebs() {
        return totalRebs;
    }

    public void setTotalRebs(double totalRebs) {
        this.totalRebs = totalRebs;
    }

    @DynamoDBAttribute(attributeName = "Average Rebounds")
    public double getAvgRebs() {
        return avgRebs;
    }

    public void setAvgRebs(double avgRebs) {
        this.avgRebs = avgRebs;
    }

    @DynamoDBAttribute(attributeName = "Total Assists")
    public double getTotalAsts() {
        return totalAsts;
    }

    public void setTotalAsts(double totalAsts) {
        this.totalAsts = totalAsts;
    }

    @DynamoDBAttribute(attributeName = "Average Assists")
    public double getAvgAsts() {
        return avgAsts;
    }

    public void setAvgAsts(double avgAsts) {
        this.avgAsts = avgAsts;
    }

    @DynamoDBAttribute(attributeName = "Total Steals")
    public double getTotalStls() {
        return totalStls;
    }

    public void setTotalStls(double totalStls) {
        this.totalStls = totalStls;
    }

    @DynamoDBAttribute(attributeName = "Average Steals")
    public double getAvgStls() {
        return avgStls;
    }

    public void setAvgStls(double avgStls) {
        this.avgStls = avgStls;
    }

    @DynamoDBAttribute(attributeName = "Total Blocks")
    public double getTotalBlks() {
        return totalBlks;
    }

    public void setTotalBlks(double totalBlks) {
        this.totalBlks = totalBlks;
    }

    @DynamoDBAttribute(attributeName = "Average Blocks")
    public double getAvgBlks() {
        return avgBlks;
    }

    public void setAvgBlks(double avgBlks) {
        this.avgBlks = avgBlks;
    }

    @DynamoDBAttribute(attributeName = "Total Field Goals Made")
    public double getTotalFGM() {
        return totalFGM;
    }

    public void setTotalFGM(double totalFGM) {
        this.totalFGM = totalFGM;
    }

    @DynamoDBAttribute(attributeName = "Total Field Goals Attempted")
    public double getTotalFGA() {
        return totalFGA;
    }

    public void setTotalFGA(double totalFGA) {
        this.totalFGA = totalFGA;
    }

    @DynamoDBAttribute(attributeName = "Field Goal Percentage")
    public double getFGP() {
        return FGP;
    }

    public void setFGP(double FGP) {
        this.FGP = FGP;
    }

    @DynamoDBAttribute(attributeName = "Total Free Throws Made")
    public double getTotalFTM() {
        return totalFTM;
    }

    public void setTotalFTM(double totalFTM) {
        this.totalFTM = totalFTM;
    }

    @DynamoDBAttribute(attributeName = "Free Throw Percentage")
    public double getFTP() {
        return FTP;
    }

    public void setFTP(double FTP) {
        this.FTP = FTP;
    }

    @DynamoDBAttribute(attributeName = "Total Free Throws Attempted")
    public double getTotalFTA() {
        return totalFTA;
    }

    public void setTotalFTA(double totalFTA) {
        this.totalFTA = totalFTA;
    }

    @DynamoDBAttribute(attributeName = "Total Three Pointers Made")
    public double getTotalTPM() {
        return totalTPM;
    }

    public void setTotalTPM(double totalTPM) {
        this.totalTPM = totalTPM;
    }

    @DynamoDBAttribute(attributeName = "Total Turnovers")
    public double getTotalTO() {
        return totalTO;
    }

    public void setTotalTO(double totalTO) {
        this.totalTO = totalTO;
    }

    @DynamoDBAttribute(attributeName = "Average Turnovers")
    public double getAvgTO() {
        return avgTO;
    }

    public void setAvgTO(double avgTO) {
        this.avgTO = avgTO;
    }
}

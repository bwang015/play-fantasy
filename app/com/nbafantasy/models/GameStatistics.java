package com.nbafantasy.models;

/**
 * Created by bwang on 9/12/17.
 */
public class GameStatistics {
    private String id;
    private String name;

    public GameStatistics(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public String getId() {

        return id;
    }
}

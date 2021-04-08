package com.example.kursachgameshop2.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "games")
public class Game {
    @PrimaryKey(autoGenerate = true)
    private int uniqueID;
    private int id ;
    private int platform;
    private String gameTitle;
    private String overview;
    private String releaseDate;
    private String boxart;
    private String boxartMedium;
    private String price;


    public Game(int uniqueID,int id, int platform, String gameTitle, String overview, String releaseDate,String boxart,String boxartMedium,String price) {
        this.uniqueID = uniqueID;
        this.id = id;
        this.gameTitle = gameTitle;
        this.releaseDate = releaseDate;
        this.platform = platform;
        this.overview = overview;
        this.boxart = boxart;
        this.boxartMedium = boxartMedium;
        this.price = price;

    }

    @Ignore
    public Game(int id, int platform, String gameTitle, String overview, String releaseDate,String boxart,String boxartMedium,String price) {

        this.id = id;
        this.gameTitle = gameTitle;
        this.releaseDate = releaseDate;
        this.platform = platform;
        this.overview = overview;
        this.boxart = boxart;
        this.boxartMedium = boxartMedium;
        this.price = price;

    }

    public String getBoxart() {
        return boxart;
    }

    public void setBoxart(String boxart) {
        this.boxart = boxart;
    }

    public int getId() {
        return id;
    }

    public int getPlatform() {
        return platform;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setBoxartMedium(String boxartMedium) {
        this.boxartMedium = boxartMedium;
    }

    public String getBoxartMedium() {
        return boxartMedium;
    }


    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setUniqueID(int uniqueID) {
        this.uniqueID = uniqueID;
    }

    public int getUniqueID() {
        return uniqueID;
    }
}

package com.example.kursachgameshop2.data;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "favourite_games")
public class FavouriteGame extends Game{
    public FavouriteGame(int uniqueID,int id, int platform, String gameTitle, String overview, String releaseDate, String boxart, String boxartMedium,String price) {
        super(uniqueID, id, platform, gameTitle, overview, releaseDate, boxart, boxartMedium,price);
    }

    @Ignore
    public FavouriteGame(Game game){
        super(game.getUniqueID(), game.getId(),game.getPlatform(),game.getGameTitle(),game.getOverview(),game.getReleaseDate(),game.getBoxart(),game.getBoxartMedium(),game.getPrice());
    }
}

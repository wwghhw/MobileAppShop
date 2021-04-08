package com.example.kursachgameshop2.data;

import android.content.Context;
import android.widget.Toast;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Game.class,FavouriteGame.class},version = 9,exportSchema = false)
public abstract class GameDatabase extends RoomDatabase {

    private static final String DB_NAME = "games.db";
    private static GameDatabase gameDatabase;
    private static final Object LOCK = new Object();

    public static GameDatabase getInstance(Context context) {

        synchronized (LOCK) {
            if (gameDatabase == null) {
                gameDatabase = Room.databaseBuilder(context, GameDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();
            }
        }

        return gameDatabase;
    }

    public abstract GameDao gameDao();

}

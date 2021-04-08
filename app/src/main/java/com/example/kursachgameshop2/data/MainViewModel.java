package com.example.kursachgameshop2.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainViewModel extends AndroidViewModel {

    private static GameDatabase gameDatabase;
    private LiveData<List<Game>> games;
    private LiveData<List<FavouriteGame>> favouriteGames;

    public MainViewModel(@NonNull Application application) {
        super(application);
        gameDatabase = GameDatabase.getInstance(getApplication());
        games = gameDatabase.gameDao().getAllGames();
        favouriteGames = gameDatabase.gameDao().getAllFavouriteGames();
    }

    public Game getGameByID(int id) {

        try {
            return new getGameTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public FavouriteGame getFavouriteGameByID(int id) {

        try {
            return new getFavouriteGameTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<Game>> getGames() {
        return games;
    }

    public void deleteAllGame() {
        new deleteAllGamesTask().execute();
    }

    public void insertGame(Game game) {
        new InsertGameTask().execute(game);
    }

    public void deleteGame(Game game){
        new DeleteGameTask().execute(game);
    }

    public LiveData<List<FavouriteGame>> getFavouriteGames() {
        return favouriteGames;
    }

    public void insertFavouriteGame(FavouriteGame game) {
        new InsertFavouriteGameTask().execute(game);
    }

    public void deleteFavouriteGame(FavouriteGame game){

        new DeleteFavouriteGameTask().execute(game);
    }

    private static class DeleteFavouriteGameTask extends AsyncTask<FavouriteGame, Void, Void> {

        @Override
        protected Void doInBackground(FavouriteGame... games) {
            if (games != null && games.length > 0) {
                gameDatabase.gameDao().deleteFavouriteGame(games[0]);
            }
            return null;
        }
    }

    private static class InsertFavouriteGameTask extends AsyncTask<FavouriteGame, Void, Void> {

        @Override
        protected Void doInBackground(FavouriteGame... games) {
            if (games != null && games.length > 0) {
                gameDatabase.gameDao().insertFavouriteGame(games[0]);
            }
            return null;
        }
    }


    private static class DeleteGameTask extends AsyncTask<Game, Void, Void> {

        @Override
        protected Void doInBackground(Game... games) {
            if (games != null && games.length > 0) {
                gameDatabase.gameDao().deleteGame(games[0]);
            }
            return null;
        }
    }

    private static class InsertGameTask extends AsyncTask<Game, Void, Void> {

        @Override
        protected Void doInBackground(Game... games) {
            if (games != null && games.length > 0) {
                gameDatabase.gameDao().insertGame(games[0]);
            }
            return null;
        }
    }


    private static class deleteAllGamesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... integers) {

            gameDatabase.gameDao().deleteAllGames();
            return null;
        }
    }


    private static class getGameTask extends AsyncTask<Integer, Void, Game> {

        @Override
        protected Game doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return gameDatabase.gameDao().getGameByID(integers[0]);
            }

            return null;
        }
    }

    private static class getFavouriteGameTask extends AsyncTask<Integer, Void, FavouriteGame> {

        @Override
        protected FavouriteGame doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return gameDatabase.gameDao().getFavouriteGameByID(integers[0]);
            }

            return null;
        }
    }


}

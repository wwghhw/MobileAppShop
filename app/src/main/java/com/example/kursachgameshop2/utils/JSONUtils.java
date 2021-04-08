package com.example.kursachgameshop2.utils;

import com.example.kursachgameshop2.data.Game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtils {

    private int id ;
    private int platform;
    private String gameTitle;
    private String overview;
    private String releaseDate;

    private static final String KEY_DATA = "data";
    private static final String KEY_GAMES = "games";
    private static final String KEY_ID = "id";
    private static final String KEY_PLATFORM = "platform";
    private static final String KEY_TITLE = "game_title";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_RELEASE_DATE = "release_date";
    private static final String KEY_FILENAME = "filename";
    private static final String KEY_VIDEOS = "youtube";
    private static final String BASE_URL_ORIGINAL_IMAGE = "https://cdn.thegamesdb.net/images/original/";
    private static final String BASE_URL_MEDIUM_IMAGE = "https://cdn.thegamesdb.net/images/medium/";
    private static final String BASE_URL_VIDEOS = "https://www.youtube.com/watch?v=";



    public static ArrayList<Game> getGamesFromJSON(JSONObject jsonObject){

        ArrayList<Game> games = new ArrayList<>();
        if(jsonObject == null)
        {
            return games;
        }

        try {

            JSONObject jsonObjectImage = jsonObject.getJSONObject("include").getJSONObject("boxart").getJSONObject("data");
            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("games");
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject objectGame = jsonArray.getJSONObject(i);
                int id = objectGame.getInt(KEY_ID);
                String title = objectGame.getString(KEY_TITLE);
                String releaseDate = objectGame.getString(KEY_RELEASE_DATE);
                int platform = objectGame.getInt(KEY_PLATFORM);
                String overview = objectGame.getString(KEY_OVERVIEW);
                int year;
                try {
                     year = Integer.parseInt(objectGame.getString(KEY_RELEASE_DATE).substring(0,4));
                }catch (NumberFormatException e){
                    year = 2017;
                }


                String price = null;
                switch (year){

                    case 2013:
                        price = "600 RUB";
                        break;
                    case 2014:
                        price = "1200 RUB";
                        break;
                    case 2015:
                        price = "1800 RUB";
                        break;
                    case 2016:
                        price = "2400 RUB";
                        break;
                    case 2017:
                        price = "3000 RUB";
                        break;
                    case 2018:
                        price = "3400 RUB";
                        break;
                    case 2019:
                        price = "4000 RUB";
                        break;
                    case 2020:
                        price = "4500 RUB";
                        break;
                    default:
                        price = "5100 RUB";
                        break;

                }


                JSONObject objectImage = jsonObjectImage.getJSONArray(String.valueOf(id)).getJSONObject(0);
                String image = objectImage.getString(KEY_FILENAME);
                String urlImage = BASE_URL_ORIGINAL_IMAGE + image.replace("back","front");
                String urlImageMedium = BASE_URL_MEDIUM_IMAGE + image.replace("back","front");

                Game game = new Game(id,platform,title,overview,releaseDate,urlImage,urlImageMedium,price);
                games.add(game);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return games;
    }

}

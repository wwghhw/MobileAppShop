package com.example.kursachgameshop2.utils;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class NetworkUtils {

    private static final String BASE_URL = "https://api.thegamesdb.net/v1/Games/ByPlatformID";

    private static final String PARAMS_API_KEY = "apikey";
    private static final String PARAMS_PAGE = "page";
    private static final String PARAMS_ID = "id";
    private static final String PARAMS_FIELDS = "fields";
    private static final String PARAMS_INCLUDE = "include";

    private static final String API_KEY = "07d2c09ad9d6504a10fcf7bfe983845a1aa5363c1994e89f6aee937d08cf0928";
    private static final String API_ID_PS4 = "4919";
    private static final String API_ID_XONE = "4920";
    private static final String API_ID_NS = "4971";
    private static final String FIELDS_OVERVIEW = "overview";
    private static final String FIELDS_YOUTUBE = "youtube";
    private static final String FIELDS_RELEASE_DATE = "release_date";
    private static final String INCLUDE_BOXART = "boxart";

    public static final int XONE_GAMES = 0;
    public static final int PS4_GAMES = 1;
    public static final int NS_GAMES = 2;


    public static URL buildURL(int platform,int page) {


        URL result = null;
        String platformForGames = "";

        if(platform == XONE_GAMES)
        {
            platformForGames = API_ID_XONE;
        } else if(platform == PS4_GAMES) {
            platformForGames = API_ID_PS4;
        } else if(platform == NS_GAMES){
            platformForGames = API_ID_NS;
        }

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY,API_KEY)
                .appendQueryParameter(PARAMS_ID,platformForGames)
                .appendQueryParameter(PARAMS_FIELDS,FIELDS_RELEASE_DATE)
                .appendQueryParameter(PARAMS_FIELDS,FIELDS_OVERVIEW)
                .appendQueryParameter(PARAMS_INCLUDE,INCLUDE_BOXART)
                .appendQueryParameter(PARAMS_PAGE,Integer.toString(page))
                .build();

        try {
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return result;

    }


    public static JSONObject getJSONFromNetwork(int platform,int page){

        JSONObject result = null;
        URL url = buildURL(platform,page);
        try {
            result = new JSONLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static class JSONLoader extends AsyncTaskLoader<JSONObject>{

        private Bundle bundle;
        private OnStartLoadingListener onStartLoadingListener;


        public interface OnStartLoadingListener{
            void onStartLoading();
        }

        public void setOnStartLoadingListener(OnStartLoadingListener onStartLoadingListener) {
            this.onStartLoadingListener = onStartLoadingListener;
        }

        public JSONLoader(@NonNull Context context,Bundle bundle) {
            super(context);
            this.bundle = bundle;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            if(onStartLoadingListener != null){
                onStartLoadingListener.onStartLoading();
            }
            forceLoad();
        }

        @Nullable
        @Override
        public JSONObject loadInBackground() {
            if(bundle == null){
                return null;
            }
            String urlString = bundle.getString("url");
            URL url = null;
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            JSONObject result = null;

            if(url == null){

                return null;
            }

            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String line = bufferedReader.readLine();
                while (line != null)
                {
                    stringBuilder.append(line);
                    line = bufferedReader.readLine();
                }

                result = new JSONObject(stringBuilder.toString());

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection != null)
                {
                    connection.disconnect();
                }

            }

            return result;
        }
    }

    private static class JSONLoadTask extends AsyncTask<URL, Void, JSONObject>{

        @Override
        protected JSONObject doInBackground(URL... urls) {

            JSONObject result = null;

            if(urls == null || urls.length == 0){

                return result;
            }

            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) urls[0].openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String line = bufferedReader.readLine();
                while (line != null)
                {
                    stringBuilder.append(line);
                    line = bufferedReader.readLine();
                }

                result = new JSONObject(stringBuilder.toString());

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection != null)
                {
                    connection.disconnect();
                }

            }

            return result;

        }
    }



}

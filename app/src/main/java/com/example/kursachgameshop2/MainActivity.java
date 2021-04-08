package com.example.kursachgameshop2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kursachgameshop2.data.FavouriteGame;
import com.example.kursachgameshop2.data.Game;
import com.example.kursachgameshop2.data.MainViewModel;
import com.example.kursachgameshop2.utils.JSONUtils;
import com.example.kursachgameshop2.utils.NetworkUtils;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject> {


    private TextView textViewNSGames;
    private RecyclerView recyclerViewImages;
    private GameAdapter gameAdapter;
    private TextView textViewPS4Games;
    private TextView textViewXOneGames;
    private ProgressBar progressBarLoading;

    private MainViewModel viewModel;

    private static final int LOADER_ID = 1;
    private LoaderManager loaderManager;

    private static int page = 1;
    private static boolean isLoading = false;
    private static int typePlatform;


   public void showPopup(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.main_menu, popupMenu.getMenu());


        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.itemMain:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.favourite:
                        Intent intentFavourite = new Intent(getApplicationContext(), WishListActivity.class);
                        startActivity(intentFavourite);
                        break;

                }

                return true;
            }
        });

        popupMenu.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id){

            case R.id.itemMain:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.favourite:
                Intent intentFavourite = new Intent(this,WishListActivity.class);
                startActivity(intentFavourite);
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loaderManager = LoaderManager.getInstance(this);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        recyclerViewImages = findViewById(R.id.recyclerViewImages);
        textViewPS4Games = findViewById(R.id.textViewPs4);
        textViewXOneGames = findViewById(R.id.textViewXOne);
        textViewNSGames = findViewById(R.id.textViewNS);
        progressBarLoading = findViewById(R.id.progressBarLoading);
        setPlatform(NetworkUtils.PS4_GAMES);


       ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        recyclerViewImages.setLayoutManager(new GridLayoutManager(this, 2));
        gameAdapter = new GameAdapter();
        recyclerViewImages.setAdapter(gameAdapter);


        gameAdapter.setOnImageGameListener(new GameAdapter.OnImageGameListener() {
            @Override
            public void onImageClick(int position) {
                Game game = gameAdapter.getGames().get(position);
                Intent intent = new Intent(MainActivity.this, DetailGameActivity.class);
                intent.putExtra("id", game.getId());
                startActivity(intent);
            }
        });
        gameAdapter.setOnReachEndListener(new GameAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                if (!isLoading) {
                    DownloadData(typePlatform, page);
                }
            }
        });


        LiveData<List<Game>> gamesFromLiveData = viewModel.getGames();
        gamesFromLiveData.observe(this, new Observer<List<Game>>() {
            @Override
            public void onChanged(List<Game> games) {
                if(page == 1){
                    DownloadData(typePlatform,page);
                }
            }
        });

    }

    public void onClickSetPS4Games(View view) {

        int platform = 1;
        setPlatform(platform);
    }

    public void onClickSetXOneGames(View view) {

        int platform = 0;
        setPlatform(platform);

    }

    public void onClickSetNSGames(View view) {

        int platform = 2;
        setPlatform(platform);
    }

    private void setPlatform(int platform) {

        page = 1;

        if (platform == 0) {
            textViewXOneGames.setTextColor(getResources().getColor(R.color.green));
            textViewPS4Games.setTextColor(getResources().getColor(R.color.grey));
            textViewNSGames.setTextColor(getResources().getColor(R.color.grey));
            typePlatform = NetworkUtils.XONE_GAMES;



        } else if (platform == 1) {
            typePlatform = NetworkUtils.PS4_GAMES;
            textViewPS4Games.setTextColor(getResources().getColor(R.color.blue));
            textViewXOneGames.setTextColor(getResources().getColor(R.color.grey));
            textViewNSGames.setTextColor(getResources().getColor(R.color.grey));



        } else if (platform == 2) {
            typePlatform = NetworkUtils.NS_GAMES;
            textViewPS4Games.setTextColor(getResources().getColor(R.color.grey));
            textViewXOneGames.setTextColor(getResources().getColor(R.color.grey));
            textViewNSGames.setTextColor(getResources().getColor(R.color.purple_500));

        }

        DownloadData(typePlatform,page);

    }


    private void DownloadData(int typePlatform, int page) {

        URL url = NetworkUtils.buildURL(typePlatform, page);
        Bundle bundle = new Bundle();
        bundle.putString("url", url.toString());
        loaderManager.restartLoader(LOADER_ID, bundle, this);
    }

    @NonNull
    @Override
    public Loader<JSONObject> onCreateLoader(int id, @Nullable Bundle args) {
        NetworkUtils.JSONLoader jsonLoader = new NetworkUtils.JSONLoader(this, args);
        jsonLoader.setOnStartLoadingListener(new NetworkUtils.JSONLoader.OnStartLoadingListener() {
            @Override
            public void onStartLoading() {
                progressBarLoading.setVisibility(View.VISIBLE);
                isLoading = true;
            }
        });
        return jsonLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<JSONObject> loader, JSONObject data) {

        ArrayList<Game> games = JSONUtils.getGamesFromJSON(data);
        if (games != null && !games.isEmpty()) {
            if(page == 1) {
               // viewModel.deleteAllGame();
                gameAdapter.clear();
            }
            for (Game game : games) {

                viewModel.insertGame(game);

            }
            gameAdapter.addGames(games);
            page++;
        }
        isLoading = false;
        progressBarLoading.setVisibility(View.INVISIBLE);
        loaderManager.destroyLoader(LOADER_ID);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONObject> loader) {

    }
}
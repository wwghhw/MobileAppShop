package com.example.kursachgameshop2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.kursachgameshop2.data.FavouriteGame;
import com.example.kursachgameshop2.data.Game;
import com.example.kursachgameshop2.data.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class WishListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFavouriteGames;
    private GameAdapter gameAdapter;
    private MainViewModel mainViewModel;

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
        setContentView(R.layout.activity_wish_list);
        recyclerViewFavouriteGames = findViewById(R.id.recycleViewFavouriteGames);
        recyclerViewFavouriteGames.setLayoutManager(new GridLayoutManager(this,2));
        gameAdapter = new GameAdapter();
        recyclerViewFavouriteGames.setAdapter(gameAdapter);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        LiveData<List<FavouriteGame>> favouriteGames = mainViewModel.getFavouriteGames();
        favouriteGames.observe(this, new Observer<List<FavouriteGame>>() {
            @Override
            public void onChanged(List<FavouriteGame> favouriteGames) {
                List<Game> games = new ArrayList<>();
                if(favouriteGames != null){
                games.addAll(favouriteGames);
                gameAdapter.setGames(games);
                }

            }
        });

        gameAdapter.setOnImageGameListener(new GameAdapter.OnImageGameListener() {
            @Override
            public void onImageClick(int position) {
                Game game = gameAdapter.getGames().get(position);
                Intent intent = new Intent(WishListActivity.this,DetailGameActivity.class);
                intent.putExtra("id",game.getId());
                startActivity(intent);
            }
        });
    }
}
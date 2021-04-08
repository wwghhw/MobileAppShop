package com.example.kursachgameshop2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kursachgameshop2.data.FavouriteGame;
import com.example.kursachgameshop2.data.Game;
import com.example.kursachgameshop2.data.MainViewModel;
import com.squareup.picasso.Picasso;

public class DetailGameActivity extends AppCompatActivity {

    private TextView textViewGamePrice;
    private ImageView imageViewFavouriteGame;
    private ImageView imageViewGameImage;
    private TextView textViewGameTitle;
    private TextView textViewReleaseDate;
    private TextView textViewDescription;
    private int id;
    private MainViewModel mainViewModel;
    private Game game;
    private FavouriteGame favouriteGame;

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
        setContentView(R.layout.activity_detail_game);
        imageViewGameImage = findViewById(R.id.imageViewGameImage);
        textViewGameTitle = findViewById(R.id.textViewGameTitleInfo);
        textViewReleaseDate = findViewById(R.id.textViewRealeseDateInfo);
        textViewDescription = findViewById(R.id.textViewGameDescriptionInfo);
        imageViewFavouriteGame = findViewById(R.id.textViewFavouriteGame);
        textViewGamePrice = findViewById(R.id.textViewGamePrice);
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("id")){

            id = intent.getIntExtra("id",-1);
        }else {
            finish();
        }

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        game = mainViewModel.getGameByID(id);
        Picasso.get().load(game.getBoxartMedium()).into(imageViewGameImage);
        textViewGameTitle.setText(game.getGameTitle());
        textViewReleaseDate.setText(game.getReleaseDate());
        textViewDescription.setText(game.getOverview());
        int year = Integer.parseInt(game.getReleaseDate().substring(0,4));
        String gamePrice = setTextViewGamePrice(year);
        textViewGamePrice.setText(gamePrice);
        setFavourite();

    }

    public String setTextViewGamePrice(int year){
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

        return price;
    }

    public void onClickChangeFavourite(View view) {
         favouriteGame = mainViewModel.getFavouriteGameByID(id);
        if(favouriteGame == null){
            mainViewModel.insertFavouriteGame(new FavouriteGame(game));
            Toast.makeText(this,"Добавлено", Toast.LENGTH_SHORT).show();
        }else {
            mainViewModel.deleteFavouriteGame(favouriteGame);
            Toast.makeText(this, "Удалено", Toast.LENGTH_SHORT).show();
        }
        setFavourite();
    }

    private void setFavourite(){
        favouriteGame = mainViewModel.getFavouriteGameByID(id);
        if(favouriteGame == null){
            imageViewFavouriteGame.setImageResource(R.drawable.nowishlist);
        }else {
            imageViewFavouriteGame.setImageResource(R.drawable.paketwishlist);
        }
    }

    public void onClickBuyGame(View view) {

        //Toast.makeText(this, "Поздравляем с покупкой", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(DetailGameActivity.this,PaymentService.class);
        intent.putExtra("id",game.getId());
        startActivity(intent);

    }
}
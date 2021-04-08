package com.example.kursachgameshop2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.kursachgameshop2.data.Game;
import com.example.kursachgameshop2.data.MainViewModel;

public class PaymentService extends AppCompatActivity {


    private TextView textViewGameTitle;
    private TextView textViewGamePrice;
    private int id;
    private Game game;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_service);

        textViewGameTitle = findViewById(R.id.GameBuyInfoDescription);
        textViewGamePrice = findViewById(R.id.game_amount);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("id")){

            id = intent.getIntExtra("id",-1);
        }else {
            finish();
        }

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        game = mainViewModel.getGameByID(id);

        textViewGameTitle.setText(game.getGameTitle());
        textViewGamePrice.setText(game.getPrice());


    }
}
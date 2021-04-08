package com.example.kursachgameshop2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kursachgameshop2.data.Game;
import com.example.kursachgameshop2.data.MainViewModel;

public class PaymentService extends AppCompatActivity {


    private TextView textViewGameTitle;
    private TextView textViewGamePrice;
    private int id;
    private Game game;
    private MainViewModel mainViewModel;
    private EditText editTextCardNumber;
    private EditText editTextCVC;
    private EditText editTextDateCard;
    private EditText editTextTelephoneNumber;
    private EditText editTextMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_service);

        textViewGameTitle = findViewById(R.id.GameBuyInfoDescription);
        textViewGamePrice = findViewById(R.id.game_amount);
        editTextCardNumber = findViewById(R.id.editCardNumber);
        editTextCVC = findViewById(R.id.editCVC);
        editTextDateCard = findViewById(R.id.editDateCard);
        editTextTelephoneNumber = findViewById(R.id.editTelephoneNumber);
        editTextMail = findViewById(R.id.edit_email);


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

    public void buyButton(View view) {
        Toast.makeText(this, "Спасибо за покупку", Toast.LENGTH_SHORT).show();

    }
}
package com.example.kursachgameshop2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kursachgameshop2.data.Game;
import com.example.kursachgameshop2.data.MainViewModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String cardNumber = editTextCardNumber.getText().toString().trim();
        String CVC = editTextCVC.getText().toString().trim();
        String cardDate = editTextDateCard.getText().toString().trim();
        String telephoneNumber = editTextTelephoneNumber.getText().toString().trim();
        String mail = editTextMail.getText().toString().trim();


        if(validateCVC(CVC) && validateDateCard(cardDate) && validateCardNumber(cardNumber) && validateTelephoneNumber(telephoneNumber) && validateEmail(mail)){
            Intent intent = new Intent(this, ActivityPurchase.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Неверный ввод данных", Toast.LENGTH_SHORT).show();
        }

    }


    public boolean validateCardNumber(String cardNumber){
        boolean b = Pattern.matches("^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$",cardNumber);
        if(b){
            return true;
        }else {
            return false;
        }
    }

    public boolean validateTelephoneNumber(String telephoneNumber){

        boolean b = Pattern.matches("^\\+?[78][-\\(]?\\d{3}\\)?-?\\d{3}-?\\d{2}-?\\d{2}$",telephoneNumber);
        if(b){
            return true;
        }else {
            return false;
        }
    }

    public boolean validateCVC(String CVC){

        boolean b = Pattern.matches("^[0-9]{3}$",CVC);
        if(b){
            return true;
        }else {
            return false;
        }
    }


    public boolean validateDateCard(String dateCard){

        boolean b = Pattern.matches("^(?:0?[1-9]|1[0-2]) *\\/ *[1-9][0-9]$",dateCard);
        if(b){
            return true;
        }else {
            return false;
        }
    }



    public boolean validateEmail(String dateCard){

        boolean b = Pattern.matches("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$",dateCard);
        if(b){
            return true;
        }else {
            return false;
        }
    }


}
package com.example.kursachgameshop2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class ActivityPurchase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        Toast.makeText(this, "ПОЗДРАВЛЯЕМ С ПОКУПКОЙ", Toast.LENGTH_SHORT).show();
    }
}
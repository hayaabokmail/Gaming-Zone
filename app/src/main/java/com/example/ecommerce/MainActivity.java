package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler counter = new Handler();
        counter.postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(MainActivity.this, NavagationActivity.class));
                finish();        }
        }, 2500);

        }




}
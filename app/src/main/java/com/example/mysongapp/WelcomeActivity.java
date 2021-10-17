package com.example.mysongapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {
    Thread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        move();
        thread.start();

    }
    public void move()
    {
         thread = new Thread(new Runnable() {
             @Override
            public void run() {
                for(int i=0;i<2;i++)
                {
                    if (i == 1)
                    {
                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    SystemClock.sleep(1000);
                }
            }
        });

    }
}
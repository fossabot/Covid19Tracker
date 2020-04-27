package com.example.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;

public class Splashscreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 0000;//2100

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        final LottieAnimationView lottieAnimationView = findViewById(R.id.splash_lottie);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                lottieAnimationView.cancelAnimation();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }, SPLASH_TIME_OUT);


    }
}

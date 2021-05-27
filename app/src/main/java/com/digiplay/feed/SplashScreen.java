package com.digiplay.feed;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.digiplay.feed.ui.slideshow.SlideshowFragment;
import com.google.firebase.FirebaseApp;

public class SplashScreen extends AppCompatActivity {

    Button pay, my_account;

    ProgressBar splashProgress;
    // private static int SPLASH_TIME_OUT = 3000; //This is 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        FirebaseApp.initializeApp(this);

        pay = findViewById(R.id.pay);
        my_account = findViewById(R.id.my_account);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashScreen.this, PayActivity.class);
                startActivity(intent);
            }
        });

        my_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashScreen.this, HomeSwitcher.class);
                startActivity(intent);
            }
        });




        //This is additional feature, used to run a progress bar
        // splashProgress = findViewById(R.id.splashProgress);
        //  playProgress();

        //Code to start timer and take action after the timer ends
          /*  new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do any action here. Now we are moving to next page
                    Intent mySuperIntent = new Intent(SplashScreen.this,OTP.class);
                    startActivity(mySuperIntent);

                    //This 'finish()' is for exiting the app when back button pressed from Home page which is ActivityHome
                    finish();

                }
            }, SPLASH_TIME_OUT);
        }

        //Method to run progress bar for 5 seconds
        private void playProgress() {
            ObjectAnimator.ofInt(splashProgress, "progress", 100)
                    .setDuration(3000)
                    .start();
        }

           */
    }
}
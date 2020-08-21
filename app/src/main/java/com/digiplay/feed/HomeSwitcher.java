package com.digiplay.feed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class HomeSwitcher extends AppCompatActivity {

    CardView cardView, cardView2, cardView3, cardView4, cardView5, cardView6, cardView7, cardView8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_switcher);

        cardView = (CardView) findViewById(R.id.cardView);
        cardView2= (CardView) findViewById(R.id.cardView2);
        cardView3 = (CardView) findViewById(R.id.cardView3);
        cardView4= (CardView) findViewById(R.id.cardView4);
        cardView5 = (CardView) findViewById(R.id.cardView5);
        cardView6 = (CardView) findViewById(R.id.cardView6);
        cardView7 = (CardView) findViewById(R.id.cardView7);
        cardView8 = (CardView) findViewById(R.id.cardView8);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cardview on click activity profile
                Intent intent = new Intent(HomeSwitcher.this, OTP.class);
                startActivity(intent);

            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cardview on click activity signout

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeSwitcher.this, SplashScreen.class);
                startActivity(intent);
                finish();
                return;



            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cardview on click activity pay merchant
                Intent intent = new Intent(HomeSwitcher.this, PayActivity.class);
                startActivity(intent);

            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cardview on click activity get paid
                Intent intent = new Intent(HomeSwitcher.this, GetPaid.class);
                startActivity(intent);

            }
        });
        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cardview on click activity Pay Bill
                Intent intent = new Intent(HomeSwitcher.this, PayActivity.class);
                startActivity(intent);

            }
        });
        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cardview on click activity History
                Intent intent = new Intent(HomeSwitcher.this, OTP.class);
                startActivity(intent);

            }
        });
        cardView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cardview on click activity
                Intent intent = new Intent(HomeSwitcher.this, OTP.class);
                startActivity(intent);

            }
        });
        cardView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cardview on click activity
                Intent intent = new Intent(HomeSwitcher.this, OTP.class);
                startActivity(intent);

            }
        });
    }
}

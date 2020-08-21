package com.digiplay.feed.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.digiplay.feed.AddCardActivity;
import com.digiplay.feed.MerchantsActivity;
import com.digiplay.feed.PayActivity;
import com.digiplay.feed.R;
import com.digiplay.feed.ui.share.ShareFragment;
import com.digiplay.feed.ui.slideshow.SlideshowFragment;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    CardView otherMerchants, bills, airtime, transfer, shop;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

       // otherMerchants = (CardView) root.findViewById(R.id.otherMerchants);
        bills = (CardView) root.findViewById(R.id.bills);
        airtime = (CardView) root.findViewById(R.id.airtime);
        transfer = (CardView) root.findViewById(R.id.transfer);
        shop = (CardView) root.findViewById(R.id.shop);


      //  otherMerchants.setOnClickListener(new View.OnClickListener() {
      //      @Override
      //      public void onClick(View v) {
                //Cardview on click activity

      //          Intent DriverIntent = new Intent(getContext(), MerchantsActivity.class);
      //          startActivity(DriverIntent);

     //       }
    //    });

        bills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cardview on click activity

                Intent DriverIntent1 = new Intent(getContext(), MerchantsActivity.class);
                startActivity(DriverIntent1);

            }
        });

        airtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cardview on click activity

                Intent DriverIntent2 = new Intent(getContext(), ShareFragment.class);
                startActivity(DriverIntent2);

            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cardview on click activity

                Intent DriverIntent3 = new Intent(getContext(), PayActivity.class);
                startActivity(DriverIntent3);

            }
        });

        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cardview on click activity

                Intent DriverIntent5 = new Intent(getContext(), ShareFragment.class);
                startActivity(DriverIntent5);

            }
        });
        return root;
    }
}
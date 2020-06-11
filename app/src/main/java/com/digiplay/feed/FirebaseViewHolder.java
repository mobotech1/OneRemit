package com.digiplay.feed;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FirebaseViewHolder extends RecyclerView.ViewHolder {

    public TextView name, bank, skills, phonenumber, about, city, country, email;


    View mView;

    public FirebaseViewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name);
        bank = itemView.findViewById(R.id.bank);
        phonenumber = itemView.findViewById(R.id.phonenumber);
        mView = itemView;

    }
}
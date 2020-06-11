package com.digiplay.feed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class MerchantsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Model> arrayList;
    private FirebaseRecyclerOptions<Model> options;
    private FirebaseRecyclerAdapter<Model, FirebaseViewHolder> adapter;
    private DatabaseReference databaseReference;
    private EditText mSearchField;
    private ImageButton mSearchBtn;


    @Override
    protected void onStart() {
        super.onStart();

        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        adapter.stopListening();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchants);;
        mSearchBtn = (ImageButton) findViewById(R.id.search_btn);
        recyclerView = findViewById(R.id.recyclerview);
        mSearchField = (EditText) findViewById(R.id.search_field);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrayList = new ArrayList<Model>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Model").child("Merchants");
        databaseReference.keepSynced(true);
        options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(databaseReference, Model.class).build();


        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = mSearchField.getText().toString();
                firebaseUserSearch(searchText);

            }
        });


        adapter = new FirebaseRecyclerAdapter<Model, FirebaseViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseViewHolder firebaseViewHolder, int i, @NonNull Model model) {


                firebaseViewHolder.name.setText(model.getName());
                firebaseViewHolder.phonenumber.setText(model.getPhonenumber());

                firebaseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Cardview on click activity
                        Intent DriverIntent = new Intent(MerchantsActivity.this, PayActivity.class);
                        DriverIntent.putExtra("name",model.getName());
                        DriverIntent.putExtra("phonenumber",model.getPhonenumber());
                        startActivity(DriverIntent);

                    }
                });
            }

            @NonNull
            @Override
            public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new FirebaseViewHolder(LayoutInflater.from(MerchantsActivity.this).inflate(R.layout.merchant_row, parent, false));
            }
        };

        recyclerView.setAdapter(adapter);
    }

    private void firebaseUserSearch(String searchText) {

        Query firebaseSearchQuery = databaseReference.orderByChild("name").startAt(searchText).endAt(searchText+ "\uf8ff");

        options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(firebaseSearchQuery, Model.class).build();
        adapter = new FirebaseRecyclerAdapter<Model, FirebaseViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseViewHolder firebaseViewHolder, int i, @NonNull Model model) {

                firebaseViewHolder.name.setText(model.getName());
                firebaseViewHolder.phonenumber.setText(model.getPhonenumber());
                firebaseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Cardview on click activity
                        Intent DriverIntent = new Intent(MerchantsActivity.this, PayActivity.class);
                        DriverIntent.putExtra("name",model.getName());
                        DriverIntent.putExtra("phonenumber",model.getPhonenumber());
                        startActivity(DriverIntent);


                    }
                });
            }

            @NonNull
            @Override
            public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new FirebaseViewHolder(LayoutInflater.from(MerchantsActivity.this).inflate(R.layout.merchant_row, parent, false));
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onBackPressed() {
        Intent CustomerIntent = new Intent(MerchantsActivity.this, MainActivity.class);
        startActivity(CustomerIntent);
    }

}


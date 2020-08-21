package com.digiplay.feed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.digiplay.feed.ui.tools.ToolsViewModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class PayHistory extends AppCompatActivity {

    private ToolsViewModel toolsViewModel;
    private RecyclerView recyclerView;
    private ArrayList<Model> arrayList;
    private FirebaseRecyclerOptions<Model> options;
    private FirebaseRecyclerAdapter<Model, FirebaseViewHolder> adapter;
    private DatabaseReference databaseReference;
    private EditText mSearchField;
    private ImageButton mSearchBtn;

    @Override
    public void onStart() {
        super.onStart();

        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();

        adapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_history);
        mSearchBtn = (ImageButton) findViewById(R.id.search_btn);
        recyclerView = findViewById(R.id.recyclerview);
        mSearchField = (EditText) findViewById(R.id.search_field);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));

        arrayList = new ArrayList<Model>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Model").child("Payments");
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


                    }
                });
            }

            @NonNull
            @Override
            public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new FirebaseViewHolder(LayoutInflater.from(getApplication()).inflate(R.layout.row_history, parent, false));
            }
        };

        recyclerView.setAdapter(adapter);

    }

    private void firebaseUserSearch(String searchText) {

        Query firebaseSearchQuery = databaseReference.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");

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


                    }
                });
            }

            @NonNull
            @Override
            public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new FirebaseViewHolder(LayoutInflater.from(getApplication()).inflate(R.layout.row_history, parent, false));
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

}

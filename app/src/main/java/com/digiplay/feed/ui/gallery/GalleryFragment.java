package com.digiplay.feed.ui.gallery;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digiplay.feed.AddCardActivity;
import com.digiplay.feed.DatabaseHelper;
import com.digiplay.feed.FirebaseViewHolder;
import com.digiplay.feed.Model;
import com.digiplay.feed.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Model> arrayList;
    private FirebaseRecyclerOptions<Model> options;
    private FirebaseRecyclerAdapter<Model, FirebaseViewHolder> adapter;
    private DatabaseReference databaseReference;
    DatabaseHelper myDb;
    private GalleryViewModel galleryViewModel;
    CardView addCard, removeCard;
    TextView bankname;
    TextView username;
    TextView accountnumber;
    CardView viewAll;

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


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        myDb = new DatabaseHelper(getContext());
        addCard = (CardView) root.findViewById(R.id.addCard);
        removeCard = (CardView) root.findViewById(R.id.removeCard);
        bankname = root.findViewById(R.id.bankname);
        username =  root.findViewById(R.id.username) ;
        accountnumber =  root.findViewById(R.id.accountnumber) ;



        recyclerView = root.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        arrayList = new ArrayList<Model>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Model").child("Cards");
        databaseReference.keepSynced(true);
        options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(databaseReference, Model.class).build();

        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cardview on click activity

                Intent DriverIntent = new Intent(getContext(), AddCardActivity.class);
                startActivity(DriverIntent);

            }
        });

        removeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cardview on click activity

                Intent DriverIntent = new Intent(getContext(), AddCardActivity.class);
                startActivity(DriverIntent);

            }
        });

        Cursor dat = myDb.getCards();
        if(dat.getCount() == 0) {
            // show message
            //showMessage("Error","Nothing found");

            Toast.makeText(getContext(),"Error ,No Cards found",Toast.LENGTH_LONG);

        }

        //   StringBuffer buffer = new StringBuffer();
        while (dat.moveToNext()) {

            String username1 = (dat.getString(1));
            String bankname1 = (dat.getString(2));
            String accountnumber1 = (dat.getString(3));

            username.setText(username1);
            accountnumber.setText(accountnumber1);
            bankname.setText(bankname1);
        }



        // Show all data in database
        adapter = new FirebaseRecyclerAdapter<Model, FirebaseViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseViewHolder firebaseViewHolder, int i, @NonNull Model model) {

                firebaseViewHolder.name.setText(model.getName());
                firebaseViewHolder.phonenumber.setText(model.getPhonenumber());
                firebaseViewHolder.bank.setText(model.getBank());
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
                return new FirebaseViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.row, parent, false));
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();




        return root;
    }



}
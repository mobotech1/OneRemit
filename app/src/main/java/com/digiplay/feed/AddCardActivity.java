package com.digiplay.feed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCardActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText name, phonenumber,bank;
    SQLiteDatabase sqLiteDatabase;
    Button add;
    TextView bankname;
    TextView username;
    TextView accountnumber;
    CardView viewAll;
    Button buttonpost, upload ,buttondelete,buttonupdate;
    private ImageView upload_pic;
    ProgressDialog progressDialog ;
//    FirebaseDatabase database;
//    DatabaseReference ref;
    Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        myDb = new DatabaseHelper(this);
        viewAll = findViewById(R.id.card1) ;
        name = findViewById(R.id.name) ;
        add = findViewById(R.id.add);
        phonenumber = findViewById(R.id.phonenumber) ;
        bank = findViewById(R.id.bank) ;

        bankname = findViewById(R.id.bankname);
        username = findViewById(R.id.username) ;
        accountnumber = findViewById(R.id.accountnumber) ;

        progressDialog = new ProgressDialog(AddCardActivity.this);
        buttondelete = (Button) findViewById(R.id.buttondelete);
//
//        database = FirebaseDatabase.getInstance();
//        ref = database.getReference("Model");
        model=new Model();

        getCards();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cardview on click activity
//                String key = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                AddData(key);
            }
        });

        buttondelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cardview on click activity
//                String key = FirebaseAuth.getInstance().getCurrentUser().getUid();
//               DeleteData(key);
            }
        });

        // viewAll();


    }

    private void DeleteData(String key) {

        if (TextUtils.isEmpty(phonenumber.getText().toString()))
        {
            phonenumber.setError("Please enter phone number to verify the acoount.");
            return;
//        ref.child("Cards").child(key).removeValue();
        }
//;
        Toast.makeText(this, "Profile Deleted, Sorry to see you go :(", Toast.LENGTH_LONG).show();


    }


    public  void AddData(String key) {

        model.setName(name.getText().toString());
        model.setPhonenumber(phonenumber.getText().toString());
        model.setBank(bank.getText().toString());

                                       if (TextUtils.isEmpty(name.getText().toString()))
                                       {
                                           name.setError("Field cannot be Empty");
                                           return;
                                       }
                                       else if (TextUtils.isEmpty(phonenumber.getText().toString()))
                                       {
                                           phonenumber.setError("Field cannot be Empty");
                                           return;
                                       }
                                       else if (TextUtils.isEmpty(bank.getText().toString()))
                                       {
                                           bank.setError("Field cannot be Empty");
                                           return;
                                       }else{


                                       boolean isInserted = myDb.insertData(name.getText().toString(),
                                               bank.getText().toString(),
                                               phonenumber.getText().toString() );


                                         //  ref.child("Cards").child(key).setValue(model);

                                           if(isInserted == true)

                                           Toast.makeText(AddCardActivity.this,"Card Added.",Toast.LENGTH_LONG).show();
                                       }

}


    public void getCards() {
        viewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor dat = myDb.getCards();
                        if(dat.getCount() == 0) {
                            // show message
                            //showMessage("Error","Nothing found");

                            Toast.makeText(AddCardActivity.this,"Error ,No Cards found",Toast.LENGTH_LONG);
                            return;
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



                        // Show all data
                        // showMessage("Data",buffer.toString());
                    }
                }
        );
    }

}

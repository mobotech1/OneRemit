package com.digiplay.feed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.digiplay.feed.ui.slideshow.SlideshowViewModel;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

import static java.security.AccessController.getContext;

public class PayActivity extends AppCompatActivity {


    private SlideshowViewModel slideshowViewModel;
    SurfaceView surfaceView;
    TextView scanResult, name1,phonenumber1;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    public String number;
    public String string;
    final int RequestCameraPermissionID = 1001;
    //firebase auth object
    private FirebaseAuth firebaseAuth;
    private EditText amount;
    Button buttonload;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(surfaceView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        name1= (TextView) findViewById(R.id.name);
        phonenumber1= (TextView) findViewById(R.id.phonenumber);
        buttonload = (Button) findViewById(R.id.buttonload);

        String name = getIntent().getStringExtra("name");
        String phonenumber = getIntent().getStringExtra("phonenumber");
        name1.setText(name);
        phonenumber1.setText(phonenumber);

        buttonload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(amount.getText().toString()))
                {
                    amount.setError("Please enter amount your are paying.");
                    return;
                } else {
                    makePayment();
                }
            }
        });


        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if (firebaseAuth.getCurrentUser() == null) {
            //closing this activity
            //  finish();
            //starting login activity
            startActivity(new Intent(PayActivity.this, OTP.class));
        } else {
            surfaceView = (SurfaceView) findViewById(R.id.cameraPreview);
            scanResult = (TextView) findViewById(R.id.scanResult);
            amount = (EditText) findViewById(R.id.amount);
            barcodeDetector = new BarcodeDetector.Builder(getApplicationContext())
                    .setBarcodeFormats(Barcode.QR_CODE)
                    .build();

            cameraSource = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                    .setRequestedPreviewSize(640, 480).build();


            surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {

                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        //Request permission
                        ActivityCompat.requestPermissions(PayActivity.this,
                                new String[]{Manifest.permission.CAMERA}, RequestCameraPermissionID);
                        return;
                    }
                    try {
                        cameraSource.start(surfaceView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    cameraSource.stop();

                }
            });

            barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<Barcode> detections) {
                    final SparseArray<Barcode> qrcodes = detections.getDetectedItems();


                   if (qrcodes.size() != 0) {

                        scanResult.post(new Runnable() {
                            @Override
                            public void run() {
                                //Create vibrate
                                Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                                vibrator.vibrate(100);
                                cameraSource.stop();
                                Barcode searchText = qrcodes.valueAt(0);

                                scanResult.setText(qrcodes.valueAt(0).displayValue);





                            }
                        });
                    }

                }
            });
        }


    }

    private void makePayment() {


        String searchtext = (String) scanResult.getText();


        string = scanResult.getText().toString().trim();

        String amountcharge = amount.getText().toString();
        Integer  commission = Integer.parseInt(amountcharge);

        double payment = commission + (commission*0.05);

        number = "tel:" + string + payment + Uri.encode("#");

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
            }
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(number)));


        } catch (ActivityNotFoundException activityException) {
            Log.e("failed", "QR Code Error");


        }



    }
}

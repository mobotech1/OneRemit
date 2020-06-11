package com.digiplay.feed.ui.slideshow;

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
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.digiplay.feed.MainActivity;
import com.digiplay.feed.OTP;
import com.digiplay.feed.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    SurfaceView surfaceView;
    TextView scanResult;
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
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
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


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        amount = (EditText) root.findViewById(R.id.amount);
        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();
        buttonload = (Button) root.findViewById(R.id.buttonload);

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


        //if the user is not logged in
        //that means current user will return null
        if (firebaseAuth.getCurrentUser() == null) {
            //closing this activity
          //  finish();
            //starting login activity
            startActivity(new Intent(getContext(), OTP.class));
        } else {
            surfaceView = (SurfaceView) root.findViewById(R.id.cameraPreview);
            scanResult = (TextView) root.findViewById(R.id.scanResult);

            barcodeDetector = new BarcodeDetector.Builder(getContext())
                    .setBarcodeFormats(Barcode.QR_CODE)
                    .build();

            cameraSource = new CameraSource.Builder(getContext(), barcodeDetector)
                    .setRequestedPreviewSize(640, 480).build();


            surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {

                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {
                    if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        //Request permission
                        ActivityCompat.requestPermissions(getActivity(),
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

                        if(amount.length()==0){

                            amount.setError("Please enter amount.");
                        }
                        scanResult.post(new Runnable() {
                            @Override
                            public void run() {
                                //Create vibrate
                                Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                                vibrator.vibrate(1000);
                                cameraSource.stop();

                                scanResult.setText(qrcodes.valueAt(0).displayValue);


                            }
                        });
                    }

                }
            });
        }
        return root;

    }

    private void makePayment() {

        string = scanResult.getText().toString().trim();

        String amountcharge = amount.getText().toString();
        Integer  commission = Integer.parseInt(amountcharge);

        double payment = commission + (commission*0.05);

        number = "tel:" + string + payment + Uri.encode("#");

        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(number)));


    }


}
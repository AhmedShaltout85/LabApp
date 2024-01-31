package com.ao8r.labapp.views;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


import com.ao8r.labapp.R;

import com.ao8r.labapp.customiz.CustomLoader;
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.customiz.ReadWriteFileFromInternalMem;
import com.ao8r.labapp.customiz.ScheduleRepeatTaskTimer;
import com.ao8r.labapp.models.CaptureAct;
import com.ao8r.labapp.R.id;
import com.ao8r.labapp.R.layout;
import com.ao8r.labapp.models.ReferenceData;
import com.ao8r.labapp.repository.GetLabAndSectorNameForBrokenPoint;
import com.ao8r.labapp.repository.GetMaxSampleCodeForBrokenPoint;

import com.ao8r.labapp.repository.InsertLocationsToTrackBreakTB;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;




public class MenuScreen extends AppCompatActivity implements OnClickListener, LocationListener {
    private LocationManager locationManager;
    private Button addNewSample, newSrcSample, addBrokenPoint, addOnSiteTests;
    private String locationLong, locationLat;
    private IntentIntegrator qrScanner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_menu_screen);

//        call get LabCode modify 20/09/2022

//        CustomAlertDialogWithEditText.getMissedLabCode(getApplicationContext(),"فضلا أدخل labCode");

//        //TODO: GET all LAB data By LAB_CODE
//        if(!ReadWriteFileFromInternalMem.getLabCodeFromFile().isEmpty()) {
//            GetSampleLabNameFromLabSamples.getSampleLabNameFromLabSamples(getApplicationContext());
//        }else {
//            CustomToast.customToast(getApplicationContext(),"عفو خطأ فى الحصول على LAB_CODE");
//        }


//      Call find Location fun
        getLocation();
        addNewSample = findViewById(R.id.addNewSampleButton);
        newSrcSample = findViewById(R.id.newSrcButton);
        addBrokenPoint = findViewById(id.addBrokenPointButton);
        addOnSiteTests = findViewById(id.addOnSiteTestButton);


//        set action in Buttons
        addNewSample.setOnClickListener(this);
        newSrcSample.setOnClickListener(this);
        addBrokenPoint.setOnClickListener(this);
        addOnSiteTests.setOnClickListener(this);

        //ask for location permission  ** run time permission
        if (ContextCompat.checkSelfPermission(MenuScreen.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MenuScreen.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        getLocation();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        getLocation();
//    }

    //    Go Back to LoginPage
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MenuScreen.this, LoginScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }


    @SuppressLint("NonConstantResourceId")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case id.newSrcButton:
                if (ReferenceData.userControl != 1) {
                    CustomToast.customToast(getApplicationContext(), "عفو ليس لديك صلاحية لهذا المحتوى");

                } else {
//                        //                    modify in 11/5/2022
//                    scanData();
                    if (locationLat != null && locationLong != null) {
                        Intent intent = new Intent(view.getContext(), SourceSample.class);
                        intent.putExtra("locationLatVal", locationLat);
                        intent.putExtra("locationLongVal", locationLong);
                        startActivity(intent);
                    } else {
//            Call Loader
                        CustomLoader.customLoader(this, ".. جارى تحديد الموقع ..");
                    }
                }
                break;
            case id.addNewSampleButton:
                if (ReferenceData.userControl == 2) {
                    scanData();

                } else {
                    CustomToast.customToast(getApplicationContext(), "عفو ليس لديك صلاحية لهذا المحتوى");

                }
                break;

//-----> 27/7/2022 add broken point

            case id.addBrokenPointButton:
//                if (ReferenceData.userControl == 1) {
//                    CustomToast.customToast(getApplicationContext(), "عفو ليس لديك صلاحية لهذا المحتوى");
//                }else{
//                TODO:change labCode from EDITTEXT to get it from External File in memory
//                    if(ReferenceData.labCode == null || ReferenceData.labCode.isEmpty()){
//                        TODO:labCode
                try {

                    ReferenceData.labCode = ReadWriteFileFromInternalMem.getLabCodeFromFile();
//                        CustomToast.customToast(getApplicationContext(), "Failed to get LabCode");
//                        CustomAlertDialogWithEditText.getMissedLabCode(view.getContext(),"Get LabCode");
//                        CustomToast.customToast(getApplicationContext(), ReferenceData.labCode);


//                    }else{
                    GetMaxSampleCodeForBrokenPoint.getMaxSampleCode(getApplicationContext());
                    GetLabAndSectorNameForBrokenPoint.getLabAndSectorNameForBrokenPoint(getApplicationContext());
                } catch (Exception e) {
                    e.getStackTrace();
                }

                if (locationLat != null && locationLong != null) {
                    System.out.println("BREAK-LOCATION-STARTED");
                    ReferenceData.sampleBrokenX = locationLat;
                    ReferenceData.sampleBrokenY = locationLong;
                    System.out.println(locationLat);
                    System.out.println(locationLong);
                    InsertLocationsToTrackBreakTB.insertLocationsToTrackBreakTB(getApplicationContext());

                    //loop for track location
//                    Timer timer = new Timer();
//
//                    timer.schedule(new TimerTask() {
//                        public void run() {
//                            //Called in a secondary thread.
//                            //GUI update not allowed.
//                            getLocation();
//                            ReferenceData.sampleBrokenX = locationLat;
//                            ReferenceData.sampleBrokenY = locationLong;
//                            InsertLocationsToTrackBreakTB.insertLocationsToTrackBreakTB(getApplicationContext());
//                        }
//                    }, 0,60000);

                    Handler handler = new Handler();
                    // Define the code block to be executed

                    Runnable runnableCode = new Runnable() {
                        @Override
                        public void run() {
                            // Do something here on the main thread
                            Log.d("Handlers", "Called on main thread");
                            // Repeat this the same runnable code block again another 2 seconds
                            // 'this' is referencing the Runnable object
                            getLocation();
                            ReferenceData.sampleBrokenX = locationLat;
                            ReferenceData.sampleBrokenY = locationLong;
                            InsertLocationsToTrackBreakTB.insertLocationsToTrackBreakTB(getApplicationContext());
                            handler.postDelayed(this, 60000);

                            //
                            Intent intent = new Intent(getApplicationContext(),TrackBreakLocInMapsActivity.class);
                            startActivity(intent);

                        }
                    };
                    // Start the initial runnable task by posting through the handler
                    handler.post(runnableCode);
//                    try {
//
//                        ScheduleRepeatTaskTimer.repeatTask(locationLat, locationLong, view.getContext());
//                    }catch (Exception exception){
//                        CustomToast.customToast(getApplicationContext(),"فضلا تحقق من أتصالك بالأنترنت");
//                    }

                    //TODO: // disable for test periodic locations track
////                            Intent intent = new Intent(view.getContext(), TrackBreakLocInMapsActivity.class);
//                            Intent intent = new Intent(this, TrackBreakLocInMapsActivity.class);
////                            intent.putExtra("locationLatVal", locationLat);
////                            intent.putExtra("locationLongVal", locationLong);
//                            startActivity(intent);

                    //call Track repeat task timer class

                    System.out.println("BREAK-LOCATION-TESTED");
                } else {
//            Call Loader
                    CustomLoader.customLoader(this, ".. جارى تحديد الموقع ..");
                }
//                    }

//                }
                break;

            case id.addOnSiteTestButton:


                if (ReferenceData.userControl == 3) {
                    scanData();

                } else {
                    CustomToast.customToast(getApplicationContext(), "عفو ليس لديك صلاحية لهذا المحتوى");

                }


                break;
        }


    }

    public void scanData() {
        qrScanner = new IntentIntegrator(this);
        qrScanner.setCaptureActivity(CaptureAct.class);
        qrScanner.setOrientationLocked(false);
        qrScanner.setBarcodeImageEnabled(true);
        qrScanner.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        qrScanner.setBeepEnabled(true);
        qrScanner.setCameraId(0);
        qrScanner.setPrompt("فضلا قم بمسح الكود");

        qrScanner.initiateScan();
    }


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode,
                    resultCode, data);
            if (result != null) {
                if (result.getContents() == null) // can not read barcode
                {
                    CustomToast.customToast(getApplicationContext(), "عفو الكود غير صالح");
                } else //find barcode
                {
                    if (locationLat != null && locationLong != null) {

                        if (ReferenceData.userControl == 2) {
//                            newSrcSample.setEnabled(false);
//                            newSrcSample.setClickable(false);
//                            newSrcSample.setText(" ");
//                            newSrcSample.setBackgroundColor(R.color.colorAccent);
//                            newSrcSample.setVisibility(View.INVISIBLE);


                            Intent intent = new Intent(this, AddNewSample.class);
                            intent.putExtra("locationLatVal", locationLat);
                            intent.putExtra("locationLongVal", locationLong);
                            intent.putExtra("scanningCode", result.getContents());

                            startActivity(intent);
                        }
//                        //                    modify in 29/8/2022

//                        //ON SITE TEST BUTTON
                        else if (ReferenceData.userControl == 3) {
                            Intent intent = new Intent(this, OnSiteTests.class);
                            intent.putExtra("locationLatVal", locationLat);
                            intent.putExtra("locationLongVal", locationLong);
                            intent.putExtra("scanningCode", result.getContents());
                            // debug Values
                            System.out.println("MenuScreenLatitude:" + result.getContents().substring(5, 12));
                            System.out.println("MenuScreenLongitude:" + result.getContents().substring(16, 23));

                            startActivity(intent);
                        }


                    } else {

//            Call Loader
                        CustomLoader.customLoader(this, ".. جارى تحديد الموقع ..");

                    }
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }


    @SuppressLint("MissingPermission")
    public void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext()
                    .getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    0, 0, MenuScreen.this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    0, 0, MenuScreen.this);
            if (locationManager == null) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        locationManager = (LocationManager) getApplicationContext()
                                .getSystemService(LOCATION_SERVICE);
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                0, 0, MenuScreen.this);
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                                0, 0, MenuScreen.this);

                    }
                }, 3000);


            } else {
            }
        } catch (Exception e) {
            System.out.println("000000000000000000000000000" + e);


            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        locationLat = String.valueOf(location.getLatitude());
        locationLong = String.valueOf(location.getLongitude());

        try {
            String locationLongLat = locationLat + locationLong;
//            System.out.println(locationLongLat);
        } catch (Exception e) {
            System.out.println("111111111111111" + e);
            e.printStackTrace();
        }

    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        CustomToast.customToast(getApplicationContext(), "فضلا قم بتفعيل GPS");


    }


    public void backToLogin(View view) {
        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
    }
}
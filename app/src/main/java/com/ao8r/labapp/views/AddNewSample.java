package com.ao8r.labapp.views;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.ao8r.labapp.R;
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.customiz.GetLocation;
import com.ao8r.labapp.customiz.ReadWriteFileFromInternalMem;
import com.ao8r.labapp.models.ReferenceData;
import com.ao8r.labapp.repository.GetOriginXandY;
import com.ao8r.labapp.repository.InsertDailyDataUsingJdbc;
import com.ao8r.labapp.services.GFG;
import com.ao8r.labapp.services.InternetConnection;

import java.util.Locale;


public class AddNewSample extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    // declare Variables

    String qrScanner, locationLong, locationLat, note;
    TextView qrScanning, locationLatitude, locationLongitude;
    Spinner qrSpinnerNotes;
    Button saveRefData, displayTrueLocationInMap, displayInGIS, displayGMapPathInMap;
    //    MenuScreen menuScreen = new MenuScreen();
//    Location location = new Location(LocationManager.GPS_PROVIDER);
    GetLocation getLocation = new GetLocation(this);


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_sample);
        getLocation.startLocationUpdates();

//        create instance from Declared vars
//        TextView

        qrScanning = findViewById(R.id.qrScannerTextView);
        locationLatitude = findViewById(R.id.locationLatitude);
        locationLongitude = findViewById(R.id.locationLongitude);
//        spinner
        qrSpinnerNotes = findViewById(R.id.qrSpinnerNotes);
//        Button
        saveRefData = findViewById(R.id.saveReferenceData);
        displayTrueLocationInMap = findViewById(R.id.displayTrueLocation);
        displayInGIS = findViewById(R.id.displayInGIS);
        displayGMapPathInMap = findViewById(R.id.displayGMapPath);

        // Get data from qr Scanner and Location
        qrScanner = getIntent().getStringExtra("scanningCode");
        locationLat = getIntent().getStringExtra("locationLatVal");
        locationLong = getIntent().getStringExtra("locationLongVal");
        // debug Values
        System.out.println("qrLatitude is :" + locationLat);
        System.out.println("qrLongitude is :" + locationLong);
//        System.out.println("locationLat.substring(0,7)" + locationLat.substring(0,7));
//        System.out.println("locationLong.substring(0,7)" + locationLong.substring(0,7));
        System.out.println("Sample note Value is :" + note);

        //Assign data to Text field
        qrScanning.setText(qrScanner);
        locationLatitude.setText(locationLat);
        locationLongitude.setText(locationLong);

        //Assign location data to static vars
        ReferenceData.xLatitude = locationLatitude.getText().toString();
        ReferenceData.yLongitude = locationLongitude.getText().toString();


//      Assign Spinner

        ArrayAdapter<String> spinnerNote = new ArrayAdapter<String>(AddNewSample.this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.notes));
        spinnerNote.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qrSpinnerNotes.setAdapter(spinnerNote);
        qrSpinnerNotes.setOnItemSelectedListener(this);

        // Set Action to Button
        saveRefData.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {
                    ReferenceData.notes = note;

//          get store data
                    ReferenceData.xLatitude = locationLatitude.getText().toString();
                    ReferenceData.yLongitude = locationLongitude.getText().toString();
                    ReferenceData.sampleQrCode = qrScanning.getText().toString();
                    String[] splitQrLt = ReferenceData.sampleQrCode.split("-");
//     debug output
                    for (int i = 0; i < 3; i++) {
                        System.out.println(splitQrLt[i]);
                    }
                    ReferenceData.labCodeLt = splitQrLt[0];
                    ReferenceData.sampleCodeLt = splitQrLt[1];
                    String sampleXComp = splitQrLt[2];
                    String sampleYComp = splitQrLt[3];


//                Check internet Connectivity
                    if (InternetConnection.checkConnection(getApplicationContext())) {
                        // Its Available...
                        CustomToast.customToast(getApplicationContext(), "متصل بالانترنت");
                    } else {
                        // Not Available...

                        CustomToast.customToast(getApplicationContext(), "فضلا تحقق من الاتصال بالانترنت");


                    }
                    if (ReferenceData.labCode == null || ReferenceData.labCode.isEmpty()) {
//                    CustomAlertDialogWithEditText.getMissedLabCode(getApplicationContext(),"أدخل رقمالمعمل= LabCode");
//
//                    TODO: change labCode


                        ReferenceData.labCode = ReadWriteFileFromInternalMem.getLabCodeFromFile();
//                    CustomToast.customToast(getApplicationContext(),"Read LabCode From File"+ ReferenceData.labCode);
                        System.out.println("Read LabCode From File");
                    } else {

                        ReferenceData.labCode = ReferenceData.labCodeLt;
                    }


//                Calling sendData Method(6/3/2022)

//                SendParamToApi.sendRefDataToApi(refData, getApplicationContext());
                    // compare Between Origin SampleX and y Daily Sample X and y
                    // 1. get the origin x, y
                    //
                    try {

                        GetOriginXandY.getSampleXAndY(getApplicationContext(),
                                ReferenceData.labCodeLt, ReferenceData.sampleCodeLt);
                    } catch (Exception e) {
                        e.getStackTrace();
                        CustomToast.customToast(getApplicationContext(), "عفو هذه النقطه غير مدرجة فى هذا المسار");

                    }

                    // 2. use Haversine formula to get distance between 2 Point
                    //
                    double distance = GFG.distance(
                            Double.parseDouble(ReferenceData.originX),
                            Double.parseDouble(ReferenceData.originY),
                            Double.parseDouble(ReferenceData.xLatitude),
                            Double.parseDouble(ReferenceData.yLongitude)
                    );

                    // debug Result
                    ReferenceData.disBetweenTowPoints = (distance);
//                        * 0.001);
                    System.out.println(
                            ReferenceData.disBetweenTowPoints
                    );

                    //
                    //

//                CustomToast.customToast(getApplicationContext(), "Distance Between S and D =  " + String.valueOf(ReferenceData.disBetweenTowPoints).substring(0,6) + "  meter");

                    try {

                        InsertDailyDataUsingJdbc.insertDailySample(getApplicationContext());
                    } catch (Exception e) {
                        e.getStackTrace();
                        CustomToast.customToast(getApplicationContext(), "عفو هذه النقطه غير مدرجة فى هذا المسار");

                    }
//
                    // update the location (from GetLocation class)
//                    getLocation.startLocationUpdates();

                    /////
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(AddNewSample.this);
                    builder1.setTitle("Saved Successful");

                    if (ReferenceData.disBetweenTowPoints < 50.0) {

                        builder1.setMessage("البعد عن النقطة المرجعية بالمتر" + "  " + String.valueOf(ReferenceData.disBetweenTowPoints).substring(0, 3));
                    } else {
                        builder1.setMessage("عفو عينه خارج النطاق المسموح به");
                    }

                    builder1.setCancelable(false);
                    if (sampleXComp == locationLat && sampleYComp == locationLong) {
                        builder1.setPositiveButton(
                                "قراءة جديدة",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(getApplicationContext(), MenuScreen.class);
                                        startActivity(intent);
                                    }
                                });
                    } else {
                        builder1.setPositiveButton(
                                "قراءة جديدة",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(getApplicationContext(), MenuScreen.class);
                                        startActivity(intent);
                                    }
                                });
                        builder1.setNegativeButton(
                                "تحديث الموقع",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
//
                                        getLocation.setLocationChangeListener(new GetLocation.OnLocationChangeListener() {
                                            @Override
                                            public void onLocationChange(Location location) {
                                                // Handle the updated location
                                                // You can access the latitude and longitude using location.getLatitude() and location.getLongitude()
                                                System.out.println("updated latitude >>" + location.getLatitude() + "\n updated long >>" + location.getLongitude());
                                                ReferenceData.xLatitude = String.valueOf(location.getLatitude());
                                                ReferenceData.yLongitude = String.valueOf(location.getLongitude());
                                                System.out.println("updated latitude  after insert >>" + ReferenceData.xLatitude + "\n updated long after insert >>" + ReferenceData.yLongitude);

                                            }
                                        });
//
                                        try {

                                            GetOriginXandY.getSampleXAndY(getApplicationContext(),
                                                    ReferenceData.labCodeLt, ReferenceData.sampleCodeLt);
                                        } catch (Exception e) {
                                            e.getStackTrace();
                                            CustomToast.customToast(getApplicationContext(), "عفو هذه النقطه غير مدرجة فى هذا المسار");

                                        }

                                        // 2. use Haversine formula to get distance between 2 Point
                                        //
                                        double distance = GFG.distance(
                                                Double.parseDouble(ReferenceData.originX),
                                                Double.parseDouble(ReferenceData.originY),
                                                Double.parseDouble(ReferenceData.xLatitude),
                                                Double.parseDouble(ReferenceData.yLongitude)
                                        );

                                        // debug Result
                                        ReferenceData.disBetweenTowPoints = (distance);
//                        * 0.001);
                                        System.out.println(
                                                ReferenceData.disBetweenTowPoints
                                        );

                                        //
                                        //update x, y location axis
                                        try {

                                            InsertDailyDataUsingJdbc.insertDailySample(getApplicationContext());
                                        } catch (Exception e) {
                                            e.getStackTrace();
                                            CustomToast.customToast(getApplicationContext(), "عفو هذه النقطه غير مدرجة فى هذا المسار");

                                        }
                                        //

//                                        getLocation.stopLocationUpdates();

//                                    CustomToast.customToast(getApplicationContext(), "Distance Between S and D =  " + String.valueOf(ReferenceData.disBetweenTowPoints).substring(0,6) + "  meter");
                                        //TODO: 22-10-2023 (update location)
//                                    menuScreen.onLocationChanged(location);
//
//                                    System.out.println(location.getLatitude() +"-------"+ location.getLongitude());
//                                    menuScreen.getLocation();
//                                    System.out.println(location.getLatitude() +"-------"+ location.getLongitude());
                                        //                                   System.out.println(locationLat +"-------"+ locationLong);

                                        Intent intent = new Intent(getApplicationContext(), MenuScreen.class);
                                        startActivity(intent);

                                    }
                                });
                    }
                    AlertDialog alert11 = builder1.create();

                    alert11.show();

                    ////

                } catch (Exception e) {
                    e.getStackTrace();
                    e.getMessage();
                    CustomToast.customToast(getApplicationContext(), "عفو هذه النقطه غير مدرجة فى هذا المسار");

                }

            }

        });

//        TODO:// NAV TO ORIGIN LOCATION IN MAP
//        nav to original x, y in map

        displayTrueLocationInMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
//                    ReferenceData.sampleQrCode = qrScanning.getText().toString();
//                    String[] splitQrLt = ReferenceData.sampleQrCode.split("-");
////     debug output
//                    for (int i = 0; i < 3; i++) {
//                        System.out.println(splitQrLt[i]);
//                    }
//                    ReferenceData.labCodeLt = splitQrLt[0];
//                    ReferenceData.sampleCodeLt = splitQrLt[1];
//                    String sampleXComp = splitQrLt[2];
//                    String sampleYComp = splitQrLt[3];
                    varsParams();
                    if (Double.parseDouble(ReferenceData.originX) == 1.1111111 ||
                            Double.parseDouble(ReferenceData.originY) == 1.1111111 ||
                            Double.parseDouble(ReferenceData.originX) == 1.111111044883728 ||
                            Double.parseDouble(ReferenceData.originY) == 1.111111044883728) {
                        CustomToast.customToast(getApplicationContext(), "عفو لم يتم إدراج إحداثيات هذه النقطة على المسار");

                    } else {
                        GetOriginXandY.getSampleXAndY(getApplicationContext(),
                                ReferenceData.labCodeLt, ReferenceData.sampleCodeLt);
                        String uri = String.format(
                                Locale.ENGLISH,
                                "http://maps.google.com/maps?daddr=%f,%f (%s)",
                                Double.parseDouble(ReferenceData.originX),
                                Double.parseDouble(ReferenceData.originY),
                                ReferenceData.originSampleName
//                            31.197056, 29.909812, "مكتب البرمجيات كوم الدكه"
                        );


                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        intent.setPackage("com.google.android.apps.maps");
                        startActivity(intent);
                        CustomToast.customToast(getApplicationContext(), "جارى التحويل لل Google map");
                    }

                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        });

//        display in GIS
//        TODO://display IN GIS
        displayInGIS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
//                    ReferenceData.sampleQrCode = qrScanning.getText().toString();
//                    String[] splitQrLt = ReferenceData.sampleQrCode.split("-");
////     debug output
//                    for (int i = 0; i < 3; i++) {
//                        System.out.println(splitQrLt[i]);
//                    }
//                    ReferenceData.labCodeLt = splitQrLt[0];
//                    ReferenceData.sampleCodeLt = splitQrLt[1];
//                    String sampleXComp = splitQrLt[2];
//                    String sampleYComp = splitQrLt[3];

                    varsParams();
//                Check internet Connectivity
                    if (InternetConnection.checkConnection(getApplicationContext())) {
                        // Its Available...
                        CustomToast.customToast(getApplicationContext(), "متصل بالانترنت");
                    } else {
                        // Not Available...

                        CustomToast.customToast(getApplicationContext(), "فضلا تحقق من الاتصال بالانترنت");


                    }
                    if (ReferenceData.labCode == null || ReferenceData.labCode.isEmpty()) {
//                    CustomAlertDialogWithEditText.getMissedLabCode(getApplicationContext(),"أدخل رقمالمعمل= LabCode");
//
//                    TODO: change labCode


                        ReferenceData.labCode = ReadWriteFileFromInternalMem.getLabCodeFromFile();
//                    CustomToast.customToast(getApplicationContext(),"Read LabCode From File"+ ReferenceData.labCode);
                        System.out.println("Read LabCode From File");
                    } else {

                        ReferenceData.labCode = ReferenceData.labCodeLt;
                    }

                    //
                    System.out.println("1- debug labCode : >>" + ReferenceData.labCodeLt);
                    System.out.println("1- debug labCode : >>" + ReferenceData.labCode);
                    //

                    GetOriginXandY.getSampleXAndY(getApplicationContext(),
                            ReferenceData.labCodeLt, ReferenceData.sampleCodeLt);

                    //TODO: check if location point is added in map or not
                    if (Double.parseDouble(ReferenceData.originX) == 1.1111111 ||
                            Double.parseDouble(ReferenceData.originY) == 1.1111111 ||
                            Double.parseDouble(ReferenceData.originX) == 1.111111044883728 ||
                            Double.parseDouble(ReferenceData.originY) == 1.111111044883728) {

                        CustomToast.customToast(getApplicationContext(), "عفو لم يتم إدراج هذة النقطة على المسار");
                    } else {
                        System.out.println("Enter to  display-GIS-Map");
                        CustomToast.customToast(getApplicationContext(), "جارى تحميل المسار على GIS");

                    }
                    // 2- debug sampleName
                    System.out.println("2- debug sampleName  : >>" + ReferenceData.originBatchName);

                    if (ReferenceData.labCode.equals(ReferenceData.labCodeLt)
                            && ReferenceData.originBatchName.contains("المطار السريع")) {
//                    call GIS LINK
                        System.out.println("Enter to  GIS 1st Link");
                        CustomToast.customToast(getApplicationContext(), "جارى تحميل المسار");
                        intentToGISLink("https://196.219.231.4:6443/arcgis/rest/services/Nozha/%D8%A7%D9%84%D9%85%D8%B7%D8%A7%D8%B1_%D8%A7%D9%84%D8%B3%D8%B1%D9%8A%D8%B9/MapServer?f=jsapi");

                        System.out.println(">3->تم التحويل الى GIS");
                    } else if (ReferenceData.labCode.equals(ReferenceData.labCodeLt)
                            && ReferenceData.originBatchName.contains("منطقة عزبة حجازى وش النصر وجرين بلازا")) {
//                        call GIS LINK
                        System.out.println("Enter to  GIS 2nd Link");
                        CustomToast.customToast(getApplicationContext(), "جارى تحميل المسار");
                        intentToGISLink("https://196.219.231.4:6443/arcgis/rest/services/Nozha/%D8%B9%D8%B2%D8%A8%D8%A9_%D8%AD%D8%AC%D8%A7%D8%B2%D9%8A/MapServer?f=jsapi");
                    } else {
                        CustomToast.customToast(getApplicationContext(), "عفو البيانات خاطئة");
                        Log.d("TAG--GIS LINK error", "onClick: GIS LINK error");
                        System.out.println("GIS LINK error");
                    }
                } catch (Exception e) {
                    e.getStackTrace();
                }

            }
        });


//        display in displayGMapPathInMap
//        TODO://display IN displayGMapPathInMap//
        displayGMapPathInMap.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                try {
//                Check internet Connectivity

                    if (InternetConnection.checkConnection(getApplicationContext())) {
                        // Its Available...
                        CustomToast.customToast(getApplicationContext(), "متصل بالانترنت");
                    } else {
                        // Not Available...
                        CustomToast.customToast(getApplicationContext(), "فضلا تحقق من الاتصال بالانترنت");

                    }
                    varsParams();
                    GetOriginXandY.getSampleXAndY(getApplicationContext(),
                            ReferenceData.labCodeLt, ReferenceData.sampleCodeLt);
                    System.out.println("addNewSample--current x: " + ReferenceData.xLatitude);
                    System.out.println("addNewSample--current y: " + ReferenceData.yLongitude);
                    System.out.println("location-from-intent " + locationLat + "," + locationLong);
                    System.out.println("addNewSample--origin x: " + ReferenceData.originX);
                    System.out.println("addNewSample--origin y: " + ReferenceData.originY);
                    System.out.println("addNewSample--sampleName: " + ReferenceData.originSampleName);
                    System.out.println("addNewSample--bachName: " + ReferenceData.originBatchName);

//                    if(     Double.parseDouble(ReferenceData.originX) == 1.1111111 ||
//                            Double.parseDouble(ReferenceData.originY) == 1.1111111 ||
//                            Double.parseDouble(ReferenceData.originX) == 1.111111044883728 ||
//                            Double.parseDouble(ReferenceData.originY) == 1.111111044883728){
//
//                        CustomToast.customToast(getApplicationContext(),"عفو لم يتم إدراج هذة النقطة على المسار");
//                    }else {
//                        System.out.println("Enter to  displayGMapPathInMap");
//                        CustomToast.customToast(getApplicationContext(), "جارى تحميل المسار على الخريطة");

                    Intent intent = new Intent(getApplicationContext(), MapsActivity2.class);
                    startActivity(intent);
//                }

                } catch (Exception exception) {
                    exception.getStackTrace();
                    CustomToast.customToast(getApplicationContext(), "عفو البيانات خاطئة");
                    Log.d("TAG--displayGMapPathInMap", "onClick: displayGMapPathInMap");
                    System.out.println("displayGMapPathInMap");
                }
            }
        });


    }

    // Get Spinner Data
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        note = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


//    INTENT for GIS LINK

    private void intentToGISLink(String uriLink) {

        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uriLink));
        try {
            AddNewSample.this.startActivity(webIntent);
        } catch (ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void varsParams() {

        ReferenceData.sampleQrCode = qrScanning.getText().toString();
        String[] splitQrLt = ReferenceData.sampleQrCode.split("-");
//     debug output
        for (int i = 0; i < 3; i++) {
            System.out.println(splitQrLt[i]);
        }
        ReferenceData.labCodeLt = splitQrLt[0];
        ReferenceData.sampleCodeLt = splitQrLt[1];
        String sampleXComp = splitQrLt[2];
        String sampleYComp = splitQrLt[3];
    }
}
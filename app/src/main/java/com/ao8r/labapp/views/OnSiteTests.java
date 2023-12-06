package com.ao8r.labapp.views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ao8r.labapp.R;
import com.ao8r.labapp.customiz.CustomTimeAndDate;
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.models.OnSiteTestModel;
import com.ao8r.labapp.models.ReferenceData;
import com.ao8r.labapp.repository.GetAllLabDataColumnNames;
import com.ao8r.labapp.repository.GetAllOnSiteListForDropdown;
import com.ao8r.labapp.repository.GetAllValuesOfOnSiteTests;
import com.ao8r.labapp.repository.GetMaxGeneralSerialFromLabsDailyTests;
import com.ao8r.labapp.repository.GetTestValueOneYearAgo;
import com.ao8r.labapp.repository.InsertIntoLabsDailyTests;
import com.ao8r.labapp.services.InternetConnection;

import java.util.ArrayList;

public class OnSiteTests extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // declare variables
    Spinner onSiteTestSpinnerTestType, testSampleKindSpinner;
    Button saveOnSiteTestData;
    TextView onSiteTestQrScanning;
    EditText onSiteTestValue;
    ArrayList<OnSiteTestModel> onSiteTestArrayList;
    String min, max;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_site_tests);

//        create instance from Declared vars
//        TextView
        onSiteTestQrScanning = findViewById(R.id.onSiteQrScannerTextView);
//        Spinner Declared
        onSiteTestSpinnerTestType = findViewById(R.id.onSiteTestSpinnerTestType);
//        testSampleKindSpinner = findViewById(R.id.onSiteTestSpinnerTestSampleKind);

//        EditText Declared
        onSiteTestValue = findViewById(R.id.onSiteTestValueEditText);
//        Button Declared
        saveOnSiteTestData = findViewById(R.id.saveOnSiteTestData);

//        Get data from qr Scanner and Location
        String tempOnSiteQrScanner = getIntent().getStringExtra("scanningCode");
        final String tempOnSiteTestLocationLat = getIntent().getStringExtra("locationLatVal");
        final String tempOnSiteTestLocationLong = getIntent().getStringExtra("locationLongVal");
//        debug Values
        System.out.println("qrLatitude is :" + tempOnSiteTestLocationLat);
        System.out.println("qrLongitude is :" + tempOnSiteTestLocationLong);


//        Assign data to Text field
        onSiteTestQrScanning.setText(tempOnSiteQrScanner);

        String tempOnSiteTestQrScanning = onSiteTestQrScanning.getText().toString().trim();
//
        String[] splitQrOnSiteTest = tempOnSiteTestQrScanning.split("-");
//               debug output
        for (int i = 0; i < 3; i++) {
            System.out.println(splitQrOnSiteTest[i]);
        }
        ReferenceData.onSiteTestSampleLabCode = splitQrOnSiteTest[0];
        System.out.println(ReferenceData.onSiteTestSampleLabCode);
        ReferenceData.onSiteTestSampleCode = splitQrOnSiteTest[1];
        System.out.println(ReferenceData.onSiteTestSampleCode);

        //

        //                    Get All Data from On Site Test Table
        try {
            GetAllLabDataColumnNames.getAllLabSamplesDataColumnNames(getApplicationContext());
            System.out.println("step1: get >>" + ReferenceData.onSiteTestSampleKind);
        } catch (Exception e) {
            e.getStackTrace();
        }

//        Assign Spinner
        try {

            onSiteTestArrayList = GetAllOnSiteListForDropdown.getAllOnSiteListForDropdown(getApplicationContext());
        } catch (Exception e) {
            CustomToast.customToast(getApplicationContext(), "الانترنت غير مستقر, حاول مره أخرى");
        }

        ArrayAdapter<OnSiteTestModel> spinnerOnSiteTestType = new
                ArrayAdapter<OnSiteTestModel>(OnSiteTests.this,
                android.R.layout.simple_spinner_item,
                onSiteTestArrayList);
        spinnerOnSiteTestType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        onSiteTestSpinnerTestType.setAdapter(spinnerOnSiteTestType);
        onSiteTestSpinnerTestType.setOnItemSelectedListener(this);
//         Spinner test Sample kind

//        ArrayAdapter<String> spinnerTestSampleKind = new
//                ArrayAdapter<String>(OnSiteTests.this,
//                android.R.layout.simple_spinner_item,
//                getResources().getStringArray(R.array.testSampleKind));
//        spinnerTestSampleKind.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        testSampleKindSpinner.setAdapter(spinnerTestSampleKind);
//        testSampleKindSpinner.setOnItemSelectedListener(this);


//        Set Action to Button

        saveOnSiteTestData.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {

//          get store data
                    ReferenceData.onTestXLoc = tempOnSiteTestLocationLat;
                    ReferenceData.onTestYLoc = tempOnSiteTestLocationLong;

//          get data from EditText

                    ReferenceData.testValue = onSiteTestValue.getText().toString();
//                Check if testValue in Range


//          get data from QR and Split it


//                String tempOnSiteTestQrScanning = onSiteTestQrScanning.getText().toString().trim();
////
//                String[] splitQrOnSiteTest = tempOnSiteTestQrScanning.split("-");
////               debug output
//                for (int i = 0; i < 3; i++) {
//                    System.out.println(splitQrOnSiteTest[i]);
//                }
//                ReferenceData.onSiteTestSampleLabCode = splitQrOnSiteTest[0];
//                System.out.println(ReferenceData.onSiteTestSampleLabCode);
//                ReferenceData.onSiteTestSampleCode = splitQrOnSiteTest[1];
//                System.out.println(ReferenceData.onSiteTestSampleCode);


//                Check internet Connectivity
                    if (InternetConnection.checkConnection(getApplicationContext())) {
                        // Its Available...
                        CustomToast.customToast(getApplicationContext(), "متصل بالانترنت");
                    } else {
                        // Not Available...

                        CustomToast.customToast(getApplicationContext(), "فضلا تحقق من الاتصال بالانترنت");


                    }
                    if (ReferenceData.testValue.isEmpty()) {
                        CustomToast.customToast(getApplicationContext(), "فضلا أدخل بيانات الحقول الفارغة");
                    } else {
////                    Get All Data from On Site Test Table
//                    GetAllLabDataColumnNames.getAllLabSamplesDataColumnNames(getApplicationContext());
//                    System.out.println("step1: get >>"+ReferenceData.onSiteTestSampleKind);
                        try {

                            GetAllValuesOfOnSiteTests.getAllValuesOfOnSiteTests(getApplicationContext());
                            System.out.println("step2: get >> List of Available onsite Tests for this type of sample kind");
                        } catch (Exception e) {
                            e.getStackTrace();
                        }

//
//                        GetSampleLabNameFromLabSamples.getSampleLabNameFromLabSamples(getApplicationContext());

                        //                        Insert Data inside ON--SITE--TEST

                        //TODO: changing in 3-05-2023

//                        InsertIntoOnSiteTest.insertIntoOnSiteTest(getApplicationContext());

                        try {

                            GetMaxGeneralSerialFromLabsDailyTests.getMaxGeneralSerial(getApplicationContext());
                            System.out.println("step3");
                        } catch (Exception e) {
                            e.getStackTrace();
                        }

                        //                      Assign value to testValueOutOfRange

                        if (Double.parseDouble(ReferenceData.testValue) >= (ReferenceData.testMinValue = Double.parseDouble(min))
//                    && Integer.parseInt(ReferenceData.testValue) == (ReferenceData.testMaxValue)
                                && Double.parseDouble(ReferenceData.testValue) <= (ReferenceData.testMaxValue = Double.parseDouble(max))) {
                            ReferenceData.testValueOutOfRange = "0";
                            ReferenceData.outOfRangeText = " ";
                        } else {
                            ReferenceData.testValueOutOfRange = "1";
                            ReferenceData.outOfRangeText = "==>";
                        }

                        //                    Assign value to testHour

                        if (CustomTimeAndDate.convertCurrentTimeToString() > 12) {
                            ReferenceData.onSiteTestHour = (CustomTimeAndDate.convertCurrentTimeToString() - 12) + "م";
                        } else {
                            ReferenceData.onSiteTestHour = CustomTimeAndDate.convertCurrentTimeToString() + "ص";

                        }


                        //                    get test Value one year ago

                        try {
                            GetTestValueOneYearAgo.getTestValueOneYearAgo(getApplicationContext());

                        } catch (Exception e) {
                            e.getStackTrace();
                        }

                        //                        Insert Data inside DAILY--LABS_TESTS
                        System.out.println("step4: >> get test Value one year ago");

                        try {

                            InsertIntoLabsDailyTests.insertIntoLabsDailyTests(getApplicationContext());
                            System.out.println("step5: >> InsertIntoLabsDailyTests");
                        } catch (Exception e) {
                            e.getStackTrace();
                        }

//                    nav to menu Screen
                        Intent intent = new Intent(getApplicationContext(), MenuScreen.class);
                        startActivity(intent);


                    }
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        });


    }

    //      Get OnSite TEST Spinner Data
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//        switch(parent.getId()) {
//            case R.id.onSiteTestSpinnerTestType:
        String tnv = parent.getItemAtPosition(position).toString();
        //
        String[] splittnv = tnv.split("-");
//               debug output
        for (int i = 0; i < 3; i++) {
            System.out.println(splittnv[i]);
        }
        ReferenceData.testNameArabic = splittnv[0];
        min = splittnv[1];
        max = splittnv[2];
//                break;
//            case R.id.onSiteTestSpinnerTestSampleKind:
//                ReferenceData.testSampleKind = parent.getItemAtPosition(position).toString();
//                System.out.println(ReferenceData.testSampleKind);
//                break;

    }

//            if (parent.getItemIdAtPosition(position) == 0) {
//                saveOnSiteTestData.setHint(getResources().getString(R.string.set_test_value_hint));
//                CustomToast.customToast(getApplicationContext(), ReferenceData.TOAST_RANGE_MIN_MAX+"0-100");
//
//            } else if (parent.getItemIdAtPosition(position) == 1) {
//                saveOnSiteTestData.setHint("2");
//                CustomToast.customToast(getApplicationContext(), ReferenceData.TOAST_RANGE_MIN_MAX+"0-1000");
//
//            } else if (parent.getItemIdAtPosition(position) == 2) {
//                saveOnSiteTestData.setHint("3");
//                CustomToast.customToast(getApplicationContext(), ReferenceData.TOAST_RANGE_MIN_MAX+"0-10000");
//
//            } else if (parent.getItemIdAtPosition(position) == 3) {
//                saveOnSiteTestData.setHint("4");
//                CustomToast.customToast(getApplicationContext(), ReferenceData.TOAST_RANGE_MIN_MAX+"0-100000");
//
//            } else if (parent.getItemIdAtPosition(position) == 4) {
//                saveOnSiteTestData.setHint("5");
//                CustomToast.customToast(getApplicationContext(), ReferenceData.TOAST_RANGE_MIN_MAX+"0-1000000");
//
//            } else if (parent.getItemIdAtPosition(position) == 5) {
//                saveOnSiteTestData.setHint("6");
//                CustomToast.customToast(getApplicationContext(), ReferenceData.TOAST_RANGE_MIN_MAX+"0-1000000");
//
//            }
////        }


//    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
package com.ao8r.labapp.views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.ao8r.labapp.R;
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.customiz.ReadWriteFileFromInternalMem;
import com.ao8r.labapp.models.ReferenceData;
import com.ao8r.labapp.repository.ConfirmLabNameWithSrcData;
import com.ao8r.labapp.repository.InsertNewSamplePointUsingJdbc;
import com.ao8r.labapp.services.InternetConnection;



@RequiresApi(api = Build.VERSION_CODES.O)
public class SourceSample extends AppCompatActivity {
    //  declare Variables
    SharedPreferences sharedpreferences;
    public static final String labNoReference = "labNoReference";
    public static final String labNoRef = "labNoRefKey";
    TextView labNameTextView, sampleXTxtView, sampleYTxtView;
    EditText labCodeEditTxt, sampleCodeEditTxt;
    Button saveSrcDataBtn, getLabNameBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_sample);

//  create instance from Declared vars
//        Edit/Text/View
        labCodeEditTxt = findViewById(R.id.labCodeEditText);
        sampleCodeEditTxt = findViewById(R.id.sampleCodeEditText);
        sampleXTxtView = findViewById(R.id.sampleXTextView);
        sampleYTxtView = findViewById(R.id.sampleYTextView);
        labNameTextView = findViewById(R.id.labNameTextView);
//        Button
        saveSrcDataBtn = findViewById(R.id.saveSrcDataBtn);
        getLabNameBtn = findViewById(R.id.getLabNameBtn);


        String temSampleX = getIntent().getStringExtra("locationLatVal");
        String temSampleY = getIntent().getStringExtra("locationLongVal");
//        ReferenceData.labName = labNameTextView.getText().toString();

        //Assign data to Text field
//        labCodeTxtView.setText(temLabCode);
//        sampleCodeTxtView.setText(tempSampleCode);
        sampleXTxtView.setText(temSampleX);
        sampleYTxtView.setText(temSampleY);


//        initialize sharedPreference 27/7/2022

        sharedpreferences = getSharedPreferences(labNoReference,
                MODE_PRIVATE);
        if (sharedpreferences.contains(labNoRef)) {
            labCodeEditTxt.setText(sharedpreferences.getString(labNoRef, ""));
        }


        getLabNameBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                try {
                    ReferenceData.labCode = labCodeEditTxt.getText().toString();
//                write lab code to file
//                TODO:labCode
                    ReadWriteFileFromInternalMem.savLabCodeToSD(labCodeEditTxt.getText().toString());
//                ReadWriteFileFromInternalMem.generateNoteOnSD(labCodeEditTxt.getText().toString(),ReferenceData.LAB_CODE_CACHE,ReferenceData.LAB_CODE_FILE_NAME);
//                CustomToast.customToast(getApplicationContext(),"Save Lab Code to File"+labCodeEditTxt.getText().toString());
                    ReferenceData.sampleCode = sampleCodeEditTxt.getText().toString();
                    if (ReferenceData.labCode.isEmpty() || ReferenceData.sampleCode.isEmpty()) {
                        CustomToast.customToast(getApplicationContext(), "من فضلك أدخل بيانات الحقول الفارغة");
                    } else {
                        ConfirmLabNameWithSrcData.getLabName(getApplicationContext());
                        labNameTextView.setText(String.format("%s -- %s", ReferenceData.labName, ReferenceData.sampleName));

                        System.out.println(ReferenceData.labName + " -- " + ReferenceData.sampleName);
                    }
                }catch (Exception e)
                {
                    e.getStackTrace();
                }

            }

        });


        // Set Action to Button
        saveSrcDataBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                try {

//              assign data to send src method Params
//                ReferenceData.labCode = (labCodeTxtView.getText().toString());
//                ReferenceData.sampleCode = (sampleCodeTxtView.getText().toString());
                    labCodeEditTxt.getText().toString();
                    ReferenceData.sampleCode = sampleCodeEditTxt.getText().toString();
                    ReferenceData.sampleX = (sampleXTxtView.getText().toString());
                    ReferenceData.sampleY = (sampleYTxtView.getText().toString());

                    if (labNameTextView.getText() == "") {
                        CustomToast.customToast(getApplicationContext(), "من فضلك أدخل بيانات الحقول الفارغة");

                    } else {

//                Save sharedPreference

                        SharedPreferences.Editor editor =
//                            getApplicationContext().getSharedPreferences(labNoReference,MODE_PRIVATE).edit();
                                sharedpreferences.edit();
                        editor.putString(labNoRef, ReferenceData.labCode);
                        editor.apply();
                        editor.commit();

//                Check internet Connectivity
                        if (InternetConnection.checkConnection(getApplicationContext())) {
                            // Its Available...
                            CustomToast.customToast(getApplicationContext(), "متصل بالانترنت");
                        } else {
                            // Not Available...

                            CustomToast.customToast(getApplicationContext(), "فضلا تحقق من الاتصال بالانترنت");
                        }
//                Calling sendData Method (comments 5/3/2022)
//                SaveRefDataToApi.sendRefData(getApplicationContext());

//                check empty data


                        InsertNewSamplePointUsingJdbc.insertNewSample(getApplicationContext());
                        System.out.println("SAMPLE-SOURCE-DATA" +
                                ReferenceData.labCode +
                                ReferenceData.sampleCode +
                                ReferenceData.sampleX +
                                ReferenceData.sampleY +
                                ReferenceData.currentUserId);
                        CustomToast.customToast(getApplicationContext(), "تمت الإضافة بنجاح");
// Back to Menu Page
//                after delay time 500ms
                        final Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //Do something after 500ms
                                Intent intent = new Intent(getBaseContext(), MenuScreen.class);
                                startActivity(intent);

                            }
                        }, 800);

                    }
                }catch (Exception e){
                    e.getStackTrace();
                }
            }

        });
    }

    // Retrive labCode SharedPreference
    public void retriveSharedPrefLabCode(View view) {

        sharedpreferences = getSharedPreferences(labNoReference,
                MODE_PRIVATE);

        if (sharedpreferences.contains(labNoRef)) {
            labCodeEditTxt.setText(sharedpreferences.getString(labNoRef, ""));
        }

    }

}


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
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.models.ReferenceData;
import com.ao8r.labapp.repository.InsertBrokenPointInDailyTB;
import com.ao8r.labapp.repository.InsertBrokenPointInSrcTB;
import com.ao8r.labapp.services.InternetConnection;

public class AddBrokenPointScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String brokenWaterTypeSpinner, tempBatchName = "كسورات";
    Button saveBrokenPoint;
    Spinner selectWaterTypeFromSpinner;
    EditText brokenSampleName, brokenDesc;
    TextView brokenBatchNameTV, brokenXTV, brokenYTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_broken_point_screen);

//        create instance from Declared vars
//        TextView

        brokenBatchNameTV = findViewById(R.id.batchNameBrokenTextView);
        brokenXTV = findViewById(R.id.xBrokenTextView);
        brokenYTV = findViewById(R.id.yBrokenTextView);

//        spinner
        selectWaterTypeFromSpinner = findViewById(R.id.sampleKindSpinner);

//        EditText

        brokenSampleName = findViewById(R.id.sampleNameBrokenEditText);
        brokenDesc = findViewById(R.id.descBrokenEditText);

//        Button

        saveBrokenPoint = findViewById(R.id.addNewBrokenPoint);

//        Get data from Location

       String tempBrokenX = getIntent().getStringExtra("locationLatVal");
        String tempBrokenY = getIntent().getStringExtra("locationLongVal");



//        Assign data to Text field
//
        brokenXTV.setText(ReferenceData.sampleBrokenX);
        brokenYTV.setText(ReferenceData.sampleBrokenY);
//        brokenXTV.setText(tempBrokenX);
//        brokenYTV.setText(tempBrokenY);
        brokenBatchNameTV.setText(tempBatchName);



//        Assign Spinner
//
        ArrayAdapter<String> spinnerWaterBroken = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.kinds));
        spinnerWaterBroken.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectWaterTypeFromSpinner.setAdapter(spinnerWaterBroken);
        selectWaterTypeFromSpinner.setOnItemSelectedListener(this);

//        Set Action to Button
        saveBrokenPoint.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                try {
                    ReferenceData.notesBroken = "valid";
                    ReferenceData.batchName = brokenBatchNameTV.getText().toString();
//          get store data
                    ReferenceData.sampleBrokenX = brokenXTV.getText().toString();
                    ReferenceData.sampleBrokenY = brokenYTV.getText().toString();
                    ReferenceData.sampleKind = brokenWaterTypeSpinner;
                    ReferenceData.disBetweenTowPoints = 0;

//          get data from EditText

                    ReferenceData.sampleName = brokenSampleName.getText().toString().trim();
                    ReferenceData.sampleDescription = brokenDesc.getText().toString().trim();


                    System.out.println(
                            ReferenceData.labCode + "--" +
                                    ReferenceData.maxSampleCode + "--" +
                                    ReferenceData.labName + "--" +
                                    ReferenceData.sectorName + "--" +
                                    ReferenceData.notesBroken + "--" +
                                    ReferenceData.batchName + "--" +
                                    ReferenceData.sampleName + "--" +
                                    ReferenceData.sampleDescription + "--" +
                                    ReferenceData.sampleKind + "--" +
                                    ReferenceData.sampleBrokenX + "--" +
                                    ReferenceData.sampleBrokenY + "--" +
                                    ReferenceData.disBetweenTowPoints + "--"


                    );
                    //                Check internet Connectivity
                    if (InternetConnection.checkConnection(getApplicationContext())) {
                        // Its Available...
                        CustomToast.customToast(getApplicationContext(), "متصل بالانترنت");
                    } else {
                        // Not Available...

                        CustomToast.customToast(getApplicationContext(), "فضلا تحقق من الاتصال بالانترنت");


                    }
                    if (ReferenceData.sampleName.isEmpty() || ReferenceData.sampleDescription.isEmpty()
                    ) {
                        CustomToast.customToast(getApplicationContext(), "فضلا أدخل بيانات الحقول الفارغة");
                    } else {

//                    InsertBrokenPointInSrcTB.insertBrokenPointInSrcTB(getApplicationContext());
                        try {

                            InsertBrokenPointInDailyTB.insertBrokenPointInDailyTB(getApplicationContext());
//                    nav to menu Screen

                            Intent intent = new Intent(getApplicationContext(), MenuScreen.class);
                            startActivity(intent);
                        }catch (Exception e){
                            e.getStackTrace();
                        }


                    }
                }catch (Exception e){
                    e.getStackTrace();
                }

            }
        });


    }
//        Get Spinner Data

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
         brokenWaterTypeSpinner = adapterView.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

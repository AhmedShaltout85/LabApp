package com.ao8r.labapp.views;

import static com.ao8r.labapp.models.ReferenceData.URL_CONNECT_LINK;
import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.ao8r.labapp.R;
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.customiz.ReadWriteFileFromInternalMem;
import com.ao8r.labapp.models.ReferenceData;
import com.ao8r.labapp.repository.LoginUsingJdbc;
import com.ao8r.labapp.services.InternetConnection;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class LoginScreen extends AppCompatActivity {
    // After API 23 permission request is asked at runtime
    Button loginUser;
    CheckBox rememberMe;
    EditText loginName;
    EditText loginPassword;
    String password;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    BottomSheetDialog bottomSheetDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        loginUser = findViewById(R.id.userLoginButton);
        loginName = findViewById(R.id.userNameEditText);
        loginPassword = findViewById(R.id.passwordEditText);
        rememberMe = findViewById(R.id.rememberMeCheckBox);

//
        bottomSheetDialog = new BottomSheetDialog(this); // assign BottomSheet
        createBottomSheetDialog(); // create bottomSheetDialog Method
        bottomSheetDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);


        //////////////////////////////////// remember login /////////////////
        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        /////////////////To get Stored Data/////////////////////////////////

        String name = sharedPreferences.getString("name", " ");
        String passwords = sharedPreferences.getString("password", "");
        ////////////////////////////////////////////////////////////////////

        loginPassword.setText(passwords);
        loginName.setText(name);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.MANAGE_EXTERNAL_STORAGE
                }, REQUEST_CODE
        );


        loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get data from user TextInput
                ReferenceData.loginUser = loginName.getText().toString();
                password = loginPassword.getText().toString();
//                loginPassword.requestFocus();
                // check user input data validation

                ///////////////To Store Data////////////////////////////
                if (rememberMe.isChecked()) {

                    editor.putString("name", ReferenceData.loginUser);
                    editor.putString("password", password);
                    editor.commit();
                } else {

                    editor.putString("name", "");
                    editor.putString("password", "");

                    editor.commit();
                }
                ////////////////////////////////////////////
//                TODO: get BaseIp
//                URL_CONNECT_LINK = ReadWriteFileFromInternalMem.getIpFromFile();
                try {
                    //TODO: HANDLE ERROR WHEN SERVER IS OFFLINE(02-04-2023)
                    ////////////////////////////////////////////
//                TODO: get BaseIp
                    if (URL_CONNECT_LINK == null || URL_CONNECT_LINK.isEmpty()) {
                        URL_CONNECT_LINK = ReadWriteFileFromInternalMem.getIpFromFile();
                    } else {
                        URL_CONNECT_LINK = "41.33.226.211";
                    }
                    /////////////////////////////////////////////////////////////
                } catch (Exception exception) {
                    exception.getStackTrace();
                    CustomToast.customToast(getApplicationContext(), "الخادم فى وضع OFFLINE");
                }


                //////////////////////////////////////////////////////////////
//                Check internet Connectivity

                if (InternetConnection.checkConnection(getApplicationContext())) {
                    // Its Available...
                    CustomToast.customToast(getApplicationContext(), "متصل بالانترنت");
                } else {
                    // Not Available...
                    CustomToast.customToast(getApplicationContext(), "فضلا تحقق من الاتصال بالانترنت");

                }

//                Check users Authority

                if (ReferenceData.loginUser.trim().length() == 0 || password.length() == 0) {
                    CustomToast.customToast(getApplicationContext(), "من فضلك أدخل البيانات");

                } else {
//              Send Login data to authorize from API(comment in 5/3/2022)
//                    SendLoginParam.sendLoginDataToApi(view.getContext(), password);
                    try {
                        LoginUsingJdbc.loginUsingJdbc(view.getContext(), password);

                    } catch (Exception e) {
                        e.getStackTrace();
                    }

                }

            }
        });
    }

    private void createBottomSheetDialog() {
        View view = getLayoutInflater().inflate(R.layout.custom_setting_bottom_sheet_dailog, null, false);
        Button addToSdFile = view.findViewById(R.id.addToSdFileIdButton);
        final EditText getIpAddress = view.findViewById(R.id.getIpAddressEditText);

        addToSdFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String getUrl = getIpAddress.getText().toString().trim();
//              TODO: write ip
                ReadWriteFileFromInternalMem.generateNoteOnSD(getUrl);

                Toast.makeText(getApplicationContext(), getUrl, Toast.LENGTH_SHORT).show();


                getIpAddress.setText("");
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.setContentView(view);
    }

    public void navToSetting(View view) {

        bottomSheetDialog.show();
    }


}
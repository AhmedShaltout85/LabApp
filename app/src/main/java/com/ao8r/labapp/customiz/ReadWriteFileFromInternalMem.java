package com.ao8r.labapp.customiz;

import static com.ao8r.labapp.models.ReferenceData.BASE_IP;
import static com.ao8r.labapp.models.ReferenceData.LAB_CODE_CACHE;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.ao8r.labapp.models.ReferenceData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ReadWriteFileFromInternalMem {


public static String getIpFromFile() {
    String txt=" ";
    // Get the directory for the user's public pictures directory.
//    File root = new File(Environment.getExternalStorageDirectory(), BASE_IP);
    File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), BASE_IP);
    if (!root.exists()) {
        root.mkdirs();
    }
    File file = new File(root, ReferenceData.LOGIN_FILE_NAME);

    // Save your stream, don't forget to flush() it before closing it.
    try
    {

        FileInputStream fOut = new FileInputStream(file);
        if ( fOut != null )
        {
            InputStreamReader inputStreamReader = new InputStreamReader(fOut);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();
            while ( (receiveString = bufferedReader.readLine()) != null )
            {
                stringBuilder.append(receiveString);
            }
            fOut.close();
            txt = stringBuilder.toString();
        }

    }
    catch (IOException e)
    {
        Log.e("Exception", "File write failed: " + e.toString());
    }
    //*********************************
    return txt;
}


    public static void generateNoteOnSD( String sBody) {
        try {
//            File root = new File(Environment.getExternalStorageDirectory(), BASE_IP);
            File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), BASE_IP);
            if (!root.exists()) {
                root.mkdirs();
            }
            File sdFile = new File(root, ReferenceData.LOGIN_FILE_NAME);
            FileWriter writer = new FileWriter(sdFile);
            writer.append(sBody);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /////////////////labCode

    public static String getLabCodeFromFile() {
        String txt=" ";
        // Get the directory for the user's public pictures directory.
//        File root = new File(Environment.getExternalStorageDirectory(), LAB_CODE_CACHE);
        File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), LAB_CODE_CACHE);
        if (!root.exists()) {
            root.mkdirs();
        }
        File file = new File(root, ReferenceData.LAB_CODE_FILE_NAME);

        // Save your stream, don't forget to flush() it before closing it.
        try
        {

            FileInputStream fOut = new FileInputStream(file);
            if ( fOut != null )
            {
                InputStreamReader inputStreamReader = new InputStreamReader(fOut);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ( (receiveString = bufferedReader.readLine()) != null )
                {
                    stringBuilder.append(receiveString);
                }
                fOut.close();
                txt = stringBuilder.toString();
            }

        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        //*********************************
        return txt;
    }


    public static void savLabCodeToSD( String sBody) {
        try {
            File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), LAB_CODE_CACHE);
            if (!root.exists()) {
                root.mkdirs();
            }
            File sdFile = new File(root, ReferenceData.LAB_CODE_FILE_NAME);
            FileWriter writer = new FileWriter(sdFile);
            writer.append(sBody);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

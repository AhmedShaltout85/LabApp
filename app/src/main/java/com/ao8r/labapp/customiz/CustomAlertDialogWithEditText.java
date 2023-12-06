package com.ao8r.labapp.customiz;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.ao8r.labapp.models.ReferenceData;

public class CustomAlertDialogWithEditText {

    public static void getMissedLabCode(Context context, String title){

//                       Get labCode From user

        AlertDialog.Builder builder
                = new AlertDialog.Builder(context);
        builder.setTitle(title);
        final EditText getLabCode = new EditText(context);
        getLabCode.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(getLabCode);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ReferenceData.labCode = getLabCode.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
//

    }
}

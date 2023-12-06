package com.ao8r.labapp.customiz;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class CustomToast {

    public static void customToast(Context context, String msg){

        Toast toast= Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
        toast.show();
    }
}

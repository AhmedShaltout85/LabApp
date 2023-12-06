package com.ao8r.labapp.customiz;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.ao8r.labapp.R;

public class CustomLoader {

    public static void customLoader(Context context, String msg){

        final ProgressDialog progress = new ProgressDialog(context,R.style.MyAlertDialogStyle);    //ProgressDialog
        progress.setTitle("");
        progress.setMessage(msg);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progress.getWindow().setGravity(Gravity.CENTER,0,0);
//        progress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//        progress.setContentView(R.layout.progress_layout);

        progress.setCancelable(false);
        progress.show();
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            progress.dismiss();
        }).start();
    }
}

package com.ao8r.labapp.repository;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ao8r.labapp.controller.MySingleton;
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.models.ReferenceData;

public class SendParamToApi {
    public static void sendRefDataToApi(ReferenceData referenceData , final Context context) {
//   Get a RequestQueue
        RequestQueue queue = MySingleton.getInstance(context).getRequestQueue();
        String url = ReferenceData.urlIP
                + "/ref/"
                + ReferenceData.labCodeLt + "/"
                + ReferenceData.sampleCodeLt + "/"
                + referenceData.xLatitude + "/"
                + referenceData.yLongitude + "/"
                + referenceData.notes + "/"
                + ReferenceData.currentUserId;

//    debug url param

        System.out.println("YOUR LOCATIONS INFO ARE : >>> "
                + ReferenceData.labCodeLt + "/"
                + ReferenceData.sampleCodeLt + "/"
                + referenceData.xLatitude + "/"
                + referenceData.yLongitude + "/"
                + referenceData.notes + "/"
                + ReferenceData.currentUserId + " <<<");
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("RESPONSE : " + response);
                        if (response.equals("done")) {
                            CustomToast.customToast(context,"تم الارسال بنجاح");

                        } else {
                            CustomToast.customToast(context, "تأكد من الإتصال بالأنترنت");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }
}

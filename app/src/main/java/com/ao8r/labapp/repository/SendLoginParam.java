package com.ao8r.labapp.repository;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ao8r.labapp.controller.MySingleton;
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.views.MenuScreen;
import com.ao8r.labapp.models.ReferenceData;

import org.json.JSONException;
import org.json.JSONObject;

public class SendLoginParam {
    public static void sendLoginDataToApi(final Context context, final String loginPassword) {
        // Get a RequestQueue
        RequestQueue queue = MySingleton.getInstance(context).
                getRequestQueue();
        String url = ReferenceData.urlIP + "/login/" + ReferenceData.loginUser;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, (JSONObject) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String userId = response.getString("id");
                            ReferenceData.userPassword = response.getString("userPassword");
                            ReferenceData.userFirstName = response.getString("firstName");
                            ReferenceData.userLastName = response.getString("lastName");

                            if (userId.equals("0")) {
                                CustomToast.customToast(context, "بيانات خاطئة");


                            } else {
                                if (loginPassword.equals(ReferenceData.userPassword)) {
                                    ReferenceData.currentUserId = Integer.parseInt(userId);
                                    //Print user id
                                    System.out.println("<USER-ID:>" + " " + ReferenceData.currentUserId +
                                            " <firstName:>" + " " + ReferenceData.userFirstName +
                                            " <lastName:>" + " " + ReferenceData.userLastName +
                                            " <loginUser:> " + " " + ReferenceData.loginUser +
                                            " <password:>" + " " + ReferenceData.userPassword

                                    );

                                    Intent intent = new Intent(context, MenuScreen.class);
                                    context.startActivity(intent);
                                } else {
                                    CustomToast.customToast(context, "بيانات خاطئة");

                                }
                            }

                        } catch (JSONException e) {
                            CustomToast.customToast(context, "لا يمكن تسجيل الدخول - أعد المحاولة");
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "onErrorResponse: " + error.getMessage());

            }
        });

        // Add a request (in this example, called stringRequest) to your RequestQueue.
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}

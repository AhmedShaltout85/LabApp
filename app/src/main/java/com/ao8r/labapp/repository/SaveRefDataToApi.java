package com.ao8r.labapp.repository;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.ao8r.labapp.controller.MySingleton;
import com.ao8r.labapp.models.ReferenceData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.ao8r.labapp.models.ReferenceData.*;

public class SaveRefDataToApi {

    public static void sendRefData(Context context) {


        try {
            // Get a RequestQueue
            RequestQueue queue = MySingleton.getInstance(context).getRequestQueue();
            String URL = urlIP + "/slsrc/" + labCode + "/" + sampleCode;
//            userEntity to json
            JSONObject jsonUserEntity = new JSONObject();
            jsonUserEntity.put("id", String.valueOf(currentUserId));
            jsonUserEntity.put("firstName", userFirstName);
            jsonUserEntity.put("lastName", userLastName);
            jsonUserEntity.put("userName", loginUser);
            jsonUserEntity.put("userPassword", userPassword);
//            Response Body
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("labCode", labCode);
            jsonBody.put("sampleCode", sampleCode);
            jsonBody.put("sampleX", sampleX);
            jsonBody.put("sampleY", sampleY);
            jsonBody.put("userEntity", jsonUserEntity);

            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }


                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            // Add a request (in this example, called stringRequest) to your RequestQueue.
            MySingleton.getInstance(context).addToRequestQueue(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}

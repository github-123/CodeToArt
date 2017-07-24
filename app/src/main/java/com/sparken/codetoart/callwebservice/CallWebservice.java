package com.sparken.codetoart.callwebservice;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sparken.codetoart.Utility.Utility;
import com.sparken.codetoart.constant.AppController;
import com.sparken.codetoart.constant.CustomProgressDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class CallWebservice {

    public static JSONArray jsonArray = null;

    public synchronized static <T> void getWebservice(final boolean progress, final Context context, int method, String url, final HashMap<String, String> param, final VolleyResponseListener listener, final Class<T[]> aClass) {

        if (Utility.isNetworkConnected(context)) {

            if (progress == true) {

                CustomProgressDialog.showDialog(context, "Please Wait");
            }
            StringRequest stringRequest = new StringRequest(method, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (progress == true) {
                        CustomProgressDialog.dismissDialog(context);
                    }
                    try {
                        /*JSONObject jsonObject = new JSONObject(response);
                        String key = jsonObject.getString(WSConstants.RESPONSE_RESULT);
                        String message = jsonObject.getString(WSConstants.RESPONSE_MESSAGE);
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        if (key.equalsIgnoreCase(WSConstants.SUCCESS_KEY)) {
                            jsonArray = jsonObject.getJSONArray(WSConstants.RESPONSE_ARRAY);
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();
                            Object[] object = gson.fromJson(String.valueOf(jsonArray), aClass);
                            listener.onResponse(object, message);
                        } else if (key.equalsIgnoreCase(WSConstants.ERROR_KEY)) {
                            if (progress == true) {
                                CustomProgressDialog.dismissDialog(context);
                                listener.onError(message.toString());
                            }
                        }*/
                        if (response != null) {
                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject = new JSONObject(response);
                            jsonArray = jsonObject.getJSONArray(WSConstants.RESPONSE_ARRAY);
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();
                            Object[] object = gson.fromJson(String.valueOf(jsonArray), aClass);
                            listener.onResponse(object, "Success");
                        } else {
                            if (progress == true) {
                                CustomProgressDialog.dismissDialog(context);
                                listener.onError("Error");
                            }
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                        if (progress == true) {
                            CustomProgressDialog.dismissDialog(context);
                            listener.onError("Something went wrong");
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (progress == true) {
                        CustomProgressDialog.dismissDialog(context);
                    }
                    listener.onError(error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap = param;
                    return hashMap;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
        } else {
            CustomProgressDialog.showAlertDialogMessage(context, "Alert", "Check Internet Connection");
        }
    }

}

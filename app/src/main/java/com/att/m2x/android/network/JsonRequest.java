package com.att.m2x.android.network;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.att.m2x.android.common.Constants;
import com.att.m2x.android.listeners.ResponseListener;
import com.att.m2x.android.sharedPreferences.APISharedPreferences;
import com.att.m2x.android.utils.ArrayUtils;

/**
 * Created by Joaquin on 1/12/14.
 */
public class JsonRequest {

    final static ApiV2Response apiResponse = new ApiV2Response();

    public static void makePostRequest(final Context context,
                                       String path,
                                       JSONObject body,
                                       final ResponseListener listener,
                                       final int requestCode) {
        makeRequest(context, Request.Method.POST, path, null, body, listener, requestCode);
    }

    public static void makeGetRequest(final Context context,
                                      String path,
                                      HashMap<String,String> params,
                                      final ResponseListener listener,
                                      final int requestCode) {
        makeRequest(context, Request.Method.GET, path, params, null, listener, requestCode);
    }

    public static void makeGetRequest(final Context context,
                                      String path,
                                      HashMap<String,String> params,
                                      JSONObject body,
                                      final ResponseListener listener,
                                      final int requestCode) {
        makeRequest(context, Request.Method.GET, path, params, body, listener, requestCode);
    }

    public static void makePutRequest(final Context context,
                                      String path,
                                      JSONObject body,
                                      final ResponseListener listener,
                                      final int requestCode) {
        makeRequest(context, Request.Method.PUT, path, null, body, listener, requestCode);
    }

    public static void makeDeleteRequest(final Context context,
                                         String path,
                                         JSONObject body,
                                         final ResponseListener listener,
                                         final int requestCode) {
        makeRequest(context, Request.Method.DELETE, path, null, body, listener, requestCode);
    }

    private static void makeRequest(final Context context,
                                    int method,
                                    String path,
                                    HashMap<String, String> params,
                                    JSONObject body,
                                    final ResponseListener listener,
                                    final int requestCode) {

        String url = Constants.API_BASE_URL.concat(path);
        if(params!=null)
            url = url.concat("?".concat(ArrayUtils.mapToQueryString(params)));

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                method,
                url,
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject o) {
                        handleResponse(context, listener, requestCode, o);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleError(context, listener, requestCode, error);
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return JsonRequest.getHeaders(context);
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                return JsonRequest.parseNetworkResponse(response);
            }
        };

        //It's better if the queue is obtained with an app context to keep it alive while the app is in foreground.
        VolleyResourcesSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjReq);
    }

    private static void handleResponse(Context context, ResponseListener listener,
                                       int requestCode, JSONObject o) {
        apiResponse.set_json(o);
        apiResponse.set_clientError(Boolean.FALSE);
        apiResponse.set_error(Boolean.FALSE);
        apiResponse.set_serverError(Boolean.FALSE);
        apiResponse.set_success(Boolean.TRUE);
        //Save response
        APISharedPreferences.saveLastResponse(context,apiResponse);
        listener.onRequestCompleted(apiResponse,requestCode);
    }

    private static void handleError(Context context, ResponseListener listener,
                                    int requestCode, VolleyError error) {
        apiResponse.set_json(null);
        if(error.networkResponse!=null)
            apiResponse.set_status(String.valueOf(error.networkResponse.statusCode));

        if(error.networkResponse!=null && error.networkResponse.data!=null){
            try {
                apiResponse.set_raw(new String(error.networkResponse.data,"utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        if(error.networkResponse!=null && error.networkResponse.statusCode<500){
            apiResponse.set_clientError(Boolean.TRUE);
            apiResponse.set_serverError(Boolean.FALSE);
        }else{
            apiResponse.set_clientError(Boolean.FALSE);
            apiResponse.set_serverError(Boolean.TRUE);
        }
        apiResponse.set_error(Boolean.TRUE);
        apiResponse.set_success(Boolean.FALSE);

        //Save response
        APISharedPreferences.saveLastResponse(context,apiResponse);
        listener.onRequestError(apiResponse,requestCode);
    }

    private static Map<String, String> getHeaders(Context context) throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/json");
        params.put("X-M2X-KEY", APISharedPreferences.getApiKey(context));
        params.put("User-agent", Constants.USER_AGENT);
        return params;
    }

    private static Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            apiResponse.set_raw(new String(response.data,"utf-8"));
            apiResponse.set_status(String.valueOf(response.statusCode));
            apiResponse.set_headers(response.headers.toString());
            if(apiResponse.get_raw()==null || apiResponse.get_raw().equals("")
                    && response.statusCode<400){
                return Response.success(
                        null,
                        HttpHeaderParser.parseCacheHeaders(response));
            }else{
                return Response.success(
                        new JSONObject(apiResponse.get_raw()),
                        HttpHeaderParser.parseCacheHeaders(response));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (JSONException e) {
            return Response.error(new ParseError(e));
        }
    }
}

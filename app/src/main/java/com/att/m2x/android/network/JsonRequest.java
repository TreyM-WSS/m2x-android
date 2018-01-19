package com.att.m2x.android.network;

import android.content.Context;
import android.util.Log;

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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Joaquin on 1/12/14.
 */
public class JsonRequest {

    public static void makePostRequest(final Context context,
                                       String url,
                                       JSONObject body,
                                       final ResponseListener listener,
                                       final int requestCode) {
        makeRequest(context, Request.Method.POST, url, null, body, listener, requestCode);
    }

    public static void makeGetRequest(final Context context,
                                      String url,
                                      Map<String,String> params,
                                      final ResponseListener listener,
                                      final int requestCode) {
        makeRequest(context, Request.Method.GET, url, params, null, listener, requestCode);
    }

    public static void makeGetRequest(final Context context,
                                      String url,
                                      Map<String,String> params,
                                      JSONObject body,
                                      final ResponseListener listener,
                                      final int requestCode) {
        makeRequest(context, Request.Method.GET, url, params, body, listener, requestCode);
    }

    public static void makePutRequest(final Context context,
                                      String url,
                                      JSONObject body,
                                      final ResponseListener listener,
                                      final int requestCode) {
        makeRequest(context, Request.Method.PUT, url, null, body, listener, requestCode);
    }

    public static void makeDeleteRequest(final Context context,
                                         String url,
                                         Map<String, String> params,
                                         final ResponseListener listener,
                                         final int requestCode) {
        makeRequest(context, Request.Method.DELETE, url, params, null, listener, requestCode);
    }

    /**
     * parameter "body" is misleading, it's actually query params
     * {@see #makeDeleteRequest(Context, String, Map, ResponseListener, int)}
     */
    @Deprecated
    public static void makeDeleteRequest(final Context context,
                                         String url,
                                         JSONObject body,
                                         final ResponseListener listener,
                                         final int requestCode) {
        /*Converting the JSONObject body to Hash map, so that we can pass it through as
           a query params, Volley library is not adding body for a DELETE request*/
        Map<String,String> params = null;
        if(body != null){
            params = new Gson().fromJson(body.toString(),new TypeToken<Map<String, String>>(){}.getType());
        }

        makeRequest(context, Request.Method.DELETE, url, params, body, listener, requestCode);
    }

    private static void makeRequest(final Context context,
                                    int method,
                                    String url,
                                    Map<String, String> params,
                                    JSONObject body,
                                    final ResponseListener listener,
                                    final int requestCode) {

        if(params!=null && !params.isEmpty())
            url = url.concat("?".concat(ArrayUtils.mapToQueryString(params)));
        Log.d("JsonRequest", "body: " + body);
        JsonApiV2Request jsonObjReq = new JsonApiV2Request(
                method,
                url,
                body,
                new Response.Listener<ApiV2Response>() {
                    @Override
                    public void onResponse(ApiV2Response apiResponse) {
                        handleApiResponse(context, listener, requestCode, apiResponse);
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

        };

        //It's better if the queue is obtained with an app context to keep it alive while the app is in foreground.
        VolleyResourcesSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjReq);
    }

    private static void handleApiResponse(Context context, ResponseListener listener,
                                       int requestCode, ApiV2Response response) {
        //Save response
        APISharedPreferences.saveLastResponse(context,response);
        if (response.success()) {
            listener.onRequestCompleted(response, requestCode);
        }
        else {
            listener.onRequestError(response, requestCode);
        }
    }

    private static void handleResponse(Context context, ResponseListener listener,
                                       int requestCode, JSONObject o) {
        ApiV2Response apiResponse = new ApiV2Response();
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
        ApiV2Response apiResponse = new ApiV2Response();
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
        Map<String, String> params = new HashMap<>();
        params.put("Content-Type", "application/json");
        params.put("X-M2X-KEY", APISharedPreferences.getApiKey(context));
        params.put("User-agent", Constants.USER_AGENT);
        return params;
    }

    @Deprecated
    private static Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            ApiV2Response apiResponse = new ApiV2Response();
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
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    private static Response<ApiV2Response> parseApiNetworkResponse(NetworkResponse response) {
        try {
            ApiV2Response apiResponse = new ApiV2Response(new String(response.data,"utf-8"), response.statusCode, response.headers);
            return Response.success(apiResponse, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }
}

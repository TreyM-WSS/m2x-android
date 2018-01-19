package com.att.m2x.android.network;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.*;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by collinbrown on 8/24/17.
 */

public class JsonApiV2Request extends com.android.volley.toolbox.JsonRequest<ApiV2Response> {

    /**
     * Creates a new request.
     * @param method the HTTP method to use
     * @param url URL to fetch the JSON from
     * @param jsonRequest A {@link JSONObject} to post with the request. Null is allowed and
     *   indicates no parameters will be posted along with request.
     * @param listener Listener to receive the ApiV2Response
     * @param errorListener Error listener, or null to ignore errors.
     */
    public JsonApiV2Request(int method, String url, JSONObject jsonRequest,
                             Response.Listener<ApiV2Response> listener, Response.ErrorListener errorListener) {
        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener,
                errorListener);
    }

    /**
     * Constructor which defaults to <code>GET</code> if <code>jsonRequest</code> is
     * <code>null</code>, <code>POST</code> otherwise.
     *
     * {@see #JsonApiV2Request(int, String, JSONObject, Response.Listener, Response.ErrorListener)}
     */
    public JsonApiV2Request(String url, JSONObject jsonRequest, Response.Listener<ApiV2Response> listener,
                             Response.ErrorListener errorListener) {
        this(jsonRequest == null ? Method.GET : Method.POST, url, jsonRequest,
                listener, errorListener);
    }

    @Override
    protected Response<ApiV2Response> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            ApiV2Response apiResponse = new ApiV2Response(jsonString, response.statusCode, response.headers);
            return Response.success(apiResponse, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }
}


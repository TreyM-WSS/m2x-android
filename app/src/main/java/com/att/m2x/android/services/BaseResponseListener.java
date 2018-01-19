package com.att.m2x.android.services;

import android.util.Log;

import com.att.m2x.android.exceptions.M2XApiException;
import com.att.m2x.android.listeners.ResponseListener;
import com.att.m2x.android.listeners.TypedResponseListener;
import com.att.m2x.android.model.Response;
import com.att.m2x.android.network.ApiV2Response;
import com.att.m2x.android.services.model.LocationResult;
import com.att.m2x.android.utils.ArrayUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Map;

/**
 * Extracts typed values from a {@link Response} and delegates to {@link TypedResponseListener}.
 * Includes a few implementations. Used only in service classes
 */
abstract class BaseResponseListener<T> implements ResponseListener {

    private final TypedResponseListener<T> listener;

    public BaseResponseListener(TypedResponseListener<T> listener) {
        this.listener = listener;
    }

    @Override
    public void onRequestCompleted(ApiV2Response result, int requestCode) {
        Log.i("APIV2Response", "result.raw: "+result.getRawBody());
        if (result.getStatus() >= 200 && result.getStatus() < 300) {
            try {
                T value = parseResponse(result.getJsonBody());
                listener.onRequestCompleted(value);
            } catch (JSONException | ParseException e) {
                listener.onRequestException(new M2XApiException(e));
            }
        } else {
            listener.onRequestError(Response.fromJsonObject(result.getJsonObject()));
        }
    }

    @Override
    public void onRequestError(ApiV2Response error, int requestCode) {
        Response response = null;
        if (error.getJsonObject() != null) {
            response = Response.fromJsonObject(error.getJsonObject());
        } else if (error.getRaw() != null) {
            try {
                JSONObject errorJson = new JSONObject(error.getRaw());
                response = Response.fromJsonObject(errorJson);
            } catch (JSONException e) {
                Log.d("APIV2Error", "Unable to parse response object");
            }
        }
        if (response == null) {
            response = new Response();
        }
        listener.onRequestError(response);
    }

    @Override
    public void onRequestException(M2XApiException exception, int requestCode) {
        listener.onRequestException(exception);
    }

    abstract T parseResponse(JSONObject jsonObject) throws JSONException, ParseException;

    static class ResponseObjectListener extends BaseResponseListener<Response> {
        ResponseObjectListener(TypedResponseListener<Response> listener) {
            super(listener);
        }

        @Override
        Response parseResponse(JSONObject jsonObject) throws JSONException {
            return Response.fromJsonObject(jsonObject);
        }
    }

    static class EmptyResponseListener extends BaseResponseListener<Void> {

        EmptyResponseListener(TypedResponseListener<Void> listener) {
            super(listener);
        }

        @Override
        Void parseResponse(JSONObject jsonObject) throws JSONException {
            return null;
        }
    }

    static class StringResponseListener extends BaseResponseListener<String> {
        private final String field;

        StringResponseListener(TypedResponseListener<String> listener, String field) {
            super(listener);
            this.field = field;
        }

        @Override
        String parseResponse(JSONObject jsonObject) throws JSONException {
            return jsonObject.getString(field);
        }
    }

    static class MapResponseListener extends BaseResponseListener<Map<String, String>> {

        public MapResponseListener(TypedResponseListener<Map<String, String>> listener) {
            super(listener);
        }

        @Override
        Map<String, String> parseResponse(JSONObject jsonObject) throws JSONException, ParseException {
            return ArrayUtils.jsonObjectToMap(jsonObject);
        }
    }

    /**
     * Created by collinbrown on 10/15/17.
     */
    static class NewLocationResponseListener implements ResponseListener {

        private final TypedResponseListener<LocationResult> listener;

        NewLocationResponseListener(TypedResponseListener<LocationResult> listener) {
            this.listener = listener;
        }

        @Override
        public void onRequestCompleted(ApiV2Response result, int requestCode) {
            if (result.getStatus() >= 200 && result.getStatus() < 300) {
                listener.onRequestCompleted(LocationResult.fromApiV2Response(result));
            } else {
                listener.onRequestError(Response.fromJsonObject(result.getJsonObject()));
            }
        }

        @Override
        public void onRequestError(ApiV2Response error, int requestCode) {
            Response response = null;
            if (error.getJsonObject() != null) {
                response = Response.fromJsonObject(error.getJsonObject());
            } else if (error.getRaw() != null) {
                try {
                    JSONObject errorJson = new JSONObject(error.getRaw());
                    response = Response.fromJsonObject(errorJson);
                } catch (JSONException e) {
                    Log.d("APIV2Error", "Unable to parse response object");
                }
            }
            if (response == null) {
                response = new Response();
            }
            listener.onRequestError(response);
        }

        @Override
        public void onRequestException(M2XApiException exception, int requestCode) {
            listener.onRequestException(exception);
        }
    }
}

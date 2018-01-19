package com.att.m2x.android.services;

import android.content.Context;

import com.att.m2x.android.common.Constants;
import com.att.m2x.android.listeners.ResponseListener;
import com.att.m2x.android.listeners.TypedResponseListener;
import com.att.m2x.android.model.Key;
import com.att.m2x.android.network.JsonRequest;
import com.att.m2x.android.services.BaseResponseListener.EmptyResponseListener;
import com.att.m2x.android.services.model.CreateUpdateKey;
import com.att.m2x.android.services.model.ListKeyOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * This API allows managing master, device, collection, and distribution keys to access all
 * resources or just a device or stream.
 * <p>When a new account is created it will be assigned a default Master API Key.
 * This key cannot be deleted nor updated, it's only possible to regenerate its token.
 * {@see <a href="https://m2x.att.com/developer/documentation/v2/keys">M2X Keys REST API</a>}
 */
public class KeyService {

    public static final int REQUEST_CODE_KEYS_LIST = 3001;
    public static final int REQUEST_CODE_KEYS_CREATE = 3002;
    public static final int REQUEST_CODE_KEYS_DETAIL = 3003;
    public static final int REQUEST_CODE_KEYS_UPDATE = 3004;
    public static final int REQUEST_CODE_KEYS_REGENERATE = 3005;
    public static final int REQUEST_CODE_KEYS_DELETE = 3006;

    private KeyService() {
        // don't instantiate
    }
    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/keys#List-Keys">List Keys</a> endpoint
     * @param context
     * @param options
     * @param listener
     */
    public static void listKeys(Context context, ListKeyOptions options,
                         TypedResponseListener<List<Key>> listener) {
        listKeys(context, options.toMap(), new KeyListResponseListener(listener));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/keys#List-Keys">List Keys</a> endpoint
     * @param context
     * @param params
     * @param listener
     */
    public static void listKeys(Context context, Map<String,String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.KEYS_LIST),
                params,
                listener,
                REQUEST_CODE_KEYS_LIST
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/keys#Create-Key">Create Key</a> endpoint
     * @param context
     * @param key
     * @param listener
     */
    public static void createKey(Context context, CreateUpdateKey key,
                                 TypedResponseListener<Key> listener) {
        createKey(context, key.toJsonObject(), new KeyResponseListener(listener));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/keys#Create-Key">Create Key</a> endpoint
     * @param context
     * @param body
     * @param listener
     */
    public static void createKey(Context context, JSONObject body, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                Constants.KEYS_CREATE,
                body,
                listener,
                REQUEST_CODE_KEYS_CREATE
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/keys#View-Key-Details">Create Key</a> endpoint
     * @param context
     * @param keyId
     * @param listener
     */
    public static void viewDetails(Context context, String keyId, TypedResponseListener<Key> listener) {
        viewDetails(context, keyId, new KeyResponseListener(listener));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/keys#View-Key-Details">Create Key</a> endpoint
     * @param context
     * @param keyId
     * @param listener
     */
    public static void viewDetails(Context context, String keyId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.KEYS_DETAIL,keyId),
                null,
                listener,
                REQUEST_CODE_KEYS_DETAIL
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/keys#Update-Key">Update Key</a> endpoint
     * @param context
     * @param key
     * @param request
     * @param listener
     */
    public static void updateKey(Context context, Key key, CreateUpdateKey request,
                                 TypedResponseListener<Key> listener) {
        if (request.getName() == null) request.setName(key.getName());
        updateKey(context, request.toJsonObject(), key.getKey(), new KeyResponseListener(listener));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/keys#Update-Key">Update Key</a> endpoint
     * @param context
     * @param keyId
     * @param body
     * @param listener
     */
    public static void updateKey(Context context,JSONObject body, String keyId, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US,Constants.KEYS_UPDATE,keyId),
                body,
                listener,
                REQUEST_CODE_KEYS_UPDATE
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/keys#Regenerate-Key">Regenerate Key</a> endpoint
     * @param context
     * @param key
     * @param listener
     */
    public static void regenerateKey(Context context, Key key, TypedResponseListener<Key> listener) {
        regenerateKey(context, key.getKey(), new KeyResponseListener(listener));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/keys#Regenerate-Key">Regenerate Key</a> endpoint
     * @param context
     * @param keyId
     * @param listener
     */
    public static void regenerateKey(Context context, String keyId, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US,Constants.KEYS_REGENERATE,keyId),
                null,
                listener,
                REQUEST_CODE_KEYS_REGENERATE
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/keys#Delete-Key">Delete Key</a> endpoint
     * @param context
     * @param key
     * @param listener
     */
    public static void deleteKey(Context context, Key key, TypedResponseListener<Void> listener) {
        deleteKey(context, key.getKey(), new EmptyResponseListener(listener));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/keys#Delete-Key">Delete Key</a> endpoint
     * @param context
     * @param keyId
     * @param listener
     */
    public static void deleteKey(Context context,String keyId, ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US,Constants.KEYS_DELETE,keyId),
                (Map<String, String>) null,
                listener,
                REQUEST_CODE_KEYS_DELETE
        );
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    //// RESPONSE LISTENERS
    ////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static class KeyResponseListener extends BaseResponseListener<Key> {
        KeyResponseListener(TypedResponseListener<Key> listener) {
            super(listener);
        }

        @Override
        public Key parseResponse(JSONObject jsonObject) throws JSONException {
            return Key.fromJsonObject(jsonObject);
        }
    }

    private static class KeyListResponseListener extends BaseResponseListener<List<Key>> {
        KeyListResponseListener(TypedResponseListener<List<Key>> listener) {
            super(listener);
        }

        @Override
        List<Key> parseResponse(JSONObject jsonObject) throws JSONException, ParseException {
            JSONArray keyArray = jsonObject.getJSONArray("keys");
            List<Key> keyList = new ArrayList<>(keyArray.length());
            for (int i = 0; i < keyArray.length(); ++i) {
                keyList.add(Key.fromJsonObject(keyArray.getJSONObject(i)));
            }
            return keyList;
        }
    }
}

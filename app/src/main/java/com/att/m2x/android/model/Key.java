package com.att.m2x.android.model;

import android.content.Context;

import com.att.m2x.android.common.Constants;
import com.att.m2x.android.listeners.ResponseListener;
import com.att.m2x.android.network.JsonRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;

/**
 *
 * This API allows managing master, device, collection, and distribution keys to access all resources or just a device or stream.
 * When a new account is created it will be assigned a default Master API Key. This key cannot be deleted nor updated, it's only possible to regenerate its token.<p>
 * Authentication:
 * All methods described in this page require that a Master API Key is specified in the X-M2X-KEY header.
 * Any attempt of using a Distribution or Device level API key would result in a 403 Forbidden response. {@see <a href="https://m2x.att.com/developer/documentation/v2/overview#API-Keys">Learn more about API Keys.</a>}
 */
public class Key {

    public static final int REQUEST_CODE_KEYS_LIST = 3001;
    public static final int REQUEST_CODE_KEYS_CREATE = 3002;
    public static final int REQUEST_CODE_KEYS_DETAIL = 3003;
    public static final int REQUEST_CODE_KEYS_UPDATE = 3004;
    public static final int REQUEST_CODE_KEYS_REGENERATE = 3005;
    public static final int REQUEST_CODE_KEYS_DELETE = 3006;

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/keys#List-Keys">List Keys</a>} endpoint
     * @param context The application Context.
     * @param params Query parameters as HashMap<String,String>. View M2X API Docs for listing of available parameters.
     * @param listener {@link ResponseListener}
     */
    public static final void list(Context context,HashMap<String,String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.KEYS_LIST),
                params,
                listener,
                REQUEST_CODE_KEYS_LIST
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/keys#Create-Key">Create Key</a>} endpoint
     * @param context The application Context.
     * @param params as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener {@link ResponseListener}
     */
    public static final void create(Context context,JSONObject params, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                Constants.KEYS_CREATE,
                params,
                listener,
                REQUEST_CODE_KEYS_CREATE
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/keys#View-Key-Details">View Key Details</a>} endpoint
     * @param context The application Context.
     * @param keyId as String, ID of the key.
     * @param listener {@link ResponseListener}
     */
    public static final void viewDetails(Context context, String keyId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.KEYS_DETAIL,keyId),
                null,
                listener,
                REQUEST_CODE_KEYS_DETAIL
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/keys#Update-Key">Update Key</a>} endpoint
     * @param context The application Context.
     * @param params as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param keyId as String, ID of the key.
     * @param listener {@link ResponseListener}
     */
    public static final void update(Context context,JSONObject params,String keyId, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US,Constants.KEYS_UPDATE,keyId),
                params,
                listener,
                REQUEST_CODE_KEYS_UPDATE
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/keys#Regenerate-Key">Regenerate Key</a>} endpoint
     * @param context The application Context.
     * @param keyId as String, ID of the key
     * @param listener {@link ResponseListener}
     */
    public static final void regenerate(Context context, String keyId, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US,Constants.KEYS_REGENERATE,keyId),
                null,
                listener,
                REQUEST_CODE_KEYS_REGENERATE
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/keys#Delete-Key">Delete Key</a>} endpoint
     * @param context The application Context.
     * @param keyId as String, ID of the key
     * @param listener {@link ResponseListener}
     */
    public static final void delete(Context context,String keyId, ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US,Constants.KEYS_DELETE,keyId),
                null,
                listener,
                REQUEST_CODE_KEYS_DELETE
        );
    }


}

package com.att.m2x.android.main;

import android.content.Context;

import com.att.m2x.android.network.ApiV2Response;
import com.att.m2x.android.sharedPreferences.APISharedPreferences;

/**
 * View the {@see <a href="https://github.com/attm2x/m2x-android/blob/master/README.md">M2X Android Client README</a>} for usage details.<p>
 * Authentication:
 * All methods described in this page require that a Master API Key is specified in the X-M2X-KEY header.
 * Any attempt of using a Distribution or Device level API key would result in a 403 Forbidden response. {@see <a href="https://m2x.att.com/developer/documentation/v2/overview#API-Keys">Learn more about API Keys.</a>}
 */
public class M2XAPI {

    /**
     * Initialize library with Master API Key used for every request.
     * @param apiKey, as String the api key
     */
    public static void initialize(Context context,String apiKey){
        // Save api
        APISharedPreferences.setApiKey(context,apiKey);
    }

    /**
     * Returns last response from API
     * @param context The application Context.
     */
    public static ApiV2Response getLastResponse(Context context){
        return APISharedPreferences.getLastResponse(context);
    }

}

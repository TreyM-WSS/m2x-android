package lateralview.net.attm2xapiv2.network;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lateralview.net.attm2xapiv2.listeners.ResponseListener;
import lateralview.net.attm2xapiv2.sharedPreferences.APISharedPreferences;

/**
 * Created by Joaquin on 1/12/14.
 */
public class JsonRequest {

    public static void makeRequest(final Context context,String url, JSONObject params, HashMap<String,String> headers, final ResponseListener listener) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject o) {
                ApiV2Response response = new ApiV2Response();
                listener.onTaskCompleted(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ApiV2Response response = new ApiV2Response();
                listener.onTaskError(response);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("X-M2X-KEY", APISharedPreferences.getApiKey(context));
                return params;
            }
        };
        //It's better if the queue is obtained with an app context to keep it alive while the app is in foreground.
        VolleyResourcesSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjReq);
    }
}

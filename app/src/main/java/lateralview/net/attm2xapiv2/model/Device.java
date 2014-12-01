package lateralview.net.attm2xapiv2.model;

import android.content.Context;

import org.json.JSONObject;

import lateralview.net.attm2xapiv2.common.Constants;
import lateralview.net.attm2xapiv2.listeners.ResponseListener;
import lateralview.net.attm2xapiv2.network.JsonRequest;

/**
 * Created by Joaquin on 28/11/14.
 */
public class Device {

    public static final void getCatalog(Context context,JSONObject params, ResponseListener listener){
        JsonRequest.makeRequest(
                context,
                Constants.DEVICE_SEARCH_CATALOG,
                params,
                null,
                listener
                );
    }
}

package lateralview.net.attm2xapiv2.model;

import android.content.Context;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;

import lateralview.net.attm2xapiv2.common.Constants;
import lateralview.net.attm2xapiv2.listeners.ResponseListener;
import lateralview.net.attm2xapiv2.network.JsonRequest;

/**
 * Created by Joaquin on 28/11/14.
 */
public class Device {

    public static final int REQUEST_CODE_SEARCH_PUBLIC_CATALOG = 1001;
    public static final int REQUEST_CODE_SEARCH_DEVICES = 1002;
    public static final int REQUEST_CODE_LIST_DEVICE_GROUP = 1003;
    public static final int REQUEST_CODE_CREATE_DEVICE = 1004;
    public static final int REQUEST_CODE_DEVICE_DETAILS = 1005;

    public static final void searchPublicCatalog(Context context,HashMap<String,String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                Constants.DEVICE_SEARCH_PUBLIC_CATALOG,
                params,
                listener,
                REQUEST_CODE_SEARCH_PUBLIC_CATALOG
        );
    }

    public static final void searchDevices(Context context,HashMap<String,String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                Constants.DEVICE_SEARCH_CATALOG,
                params,
                listener,
                REQUEST_CODE_SEARCH_DEVICES
        );
    }

    public static final void listDeviceGroups(Context context,HashMap<String,String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                Constants.DEVICE_LIST_GROUPS,
                params,
                listener,
                REQUEST_CODE_LIST_DEVICE_GROUP
        );
    }

    public static final void createDevice(Context context,JSONObject params, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                Constants.DEVICE_CREATE,
                params,
                listener,
                REQUEST_CODE_CREATE_DEVICE
        );
    }

    public static final void updateDeviceDetails(Context context,JSONObject params,String deviceId, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_CREATE,deviceId),
                params,
                listener,
                REQUEST_CODE_CREATE_DEVICE
        );
    }

    public static final void viewDevice(Context context,HashMap<String,String> params,String deviceId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_VIEW_DETAILS,deviceId),
                params,
                listener,
                REQUEST_CODE_DEVICE_DETAILS
        );
    }

    public static final void readDeviceLocation(Context context,HashMap<String,String> params,String deviceId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_VIEW_DETAILS,deviceId),
                params,
                listener,
                REQUEST_CODE_DEVICE_DETAILS
        );
    }

}

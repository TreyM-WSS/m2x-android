package com.att.m2x.android.model;

import android.content.Context;

import com.att.m2x.android.common.Constants;
import com.att.m2x.android.listeners.ResponseListener;
import com.att.m2x.android.network.JsonRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;

/**
 * The Device API allows you to interact with your devices (including creating new devices, updating devices, pushing data to a device stream,
 * updating a device location, etc.) as well as public devices that belong to other M2X accounts. A Device is any individual generator of data
 * that can be connected to M2X. This could be a physical device, application,
 * service, or something else (e.g. a smart thermostat, iPhone app, etc.).<p>
 *
 * Authentication:
 * All methods described in this page require that a Master API Key is specified in the X-M2X-KEY header.
 * Any attempt of using a Distribution or Device level API key would result in a 403 Forbidden response. {@see <a href="https://m2x.att.com/developer/documentation/v2/overview#API-Keys">Learn more about API Keys.</a>}
 */
public class Device {

    public static final int REQUEST_CODE_SEARCH_PUBLIC_CATALOG = 1001;
    public static final int REQUEST_CODE_SEARCH_DEVICES = 1002;
    public static final int REQUEST_CODE_LIST_DEVICE_TAG = 1003;
    public static final int REQUEST_CODE_CREATE_DEVICE = 1004;
    public static final int REQUEST_CODE_DEVICE_DETAILS = 1005;
    public static final int REQUEST_CODE_DEVICE_LOCATION = 1006;
    public static final int REQUEST_CODE_DEVICE_UPDATE_LOCATION = 1007;
    public static final int REQUEST_CODE_DEVICE_LIST_DATA_STREAMS = 1008;
    public static final int REQUEST_CODE_DEVICE_CREATE_UPDATE_DATA_STREAMS = 1009;
    public static final int REQUEST_CODE_DEVICE_UPDATE_DATA_STREAM_VALUE = 1010;
    public static final int REQUEST_CODE_DEVICE_VIEW_DATA_STREAM = 1011;
    public static final int REQUEST_CODE_DEVICE_LIST_DATA_STREAM_VALUES = 1012;
    public static final int REQUEST_CODE_DEVICE_DATA_STREAM_SAMPLING = 1013;
    public static final int REQUEST_CODE_DEVICE_DATA_STREAM_STATS = 1014;
    public static final int REQUEST_CODE_DEVICE_POST_DATA_STREAM_VALUES = 1015;
    public static final int REQUEST_CODE_DEVICE_DELETE_DATA_STREAM_VALUES = 1016;
    public static final int REQUEST_CODE_DEVICE_DELETE_DATA_STREAM = 1017;
    public static final int REQUEST_CODE_DEVICE_POST_DEVICE_UPDATES = 1018;
    public static final int REQUEST_CODE_DEVICE_VIEW_REQUEST_LOG = 1025;
    public static final int REQUEST_CODE_DEVICE_DELETE = 1026;
    public static final int REQUEST_CODE_LIST_DEVICES = 1027;
    public static final int REQUEST_CODE_METADATA = 1028;
    public static final int REQUEST_CODE_UPDATE_METADATA = 1029;
    public static final int REQUEST_CODE_METADATA_FIELD = 1030;
    public static final int REQUEST_CODE_UPDATE_METADATA_FIELD = 1031;
    public static final int REQUEST_CODE_DEVICE_LOCATION_HISTORY = 1032;
    public static final int REQUEST_CODE_LIST_RECEIVED_COMMANDS = 1033;
    public static final int REQUEST_CODE_VIEW_COMMAND_DETAILS = 1034;
    public static final int REQUEST_CODE_MARK_COMMAND_PROCESSED = 1035;
    public static final int REQUEST_CODE_MARK_COMMAND_REJECTED = 1036;
    public static final int REQUEST_CODE_DEVICE_POST_DEVICE_UPDATE = 1037;
    public static final int REQUEST_CODE_DEVICE_EXPORT_VALUES = 1038;
    public static final int REQUEST_CODE_SEARCH_DATA_STREAM_VALUES = 1039;
    public static final int REQUEST_CODE_DEVICE_DELETE_LOCATION = 1040;

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Search-Public-Devices-Catalog">Search Public Devices Catalog</a>} endpoint.
     * @param context The application Context
     * @param params Query parameters as HashMap<String,String>. View M2X API Docs for listing of available parameters.
     * @param listener {@link ResponseListener}
     */
    public static final void searchPublicCatalog(Context context,HashMap<String,String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                Constants.DEVICE_SEARCH_PUBLIC_CATALOG,
                params,
                listener,
                REQUEST_CODE_SEARCH_PUBLIC_CATALOG
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#List-Devices">List Devices</a>} endpoint.
     * @param context The application Context
     * @param params Query parameters as HashMap<String,String>. View M2X API Docs for listing of available parameters.
     * @param listener {@link ResponseListener}
     */
    public static final void listDevices(Context context, HashMap<String,String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                Constants.DEVICE_LIST,
                params,
                listener,
                REQUEST_CODE_LIST_DEVICES
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Search-Devices">Search Devices</a>} endpoint
     * @param context The application Context.
     * @param body as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener {@link ResponseListener}
     */
    public static final void searchDevices(Context context, JSONObject body, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                Constants.DEVICE_SEARCH,
                body,
                listener,
                REQUEST_CODE_SEARCH_DEVICES
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#List-Device-Tags">List Device Tags</a>} endpoint.
     * @param context The application Context
     * @param listener {@link ResponseListener}
     */
    public static final void listDeviceTags(Context context, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                Constants.DEVICE_LIST_TAGS,
                null,
                listener,
                REQUEST_CODE_LIST_DEVICE_TAG
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Create-Device">Create Devices</a>} endpoint
     * @param context The application Context.
     * @param params as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener {@link ResponseListener}
     */
    public static final void createDevice(Context context,JSONObject params, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                Constants.DEVICE_CREATE,
                params,
                listener,
                REQUEST_CODE_CREATE_DEVICE
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Device-Details">Update Device Details</a>} endpoint.
     * @param context The application Context
     * @param params Query parameters as JSONObject. View M2X API Docs for listing of available parameters. 
     * @param deviceId Device ID of the device.
     * @param listener {@link ResponseListener}
     */
    public static final void updateDeviceDetails(Context context,JSONObject params,String deviceId, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_UPDATE_DETAILS,deviceId),
                params,
                listener,
                REQUEST_CODE_CREATE_DEVICE
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#View-Device-Details">View Device Details</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param listener {@link ResponseListener}
     */
    public static final void viewDeviceDetails(Context context,String deviceId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_VIEW_DETAILS,deviceId),
                null,
                listener,
                REQUEST_CODE_DEVICE_DETAILS
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Read-Device-Location">Read Device Location</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param listener {@link ResponseListener}
     */
    public static final void readDeviceLocation(Context context,String deviceId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_READ_LOCATION,deviceId),
                null,
                listener,
                REQUEST_CODE_DEVICE_LOCATION
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Read-Device-Location-History">Read Device Location History</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param params Query parameters as HashMap<String,String>. View M2X API Docs for listing of available parameters.
     * @param listener {@link ResponseListener}
     */
    public static final void readDeviceLocationHistory(Context context, String deviceId, HashMap<String,String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_READ_LOCATION_HISTORY, deviceId),
                params,
                listener,
                REQUEST_CODE_DEVICE_LOCATION_HISTORY
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Device-Location">Update Device Location</a>} endpoint.
     * @param context The application Context.
     * @param params as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param deviceId as String, ID of the device.
     * @param listener {@link ResponseListener}
     */
    public static final void updateDeviceLocation(Context context,JSONObject params,String deviceId, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_UPDATE_LOCATION, deviceId),
                params,
                listener,
                REQUEST_CODE_DEVICE_UPDATE_LOCATION
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Read-Device-Metadata">Read Device Metadata</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param listener {@link ResponseListener}
     */
    public static final void metadata(Context context, String deviceId, ResponseListener listener){
        Metadata.metadata(
                context,
                String.format(Locale.US, Constants.DEVICE_METADATA, deviceId),
                listener,
                REQUEST_CODE_METADATA);
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Device-Metadata">Update Device Metadata</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param body as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener {@link ResponseListener}
     */
    public static final void updateMetadata(Context context, String deviceId, JSONObject body, ResponseListener listener){
        Metadata.updateMetadata(
                context,
                String.format(Locale.US, Constants.DEVICE_METADATA, deviceId),
                body,
                listener,
                REQUEST_CODE_UPDATE_METADATA);
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Read-Device-Metadata-Field">Read Device Metadata Field</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param field as String
     * @param listener {@link ResponseListener}
     */
    public static final void metadataField(Context context, String deviceId, String field, ResponseListener listener){
        Metadata.metadataField(
                context,
                String.format(Locale.US, Constants.DEVICE_METADATA_FIELD, deviceId, field),
                listener,
                REQUEST_CODE_METADATA_FIELD);
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Device-Metadata">Update Device Metadata</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param field as String
     * @param body as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener {@link ResponseListener}
     */
    public static final void updateMetadataField(Context context, String deviceId, String field, JSONObject body, ResponseListener listener){
        Metadata.updateMetadataField(
                context,
                String.format(Locale.US, Constants.DEVICE_METADATA_FIELD, deviceId, field),
                body,
                listener,
                REQUEST_CODE_UPDATE_METADATA_FIELD);
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#List-Data-Streams">List Data Streams</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param listener {@link ResponseListener}
     */
    public static final void listDataStreams(Context context,String deviceId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_LIST_DATA_STREAMS, deviceId),
                null,
                listener,
                REQUEST_CODE_DEVICE_LIST_DATA_STREAMS
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Create-Update-Data-Stream">Create/Update Data Stream</a>} endpoint.
     * @param context The application Context.
     * @param params as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param deviceId as String, ID of the device.
     * @param name as String
     * @param listener {@link ResponseListener}
     */
    public static final void createUpdateDataStreams(Context context,JSONObject params,String deviceId,String name, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_CREATE_UPDATE_DATA_STREAMS, deviceId,name),
                params,
                listener,
                REQUEST_CODE_DEVICE_CREATE_UPDATE_DATA_STREAMS
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Data-Stream-Value">Update Data Stream Value</a>} endpoint.
     * @param context The application Context.
     * @param params as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param deviceId as String, ID of the device.
     * @param name as String
     * @param listener {@link ResponseListener}
     */
    public static final void updateDataStreamValue(Context context,JSONObject params,String deviceId,String name, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_UPDATE_DATA_STREAM_VALUE, deviceId,name),
                params,
                listener,
                REQUEST_CODE_DEVICE_UPDATE_DATA_STREAM_VALUE
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#View-Data-Stream">View Data Stream</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param name as String
     * @param listener {@link ResponseListener}
     */
    public static final void viewDataStream(Context context,String deviceId,String name, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_VIEW_DATA_STREAM, deviceId,name),
                null,
                listener,
                REQUEST_CODE_DEVICE_VIEW_DATA_STREAM
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#List-Data-Stream-Values">List Data Stream Values</a>} endpoint.
     * @param context The application Context.
     * @param params Query parameters as HashMap<String,String>. View M2X API Docs for listing of available parameters.
     * @param deviceId as String, ID of the device.
     * @param name as String
     * @param listener {@link ResponseListener}
     */
    public static final void listDataStreamValues(Context context,HashMap<String,String> params,String deviceId,String name, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_LIST_DATA_STREAM_VALUES, deviceId,name),
                params,
                listener,
                REQUEST_CODE_DEVICE_LIST_DATA_STREAM_VALUES
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Data-Stream-Sampling">Data Stream Sampling</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param name as String
     * @param listener {@link ResponseListener}
     */
    public static final void dataStreamSampling(Context context,String deviceId,String name, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_LIST_DATA_STREAM_SAMPLING, deviceId,name),
                null,
                listener,
                REQUEST_CODE_DEVICE_DATA_STREAM_SAMPLING
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Data-Stream-Stats">Data Stream Stats</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param name as String
     * @param listener {@link ResponseListener}
     */
    public static final void dataStreamStats(Context context,String deviceId,String name, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_LIST_DATA_STREAM_STATS, deviceId,name),
                null,
                listener,
                REQUEST_CODE_DEVICE_DATA_STREAM_STATS
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Search-Values-from-all-Data-Streams-of-a-Device">Delete Data Stream</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param format as String
     * @param body as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener {@link ResponseListener}
     */
    public static final void searchDataStreamValues(Context context, String deviceId, String format, JSONObject body, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_SEARCH_DATA_STREAM_VALUES, deviceId, format),
                body,
                listener,
                REQUEST_CODE_SEARCH_DATA_STREAM_VALUES
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Data-Stream-Value">Update Data Stream Value</a>} endpoint.
     * @param context The application Context.
     * @param params as JSON Object View M2X API Docs for listing of available body parameters.
     * @param deviceId as String, ID of the device.
     * @param name as String
     * @param listener {@link ResponseListener}
     */
    public static final void postDataStreamValues(Context context,JSONObject params,String deviceId, String name, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_POST_DATA_STREAM_VALUES,deviceId,name),
                params,
                listener,
                REQUEST_CODE_DEVICE_POST_DATA_STREAM_VALUES
        );
    }


    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Export-Values-from-all-Data-Streams-of-a-Device">Export Values from all Data Streams of a Device</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param params Query parameters as HashMap<String,String>. View M2X API Docs for listing of available parameters.
     * @param listener {@link ResponseListener}
     */
    public static final void exportValues(Context context, String deviceId, HashMap<String, String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_EXPORT_VALUES, deviceId),
                params,
                listener,
                REQUEST_CODE_DEVICE_EXPORT_VALUES
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Delete-Data-Stream-Values">Delete Data Stream Values</a>} endpoint.
     * @param context The application Context.
     * @param params as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param deviceId as String, ID of the device.
     * @param name as String
     * @param listener {@link ResponseListener}
     */
    public static final void deleteDataStreamValues(Context context,JSONObject params,String deviceId, String name, ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_DELETE_DATA_STREAM_VALUES,deviceId,name),
                params,
                listener,
                REQUEST_CODE_DEVICE_DELETE_DATA_STREAM_VALUES
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Delete-Data-Stream">Delete Data Stream</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param name as String
     * @param listener {@link ResponseListener}
     */
    public static final void deleteDataStream(Context context,String deviceId, String name, ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_DELETE_DATA_STREAM,deviceId,name),
                null,
                listener,
                REQUEST_CODE_DEVICE_DELETE_DATA_STREAM
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Delete-Location-History">Delete Location History</a>} endpoint.
     * @param context The application Context.
     * @param params as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param deviceId as String, ID of the device.
     * @param listener {@link ResponseListener}
     */
    public static final void deleteDeviceLocation(Context context,JSONObject params,String deviceId, ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_DELETE_LOCATION, deviceId),
                params,
                listener,
                REQUEST_CODE_DEVICE_DELETE_LOCATION
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Post-Device-Update--Single-Values-to-Multiple-Streams-">Post Device Update</a>} endpoint.
     * @param context The application Context.
     * @param params as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param deviceId as String, ID of the device.
     * @param listener {@link ResponseListener}
     */
    public static final void postDeviceUpdate(Context context, JSONObject params, String deviceId, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_POST_UPDATE, deviceId),
                params,
                listener,
                REQUEST_CODE_DEVICE_POST_DEVICE_UPDATE
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Post-Device-Updates--Multiple-Values-to-Multiple-Streams-">Post Device Updates (Multiple Values to Multiple Streams)</a>} endpoint.
     * @param context The application Context.
     * @param params as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param deviceId as String, ID of the device.
     * @param listener {@link ResponseListener}
     */
    public static final void postDeviceUpdates(Context context,JSONObject params,String deviceId, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_POST_UPDATES,deviceId),
                params,
                listener,
                REQUEST_CODE_DEVICE_POST_DEVICE_UPDATES
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#View-Request-Log">View Request Log</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param listener {@link ResponseListener}
     */
    public static final void viewRequestLog(Context context,String deviceId,ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_REQUEST_LOG, deviceId),
                null,
                listener,
                REQUEST_CODE_DEVICE_VIEW_REQUEST_LOG
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Delete-Device">Delete Device</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param listener {@link ResponseListener}
     */
    public static final void deleteDevice(Context context,String deviceId,ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_DELETE,deviceId),
                null,
                listener,
                REQUEST_CODE_DEVICE_DELETE
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#List-Devices">Device's List of Received Commands</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param params Query parameters as HashMap<String,String>. View M2X API Docs for listing of available parameters.
     * @param listener {@link ResponseListener}
     */
    public static final void listCommands(Context context, String deviceId, HashMap<String, String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_LIST_RECEIVED_COMMANDS, deviceId),
                params,
                listener,
                REQUEST_CODE_LIST_RECEIVED_COMMANDS
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/commands#Device-s-View-of-Command-Details">Device's View of Command Details</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param commandId as String, ID of the Command.
     * @param listener {@link ResponseListener}
     */
    public static final void viewCommand(Context context, String deviceId, String commandId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_VIEW_COMMAND_DETAILS, deviceId, commandId),
                null,
                listener,
                REQUEST_CODE_VIEW_COMMAND_DETAILS
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/commands#Device-Marks-a-Command-as-Processed">Device Marks a Command as Processed</a>} endpoint
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param commandId as String, ID of the Command.
     * @param body as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener {@link ResponseListener}
     */
    public static final void processCommand(Context context, String deviceId, String commandId, JSONObject body, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_MARK_COMMAND_PROCESSED, deviceId, commandId),
                body,
                listener,
                REQUEST_CODE_MARK_COMMAND_PROCESSED
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/commands#Device-Marks-a-Command-as-Rejected">Device Marks a Command as Rejected</a>} endpoint
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param commandId as String, ID of the Command.
     * @param body as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener {@link ResponseListener}
     */
    public static final void rejectCommand(Context context, String deviceId, String commandId,  JSONObject body, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_MARK_COMMAND_REJECTED, deviceId, commandId),
                body,
                listener,
                REQUEST_CODE_MARK_COMMAND_REJECTED
        );
    }

}

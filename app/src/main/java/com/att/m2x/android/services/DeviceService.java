package com.att.m2x.android.services;

import android.content.Context;
import android.util.Log;

import com.att.m2x.android.common.Constants;
import com.att.m2x.android.exceptions.M2XApiException;
import com.att.m2x.android.listeners.ResponseListener;
import com.att.m2x.android.listeners.TypedResponseListener;
import com.att.m2x.android.model.Command;
import com.att.m2x.android.model.Stream;
import com.att.m2x.android.services.model.StreamSamplingOptions;
import com.att.m2x.android.model.StreamStats;
import com.att.m2x.android.services.model.StreamStatsOptions;
import com.att.m2x.android.model.StreamValue;
import com.att.m2x.android.services.model.StreamValueSearch;
import com.att.m2x.android.services.model.StreamValues;
import com.att.m2x.android.model.Device;
import com.att.m2x.android.services.model.DeviceCommand;
import com.att.m2x.android.services.model.DeviceRequest;
import com.att.m2x.android.services.model.DeviceUpdate;
import com.att.m2x.android.services.model.DeviceUpdates;
import com.att.m2x.android.services.model.ExportOptions;
import com.att.m2x.android.services.model.ListDevicesOptions;
import com.att.m2x.android.services.model.LocationResult;
import com.att.m2x.android.services.model.ListStreamValuesOptions;
import com.att.m2x.android.services.model.ListDeviceCommandsOptions;
import com.att.m2x.android.model.Location;
import com.att.m2x.android.model.Response;
import com.att.m2x.android.network.ApiV2Response;
import com.att.m2x.android.network.JsonRequest;
import com.att.m2x.android.services.model.DeviceSearch;
import com.att.m2x.android.services.model.MultiStreamValues;
import com.att.m2x.android.services.model.PublicCatalogSearch;
import com.att.m2x.android.utils.ArrayUtils;
import com.att.m2x.android.utils.DateUtils;
import com.att.m2x.android.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.att.m2x.android.services.BaseResponseListener.*;

/**
 * The Device API allows you to interact with your devices (including creating new devices,
 * updating devices, pushing data to a device stream, updating a device location, etc.) as well as
 * public devices that belong to other M2X accounts. A Device is any individual generator of data
 * that can be connected to M2X. This could be a physical device, application,
 * service, or something else (e.g. a smart thermostat, iPhone app, etc.).
 * {@see <a href="https://m2x.att.com/developer/documentation/v2/devices">M2X Device REST API</a>}
 *
 * Authentication:
 * All methods described in this page require that a Master API Key is specified in the X-M2X-KEY header.
 * Any attempt of using a Distribution or Device level API key would result in a 403 Forbidden response. {@see <a href="https://m2x.att.com/developer/documentation/v2/overview#API-Keys">Learn more about API Keys.</a>}
 */
public class DeviceService {

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
    public static final int REQUEST_CODE_DEVICE_DELETE_LOCATION_HISTORY = 1041;

    private DeviceService() {
        // don't instantiate
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#List-Public-Devices-Catalog">Search Public Catalog</a> endpoint.
     * @param context
     * @param search
     * @param listener
     */
    public static void searchPublicCatalog(Context context, PublicCatalogSearch search,
                                           TypedResponseListener<List<Device>> listener) {
        JsonRequest.makeGetRequest(context,
                Constants.DEVICE_SEARCH_PUBLIC_CATALOG,
                search.getParams(),
                search.getBody(),
                new DeviceListResponseListener(listener),
                REQUEST_CODE_SEARCH_PUBLIC_CATALOG
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#List-Public-Devices-Catalog">Search Public Catalog</a> endpoint.
     * @param context
     * @param params
     * @param listener
     */
    public static void searchPublicCatalog(Context context, Map<String,String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                Constants.DEVICE_SEARCH_PUBLIC_CATALOG,
                params,
                new JSONObject(),
                listener,
                REQUEST_CODE_SEARCH_PUBLIC_CATALOG
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#List-Devices">List Devices</a> endpoint
     * @param context
     * @param options
     * @param listener
     */
    public static void listDevices(Context context, ListDevicesOptions options,
                                   TypedResponseListener<List<Device>> listener) {

        listDevices(context, options.toMap(), new DeviceListResponseListener(listener));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#List-Devices">List Devices</a> endpoint
     * @param context
     * @param params
     * @param listener
     */
    public static void listDevices(Context context, Map<String, String> params,
                                   ResponseListener listener) {
        JsonRequest.makeGetRequest(
                context,
                Constants.DEVICES,
                params,
                listener,
                REQUEST_CODE_LIST_DEVICES
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Search-Devices">Search Devices</a> endpoint
     * @param context
     * @param deviceSearch
     * @param listener
     */
    public static void searchDevices(final Context context, final DeviceSearch deviceSearch,
                                     final TypedResponseListener<List<Device>> listener){
        searchDevices(context, deviceSearch.toJsonObject(), new DeviceListResponseListener(listener));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Search-Devices">Search Devices</a> endpoint
     * @param context
     * @param body
     * @param listener
     */
    public static void searchDevices(final Context context,
                                           final JSONObject body,
                                           final ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                Constants.DEVICE_SEARCH,
                body,
                listener,
                REQUEST_CODE_SEARCH_DEVICES
        );
    }


    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#List-Device-Tags">List Device Tags</a> endpoint
     * @param context
     * @param listener
     */
    public static void listDeviceTags(Context context,
                                      TypedResponseListener<Map<String, Integer>> listener) {
        listDeviceTags(context, new TagsResponseListener(listener));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#List-Device-Tags">List Device Tags</a> endpoint
     * @param context
     * @param listener
     */
    public static void listDeviceTags(Context context, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                Constants.DEVICE_LIST_TAGS,
                null,
                listener,
                REQUEST_CODE_LIST_DEVICE_TAG
        );
    }


    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Create-Device">Create Device</a> endpoint
     * @param context
     * @param device as {@link Device}
     * @param listener {@link TypedResponseListener<Device>}
     */
    public static void createDevice(final Context context, final Device device, final TypedResponseListener<Device> listener){
        createDevice(context, device.toJsonObject(), new DeviceResponseListener(listener));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Create-Device">Create Device</a> endpoint
     * @param context
     * @param params
     * @param listener
     */
    public static void createDevice(final Context context, final JSONObject params, final ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                Constants.DEVICES,
                params,
                listener,
                REQUEST_CODE_CREATE_DEVICE
        );
    }


    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Device-Location">Update Device Location</a> endpoint.
     * @param context
     * @param device
     * @param location
     * @param listener
     */
    public static void updateDeviceLocation(final Context context, final Device device,
                                            final Location location,
                                            final TypedResponseListener<Void> listener) {

        if (StringUtils.hasText(device.getId())) {
            updateDeviceLocation(context, location.toJsonObject(), device.getId(), new EmptyResponseListener(listener));
        }
        else if (StringUtils.hasText(device.getSerial())) {
            updateDeviceLocationSerial(context, location.toJsonObject(), device.getSerial(), new EmptyResponseListener(listener));
        }
        else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Device-Location">Update Device Location</a> endpoint.
     * @param context
     * @param body
     * @param deviceId
     * @param listener
     */
    public static void updateDeviceLocation(Context context, JSONObject body,
                                            String deviceId, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_LOCATION, deviceId),
                body,
                listener,
                REQUEST_CODE_DEVICE_UPDATE_LOCATION
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Device-Location">Update Device Location</a> endpoint.
     * @param context
     * @param body
     * @param deviceSerial
     * @param listener
     */
    public static void updateDeviceLocationSerial(Context context, JSONObject body,
                                                  String deviceSerial,
                                                  ResponseListener listener) {
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_LOCATION_SERIAL, deviceSerial),
                body,
                listener,
                REQUEST_CODE_DEVICE_UPDATE_LOCATION
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Device-Details">Update Device Details</a> endpoint.
     * @param context
     * @param device
     * @param listener
     */
    public static void updateDeviceDetails(Context context, Device device,
                                           TypedResponseListener<Void> listener) {
        if (device.getId() != null) {
            updateDeviceDetails(context, device.toJsonObject(), device.getId(), new EmptyResponseListener(listener));
        } else if (device.getSerial() != null) {
            updateDeviceDetailsSerial(context, device.toJsonObject(), device.getSerial(), new EmptyResponseListener(listener));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Device-Details">Update Device Details</a> endpoint.
     * @param context
     * @param deviceId
     * @param listener
     */
    public static void updateDeviceDetails(Context context, JSONObject params, String deviceId,
                                           ResponseListener listener) {
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US,Constants.DEVICE, deviceId),
                params,
                listener,
                REQUEST_CODE_CREATE_DEVICE
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Device-Details">Update Device Details</a> endpoint.
     * @param context
     * @param deviceSerial
     * @param listener
     */
    public static void updateDeviceDetailsSerial(Context context, JSONObject params,
                                                 String deviceSerial, ResponseListener listener) {
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_SERIAL, deviceSerial),
                params,
                listener,
                REQUEST_CODE_CREATE_DEVICE
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#View-Device-Details">View Device Details</a> endpoint.
     * @param context
     * @param device
     * @param listener
     */
    public static void viewDeviceDetails(Context context, Device device,
                                         TypedResponseListener<Device> listener) {
        String url;
        if (StringUtils.hasText(device.getId())) {
            viewDeviceDetails(context, device.getId(), new DeviceResponseListener(listener));
        } else if (StringUtils.hasText((device.getSerial()))) {
            viewDeviceDetailsSerial(context, device.getSerial(), new DeviceResponseListener(listener));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#View-Device-Details">View Device Details</a> endpoint.
     * @param context
     * @param deviceId
     * @param listener
     */
    public static void viewDeviceDetails(Context context, String deviceId, ResponseListener listener) {
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US,Constants.DEVICE, deviceId),
                null,
                listener,
                REQUEST_CODE_DEVICE_DETAILS
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#View-Device-Details">View Device Details</a> endpoint.
     * @param context
     * @param deviceSerial
     * @param listener
     */
    public static void viewDeviceDetailsSerial(Context context, String deviceSerial,
                                               ResponseListener listener) {
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_SERIAL, deviceSerial),
                null,
                listener,
                REQUEST_CODE_DEVICE_DETAILS
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Read-Device-Location">Read Device Location</a> endpoint
     * @param context
     * @param device
     * @param listener
     */
    public static void readDeviceLocation(Context context, Device device, TypedResponseListener<Location> listener) {
        if (StringUtils.hasText(device.getId())) {
            readDeviceLocation(context, device.getId(), new LocationResponseListener(listener));
        } else if (StringUtils.hasText(device.getSerial())) {
            readDeviceLocationSerial(context, device.getSerial(), new LocationResponseListener(listener));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Read-Device-Location">Read Device Location</a> endpoint
     * @param context
     * @param deviceId
     * @param listener
     */
    public static void readDeviceLocation(Context context,String deviceId, ResponseListener listener) {
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_LOCATION, deviceId),
                null,
                listener,
                REQUEST_CODE_DEVICE_LOCATION
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Read-Device-Location">Read Device Location</a> endpoint
     * @param context
     * @param deviceSerial
     * @param listener
     */
    public static void readDeviceLocationSerial(Context context, String deviceSerial, ResponseListener listener) {
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_LOCATION_SERIAL, deviceSerial),
                null,
                listener,
                REQUEST_CODE_DEVICE_LOCATION
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Read-Device-Location-History">Read Device Location History</a> endpoint
     * @param context
     * @param device
     * @param length
     */
    public static void readDeviceLocationHistory(Context context, Device device, Integer length,
                                                 TypedResponseListener<List<Location>> listener){
        Map<String, String> params = null;
        if (length != null) {
            params = new HashMap<>();
            params.put("length", length.toString());
        }

        if (StringUtils.hasText(device.getId())) {
            readDeviceLocationHistory(context, device.getId(), params, new LocationHistoryResponseListener(listener));
        } else if (StringUtils.hasText(device.getSerial())) {
            readDeviceLocationHistorySerial(context, device.getSerial(), params, new LocationHistoryResponseListener(listener));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Read-Device-Location-History">Read Device Location History</a> endpoint
     * @param context
     * @param deviceId
     * @param params
     */
    public static void readDeviceLocationHistory(Context context, String deviceId,
                                                 Map<String,String> params,
                                                 ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_LOCATION_HISTORY, deviceId),
                params,
                listener,
                REQUEST_CODE_DEVICE_LOCATION_HISTORY
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Read-Device-Location-History">Read Device Location History</a> endpoint
     * @param context
     * @param deviceSerial
     * @param params
     */
    public static void readDeviceLocationHistorySerial(Context context, String deviceSerial,
                                                       Map<String,String> params,
                                                       ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_LOCATION_HISTORY_SERIAL, deviceSerial),
                params,
                listener,
                REQUEST_CODE_DEVICE_LOCATION_HISTORY
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Delete-Location-History">Delete Location History</a> endpoint
     * @param context
     * @param device
     * @param from
     * @param end
     * @param listener
     */
    public static void deleteDeviceLocationHistory(Context context, Device device, Date from,
                                                   Date end, TypedResponseListener<Void> listener) {
        Map<String, String> params = new HashMap<>();
        if (from != null) {
            params.put("from", DateUtils.dateTimeToString(from));
        }
        if (end != null) {
            params.put("end", DateUtils.dateTimeToString(end));
        }
        if (StringUtils.hasText(device.getId())) {
            deleteDeviceLocationHistory(context, device.getId(), params, new EmptyResponseListener(listener));
        } else if (StringUtils.hasText(device.getSerial())) {
            deleteDeviceLocationHistorySerial(context, device.getSerial(), params, new EmptyResponseListener(listener));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Delete-Location-History">Delete Location History</a> endpoint
     * @param context
     * @param deviceId
     * @param params
     * @param listener
     */
    public static void deleteDeviceLocationHistory(Context context, String deviceId,
                                                   Map<String, String> params,
                                                   ResponseListener listener) {
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_LOCATION_HISTORY, deviceId),
                params,
                listener,
                REQUEST_CODE_DEVICE_DELETE_LOCATION_HISTORY
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Delete-Location-History">Delete Location History</a> endpoint
     * @param context
     * @param deviceSerial
     * @param params
     * @param listener
     */
    public static void deleteDeviceLocationHistorySerial(Context context, String deviceSerial,
                                                         Map<String, String> params,
                                                         ResponseListener listener) {
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_LOCATION_HISTORY_SERIAL, deviceSerial),
                params,
                listener,
                REQUEST_CODE_DEVICE_DELETE_LOCATION_HISTORY
        );
    }


    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Device-Location"Read Device Metadata</a> endpoint
     * @param context
     * @param device
     * @param listener
     */
    public static void readDeviceMetadata(Context context, Device device,
                                          TypedResponseListener<Map<String, String>> listener){
        if (StringUtils.hasText(device.getId())) {
            readDeviceMetadata(context, device.getId(), new MapResponseListener(listener));
        } else if (StringUtils.hasText(device.getSerial())) {
            readDeviceMetadataSerial(context, device.getSerial(), new MapResponseListener(listener));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Device-Location"Read Device Metadata</a> endpoint
     * @param context
     * @param deviceId
     * @param listener
     */
    public static void readDeviceMetadata(Context context, String deviceId,
                                          ResponseListener listener) {

        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_METADATA, deviceId),
                null,
                listener,
                REQUEST_CODE_METADATA
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Device-Location"Read Device Metadata</a> endpoint
     * @param context
     * @param deviceSerial
     * @param listener
     */
    public static void readDeviceMetadataSerial(Context context, String deviceSerial,
                                                ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_METADATA_SERIAL, deviceSerial),
                null,
                listener,
                REQUEST_CODE_METADATA
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Device-Location"Read Device Metadata</a> endpoint
     * @param context
     * @param device
     * @param listener
     */
    public static void updateDeviceMetadata(Context context, Device device,
                                            TypedResponseListener<Void> listener) {

        JSONObject body;
        try {
            body = ArrayUtils.mapToJsonObject(device.getMetadata());
        } catch (JSONException e) {
            listener.onRequestException(new M2XApiException(e));
            return;
        }

        if (StringUtils.hasText(device.getId())) {
            updateDeviceMetadata(context, device.getId(), body, new EmptyResponseListener(listener));
        } else if (StringUtils.hasText(device.getSerial())) {
            updateDeviceMetadataSerial(context, device.getSerial(), body, new EmptyResponseListener(listener));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Device-Location"Read Device Metadata</a> endpoint
     * @param context
     * @param deviceId
     * @param listener
     */
    public static void updateDeviceMetadata(Context context, String deviceId, JSONObject metadata,
                                            ResponseListener listener) {
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_METADATA, deviceId),
                metadata,
                listener,
                REQUEST_CODE_UPDATE_METADATA
        );
    }
    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Device-Location"Read Device Metadata</a> endpoint
     * @param context
     * @param deviceSerial
     * @param listener
     */
    public static void updateDeviceMetadataSerial(Context context, String deviceSerial,
                                                  JSONObject metadata, ResponseListener listener) {
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_METADATA_SERIAL, deviceSerial),
                metadata,
                listener,
                REQUEST_CODE_UPDATE_METADATA
        );
    }


    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Read-Device-Metadata-Field">Read Device Metadata Field</a> endpoint
     * @param context
     * @param device
     * @param field
     * @param listener
     */
    public static void readDeviceMetadataField(Context context, Device device, String field,
                                               TypedResponseListener<String> listener) {
        if (StringUtils.hasText(device.getId())) {
            readDeviceMetadataField(context, device.getId(), field, new StringResponseListener(listener, "value"));
        } else if (StringUtils.hasText(device.getSerial())) {
            readDeviceMetadataFieldSerial(context, device.getSerial(), field, new StringResponseListener(listener, "value"));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Read-Device-Metadata-Field">Read Device Metadata Field</a> endpoint
     * @param context
     * @param deviceId
     * @param field
     * @param listener
     */
    public static void readDeviceMetadataField(Context context, String deviceId, String field,
                                               ResponseListener listener) {
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_METADATA_FIELD, deviceId, field),
                null,
                listener,
                REQUEST_CODE_METADATA_FIELD);
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Read-Device-Metadata-Field">Read Device Metadata Field</a> endpoint
     * @param context
     * @param deviceSerial
     * @param field
     * @param listener
     */
    public static void readDeviceMetadataFieldSerial(Context context, String deviceSerial, String field,
                                                     ResponseListener listener) {
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_METADATA_FIELD_SERIAL, deviceSerial, field),
                null,
                listener,
                REQUEST_CODE_METADATA_FIELD);
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Device-Metadata-Field">Read Device Metadata Field</a> endpoint
     * @param context
     * @param device
     * @param field
     * @param listener
     */
    public static void updateDeviceMetadataField(Context context, Device device, String field,
                                                 String value,
                                                 TypedResponseListener<Void> listener) {
        JSONObject body;
        try {
            body = ArrayUtils.mapToJsonObject(Collections.singletonMap("value", value));
        } catch (JSONException e) {
            listener.onRequestException(new M2XApiException(e));
            return;
        }

        if (StringUtils.hasText(device.getId())) {
            updateDeviceMetadataField(context, device.getId(), field, body, new EmptyResponseListener(listener));
        } else if (StringUtils.hasText(device.getSerial())) {
            updateDeviceMetadataFieldSerial(context, device.getSerial(), field, body, new EmptyResponseListener(listener));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Device-Metadata-Field">Read Device Metadata Field</a> endpoint
     * @param context
     * @param deviceId
     * @param field
     * @param listener
     */
    public static void updateDeviceMetadataField(Context context, String deviceId, String field,
                                                 JSONObject body, ResponseListener listener) {
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_METADATA_FIELD, deviceId, field),
                body,
                listener,
                REQUEST_CODE_UPDATE_METADATA_FIELD);
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Device-Metadata-Field">Read Device Metadata Field</a> endpoint
     * @param context
     * @param deviceSerial
     * @param field
     * @param listener
     */
    public static void updateDeviceMetadataFieldSerial(Context context, String deviceSerial, String field,
                                                     JSONObject body, ResponseListener listener) {
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_METADATA_FIELD_SERIAL, deviceSerial, field),
                body,
                listener,
                REQUEST_CODE_UPDATE_METADATA_FIELD);
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#List-Data-Streams">List Data Streams</a> endpoint
     * @param context
     * @param device
     * @param listener
     */
    public static void listDataStreams(Context context, Device device,
                                       TypedResponseListener<List<Stream>> listener) {
        if (StringUtils.hasText(device.getId())) {
            listDataStreams(context, device.getId(), new StreamListResponseListener(listener));
        } else if (StringUtils.hasText(device.getSerial())) {
            listDataStreamsSerial(context, device.getSerial(), new StreamListResponseListener(listener));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#List-Data-Streams">List Data Streams</a> endpoint
     * @param context
     * @param deviceId
     * @param listener
     */
    public static void listDataStreams(Context context,String deviceId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_DATA_STREAMS, deviceId),
                null,
                listener,
                REQUEST_CODE_DEVICE_LIST_DATA_STREAMS
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#List-Data-Streams">List Data Streams</a> endpoint
     * @param context
     * @param deviceSerial
     * @param listener
     */
    public static void listDataStreamsSerial(Context context, String deviceSerial, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_DATA_STREAMS_SERIAL, deviceSerial),
                null,
                listener,
                REQUEST_CODE_DEVICE_LIST_DATA_STREAMS
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Create-Update-Data-Stream>Create/Update Data Stream</a>
     * @param context
     * @param device
     * @param stream
     * @param listener
     */
    public static void createUpdateDataStream(Context context, Device device, Stream stream,
                                              TypedResponseListener<Void> listener) {
        if (StringUtils.hasText(device.getId())) {
            createUpdateDataStream(context, stream.toJsonObject(), device.getId(),
                    stream.getName(), new EmptyResponseListener(listener));
        } else if (StringUtils.hasText(device.getSerial())) {
            createUpdateDataStreamSerial(context, stream.toJsonObject(), device.getSerial(),
                    stream.getName(), new EmptyResponseListener(listener));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Create-Update-Data-Stream>Create/Update Data Stream</a>
     * @param context
     * @param body
     * @param deviceId
     * @param name
     * @param listener
     */
    public static void createUpdateDataStream(Context context, JSONObject body, String deviceId,
                                              String name, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_DATA_STREAM, deviceId, name),
                body,
                listener,
                REQUEST_CODE_DEVICE_CREATE_UPDATE_DATA_STREAMS
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Create-Update-Data-Stream>Create/Update Data Stream</a>
     * @param context
     * @param body
     * @param deviceSerial
     * @param name
     * @param listener
     */
    public static void createUpdateDataStreamSerial(Context context, JSONObject body,
                                                    String deviceSerial, String name,
                                                    ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_DATA_STREAM_SERIAL, deviceSerial, name),
                body,
                listener,
                REQUEST_CODE_DEVICE_CREATE_UPDATE_DATA_STREAMS
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Create-Update-Data-Stream>Create/Update Data Stream</a>
     * @param context
     * @param device
     * @param streams
     * @param listener
     */
    public static void createUpdateDataStreams(Context context, Device device,
                                               List<Stream> streams,
                                               TypedResponseListener<List<Stream>> listener) {

        JSONObject body = new JSONObject();
        JSONArray streamsArray = new JSONArray();
        try {
            for (Stream stream : streams) {
                streamsArray.put(stream.toJsonObject());
            }
            body.put("streams", streamsArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (StringUtils.hasText(device.getId())) {
            createUpdateDataStreams(context, body, device.getId(), new CreateStreamsResponseHandler(listener));
        } else if (StringUtils.hasText(device.getSerial())) {
            createUpdateDataStreamsSerial(context, body, device.getSerial(), new CreateStreamsResponseHandler(listener));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Create-Update-Data-Stream>Create/Update Data Stream</a>
     * @param context
     * @param body
     * @param deviceId
     * @param listener
     */
    public static void createUpdateDataStreams(Context context, JSONObject body, String deviceId,
                                               ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_DATA_STREAMS, deviceId),
                body,
                listener,
                REQUEST_CODE_DEVICE_CREATE_UPDATE_DATA_STREAMS
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Create-Update-Data-Stream>Create/Update Data Stream</a>
     * @param context
     * @param body
     * @param deviceSerial
     * @param listener
     */
    public static void createUpdateDataStreamsSerial(Context context, JSONObject body,
                                                     String deviceSerial,
                                                     ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_DATA_STREAMS_SERIAL, deviceSerial),
                body,
                listener,
                REQUEST_CODE_DEVICE_CREATE_UPDATE_DATA_STREAMS
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Data-Stream-Value">Update Data Stream Value</a> endpoint
     * @param context
     * @param device
     * @param stream
     * @param value
     * @param listener
     */
    public static void updateDataStreamValue(Context context, Device device, Stream stream,
                                             StreamValue value, TypedResponseListener<Void> listener) {
        if (StringUtils.hasText(device.getId())) {
            updateDataStreamValue(context, value.toJsonObject(), device.getId(), stream.getName(), new EmptyResponseListener(listener));
        } else if (StringUtils.hasText(device.getSerial())) {
            updateDataStreamValueSerial(context, value.toJsonObject(), device.getSerial(), stream.getName(), new EmptyResponseListener(listener));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Data-Stream-Value">Update Data Stream Value</a> endpoint
     * @param context
     * @param body
     * @param deviceId
     * @param name
     * @param listener
     */
    public static void updateDataStreamValue(Context context,JSONObject body, String deviceId ,String name, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_DATA_STREAM_VALUE, deviceId, name),
                body,
                listener,
                REQUEST_CODE_DEVICE_UPDATE_DATA_STREAM_VALUE
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Data-Stream-Value">Update Data Stream Value</a> endpoint
     * @param context
     * @param body
     * @param deviceSerial
     * @param name
     * @param listener
     */
    public static void updateDataStreamValueSerial(Context context, JSONObject body, String deviceSerial, String name, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_DATA_STREAM_VALUE_SERIAL, deviceSerial, name),
                body,
                listener,
                REQUEST_CODE_DEVICE_UPDATE_DATA_STREAM_VALUE
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#View-Data-Stream">View Data Stream</a> endpoint
     * @param context
     * @param device
     * @param name
     * @param listener
     */
    public static void viewDataStream(Context context, Device device, String name,
                                      TypedResponseListener<Stream> listener) {
        if (StringUtils.hasText(device.getId())) {
            viewDataStream(context, device.getId(), name, new StreamResponseListener(listener));
        } else if (StringUtils.hasText(device.getSerial())) {
            viewDataStreamSerial(context, device.getSerial(), name, new StreamResponseListener(listener));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#View-Data-Stream">View Data Stream</a> endpoint
     * @param context
     * @param deviceId
     * @param name
     * @param listener
     */
    public static void viewDataStream(Context context, String deviceId, String name, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_DATA_STREAM, deviceId,name),
                null,
                listener,
                REQUEST_CODE_DEVICE_VIEW_DATA_STREAM
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#View-Data-Stream">View Data Stream</a> endpoint
     * @param context
     * @param deviceSerial
     * @param name
     * @param listener
     */
    public static void viewDataStreamSerial(Context context, String deviceSerial, String name, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_DATA_STREAM_SERIAL, deviceSerial,name),
                null,
                listener,
                REQUEST_CODE_DEVICE_VIEW_DATA_STREAM
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#List-Data-Stream-Values"> List Data Stream Values</a> endpoint
     * @param context
     * @param device
     * @param stream
     * @param options
     * @param listener
     */
    public static void listDataStreamValues(Context context, Device device, Stream stream,
                                            ListStreamValuesOptions options,
                                            TypedResponseListener<StreamValues> listener) {
        if (StringUtils.hasText(device.getId())) {
            listDataStreamValues(context, options.toMap(), device.getId(), stream.getName(), new StreamValuesResponseListener(listener));
        } else if (StringUtils.hasText(device.getSerial())) {
            listDataStreamValuesSerial(context, options.toMap(), device.getSerial(), stream.getName(), new StreamValuesResponseListener(listener));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#List-Data-Stream-Values"> List Data Stream Values</a> endpoint
     * @param context
     * @param params
     * @param deviceId
     * @param name
     * @param listener
     */
    public static void listDataStreamValues(Context context, Map<String,String> params,String deviceId,String name, ResponseListener listener) {
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_DATA_STREAM_VALUES, deviceId,name),
                params,
                listener,
                REQUEST_CODE_DEVICE_LIST_DATA_STREAM_VALUES
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#List-Data-Stream-Values"> List Data Stream Values</a> endpoint
     * @param context
     * @param params
     * @param deviceSerial
     * @param name
     * @param listener
     */
    public static void listDataStreamValuesSerial(Context context, Map<String,String> params,String deviceSerial,String name, ResponseListener listener) {
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_DATA_STREAM_VALUES_SERIAL, deviceSerial,name),
                params,
                listener,
                REQUEST_CODE_DEVICE_LIST_DATA_STREAM_VALUES
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Data-Stream-Sampling">Data Stream Sampling</a> endpoint
     * @param context
     * @param device
     * @param stream
     * @param options
     * @param listener
     */
    public static void dataStreamSampling(Context context, Device device, Stream stream,
                                          StreamSamplingOptions options,
                                          TypedResponseListener<StreamValues> listener) {
        if (StringUtils.hasText(device.getId())) {
            dataStreamSampling(context, options.toMap(), device.getId(), stream.getName(), new StreamValuesResponseListener(listener));
        } else if (StringUtils.hasText(device.getSerial())) {
            dataStreamSamplingSerial(context, options.toMap(), device.getSerial(), stream.getName(), new StreamValuesResponseListener(listener));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Data-Stream-Sampling">Data Stream Sampling</a> endpoint
     * @param context
     * @param params
     * @param deviceId
     * @param name
     * @param listener
     */
    public static void dataStreamSampling(Context context, Map<String, String> params,
                                          String deviceId, String name, ResponseListener listener) {
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_DATA_STREAM_SAMPLING, deviceId,name),
                params,
                listener,
                REQUEST_CODE_DEVICE_DATA_STREAM_SAMPLING
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Data-Stream-Sampling">Data Stream Sampling</a> endpoint
     * @param context
     * @param params
     * @param deviceSerial
     * @param name
     * @param listener
     */
    public static void dataStreamSamplingSerial(Context context, Map<String, String> params,
                                                String deviceSerial, String name, ResponseListener listener) {
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_DATA_STREAM_SAMPLING_SERIAL, deviceSerial,name),
                params,
                listener,
                REQUEST_CODE_DEVICE_DATA_STREAM_SAMPLING
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Data-Stream-Stats">Data Stream Stats</a> endpoint
     * @param context
     * @param device
     * @param stream
     * @param options
     * @param listener
     */
    public static void dataStreamStats(Context context, Device device, Stream stream,
                                       StreamStatsOptions options,
                                       TypedResponseListener<StreamStats> listener) {
        if (StringUtils.hasText(device.getId())) {
            dataStreamStats(context, options.toMap(), device.getId(), stream.getName(), new StatsResponseListener(listener));
        } else if (StringUtils.hasText(device.getSerial())) {
            dataStreamStatsSerial(context, options.toMap(), device.getSerial(), stream.getName(), new StatsResponseListener(listener));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Data-Stream-Stats">Data Stream Stats</a> endpoint
     * @param context
     * @param params
     * @param deviceId
     * @param name
     * @param listener
     */
    public static void dataStreamStats(Context context, Map<String, String> params, String deviceId,
                                       String name, ResponseListener listener) {
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_DATA_STREAM_STATS, deviceId, name),
                params,
                listener,
                REQUEST_CODE_DEVICE_DATA_STREAM_STATS
        );
    }


    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Data-Stream-Stats">Data Stream Stats</a> endpoint
     * @param context
     * @param params
     * @param deviceSerial
     * @param name
     * @param listener
     */
    public static void dataStreamStatsSerial(Context context, Map<String, String> params,
                                             String deviceSerial, String name,
                                             ResponseListener listener) {
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_DATA_STREAM_STATS_SERIAL, deviceSerial, name),
                params,
                listener,
                REQUEST_CODE_DEVICE_DATA_STREAM_STATS
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#List-Values-from-all-Data-Streams-of-a-Device">List Values from all Data Streams of a Device</a> endpoint
     * @param context
     * @param device
     * @param listener
     */
    public static void listAllDataStreamValues(Context context, Device device, TypedResponseListener<MultiStreamValues> listener) {
        if (StringUtils.hasText(device.getId())) {
            listAllDataStreamValues(context, device.getId(), new MultiStreamValuesResponseListener(listener));
        } else if (StringUtils.hasText(device.getSerial())) {
            listAllDataStreamValuesSerial(context, device.getSerial(), new MultiStreamValuesResponseListener(listener));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#List-Values-from-all-Data-Streams-of-a-Device">List Values from all Data Streams of a Device</a> endpoint
     * @param context
     * @param deviceId
     * @param listener
     */
    public static void listAllDataStreamValues(Context context, String deviceId, ResponseListener listener) {
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_DATA_STREAMS_VALUES_ALL, deviceId),
                null,
                listener,
                REQUEST_CODE_DEVICE_DATA_STREAM_STATS
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#List-Values-from-all-Data-Streams-of-a-Device">List Values from all Data Streams of a Device</a> endpoint
     * @param context
     * @param deviceSerial
     * @param listener
     */
    public static void listAllDataStreamValuesSerial(Context context, String deviceSerial, ResponseListener listener) {
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_DATA_STREAMS_VALUES_ALL_SERIAL, deviceSerial),
                null,
                listener,
                REQUEST_CODE_DEVICE_DATA_STREAM_STATS
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Search-Values-from-all-Data-Streams-of-a-Device">Search Values from all Data Streams of a Device</a> endpoint
     * @param context
     * @param device
     * @param search
     * @param listener
     */
    public static void searchDataStreamValues(Context context, Device device,
                                              StreamValueSearch search,
                                              TypedResponseListener<MultiStreamValues> listener) {
        if (StringUtils.hasText(device.getId())) {
            searchDataStreamValues(context, device.getId(), search.toJsonObject(), new MultiStreamValuesResponseListener(listener));
        } else if (StringUtils.hasText(device.getSerial())) {
            searchDataStreamValuesSerial(context, device.getSerial(), search.toJsonObject(), new MultiStreamValuesResponseListener(listener));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Search-Values-from-all-Data-Streams-of-a-Device">Search Values from all Data Streams of a Device</a> endpoint
     * @param context
     * @param deviceId
     * @param body
     * @param listener
     */
    public static void searchDataStreamValues(Context context, String deviceId, JSONObject body,
                                              ResponseListener listener) {
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_SEARCH_DATA_STREAM_VALUES, deviceId),
                body,
                listener,
                REQUEST_CODE_SEARCH_DATA_STREAM_VALUES
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Search-Values-from-all-Data-Streams-of-a-Device">Search Values from all Data Streams of a Device</a> endpoint
     * @param context
     * @param deviceSerial
     * @param body
     * @param listener
     */
    public static void searchDataStreamValuesSerial(Context context, String deviceSerial, JSONObject body,
                                                    ResponseListener listener) {
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_SEARCH_DATA_STREAM_VALUES_SERIAL, deviceSerial),
                body,
                listener,
                REQUEST_CODE_SEARCH_DATA_STREAM_VALUES
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Post-Data-Stream-Values">Post Data Stream Values</a> endpoint
     * @param context
     * @param device
     * @param stream
     * @param values
     * @param listener
     */
    public static void postDataStreamValues(Context context, Device device, Stream stream,
                                            List<StreamValue> values,
                                            TypedResponseListener<Void> listener) {
        JSONObject body = new JSONObject();
        if (values != null) {
            try {
                body.put("values", ArrayUtils.listToJsonArray(values));
            } catch (JSONException e) {
                listener.onRequestException(new M2XApiException(e));
                return;
            }
        }
        if (StringUtils.hasText(device.getId())) {
            postDataStreamValues(context, body, device.getId(), stream.getName(), new EmptyResponseListener(listener));
        } else if (StringUtils.hasText(device.getSerial())) {
            postDataStreamValuesSerial(context, body, device.getSerial(), stream.getName(), new EmptyResponseListener(listener));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Post-Data-Stream-Values">Post Data Stream Values</a> endpoint
     * @param context
     * @param deviceId
     * @param name
     * @param listener
     */
    public static void postDataStreamValues(Context context, JSONObject body, String deviceId,
                                            String name, ResponseListener listener) {
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_DATA_STREAM_VALUES,deviceId,name),
                body,
                listener,
                REQUEST_CODE_DEVICE_POST_DATA_STREAM_VALUES
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Post-Data-Stream-Values">Post Data Stream Values</a> endpoint
     * @param context
     * @param deviceSerial
     * @param name
     * @param listener
     */
    public static void postDataStreamValuesSerial(Context context, JSONObject params, String deviceSerial,
                                                  String name, ResponseListener listener) {
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_DATA_STREAM_VALUES_SERIAL,deviceSerial,name),
                params,
                listener,
                REQUEST_CODE_DEVICE_POST_DATA_STREAM_VALUES
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Export-Values-from-all-Data-Streams-of-a-Device">Export Values from all Data Streams of a Device</a> endpoint
     * @param context
     * @param device
     * @param options
     * @param listener
     */
    public static void exportValues(Context context, Device device, ExportOptions options,
                                    TypedResponseListener<LocationResult> listener) {
        if (StringUtils.hasText(device.getId())) {
            exportValues(context, device.getId(), options.toMap(), new BaseResponseListener.NewLocationResponseListener(listener));
        } else if (StringUtils.hasText(device.getSerial())) {
            exportValuesSerial(context, device.getSerial(), options.toMap(), new BaseResponseListener.NewLocationResponseListener(listener));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Export-Values-from-all-Data-Streams-of-a-Device">Export Values from all Data Streams of a Device</a> endpoint
     * @param context
     * @param deviceId
     * @param params
     * @param listener
     */
    public static void exportValues(Context context, String deviceId, Map<String, String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_EXPORT_VALUES, deviceId),
                params,
                listener,
                REQUEST_CODE_DEVICE_EXPORT_VALUES
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Export-Values-from-all-Data-Streams-of-a-Device">Export Values from all Data Streams of a Device</a> endpoint
     * @param context
     * @param deviceSerial
     * @param params
     * @param listener
     */
    public static void exportValuesSerial(Context context, String deviceSerial, Map<String, String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_EXPORT_VALUES_SERIAL, deviceSerial),
                params,
                listener,
                REQUEST_CODE_DEVICE_EXPORT_VALUES
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Delete-Data-Stream-Values">Delete Data Stream Values</a> endpoint
     * @param context
     * @param device
     * @param stream
     * @param from
     * @param end
     * @param listener
     */
    public static void deleteDataStreamValues(Context context, Device device, Stream stream,
                                              Date from, Date end,
                                              TypedResponseListener<Void> listener) {
        Map<String, String> params = new HashMap<>();
        if (from != null) params.put("from", DateUtils.dateTimeToString(from));
        if (end != null) params.put("end", DateUtils.dateTimeToString(end));

        if (StringUtils.hasText(device.getId())) {
            deleteDataStreamValues(context, params, device.getId(), stream.getName(), new EmptyResponseListener(listener));
        } else if (StringUtils.hasText(device.getSerial())) {
            deleteDataStreamValuesSerial(context, params, device.getSerial(), stream.getName(), new EmptyResponseListener(listener));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Delete-Data-Stream-Values">Delete Data Stream Values</a> endpoint
     * @param context
     * @param params
     * @param deviceId
     * @param name
     * @param listener
     */
    public static void deleteDataStreamValues(Context context, Map<String, String> params,
                                              String deviceId, String name,
                                              ResponseListener listener) {
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_DATA_STREAM_VALUES,deviceId,name),
                params,
                listener,
                REQUEST_CODE_DEVICE_DELETE_DATA_STREAM_VALUES
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Delete-Data-Stream-Values">Delete Data Stream Values</a> endpoint
     * @param context
     * @param params
     * @param deviceSerial
     * @param name
     * @param listener
     */
    public static void deleteDataStreamValuesSerial(Context context, Map<String, String> params,
                                                    String deviceSerial, String name,
                                                    ResponseListener listener) {
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_DATA_STREAM_VALUES_SERIAL,deviceSerial,name),
                params,
                listener,
                REQUEST_CODE_DEVICE_DELETE_DATA_STREAM_VALUES
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Delete-Data-Stream">Delete Data Stream</a> endpoint
     * @param context
     * @param device
     * @param stream
     * @param listener
     */
    public static void deleteDataStream(Context context, Device device, Stream stream,
                                        TypedResponseListener<Void> listener) {
        if (StringUtils.hasText(device.getId())) {
            deleteDataStream(context, device.getId(), stream.getName(), new EmptyResponseListener(listener));
        } else if (StringUtils.hasText(device.getSerial())) {
            deleteDataStreamSerial(context, device.getSerial(), stream.getName(), new EmptyResponseListener(listener));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Delete-Data-Stream">Delete Data Stream</a> endpoint
     * @param context
     * @param deviceId
     * @param name
     * @param listener
     */
    public static void deleteDataStream(Context context,String deviceId, String name,
                                        ResponseListener listener) {
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_DATA_STREAM,deviceId,name),
                (Map<String, String>)null,
                listener,
                REQUEST_CODE_DEVICE_DELETE_DATA_STREAM
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Delete-Data-Stream">Delete Data Stream</a> endpoint
     * @param context
     * @param deviceSerial
     * @param name
     * @param listener
     */
    public static void deleteDataStreamSerial(Context context,String deviceSerial, String name,
                                              ResponseListener listener) {
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_DATA_STREAM_SERIAL,deviceSerial,name),
                (Map<String, String>)null,
                listener,
                REQUEST_CODE_DEVICE_DELETE_DATA_STREAM
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Post-Device-Update--Single-Values-to-Multiple-Streams-">Post Device Update (Single Values to Multiple Streams)</a> endpoint
     * @param context
     * @param device
     * @param update
     * @param listener
     */
    public static void postDeviceUpdate(Context context, Device device, DeviceUpdate update,
                                        TypedResponseListener<Void> listener) {
        if (StringUtils.hasText(device.getId())) {
            postDeviceUpdate(context, update.toJsonObject(), device.getId(), new EmptyResponseListener(listener));
        } else if (StringUtils.hasText(device.getSerial())) {
            postDeviceUpdateSerial(context, update.toJsonObject(), device.getSerial(), new EmptyResponseListener(listener));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Post-Device-Update--Single-Values-to-Multiple-Streams-">Post Device Update (Single Values to Multiple Streams)</a> endpoint
     * @param context
     * @param body
     * @param deviceId
     * @param listener
     */
    public static void postDeviceUpdate(Context context, JSONObject body, String deviceId,
                                        ResponseListener listener) {
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_UPDATE, deviceId),
                body,
                listener,
                REQUEST_CODE_DEVICE_POST_DEVICE_UPDATE
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Post-Device-Update--Single-Values-to-Multiple-Streams-">Post Device Update (Single Values to Multiple Streams)</a> endpoint
     * @param context
     * @param body
     * @param deviceSerial
     * @param listener
     */
    public static void postDeviceUpdateSerial(Context context, JSONObject body, String deviceSerial,
                                              ResponseListener listener) {
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_UPDATE_SERIAL, deviceSerial),
                body,
                listener,
                REQUEST_CODE_DEVICE_POST_DEVICE_UPDATE
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Post-Device-Updates--Multiple-Values-to-Multiple-Streams-">Post Device Updates (Multiple Values to Multiple Streams)</a> endpoint
     * @param context
     * @param device
     * @param updates
     * @param listener
     */
    public static void postDeviceUpdates(Context context, Device device, DeviceUpdates updates,
                                         TypedResponseListener<Void> listener) {
        if (StringUtils.hasText(device.getId())) {
            postDeviceUpdates(context, updates.toJsonObject(), device.getId(), new EmptyResponseListener(listener));
        } else if (StringUtils.hasText(device.getSerial())) {
            postDeviceUpdatesSerial(context, updates.toJsonObject(), device.getSerial(), new EmptyResponseListener(listener));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Post-Device-Updates--Multiple-Values-to-Multiple-Streams-">Post Device Updates (Multiple Values to Multiple Streams)</a> endpoint
     * @param context
     * @param body
     * @param deviceId
     * @param listener
     */
    public static void postDeviceUpdates(Context context,JSONObject body,String deviceId, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_UPDATES,deviceId),
                body,
                listener,
                REQUEST_CODE_DEVICE_POST_DEVICE_UPDATES
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Post-Device-Updates--Multiple-Values-to-Multiple-Streams-">Post Device Updates (Multiple Values to Multiple Streams)</a> endpoint
     * @param context
     * @param body
     * @param deviceSerial
     * @param listener
     */
    public static void postDeviceUpdatesSerial(Context context,JSONObject body,String deviceSerial, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_UPDATES_SERIAL,deviceSerial),
                body,
                listener,
                REQUEST_CODE_DEVICE_POST_DEVICE_UPDATES
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#View-Request-Log">View Request Log</a> endpoint
     * @param context
     * @param device
     * @param listener
     */
    public static void viewRequestLog(Context context, Device device, TypedResponseListener<List<DeviceRequest>> listener) {
        if (StringUtils.hasText(device.getId())) {
            viewRequestLog(context, device.getId(), new RequestLogResponseListener(listener));
        } else if (StringUtils.hasText(device.getSerial())) {
            viewRequestLogSerial(context, device.getSerial(), new RequestLogResponseListener(listener));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#View-Request-Log">View Request Log</a> endpoint
     * @param context
     * @param deviceId
     * @param listener
     */
    public static void viewRequestLog(Context context, String deviceId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_REQUEST_LOG, deviceId),
                null,
                listener,
                REQUEST_CODE_DEVICE_VIEW_REQUEST_LOG
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#View-Request-Log">View Request Log</a> endpoint
     * @param context
     * @param deviceSerial
     * @param listener
     */
    public static void viewRequestLogSerial(Context context,String deviceSerial, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_REQUEST_LOG_SERIAL, deviceSerial),
                null,
                listener,
                REQUEST_CODE_DEVICE_VIEW_REQUEST_LOG
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Delete-Device">Delete Device</a> endpoint
     * @param context
     * @param device
     * @param listener
     */
    public static void deleteDevice(Context context, Device device, TypedResponseListener<Void> listener) {
        if (StringUtils.hasText(device.getId())) {
            deleteDevice(context, device.getId(), new EmptyResponseListener(listener));
        } else if (StringUtils.hasText(device.getSerial())) {
            deleteDeviceSerial(context, device.getSerial(), new EmptyResponseListener(listener));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Delete-Device">Delete Device</a> endpoint
     * @param context
     * @param deviceId
     * @param listener
     */
    public static void deleteDevice(Context context, String deviceId, ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US,Constants.DEVICE,deviceId),
                (Map<String, String>) null,
                listener,
                REQUEST_CODE_DEVICE_DELETE
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#Delete-Device">Delete Device</a> endpoint
     * @param context
     * @param deviceSerial
     * @param listener
     */
    public static void deleteDeviceSerial(Context context, String deviceSerial, ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_SERIAL,deviceSerial),
                (Map<String, String>) null,
                listener,
                REQUEST_CODE_DEVICE_DELETE
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/commands#Device-s-List-of-Received-Commands">Received Commands</a> endpoint
     * @param context
     * @param device
     * @param options
     * @param listener
     */
    public static void listCommands(Context context, Device device, ListDeviceCommandsOptions options,
                                    TypedResponseListener<List<DeviceCommand>> listener) {
        if (StringUtils.hasText(device.getId())) {
            listCommands(context, device.getId(), options.toMap(), new DeviceCommandResponseListener(listener));
        } else if (StringUtils.hasText(device.getSerial())) {
            listCommandsSerial(context, device.getSerial(), options.toMap(), new DeviceCommandResponseListener(listener));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/commands#Device-s-List-of-Received-Commands">Received Commands</a> endpoint
     * @param context
     * @param deviceId
     * @param params
     * @param listener
     */
    public static void listCommands(Context context, String deviceId, Map<String, String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_COMMANDS, deviceId),
                params,
                listener,
                REQUEST_CODE_LIST_RECEIVED_COMMANDS
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/commands#Device-s-List-of-Received-Commands">Received Commands</a> endpoint
     * @param context
     * @param deviceSerial
     * @param params
     * @param listener
     */
    public static void listCommandsSerial(Context context, String deviceSerial, Map<String, String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_COMMANDS_SERIAL, deviceSerial),
                params,
                listener,
                REQUEST_CODE_LIST_RECEIVED_COMMANDS
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/commands#Device-s-View-of-Command-Details">View Command Details</a> endpoint
     * @param context
     * @param device
     * @param commandId
     * @param listener
     */
    public static void viewCommand(Context context, Device device, String commandId, TypedResponseListener<Command> listener) {
        if (StringUtils.hasText(device.getId())) {
            viewCommand(context, device.getId(), commandId, new CommandResponseListener(listener));
        } else if (StringUtils.hasText(device.getSerial())) {
            viewCommandSerial(context, device.getSerial(), commandId, new CommandResponseListener(listener));
        } else {
            listener.onRequestException(new M2XApiException("Invalid Device: Missing ID/serial"));
        }
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/commands#Device-s-View-of-Command-Details">View Command Details</a> endpoint
     * @param context
     * @param deviceId
     * @param commandId
     * @param listener
     */
    public static void viewCommand(Context context, String deviceId, String commandId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_COMMAND, deviceId, commandId),
                null,
                listener,
                REQUEST_CODE_VIEW_COMMAND_DETAILS
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/commands#Device-s-View-of-Command-Details">View Command Details</a> endpoint
     * @param context
     * @param deviceSerial
     * @param commandId
     * @param listener
     */
    public static void viewCommandSerial(Context context, String deviceSerial, String commandId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_COMMAND_SERIAL, deviceSerial, commandId),
                null,
                listener,
                REQUEST_CODE_VIEW_COMMAND_DETAILS
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/commands#Device-Marks-a-Command-as-Processed">Mark Command as Processed</a> endpoint
     * @param context
     * @param device
     * @param command
     * @param responseData
     * @param listener
     */
    public static void processCommand(Context context, Device device, Command command,
                                      Map<String, String> responseData,
                                      TypedResponseListener<Void> listener) {
        JSONObject body = null;
        if (responseData != null) {
            try {
                body = ArrayUtils.mapToJsonObject(responseData);
            } catch (JSONException e) {
                throw new IllegalArgumentException(e);
            }
        }

        processCommand(context, device.getId(), command.getId(), body, new EmptyResponseListener(listener));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/commands#Device-Marks-a-Command-as-Processed">Mark Command as Processed</a> endpoint
     * @param context
     * @param deviceId
     * @param commandId
     * @param body
     * @param listener
     */
    public static void processCommand(Context context, String deviceId, String commandId, JSONObject body, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_MARK_COMMAND_PROCESSED, deviceId, commandId),
                body,
                listener,
                REQUEST_CODE_MARK_COMMAND_PROCESSED
        );
    }


    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/commands#Device-Marks-a-Command-as-Rejected">Mark Command as Rejected</a> endpoint
     * @param context
     * @param device
     * @param command
     * @param responseData
     * @param listener
     */
    public static void rejectCommand(Context context, Device device, Command command,
                                     Map<String, String> responseData,
                                     TypedResponseListener<Void> listener) {
        JSONObject body = null;
        if (responseData != null) {
            try {
                body = ArrayUtils.mapToJsonObject(responseData);
            } catch (JSONException e) {
                throw new IllegalArgumentException(e);
            }
        }

        rejectCommand(context, device.getId(), command.getId(), body, new EmptyResponseListener(listener));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/commands#Device-Marks-a-Command-as-Rejected">Mark Command as Rejected</a> endpoint
     * @param context
     * @param deviceId
     * @param commandId
     * @param body
     * @param listener
     */
    public static void rejectCommand(Context context, String deviceId, String commandId,  JSONObject body, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_MARK_COMMAND_REJECTED, deviceId, commandId),
                body,
                listener,
                REQUEST_CODE_MARK_COMMAND_REJECTED
        );
    }




    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    //// RESPONSE LISTENERS
    ////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static class TagsResponseListener extends BaseResponseListener<Map<String, Integer>> {
        TagsResponseListener(TypedResponseListener<Map<String, Integer>> listener) {
            super(listener);
        }

        @Override
        Map<String, Integer> parseResponse(JSONObject jsonObject) throws JSONException, ParseException {
            JSONObject tagsObject = jsonObject.getJSONObject("tags");
            Map<String, Integer> tags = new HashMap<>();
            for (Iterator<String> keys = tagsObject.keys(); keys.hasNext();) {
                String key = keys.next();
                if (tagsObject.isNull(key)) tags.put(key, null);
                else tags.put(key, tagsObject.getInt(key));
            }
            return tags;
        }
    }

    private static class DeviceListResponseListener extends BaseResponseListener<List<Device>> {

        DeviceListResponseListener(TypedResponseListener<List<Device>> listener) {
            super(listener);
        }

        @Override
        public List<Device> parseResponse(JSONObject jsonObject) throws JSONException {
            JSONArray deviceArray = jsonObject.getJSONArray("devices");
            List<Device> devices = new ArrayList<>(deviceArray.length());
            for(int i=0; i<deviceArray.length(); i++) {
                Device nextDevice = Device.fromJsonObject(deviceArray.getJSONObject(i));
                devices.add(nextDevice);
            }
            return devices;
        }
    }

    private static class DeviceResponseListener extends BaseResponseListener<Device> {
        DeviceResponseListener(TypedResponseListener<Device> listener) {
            super(listener);
        }

        @Override
        public Device parseResponse(JSONObject jsonObject) throws JSONException {
            return Device.fromJsonObject(jsonObject);
        }
    }

    private static class LocationResponseListener extends BaseResponseListener<Location> {
        LocationResponseListener(TypedResponseListener<Location> listener) {
            super(listener);
        }

        @Override
        Location parseResponse(JSONObject jsonObject) throws JSONException {
            return Location.fromJsonObject(jsonObject);
        }
    }

    private static class LocationHistoryResponseListener extends BaseResponseListener<List<Location>> {
        LocationHistoryResponseListener(TypedResponseListener<List<Location>> listener) {
            super(listener);
        }

        @Override
        List<Location> parseResponse(JSONObject jsonObject) throws JSONException {
            JSONArray waypoints = jsonObject.getJSONArray("waypoints");
            List<Location> locations = new ArrayList<>(waypoints.length());
            for (int i = 0; i < waypoints.length(); ++i) {
                locations.add(Location.fromJsonObject(waypoints.getJSONObject(i)));
            }
            return locations;
        }
    }

    private static class StreamResponseListener extends BaseResponseListener<Stream> {

        StreamResponseListener(TypedResponseListener<Stream> listener) {
            super(listener);
        }

        @Override
        Stream parseResponse(JSONObject jsonObject) throws JSONException {
            return Stream.fromJsonObject(jsonObject);
        }
    }

    private static class StreamListResponseListener extends BaseResponseListener<List<Stream>> {
        StreamListResponseListener(TypedResponseListener<List<Stream>> listener) {
            super(listener);
        }

        @Override
        List<Stream> parseResponse(JSONObject jsonObject) throws JSONException {
            JSONArray streams = jsonObject.getJSONArray("streams");
            List<Stream> streamsList = new ArrayList<>(streams.length());
            for (int i = 0; i < streams.length(); ++i) {
                streamsList.add(Stream.fromJsonObject(streams.getJSONObject(i)));
            }
            return streamsList;
        }
    }

    /**
     * We can't use BaseResponseListener because this endpoint returns an array instead of an object
     */
    private static class CreateStreamsResponseHandler implements com.att.m2x.android.listeners.ResponseListener {
        private final TypedResponseListener<List<Stream>> listener;

        CreateStreamsResponseHandler(TypedResponseListener<List<Stream>> listener) {
            this.listener = listener;
        }

        @Override
        public void onRequestCompleted(ApiV2Response result, int requestCode) {
            if (result.getStatus() >= 200 && result.getStatus() < 300) {
                try {
                    JSONArray responseArray = new JSONArray(result.getRawBody());
                    List<Stream> streams = new ArrayList<>(responseArray.length());
                    for (int i = 0; i < responseArray.length(); ++i) {
                        streams.add(Stream.fromJsonObject(responseArray.getJSONObject(i)));
                    }
                    listener.onRequestCompleted(streams);
                } catch (JSONException e) {
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
    }

    private static class StreamValuesResponseListener extends BaseResponseListener<StreamValues> {
        StreamValuesResponseListener(TypedResponseListener<StreamValues> listener) {
            super(listener);
        }

        @Override
        StreamValues parseResponse(JSONObject jsonObject) throws JSONException, ParseException {
            return StreamValues.fromJsonObject(jsonObject);
        }
    }

    private static class MultiStreamValuesResponseListener extends BaseResponseListener<MultiStreamValues> {
        MultiStreamValuesResponseListener(TypedResponseListener<MultiStreamValues> listener) {
            super(listener);
        }

        @Override
        MultiStreamValues parseResponse(JSONObject jsonObject) throws JSONException, ParseException {
            return MultiStreamValues.fromJsonObject(jsonObject);
        }
    }

    private static class StatsResponseListener extends BaseResponseListener<StreamStats> {
        StatsResponseListener(TypedResponseListener<StreamStats> listener) {
            super(listener);
        }

        @Override
        StreamStats parseResponse(JSONObject jsonObject) throws JSONException, ParseException {
            return StreamStats.fromJsonObject(jsonObject);
        }
    }

    private static class RequestLogResponseListener extends BaseResponseListener<List<DeviceRequest>> {
        RequestLogResponseListener(TypedResponseListener<List<DeviceRequest>> listener) {
            super(listener);
        }

        @Override
        List<DeviceRequest> parseResponse(JSONObject jsonObject) throws JSONException, ParseException {
            if (jsonObject == null || jsonObject.isNull("requests")) return Collections.emptyList();
            JSONArray requestArray = jsonObject.getJSONArray("requests");
            List<DeviceRequest> requestList = new ArrayList<>(requestArray.length());
            for (int i = 0; i < requestArray.length(); ++i) {
                requestList.add(DeviceRequest.fromJsonObject(requestArray.getJSONObject(i)));
            }
            return requestList;
        }
    }

    private static class DeviceCommandResponseListener extends BaseResponseListener<List<DeviceCommand>> {
        DeviceCommandResponseListener(TypedResponseListener<List<DeviceCommand>> listener) {
            super(listener);
        }

        @Override
        List<DeviceCommand> parseResponse(JSONObject jsonObject) throws JSONException, ParseException {
            if (jsonObject == null) return null;
            JSONArray commandsArray = jsonObject.getJSONArray("commands");
            List<DeviceCommand> commandsList = new ArrayList<>();
            for (int i = 0; i < commandsArray.length(); ++i) {
                commandsList.add(DeviceCommand.fromJsonObject(jsonObject));
            }
            return commandsList;
        }
    }

    private static class CommandResponseListener extends BaseResponseListener<Command> {
        CommandResponseListener(TypedResponseListener<Command> listener) {
            super(listener);
        }

        @Override
        Command parseResponse(JSONObject jsonObject) throws JSONException, ParseException {
            return Command.fromJsonObject(jsonObject);
        }
    }

}



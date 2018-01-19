package com.att.m2x.android.model;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.att.m2x.android.common.Constants;
import com.att.m2x.android.listeners.ResponseListener;
import com.att.m2x.android.listeners.TypedResponseListener;
import com.att.m2x.android.network.JsonRequest;
import com.att.m2x.android.services.DeviceService;
import com.att.m2x.android.services.model.StreamSamplingOptions;
import com.att.m2x.android.services.model.StreamStatsOptions;
import com.att.m2x.android.services.model.StreamValueSearch;
import com.att.m2x.android.services.model.DeviceSearch;
import com.att.m2x.android.services.model.DeviceUpdate;
import com.att.m2x.android.services.model.DeviceUpdates;
import com.att.m2x.android.services.model.ExportOptions;
import com.att.m2x.android.services.model.ListStreamValuesOptions;
import com.att.m2x.android.services.model.ListDeviceCommandsOptions;
import com.att.m2x.android.services.model.ListDevicesOptions;
import com.att.m2x.android.services.model.PublicCatalogSearch;
import com.att.m2x.android.utils.DateUtils;

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
public class Device implements IModelObject {

    public Device() {
    }

    public Device(String id) {
        this.id = id;
    }

    public Device(String name, String description, Visibility visibility) {
        this.name = name;
        this.description = description;
        this.visibility = visibility;
    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String baseDeviceId;

    public String getBaseDeviceId() {
        return baseDeviceId;
    }

    public void setBaseDeviceId(String baseDeviceId) {
        this.baseDeviceId = baseDeviceId;
    }

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private Visibility visibility;

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    private Date created;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    private Date lastActivity;

    public Date getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(Date lastActivity) {
        this.lastActivity = lastActivity;
    }

    private Date updated;

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    private String serial;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    private Set<String> tags;

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    private Integer streamsCount;

    public Integer getStreamsCount() {
        return streamsCount;
    }

    public void setStreamsCount(Integer streamsCount) {
        this.streamsCount = streamsCount;
    }

    private String streamsUrl;

    public String getStreamsUrl() {
        return streamsUrl;
    }

    public void setStreamsUrl(String streamsUrl) {
        this.streamsUrl = streamsUrl;
    }

    private Map<String, StreamValue> streamsValues;

    public Map<String, StreamValue> getStreamsValues() {
        return streamsValues;
    }

    public void setStreamsValues(Map<String, StreamValue> streamsValues) {
        this.streamsValues = streamsValues;
    }

    private DeviceStatus status;

    public DeviceStatus getStatus() {
        return status;
    }

    public void setStatus(DeviceStatus status) {
        this.status = status;
    }

    private String distribution;

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    private Map<String, String> metadata;

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    ////////////////////////////////////
    // JSON
    ////////////////////////////////////

    /**
     * Converts this Device into a JSONObject
     *
     * @return
     */
    public JSONObject toJsonObject() {
        return Device.toJsonObject(this);
    }

    /**
     * Converts a Device into a JSONObject
     *
     * @param device
     * @return
     */
    public static JSONObject toJsonObject(Device device) {
        JSONObject result = null;
        try {
            if (device != null) {
                result = new JSONObject();

                if (device.getId() != null) result.put("id", device.getId());
                if (device.getBaseDeviceId() != null)
                    result.put("base_device", device.getBaseDeviceId());
                if (device.getKey() != null) result.put("key", device.getKey());
                if (device.getUrl() != null) result.put("url", device.getUrl());
                if (device.getSerial() != null) result.put("serial", device.getSerial());
                if (device.getName() != null) result.put("name", device.getName());
                if (device.getDescription() != null)
                    result.put("description", device.getDescription());
                if (device.getCreated() != null) result.put("created", device.getCreated());
                if (device.getLastActivity() != null)
                    result.put("last_activity", device.getLastActivity());
                if (device.getUpdated() != null) result.put("updated", device.getUpdated());
                if (device.getStatus() != null) result.put("status", device.getStatus());
                if (device.getDistribution() != null)
                    result.put("distribution", device.getDistribution());
                if (device.getVisibility() != null)
                    result.put("visibility", device.getVisibility().visibility());

                if (device.getLocation() != null)
                    result.put("location", device.getLocation().toJsonObject());

                if (device.getMetadata() != null) {
                    JSONObject jsonMetadata = new JSONObject(device.getMetadata());
                    result.put("metadata", jsonMetadata);
                }

                if (device.getTags() != null) {
                    String strTags = null;
                    for (String tag : device.getTags()) {
                        if (strTags != null) {
                            strTags += ",";
                        } else {
                            strTags = "";
                        }
                        strTags += tag;
                    }
                    result.put("tags", strTags);
                }

                if (device.getStreamsCount() != null || device.getStreamsUrl() != null ||
                        device.getStreamsValues() != null) {
                    JSONObject streamsJsonObject = new JSONObject();
                    if (device.getStreamsCount() != null)
                        streamsJsonObject.put("count", device.getStreamsCount());
                    if (device.getStreamsUrl() != null)
                        streamsJsonObject.put("url", device.getStreamsUrl());
                    if (device.getStreamsValues() != null) {
                        JSONObject valueObj = new JSONObject();
                        for (Map.Entry<String, StreamValue> e : device.getStreamsValues().entrySet()) {
                            if (e.getValue() != null) {
                                StreamValue v = e.getValue();
                                if (e.getValue() != null && v.getValue() != null) {
                                    valueObj.put(e.getKey(), v.getValue());
                                } else if (v.getStringValue() != null) {
                                    valueObj.put(e.getKey(), v.getStringValue());
                                }
                            }
                        }
                        streamsJsonObject.put("values", valueObj);
                    }
                    result.put("streams", streamsJsonObject);
                }
            }
        } catch (JSONException je) {
            je.printStackTrace();
            result = null;
        }

        return result;
    }


    public void fromJson(JSONObject jsonObject) throws JSONException {

        if (jsonObject.has("id")) setId(jsonObject.getString("id"));
        if (jsonObject.has("base_device"))
            setBaseDeviceId(jsonObject.getString("base_device"));
        if (jsonObject.has("key")) setKey(jsonObject.getString("key"));
        if (jsonObject.has("url")) setUrl(jsonObject.getString("url"));
        if (!jsonObject.isNull("location")) setLocation(Location.fromJsonObject(jsonObject.getJSONObject("location")));
        if (jsonObject.has("serial")) setSerial(jsonObject.getString("serial"));
        if (jsonObject.has("name")) setName(jsonObject.getString("name"));
        if (jsonObject.has("description"))
            setDescription(jsonObject.getString("description"));

        //XXX
        try {
            if (!jsonObject.isNull("created"))
                setCreated(DateUtils.stringToDateTime(jsonObject.getString("created")));
            if (!jsonObject.isNull("updated"))
                setUpdated(DateUtils.stringToDateTime(jsonObject.getString("updated")));
            if (!jsonObject.isNull("last_activity"))
                setLastActivity(DateUtils.stringToDateTime(jsonObject.getString("last_activity")));
        } catch (ParseException e) {
            throw new JSONException(e.getMessage());
        }

        if (!jsonObject.isNull("status")) setStatus(DeviceStatus.parse(jsonObject.getString("status")));
        if (jsonObject.has("distribution"))
            setDistribution(jsonObject.getString("distribution"));
        if (!jsonObject.isNull("visibility"))
            setVisibility(Visibility.parse(jsonObject.getString("visibility")));

        if (jsonObject.has("location")) {
            setLocation(Location.fromJsonObject(jsonObject.getJSONObject("location")));
        }

        if (jsonObject.has("metadata")) {
            setMetadata(new HashMap<String, String>());
            JSONObject metadata = jsonObject.getJSONObject("metadata");
            if (metadata != null) {
                Iterator<String> itrKeys = metadata.keys();
                while (itrKeys.hasNext()) {
                    String key = itrKeys.next();
                    getMetadata().put(key, metadata.getString(key));
                }
            }
        }

        if (jsonObject.has("tags")) {
            setTags(new HashSet<String>());
            JSONArray jsonTagsArray = jsonObject.getJSONArray("tags");
            if (jsonTagsArray != null) {
                for (int i = 0; i < jsonTagsArray.length(); i++) {
                    String tag = (String) jsonTagsArray.get(i);
                    getTags().add(tag);
                }
            }
        }

        if (jsonObject.has("streams")) {
            JSONObject jsonStreams = jsonObject.getJSONObject("streams");
            if (jsonStreams != null) {
                if (!jsonStreams.isNull("count")) setStreamsCount(jsonStreams.getInt("count"));
                setStreamsUrl(jsonStreams.optString("url", null));
                if (!jsonStreams.isNull("values")) {
                    JSONObject valueObj = jsonObject.getJSONObject("values");
                    streamsValues = new HashMap<>();
                    for (Iterator<String> keys = valueObj.keys(); keys.hasNext();) {
                        String k = keys.next();
                        StreamValue streamValue = new StreamValue();
                        if (!valueObj.isNull(k)) {
                            Object v = valueObj.get(k);
                            if (v instanceof String) streamValue.setValue((String) v);
                            else if (v instanceof Number) streamValue.setValue(((Number) v).doubleValue());
                            else throw new JSONException("Invalid stream value: " + v);
                        }
                        streamsValues.put(k, streamValue);
                    }
                }
            }
        }
    }

    /**
     * Converts a JSONObject into a Device
     *
     * @param jsonObject
     * @return
     */
    public static Device fromJsonObject(JSONObject jsonObject) throws JSONException {
        Device device = new Device();
        device.fromJson(jsonObject);
        return device;
    }


    public enum DeviceStatus {
        ENABLED("enabled"),
        DISABLED("disabled");

        private final String status;

        DeviceStatus(String status) {
            this.status = status;
        }

        public String status() {
            return status;
        }

        public static DeviceStatus parse(String status) {
            if (status == null) return null;
            switch (status.toLowerCase()) {
                case "enabled": return ENABLED;
                case "disabled": return DISABLED;
            }
            throw new IllegalArgumentException("Invalid Device Status: " + status);
        }
    }


    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //
    //
    //  DEPRECATED STATIC METHODS ->
    //      MOVED TO DeviceService
    //
    //
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

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
     * @deprecated  Replaced by {@link DeviceService#searchPublicCatalog(Context, PublicCatalogSearch, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Search-Public-Devices-Catalog">Search Public Devices Catalog</a>} endpoint.
     * @param context The application Context
     * @param params Query parameters as HashMap<String,String>. View M2X API Docs for listing of available parameters.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void searchPublicCatalog(Context context, Map<String,String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                Constants.DEVICE_SEARCH_PUBLIC_CATALOG,
                params,
                listener,
                REQUEST_CODE_SEARCH_PUBLIC_CATALOG
        );
    }

    /**
     * @deprecated Replaced by {@link DeviceService#listDevices(Context, ListDevicesOptions, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#List-Devices">List Devices</a>} endpoint.
     * @param context The application Context
     * @param params Query parameters as HashMap<String,String>. View M2X API Docs for listing of available parameters.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void listDevices(Context context, Map<String,String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                Constants.DEVICES,
                params,
                listener,
                REQUEST_CODE_LIST_DEVICES
        );
    }

    /**
     * @deprecated Replaced by {@link DeviceService#searchDevices(Context, DeviceSearch, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Search-Devices">Search Devices</a>} endpoint
     * @param context The application Context.
     * @param body as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void searchDevices(Context context, JSONObject body, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                Constants.DEVICE_SEARCH,
                body,
                listener,
                REQUEST_CODE_SEARCH_DEVICES
        );
    }

    /**
     * @deprecated Replaced by {@link DeviceService#listDeviceTags(Context, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#List-Device-Tags">List Device Tags</a>} endpoint.
     * @param context The application Context
     * @param listener {@link ResponseListener}
     */
    @Deprecated
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
     * @deprecated Replaced by {@link DeviceService#createDevice(Context, Device, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Create-Device">Create Devices</a>} endpoint
     * @param context The application Context.
     * @param params as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void createDevice(Context context,JSONObject params, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                Constants.DEVICES,
                params,
                listener,
                REQUEST_CODE_CREATE_DEVICE
        );
    }

    /**
     * @deprecated Replaced by {@link DeviceService#updateDeviceDetails(Context, Device, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Device-Details">Update Device Details</a>} endpoint.
     * @param context The application Context
     * @param params Query parameters as JSONObject. View M2X API Docs for listing of available parameters. 
     * @param deviceId Device ID of the device.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void updateDeviceDetails(Context context,JSONObject params,String deviceId, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US,Constants.DEVICE,deviceId),
                params,
                listener,
                REQUEST_CODE_CREATE_DEVICE
        );
    }

    /**
     * @deprecated Replaced by {@link DeviceService#viewDeviceDetails(Context, Device, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#View-Device-Details">View Device Details</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void viewDeviceDetails(Context context,String deviceId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US,Constants.DEVICE,deviceId),
                null,
                listener,
                REQUEST_CODE_DEVICE_DETAILS
        );
    }

    /**
     * @deprecated Replaced by {@link DeviceService#readDeviceLocation(Context, Device, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Read-Device-Location">Read Device Location</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void readDeviceLocation(Context context,String deviceId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_LOCATION,deviceId),
                null,
                listener,
                REQUEST_CODE_DEVICE_LOCATION
        );
    }

    /**
     * @deprecated Replaced by {@link DeviceService#readDeviceLocationHistory(Context, Device, Integer, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Read-Device-Location-History">Read Device Location History</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param params Query parameters as HashMap<String,String>. View M2X API Docs for listing of available parameters.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void readDeviceLocationHistory(Context context, String deviceId, Map<String,String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_LOCATION_HISTORY, deviceId),
                params,
                listener,
                REQUEST_CODE_DEVICE_LOCATION_HISTORY
        );
    }

    /**
     * @deprecated Replaced by {@link DeviceService#updateDeviceLocation(Context, Device, Location, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Device-Location">Update Device Location</a>} endpoint.
     * @param context The application Context.
     * @param params as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param deviceId as String, ID of the device.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void updateDeviceLocation(Context context,JSONObject params,String deviceId, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_LOCATION, deviceId),
                params,
                listener,
                REQUEST_CODE_DEVICE_UPDATE_LOCATION
        );
    }

    /**
     * @deprecated Replaced by {@link DeviceService#readDeviceMetadata(Context, Device, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Read-Device-Metadata">Read Device Metadata</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void metadata(Context context, String deviceId, ResponseListener listener){
        Metadata.metadata(
                context,
                String.format(Locale.US, Constants.DEVICE_METADATA, deviceId),
                listener,
                REQUEST_CODE_METADATA);
    }

    /**
     * @deprecated Replaced by {@link DeviceService#updateDeviceMetadata(Context, Device, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Device-Metadata">Update Device Metadata</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param body as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void updateMetadata(Context context, String deviceId, JSONObject body, ResponseListener listener){
        Metadata.updateMetadata(
                context,
                String.format(Locale.US, Constants.DEVICE_METADATA, deviceId),
                body,
                listener,
                REQUEST_CODE_UPDATE_METADATA);
    }

    /**
     * @deprecated Replaced by {@link DeviceService#readDeviceMetadataField(Context, Device, String, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Read-Device-Metadata-Field">Read Device Metadata Field</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param field as String
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void metadataField(Context context, String deviceId, String field, ResponseListener listener){
        Metadata.metadataField(
                context,
                String.format(Locale.US, Constants.DEVICE_METADATA_FIELD, deviceId, field),
                listener,
                REQUEST_CODE_METADATA_FIELD);
    }

    /**
     * @deprecated Replaced by {@link DeviceService#updateDeviceMetadataField(Context, Device, String, String, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Device-Metadata">Update Device Metadata</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param field as String
     * @param body as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void updateMetadataField(Context context, String deviceId, String field, JSONObject body, ResponseListener listener){
        Metadata.updateMetadataField(
                context,
                String.format(Locale.US, Constants.DEVICE_METADATA_FIELD, deviceId, field),
                body,
                listener,
                REQUEST_CODE_UPDATE_METADATA_FIELD);
    }

    /**
     * @deprecated Replaced by {@link DeviceService#listDataStreams(Context, Device, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#List-Data-Streams">List Data Streams</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
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
     * @deprecated Replaced by {@link DeviceService#createUpdateDataStreams(Context, Device, List, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Create-Update-Data-Stream">Create/Update Data Stream</a>} endpoint.
     * @param context The application Context.
     * @param params as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param deviceId as String, ID of the device.
     * @param name as String
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void createUpdateDataStreams(Context context,JSONObject params,String deviceId,String name, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_DATA_STREAMS, deviceId,name),
                params,
                listener,
                REQUEST_CODE_DEVICE_CREATE_UPDATE_DATA_STREAMS
        );
    }

    /**
     * @deprecated Replaced by {@link DeviceService#updateDataStreamValue(Context, Device, Stream, StreamValue, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Data-Stream-Value">Update Data Stream Value</a>} endpoint.
     * @param context The application Context.
     * @param params as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param deviceId as String, ID of the device.
     * @param name as String
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void updateDataStreamValue(Context context,JSONObject params,String deviceId,String name, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_DATA_STREAM_VALUE, deviceId,name),
                params,
                listener,
                REQUEST_CODE_DEVICE_UPDATE_DATA_STREAM_VALUE
        );
    }

    /**
     * @deprecated Replaced by {@link DeviceService#viewDataStream(Context, Device, String, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#View-Data-Stream">View Data Stream</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param name as String
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void viewDataStream(Context context,String deviceId,String name, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_DATA_STREAM, deviceId,name),
                null,
                listener,
                REQUEST_CODE_DEVICE_VIEW_DATA_STREAM
        );
    }

    /**
     * @deprecated Replaced by {@link DeviceService#listDataStreamValues(Context, Device, Stream, ListStreamValuesOptions, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#List-Data-Stream-Values">List Data Stream Values</a>} endpoint.
     * @param context The application Context.
     * @param params Query parameters as HashMap<String,String>. View M2X API Docs for listing of available parameters.
     * @param deviceId as String, ID of the device.
     * @param name as String
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void listDataStreamValues(Context context, Map<String,String> params,String deviceId,String name, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_DATA_STREAM_VALUES, deviceId,name),
                params,
                listener,
                REQUEST_CODE_DEVICE_LIST_DATA_STREAM_VALUES
        );
    }

    /**
     * @deprecated Replaced by {@link DeviceService#dataStreamSampling(Context, Device, Stream, StreamSamplingOptions, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Data-Stream-Sampling">Data Stream Sampling</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param name as String
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void dataStreamSampling(Context context,String deviceId,String name, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_DATA_STREAM_SAMPLING, deviceId,name),
                null,
                listener,
                REQUEST_CODE_DEVICE_DATA_STREAM_SAMPLING
        );
    }

    /**
     * @deprecated Replaced by {@link DeviceService#dataStreamStats(Context, Device, Stream, StreamStatsOptions, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Data-Stream-Stats">Data Stream Stats</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param name as String
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void dataStreamStats(Context context,String deviceId,String name, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_DATA_STREAM_STATS, deviceId,name),
                null,
                listener,
                REQUEST_CODE_DEVICE_DATA_STREAM_STATS
        );
    }

    /**
     * @deprecated Replaced by {@link DeviceService#searchDataStreamValues(Context, Device, StreamValueSearch, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Search-Values-from-all-Data-Streams-of-a-Device">Delete Data Stream</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param format as String
     * @param body as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void searchDataStreamValues(Context context, String deviceId, String format, JSONObject body, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_SEARCH_DATA_STREAM_VALUES, deviceId, format),
                body,
                listener,
                REQUEST_CODE_SEARCH_DATA_STREAM_VALUES
        );
    }

    /**
     * @deprecated Replaced by {@link DeviceService#postDataStreamValues(Context, Device, Stream, List, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Update-Data-Stream-Value">Update Data Stream Value</a>} endpoint.
     * @param context The application Context.
     * @param params as JSON Object View M2X API Docs for listing of available body parameters.
     * @param deviceId as String, ID of the device.
     * @param name as String
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void postDataStreamValues(Context context,JSONObject params,String deviceId, String name, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_DATA_STREAM_VALUES,deviceId,name),
                params,
                listener,
                REQUEST_CODE_DEVICE_POST_DATA_STREAM_VALUES
        );
    }

    /**
     * @deprecated Replaced by {@link DeviceService#exportValues(Context, Device, ExportOptions, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Export-Values-from-all-Data-Streams-of-a-Device">Export Values from all Data Streams of a Device</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param params Query parameters as HashMap<String,String>. View M2X API Docs for listing of available parameters.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
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
     * @deprecated Replaced by {@link DeviceService#deleteDataStreamValues(Context, Device, Stream, Date, Date, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Delete-Data-Stream-Values">Delete Data Stream Values</a>} endpoint.
     * @param context The application Context.
     * @param params as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param deviceId as String, ID of the device.
     * @param name as String
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void deleteDataStreamValues(Context context,JSONObject params,String deviceId, String name, ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_DATA_STREAM_VALUES,deviceId,name),
                params,
                listener,
                REQUEST_CODE_DEVICE_DELETE_DATA_STREAM_VALUES
        );
    }

    /**
     * @deprecated Replaced by {@link DeviceService#deleteDataStream(Context, Device, Stream, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Delete-Data-Stream">Delete Data Stream</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param name as String
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void deleteDataStream(Context context,String deviceId, String name, ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_DATA_STREAM,deviceId,name),
                (Map<String, String>)null,
                listener,
                REQUEST_CODE_DEVICE_DELETE_DATA_STREAM
        );
    }

    /**
     * @deprecated Delete for device location not supported in V2 documentation {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Delete-Location-History"></a>}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Delete-Location-History">Delete Location History</a>} endpoint.
     * @param context The application Context.
     * @param params as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param deviceId as String, ID of the device.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void deleteDeviceLocation(Context context,JSONObject params,String deviceId, ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_LOCATION, deviceId),
                params,
                listener,
                REQUEST_CODE_DEVICE_DELETE_LOCATION
        );
    }

    /**
     * @deprecated Replaced by {@link DeviceService#postDeviceUpdate(Context, Device, DeviceUpdate, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Post-Device-Update--Single-Values-to-Multiple-Streams-">Post Device Update</a>} endpoint.
     * @param context The application Context.
     * @param params as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param deviceId as String, ID of the device.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void postDeviceUpdate(Context context, JSONObject params, String deviceId, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_UPDATE, deviceId),
                params,
                listener,
                REQUEST_CODE_DEVICE_POST_DEVICE_UPDATE
        );
    }

    /**
     * @deprecated Replaced by {@link DeviceService#postDeviceUpdates(Context, Device, DeviceUpdates, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Post-Device-Updates--Multiple-Values-to-Multiple-Streams-">Post Device Updates (Multiple Values to Multiple Streams)</a>} endpoint.
     * @param context The application Context.
     * @param params as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param deviceId as String, ID of the device.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void postDeviceUpdates(Context context,JSONObject params,String deviceId, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US,Constants.DEVICE_UPDATES,deviceId),
                params,
                listener,
                REQUEST_CODE_DEVICE_POST_DEVICE_UPDATES
        );
    }

    /**
     * @deprecated Replaced by {@link DeviceService#viewRequestLog(Context, Device, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#View-Request-Log">View Request Log</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void viewRequestLog(Context context,String deviceId,ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_REQUEST_LOG, deviceId),
                null,
                listener,
                REQUEST_CODE_DEVICE_VIEW_REQUEST_LOG
        );
    }

    /**
     * @deprecated Replaced by {@link DeviceService#deleteDevice(Context, Device, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Delete-Device">Delete Device</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void deleteDevice(Context context,String deviceId,ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US,Constants.DEVICE,deviceId),
                (Map<String, String>) null,
                listener,
                REQUEST_CODE_DEVICE_DELETE
        );
    }

    /**
     * @deprecated Replaced by {@link DeviceService#listCommands(Context, Device, ListDeviceCommandsOptions, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/device#List-Devices">Device's List of Received Commands</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param params Query parameters as HashMap<String,String>. View M2X API Docs for listing of available parameters.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
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
     * @deprecated Replaced by {@link DeviceService#viewCommand(Context, Device, String, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/commands#Device-s-View-of-Command-Details">Device's View of Command Details</a>} endpoint.
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param commandId as String, ID of the Command.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
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
     * @deprecated Replaced by {@link DeviceService#processCommand(Context, Device, Command, Map, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/commands#Device-Marks-a-Command-as-Processed">Device Marks a Command as Processed</a>} endpoint
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param commandId as String, ID of the Command.
     * @param body as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
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
     * @deprecated Replaced by {@link DeviceService#rejectCommand(Context, Device, Command, Map, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/commands#Device-Marks-a-Command-as-Rejected">Device Marks a Command as Rejected</a>} endpoint
     * @param context The application Context.
     * @param deviceId as String, ID of the device.
     * @param commandId as String, ID of the Command.
     * @param body as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void rejectCommand(Context context, String deviceId, String commandId,  JSONObject body, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US, Constants.DEVICE_MARK_COMMAND_REJECTED, deviceId, commandId),
                body,
                listener,
                REQUEST_CODE_MARK_COMMAND_REJECTED
        );
    }

}

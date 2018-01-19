package com.att.m2x.android.model;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import com.att.m2x.android.common.Constants;
import com.att.m2x.android.listeners.ResponseListener;
import com.att.m2x.android.listeners.TypedResponseListener;
import com.att.m2x.android.network.JsonRequest;
import com.att.m2x.android.utils.ArrayUtils;
import com.att.m2x.android.utils.DateUtils;
import com.att.m2x.android.utils.StringUtils;

import org.json.JSONObject;

import java.util.Locale;

/**
 * The Distribution API allows you to manage device distributions, allowing you
 * to take a device from prototype to market. Distributions represent a group of Devices that begin life with the attributes of the original device template when created.<p>
 *
 * Authentication:
 * All methods described in this page require that a Master API Key is specified in the X-M2X-KEY header.
 * Any attempt of using a Distribution or Device level API key would result in a 403 Forbidden response. {@see <a href="https://m2x.att.com/developer/documentation/v2/overview#API-Keys">Learn more about API Keys.</a>}
 */
public class Distribution implements IModelObject {

    private String id;
    private String name;
    private String description;
    private Visibility visibility;
    private String status;
    private String url;
    private String key;
    private Date created;
    private Date updated;
    private Map<String, String> metadata;
    private Devices devices;

    public Distribution() {
    }

    public Distribution(String id) {
        this.id = id;
    }

    public Distribution(String id, String name, String description, Visibility visibility,
                        String status, String url, String key, Date created, Date updated,
                        Map<String, String> metadata, Devices devices) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.visibility = visibility;
        this.status = status;
        this.url = url;
        this.key = key;
        this.created = created;
        this.updated = updated;
        this.metadata = metadata;
        this.devices = devices;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public Devices getDevices() {
        return devices;
    }

    public void setDevices(Devices devices) {
        this.devices = devices;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (StringUtils.hasText(id)) jsonObject.put("id", id);
            if (StringUtils.hasText(name)) jsonObject.put("name", name);
            if (StringUtils.hasText(description)) jsonObject.put("description", description);
            if (visibility != null) jsonObject.put("visibility", visibility.visibility());
            if (StringUtils.hasText(status)) jsonObject.put("status", status);
            if (StringUtils.hasText(url)) jsonObject.put("url", url);
            if (StringUtils.hasText(key)) jsonObject.put("key", key);
            if (created != null) jsonObject.put("created", DateUtils.dateTimeToString(created));
            if (updated != null) jsonObject.put("updated", DateUtils.dateTimeToString(updated));
            if (ArrayUtils.isNotEmpty(metadata)) jsonObject.put("metadata", ArrayUtils.mapToJsonObject(metadata));
            if (devices != null) jsonObject.put("devices", devices.toJsonObject());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void fromJson(JSONObject jsonObject) throws JSONException {
        id = jsonObject.optString("id", null);
        name = jsonObject.optString("name", null);
        description = jsonObject.optString("description", null);
        visibility = Visibility.parse(jsonObject.optString("visibility", null));
        status = jsonObject.optString("status", null);
        url = jsonObject.optString("url", null);
        key = jsonObject.optString("key", null);
        try {
            if (!jsonObject.isNull("created"))
                created = DateUtils.stringToDateTime(jsonObject.getString("created"));
            if (!jsonObject.isNull("updated"))
                updated = DateUtils.stringToDateTime(jsonObject.getString("updated"));
        } catch (ParseException e) {
            throw new JSONException(e.getMessage());
        }
        if (!jsonObject.isNull("metadata")) {
            metadata = ArrayUtils.jsonObjectToMap(jsonObject.getJSONObject("metadata"));
        }
        if (!jsonObject.isNull("devices")) {
            devices = Devices.fromJsonObject(jsonObject.getJSONObject("devices"));
        }
    }

    public static Distribution fromJsonObject(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) return null;
        Distribution distribution = new Distribution();
        distribution.fromJson(jsonObject);
        return distribution;
    }


    public static class Devices implements IModelObject {
        private Integer total;
        private Integer registered;
        private Integer unregistered;

        public Devices() {
        }

        public Devices(Integer total, Integer registered, Integer unregistered) {
            this.total = total;
            this.registered = registered;
            this.unregistered = unregistered;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Integer getRegistered() {
            return registered;
        }

        public void setRegistered(Integer registered) {
            this.registered = registered;
        }

        public Integer getUnregistered() {
            return unregistered;
        }

        public void setUnregistered(Integer unregistered) {
            this.unregistered = unregistered;
        }

        @Override
        public JSONObject toJsonObject() {
            JSONObject jsonObject = new JSONObject();
            try {
                if (total != null) jsonObject.put("total", total);
                if (registered != null) jsonObject.put("registered", registered);
                if (unregistered != null) jsonObject.put("unregistered", unregistered);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        public void fromJson(JSONObject jsonObject) throws JSONException {
            if (!jsonObject.isNull("total")) total = jsonObject.getInt("total");
            if (!jsonObject.isNull("registered")) total = jsonObject.getInt("registered");
            if (!jsonObject.isNull("unregistered")) total = jsonObject.getInt("unregistered");
        }

        public static Devices fromJsonObject(JSONObject jsonObject) throws JSONException {
            if (jsonObject == null) return null;
            Devices devices = new Devices();
            devices.fromJson(jsonObject);
            return devices;
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

    public static final int REQUEST_CODE_LIST_DISTRIBUTIONS = 2001;
    public static final int REQUEST_CODE_CREATE_DISTRIBUTION = 2002;
    public static final int REQUEST_CODE_VIEW_DISTRIBUTION_DETAILS = 2003;
    public static final int REQUEST_CODE_UPDATE_DISTRIBUTION_DETAILS = 2004;
    public static final int REQUEST_CODE_LIST_DEVICES = 2005;
    public static final int REQUEST_CODE_ADD_DEVICE = 2006;
    public static final int REQUEST_CODE_DELETE_DISTRIBUTION = 2007;
    public static final int REQUEST_CODE_LIST_DATA_STREAMS = 2008;
    public static final int REQUEST_CODE_CREATE_UPDATE_DATA_STREAMS = 2009;
    public static final int REQUEST_CODE_VIEW_DATA_STREAM = 2010;
    public static final int REQUEST_CODE_DELETE_DATA_STREAM = 2011;
    public static final int REQUEST_CODE_METADATA = 2012;
    public static final int REQUEST_CODE_UPDATE_METADATA = 2013;
    public static final int REQUEST_CODE_METADATA_FIELD = 2014;
    public static final int REQUEST_CODE_UPDATE_METADATA_FIELD = 2015;

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.DistributionService#listDistributions(Context, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution#List-Distributions">List Distributions</a>} endpoint
     * @param context The application Context.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void list(Context context, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                Constants.DISTRIBUTION_LIST,
                null,
                listener,
                REQUEST_CODE_LIST_DISTRIBUTIONS
        );
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.DistributionService#createDistribution(Context, Distribution, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution#Create-Distribution">Create-Distribution</a>} endpoint
     * @param context The application Context.
     * @param params as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void create(Context context, JSONObject params, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                Constants.DISTRIBUTION_CREATE,
                params,
                listener,
                REQUEST_CODE_CREATE_DISTRIBUTION
        );
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.DistributionService#viewDistributionDetails(Context, String, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution#View-Distribution-Details">View Distribution Details</a>} endpoint
     * @param context The application Context.
     * @param distributionId as String, ID of the distribution
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void viewDetails(Context context, String distributionId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_VIEW_DETAILS, distributionId),
                null,
                listener,
                REQUEST_CODE_VIEW_DISTRIBUTION_DETAILS
        );
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.DistributionService#updateDistributionDetails(Context, Distribution, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution#Update-Distribution-Details">Update Distribution Details</a>} endpoint
     * @param context The application Context.
     * @param params as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param distributionId as String, ID of the distribution.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void updateDetails(Context context, JSONObject params, String distributionId, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_UPDATE_DETAILS, distributionId),
                params,
                listener,
                REQUEST_CODE_UPDATE_DISTRIBUTION_DETAILS
        );
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.DistributionService#readDistributionMetadata(Context, Distribution, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution#Read-Distribution-Metadata">Read Distribution Metadata</a>} endpoint
     * @param context The application Context.
     * @param distributionId as String, ID of the distribution.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void metadata(Context context, String distributionId, ResponseListener listener){
        Metadata.metadata(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_METADATA, distributionId),
                listener,
                REQUEST_CODE_METADATA);
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.DistributionService#updateDistributionMetadata(Context, Distribution, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution#Update-Distribution-Metadata">Update Distribution Metadata</a>} endpoint
     * @param context The application Context.
     * @param distributionId as String, ID of the distribution.
     * @param body as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void updateMetadata(Context context, String distributionId, JSONObject body, ResponseListener listener){
        Metadata.updateMetadata(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_METADATA, distributionId),
                body,
                listener,
                REQUEST_CODE_UPDATE_METADATA);
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.DistributionService#readDistributionMetadataField(Context, Distribution, String, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/commands#Send-Command">Send Command</a>} endpoint
     * @param context The application Context.
     * @param distributionId as String, ID of the distribution.
     * @param field as String
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void metadataField(Context context, String distributionId, String field, ResponseListener listener){
        Metadata.metadataField(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_METADATA_FIELD, distributionId, field),
                listener,
                REQUEST_CODE_METADATA_FIELD);
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.DistributionService#updateDistributionMetadataField(Context, Distribution, String, String, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution#Read-Distribution-Metadata-Field">Read Distribution Metadata Field</a>} endpoint
     * @param context The application Context.
     * @param distributionId as String, ID of the distribution.
     * @param field as String
     * @param body as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void updateMetadataField(Context context, String distributionId, String field, JSONObject body, ResponseListener listener){
        Metadata.updateMetadataField(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_METADATA_FIELD, distributionId, field),
                body,
                listener,
                REQUEST_CODE_UPDATE_METADATA_FIELD);
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.DistributionService#listDistributionDevices(Context, Distribution, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution#List-Devices-from-an-existing-Distribution">List Devices from an existing Distribution</a>} endpoint
     * @param context The application Context.
     * @param distributionId as String, ID of the distribution.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void listDevices(Context context, String distributionId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_LIST_DEVICES, distributionId),
                null,
                listener,
                REQUEST_CODE_LIST_DEVICES
        );
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.DistributionService#addDeviceToDistribution(Context, Distribution, Device, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution#Add-Device-to-an-existing-Distribution">Add Devices to an existing Distribution</a>} endpoint
     * @param context The application Context.
     * @param params as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param distributionId as String, ID of the distribution.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void addDevice(Context context, JSONObject params, String distributionId, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_ADD_DEVICE, distributionId),
                params,
                listener,
                REQUEST_CODE_ADD_DEVICE
        );
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.DistributionService#deleteDistribution(Context, Distribution, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution#Delete-Distribution">Delete Distribution</a>} endpoint
     * @param context The application Context.
     * @param distributionId as String, ID of the distribution.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void delete(Context context, String distributionId, ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_DELETE, distributionId),
                (Map<String, String>)null,
                listener,
                REQUEST_CODE_DELETE_DISTRIBUTION
        );
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.DistributionService#listDataStreams(Context, Distribution, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution#List-Data-Streams">List Data Streams</a>} endpoint
     * @param context The application Context.
     * @param distributionId as String, ID of the distribution.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void listDataStreams(Context context, String distributionId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_LIST_DATA_STREAMS, distributionId),
                null,
                listener,
                REQUEST_CODE_LIST_DATA_STREAMS
        );
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.DistributionService#createUpdateDataStream(Context, Distribution, Stream, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution#Create-Update-Data-Stream">Create Update Data Stream</a>} endpoint
     * @param context The application Context.
     * @param params as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param distributionId as String, ID of the distribution.
     * @param streamName as String
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void createUpdateDataStream(Context context, JSONObject params, String distributionId, String streamName, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_CREATE_UPDATE_DATA_STREAMS, distributionId,streamName),
                params,
                listener,
                REQUEST_CODE_CREATE_UPDATE_DATA_STREAMS
        );
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.DistributionService#viewDataStream(Context, Distribution, String, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution#View-Data-Stream">View Data Stream</a>} endpoint
     * @param context The application Context.
     * @param distributionId as String, ID of the distribution.
     * @param streamName as String
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void viewDataStream(Context context, String distributionId, String streamName, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_VIEW_DATA_STREAMS, distributionId,streamName),
                null,
                listener,
                REQUEST_CODE_VIEW_DATA_STREAM
        );
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.DistributionService#deleteDataStream(Context, Distribution, Stream, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution#Delete-Data-Stream">Delete Data Stream</a>} endpoint
     * @param context The application Context.
     * @param distributionId as String, ID of the distribution.
     * @param streamName as String
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void deleteDataStream(Context context, String distributionId, String streamName, ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_DELETE_DATA_STREAMS, distributionId,streamName),
                (Map<String, String>) null,
                listener,
                REQUEST_CODE_DELETE_DATA_STREAM
        );
    }

}

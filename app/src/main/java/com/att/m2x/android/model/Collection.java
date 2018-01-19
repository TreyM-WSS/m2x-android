package com.att.m2x.android.model;

import android.content.Context;

import com.att.m2x.android.common.Constants;
import com.att.m2x.android.listeners.ResponseListener;
import com.att.m2x.android.listeners.TypedResponseListener;
import com.att.m2x.android.network.JsonRequest;
import com.att.m2x.android.services.model.ListCollectionsOptions;
import com.att.m2x.android.services.model.ListDevicesOptions;
import com.att.m2x.android.utils.ArrayUtils;
import com.att.m2x.android.utils.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Collections allow a user to organize Devices into nested groups. Each Device can belong to multiple Collections.
 * The Collections API makes it easy to manage Collections and to organize Devices into Collections.
 * Additionally, you can access all Devices contained within a Collection via the Device API using a Collection API Key.<p>
 *
 * Authentication:
 * All methods described in this page require that a Master API Key is specified in the X-M2X-KEY header.
 * Any attempt of using a Distribution or Device level API key would result in a 403 Forbidden response. {@see <a href="https://m2x.att.com/developer/documentation/v2/overview#API-Keys">Learn more about API Keys.</a>}
 */
public class Collection implements IModelObject {

    private String id;
    private String url;
    private String parent;
    private String name;
    private String description;
    private Integer devices;
    private Integer collections;
    private Set<String> tags;
    private Map<String, String> metadata;
    private String key;
    private Date created;
    private Date updated;

    public Collection() {
    }

    public Collection(String id) {
        this.id = id;
    }

    public Collection(String id, String url, String parent, String name, String description,
                      Integer devices, Integer collections, Set<String> tags,
                      Map<String, String> metadata, String key, Date created, Date updated) {
        this.id = id;
        this.url = url;
        this.parent = parent;
        this.name = name;
        this.description = description;
        this.devices = devices;
        this.collections = collections;
        this.tags = tags;
        this.metadata = metadata;
        this.key = key;
        this.created = created;
        this.updated = updated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
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

    public Integer getDevices() {
        return devices;
    }

    public void setDevices(Integer devices) {
        this.devices = devices;
    }

    public Integer getCollections() {
        return collections;
    }

    public void setCollections(Integer collections) {
        this.collections = collections;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
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

    @Override
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (id != null) jsonObject.put("id", id);
            if (parent != null) jsonObject.put("parent", parent);
            if (name != null) jsonObject.put("name", name);
            if (description != null) jsonObject.put("description", description);
            if (devices != null) jsonObject.put("devices", devices);
            if (collections != null) jsonObject.put("collections", collections);
            if (tags != null) jsonObject.put("tags", ArrayUtils.listToJsonStringArray(tags));
            if (metadata != null) jsonObject.put("metadata", ArrayUtils.mapToJsonObject(metadata));
            if (key != null) jsonObject.put("key", key);
            if (created != null) jsonObject.put("created", DateUtils.dateTimeToString(created));
            if (updated != null) jsonObject.put("updated", DateUtils.dateTimeToString(updated));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void fromJson(JSONObject jsonObject) throws JSONException {
        id = jsonObject.optString("id", null);
        url = jsonObject.optString("url", null);
        parent = jsonObject.optString("parent", null);
        name = jsonObject.optString("name", null);
        description = jsonObject.optString("description", null);
        if (!jsonObject.isNull("devices")) devices = jsonObject.getInt("devices");
        if (!jsonObject.isNull("collections")) collections = jsonObject.getInt("collections");
        if (!jsonObject.isNull("tags")) {
            tags = new HashSet<>();
            tags.addAll(ArrayUtils.jsonArrayToStringList(jsonObject.getJSONArray("tags")));
        }
        if (!jsonObject.isNull("metadata")) {
            metadata = ArrayUtils.jsonObjectToMap(jsonObject.getJSONObject("metadata"));
        }
        key = jsonObject.optString("key", null);
        try {
            if (!jsonObject.isNull("created"))
                created = DateUtils.stringToDateTime(jsonObject.getString("created"));
            if (!jsonObject.isNull("updated"))
                updated = DateUtils.stringToDateTime(jsonObject.getString("updated"));
        } catch (ParseException e) {
            throw new JSONException(e.getMessage());
        }

    }

    public static Collection fromJsonObject(JSONObject jsonObject) throws JSONException {
        Collection collection = new Collection();
        collection.fromJson(jsonObject);
        return collection;
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //
    //
    //  DEPRECATED STATIC METHODS ->
    //      MOVED TO CollectionService
    //
    //
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    public static final int REQUEST_CODE_LIST_COLLECTIONS = 6001;
    public static final int REQUEST_CODE_CREATE_COLLECTION = 6002;
    public static final int REQUEST_CODE_VIEW_COLLECTION_DETAILS = 6003;
    public static final int REQUEST_CODE_UPDATE_COLLECTION_DETAILS = 6004;
    public static final int REQUEST_CODE_LIST_DEVICES = 6005;
    public static final int REQUEST_CODE_METADATA = 6006;
    public static final int REQUEST_CODE_UPDATE_METADATA = 6007;
    public static final int REQUEST_CODE_METADATA_FIELD = 6008;
    public static final int REQUEST_CODE_UPDATE_METADATA_FIELD = 6009;
    public static final int REQUEST_CODE_DELETE_COLLECTION = 6010;
    public static final int REQUEST_CODE_ADD_DEVICE_TO_COLLECTION = 6011;
    public static final int REQUEST_CODE_DELETE_DEVICE_FROM_COLLECTION = 6012;

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.CollectionService#listCollections(Context, ListCollectionsOptions, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/collections#List-collections">List collections</a>} endpoint
     * @param context The application Context.
     * @param params Query parameters as HashMap<String,String>. View M2X API Docs for listing of available parameters.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void list(Context context, HashMap<String, String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                Constants.COLLECTION_LIST,
                params,
                listener,
                REQUEST_CODE_LIST_COLLECTIONS
        );
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.CollectionService#createCollection(Context, Collection, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/collections#Create-Collection">Create Collection</a>} endpoint
     * @param context The application Context.
     * @param body as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void create(Context context, JSONObject body, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                Constants.COLLECTION_CREATE,
                body,
                listener,
                REQUEST_CODE_CREATE_COLLECTION
        );
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.CollectionService#viewCollectionDetails(Context, String, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/collections#View-Collection-Details">View Collection Details</a>} endpoint
     * @param context The application Context.
     * @param collectionId as String, ID of the collection.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void viewDetails(Context context, String collectionId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US,Constants.COLLECTION_VIEW_DETAILS, collectionId),
                null,
                listener,
                REQUEST_CODE_VIEW_COLLECTION_DETAILS
        );
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.CollectionService#updateCollectionDetails(Context, Collection, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/collections#Update-Collection-Details">Update collections</a>} endpoint
     * @param context The application Context.
     * @param collectionId as String, ID of the collection.
     * @param body as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void updateDetails(Context context, String collectionId, JSONObject body, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US,Constants.COLLECTION_UPDATE_DETAILS,collectionId),
                body,
                listener,
                REQUEST_CODE_UPDATE_COLLECTION_DETAILS
        );
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.CollectionService#listCollectionDevices(Context, Collection, ListDevicesOptions, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/collections#List-Devices-from-an-existing-Collection">List Devices from an existing Collection</a>} endpoint
     * @param context The application Context.
     * @param collectionId as String, ID of the collection.
     * @param params Query parameters as HashMap<String,String>. View M2X API Docs for listing of available parameters. 
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void listDevices(Context context, String collectionId, HashMap<String, String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.COLLECTION_LIST_DEVICES, collectionId),
                params,
                listener,
                REQUEST_CODE_LIST_DEVICES
        );
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.CollectionService#readCollectionMetadata(Context, Collection, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/collections#Read-Collection-Metadata">Read Collection Metadata</a>} endpoint
     * @param context The application Context.
     * @param collectionId as String, ID of the collection.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void metadata(Context context, String collectionId, ResponseListener listener){
        Metadata.metadata(
                context,
                String.format(Locale.US, Constants.COLLECTION_METADATA, collectionId),
                listener,
                REQUEST_CODE_METADATA);
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.CollectionService#updateCollectionMetadata(Context, Collection, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/collections#Update-Collection-Metadata">Update Collection Metadata</a>} endpoint
     * @param context The application Context.
     * @param collectionId as String, ID of the collection.
     * @param body as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void updateMetadata(Context context, String collectionId, JSONObject body, ResponseListener listener){
        Metadata.updateMetadata(
                context,
                String.format(Locale.US, Constants.COLLECTION_METADATA, collectionId),
                body,
                listener,
                REQUEST_CODE_UPDATE_METADATA);
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.CollectionService#readCollectionMetadataField(Context, Collection, String, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/collections#Read-Collection-Metadata-Field">Read Collection Metadata Field</a>} endpoint
     * @param context The application Context.
     * @param collectionId as String, ID of the collection.
     * @param field as String
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void metadataField(Context context, String collectionId, String field, ResponseListener listener){
        Metadata.metadataField(
                context,
                String.format(Locale.US, Constants.COLLECTION_METADATA_FIELD, collectionId, field),
                listener,
                REQUEST_CODE_METADATA_FIELD);
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.CollectionService#updateCollectionMetadataField(Context, Collection, String, String, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/collections#Update-Collection-Metadata-Field">Update Collection Metadata Field</a>} endpoint
     * @param context The application Context.
     * @param collectionId as String, ID of the collection.
     * @param field as String
     * @param body as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void updateMetadataField(Context context, String collectionId, String field, JSONObject body, ResponseListener listener){
        Metadata.updateMetadataField(
                context,
                String.format(Locale.US, Constants.COLLECTION_METADATA_FIELD, collectionId, field),
                body,
                listener,
                REQUEST_CODE_UPDATE_METADATA_FIELD);
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.CollectionService#deleteCollection(Context, Collection, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/collections#Delete-Collection">Delete collections</a>} endpoint
     * @param context The application Context.
     * @param collectionId as String, ID of the collection.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void delete(Context context, String collectionId, ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US, Constants.COLLECTION_DELETE, collectionId),
                (Map<String, String>)null,
                listener,
                REQUEST_CODE_DELETE_COLLECTION
        );
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.CollectionService#removeDeviceFromCollection(Context, Collection, Device, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/collections#Remove-device-from-collection">Remove device from collection</a>} endpoint
     * @param context The application Context.
     * @param collectionId as String, ID of the collection.
     * @param deviceId as String, ID of the device.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void deleteDeviceFromCollection(Context context, String collectionId, String deviceId, ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US, Constants.COLLECTION_DELETE_DEVICE_FROM_COLLECTION, collectionId, deviceId),
                (Map<String, String>) null,
                listener,
                REQUEST_CODE_DELETE_DEVICE_FROM_COLLECTION
        );
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.CollectionService#addDeviceToCollection(Context, Collection, Device, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/collections#Add-device-to-collection">Add device to collection</a>} endpoint
     * @param context The application Context.
     * @param collectionId as String, ID of the collection.
     * @param deviceId as String, ID of the device.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void addDeviceToCollection(Context context, String collectionId, String deviceId, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.COLLECTION_ADD_DEVICE_TO_COLLECTION, collectionId, deviceId),
                null,
                listener,
                REQUEST_CODE_ADD_DEVICE_TO_COLLECTION
        );
    }
}

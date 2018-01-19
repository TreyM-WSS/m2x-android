package com.att.m2x.android.services;

import android.content.Context;

import com.att.m2x.android.common.Constants;
import com.att.m2x.android.listeners.ResponseListener;
import com.att.m2x.android.listeners.TypedResponseListener;
import com.att.m2x.android.model.Collection;
import com.att.m2x.android.model.Device;
import com.att.m2x.android.model.Metadata;
import com.att.m2x.android.network.JsonRequest;
import com.att.m2x.android.services.BaseResponseListener.EmptyResponseListener;
import com.att.m2x.android.services.BaseResponseListener.MapResponseListener;
import com.att.m2x.android.services.BaseResponseListener.StringResponseListener;
import com.att.m2x.android.services.model.ListCollectionsOptions;
import com.att.m2x.android.services.model.ListDevicesOptions;
import com.att.m2x.android.utils.ArrayUtils;
import com.att.m2x.android.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Collections allow a user to organize Devices into nested groups. Each Device can belong to
 * multiple Collections. The Collections API makes it easy to manage Collections and to organize
 * Devices into Collections. Additionally, you can access all Devices contained within a Collection
 * via the <a href="https://m2x.att.com/developer/documentation/v2/device">Device API</a> using a
 * Collection API Key.
 * {@see <a href="https://m2x.att.com/developer/documentation/v2/collections">M2X Collections REST API</a>}
 */
public class CollectionService {

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

    private CollectionService() {
        // don't instantiate
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/collections#List-collections">List Collections</a> endpoint
     * @param context
     * @param options
     * @param listener
     */
    public static void listCollections(Context context, ListCollectionsOptions options,
                            TypedResponseListener<List<Collection>> listener) {
        listCollections(context, options.toMap(), new CollectionListListener(listener));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/collections#List-collections">List Collections</a> endpoint
     * @param context
     * @param params
     * @param listener
     */
    public static void listCollections(Context context, Map<String, String> params,
                                       ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                Constants.COLLECTION_LIST,
                params,
                listener,
                REQUEST_CODE_LIST_COLLECTIONS
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/collections#Create-Collection">Create Collection</a> endpoint
     * @param context
     * @param collection
     * @param listener
     */
    public static void createCollection(Context context, Collection collection,
                                        TypedResponseListener<Collection> listener) {
        JSONObject body = new JSONObject();
        try {
            if (collection.getName() != null) body.put("name", collection.getName());
            if (collection.getDescription() != null) body.put("description", collection.getDescription());
            if (collection.getParent() != null) body.put("parent", collection.getParent());
            if (ArrayUtils.isNotEmpty(collection.getTags()))
                body.put("tags", StringUtils.join(collection.getTags(), ","));
            if (ArrayUtils.isNotEmpty(collection.getMetadata())) {
                body.put("metadata", ArrayUtils.mapToJsonObject(collection.getMetadata()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        createCollection(context, body, new CollectionListener(listener));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/collections#Create-Collection">Create Collection</a> endpoint
     * @param context
     * @param body
     * @param listener
     */
    public static void createCollection(Context context, JSONObject body, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                Constants.COLLECTION_CREATE,
                body,
                listener,
                REQUEST_CODE_CREATE_COLLECTION
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/collections#View-Collection-Details>View Collection Details</a> endpoint
     * @param context
     * @param collectionId
     * @param listener
     */
    public static void viewCollectionDetails(Context context, String collectionId,
                                             TypedResponseListener<Collection> listener) {
        viewCollectionDetails(context, collectionId, new CollectionListener(listener));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/collections#View-Collection-Details>View Collection Details</a> endpoint
     * @param context
     * @param collectionId
     * @param listener
     */
    public static void viewCollectionDetails(Context context, String collectionId, ResponseListener listener) {
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US,Constants.COLLECTION_VIEW_DETAILS, collectionId),
                null,
                listener,
                REQUEST_CODE_VIEW_COLLECTION_DETAILS
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/collections#Update-Collection-Details">Update Collection Details</a> endpoint
     * @param context
     * @param collection
     * @param listener
     */
    public static void updateCollectionDetails(Context context, Collection collection,
                                        TypedResponseListener<Void> listener) {
        JSONObject body = new JSONObject();
        try {
            if (collection.getName() != null) body.put("name", collection.getName());
            if (collection.getDescription() != null) body.put("description", collection.getDescription());
            if (collection.getParent() != null) body.put("parent", collection.getParent());
            if (ArrayUtils.isNotEmpty(collection.getTags()))
                body.put("tags", StringUtils.join(collection.getTags(), ","));
            if (ArrayUtils.isNotEmpty(collection.getMetadata())) {
                body.put("metadata", ArrayUtils.mapToJsonObject(collection.getMetadata()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        updateCollectionDetails(context, collection.getId(), body, new EmptyResponseListener(listener));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/collections#Update-Collection-Details">Update Collection Details</a> endpoint
     * @param context
     * @param collectionId
     * @param body
     * @param listener
     */
    public static void updateCollectionDetails(Context context, String collectionId, JSONObject body, ResponseListener listener) {
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US,Constants.COLLECTION_UPDATE_DETAILS,collectionId),
                body,
                listener,
                REQUEST_CODE_UPDATE_COLLECTION_DETAILS
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/collections#List-Devices-from-an-existing-Collection>List Devices</a> endpoint.
     * @param context
     * @param collection
     * @param options
     * @param listener
     */
    public static void listCollectionDevices(Context context, Collection collection,
                                             ListDevicesOptions options,
                                             TypedResponseListener<List<Device>> listener) {
        listCollectionDevices(context, collection.getId(), options.toMap(), new DeviceListResponseListener(listener));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/collections#List-Devices-from-an-existing-Collection>List Devices</a> endpoint.
     * @param context
     * @param collectionId
     * @param params
     * @param listener
     */
    public static void listCollectionDevices(Context context, String collectionId, Map<String, String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.COLLECTION_LIST_DEVICES, collectionId),
                params,
                listener,
                REQUEST_CODE_LIST_DEVICES
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/collections#Add-device-to-collection">Add Device to Collection</a> endpoint.
     * @param context
     * @param collection
     * @param device
     * @param listener
     */
    public static void addDeviceToCollection(Context context, Collection collection, Device device,
                                             TypedResponseListener<Void> listener) {
        addDeviceToCollection(context, collection.getId(), device.getId(), new EmptyResponseListener(listener));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/collections#Add-device-to-collection">Add Device to Collection</a> endpoint.
     * @param context
     * @param collectionId
     * @param deviceId
     * @param listener
     */
    public static void addDeviceToCollection(Context context, String collectionId, String deviceId, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.COLLECTION_ADD_DEVICE_TO_COLLECTION, collectionId, deviceId),
                null,
                listener,
                REQUEST_CODE_ADD_DEVICE_TO_COLLECTION
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/collections#Remove-device-from-collection">Remove Device From Collection</a> endpoint.
     * @param context
     * @param collection
     * @param device
     * @param listener
     */
    public static void removeDeviceFromCollection(Context context, Collection collection,
                                                  Device device, TypedResponseListener<Void> listener) {
        removeDeviceFromCollection(context, collection.getId(), device.getId(), new EmptyResponseListener(listener));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/collections#Remove-device-from-collection">Remove Device From Collection</a> endpoint.
     * @param context
     * @param collectionId
     * @param deviceId
     * @param listener
     */
    public static void removeDeviceFromCollection(Context context, String collectionId, String deviceId, ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US, Constants.COLLECTION_DELETE_DEVICE_FROM_COLLECTION, collectionId, deviceId),
                (Map<String, String>) null,
                listener,
                REQUEST_CODE_DELETE_DEVICE_FROM_COLLECTION
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/collections#Read-Collection-Metadata">Read Collection Metadata</a> endpoint
     * @param context
     * @param collection
     * @param listener
     */
    public static void readCollectionMetadata(Context context, Collection collection,
                                             TypedResponseListener<Map<String, String>> listener) {
        readCollectionMetadata(context, collection.getId(), new MapResponseListener(listener));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/collections#Read-Collection-Metadata">Read Collection Metadata</a> endpoint
     * @param context
     * @param collectionId
     * @param listener
     */
    public static void readCollectionMetadata(Context context, String collectionId,
                                             ResponseListener listener){
        Metadata.metadata(
                context,
                String.format(Locale.US, Constants.COLLECTION_METADATA, collectionId),
                listener,
                REQUEST_CODE_METADATA);
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/collections#Update-Collection-Metadata">Update Collection Metadata</a> endpoint
     * @param context
     * @param collection
     * @param listener
     */
    public static void updateCollectionMetadata(Context context, Collection collection,
                                         TypedResponseListener<Void> listener) {
        JSONObject body = null;
        try {
            body = ArrayUtils.mapToJsonObject(collection.getMetadata());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        updateCollectionMetadata(context, collection.getId(), body, new EmptyResponseListener(listener));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/collections#Update-Collection-Metadata">Update Collection Metadata</a> endpoint
     * @param context
     * @param collectionId
     * @param body
     * @param listener
     */
    public static void updateCollectionMetadata(Context context, String collectionId, JSONObject body, ResponseListener listener){
        Metadata.updateMetadata(
                context,
                String.format(Locale.US, Constants.COLLECTION_METADATA, collectionId),
                body,
                listener,
                REQUEST_CODE_UPDATE_METADATA);
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/collections#Read-Collection-Metadata-Field">Read Collection Metadata Field</a> endpoint
     * @param context
     * @param collection
     * @param field
     * @param listener
     */
    public static void readCollectionMetadataField(Context context, Collection collection,
                                                  String field, TypedResponseListener<String> listener) {
        readCollectionMetadataField(context, collection.getId(), field, new StringResponseListener(listener, "value"));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/collections#Read-Collection-Metadata-Field">Read Collection Metadata Field</a> endpoint
     * @param context
     * @param collectionId
     * @param field
     * @param listener
     */
    public static void readCollectionMetadataField(Context context, String collectionId, String field, ResponseListener listener){
        Metadata.metadataField(
                context,
                String.format(Locale.US, Constants.COLLECTION_METADATA_FIELD, collectionId, field),
                listener,
                REQUEST_CODE_METADATA_FIELD);
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/collections#Update-Collection-Metadata-Field">Update Collection Metadata Field</a>} endpoint
     * @param context
     * @param collection
     * @param field
     * @param value
     * @param listener
     */
    public static void updateCollectionMetadataField(Context context, Collection collection,
                                                     String field, String value,
                                                     TypedResponseListener<Void> listener) {
        JSONObject body = new JSONObject();
        try {
            body.put("value", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        updateCollectionMetadataField(context, collection.getId(), field, body, new EmptyResponseListener(listener));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/collections#Update-Collection-Metadata-Field">Update Collection Metadata Field</a> endpoint
     * @param context
     * @param collectionId
     * @param field
     * @param body
     * @param listener
     */
    public static void updateCollectionMetadataField(Context context, String collectionId, String field, JSONObject body, ResponseListener listener){
        Metadata.updateMetadataField(
                context,
                String.format(Locale.US, Constants.COLLECTION_METADATA_FIELD, collectionId, field),
                body,
                listener,
                REQUEST_CODE_UPDATE_METADATA_FIELD);
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/collections#Delete-Collection">Delete Collection</a> endpoint.
     * @param context
     * @param collection
     * @param listener
     */
    public static void deleteCollection(Context context, Collection collection,
                                        TypedResponseListener<Void> listener) {
        deleteCollection(context, collection.getId(), new EmptyResponseListener(listener));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/collections#Delete-Collection">Delete Collection</a> endpoint.
     * @param context
     * @param collectionId
     * @param listener
     */
    public static void deleteCollection(Context context, String collectionId, ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US, Constants.COLLECTION_DELETE, collectionId),
                (Map<String, String>)null,
                listener,
                REQUEST_CODE_DELETE_COLLECTION
        );
    }




    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    //// RESPONSE LISTENERS
    ////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static class CollectionListListener extends BaseResponseListener<List<Collection>> {

        CollectionListListener(TypedResponseListener<List<Collection>> listener) {
            super(listener);
        }

        @Override
        List<Collection> parseResponse(JSONObject jsonObject) throws JSONException, ParseException {
            JSONArray collectionsArray = jsonObject.getJSONArray("collections");
            List<Collection> collectionList = new ArrayList<>();
            for (int i = 0; i < collectionsArray.length(); ++i) {
                collectionList.add(Collection.fromJsonObject(collectionsArray.getJSONObject(i)));
            }
            return collectionList;
        }
    }

    private static class CollectionListener extends BaseResponseListener<Collection> {
        CollectionListener(TypedResponseListener<Collection> listener) {
            super(listener);
        }

        @Override
        Collection parseResponse(JSONObject jsonObject) throws JSONException, ParseException {
            return Collection.fromJsonObject(jsonObject);
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
}

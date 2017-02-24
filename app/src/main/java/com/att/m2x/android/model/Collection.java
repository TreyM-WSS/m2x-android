package com.att.m2x.android.model;

import android.content.Context;

import com.att.m2x.android.common.Constants;
import com.att.m2x.android.listeners.ResponseListener;
import com.att.m2x.android.network.JsonRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;

/**
 *
 * Collections allow a user to organize Devices into nested groups. Each Device can belong to multiple Collections.
 * The Collections API makes it easy to manage Collections and to organize Devices into Collections.
 * Additionally, you can access all Devices contained within a Collection via the Device API using a Collection API Key.<p>
 *
 * Authentication:
 * All methods described in this page require that a Master API Key is specified in the X-M2X-KEY header.
 * Any attempt of using a Distribution or Device level API key would result in a 403 Forbidden response. {@see <a href="https://m2x.att.com/developer/documentation/v2/overview#API-Keys">Learn more about API Keys.</a>}
 */
public class Collection {

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
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/collections#List-collections">List collections</a>} endpoint
     * @param context The application Context.
     * @param params Query parameters as HashMap<String,String>. View M2X API Docs for listing of available parameters.
     * @param listener Http responseListener {@link ResponseListener}
     */
    public static final void list(Context context, HashMap<String, String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                Constants.COLLECTION_LIST,
                params,
                listener,
                REQUEST_CODE_LIST_COLLECTIONS
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/collections#Create-Collection">Create Collection</a>} endpoint
     * @param context The application Context.
     * @param body as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener Http responseListener {@link ResponseListener}
     */
    public static final void create(Context context, JSONObject body, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                Constants.COLLECTION_CREATE,
                body,
                listener,
                REQUEST_CODE_CREATE_COLLECTION
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/collections#View-Collection-Details">View Collection Details</a>} endpoint
     * @param context The application Context.
     * @param collectionId as String, ID of the collection.
     * @param listener Http responseListener {@link ResponseListener}
     */
    public static final void viewDetails(Context context, String collectionId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US,Constants.COLLECTION_VIEW_DETAILS, collectionId),
                null,
                listener,
                REQUEST_CODE_VIEW_COLLECTION_DETAILS
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/collections#Update-Collection-Details">Update collections</a>} endpoint
     * @param context The application Context.
     * @param collectionId as String, ID of the collection.
     * @param body as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener Http responseListener {@link ResponseListener}
     */
    public static final void updateDetails(Context context, String collectionId, JSONObject body, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US,Constants.COLLECTION_UPDATE_DETAILS,collectionId),
                body,
                listener,
                REQUEST_CODE_UPDATE_COLLECTION_DETAILS
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/collections#List-Devices-from-an-existing-Collection">List Devices from an existing Collection</a>} endpoint
     * @param context The application Context.
     * @param collectionId as String, ID of the collection.
     * @param params Query parameters as HashMap<String,String>. View M2X API Docs for listing of available parameters. 
     * @param listener Http responseListener {@link ResponseListener}
     */
    public static final void listDevices(Context context, String collectionId, HashMap<String, String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.COLLECTION_LIST_DEVICES, collectionId),
                params,
                listener,
                REQUEST_CODE_LIST_DEVICES
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/collections#Read-Collection-Metadata">Read Collection Metadata</a>} endpoint
     * @param context The application Context.
     * @param collectionId as String, ID of the collection.
     * @param listener Http responseListener {@link ResponseListener}
     */
    public static final void metadata(Context context, String collectionId, ResponseListener listener){
        Metadata.metadata(
                context,
                String.format(Locale.US, Constants.COLLECTION_METADATA, collectionId),
                listener,
                REQUEST_CODE_METADATA);
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/collections#Update-Collection-Metadata">Update Collection Metadata</a>} endpoint
     * @param context The application Context.
     * @param collectionId as String, ID of the collection.
     * @param body as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener Http responseListener {@link ResponseListener}
     */
    public static final void updateMetadata(Context context, String collectionId, JSONObject body, ResponseListener listener){
        Metadata.updateMetadata(
                context,
                String.format(Locale.US, Constants.COLLECTION_METADATA, collectionId),
                body,
                listener,
                REQUEST_CODE_UPDATE_METADATA);
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/collections#Read-Collection-Metadata-Field">Read Collection Metadata Field</a>} endpoint
     * @param context The application Context.
     * @param collectionId as String, ID of the collection.
     * @param field as String
     * @param listener Http responseListener {@link ResponseListener}
     */
    public static final void metadataField(Context context, String collectionId, String field, ResponseListener listener){
        Metadata.metadataField(
                context,
                String.format(Locale.US, Constants.COLLECTION_METADATA_FIELD, collectionId, field),
                listener,
                REQUEST_CODE_METADATA_FIELD);
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/collections#Update-Collection-Metadata-Field">Update Collection Metadata Field</a>} endpoint
     * @param context The application Context.
     * @param collectionId as String, ID of the collection.
     * @param field as String
     * @param body as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener Http responseListener {@link ResponseListener}
     */
    public static final void updateMetadataField(Context context, String collectionId, String field, JSONObject body, ResponseListener listener){
        Metadata.updateMetadataField(
                context,
                String.format(Locale.US, Constants.COLLECTION_METADATA_FIELD, collectionId, field),
                body,
                listener,
                REQUEST_CODE_UPDATE_METADATA_FIELD);
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/collections#Delete-Collection">Delete collections</a>} endpoint
     * @param context The application Context.
     * @param collectionId as String, ID of the collection.
     * @param listener Http responseListener {@link ResponseListener}
     */
    public static final void delete(Context context, String collectionId, ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US, Constants.COLLECTION_DELETE, collectionId),
                null,
                listener,
                REQUEST_CODE_DELETE_COLLECTION
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/collections#Remove-device-from-collection">Remove device from collection</a>} endpoint
     * @param context The application Context.
     * @param collectionId as String, ID of the collection.
     * @param deviceId as String, ID of the device.
     * @param listener Http responseListener {@link ResponseListener}
     */
    public static final void deleteDeviceFromCollection(Context context, String collectionId, String deviceId, ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US, Constants.COLLECTION_DELETE_DEVICE_FROM_COLLECTION, collectionId, deviceId),
                null,
                listener,
                REQUEST_CODE_DELETE_DEVICE_FROM_COLLECTION
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/collections#Add-device-to-collection">Add device to collection</a>} endpoint
     * @param context The application Context.
     * @param collectionId as String, ID of the collection.
     * @param deviceId as String, ID of the device.
     * @param listener Http responseListener {@link ResponseListener}
     */
    public static final void addDeviceToCollection(Context context, String collectionId, String deviceId, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.COLLECTION_ADD_DEVICE_TO_COLLECTION, collectionId, deviceId),
                null,
                listener,
                REQUEST_CODE_ADD_DEVICE_TO_COLLECTION
        );
    }
}

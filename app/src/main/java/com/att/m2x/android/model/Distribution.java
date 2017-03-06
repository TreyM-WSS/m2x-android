package com.att.m2x.android.model;

import android.content.Context;

import com.att.m2x.android.common.Constants;
import com.att.m2x.android.listeners.ResponseListener;
import com.att.m2x.android.network.JsonRequest;

import org.json.JSONObject;

import java.util.Locale;

/**
 *
 * The Distribution API allows you to manage device distributions, allowing you
 * to take a device from prototype to market. Distributions represent a group of Devices that begin life with the attributes of the original device template when created.<p>
 *
 * Authentication:
 * All methods described in this page require that a Master API Key is specified in the X-M2X-KEY header.
 * Any attempt of using a Distribution or Device level API key would result in a 403 Forbidden response. {@see <a href="https://m2x.att.com/developer/documentation/v2/overview#API-Keys">Learn more about API Keys.</a>}
 */
public class Distribution {

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
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution#List-Distributions">List Distributions</a>} endpoint
     * @param context The application Context.
     * @param listener {@link ResponseListener}
     */
    public static final void list(Context context, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                Constants.DISTRIBUTION_LIST,
                null,
                listener,
                REQUEST_CODE_LIST_DISTRIBUTIONS
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution#Create-Distribution">Create-Distribution</a>} endpoint
     * @param context The application Context.
     * @param params as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener {@link ResponseListener}
     */
    public static final void create(Context context, JSONObject params, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                Constants.DISTRIBUTION_CREATE,
                params,
                listener,
                REQUEST_CODE_CREATE_DISTRIBUTION
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution#View-Distribution-Details">View Distribution Details</a>} endpoint
     * @param context The application Context.
     * @param distributionId as String, ID of the distribution
     * @param listener {@link ResponseListener}
     */
    public static final void viewDetails(Context context, String distributionId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_VIEW_DETAILS, distributionId),
                null,
                listener,
                REQUEST_CODE_VIEW_DISTRIBUTION_DETAILS
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution#Update-Distribution-Details">Update Distribution Details</a>} endpoint
     * @param context The application Context.
     * @param params as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param distributionId as String, ID of the distribution.
     * @param listener {@link ResponseListener}
     */
    public static final void updateDetails(Context context, JSONObject params, String distributionId, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_UPDATE_DETAILS, distributionId),
                params,
                listener,
                REQUEST_CODE_UPDATE_DISTRIBUTION_DETAILS
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution#Read-Distribution-Metadata">Read Distribution Metadata</a>} endpoint
     * @param context The application Context.
     * @param distributionId as String, ID of the distribution.
     * @param listener {@link ResponseListener}
     */
    public static final void metadata(Context context, String distributionId, ResponseListener listener){
        Metadata.metadata(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_METADATA, distributionId),
                listener,
                REQUEST_CODE_METADATA);
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution#Update-Distribution-Metadata">Update Distribution Metadata</a>} endpoint
     * @param context The application Context.
     * @param distributionId as String, ID of the distribution.
     * @param body as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener {@link ResponseListener}
     */
    public static final void updateMetadata(Context context, String distributionId, JSONObject body, ResponseListener listener){
        Metadata.updateMetadata(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_METADATA, distributionId),
                body,
                listener,
                REQUEST_CODE_UPDATE_METADATA);
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/commands#Send-Command">Send Command</a>} endpoint
     * @param context The application Context.
     * @param distributionId as String, ID of the distribution.
     * @param field as String
     * @param listener {@link ResponseListener}
     */
    public static final void metadataField(Context context, String distributionId, String field, ResponseListener listener){
        Metadata.metadataField(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_METADATA_FIELD, distributionId, field),
                listener,
                REQUEST_CODE_METADATA_FIELD);
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution#Read-Distribution-Metadata-Field">Read Distribution Metadata Field</a>} endpoint
     * @param context The application Context.
     * @param distributionId as String, ID of the distribution.
     * @param field as String
     * @param body as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener {@link ResponseListener}
     */
    public static final void updateMetadataField(Context context, String distributionId, String field, JSONObject body, ResponseListener listener){
        Metadata.updateMetadataField(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_METADATA_FIELD, distributionId, field),
                body,
                listener,
                REQUEST_CODE_UPDATE_METADATA_FIELD);
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution#List-Devices-from-an-existing-Distribution">List Devices from an existing Distribution</a>} endpoint
     * @param context The application Context.
     * @param distributionId as String, ID of the distribution.
     * @param listener {@link ResponseListener}
     */
    public static final void listDevices(Context context, String distributionId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_LIST_DEVICES, distributionId),
                null,
                listener,
                REQUEST_CODE_LIST_DEVICES
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution#Add-Device-to-an-existing-Distribution">Add Devices to an existing Distribution</a>} endpoint
     * @param context The application Context.
     * @param params as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param distributionId as String, ID of the distribution.
     * @param listener {@link ResponseListener}
     */
    public static final void addDevice(Context context, JSONObject params, String distributionId, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_ADD_DEVICE, distributionId),
                params,
                listener,
                REQUEST_CODE_ADD_DEVICE
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution#Delete-Distribution">Delete Distribution</a>} endpoint
     * @param context The application Context.
     * @param distributionId as String, ID of the distribution.
     * @param listener {@link ResponseListener}
     */
    public static final void delete(Context context, String distributionId, ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_DELETE, distributionId),
                null,
                listener,
                REQUEST_CODE_DELETE_DISTRIBUTION
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution#List-Data-Streams">List Data Streams</a>} endpoint
     * @param context The application Context.
     * @param distributionId as String, ID of the distribution.
     * @param listener {@link ResponseListener}
     */
    public static final void listDataStreams(Context context, String distributionId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_LIST_DATA_STREAMS, distributionId),
                null,
                listener,
                REQUEST_CODE_LIST_DATA_STREAMS
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution#Create-Update-Data-Stream">Create Update Data Stream</a>} endpoint
     * @param context The application Context.
     * @param params as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param distributionId as String, ID of the distribution.
     * @param streamName as String
     * @param listener {@link ResponseListener}
     */
    public static final void createUpdateDataStream(Context context, JSONObject params, String distributionId, String streamName, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_CREATE_UPDATE_DATA_STREAMS, distributionId,streamName),
                params,
                listener,
                REQUEST_CODE_CREATE_UPDATE_DATA_STREAMS
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution#View-Data-Stream">View Data Stream</a>} endpoint
     * @param context The application Context.
     * @param distributionId as String, ID of the distribution.
     * @param streamName as String
     * @param listener {@link ResponseListener}
     */
    public static final void viewDataStream(Context context, String distributionId, String streamName, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_VIEW_DATA_STREAMS, distributionId,streamName),
                null,
                listener,
                REQUEST_CODE_VIEW_DATA_STREAM
        );
    }

    /**
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution#Delete-Data-Stream">Delete Data Stream</a>} endpoint
     * @param context The application Context.
     * @param distributionId as String, ID of the distribution.
     * @param streamName as String
     * @param listener {@link ResponseListener}
     */
    public static final void deleteDataStream(Context context, String distributionId, String streamName, ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_DELETE_DATA_STREAMS, distributionId,streamName),
                null,
                listener,
                REQUEST_CODE_DELETE_DATA_STREAM
        );
    }

}

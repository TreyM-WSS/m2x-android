package com.att.m2x.android.services;

import android.content.Context;

import com.att.m2x.android.common.Constants;
import com.att.m2x.android.listeners.ResponseListener;
import com.att.m2x.android.listeners.TypedResponseListener;
import com.att.m2x.android.model.Stream;
import com.att.m2x.android.model.Device;
import com.att.m2x.android.model.Distribution;
import com.att.m2x.android.model.Metadata;
import com.att.m2x.android.network.JsonRequest;
import com.att.m2x.android.services.BaseResponseListener.EmptyResponseListener;
import com.att.m2x.android.services.BaseResponseListener.MapResponseListener;
import com.att.m2x.android.services.model.DistributionSearch;
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

import static com.att.m2x.android.services.BaseResponseListener.StringResponseListener;

/**
 * The Distribution API allows you to manage device distributions, allowing you to take a device
 * from prototype to market. Distributions represent a group of Devices that begin life with the
 * attributes of the original device template when created.
 * {@see <a href="https://m2x.att.com/developer/documentation/v2/distribution">M2X Distribution API</a>}
 */
public class DistributionService {

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
    public static final int REQUEST_CODE_SEARCH_DISTRIBUTIONS = 2016;

    private DistributionService() {
        // don't instantiate
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/distribution#List-Distributions">List Distributions</a> endpoint.
     * @param context
     * @param listener
     */
    public static void listDistributions(Context context,
                                         TypedResponseListener<List<Distribution>> listener) {
        listDistributions(context, new DistributionListResponseListener(listener));
    }
    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/distribution#List-Distributions">List Distributions</a> endpoint.
     * @param context
     * @param listener
     */
    public static void listDistributions(Context context, ResponseListener listener) {
        JsonRequest.makeGetRequest(
                context,
                Constants.DISTRIBUTION_LIST,
                null,
                listener,
                REQUEST_CODE_LIST_DISTRIBUTIONS
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/distribution#Search-Distributions">Search</a> endpoint
     * @param context
     * @param distributionSearch
     * @param listener
     */
    public static void searchDistributions(Context context, DistributionSearch distributionSearch,
                                           TypedResponseListener<List<Distribution>> listener) {
        JsonRequest.makePostRequest(
                context,
                Constants.DISTRIBUTION_SEARCH,
                distributionSearch.toJsonObject(),
                new DistributionListResponseListener(listener),
                REQUEST_CODE_SEARCH_DISTRIBUTIONS
        );
    }



    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/distribution#Create-Distribution">Create Distribution</a> endpoint
     * @param context
     * @param distribution
     * @param listener
     */
    public static void createDistribution(Context context, Distribution distribution,
                                          TypedResponseListener<Distribution> listener) {
        createDistribution(context, distribution.toJsonObject(), new DistributionResponseListener(listener));
    }

    @Deprecated
    public static void createDistribution(Context context, JSONObject params, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                Constants.DISTRIBUTION_CREATE,
                params,
                listener,
                REQUEST_CODE_CREATE_DISTRIBUTION
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/distribution#View-Distribution-Details>View Distribution Details</a> endpoint
     * @param context
     * @param distributionId
     * @param listener
     */
    public static void viewDistributionDetails(Context context, String distributionId,
                                               TypedResponseListener<Distribution> listener) {
        viewDistributionDetails(context, distributionId, new DistributionResponseListener(listener));
    }

    @Deprecated
    public static void viewDistributionDetails(Context context, String distributionId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_VIEW_DETAILS, distributionId),
                null,
                listener,
                REQUEST_CODE_VIEW_DISTRIBUTION_DETAILS
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/distribution#Update-Distribution-Details">Update Distribution Details</a> endpoint
     * @param context
     * @param distribution
     * @param listener
     */
    public static void updateDistributionDetails(Context context, Distribution distribution,
                                                 TypedResponseListener<Void> listener) {
        JSONObject body = new JSONObject();
        try {
            if (StringUtils.hasText(distribution.getName())) body.put("name", distribution.getName());
            if (StringUtils.hasText(distribution.getDescription())) body.put("description", distribution.getDescription());
            if (distribution.getVisibility() != null) body.put("visibility", distribution.getVisibility().visibility());
            if (distribution.getMetadata() != null) body.put("metadata", ArrayUtils.mapToJsonObject(distribution.getMetadata()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        updateDistributionDetails(context, body, distribution.getId(), new EmptyResponseListener(listener));
    }

    @Deprecated
    public static void updateDistributionDetails(Context context, JSONObject params,
                                                 String distributionId, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_UPDATE_DETAILS, distributionId),
                params,
                listener,
                REQUEST_CODE_UPDATE_DISTRIBUTION_DETAILS
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/distribution#Read-Distribution-Metadata">Read Distribution Metadata</a> endpoint
     * @param context
     * @param distribution
     * @param listener
     */
    public static void readDistributionMetadata(Context context, Distribution distribution,
                                                TypedResponseListener<Map<String, String>> listener) {
        readDistributionMetadata(context, distribution.getId(), new MapResponseListener(listener));
    }

    @Deprecated
    public static void readDistributionMetadata(Context context, String distributionId,
                                                ResponseListener listener) {
        Metadata.metadata(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_METADATA, distributionId),
                listener,
                REQUEST_CODE_METADATA);
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/distribution#Update-Distribution-Metadata">Update Distribution Metadata</a>
     * @param context
     * @param distribution
     * @param listener
     */
    public static void updateDistributionMetadata(Context context, Distribution distribution,
                                                  TypedResponseListener<Void> listener) {

        JSONObject body = null;
        try {
            body = ArrayUtils.mapToJsonObject(distribution.getMetadata());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        updateDistributionMetadata(context, distribution.getId(), body, new EmptyResponseListener(listener));
    }

    @Deprecated
    public static void updateDistributionMetadata(Context context, String distributionId,
                                                  JSONObject body, ResponseListener listener){
        Metadata.updateMetadata(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_METADATA, distributionId),
                body,
                listener,
                REQUEST_CODE_UPDATE_METADATA);
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/distribution#Read-Distribution-Metadata-Field">Read Distribution Metadata Field</a> endpoint
     * @param context
     * @param distribution
     * @param field
     * @param listener
     */
    public static void readDistributionMetadataField(Context context, Distribution distribution,
                                                    String field, TypedResponseListener<String> listener) {
        readDistributionMetadataField(context, distribution.getId(), field, new StringResponseListener(listener, "value"));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/distribution#Read-Distribution-Metadata-Field">Read Distribution Metadata Field</a> endpoint
     * @param context
     * @param distributionId
     * @param field
     * @param listener
     */
    public static void readDistributionMetadataField(Context context, String distributionId,
                                                    String field, ResponseListener listener){
        Metadata.metadataField(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_METADATA_FIELD, distributionId, field),
                listener,
                REQUEST_CODE_METADATA_FIELD);
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/distribution#Update-Distribution-Metadata-Field>Update Distribution Metadata Field</a> endpoint
     * @param context
     * @param distribution
     * @param field
     * @param listener
     */
    public static void updateDistributionMetadataField(Context context, Distribution distribution,
                                                       String field, String value,
                                                       TypedResponseListener<Void> listener) {
        JSONObject body = new JSONObject();
        try {
            body.put("value", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        updateDistributionMetadataField(context, distribution.getId(), field, body, new EmptyResponseListener(listener));
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/distribution#Update-Distribution-Metadata-Field>Update Distribution Metadata Field</a> endpoint
     * @param context
     * @param distributionId
     * @param field
     * @param body
     * @param listener
     */
    public static void updateDistributionMetadataField(Context context, String distributionId,
                                                       String field, JSONObject body,
                                                       ResponseListener listener) {
        Metadata.updateMetadataField(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_METADATA_FIELD, distributionId, field),
                body,
                listener,
                REQUEST_CODE_UPDATE_METADATA_FIELD);
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/distribution#List-Devices-from-an-existing-Distribution>List Distribution Devices</a> endpoint
     * @param context
     * @param distribution
     * @param listener
     */
    public static void listDistributionDevices(Context context, Distribution distribution,
                                               TypedResponseListener<List<Device>> listener) {
        listDistributionDevices(context, distribution.getId(), new DeviceListResponseListener(listener));
    }

    @Deprecated
    public static void listDistributionDevices(Context context, String distributionId,
                                               ResponseListener listener) {
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_LIST_DEVICES, distributionId),
                null,
                listener,
                REQUEST_CODE_LIST_DEVICES
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/distribution#Add-Device-to-an-existing-Distribution">Add Device to Distribution</a> endpoint.
     * @param context
     * @param distribution
     * @param device
     */
    public static void addDeviceToDistribution(Context context, Distribution distribution,
                                               Device device, TypedResponseListener<Device> listener) {
        addDeviceToDistribution(context, device.toJsonObject(), distribution.getId(), new DeviceResponseListener(listener));
    }

    @Deprecated
    public static void addDeviceToDistribution(Context context, JSONObject params,
                                               String distributionId, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_ADD_DEVICE, distributionId),
                params,
                listener,
                REQUEST_CODE_ADD_DEVICE
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/distribution#Delete-Distribution>Delete Distribution</a> endpoint.
     * @param context
     * @param distribution
     * @param listener
     */
    public static void deleteDistribution(Context context, Distribution distribution,
                                          TypedResponseListener<Void> listener) {
        deleteDistribution(context, distribution.getId(), new EmptyResponseListener(listener));
    }

    @Deprecated
    public static void deleteDistribution(Context context, String distributionId,
                                          ResponseListener listener) {
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US, Constants.DISTRIBUTION_DELETE, distributionId),
                (Map<String, String>)null,
                listener,
                REQUEST_CODE_DELETE_DISTRIBUTION
        );
    }

    /**
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/distribution#Delete-Distribution">List Data Streams</a> endpoint
     * @param context
     * @param distribution
     * @param listener
     */
    public static void listDataStreams(Context context, Distribution distribution,
                                       TypedResponseListener<List<Stream>> listener) {
        listDataStreams(context, distribution.getId(), new DataStreamListResponseListener(listener));
    }

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
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/distribution#Create-Update-Data-Stream">Create/Update Data Stream</a> endpoint
     * @param context
     * @param distribution
     * @param stream
     * @param listener
     */
    public static void createUpdateDataStream(Context context, Distribution distribution,
                                              Stream stream, TypedResponseListener<Void> listener) {
        createUpdateDataStream(context, stream.toJsonObject(), distribution.getId(), stream.getName(), new EmptyResponseListener(listener));
    }

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
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/device#View-Data-Stream">View Data Stream</a> endpoint
     * @param context
     * @param distribution
     * @param name
     * @param listener
     */
    public static void viewDataStream(Context context, Distribution distribution, String name,
                                      TypedResponseListener<Stream> listener) {
        viewDataStream(context, distribution.getId(), name, new DataStreamResponseListener(listener));
    }

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
     * Method for <a href="https://m2x.att.com/developer/documentation/v2/distribution#Delete-Data-Stream">Delete Data Stream</a> endpoint
     * @param context
     * @param distribution
     * @param stream
     * @param listener
     */
    public static void deleteDataStream(Context context, Distribution distribution,
                                        Stream stream, TypedResponseListener<Void> listener) {
        deleteDataStream(context, distribution.getId(), stream.getName(), new EmptyResponseListener(listener));
    }

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



    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    //// RESPONSE LISTENERS
    ////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static class DistributionResponseListener extends BaseResponseListener<Distribution> {

        DistributionResponseListener(TypedResponseListener<Distribution> listener) {
            super(listener);
        }

        @Override
        Distribution parseResponse(JSONObject jsonObject) throws JSONException, ParseException {
            return Distribution.fromJsonObject(jsonObject);
        }
    }

    private static class DistributionListResponseListener extends BaseResponseListener<List<Distribution>> {
        DistributionListResponseListener(TypedResponseListener<List<Distribution>> listener) {
            super(listener);
        }

        @Override
        List<Distribution> parseResponse(JSONObject jsonObject) throws JSONException, ParseException {
            JSONArray distributionArray = jsonObject.getJSONArray("distributions");
            List<Distribution> distributionList = new ArrayList<>(distributionArray.length());
            for (int i = 0; i < distributionArray.length(); ++i) {
                distributionList.add(Distribution.fromJsonObject(distributionArray.getJSONObject(i)));
            }
            return distributionList;
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

    private static class DataStreamResponseListener extends BaseResponseListener<Stream> {

        DataStreamResponseListener(TypedResponseListener<Stream> listener) {
            super(listener);
        }

        @Override
        Stream parseResponse(JSONObject jsonObject) throws JSONException {
            return Stream.fromJsonObject(jsonObject);
        }
    }

    private static class DataStreamListResponseListener extends BaseResponseListener<List<Stream>> {
        DataStreamListResponseListener(TypedResponseListener<List<Stream>> listener) {
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
}

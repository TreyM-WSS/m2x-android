package com.att.m2x.android.common;

/**
 * Created by Joaquin on 28/11/14.
 */
public class Constants {

    public static final String API_BASE_URL = "http://api-m2x.att.com/v2"; //PROD
    public static final String USER_AGENT = "M2X-Android/3.0.0 java/21 (".
                                            concat(System.getProperty("os.arch")).
                                            concat(" ").
                                            concat(android.os.Build.VERSION.RELEASE).
                                            concat(")");

    //Device
    public static final String DEVICE_PATH = "/devices";
    public static final String DEVICE_SEARCH_PUBLIC_CATALOG = DEVICE_PATH.concat("/catalog");
    public static final String DEVICE_LIST = DEVICE_PATH;
    public static final String DEVICE_SEARCH = DEVICE_PATH.concat("/search");
    public static final String DEVICE_LIST_TAGS = DEVICE_PATH.concat("/tags");
    public static final String DEVICE_CREATE = DEVICE_PATH;
    public static final String DEVICE_UPDATE_DETAILS = DEVICE_PATH.concat("/%s");
    public static final String DEVICE_VIEW_DETAILS = DEVICE_PATH.concat("/%s");
    public static final String DEVICE_READ_LOCATION = DEVICE_PATH.concat("/%s/location");
    public static final String DEVICE_UPDATE_LOCATION = DEVICE_PATH.concat("/%s/location");
    public static final String DEVICE_METADATA = DEVICE_PATH.concat("/%s/metadata");
    public static final String DEVICE_METADATA_FIELD = DEVICE_PATH.concat("/%s/metadata/%s");
    public static final String DEVICE_LIST_DATA_STREAMS = DEVICE_PATH.concat("/%s/streams");
    public static final String DEVICE_CREATE_UPDATE_DATA_STREAMS = DEVICE_PATH.concat("/%s/streams/%s");
    public static final String DEVICE_UPDATE_DATA_STREAM_VALUE = DEVICE_PATH.concat("/%s/streams/%s/value");
    public static final String DEVICE_VIEW_DATA_STREAM = DEVICE_PATH.concat("/%s/streams/%s");
    public static final String DEVICE_LIST_DATA_STREAM_VALUES = DEVICE_PATH.concat("/%s/streams/%s/values");
    public static final String DEVICE_LIST_DATA_STREAM_SAMPLING = DEVICE_PATH.concat("/%s/streams/%s/sampling");
    public static final String DEVICE_LIST_DATA_STREAM_STATS = DEVICE_PATH.concat("/%s/streams/%s/stats");
    public static final String DEVICE_POST_DATA_STREAM_VALUES = DEVICE_PATH.concat("/%s/streams/%s/values");
    public static final String DEVICE_DELETE_DATA_STREAM_VALUES = DEVICE_PATH.concat("/%s/streams/%s/values");
    public static final String DEVICE_DELETE_DATA_STREAM = DEVICE_PATH.concat("/%s/streams/%s");
    public static final String DEVICE_POST_UPDATES = DEVICE_PATH.concat("/%s/updates");
    public static final String DEVICE_REQUEST_LOG = DEVICE_PATH.concat("/%s/log");
    public static final String DEVICE_DELETE = DEVICE_PATH.concat("/%s");

    //Distribution
    public static final String DISTRIBUTION_PATH = "/distributions";
    public static final String DISTRIBUTION_LIST = DISTRIBUTION_PATH;
    public static final String DISTRIBUTION_CREATE = DISTRIBUTION_PATH;
    public static final String DISTRIBUTION_VIEW_DETAILS = DISTRIBUTION_PATH.concat("/%s");
    public static final String DISTRIBUTION_UPDATE_DETAILS = DISTRIBUTION_PATH.concat("/%s");
    public static final String DISTRIBUTION_METADATA = DISTRIBUTION_PATH.concat("/%s/metadata");
    public static final String DISTRIBUTION_METADATA_FIELD = DISTRIBUTION_PATH.concat("/%s/metadata/%s");
    public static final String DISTRIBUTION_LIST_DEVICES = DISTRIBUTION_PATH.concat("/%s/devices");
    public static final String DISTRIBUTION_ADD_DEVICE = DISTRIBUTION_PATH.concat("/%s/devices");
    public static final String DISTRIBUTION_DELETE = DISTRIBUTION_PATH.concat("/%s");
    public static final String DISTRIBUTION_LIST_DATA_STREAMS = DISTRIBUTION_PATH.concat("/%s/streams");
    public static final String DISTRIBUTION_CREATE_UPDATE_DATA_STREAMS = DISTRIBUTION_PATH.concat("/%s/streams/%s");
    public static final String DISTRIBUTION_VIEW_DATA_STREAMS = DISTRIBUTION_PATH.concat("/%s/streams/%s");
    public static final String DISTRIBUTION_DELETE_DATA_STREAMS = DISTRIBUTION_PATH.concat("/%s/streams/%s");

    //Keys
    public static final String KEYS_PATH = "/keys";
    public static final String KEYS_LIST = KEYS_PATH;
    public static final String KEYS_CREATE = KEYS_PATH;
    public static final String KEYS_DETAIL = KEYS_PATH.concat("/%s");
    public static final String KEYS_UPDATE = KEYS_PATH.concat("/%s");
    public static final String KEYS_REGENERATE = KEYS_PATH.concat("/%s/regenerate");
    public static final String KEYS_DELETE = KEYS_PATH.concat("/%s");

    //Charts
    public static final String CHARTS_PATH = "/charts";
    public static final String CHARTS_LIST = CHARTS_PATH;
    public static final String CHARTS_CREATE = CHARTS_PATH;
    public static final String CHARTS_VIEW_DETAILS = CHARTS_PATH.concat("/%s");
    public static final String CHARTS_UPDATE = CHARTS_PATH.concat("/%s");
    public static final String CHARTS_DELETE = CHARTS_PATH.concat("/%s");
    public static final String CHARTS_RENDER = CHARTS_PATH.concat("/%s.%s");

}

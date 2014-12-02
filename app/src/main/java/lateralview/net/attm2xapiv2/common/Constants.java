package lateralview.net.attm2xapiv2.common;

/**
 * Created by Joaquin on 28/11/14.
 */
public class Constants {

    public static final String API_BASE_URL = "http://api-m2x.att.com/v2";

    //Device calls
    public static final String DEVICE_SEARCH_PUBLIC_CATALOG = API_BASE_URL.concat("/devices/catalog");
    public static final String DEVICE_SEARCH_CATALOG = API_BASE_URL.concat("/devices");
    public static final String DEVICE_LIST_GROUPS = API_BASE_URL.concat("/devices/groups");
    public static final String DEVICE_CREATE = API_BASE_URL.concat("/devices");
    public static final String DEVICE_UPDATE_DETAILS = API_BASE_URL.concat("/devices/%s");
    public static final String DEVICE_VIEW_DETAILS = API_BASE_URL.concat("/devices/%s");
    public static final String DEVICE_READ_LOCATION = API_BASE_URL.concat("/devices/%s/location");
    public static final String DEVICE_UPDATE_LOCATION = API_BASE_URL.concat("/devices/%s/location");
    public static final String DEVICE_LIST_DATA_STREAMS = API_BASE_URL.concat("/devices/%s/streams");
    public static final String DEVICE_CREATE_DATA_STREAMS = API_BASE_URL.concat("/devices/%s/streams/%s");
    public static final String DEVICE_UPDATE_DATA_STREAM_VALUE = API_BASE_URL.concat("/devices/%s/streams/%s/value");
    public static final String DEVICE_VIEW_DATA_STREAM = API_BASE_URL.concat("/devices/%s/streams/%s");
    public static final String DEVICE_LIST_DATA_STREAM_VALUES = API_BASE_URL.concat("/devices/%s/streams/%s/values");
    public static final String DEVICE_LIST_DATA_STREAM_SAMPLING = API_BASE_URL.concat("/devices/%s/streams/%s/sampling");
    public static final String DEVICE_LIST_DATA_STREAM_STATS = API_BASE_URL.concat("/devices/%s/streams/%s/stats");
    public static final String DEVICE_LIST_POST_DATA_STREAM_VALUES = API_BASE_URL.concat("/devices/%s/streams/%s/values");
    public static final String DEVICE_DELETE_DATA_STREAM_VALUES = API_BASE_URL.concat("/devices/%s/streams/%s/values");
    public static final String DEVICE_DELETE_DATA_STREAM = API_BASE_URL.concat("/devices/%s/streams/%s");
    public static final String DEVICE_POST_UPDATES = API_BASE_URL.concat("/devices/%s/updates");
    public static final String DEVICE_LIST_TRIGGERS = API_BASE_URL.concat("/devices/%s/triggers");
    public static final String DEVICE_CREATE_TRIGGERS = API_BASE_URL.concat("/devices/%s/triggers");
    public static final String DEVICE_VIEW_TRIGGER = API_BASE_URL.concat("/devices/%s/triggers/%s");
    public static final String DEVICE_UPDATE_TRIGGER = API_BASE_URL.concat("/devices/%s/triggers/%s");
    public static final String DEVICE_TEST_TRIGGER = API_BASE_URL.concat("/devices/%s/triggers/%s/test");
    public static final String DEVICE_DELETE_TRIGGER = API_BASE_URL.concat("/devices/%s/triggers/%s");
    public static final String DEVICE_REQUEST_LOG = API_BASE_URL.concat("/devices/%s/log");
    public static final String DEVICE_DELETE = API_BASE_URL.concat("/devices/%s");







}

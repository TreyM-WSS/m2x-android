package com.att.m2x.android.model;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.att.m2x.android.common.Constants;
import com.att.m2x.android.listeners.ResponseListener;
import com.att.m2x.android.listeners.TypedResponseListener;
import com.att.m2x.android.network.JsonRequest;
import com.att.m2x.android.services.model.CreateUpdateKey;
import com.att.m2x.android.services.model.ListKeyOptions;
import com.att.m2x.android.utils.ArrayUtils;
import com.att.m2x.android.utils.DateUtils;
import com.att.m2x.android.utils.StringUtils;

/**
 * This API allows managing master, device, collection, and distribution keys to access all resources or just a device or stream.
 * When a new account is created it will be assigned a default Master API Key. This key cannot be deleted nor updated, it's only possible to regenerate its token.<p>
 * Authentication:
 * All methods described in this page require that a Master API Key is specified in the X-M2X-KEY header.
 * Any attempt of using a Distribution or Device level API key would result in a 403 Forbidden response. {@see <a href="https://m2x.att.com/developer/documentation/v2/overview#API-Keys">Learn more about API Keys.</a>}
 */
public class Key implements IModelObject {

    private String name;
    private String key;
    private Boolean master;
    private Date expiresAt;
    private Boolean expired;
    private Set<String> origin;
    private DeviceAccess deviceAccess;
    private Set<KeyPermission> permissions;

    public Key() {
    }

    public Key(String key) {
        this.key = key;
    }

    public Key(String name, String key, Boolean master, Date expiresAt, Boolean expired,
               Set<String> origin, DeviceAccess deviceAccess, Set<KeyPermission> permissions) {
        this.name = name;
        this.key = key;
        this.master = master;
        this.expiresAt = expiresAt;
        this.expired = expired;
        this.origin = origin;
        this.deviceAccess = deviceAccess;
        this.permissions = permissions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getMaster() {
        return master;
    }

    public void setMaster(Boolean master) {
        this.master = master;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public Set<String> getOrigin() {
        return origin;
    }

    public void setOrigin(Set<String> origin) {
        this.origin = origin;
    }

    public DeviceAccess getDeviceAccess() {
        return deviceAccess;
    }

    public void setDeviceAccess(DeviceAccess deviceAccess) {
        this.deviceAccess = deviceAccess;
    }

    public Set<KeyPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<KeyPermission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (StringUtils.hasText(name)) jsonObject.put("name", name);
            if (StringUtils.hasText(key)) jsonObject.put("key", key);
            if (master != null) jsonObject.put("master", master);
            if (expiresAt != null) jsonObject.put("expires_at", DateUtils.dateTimeToString(expiresAt));
            if (ArrayUtils.isNotEmpty(origin)) jsonObject.put("origin", StringUtils.join(origin, ","));
            if (deviceAccess != null) jsonObject.put("device_access", deviceAccess.access());
            if (permissions != null) {
                JSONArray permissionsArray = new JSONArray();
                for (KeyPermission permission: permissions) {
                    permissionsArray.put(permission.verb());
                }
                jsonObject.put("permissions", permissionsArray);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void fromJson(JSONObject jsonObject) throws JSONException {
        name = jsonObject.optString("name", null);
        key = jsonObject.optString("key", null);
        if (!jsonObject.isNull("master")) master = jsonObject.getBoolean("master");
        if (!jsonObject.isNull("expired_at")) try {
            expiresAt = DateUtils.stringToDateTime(jsonObject.getString("expires_at"));
        } catch (ParseException e) {
            throw new JSONException(e.getMessage());
        }
        if (!jsonObject.isNull("origin")) {
            String[] origins = jsonObject.getString("origin").split(",");
            origin = new HashSet<>(origins.length);
            for (String ip : origins) {
                origin.add(ip);
            }
        }
        if (!jsonObject.isNull("permissions")) {
            JSONArray permissionsArray = jsonObject.getJSONArray("permissions");
            permissions = new HashSet<>(permissionsArray.length());
            for (int i = 0; i < permissionsArray.length(); ++i) {
                permissions.add(KeyPermission.parse(permissionsArray.getString(i)));
            }
        }
    }

    public static Key fromJsonObject(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) return null;
        Key key = new Key();
        key.fromJson(jsonObject);
        return key;
    }

    public enum DeviceAccess {
        PRIVATE("private"),
        PUBLIC("public");

        private final String access;

        DeviceAccess(String access) {
            this.access = access;
        }

        public String access() {
            return access;
        }
    }

    public enum KeyPermission {
        GET("GET"),
        PUT("PUT"),
        POST("POST"),
        DELETE("DELETE");

        private final String verb;

        KeyPermission(String verb) {
            this.verb = verb;
        }

        public String verb() {
            return verb;
        }

        public static KeyPermission parse(String value) {
            if (value == null) return null;
            switch (value.toUpperCase()) {
                case "GET": return GET;
                case "PUT": return PUT;
                case "POST": return POST;
                case "DELETE": return DELETE;
            }
            throw new IllegalArgumentException("Invalid Key Permission: " + value);
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

    public static final int REQUEST_CODE_KEYS_LIST = 3001;
    public static final int REQUEST_CODE_KEYS_CREATE = 3002;
    public static final int REQUEST_CODE_KEYS_DETAIL = 3003;
    public static final int REQUEST_CODE_KEYS_UPDATE = 3004;
    public static final int REQUEST_CODE_KEYS_REGENERATE = 3005;
    public static final int REQUEST_CODE_KEYS_DELETE = 3006;

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.KeyService#listKeys(Context, ListKeyOptions, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/keys#List-Keys">List Keys</a>} endpoint
     * @param context The application Context.
     * @param params Query parameters as HashMap<String,String>. View M2X API Docs for listing of available parameters.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void list(Context context,HashMap<String,String> params, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.KEYS_LIST),
                params,
                listener,
                REQUEST_CODE_KEYS_LIST
        );
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.KeyService#createKey(Context, CreateUpdateKey, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/keys#Create-Key">Create Key</a>} endpoint
     * @param context The application Context.
     * @param params as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void create(Context context,JSONObject params, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                Constants.KEYS_CREATE,
                params,
                listener,
                REQUEST_CODE_KEYS_CREATE
        );
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.KeyService#viewDetails(Context, String, ResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/keys#View-Key-Details">View Key Details</a>} endpoint
     * @param context The application Context.
     * @param keyId as String, ID of the key.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void viewDetails(Context context, String keyId, ResponseListener listener){
        JsonRequest.makeGetRequest(
                context,
                String.format(Locale.US, Constants.KEYS_DETAIL,keyId),
                null,
                listener,
                REQUEST_CODE_KEYS_DETAIL
        );
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.KeyService#updateKey(Context, Key, CreateUpdateKey, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/keys#Update-Key">Update Key</a>} endpoint
     * @param context The application Context.
     * @param params as JSONObject, View M2X API Docs for listing of available body parameters.
     * @param keyId as String, ID of the key.
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void update(Context context,JSONObject params,String keyId, ResponseListener listener){
        JsonRequest.makePutRequest(
                context,
                String.format(Locale.US,Constants.KEYS_UPDATE,keyId),
                params,
                listener,
                REQUEST_CODE_KEYS_UPDATE
        );
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.KeyService#regenerateKey(Context, Key, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/keys#Regenerate-Key">Regenerate Key</a>} endpoint
     * @param context The application Context.
     * @param keyId as String, ID of the key
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void regenerate(Context context, String keyId, ResponseListener listener){
        JsonRequest.makePostRequest(
                context,
                String.format(Locale.US,Constants.KEYS_REGENERATE,keyId),
                null,
                listener,
                REQUEST_CODE_KEYS_REGENERATE
        );
    }

    /**
     * @deprecated Replaced by {@link com.att.m2x.android.services.KeyService#deleteKey(Context, Key, TypedResponseListener)}
     *
     * Method for {@see <a href="https://m2x.att.com/developer/documentation/v2/keys#Delete-Key">Delete Key</a>} endpoint
     * @param context The application Context.
     * @param keyId as String, ID of the key
     * @param listener {@link ResponseListener}
     */
    @Deprecated
    public static void delete(Context context,String keyId, ResponseListener listener){
        JsonRequest.makeDeleteRequest(
                context,
                String.format(Locale.US,Constants.KEYS_DELETE,keyId),
                (Map<String, String>) null,
                listener,
                REQUEST_CODE_KEYS_DELETE
        );
    }


}

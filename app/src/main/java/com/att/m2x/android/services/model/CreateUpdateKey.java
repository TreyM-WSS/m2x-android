package com.att.m2x.android.services.model;

import com.att.m2x.android.model.Key;
import com.att.m2x.android.utils.ArrayUtils;
import com.att.m2x.android.utils.DateUtils;
import com.att.m2x.android.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Set;

/**
 * Created by collinbrown on 10/15/17.
 */

public class CreateUpdateKey {
    private String name;
    private Set<Key.KeyPermission> permissions;
    private String deviceId;
    private String distributionId;
    private String collectionId;
    private String stream;
    private Date expiresAt;
    private Set<String> origin;
    private Key.DeviceAccess deviceAccess;

    public CreateUpdateKey() {
    }

    public CreateUpdateKey(String name, Set<Key.KeyPermission> permissions, String deviceId,
                           String distributionId, String collectionId, String stream, Date expiresAt,
                           Set<String> origin, Key.DeviceAccess deviceAccess) {
        this.name = name;
        this.permissions = permissions;
        this.deviceId = deviceId;
        this.distributionId = distributionId;
        this.collectionId = collectionId;
        this.stream = stream;
        this.expiresAt = expiresAt;
        this.origin = origin;
        this.deviceAccess = deviceAccess;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Key.KeyPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Key.KeyPermission> permissions) {
        this.permissions = permissions;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDistributionId() {
        return distributionId;
    }

    public void setDistributionId(String distributionId) {
        this.distributionId = distributionId;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Set<String> getOrigin() {
        return origin;
    }

    public void setOrigin(Set<String> origin) {
        this.origin = origin;
    }

    public Key.DeviceAccess getDeviceAccess() {
        return deviceAccess;
    }

    public void setDeviceAccess(Key.DeviceAccess deviceAccess) {
        this.deviceAccess = deviceAccess;
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (StringUtils.hasText(name)) jsonObject.put("name", name);
            if (StringUtils.hasText(deviceId)) jsonObject.put("device", deviceId);
            if (StringUtils.hasText(distributionId)) jsonObject.put("distribution", distributionId);
            if (StringUtils.hasText(collectionId)) jsonObject.put("collection", collectionId);
            if (StringUtils.hasText(stream)) jsonObject.put("device", stream);
            if (expiresAt != null) jsonObject.put("expires_at", DateUtils.dateTimeToString(expiresAt));
            if (ArrayUtils.isNotEmpty(origin)) jsonObject.put("origin", StringUtils.join(origin, ","));
            if (deviceAccess != null) jsonObject.put("device_access", deviceAccess.access());
            if (permissions != null) {
                JSONArray permissionsArray = new JSONArray();
                for (Key.KeyPermission permission: permissions) {
                    permissionsArray.put(permission.verb());
                }
                jsonObject.put("permissions", permissionsArray);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}

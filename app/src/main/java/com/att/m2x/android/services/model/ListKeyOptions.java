package com.att.m2x.android.services.model;

import com.att.m2x.android.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by collinbrown on 10/15/17.
 */

public class ListKeyOptions {
    private String deviceId;
    private String collectionId;
    private String distributionId;

    public ListKeyOptions() {
    }

    public ListKeyOptions(String deviceId, String collectionId, String distributionId) {
        this.deviceId = deviceId;
        this.collectionId = collectionId;
        this.distributionId = distributionId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getDistributionId() {
        return distributionId;
    }

    public void setDistributionId(String distributionId) {
        this.distributionId = distributionId;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.hasText(deviceId)) map.put("device", deviceId);
        if (StringUtils.hasText(collectionId)) map.put("collection", collectionId);
        if (StringUtils.hasText(distributionId)) map.put("distribution", distributionId);
        return map;
    }
}

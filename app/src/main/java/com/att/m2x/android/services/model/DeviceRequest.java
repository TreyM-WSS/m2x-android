package com.att.m2x.android.services.model;

import com.att.m2x.android.utils.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by collinbrown on 10/15/17.
 */
public class DeviceRequest {
    private Date timestamp;
    private Integer status;
    private String method;
    private String path;

    public DeviceRequest() {
    }

    public DeviceRequest(Date timestamp, Integer status, String method, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.method = method;
        this.path = path;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void fromJson(JSONObject jsonObject) throws JSONException {
        try {
            if (!jsonObject.isNull("timestamp"))
                timestamp = DateUtils.stringToDateTime(jsonObject.getString("timestamp"));
        } catch (ParseException e) {
            throw new JSONException(e.getMessage());
        }
        if (!jsonObject.isNull("status")) status = jsonObject.getInt("status");
        if (jsonObject.has("method")) method = jsonObject.getString("method");
        if (jsonObject.has("path")) path = jsonObject.getString("path");
    }

    public static DeviceRequest fromJsonObject(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) return null;
        DeviceRequest request = new DeviceRequest();
        request.fromJson(jsonObject);
        return request;
    }
}

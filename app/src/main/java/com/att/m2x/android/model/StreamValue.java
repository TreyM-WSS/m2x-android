package com.att.m2x.android.model;

import com.att.m2x.android.utils.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by collinbrown on 10/15/17.
 */

public class StreamValue implements IModelObject {
    private Date timestamp;
    private Double value;
    private String stringValue;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setValue(String value) {
        this.value = null;
        this.stringValue = value;
    }

    public void setValue(Number value) {
        this.value = value == null ? null : value.doubleValue();
        this.stringValue = null;
    }

    public Double getValue() {
        return this.value;
    }

    public String getStringValue() {
        return stringValue;
    }

    public StreamValue() {
    }

    public StreamValue(Date timestamp, Number value) {
        this.timestamp = timestamp;
        if (value != null) this.value = value.doubleValue();
    }

    public StreamValue(Date timestamp, String value) {
        this.timestamp = timestamp;
        this.stringValue = value;
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (timestamp != null) jsonObject.put("timestamp", DateUtils.dateTimeToString(timestamp));
            if (value != null) jsonObject.put("value", value);
            else if (stringValue != null) jsonObject.put("value", stringValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (jsonObject.has("timestamp") && !jsonObject.isNull("timestamp")) {
            try {
                timestamp = DateUtils.stringToDateTime(jsonObject.getString("timestamp"));
            } catch (ParseException e) {
                throw new JSONException(e.getMessage());
            }
        }
        if (jsonObject.has("value") && !jsonObject.isNull("value")) {
            Object val = jsonObject.get("value");
            if (val instanceof Number) value = ((Number) val).doubleValue();
            else if (val instanceof String) stringValue = (String) val;
            else throw new JSONException("Expected 'value' to be string or number");
        }
    }


    public static StreamValue fromJsonObject(JSONObject jsonObject) throws JSONException {
        StreamValue value = new StreamValue();
        value.fromJson(jsonObject);
        return value;
    }
}

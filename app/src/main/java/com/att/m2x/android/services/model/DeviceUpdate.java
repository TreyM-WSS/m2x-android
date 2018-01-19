package com.att.m2x.android.services.model;

import com.att.m2x.android.model.Location;
import com.att.m2x.android.utils.ArrayUtils;
import com.att.m2x.android.utils.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by collinbrown on 10/15/17.
 */

public class DeviceUpdate {
    private Date timestamp;
    private Map<String, Value> values;
    private Location location;

    public DeviceUpdate() {
    }

    public DeviceUpdate(Date timestamp, Map<String, Value> values) {
        this.timestamp = timestamp;
        this.values = values;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Value> getValues() {
        return values;
    }

    public void setValues(Map<String, Value> values) {
        this.values = values;
    }

    public void setValue(String stream, Number value) {
        if (this.values == null) this.values = new HashMap<>();
        this.values.put(stream, new Value(value));
    }

    public void setValue(String stream, String value) {
        if (this.values == null) this.values = new HashMap<>();
        this.values.put(stream, new Value(value));
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (timestamp != null) jsonObject.put("timestamp", DateUtils.dateTimeToString(timestamp));
            if (ArrayUtils.isNotEmpty(values)) {
                JSONObject valuesObject = new JSONObject();
                for (Map.Entry<String, Value> e : values.entrySet()) {
                    Value value = e.getValue();
                    if (value.getValue() != null) {
                        valuesObject.put(e.getKey(), value.getValue());
                    } else if (value.getStringValue() != null) {
                        valuesObject.put(e.getKey(), value.getStringValue());
                    } else {
                        valuesObject.put(e.getKey(), null);
                    }
                }
                jsonObject.put("values", valuesObject);
            }
            if (location != null) {
                jsonObject.put("location", location.toJsonObject());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static class Value {
        private Double value;
        private String stringValue;

        public Value(Number value) {
            this.value = value == null ? null : value.doubleValue();
        }

        public Value(String stringValue) {
            this.stringValue = stringValue;
        }

        public Double getValue() {
            return value;
        }

        public String getStringValue() {
            return stringValue;
        }

        public void setValue(Double value) {
            this.value = value == null ? null : value.doubleValue();
        }

        public void setValue(String stringValue) {
            this.stringValue = stringValue;
        }
    }
}

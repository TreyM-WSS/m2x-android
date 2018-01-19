package com.att.m2x.android.services.model;

import com.att.m2x.android.model.Location;
import com.att.m2x.android.model.StreamValue;
import com.att.m2x.android.utils.ArrayUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by collinbrown on 10/15/17.
 */

public class DeviceUpdates {
    private Map<String, List<LocatedStreamValue>> values;

    public DeviceUpdates() {
    }

    public DeviceUpdates(Map<String, List<LocatedStreamValue>> values) {
        this.values = values;
    }

    public Map<String, List<LocatedStreamValue>> getValues() {
        return values;
    }

    public void setValues(Map<String, List<LocatedStreamValue>> values) {
        this.values = values;
    }

    public void setValues(String stream, List<LocatedStreamValue> values) {
        if (this.values == null) {
            this.values = new HashMap<>();
        }
        this.values.put(stream, values);
    }

    public void addValue(String stream, LocatedStreamValue value) {
        if (values == null) {
            values = new HashMap<>();
        }
        List<LocatedStreamValue> streamValues = values.get(stream);
        if (streamValues == null) {
            streamValues = new ArrayList<>();
            values.put(stream, streamValues);
        }
        streamValues.add(value);
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (ArrayUtils.isNotEmpty(values)) {
                JSONObject valuesObject = new JSONObject();
                for (Map.Entry<String, List<LocatedStreamValue>> e : values.entrySet()) {
                    JSONArray valuesArray = ArrayUtils.listToJsonArray(e.getValue());
                    valuesObject.put(e.getKey(), valuesArray);
                }
                jsonObject.put("values", valuesObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static class LocatedStreamValue extends StreamValue {
        private Location location;

        public LocatedStreamValue() {
        }

        public LocatedStreamValue(Date timestamp, Number value, Location location) {
            super(timestamp, value);
            this.location = location;
        }

        public LocatedStreamValue(Date timestamp, String value, Location location) {
            super(timestamp, value);
            this.location = location;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        @Override
        public JSONObject toJsonObject() {
            JSONObject jsonObject = super.toJsonObject();
            try {
                if (location != null) {
                    jsonObject.put("location", location.toJsonObject());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        public void fromJson(JSONObject jsonObject) throws JSONException {
            super.fromJson(jsonObject);
            if (!jsonObject.isNull("location")) {
                location = Location.fromJsonObject(jsonObject.getJSONObject("location"));
            }
        }

        public static LocatedStreamValue fromJsonObject(JSONObject jsonObject) throws JSONException {
            if (jsonObject == null) return null;
            LocatedStreamValue value = new LocatedStreamValue();
            value.fromJson(jsonObject);
            return value;
        }
    }
}


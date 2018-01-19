package com.att.m2x.android.services.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by collinbrown on 8/30/17.
 */

public class StreamFilter {

    String key;
    SortedMap<String, Object> values;

    public StreamFilter() {}

    public StreamFilter(String key, SortedMap<String, Object> values) {
        this.key = key;
        this.values = values;
    }

    public void addValue(String name, Object value) {
        if (values == null) {
            values = new TreeMap<String, Object>();
        }
        values.put(name, value);
    }

    public JSONObject toJsonObject() {
        JSONObject result = new JSONObject();

        if (values != null && !values.isEmpty()) {
            try {
                JSONObject jsonValues = new JSONObject();
                Iterator<String> itrKeys = values.keySet().iterator();
                while (itrKeys.hasNext()) {
                    String key = itrKeys.next();
                    Object value = values.get(key);
                    if (value != null) {
                        jsonValues.put(key, value);
                    }
                }

                result.put(key, jsonValues);
            } catch(JSONException e) {
                Log.e("StreamFilter", e.getMessage());
            }
        }

        return result;
    }
}

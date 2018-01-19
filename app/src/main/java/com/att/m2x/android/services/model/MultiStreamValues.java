package com.att.m2x.android.services.model;

import com.att.m2x.android.model.StreamValue;
import com.att.m2x.android.utils.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by collinbrown on 10/16/17.
 */

public class MultiStreamValues {
    private Date start;
    private Date end;
    private Integer limit;
    private Collection<MultiValue> values;

    public MultiStreamValues() {
    }

    public MultiStreamValues(Date start, Date end, Integer limit, Collection<MultiValue> values) {
        this.start = start;
        this.end = end;
        this.limit = limit;
        this.values = values;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Collection<MultiValue> getValues() {
        return values;
    }

    public void setValues(Collection<MultiValue> values) {
        this.values = values;
    }

    public void fromJson(JSONObject jsonObject) throws JSONException {
        try {
            if (!jsonObject.isNull("start"))
                start = DateUtils.stringToDateTime(jsonObject.getString("start"));
            if (!jsonObject.isNull("end"))
                end = DateUtils.stringToDateTime(jsonObject.getString("end"));
        } catch (ParseException e) {
            throw new JSONException(e.getMessage());
        }
        if (!jsonObject.isNull("limit")) limit = jsonObject.getInt("limit");
        if (!jsonObject.isNull("values")) {
            JSONArray valuesArray = jsonObject.getJSONArray("values");
            values = new ArrayList<>(valuesArray.length());
            for (int i = 0; i < valuesArray.length(); ++i) {
                values.add(MultiValue.fromJsonObject(valuesArray.getJSONObject(i)));
            }
        }
    }

    public static MultiStreamValues fromJsonObject(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) return null;
        MultiStreamValues values = new MultiStreamValues();
        values.fromJson(jsonObject);
        return values;
    }

    public static class MultiValue {
        private Date timestamp;
        private Map<String, StreamValue> values;

        public MultiValue() {
        }

        public MultiValue(Date timestamp, Map<String, StreamValue> values) {
            this.timestamp = timestamp;
            this.values = values;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }

        public Map<String, StreamValue> getValues() {
            return values;
        }

        public void setValues(Map<String, StreamValue> values) {
            this.values = values;
        }

        public void fromJson(JSONObject jsonObject) throws JSONException {
            if (!jsonObject.isNull("timestamp")) try {
                timestamp = DateUtils.stringToDateTime(jsonObject.getString("timestamp"));
            } catch (ParseException e) {
                throw new JSONException(e.getMessage());
            }
            if (!jsonObject.isNull("values")) {
                JSONObject valuesObj = jsonObject.getJSONObject("values");
                values = new HashMap<>();
                for (Iterator<String> streams = valuesObj.keys(); streams.hasNext();) {
                    String stream = streams.next();
                    if (!valuesObj.isNull(stream)) {
                        Object value = valuesObj.get(stream);
                        if (value instanceof String) values.put(stream, new StreamValue(timestamp, (String) value));
                        else if (value instanceof Number) values.put(stream, new StreamValue(timestamp, (Number) value));
                        else throw new JSONException("Invalid stream value: " + value);
                    }
                }
            }
        }

        public static MultiValue fromJsonObject(JSONObject jsonObject) throws JSONException {
            if (jsonObject == null) return null;
            MultiValue value = new MultiValue();
            value.fromJson(jsonObject);
            return value;
        }
    }
}

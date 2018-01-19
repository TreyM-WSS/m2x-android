package com.att.m2x.android.services.model;

import com.att.m2x.android.model.StreamValue;
import com.att.m2x.android.model.IModelObject;
import com.att.m2x.android.utils.ArrayUtils;
import com.att.m2x.android.utils.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.*;

/**
 * Created by collinbrown on 10/15/17.
 */

public class StreamValues implements IModelObject {
    private Date start;
    private Date end;
    private Integer limit;
    private Collection<StreamValue> values;

    public StreamValues() {
    }

    public StreamValues(Date start, Date end, Integer limit, java.util.Collection<StreamValue> values) {
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

    public Collection<StreamValue> getValues() {
        return values;
    }

    public void setValues(Collection<StreamValue> values) {
        this.values = values;
    }

    public void fromJson(JSONObject jsonObject) throws JSONException {
        try {
            if (jsonObject.has("start") && !jsonObject.isNull("start"))
                start = DateUtils.stringToDateTime(jsonObject.getString("start"));
            if (jsonObject.has("end") && !jsonObject.isNull("end"))
                end = DateUtils.stringToDateTime(jsonObject.getString("end"));
            if (jsonObject.has("limit"))
                limit = jsonObject.getInt("limit");
            if (jsonObject.has("values") && !jsonObject.isNull("values")) {
                JSONArray valueArray = jsonObject.getJSONArray("values");
                values = new ArrayList<>(valueArray.length());
                for (int i = 0; i < valueArray.length(); ++i) {
                    values.add(StreamValue.fromJsonObject(valueArray.getJSONObject(i)));
                }
            }
        } catch (ParseException e) {
            throw new JSONException(e.getMessage());
        }
    }

    public static StreamValues fromJsonObject(JSONObject jsonObject) throws JSONException, ParseException {
        StreamValues streamValues = new StreamValues();
        streamValues.fromJson(jsonObject);
        return streamValues;
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (start != null) jsonObject.put("start", DateUtils.dateTimeToString(start));
            if (end != null) jsonObject.put("end", DateUtils.dateTimeToString(end));
            if (limit != null) jsonObject.put("limit", limit.toString());
            if (values != null) {
                jsonObject.put("values", ArrayUtils.listToJsonArray(values));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}

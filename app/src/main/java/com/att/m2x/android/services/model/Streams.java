package com.att.m2x.android.services.model;

import com.att.m2x.android.model.Stream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;

/**
 * Created by collinbrown on 10/14/17.
 */

public class Streams {

    private java.util.Collection<Stream> streams;

    public java.util.Collection<Stream> getStreams() {
        return streams;
    }

    public void setStreams(Collection<Stream> streams) {
        this.streams = streams;
    }

    public Streams() {
    }

    public Streams(Collection<Stream> streams) {
        this.streams = streams;
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        JSONArray streamsArray = new JSONArray();
        for (Stream stream : streams) {
            streamsArray.put(stream);
        }
        try {
            jsonObject.put("streams", streamsArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}

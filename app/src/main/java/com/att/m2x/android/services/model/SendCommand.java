package com.att.m2x.android.services.model;

import com.att.m2x.android.utils.ArrayUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by collinbrown on 10/15/17.
 */

public class SendCommand {
    private String name;
    private Map<String, String> data;
    private Targets targets;

    public SendCommand() {
    }

    public SendCommand(String name, Map<String, String> data, Targets targets) {
        this.name = name;
        this.data = data;
        this.targets = targets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public Targets getTargets() {
        return targets;
    }

    public void setTargets(Targets targets) {
        this.targets = targets;
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (name != null) jsonObject.put("name", name);
            if (data != null) jsonObject.put("data", ArrayUtils.mapToJsonObject(data));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static class Targets {
        private List<String> devices;
        private List<String> collections;

        public Targets() {
        }

        public Targets(List<String> devices, List<String> collections) {
            this.devices = devices;
            this.collections = collections;
        }

        public List<String> getDevices() {
            return devices;
        }

        public void setDevices(List<String> devices) {
            this.devices = devices;
        }

        public List<String> getCollections() {
            return collections;
        }

        public void setCollections(List<String> collections) {
            this.collections = collections;
        }

        public JSONObject toJsonObject() {
            JSONObject jsonObject = new JSONObject();
            try {
                if (ArrayUtils.isNotEmpty(devices)) {
                    jsonObject.put("devices", ArrayUtils.listToJsonStringArray(devices));
                }
                if (ArrayUtils.isNotEmpty(collections)) {
                    jsonObject.put("collections", ArrayUtils.listToJsonStringArray(collections));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject;
        }
    }
}

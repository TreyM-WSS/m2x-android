package com.att.m2x.android.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by collinbrown on 10/14/17.
 */
public class Stream implements IModelObject {

    private String name;
    private String displayName;
    private Type type;
    private Double value;
    private String stringValue;
    private String latestValueAt;
    private Unit unit;
    private String url;
    private String created;
    private String updated;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getStringValue() {
        return this.stringValue;
    }

    public void setValue(String value) {
        this.stringValue = value;
    }

    public String getLatestValueAt() {
        return latestValueAt;
    }

    public void setLatestValueAt(String latestValueAt) {
        this.latestValueAt = latestValueAt;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public Stream() {
    }

    public Stream(String name) {
        this.name = name;
    }

    public Stream(String name, String displayName, Type type, Double value,
                  String latestValueAt, Unit unit, String url, String created, String updated) {
        this.name = name;
        this.displayName = displayName;
        this.type = type;
        this.value = value;
        this.latestValueAt = latestValueAt;
        this.unit = unit;
        this.url = url;
        this.created = created;
        this.updated = updated;
    }

    public Stream(String name, String displayName, Type type, String value,
                  String latestValueAt, Unit unit, String url, String created, String updated) {
        this.name = name;
        this.displayName = displayName;
        this.type = type;
        this.stringValue = value;
        this.latestValueAt = latestValueAt;
        this.unit = unit;
        this.url = url;
        this.created = created;
        this.updated = updated;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject body = new JSONObject();
        try {
            if (name != null) body.put("name", name);
            if (displayName != null) body.put("display_name", displayName);
            if (type != null) body.put("type", type.type());
            if (value != null) body.put("value", value);
            else if (stringValue != null) body.put("value", stringValue);
            if (latestValueAt != null) body.put("latest_value_at", latestValueAt);
            if (unit != null) body.put("unit", unit.toJsonObject());
            if (url != null) body.put("location", url);
            if (created != null) body.put("created", created);
            if (updated != null) body.put("updated", updated);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return body;
    }

    @Override
    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (jsonObject.has("name") && !jsonObject.isNull("name")) name = jsonObject.getString("name");
        if (jsonObject.has("display_name") && !jsonObject.isNull("display_name"))
            displayName = jsonObject.getString("display_name");
        if (jsonObject.has("type") && !jsonObject.isNull("type")) type = Type.parse(jsonObject.getString("type"));

        if (jsonObject.has("value") && !jsonObject.isNull("value")) {
            Object value = jsonObject.get("value");
            if (value instanceof Number) value = ((Number) value).doubleValue();
            else if (value instanceof String) stringValue = (String) value;
            else throw new JSONException("Expected 'value' to be string or number");
        }
        if (jsonObject.has("latest_value_at") && !jsonObject.isNull("latest_value_at"))
            latestValueAt = jsonObject.getString("latest_value_at");
        if (jsonObject.has("unit") && !jsonObject.isNull("unit")) unit = Unit.fromJsonObject(jsonObject.getJSONObject("unit"));
        if (jsonObject.has("location") && !jsonObject.isNull("location")) url = jsonObject.getString("location");
        if (jsonObject.has("created") && !jsonObject.isNull("created")) created = jsonObject.getString("created");
        if (jsonObject.has("updated") && !jsonObject.isNull("updated")) updated = jsonObject.getString("updated");

    }

    public static Stream fromJsonObject(JSONObject jsonObject) throws JSONException {
        Stream stream = new Stream();
        stream.fromJson(jsonObject);
        return stream;
    }

    public enum Type {
        NUMERIC("numeric"),
        ALPHANUMERIC("alphanumeric");

        private final String type;

        Type(String type) {
            this.type = type;
        }

        public String type() {
            return type;
        }

        public static Type parse(String value) {
            if (value == null) return null;
            switch (value.toLowerCase()) {
                case "numeric": return NUMERIC;
                case "alphanumeric": return ALPHANUMERIC;
            }
            throw new IllegalArgumentException("Invalid Stream.Type: " + value);
        }
    }

    public static class Unit {
        private String label;
        private String symbol;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public Unit() {
        }

        public Unit(String label, String symbol) {
            this.label = label;
            this.symbol = symbol;
        }

        public JSONObject toJsonObject() {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("label", label);
                jsonObject.put("symbol", symbol);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject;
        }

        public static Unit fromJsonObject(JSONObject jsonObject) {
            Unit unit = new Unit();
            try {
                if (jsonObject.has("label") && !jsonObject.isNull("label")) unit.label = jsonObject.getString("label");
                if (jsonObject.has("symbol") && !jsonObject.isNull("symbol")) unit.symbol = jsonObject.getString("symbol");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return unit;
        }
    }
}

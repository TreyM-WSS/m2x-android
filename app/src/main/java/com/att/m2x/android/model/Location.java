package com.att.m2x.android.model;

import com.att.m2x.android.utils.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;

/**
 * Model Object represents a {@see <a href="https://m2x.att.com/developer/documentation/v2/device#Read-Device-Location">Location</a>}
 */
public class Location implements IModelObject {

    public Location() {

    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Double elevation;

    public Double getElevation() {
        return elevation;
    }

    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }

    private Double latitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    private Double longitude;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    private Date timestamp;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }


    ////////////////////////////////////
    // JSON
    ////////////////////////////////////

    /**
     * Converts this Location into a JSONObject
     *
     * @return
     */
    public JSONObject toJsonObject() {
        return Location.toJsonObject(this);
    }

    /**
     * Converts a Location into a JSONObject
     *
     * @param location
     * @return
     */
    public static JSONObject toJsonObject(Location location) {
        JSONObject result = null;
        try {
            if (location != null) {
                result = new JSONObject();

                if (location.getName() != null) result.put("name", location.getName());
                if (location.getElevation() != null)
                    result.put("elevation", location.getElevation());
                if (location.getLatitude() != null)
                    result.put("latitude", location.getLatitude());
                if (location.getLongitude() != null)
                    result.put("longitude", location.getLongitude());
                if (location.getTimestamp() != null)
                    result.put("timestamp", DateUtils.dateTimeToString(location.getTimestamp()));
            }
        } catch (JSONException je) {
            je.printStackTrace();
            result = null;
        }

        return result;
    }

    @Override
    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (jsonObject != null) {

            if (jsonObject.has("name")) setName(jsonObject.getString("name"));
            if (!jsonObject.isNull("timestamp"))
                try {
                    setTimestamp(DateUtils.stringToDateTime(jsonObject.getString("timestamp")));
                } catch (ParseException e) {
                    throw new JSONException(e.getMessage());
                }
            if (!jsonObject.isNull("elevation"))
                setElevation(jsonObject.getDouble("elevation"));
            if (!jsonObject.isNull("latitude"))
                setLatitude(jsonObject.getDouble("latitude"));
            if (!jsonObject.isNull("longitude"))
                setLongitude(jsonObject.getDouble("longitude"));
        }
    }
    /**
     * Converts a JSONObject into a Location
     *
     * @param jsonObject
     * @return
     */
    public static Location fromJsonObject(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) return null;
        Location location = new Location();
        location.fromJson(jsonObject);
        return location;
    }
}

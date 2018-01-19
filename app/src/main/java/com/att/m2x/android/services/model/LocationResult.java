package com.att.m2x.android.services.model;

import com.att.m2x.android.network.ApiV2Response;

import java.util.Map;

/**
 * Created by collinbrown on 10/15/17.
 */

public class LocationResult {

    String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocationResult() {}

    public LocationResult(String url) {
        this.location = url;
    }

    public static LocationResult fromApiV2Response(ApiV2Response response) {
        LocationResult result = new LocationResult();
        if (response == null) return result;
        if (response.getHeaders() == null) return result;
        Map<String, String> headers = response.getHeaders();
        result.location = headers.get("Location");
        return result;
    }
}

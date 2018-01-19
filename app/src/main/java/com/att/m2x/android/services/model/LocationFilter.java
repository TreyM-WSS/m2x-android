package com.att.m2x.android.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by collinbrown on 8/25/17.
 */

public interface LocationFilter {

    JSONObject toJsonObject();

    class WithinCircleFilter implements LocationFilter {

        Double latitude;
        Double longitude;
        Double distance;
        String units;

        public WithinCircleFilter() {}

        public WithinCircleFilter(Double latitude, Double longitude, Double distance, String units) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.distance = distance;
            this.units = units;
        }

        public JSONObject toJsonObject() {
            JSONObject result = new JSONObject();
            try {
                JSONObject withinCircle = new JSONObject();
                JSONObject center = new JSONObject();
                if (latitude != null) {
                    center.put("latitude", latitude);
                }
                if (longitude != null) {
                    center.put("longitude", longitude);
                }
                withinCircle.put("center", center);

                JSONObject radius = new JSONObject();
                if (units != null && units.trim().length() > 0 && distance != null) {
                    radius.put(units, distance);
                }

                result.put("within_circle", withinCircle);
            } catch (JSONException e) {
                Log.e("LocationFilter", e.getMessage());
            }

            return result;
        }
    }

    class WithinPolygonFilter implements LocationFilter {

        Collection<Coordinates> coordinates;

        public WithinPolygonFilter() {}

        public WithinPolygonFilter(Collection<Coordinates> coordinates) {
            this.coordinates = coordinates;
        }

        public JSONObject toJsonObject() {
            JSONObject result = new JSONObject();
            try {
                JSONArray withinPolygon = new JSONArray();

                if (coordinates != null) {
                    Iterator<Coordinates> itrCoordinates = coordinates.iterator();
                    while (itrCoordinates.hasNext()) {
                        Coordinates nextCoordinates = itrCoordinates.next();
                        JSONObject jsonCoords = new JSONObject();
                        if (nextCoordinates.latitude != null) {
                            jsonCoords.put("latitude", nextCoordinates.latitude);
                        }
                        if (nextCoordinates.longitude != null) {
                            jsonCoords.put("longitude", nextCoordinates.longitude);
                        }

                        withinPolygon.put(jsonCoords);
                    }
                }

                result.put("within_polygon", withinPolygon);
            } catch (JSONException e) {
                Log.e("LocationFilter", e.getMessage());
            }

            return result;
        }
    }

    class Coordinates {

        Double latitude;
        Double longitude;

        public Coordinates() {}

        public Coordinates(Double latitude, Double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
}


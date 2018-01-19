package com.att.m2x.android.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Model Object represents a Response
 */
public class Response implements IModelObject {

    public Response() {
    }

    private String id;
    private String status;
    private String message;
    private String description;
    private Map<String, List<String>> errors;

    public String getId() {
        return id;
    }
    public String getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
    public String getDescription() {
        return description;
    }
    public Map<String, List<String>> getErrors() {
        return errors;
    }


    ////////////////////////////////////
    // JSON
    ////////////////////////////////////

    /**
     * Converts this Unit into a JSONObject
     *
     * @return
     */
    public JSONObject toJsonObject() {
        return Response.toJsonObject(this);
    }

    /**
     * Converts a Unit into a JSONObject
     *
     * @param response
     * @return
     */
    public static JSONObject toJsonObject(Response response) {
        JSONObject result = null;
        try {
            if (response != null) {
                result = new JSONObject();

                if (response.getId() != null) {
                    result.put("id", response.getId());
                }
                if (response.getStatus() != null) {
                    result.put("status", response.getStatus());
                }
                if (response.getMessage() != null) {
                    result.put("message", response.getMessage());
                }
                if (response.getDescription() != null) {
                    result.put("description", response.getDescription());
                }
                if (response.getErrors() != null) {
                    result.put("errors", response.getErrors());
                }
            }
        } catch (JSONException je) {
            je.printStackTrace();
            result = null;
        }

        return result;
    }


    /**
     * Converts a JSONObject into a Unit
     *
     * @param jsonObject
     * @return
     */
    public void fromJson(final JSONObject jsonObject) {
        try {
            if (jsonObject != null) {
                if (jsonObject.has("id")) this.id = jsonObject.getString("id");
                if (jsonObject.has("status")) this.status = jsonObject.getString("status");
                if (jsonObject.has("message")) this.message = jsonObject.getString("message");
                if (jsonObject.has("description")) this.description = jsonObject.getString("description");
                if (jsonObject.has("errors")) {
                    this.errors = new HashMap<>();
                    JSONObject jsonErrors = jsonObject.getJSONObject("errors");
                    if (jsonErrors != null) {
                        Iterator<String> itrErrorsKeys = jsonErrors.keys();
                        while (itrErrorsKeys.hasNext()) {
                            String nextKey = itrErrorsKeys.next();
                            List<String> errorValues = new ArrayList<>();
                            this.errors.put(nextKey, errorValues);

                            JSONArray jsonErrorArray = jsonErrors.optJSONArray(nextKey);
                            if (jsonErrorArray != null) {
                                for(int i=0; i<jsonErrorArray.length(); i++) {
                                    errorValues.add(jsonErrorArray.getString(i));
                                }
                            }
                            else {
                                errorValues.add(jsonErrors.getString(nextKey));
                            }
                        }
                    }
                }

                if (jsonObject.optJSONObject("body") != null) {
                    this.fromJson(jsonObject.getJSONObject("body"));
                }
            }
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }
    public static Response fromJsonObject(final JSONObject jsonObject) {
        Response result = null;
        result = new Response();
        result.fromJson(jsonObject);

        return result;
    }
}

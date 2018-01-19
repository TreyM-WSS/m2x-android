package com.att.m2x.android.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Base interface for (@ see <a href="https://m2x.att.com/developer/documentation/v2/overview">M2X Model Objects</a>}
 */
public interface IModelObject {
    /**
     * Converts this ModelObject into a JSONObject
     *
     * @return
     */
    JSONObject toJsonObject();
    void fromJson(JSONObject jsonObject) throws JSONException;
}

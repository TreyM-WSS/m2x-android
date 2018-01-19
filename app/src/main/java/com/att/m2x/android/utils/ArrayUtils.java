package com.att.m2x.android.utils;

import android.util.Log;

import com.att.m2x.android.model.IModelObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * ATT M2X Array Utils
 */
public class ArrayUtils {

    /**
     * @param queryString as HashMap<String, String>
     * @return as String
     */
    public static String mapToQueryString(Map<String, String> queryString){
        StringBuilder sb = new StringBuilder();
        String separator = "";
        try {
            for (String k: queryString.keySet()) {
                String v = queryString.get(k);
                sb.append(separator).append(URLEncoder.encode(k, "UTF-8")).append('=').append(URLEncoder.encode(v, "UTF-8"));
                separator = "&";
            }
        } catch (UnsupportedEncodingException e) {
            Log.e("ArrayUtils", "Unable to format query string", e);
        }
        return sb.toString();
    }

    /**
     * @param map as HashMap<String, String>
     * @return as JSONObject
     */
    public static JSONObject mapToJsonObject(Map<String, String> map) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, String> e : map.entrySet()) {
            jsonObject.put(e.getKey(), e.getValue());
        }
        return jsonObject;
    }

    /**
     * @param list as Collection<T>
     * @return as <T extends IModelObject> JSONArray
     */
    public static <T extends IModelObject> JSONArray listToJsonArray(Collection<T> list) {
        JSONArray array = new JSONArray();
        for (T value : list) {
            if (value != null) {
                array.put(value.toJsonObject());
            } else {
                array.put(null);
            }
        }
        return array;
    }

    /**
     * @param list as Collection<String>
     * @return as JSONArray
     */
    public static JSONArray listToJsonStringArray(Collection<String> list) {
        JSONArray array = new JSONArray();
        for (String value : list) {
            array.put(value);
        }
        return array;
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isEmpty(Map<?,?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map<?,?> map) {
        return !isEmpty(map);
    }

    /**
     * @param jsonObject as JSONObject
     * @return as Map<String, String>
     */
    public static Map<String, String> jsonObjectToMap(JSONObject jsonObject) throws JSONException {
        Map<String, String> map = new HashMap<>();
        for (Iterator<String> keys = jsonObject.keys(); keys.hasNext();) {
            String key = keys.next();
            map.put(key, jsonObject.getString(key));
        }
        return map;
    }

    /**
     * @param array as JSONArray
     * @return as Collection<String>
     */
    public static Collection<String> jsonArrayToStringList(JSONArray array) throws JSONException {
        List<String> list = new ArrayList<>(array.length());
        for (int i = 0; i < array.length(); ++i) {
            list.add(array.getString(i));
        }
        return list;
    }
}

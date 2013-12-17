package com.att.m2x.helpers;

import java.util.ArrayList;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.att.m2x.Serializable;

public class JSONHelper {

	public static String stringValue(JSONObject obj, String key, String defaultValue) {		
		String value = defaultValue;
		try {
			value = obj.getString(key);
		} catch (JSONException e) {
		}
		return value;
	}
	
	public static double doubleValue(JSONObject obj, String key, double defaultValue) {		
		double value = defaultValue;
		try {
			value = obj.getDouble(key);
		} catch (JSONException e) {
		}
		return value;
	}

	public static Date dateValue(JSONObject obj, String key, Date defaultValue) {		
		Date value = defaultValue;
		try {
			value = DateHelper.stringToDate(obj.getString(key));
		} catch (JSONException e) {
		}
		return value;
	}

	public static void put(JSONObject obj, String key, String value) {
		try {
			obj.put(key, value);
		} catch (JSONException e) {
		}
	}

	public static void put(JSONObject obj, String key, double value) {
		try {
			obj.put(key, value);
		} catch (JSONException e) {
		}
	}

	public static void put(JSONObject obj, String key, Date value) {
		try {
			obj.put(key, DateHelper.dateToString(value));
		} catch (JSONException e) {
		}
	}

	public static void put(JSONObject obj, String key, Object value) {
		try {
			obj.put(key, value);
		} catch (JSONException e) {
		}
	}
		
	public static JSONArray putValues(JSONObject obj, String key, ArrayList<? extends Serializable> values) {

		JSONArray objects = new JSONArray();
		if (values != null) {
			for (Serializable value : values) {
				objects.put(value.toJSONObject());
			}
		}
		JSONHelper.put(obj, key, objects);			
		return objects;
	}

}

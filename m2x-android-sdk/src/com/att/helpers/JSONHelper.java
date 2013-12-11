package com.att.helpers;

import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

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

}

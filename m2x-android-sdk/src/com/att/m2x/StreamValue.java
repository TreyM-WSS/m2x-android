package com.att.m2x;

import java.util.Locale;

import org.json.JSONObject;

import com.att.m2x.helpers.JSONHelper;

public class StreamValue extends com.att.m2x.model.StreamValue implements Serializable {

	private static final String DATE = "at";
	private static final String VALUE = "value";

	public StreamValue() {
		
	}
	
	public StreamValue(JSONObject obj) {
		this.setDate(JSONHelper.dateValue(obj, DATE, null));
		this.setValue(JSONHelper.doubleValue(obj, VALUE, 0));		
	}
	
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		JSONHelper.put(obj, DATE, this.getDate());
		JSONHelper.put(obj, VALUE, this.getValue());
		return obj;
	}

}

package com.att.m2x;

import org.json.JSONObject;
import com.att.m2x.helpers.JSONHelper;

public final class Trigger extends com.att.m2x.model.Trigger {

	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String STREAM = "stream";
	private static final String CONDITION = "condition";
	private static final String VALUE = "value";
	private static final String UNIT = "unit";
	private static final String CALLBACK_URL = "callback_url";
	private static final String URL = "url";
	private static final String STATUS = "status";
	private static final String CREATED = "created";
	private static final String UPDATED = "updated";

	public Trigger() {
		
	}
	
	public Trigger(JSONObject obj) {
		
		this.setId(JSONHelper.stringValue(obj, ID, ""));
		this.setName(JSONHelper.stringValue(obj, NAME, ""));
		this.setStream(JSONHelper.stringValue(obj, STREAM, ""));
		this.setCondition(JSONHelper.stringValue(obj, CONDITION, ""));
		this.setValue(JSONHelper.doubleValue(obj, VALUE, 0));
		this.setUnit(JSONHelper.stringValue(obj, UNIT, ""));
		this.setCallbackUrl(JSONHelper.stringValue(obj, CALLBACK_URL, ""));
		this.setUrl(JSONHelper.stringValue(obj, URL, ""));
		this.setStatus(JSONHelper.stringValue(obj, STATUS, ""));
		this.setCreated(JSONHelper.dateValue(obj, CREATED, null));
		this.setUpdated(JSONHelper.dateValue(obj, UPDATED, null));
	}
	
}

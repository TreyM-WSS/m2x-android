package com.att.m2x;

import org.json.JSONException;
import org.json.JSONObject;
import com.att.m2x.helpers.JSONHelper;

public final class Stream extends com.att.m2x.model.Stream {

	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String VALUE = "value";
	private static final String LATESTVALUEAT = "latest_value_at";
	private static final String MIN = "min";
	private static final String MAX = "max";
	private static final String UNIT = "unit";
	private static final String URL = "url";
	private static final String CREATED = "created";
	private static final String UPDATED = "updated";

	public Stream() {
		
	}
	
	public Stream(JSONObject obj) {
		
		this.setId(JSONHelper.stringValue(obj, ID, ""));
		this.setName(JSONHelper.stringValue(obj, NAME, ""));
		this.setValue(JSONHelper.doubleValue(obj, VALUE, 0));
		this.setLatestValueAt(JSONHelper.dateValue(obj, LATESTVALUEAT, null));
		this.setMin(JSONHelper.doubleValue(obj, MIN, 0));
		this.setMax(JSONHelper.doubleValue(obj, MAX, 0));
		try {
			Unit unit = new Unit(obj.getJSONObject(UNIT));
			this.setUnit(unit);
		} catch (JSONException e) {
		}
		this.setUrl(JSONHelper.stringValue(obj, URL, ""));
		this.setCreated(JSONHelper.dateValue(obj, CREATED, null));
		this.setUpdated(JSONHelper.dateValue(obj, UPDATED, null));
	}

}

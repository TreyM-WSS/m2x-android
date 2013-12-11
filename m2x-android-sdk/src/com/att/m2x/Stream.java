package com.att.m2x;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.att.helpers.JSONHelper;

public class Stream {

	private String id;
	private String name;
	private double value;
	private Date latestValueAt;
	private double min;
	private double max;
	private Unit unit;
	private String url;
	private Date created;
	private Date updated;
	
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
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Date getLatestValueAt() {
		return latestValueAt;
	}

	public void setLatestValueAt(Date latestValueAt) {
		this.latestValueAt = latestValueAt;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	public static Stream streamFromJSONObject(JSONObject obj) {
		
		Stream s = new Stream();
		s.id = JSONHelper.stringValue(obj, ID, "");
		s.name = JSONHelper.stringValue(obj, NAME, "");
		s.value = JSONHelper.doubleValue(obj, VALUE, 0);
		s.latestValueAt = JSONHelper.dateValue(obj, LATESTVALUEAT, null);
		s.min = JSONHelper.doubleValue(obj, MIN, 0);
		s.max = JSONHelper.doubleValue(obj, MAX, 0);		
		
		try {
			s.unit = Unit.unitFromJSONObject(obj.getJSONObject(UNIT));
		} catch (JSONException e) {
		}
		
		s.url = JSONHelper.stringValue(obj, URL, "");
		s.created = JSONHelper.dateValue(obj, CREATED, null);
		s.updated = JSONHelper.dateValue(obj, UPDATED, null);
		return s;
	}
	
}

package com.att.m2x;

import java.util.Date;
import org.json.JSONObject;

import com.att.helpers.JSONHelper;

public class Trigger {

	private String id;
	private String name;
	private String stream;
	private String condition;
	private double value;
	private String unit;
	private String callbackUrl;
	private String url;
	private String status;
	private Date created;
	private Date updated;
	
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
	
	public String getStream() {
		return stream;
	}
	
	public void setStream(String stream) {
		this.stream = stream;
	}
	
	public String getCondition() {
		return condition;
	}
	
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	public String getUnit() {
		return unit;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getCallbackUrl() {
		return callbackUrl;
	}
	
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
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
	
	public static Trigger triggerFromJSONObject(JSONObject obj) {
		
		Trigger t = new Trigger();
		t.id = JSONHelper.stringValue(obj, ID, "");
		t.name = JSONHelper.stringValue(obj, NAME, "");
		t.stream = JSONHelper.stringValue(obj, STREAM, "");
		t.condition = JSONHelper.stringValue(obj, CONDITION, "");
		t.value = JSONHelper.doubleValue(obj, VALUE, 0);
		t.unit = JSONHelper.stringValue(obj, UNIT, "");
		t.callbackUrl = JSONHelper.stringValue(obj, CALLBACK_URL, "");
		t.url = JSONHelper.stringValue(obj, URL, "");
		t.status = JSONHelper.stringValue(obj, STATUS, "");
		t.created = JSONHelper.dateValue(obj, CREATED, null);
		t.updated = JSONHelper.dateValue(obj, UPDATED, null);
		return t;
	}
}

package com.att.m2x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
		
		try {
			t.id = obj.getString("id");
		} catch (JSONException e) {
			t.id = null;
		}

		try {
			t.name = obj.getString("name");
		} catch (JSONException e) {
			t.name = null;
		}

		try {
			t.stream = obj.getString("stream");
		} catch (JSONException e) {
			t.stream = null;
		}

		try {
			t.condition = obj.getString("condition");
		} catch (JSONException e) {
			t.condition = null;
		}

		try {
			t.value = obj.getDouble("value");
		} catch (JSONException e) {
			t.value = 0;
		}

		try {
			t.unit = obj.getString("unit");
		} catch (JSONException e) {
			t.unit = null;
		}

		try {
			t.callbackUrl = obj.getString("callback_url");
		} catch (JSONException e) {
			t.callbackUrl = null;
		}

		try {
			t.url = obj.getString("url");
		} catch (JSONException e) {
			t.url = null;
		}

		try {
			t.status = obj.getString("status");
		} catch (JSONException e) {
			t.status = null;
		}

		try {
			t.created = sdf.parse(obj.getString("created"));
		} catch (JSONException e) {
			t.created = null;
		} catch (ParseException e) {
			t.created = null;
		}

		try {
			t.updated = sdf.parse(obj.getString("updated"));
		} catch (JSONException e) {
			t.updated = null;
		} catch (ParseException e) {
			t.updated = null;
		}
		
		return t;
	}
}

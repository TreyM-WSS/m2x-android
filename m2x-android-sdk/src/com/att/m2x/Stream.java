package com.att.m2x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);

		try {
			s.id = obj.getString("id");
		} catch (JSONException e) {
			s.id = null;
		}

		try {
			s.name = obj.getString("name");
		} catch (JSONException e) {
			s.name = null;
		}
		
		try {
			s.value = obj.getDouble("value");
		} catch (JSONException e) {
			s.value = 0;
		}
		
		try {
			s.latestValueAt = sdf.parse(obj.getString("latest_value_at"));
		} catch (JSONException e) {
			s.latestValueAt = null;
		} catch (ParseException e) {
			s.latestValueAt = null;
		}

		try {
			s.min = obj.getDouble("min");
		} catch (JSONException e) {
			s.min = 0;
		}

		try {
			s.max = obj.getDouble("max");
		} catch (JSONException e) {
			s.max = 0;
		}
		
		try {
			s.unit = Unit.unitFromJSONObject(obj.getJSONObject("unit"));
		} catch (JSONException e) {
			s.unit = null;
		}
		
		try {
			s.url = obj.getString("url");
		} catch (JSONException e) {
			s.url = null;
		}

		try {
			s.created = sdf.parse(obj.getString("created"));
		} catch (JSONException e) {
			s.created = null;
		} catch (ParseException e) {
			s.created = null;
		}

		try {
			s.updated = sdf.parse(obj.getString("updated"));
		} catch (JSONException e) {
			s.updated = null;
		} catch (ParseException e) {
			s.updated = null;
		}

		return s;
	}
	
}

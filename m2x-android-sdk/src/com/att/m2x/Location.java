package com.att.m2x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

public class Location {
	
	private String name;
	private double latitude;
	private double longitude;
	private double elevation;
	private Date timestamp;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public double getElevation() {
		return elevation;
	}
	
	public void setElevation(double elevation) {
		this.elevation = elevation;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public static Location locationFromJSONObject(JSONObject obj) {

		Location i = new Location();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
		
		try {
			i.name = obj.getString("name");
		} catch (JSONException e) {
			i.name = null;
		}
		
		try {
			i.latitude = obj.getDouble("latitude");
		} catch (JSONException e) {
			i.latitude = 0;
		}

		try {
			i.longitude = obj.getDouble("longitude");
		} catch (JSONException e) {
			i.longitude = 0;
		}

		try {
			i.elevation = obj.getDouble("elevation");
		} catch (JSONException e) {
			i.elevation = 0;
		}

		try {
			i.timestamp = sdf.parse(obj.getString("timestamp"));
		} catch (JSONException e) {
			i.timestamp = null;
		} catch (ParseException e) {
			i.timestamp = null;
		}

		return i;
	}
	
}

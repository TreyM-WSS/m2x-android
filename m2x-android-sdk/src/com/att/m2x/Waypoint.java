package com.att.m2x;

import java.util.Date;

import org.json.JSONObject;

import com.att.helpers.JSONHelper;

public class Waypoint {
	
	private Date timestamp;
	private double latitude;
	private double longitude;
	private double elevation;
	
	private static final String TIMESTAMP = "timestamp";
	private static final String LATITUDE = "latitude";
	private static final String LONGITUDE = "longitude";
	private static final String ELEVATION = "elevation";
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
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
	
	public static Waypoint waypointFromJSONObject(JSONObject obj) {
		
		Waypoint w = new Waypoint();
		w.timestamp = JSONHelper.dateValue(obj, TIMESTAMP, null);
		w.latitude = JSONHelper.doubleValue(obj, LATITUDE, 0);
		w.longitude = JSONHelper.doubleValue(obj, LONGITUDE, 0);
		w.elevation = JSONHelper.doubleValue(obj, ELEVATION, 0);
		return w;
	}
	

}

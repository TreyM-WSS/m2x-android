package com.att.m2x;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.att.helpers.*;

public class Location {
	
	private String name;
	private double latitude;
	private double longitude;
	private double elevation;
	private Date timestamp;
	private ArrayList<Waypoint> waypoints;
	
	private static final String NAME = "name";
	private static final String LATITUDE = "latitude";
	private static final String LONGITUDE = "longitude";
	private static final String ELEVATION = "elevation";
	private static final String TIMESTAMP = "timestamp";
	private static final String WAYPOINTS = "waypoints";
	
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
	
	public ArrayList<Waypoint> getWaypoints() {
		return waypoints;
	}

	public void setWaypoints(ArrayList<Waypoint> waypoints) {
		this.waypoints = waypoints;
	}

	public static Location locationFromJSONObject(JSONObject obj) {

		Location l = new Location();
		l.name = JSONHelper.stringValue(obj, NAME, "");
		l.latitude = JSONHelper.doubleValue(obj, LATITUDE, 0);
		l.longitude = JSONHelper.doubleValue(obj, LONGITUDE, 0);
		l.elevation = JSONHelper.doubleValue(obj, ELEVATION, 0);
		l.timestamp = JSONHelper.dateValue(obj, TIMESTAMP, null);
		
		if (obj.has(WAYPOINTS)) {
			try {
				JSONArray items = obj.getJSONArray(WAYPOINTS);
				l.waypoints = new ArrayList<Waypoint>();
				for (int i = 0; i < items.length(); i++) {
					Waypoint waypoint = Waypoint.waypointFromJSONObject(items.getJSONObject(i));
					l.waypoints.add(waypoint);
				}
			} catch (JSONException e1) {
				l.waypoints = null;
			}
		}
		
		return l;
	}
	
}

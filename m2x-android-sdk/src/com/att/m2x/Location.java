package com.att.m2x;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.att.m2x.helpers.*;

public final class Location extends com.att.m2x.model.Location {

	private static final String NAME = "name";
	private static final String LATITUDE = "latitude";
	private static final String LONGITUDE = "longitude";
	private static final String ELEVATION = "elevation";
	private static final String TIMESTAMP = "timestamp";
	private static final String WAYPOINTS = "waypoints";

	private ArrayList<Waypoint> waypoints;

	public Location() {	
	}
	
	public Location(JSONObject obj) {
		this.setName(JSONHelper.stringValue(obj, NAME, ""));
		this.setLatitude(JSONHelper.doubleValue(obj, LATITUDE, 0));
		this.setLongitude(JSONHelper.doubleValue(obj, LONGITUDE, 0));
		this.setElevation(JSONHelper.doubleValue(obj, ELEVATION, 0));
		this.setTimestamp(JSONHelper.dateValue(obj, TIMESTAMP, null));
		
		if (obj.has(WAYPOINTS)) {
			try {
				JSONArray items = obj.getJSONArray(WAYPOINTS);
				ArrayList<Waypoint> waypoints = new ArrayList<Waypoint>();
				for (int i = 0; i < items.length(); i++) {
					Waypoint waypoint = new Waypoint(items.getJSONObject(i));
					waypoints.add(waypoint);
				}
				this.setWaypoints(waypoints);
			} catch (JSONException e1) {
				this.setWaypoints(null);
			}
		}		
	}
	
	public ArrayList<Waypoint> getWaypoints() {
		return waypoints;
	}

	public void setWaypoints(ArrayList<Waypoint> waypoints) {
		this.waypoints = waypoints;
	}
	
}

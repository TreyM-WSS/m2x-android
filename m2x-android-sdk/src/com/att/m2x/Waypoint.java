package com.att.m2x;

import org.json.JSONObject;
import com.att.m2x.helpers.*;

public final class Waypoint extends com.att.m2x.model.Waypoint {

	private static final String TIMESTAMP = "timestamp";
	private static final String LATITUDE = "latitude";
	private static final String LONGITUDE = "longitude";
	private static final String ELEVATION = "elevation";

	public Waypoint() {
	}

	public Waypoint(JSONObject obj) {
		this.setTimestamp(JSONHelper.dateValue(obj, TIMESTAMP, null));
		this.setLatitude(JSONHelper.doubleValue(obj, LATITUDE, 0));
		this.setLongitude(JSONHelper.doubleValue(obj, LONGITUDE, 0));
		this.setElevation(JSONHelper.doubleValue(obj, ELEVATION, 0));
	}

}

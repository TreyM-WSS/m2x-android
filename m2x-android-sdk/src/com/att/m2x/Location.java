package com.att.m2x;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import com.att.m2x.helpers.*;

public final class Location extends com.att.m2x.model.Location {

	public interface LocationListener {
		public void onSuccess(Location location);
		public void onError(String errorMessage);
	}

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
	
	public static void getLocation(Context context, String feedKey, String feedId, final LocationListener callback) {
		
		M2XHttpClient client = M2X.getInstance().getClient();
		String path = "/feeds/" + feedId + "/location";
		
		client.get(context, feedKey, path, null, new M2XHttpClient.Handler() {
									
			@Override
			public void onSuccess(int statusCode, JSONObject object) {

				Location location = new Location(object);
				callback.onSuccess(location);				
			}

			@Override
			public void onFailure(int statusCode, String body) {
				callback.onError(body);
			}
			
		});
		
	}
	
	public ArrayList<Waypoint> getWaypoints() {
		return waypoints;
	}

	public void setWaypoints(ArrayList<Waypoint> waypoints) {
		this.waypoints = waypoints;
	}

}

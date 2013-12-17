package com.att.m2x;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import com.att.m2x.helpers.*;

public final class Location extends com.att.m2x.model.Location implements Serializable {

	public interface LocationListener {
		public void onSuccess(Location location);
		public void onError(String errorMessage);
	}

	public interface UpdateListener {
		public void onSuccess();
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
			public void onFailure(int statusCode, String message) {
				callback.onError(message);
			}
			
		});
		
	}
	
	public void update(Context context, String feedKey, String feedId, final UpdateListener callback) {
		
		M2XHttpClient client = M2X.getInstance().getClient();
		String path = "/feeds/" + feedId + "/location";
		
		JSONObject content = this.toJSONObject();
		
		client.put(context, 
				feedKey,
				path, 
				content,
				new M2XHttpClient.Handler() {

					@Override
					public void onSuccess(int statusCode, JSONObject object) {
						callback.onSuccess();
					}

					@Override
					public void onFailure(int statusCode, String message) {						
						callback.onError(message);
					}
			
		});
		
	}
	
	public ArrayList<Waypoint> getWaypoints() {
		return waypoints;
	}

	public void setWaypoints(ArrayList<Waypoint> waypoints) {
		this.waypoints = waypoints;
	}

	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		JSONHelper.put(obj, NAME, this.getName());
		JSONHelper.put(obj, LATITUDE, this.getLatitude());
		JSONHelper.put(obj, LONGITUDE, this.getLongitude());
		JSONHelper.put(obj, ELEVATION, this.getElevation());
		JSONHelper.put(obj, TIMESTAMP, this.getTimestamp());
//		waypoints not being saved due to 500 status codes
//		JSONHelper.putValues(obj, WAYPOINTS, this.getWaypoints());
		return obj;
	}

}

package com.att.m2x;

import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.att.m2x.helpers.JSONHelper;

import android.content.Context;
import android.os.Parcel;

public final class Blueprint extends com.att.m2x.Feed {

	public interface BlueprintsListener {
		public void onSuccess(ArrayList<Blueprint> blueprints);
		public void onError(String errorMessage);		
	}
		
	private static final String SERIAL = "serial";
	private static final String PAGE_KEY = "blueprints";
	
	private String serial;

	public Blueprint(Parcel in) {
		super(in);
		serial = in.readString();
	}	

	public Blueprint(JSONObject obj) {
		super(obj);
		this.setSerial(JSONHelper.stringValue(obj, SERIAL, ""));
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeString(serial);
	}

	public static void getBlueprints(Context context, String feedKey, final BlueprintsListener callback) {
		
		M2XHttpClient client = M2X.getInstance().getClient();
		String path = "/blueprints";
		
		client.get(context, feedKey, path, null, new M2XHttpClient.Handler() {

			@Override
			public void onSuccess(int statusCode, JSONObject object) {

				ArrayList<Blueprint> array = new ArrayList<Blueprint>();
				try {
					JSONArray blueprints = object.getJSONArray(PAGE_KEY);
					for (int i = 0; i < blueprints.length(); i++) {
						Blueprint blueprint = new Blueprint(blueprints.getJSONObject(i));
						array.add(blueprint);
					}
				} catch (JSONException e) {
					Log.d("Failed to parse Blueprint JSON objects");
				}
				callback.onSuccess(array);
				
			}

			@Override
			public void onFailure(int statusCode, String body) {
				callback.onError(body);
			}
			
		});
		
	}
	
	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}
	
	public String toString() {
		return String.format(Locale.US, "M2X Blueprint - %s %s (serial: %s)", 
				this.getId(), 
				this.getName(), 
				this.getSerial() ); 
	}

}

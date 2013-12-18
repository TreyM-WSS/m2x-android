package com.att.m2x;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import com.att.m2x.helpers.JSONHelper;

public class RequestLogEntry extends com.att.m2x.model.RequestLogEntry {
	
	public interface Listener {
		public void onSuccess(ArrayList<RequestLogEntry> entries);
		public void onError(String errorMessage);		
	}
	
	private static final String DATE = "at";
	private static final String STATUS_CODE = "status";
	private static final String METHOD = "http_method";
	private static final String PATH = "path";
	
	private RequestLogEntry() {
	}

	private RequestLogEntry(JSONObject obj) {		
		this.setDate(JSONHelper.dateValue(obj, DATE, null));
		this.setStatusCode(JSONHelper.intValue(obj, STATUS_CODE, 0));
		this.setMethod(JSONHelper.stringValue(obj, METHOD, ""));
		this.setPath(JSONHelper.stringValue(obj, PATH, ""));
	}

	public static void getEntries(Context context, String feedKey, String feedId, final Listener callback) {
		M2XHttpClient client = M2X.getInstance().getClient();
		String path = "/feeds/" + feedId + "/log";
		
		client.get(context, feedKey, path, null, new M2XHttpClient.Handler() {
			
			@Override
			public void onSuccess(int statusCode, JSONObject object) {
				
				ArrayList<RequestLogEntry> array = new ArrayList<RequestLogEntry>();
				try {
					JSONArray entries = object.getJSONArray("requests");
					for (int i = 0; i < entries.length(); i++) {
						RequestLogEntry entry = new RequestLogEntry(entries.getJSONObject(i));
						array.add(entry);
					}
				} catch (JSONException e) {
					Log.d("Failed to parse RequestLogEntry JSON objects");
				}
				callback.onSuccess(array);
			}
			
			@Override
			public void onFailure(int statusCode, String message) {
				callback.onError(message);
			}
		});
	}
	
}

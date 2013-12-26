package com.att.m2x;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.att.m2x.helpers.JSONHelper;

import android.content.Context;

public class Key extends com.att.m2x.model.Key {

	public interface KeysListener {
		public void onSuccess(ArrayList<Key> keys);
		public void onError(String errorMessage);		
	}

	public interface KeyListener {
		public void onSuccess(Key key);
		public void onError(String errorMessage);		
	}
	
	public interface BasicListener {
		public void onSuccess();
		public void onError(String errorMessage);		
	}
	
	private static final String NAME = "name";
	private static final String KEY = "key";
	private static final String IS_MASTER = "master";
	private static final String FEED_URL = "feed";
	private static final String STREAM_URL = "stream";
	private static final String EXPIRES_AT = "expires_at";
	private static final String IS_EXPIRED = "expired";
	private static final String PERMISSIONS = "permissions";
	
	private static final String KEYS_PAGE_KEY = "keys";

	public Key(JSONObject obj) {
		
		this.setName(JSONHelper.stringValue(obj, NAME, ""));
		this.setKey(JSONHelper.stringValue(obj, KEY, ""));
		this.setIsMaster(JSONHelper.booleanValue(obj, IS_MASTER, false));
		this.setFeedUrl(JSONHelper.stringValue(obj, FEED_URL, ""));
		this.setStreamUrl(JSONHelper.stringValue(obj, STREAM_URL, ""));
		this.setExpiresAt(JSONHelper.dateValue(obj, EXPIRES_AT, null));
		this.setIsExpired(JSONHelper.booleanValue(obj, IS_EXPIRED, false));
		
		if (obj.has(PERMISSIONS)) {
			try {
				JSONArray items = obj.getJSONArray(PERMISSIONS);
				ArrayList<String> permissions = new ArrayList<String>();
				for (int i = 0; i < items.length(); i++) {
					permissions.add(items.getJSONObject(i).toString());
				}
				this.setPermissions(permissions);
			} catch (JSONException e1) {
				this.setPermissions(null);
			}			
		}

	}
	
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();		
		JSONHelper.put(obj, NAME, this.getName());

		StringBuilder sb = new StringBuilder();
		ArrayList<String> permissions = this.getPermissions();
		if (permissions != null) {
			for (String permission : permissions)
			{
			    sb.append(permission);
			    sb.append(",");
			}
			String joinedString = sb.toString();
			joinedString = joinedString.substring(0, joinedString.length()-1);
			JSONHelper.put(obj, PERMISSIONS, joinedString);			
		}

		String feedUrl = this.getFeedUrl();
		if (feedUrl != null) {
			JSONHelper.put(obj, FEED_URL, feedUrl);
			String streamUrl = this.getStreamUrl();
			if (streamUrl != null) {
				JSONHelper.put(obj, STREAM_URL, streamUrl);
			}
		}
		JSONHelper.put(obj, EXPIRES_AT, this.getExpiresAt());		
		return obj;
	}	
	
	public static void getKeys(Context context, final KeysListener callback) {
		Key.getKeys(context, null, callback);
	}
	
	public static void getKeys(Context context, String feedId, final KeysListener callback) {
		
		M2XHttpClient client = M2X.getInstance().getClient();
		String path = "/keys";
		
		HashMap<String, String> params = null;
		if (feedId != null) {
			params = new HashMap<String, String>();
			params.put("feed", feedId);		
		}
		
		client.get(context, null, path, params, new M2XHttpClient.Handler() {

			@Override
			public void onSuccess(int statusCode, JSONObject object) {

				ArrayList<Key> array = new ArrayList<Key>();
				try {
					JSONArray keys = object.getJSONArray(KEYS_PAGE_KEY);
					for (int i = 0; i < keys.length(); i++) {
						Key key = new Key(keys.getJSONObject(i));
						array.add(key);
					}
				} catch (JSONException e) {
					Log.d("Failed to parse Key JSON objects");
				}
				callback.onSuccess(array);
				
			}

			@Override
			public void onFailure(int statusCode, String body) {
				callback.onError(body);
			}
			
		});
		
	}
	
}

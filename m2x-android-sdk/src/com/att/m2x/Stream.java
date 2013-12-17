package com.att.m2x;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import com.att.m2x.helpers.JSONHelper;

public final class Stream extends com.att.m2x.model.Stream implements Serializable {

	public interface StreamsListener {
		public void onSuccess(ArrayList<Stream> streams);
		public void onError(String errorMessage);
	}

	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String VALUE = "value";
	private static final String LATESTVALUEAT = "latest_value_at";
	private static final String MIN = "min";
	private static final String MAX = "max";
	private static final String UNIT = "unit";
	private static final String URL = "url";
	private static final String CREATED = "created";
	private static final String UPDATED = "updated";

	public Stream() {
		
	}
	
	public Stream(JSONObject obj) {
		
		this.setId(JSONHelper.stringValue(obj, ID, ""));
		this.setName(JSONHelper.stringValue(obj, NAME, ""));
		this.setValue(JSONHelper.doubleValue(obj, VALUE, 0));
		this.setLatestValueAt(JSONHelper.dateValue(obj, LATESTVALUEAT, null));
		this.setMin(JSONHelper.doubleValue(obj, MIN, 0));
		this.setMax(JSONHelper.doubleValue(obj, MAX, 0));
		try {
			Unit unit = new Unit(obj.getJSONObject(UNIT));
			this.setUnit(unit);
		} catch (JSONException e) {
		}
		this.setUrl(JSONHelper.stringValue(obj, URL, ""));
		this.setCreated(JSONHelper.dateValue(obj, CREATED, null));
		this.setUpdated(JSONHelper.dateValue(obj, UPDATED, null));
	}
	
	public static void getStreams(Context context, String feedKey, String feedId, final StreamsListener callback) {

		M2XHttpClient client = M2X.getInstance().getClient();
		String path = "/feeds/" + feedId + "/streams";
		
		client.get(context, feedKey, path, null, new M2XHttpClient.Handler() {

			@Override
			public void onSuccess(int statusCode, JSONObject object) {

				ArrayList<Stream> array = new ArrayList<Stream>();
				try {
					JSONArray streams = object.getJSONArray("streams");
					for (int i = 0; i < streams.length(); i++) {
						Stream stream = new Stream(streams.getJSONObject(i));
						array.add(stream);
					}
				} catch (JSONException e) {
					Log.d("Failed to parse json objects");
				}
				callback.onSuccess(array);
				
			}

			@Override
			public void onFailure(int statusCode, String body) {
				callback.onError(body);
			}
			
		});
		
	}
	
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		JSONHelper.put(obj, ID, this.getId());
		JSONHelper.put(obj, NAME, this.getName());
		JSONHelper.put(obj, VALUE, this.getValue());
		JSONHelper.put(obj, LATESTVALUEAT, this.getLatestValueAt());
		JSONHelper.put(obj, MIN, this.getMin());
		JSONHelper.put(obj, MAX, this.getMax());
		JSONHelper.put(obj, UNIT, this.getUnit());
		JSONHelper.put(obj, URL, this.getUrl());
		JSONHelper.put(obj, CREATED, this.getCreated());
		JSONHelper.put(obj, UPDATED, this.getUpdated());
		return obj;
	}
	
}

package com.att.m2x;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import com.att.m2x.helpers.JSONHelper;

public final class Trigger extends com.att.m2x.model.Trigger implements Serializable {

	public interface TriggersListener {
		public void onSuccess(ArrayList<Trigger> triggers);
		public void onError(String errorMessage);
	}

	public interface TriggerListener {
		public void onSuccess(Trigger trigger);
		public void onError(String errorMessage);		
	}

	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String STREAM = "stream";
	private static final String CONDITION = "condition";
	private static final String VALUE = "value";
	private static final String UNIT = "unit";
	private static final String CALLBACK_URL = "callback_url";
	private static final String URL = "url";
	private static final String STATUS = "status";
	private static final String CREATED = "created";
	private static final String UPDATED = "updated";

	public Trigger() {
		
	}
	
	public Trigger(JSONObject obj) {
		
		this.setId(JSONHelper.stringValue(obj, ID, ""));
		this.setName(JSONHelper.stringValue(obj, NAME, ""));
		this.setStream(JSONHelper.stringValue(obj, STREAM, ""));
		this.setCondition(JSONHelper.stringValue(obj, CONDITION, ""));
		this.setValue(JSONHelper.doubleValue(obj, VALUE, 0));
		this.setUnit(JSONHelper.stringValue(obj, UNIT, ""));
		this.setCallbackUrl(JSONHelper.stringValue(obj, CALLBACK_URL, ""));
		this.setUrl(JSONHelper.stringValue(obj, URL, ""));
		this.setStatus(JSONHelper.stringValue(obj, STATUS, ""));
		this.setCreated(JSONHelper.dateValue(obj, CREATED, null));
		this.setUpdated(JSONHelper.dateValue(obj, UPDATED, null));
	}
	
	public static void getTriggers(Context context, String feedKey, String feedId, final TriggersListener callback) {

		M2XHttpClient client = M2X.getInstance().getClient();
		String path = "/feeds/" + feedId + "/triggers";
		
		client.get(context, feedKey, path, null, new M2XHttpClient.Handler() {

			@Override
			public void onSuccess(int statusCode, JSONObject object) {

				ArrayList<Trigger> array = new ArrayList<Trigger>();
				try {
					JSONArray triggers = object.getJSONArray("triggers");
					for (int i = 0; i < triggers.length(); i++) {
						Trigger trigger = new Trigger(triggers.getJSONObject(i));
						array.add(trigger);
					}
				} catch (JSONException e) {
					Log.d("Failed to parse Trigger JSON objects");
				}
				callback.onSuccess(array);
				
			}

			@Override
			public void onFailure(int statusCode, String body) {
				callback.onError(body);
			}
			
		});
		
	}

	public void create(Context context, String feedKey, String feedId, final TriggerListener callback) {
		
		M2XHttpClient client = M2X.getInstance().getClient();
		String path = "/feeds/" + feedId + "/triggers";
		JSONObject content = this.toJSONObject();
		client.post(context, feedKey, path, content, new M2XHttpClient.Handler() {

			@Override
			public void onSuccess(int statusCode, JSONObject object) {
				Trigger trigger = new Trigger(object);
				callback.onSuccess(trigger);				
			}

			@Override
			public void onFailure(int statusCode, String message) {
				callback.onError(message);
			}
			
		});

	}
	
	public JSONObject toJSONObject() {
		
		JSONObject obj = new JSONObject();		
		JSONHelper.put(obj, NAME, this.getName());
		JSONHelper.put(obj, STREAM, this.getStream());
		JSONHelper.put(obj, CONDITION, this.getCondition());
		JSONHelper.put(obj, VALUE, this.getValue());
		JSONHelper.put(obj, UNIT, this.getUnit());
		JSONHelper.put(obj, CALLBACK_URL, this.getCallbackUrl());
		JSONHelper.put(obj, URL, this.getUrl());
		JSONHelper.put(obj, STATUS, this.getStatus());
		return obj;
	}

}

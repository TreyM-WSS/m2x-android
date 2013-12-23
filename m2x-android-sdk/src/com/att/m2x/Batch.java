package com.att.m2x;

import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.att.m2x.helpers.JSONHelper;

import android.content.Context;
import android.os.Parcel;

public class Batch extends com.att.m2x.Feed {

	public interface DatasourceListener {
		public void onSuccess(Feed datasource);
		public void onError(String errorMessage);			
	}
	
	public interface DatasourcesListener {
		public void onSuccess(ArrayList<Feed> datasources);
		public void onError(String errorMessage);		
	}

	public interface BatchesListener {
		public void onSuccess(ArrayList<Batch> batches);
		public void onError(String errorMessage);		
	}

	public interface BatchListener {
		public void onSuccess(Batch batch);
		public void onError(String errorMessage);		
	}

	public interface BasicListener {
		public void onSuccess();
		public void onError(String errorMessage);				
	}

	protected static final String SERIAL = "serial";
	protected static final String BATCHES_PAGE_KEY = "batches";
	protected static final String DATASOURCES_PAGE_KEY = "datasources";
	protected static final String DATASOURCES = "datasources";
	protected static final String TOTAL = "total";
	protected static final String REGISTERED = "registered";
	protected static final String UNREGISTERED = "unregistered";

	private String serial;
	private int totalDatasources;
	private int registeredDatasources;
	private int unregisteredDatasources;
	
	public Batch() {
		
	}
	
	public Batch(Parcel in) {
		super(in);
		serial = in.readString();
		totalDatasources = in.readInt();
		registeredDatasources = in.readInt();
		unregisteredDatasources = in.readInt();
	}
	
	public Batch(JSONObject obj) {
		super(obj);
		this.setSerial(JSONHelper.stringValue(obj, SERIAL, ""));
		
		try {
			JSONObject datasources = obj.getJSONObject(DATASOURCES);
			this.setTotalDatasources(JSONHelper.intValue(datasources, TOTAL, 0));
			this.setRegisteredDatasources(JSONHelper.intValue(datasources, REGISTERED, 0));
			this.setUnregisteredDatasources(JSONHelper.intValue(datasources, UNREGISTERED, 0));
		} catch (JSONException e) {
		}
		
	}
	
	public static void getBatches(Context context, String feedKey, final BatchesListener callback) {
		
		M2XHttpClient client = M2X.getInstance().getClient();
		String path = "/batches";
		
		client.get(context, feedKey, path, null, new M2XHttpClient.Handler() {

			@Override
			public void onSuccess(int statusCode, JSONObject object) {

				ArrayList<Batch> array = new ArrayList<Batch>();
				try {
					JSONArray batches = object.getJSONArray(BATCHES_PAGE_KEY);
					for (int i = 0; i < batches.length(); i++) {
						Batch batch = new Batch(batches.getJSONObject(i));
						array.add(batch);
					}
				} catch (JSONException e) {
					Log.d("Failed to parse Batch JSON objects");
				}
				callback.onSuccess(array);
				
			}

			@Override
			public void onFailure(int statusCode, String body) {
				callback.onError(body);
			}
			
		});
		
	}
	
	public static void getBatch(Context context, String feedKey, String batchId, final BatchListener callback) {
		
		M2XHttpClient client = M2X.getInstance().getClient();
		String path = "/batches/" + batchId;
		
		client.get(context, feedKey, path, null, new M2XHttpClient.Handler() {

			@Override
			public void onSuccess(int statusCode, JSONObject object) {
				Batch batch = new Batch(object);
				callback.onSuccess(batch);
				
			}

			@Override
			public void onFailure(int statusCode, String body) {
				callback.onError(body);
			}
			
		});
		
	}

	public void getDatasources(Context context, String feedKey, final DatasourcesListener callback) {
		
		M2XHttpClient client = M2X.getInstance().getClient();
		String path = "/batches/" + this.getId() + "/datasources";
		
		client.get(context, feedKey, path, null, new M2XHttpClient.Handler() {

			@Override
			public void onSuccess(int statusCode, JSONObject object) {

				ArrayList<Feed> array = new ArrayList<Feed>();
				try {
					JSONArray datasources = object.getJSONArray(DATASOURCES_PAGE_KEY);
					for (int i = 0; i < datasources.length(); i++) {
						Feed datasource = new Feed(datasources.getJSONObject(i));
						array.add(datasource);
					}
				} catch (JSONException e) {
					Log.d("Failed to parse Datasource JSON objects");
				}
				callback.onSuccess(array);
				
			}

			@Override
			public void onFailure(int statusCode, String body) {
				callback.onError(body);
			}
			
		});
		
	}

	public void create(Context context, String feedKey, final BatchListener callback) {
		
		M2XHttpClient client = M2X.getInstance().getClient();
		String path = "/batches";
		JSONObject content = this.toJSONObject();
		client.post(context, feedKey, path, content, new M2XHttpClient.Handler() {

			@Override
			public void onSuccess(int statusCode, JSONObject object) {
				Batch batch = new Batch(object);
				callback.onSuccess(batch);				
			}

			@Override
			public void onFailure(int statusCode, String message) {
				callback.onError(message);
			}
			
		});
		
	}
	
	public void update(Context context, String feedKey, final BasicListener callback) {

		M2XHttpClient client = M2X.getInstance().getClient();
		String path = "/batches/" + this.getId();
		JSONObject content = this.toJSONObject();
		client.put(context, feedKey, path, content, new M2XHttpClient.Handler() {

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
		
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeInt(totalDatasources);
		dest.writeInt(registeredDatasources);
		dest.writeInt(unregisteredDatasources);
	}
	
	public void addDatasource(Context context, String feedKey, String serial, final DatasourceListener callback) {
		
		M2XHttpClient client = M2X.getInstance().getClient();
		String path = "/batches/" + this.getId() + "/datasources";
		
		JSONObject content = new JSONObject();
		JSONHelper.put(content, SERIAL, serial);
		
		client.post(context, feedKey, path, content, new M2XHttpClient.Handler() {

			@Override
			public void onSuccess(int statusCode, JSONObject object) {
				Feed datasource = new Feed(object);
				callback.onSuccess(datasource);				
			}

			@Override
			public void onFailure(int statusCode, String message) {
				callback.onError(message);
			}
			
		});

	}
	
	public String getSerial() {
		return serial;
	}
	
	public void setSerial(String serial) {
		this.serial = serial;
	}
	
	public int getTotalDatasources() {
		return totalDatasources;
	}
	
	public void setTotalDatasources(int totalDatasources) {
		this.totalDatasources = totalDatasources;
	}
	
	public int getRegisteredDatasources() {
		return registeredDatasources;
	}
	
	public void setRegisteredDatasources(int registeredDatasources) {
		this.registeredDatasources = registeredDatasources;
	}
	
	public int getUnregisteredDatasources() {
		return unregisteredDatasources;
	}
	
	public void setUnregisteredDatasources(int unregisteredDatasources) {
		this.unregisteredDatasources = unregisteredDatasources;
	}

	public String toString() {
		return String.format(Locale.US, "M2X Batch - %s %s (total datasources: %d)", 
				this.getId(), 
				this.getName(), 
				this.getTotalDatasources() ); 
	}

}

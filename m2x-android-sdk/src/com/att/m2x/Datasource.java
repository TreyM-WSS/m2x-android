package com.att.m2x;

import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.att.m2x.helpers.JSONHelper;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

public final class Datasource extends Feed {

	public interface DatasourceListener {
		public void onSuccess(Datasource datasource);
		public void onError(String errorMessage);			
	}
	
	public interface DatasourcesListener {
		public void onSuccess(ArrayList<Datasource> datasources);
		public void onError(String errorMessage);		
	}
	
	public interface BasicListener {
		public void onSuccess();
		public void onError(String errorMessage);				
	}

	protected static final String SERIAL = "serial";
	protected static final String DATASOURCES_PAGE_KEY = "datasources";
	
	private String serial;
	
	public Datasource() {
		
	}
	
	public Datasource(JSONObject obj) {
		super(obj);
		this.setSerial(JSONHelper.stringValue(obj, SERIAL, ""));
	}

	public Datasource(Parcel in) {
		super(in);
		serial = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeString(serial);
	}
	
	public static void getDatasources(Context context, final DatasourcesListener callback) {
		
		M2XHttpClient client = M2X.getInstance().getClient();
		String path = "/datasources";
		
		client.get(context, null, path, null, new M2XHttpClient.Handler() {

			@Override
			public void onSuccess(int statusCode, JSONObject object) {

				ArrayList<Datasource> array = new ArrayList<Datasource>();
				try {
					JSONArray datasources = object.getJSONArray(DATASOURCES_PAGE_KEY);
					for (int i = 0; i < datasources.length(); i++) {
						Datasource datasource = new Datasource(datasources.getJSONObject(i));
						array.add(datasource);
					}
				} catch (JSONException e) {
					M2XLog.d("Failed to parse Batch JSON objects");
				}
				callback.onSuccess(array);
				
			}

			@Override
			public void onFailure(int statusCode, String body) {
				callback.onError(body);
			}
			
		});
		
	}
	
	public static void getDatasource(Context context, String datasourceId, final DatasourceListener callback) {
		
		M2XHttpClient client = M2X.getInstance().getClient();
		String path = "/datasources/" + datasourceId;
		
		client.get(context, null, path, null, new M2XHttpClient.Handler() {

			@Override
			public void onSuccess(int statusCode, JSONObject object) {
				Datasource datasource = new Datasource(object);
				callback.onSuccess(datasource);
				
			}

			@Override
			public void onFailure(int statusCode, String body) {
				callback.onError(body);
			}
			
		});
		
	}
	
	public void create(Context context, final DatasourceListener callback) {
		
		M2XHttpClient client = M2X.getInstance().getClient();
		String path = "/datasources";
		JSONObject content = this.toJSONObject();
		client.post(context, null, path, content, new M2XHttpClient.Handler() {

			@Override
			public void onSuccess(int statusCode, JSONObject object) {
				Datasource datasource = new Datasource(object);
				callback.onSuccess(datasource);				
			}

			@Override
			public void onFailure(int statusCode, String message) {
				callback.onError(message);
			}
			
		});
		
	}

	public void update(Context context, final BasicListener callback) {

		M2XHttpClient client = M2X.getInstance().getClient();
		String path = "/datasources/" + this.getId();
		JSONObject content = this.toJSONObject();
		client.put(context, null, path, content, new M2XHttpClient.Handler() {

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
	
	public void delete(Context context, final BasicListener callback) {
		
		M2XHttpClient client = M2X.getInstance().getClient();
		String path = "/datasources/" + this.getId();
		client.delete(context, null, path, new M2XHttpClient.Handler() {

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

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}
	
	public String toString() {
		return String.format(Locale.US, "M2X Datasource - %s %s (serial: %s)", 
				this.getId(), 
				this.getName(), 
				this.getSerial() ); 
	}

	public static final Parcelable.Creator<Datasource> CREATOR = new Parcelable.Creator<Datasource>() {
	    public Datasource createFromParcel(Parcel in) {
	     return new Datasource(in);
	    }

	    public Datasource[] newArray(int size) {
	     return new Datasource[size];
	    }
	};

}

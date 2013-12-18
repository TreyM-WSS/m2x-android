package com.att.m2x;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map.Entry;
import org.apache.http.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;
import android.content.Context;

import com.att.m2x.helpers.JSONHelper;
import com.loopj.android.http.*;

public class M2XHttpClient {
	
	public interface Handler {
		public void onSuccess(int statusCode, JSONObject object);
		public void onFailure(int statusCode, String message);		
	}
	
	private final static String M2X_AUTH_HEADER = "X-M2X-KEY";
	private final static String USER_AGENT = "M2X Android API Client version " + M2X.VERSION;
	private final static String MESSAGE_ERROR_FIELD = "message";
	private final static String UNDEFINED_ERROR = "Undefined error";
	private final static String SERVER_ERROR = "Internal Server Error";

	private AsyncHttpClient client;
	private String masterKey;
	
	public M2XHttpClient() {
		client = new AsyncHttpClient();
		client.setUserAgent(USER_AGENT);
	}

	public void get(Context context, 
			String key, 
			String path, 
			HashMap<String, String> params, 
			final Handler handler) {
		
		String baseUrl = M2X.getInstance().getBaseUrl();
		String url = baseUrl + path;
		
		// create RequestParams for values provided in params
		RequestParams requestParams = this.requestParamsFromHashMap(params);

		// determine which API key we're going to use
		String keyValue = (key != null) ? key : masterKey;
		Header[] headers = { new BasicHeader(M2X_AUTH_HEADER, keyValue) };
		
		// call the actual http request client
		client.get(context, url, headers, requestParams, new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, org.apache.http.Header[] headers, org.json.JSONObject response) {
				
				handler.onSuccess(statusCode, response);
            }
						
			@Override
			public void onFailure(int statusCode, java.lang.Throwable e, org.json.JSONObject errorResponse) {
				
				String message = JSONHelper.stringValue(errorResponse, MESSAGE_ERROR_FIELD, UNDEFINED_ERROR);
				handler.onFailure(statusCode, message);
			}
			
		});
				
	}
	
	public void put(Context context, 
			String key, 
			String path,
			JSONObject data,
			final Handler handler) {
		
		String url = M2X.getInstance().getBaseUrl().concat(path);

		// determine which API key we're going to use
		String keyValue = (key != null) ? key : masterKey;
		Header[] headers = { new BasicHeader(M2X_AUTH_HEADER, keyValue) };

		HttpEntity body;
		try {
			
			body = new StringEntity(data.toString());				
			client.put(context, url, headers, body, "application/json", new JsonHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseBody) {
					
					handler.onSuccess(statusCode, null);
				}
								
				@Override
				public void onFailure(int statusCode, Header[] headers,
						Throwable e, JSONObject errorResponse) {

					String message = JSONHelper.stringValue(errorResponse, MESSAGE_ERROR_FIELD, UNDEFINED_ERROR);
					handler.onFailure(statusCode, message);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseBody, Throwable e) {

					if (statusCode == 500) {
						handler.onFailure(statusCode, SERVER_ERROR);						
					} else {
						super.onFailure(statusCode, headers, responseBody, e);						
					}					
					
				}
				
			});
			
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
				
	}

	public void post(Context context, 
			String key, 
			String path,
			JSONObject data,
			final Handler handler) {

		String url = M2X.getInstance().getBaseUrl().concat(path);

		// determine which API key we're going to use
		String keyValue = (key != null) ? key : masterKey;
		Header[] headers = { new BasicHeader(M2X_AUTH_HEADER, keyValue) };

		HttpEntity body = null;
		try {
			
			if (data != null) { 
				body = new StringEntity(data.toString());				
			}
			
			client.post(context, url, headers, body, "application/json", new JsonHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseBody) {
					
					if (statusCode == 201) {
						super.onSuccess(statusCode, headers, responseBody);
					} else {
						handler.onSuccess(statusCode, null);
					}					
				}
				
				@Override
				public void onSuccess(int statusCode, JSONObject response) {

					handler.onSuccess(statusCode, response);
				}
								
				@Override
				public void onFailure(int statusCode, Header[] headers,
						Throwable e, JSONObject errorResponse) {

					String message = JSONHelper.stringValue(errorResponse, MESSAGE_ERROR_FIELD, UNDEFINED_ERROR);
					handler.onFailure(statusCode, message);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseBody, Throwable e) {

					if (statusCode == 500) {
						handler.onFailure(statusCode, SERVER_ERROR);						
					} else {
						super.onFailure(statusCode, headers, responseBody, e);						
					}					
					
				}
				
			});
			
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public void delete(Context context, 
			String key, 
			String path,
			final Handler handler) {
		
		String url = M2X.getInstance().getBaseUrl().concat(path);

		// determine which API key we're going to use
		String keyValue = (key != null) ? key : masterKey;
		Header[] headers = { new BasicHeader(M2X_AUTH_HEADER, keyValue) };

		client.delete(context, url, headers, null, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseBody) {
				
				handler.onSuccess(statusCode, null);
			}
							
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable e, JSONObject errorResponse) {

				String message = JSONHelper.stringValue(errorResponse, MESSAGE_ERROR_FIELD, UNDEFINED_ERROR);
				handler.onFailure(statusCode, message);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseBody, Throwable e) {

				if (statusCode == 500) {
					handler.onFailure(statusCode, SERVER_ERROR);						
				} else {
					super.onFailure(statusCode, headers, responseBody, e);						
				}					
				
			}
			
		});				
	}
	
	public String getMasterKey() {
		return masterKey;
	}

	public void setMasterKey(String masterKey) {
		this.masterKey = masterKey;
	}
	
	private RequestParams requestParamsFromHashMap(HashMap<String, String> map) {
		RequestParams requestParams = null;
		if (map != null) {
			requestParams = new RequestParams();
			for(Entry<String, String> entry : map.entrySet()) {
			    requestParams.put(entry.getKey(), entry.getValue());
			}
		};
		return requestParams;
	}
	
}

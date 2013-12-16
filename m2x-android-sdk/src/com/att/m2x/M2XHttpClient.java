package com.att.m2x;

import java.util.HashMap;
import java.util.Map.Entry;
import org.apache.http.*;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;
import android.content.Context;

import com.att.m2x.helpers.JSONHelper;
import com.loopj.android.http.*;

public class M2XHttpClient {
	
	public interface Handler {
		public void onSuccess(int statusCode, JSONObject object);
		public void onFailure(int statusCode, String body);		
	}
	
	private final static String M2X_AUTH_HEADER = "X-M2X-KEY";
	private final static String MESSAGE_ERROR_FIELD = "message";
	private final static String UNDEFINED_ERROR = "Undefined error";

	private AsyncHttpClient client;
	private String masterKey;
	
	public M2XHttpClient() {
		client = new AsyncHttpClient(); 
	}

	public String getMasterKey() {
		return masterKey;
	}

	public void setMasterKey(String masterKey) {
		this.masterKey = masterKey;
	}

	public void get(Context context, String key, String path, HashMap<String, String> params, final Handler handler) {
		
		String url = M2X.getInstance().getBaseUrl().concat(path);

		// create RequestParams for values provided in params
		RequestParams requestParams = null;
		if (params != null) {
			requestParams = new RequestParams();
			for(Entry<String, String> entry : params.entrySet()) {
			    requestParams.put(entry.getKey(), entry.getValue());
			}
		};

		// determine which API key we're going to use
		String keyValue = (key != null) ? key : masterKey;
		Header[] headers = { new BasicHeader(M2X_AUTH_HEADER, keyValue) };
		
		// call the actual http request client
		client.get(context, url, headers, requestParams, new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, org.apache.http.Header[] headers, org.json.JSONObject response) {
				
				handler.onSuccess(statusCode, response);
            }
						
			public void onFailure(int statusCode, java.lang.Throwable e, org.json.JSONObject errorResponse) {
				
				String message = JSONHelper.stringValue(errorResponse, MESSAGE_ERROR_FIELD, UNDEFINED_ERROR);
				handler.onFailure(statusCode, message);
			}
		});
				
	}
	
}

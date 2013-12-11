/**
 * 
 */
package com.att.m2x;

import android.util.Log;
import com.loopj.android.http.*;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.*;

/**
 * @author leandrinux
 *
 */
public final class M2X {

	public interface FeedResponseListener {
		public void onSuccess(ArrayList<Feed> feeds);
		public void onError(String errorMessage);
	}

	private static String DEFAULT_BASE_URL = "http://api-m2x.att.citrusbyte.com/v1";
	private static String LOG_TAG = "M2X-SDK";
	private static M2X mInstance = null;
	private static AsyncHttpClient client;
	
	private String masterKey;
	private String baseUrl;

	private M2X() {
		baseUrl = DEFAULT_BASE_URL;
		client = new AsyncHttpClient();
	}
	
	public String getMasterKey() {
		return masterKey;
	}

	public void setMasterKey(String masterKey) {
		this.masterKey = masterKey;
		client.addHeader("X-M2X-KEY", masterKey);
	}
	
	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public void getFeeds(HashMap<String, String> params, final FeedResponseListener callback) {
		String url = this.baseUrl.concat("/feeds");
		
		RequestParams requestParams = null;
		if (params != null) {
			params.putAll(params);
		};
		
		client.get(url, requestParams, new JsonHttpResponseHandler() {
						
			@Override
			public void onSuccess(int statusCode,
                    org.apache.http.Header[] headers,
                    org.json.JSONObject response) {
				
				ArrayList<Feed> array = new ArrayList<Feed>();
				try {
					JSONArray feeds = response.getJSONArray("feeds");
					for (int i = 0; i < feeds.length(); i++) {
						Feed feed = Feed.feedFromJSONObject(feeds.getJSONObject(i));
						array.add(feed);
					}
				} catch (JSONException e) {
					Log.d(LOG_TAG, "Failed to parse json objects");
				}
				callback.onSuccess(array);
            }
			
			@Override
			public void onFailure(int statusCode,
                    org.apache.http.Header[] headers,
                    java.lang.String responseBody,
                    java.lang.Throwable e) {
				Log.d(LOG_TAG, String.format("Failed to get json objects (status %d): %@", statusCode, responseBody));
			}
			
		});
	}	

	public static M2X getInstance() {
		if (mInstance == null) {
			mInstance = new M2X();
		}
		return mInstance;
	}

	
}

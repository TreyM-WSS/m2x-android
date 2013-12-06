/**
 * 
 */
package com.att.m2x;

import android.util.Log;
import com.loopj.android.http.*;
import java.util.ArrayList;
import org.json.*;

/**
 * @author leandrinux
 *
 */
public final class M2X {

	private static String DEFAULT_BASE_URL = "http://api-m2x.att.citrusbyte.com/v1";
	private static M2X mInstance = null;
	private static AsyncHttpClient client;
	
	private String masterKey;
	private String baseUrl;

	private M2X() {
		baseUrl = DEFAULT_BASE_URL;
		client = new AsyncHttpClient();
	}
	
	// Feed-related methods
	public void getAllFeeds(final ResponseListener callback) {
		
		String url = this.baseUrl.concat("/feeds");
		client.addHeader("X-M2X-KEY", this.masterKey);
		client.get(url, null, new JsonHttpResponseHandler() {
						
			@Override
			public void onSuccess(int statusCode,
                    org.apache.http.Header[] headers,
                    org.json.JSONObject response) {
				
				ArrayList<Object> array = new ArrayList<Object>();
				try {
					JSONArray feeds = response.getJSONArray("feeds");
					for (int i = 0; i < feeds.length(); i++) {
						Feed feed = Feed.feedFromJSONObject(feeds.getJSONObject(i));
						array.add(feed);
					}
				} catch (JSONException e) {
					Log.d("test", "failed to parse json objects");
				}
				callback.onSuccess(array);
            }
			
			@Override
			public void onFailure(int statusCode,
                    org.apache.http.Header[] headers,
                    java.lang.String responseBody,
                    java.lang.Throwable e) {
				Log.d("test", "failed to get json objects: ".concat(responseBody));
			}
			
		});
		
	}	
	
	// Getters and setters
	public String getMasterKey() {
		return masterKey;
	}

	public void setMasterKey(String masterKey) {
		this.masterKey = masterKey;
	}
	
	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	// Singleton class method
	public static M2X getInstance() {
		if (mInstance == null) {
			mInstance = new M2X();
		}
		return mInstance;
	}

	
}

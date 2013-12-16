package com.att.m2x.testapp;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import com.att.m2x.*;

public class MainActivity extends Activity {

	private static String LOG_TAG = "M2X-TestApp"; 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); 
        
        M2X.getInstance().setMasterKey("8181c16a0097325041a0c5a55f4fee1d");
        
//        this.loadFeeds();
        this.loadFeed();  
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private void loadFeed() { 
    	
    	Feed.getFeed(this, "7fde9db5578f3ba4b3a70a15893a9f04", "bb15f48d8e53131faa47efe04cff734e", new Feed.FeedListener()  {
			
			@Override
			public void onSuccess(Feed feed) {
				Log.d("M2X-TestApp", "Found feed: ".concat(feed.toString()));
			}
			
			@Override
			public void onError(String errorMessage) {
				Log.d("M2X-TestApp", "Failed to get feed: ".concat(errorMessage));
			}
		});

    }
    
    private void loadFeeds() {
    	
    	Feed.getFeeds(this, null, new Feed.FeedsListener() { 

    		public void onSuccess(ArrayList<Feed> feeds) {
        		Log.d(LOG_TAG, String.format("Obtained %d feeds", feeds.size()));
        		for (Feed feed : feeds) {
        			Log.d(LOG_TAG, feed.toString());
        			Log.d(LOG_TAG, "Feed key: ".concat(feed.getKey()));
        		}    			
    		}
    		
    		public void onError(String errorMessage) {
        		Log.d(LOG_TAG, "Failed to obtain feeds: ".concat(errorMessage));
        		
    		}

    	});
    	
    }
}

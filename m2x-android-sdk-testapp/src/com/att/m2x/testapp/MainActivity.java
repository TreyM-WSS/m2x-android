package com.att.m2x.testapp;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import com.att.m2x.*;
import com.att.m2x.Stream.ValuesListener;

public class MainActivity extends Activity {

	private static String LOG_TAG = "M2X-TestApp"; 

	private static String TEST_FEED_KEY = "7fde9db5578f3ba4b3a70a15893a9f04"; 
	private static String TEST_FEED_ID = "bb15f48d8e53131faa47efe04cff734e";
	private static String TEST_STREAM_NAME = "temperature";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); 
        
        M2X.getInstance().setMasterKey("8181c16a0097325041a0c5a55f4fee1d");
        
//        this.loadFeeds();
//        this.loadFeed();
//        this.loadLocation();
//        this.updateLocation();
//        this.loadStreams();
//        this.createStream();
        this.loadStream();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void loadStreamValues(Stream stream) {
    	
    	stream.getValues(this, TEST_FEED_KEY, TEST_FEED_ID, null, new Stream.ValuesListener() {

			@Override
			public void onSuccess(ArrayList<StreamValue> values) {
        		Log.d(LOG_TAG, String.format("Found %d stream values", values.size()));
        		for (StreamValue streamValue : values) {
        			Log.d(LOG_TAG, streamValue.toString());
        		}    							
			}

			@Override
			public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Failed to get stream values: ".concat(errorMessage));
			}
    		
    	});
    }
    
    private void loadStream() {

    	Stream.getStream(this, TEST_FEED_KEY, TEST_FEED_ID, TEST_STREAM_NAME, new Stream.StreamListener() {

			@Override
			public void onSuccess(Stream stream) {
				Log.d(LOG_TAG, "Found stream: ".concat(stream.toString()));
				loadStreamValues(stream);
			}

			@Override
			public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Failed to get stream: ".concat(errorMessage));
			}
    		
    	});
    	
    }
    
    private void createStream() {
    	Stream stream = new Stream();
    	stream.setName("Test stream");
    	
    	Unit unit = new Unit();
    	unit.setLabel("Kilos");
    	unit.setSymbol("Kg"); 
    	stream.setUnit(unit);
    	
    	stream.update(this, TEST_FEED_KEY, TEST_FEED_ID, new Stream.UpdateListener() {

			@Override
			public void onSuccess() {
				Log.d(LOG_TAG, "Stream added successfully!");
			}

			@Override
			public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Stream creation failed :( ".concat(errorMessage));				
			}
    		
    	});
    	
    }
    
    private void loadStreams() {
    	
    	Stream.getStreams(this, TEST_FEED_KEY, TEST_FEED_ID, new Stream.StreamsListener() {

			@Override
			public void onSuccess(ArrayList<Stream> streams) {
        		Log.d(LOG_TAG, String.format("Obtained %d streams", streams.size()));
        		for (Stream stream : streams) {
        			Log.d(LOG_TAG, stream.toString());
        		}    							
        		
			}

			@Override
			public void onError(String errorMessage) {

        		Log.d(LOG_TAG, "Failed to obtain streams: ".concat(errorMessage));				
			}
    		
    	});
    	
    }
    
    private void updateLocation() {
    	Location loc = new Location();
    	loc.setName("AT&T Corporate HQ");
    	loc.setLatitude(32.779846);
    	loc.setLongitude(-96.799179);
    	loc.setElevation(10);
    	loc.update(this, TEST_FEED_KEY, TEST_FEED_ID, new Location.UpdateListener() {

    		public void onSuccess() {
				Log.d(LOG_TAG, "Location update successful!");
    		}
    		
    		public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Location update failed :( ".concat(errorMessage));    			
    		}

    	});
    }
    
    private void loadLocation() { 
    	
    	Location.getLocation(this, TEST_FEED_KEY, TEST_FEED_ID, new Location.LocationListener()  {
			
			@Override
			public void onSuccess(Location location)  {
				Log.d(LOG_TAG, "Found location: ".concat(location.toString()));
			}
			
			@Override
			public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Failed to get location: ".concat(errorMessage));
			}
		});

    }
    
    private void loadFeed() { 
    	
    	Feed.getFeed(this, "7fde9db5578f3ba4b3a70a15893a9f04", "bb15f48d8e53131faa47efe04cff734e", new Feed.FeedListener()  {
			
			@Override
			public void onSuccess(Feed feed) {
				Log.d(LOG_TAG, "Found feed: ".concat(feed.toString()));
			}
			
			@Override
			public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Failed to get feed: ".concat(errorMessage));
			}
		});

    }
    
    private void loadFeeds() {
    	
    	Feed.getFeeds(this, null, new Feed.FeedsListener() { 

    		public void onSuccess(ArrayList<Feed> feeds) {
        		Log.d(LOG_TAG, String.format("Obtained %d feeds", feeds.size()));
        		for (Feed feed : feeds) {
        			Log.d(LOG_TAG, feed.toString());
        		}    			
    		}
    		
    		public void onError(String errorMessage) {
        		Log.d(LOG_TAG, "Failed to obtain feeds: ".concat(errorMessage));
        		
    		}

    	});
    	
    }

}

package com.att.m2x.testapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import com.att.m2x.*;
import com.att.m2x.Stream.BasicListener;
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
//        this.loadStream();
//        this.loadTriggers();
        this.createTrigger();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void createTrigger() {
    	
    	final Trigger trigger = new Trigger();
    	trigger.setStream("temperature");
    	trigger.setCondition(">");
    	trigger.setValue(10);
    	trigger.setCallbackUrl("http://www.example.com");
    	trigger.setStatus("enabled");
    	trigger.setName("Test trigger");
    	
    	trigger.create(this, TEST_FEED_KEY, TEST_FEED_ID, new Trigger.TriggerListener() {

			@Override
			public void onSuccess(Trigger trigger) {
				Log.d(LOG_TAG, String.format(Locale.US, "Trigger created successfully with id %s", trigger.getId()));

			}

			@Override
			public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Failed to create trigger " + trigger.getName());				
			}
    		
    	});
    	
    }
    
    private void loadTriggers() {
    	
    	Trigger.getTriggers(this, TEST_FEED_KEY, TEST_FEED_ID, new Trigger.TriggersListener() {

			@Override
			public void onSuccess(ArrayList<Trigger> triggers) {
        		Log.d(LOG_TAG, String.format("Obtained %d triggers", triggers.size()));
        		for (Trigger trigger : triggers) { 
        			Log.d(LOG_TAG, trigger.toString());
        		}    			
				
			}

			@Override
			public void onError(String errorMessage) {
        		Log.d(LOG_TAG, "Failed to obtain triggers: ".concat(errorMessage));				
			}
    		
    	});
    	
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
    
    private void addMultipleRandomValuesToFeed(final Feed feed) {

    	// we're going to add two random values to each stream using a single request.
		HashMap<Stream, ArrayList<StreamValue>> data = new HashMap<Stream, ArrayList<StreamValue>>(); 
		
		for (Stream stream : feed.getStreams()) {
			// generate random value
	    	Random randomGenerator = new Random(); 
	    	
    		ArrayList<StreamValue> values = new ArrayList<StreamValue>();
	    	for (int i=0;i<2;i++) {
	    		double reading = randomGenerator.nextDouble() * 100;
	    		StreamValue value = new StreamValue();
	    		value.setValue(reading);
	    		value.setDate(new Date());
	    		values.add(value);	    		
	    	}
    		
    		data.put(stream, values);
		}
    	
		feed.setValuesForMultipleStreams(this, TEST_FEED_KEY, data,  new Feed.BasicListener() {

			@Override
			public void onSuccess() {

				Log.d(LOG_TAG, "Successfully added random values to all streams in ".concat(feed.getName()));
			}

			@Override
			public void onError(String errorMessage) {
				
				Log.d(LOG_TAG, "Failed to add random values to all streams in ".concat(feed.getName()));
			}
			
		});
    }
    
    private void addRandomValuesToStream(final Stream stream) {
    	
    	Random randomGenerator = new Random(); 
    	ArrayList<StreamValue> readings = new ArrayList<StreamValue>();
    	for (int i = 0 ; i < 5 ; i++) {
    		double reading = randomGenerator.nextDouble() * 100;
    		StreamValue value = new StreamValue();
    		value.setValue(reading);
    		value.setDate(new Date());
    		readings.add(value);
    	}
    	
    	stream.setValues(this, TEST_FEED_KEY, TEST_FEED_ID, readings, new Stream.BasicListener() {

			@Override
			public void onSuccess() {
				Log.d(LOG_TAG, "Successfully added random values to stream");				
				loadStreamValues(stream);
			}

			@Override
			public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Failed to add random values to stream: ".concat(errorMessage));
			}
    		
    	});
    	
    }
    
    private void loadStream() {

    	Stream.getStream(this, TEST_FEED_KEY, TEST_FEED_ID, TEST_STREAM_NAME, new Stream.StreamListener() {

			@Override
			public void onSuccess(Stream stream) {
				Log.d(LOG_TAG, "Found stream: ".concat(stream.toString()));
				addRandomValuesToStream(stream);
			}

			@Override
			public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Failed to get stream: ".concat(errorMessage));
			}
    		
    	});
    	
    }
    
    private void deleteStream(Stream stream) {
    	
    	stream.delete(this, TEST_FEED_KEY, TEST_FEED_ID, new Stream.BasicListener() {
			
			@Override
			public void onSuccess() {
				Log.d(LOG_TAG, "Stream deleted successfully!");
			}
			
			@Override
			public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Stream deletion failed :( ".concat(errorMessage));
			}
		});
    	
    }
    
    private void createStream() {
    	final Stream stream = new Stream();
    	stream.setName("Test stream");
    	
    	Unit unit = new Unit();
    	unit.setLabel("Kilos");
    	unit.setSymbol("Kg"); 
    	stream.setUnit(unit);
    	
    	stream.update(this, TEST_FEED_KEY, TEST_FEED_ID, new Stream.BasicListener() {

			@Override
			public void onSuccess() {
				Log.d(LOG_TAG, "Stream added successfully!");
				deleteStream(stream); 
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
    	
    	Feed.getFeed(this, TEST_FEED_KEY, TEST_FEED_ID, new Feed.FeedListener()  {
			
			@Override
			public void onSuccess(Feed feed) {
				Log.d(LOG_TAG, "Found feed: ".concat(feed.toString()));
				addMultipleRandomValuesToFeed(feed);
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

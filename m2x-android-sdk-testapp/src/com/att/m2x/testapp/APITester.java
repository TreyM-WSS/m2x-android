package com.att.m2x.testapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import android.content.Context;
import android.util.Log;

import com.att.m2x.*;

public class APITester {

	private static String LOG_TAG = "M2X-TestApp"; 
	private static String TEST_FEED_KEY = "7fde9db5578f3ba4b3a70a15893a9f04"; 
	private static String TEST_FEED_ID = "bb15f48d8e53131faa47efe04cff734e";
	private static String EXTRA_TEST_KEY = "274290f1e03dbfd2aca8f39fca29003e";	
	private static String TEST_STREAM_NAME = "temperature";
		
	private Context defaultContext;
	
	public APITester(Context context) {
		defaultContext = context;
	}
	
	public void run() {		
//      this.loadFeeds();
//      this.loadFeed();
//      this.loadLocation();
//      this.updateLocation();
//      this.loadStreams();
//      this.createStream();
//      this.loadStream();
//      this.loadTriggers();
//      this.createTrigger();
//      this.loadRequestLogEntries();
	
//		this.loadBlueprint();
//		this.loadBlueprints();
//		this.createBlueprint();
		
//		this.createBatch();
		
//		this.createDatasource();
		
		this.beginKeysTest();
		
	}
	
	private void deleteKey(final Key key) {
		key.delete(defaultContext, new Key.BasicListener() {
			
			@Override
			public void onSuccess() {
				Log.d(LOG_TAG, "Key successfully deleted: " + key.toString());
			}
			
			@Override
			public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Failed to delete key: " + errorMessage);
			}
		});
	}

	private void createKeys() {
		
		Calendar today_plus_year = Calendar.getInstance();  
		today_plus_year.add( Calendar.YEAR, 1 ); 

//		Key masterKey = new Key();
//		masterKey.setName("Testing master key");
//		masterKey.setPermissions(new ArrayList<String>(Arrays.asList("GET", "PUT")));		
//		masterKey.setExpiresAt(today_plus_year.getTime());
//		masterKey.create(defaultContext, new Key.KeyListener() {
//			
//			@Override
//			public void onSuccess(Key key) {
//				Log.d(LOG_TAG, "Master key successfully created: " + key.toString());
//			}
//			
//			@Override
//			public void onError(String errorMessage) {
//				Log.d(LOG_TAG, "Failed to create master key: ".concat(errorMessage));				
//			}
//		});
		
		Key feedKey = new Key();
		feedKey.setName("Testing feed key");
		feedKey.setFeedId(TEST_FEED_ID);
		feedKey.setStreamName(TEST_STREAM_NAME);
		feedKey.setExpiresAt(today_plus_year.getTime());
		feedKey.setPermissions(new ArrayList<String>(Arrays.asList("GET", "PUT")));
		feedKey.create(defaultContext, new Key.KeyListener() {
			
			@Override
			public void onSuccess(Key key) {
				Log.d(LOG_TAG, "Feed key successfully created: " + key.toString());
				updateKey(key);
			}
			
			@Override
			public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Failed to create feed key: ".concat(errorMessage));				
			}
		});

	}
	
	private void regenerateKey(final Key key) {
		key.regenerate(defaultContext, new Key.KeyListener() {
			
			@Override
			public void onSuccess(Key key) {
				Log.d(LOG_TAG, "Key successfully regenerated: " + key.toString());
				deleteKey(key);
			}
			
			@Override
			public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Failed to regenerate key: " + errorMessage);
			}
		});
	}
	
	private void updateKey(final Key key) {
		key.setName("Updated key name");
		key.setStreamName(null);
		key.update(defaultContext, new Key.BasicListener() {
			
			@Override
			public void onSuccess() {
				Log.d(LOG_TAG, "Key successfully updated: " + key.toString());
				regenerateKey(key);
			}
			
			@Override
			public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Failed to update key: " + errorMessage);
			}
		});
	}
	
	private void beginKeysTest() {
		
//		Key.getKeys(defaultContext, new Key.KeysListener() {
//			
//			@Override
//			public void onSuccess(ArrayList<Key> keys) {
//        		Log.d(LOG_TAG, String.format("Obtained %d keys", keys.size()));
//        		for (Key key : keys) { 
//        			Log.d(LOG_TAG, key.toString());
//        		}
//			}
//			
//			@Override
//			public void onError(String errorMessage) {
//				Log.d(LOG_TAG, "Failed to list keys: ".concat(errorMessage));
//			}
//		});
//		
//		Key.getKeys(defaultContext, TEST_FEED_ID, new Key.KeysListener() {
//			
//			@Override
//			public void onSuccess(ArrayList<Key> keys) {
//        		Log.d(LOG_TAG, String.format("Obtained %d keys for test feed.", keys.size()));
//        		for (Key key : keys) { 
//        			Log.d(LOG_TAG, key.toString());
//        		}
//			}
//			
//			@Override
//			public void onError(String errorMessage) {
//				Log.d(LOG_TAG, "Failed to list keys for test feed: ".concat(errorMessage));
//			}
//		}); 
		
//		Key.getKey(defaultContext, EXTRA_TEST_KEY, new Key.KeyListener() {
//			
//			@Override
//			public void onSuccess(Key key) {
//				Log.d(LOG_TAG, "Found key: " + key.toString() );
//				updateKey(key);
//			}
//			
//			@Override
//			public void onError(String errorMessage) {
//				Log.d(LOG_TAG, "Failed to find key: " + errorMessage );
//			}
//		});
		
		createKeys();
//		createKeys();
	}
	
	private void deleteDatasource(final Datasource datasource) {
		datasource.delete(defaultContext, new Datasource.BasicListener() {
			
			@Override
			public void onSuccess() {
				Log.d(LOG_TAG, String.format("Deleted datasource %s successfully", datasource.toString()));
			}
			
			@Override
			public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Failed to delete datasource: ".concat(errorMessage));
			}
		});
	}
	
	private void updateDatasource(final Datasource datasource) {
		datasource.setTags(new ArrayList<String>(Arrays.asList("updated tag1", "updated tag2")));
		datasource.update(defaultContext, new Datasource.BasicListener() {
			
			@Override
			public void onSuccess() {
				Log.d(LOG_TAG, String.format("Updated datasource %s successfully", datasource.toString()));
				deleteDatasource(datasource);
			}
			
			@Override
			public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Failed to update datasource: ".concat(errorMessage));
			}
		});
	}
	
	private void loadDatasource(String datasourceId) {
		Datasource.getDatasource(defaultContext, datasourceId, new Datasource.DatasourceListener() {
			
			@Override
			public void onSuccess(Datasource datasource) {
				Log.d(LOG_TAG, String.format("Found datasource %s", datasource.toString()));
				updateDatasource(datasource);
			}
			
			@Override
			public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Failed to find datasource: ".concat(errorMessage));
			}
		});
	}

	private void createDatasource() {
		Datasource d = new Datasource();
		d.setName("Test Datasource");
		d.setDescription("Datasource for testing");
		d.setVisibility("public");
		d.setTags(new ArrayList<String>(Arrays.asList("datasource testing tag","lalala")));
		d.create(defaultContext, new Datasource.DatasourceListener() {
			
			@Override
			public void onSuccess(Datasource datasource) {
				Log.d(LOG_TAG, String.format("Successfully created datasource %s", datasource.toString()));
				loadDatasource(datasource.getId());
			}
			
			@Override
			public void onError(String errorMessage) {
        		Log.d(LOG_TAG, "Failed to create datasource: ".concat(errorMessage));
			}
		});
		
	}
	
	private void deleteBatch(final Batch batch) {
		batch.delete(defaultContext, new Batch.BasicListener() {
			
			@Override
			public void onSuccess() {
				Log.d(LOG_TAG, String.format("Deleted batch %s successfully", batch.toString()));
			}
			
			@Override
			public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Failed to delete batch: ".concat(errorMessage));
			}
		});
	}
	
	private void addDatasourcesToBatch(Batch batch) {
		deleteBatch(batch);
	}
	
	private void updateBatch(final Batch batch) {
		batch.setTags(new ArrayList<String>(Arrays.asList("updated tag1", "updated tag2")));
		batch.update(defaultContext, new Batch.BasicListener() {
			
			@Override
			public void onSuccess() {
				Log.d(LOG_TAG, String.format("Updated batch %s successfully", batch.toString()));
				addDatasourcesToBatch(batch);
			}
			
			@Override
			public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Failed to update batch: ".concat(errorMessage));
			}
		});
	}
	
	private void loadBatch(String batchId) {
		Batch.getBatch(defaultContext, batchId, new Batch.BatchListener() {
			
			@Override
			public void onSuccess(Batch batch) {
				Log.d(LOG_TAG, String.format("Found batch %s", batch.toString()));
				updateBatch(batch);
			}
			
			@Override
			public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Failed to find batch: ".concat(errorMessage));
			}
		});
	}
	
	private void createBatch() {
		Batch b = new Batch();
		b.setName("Test Batch");
		b.setDescription("Batch for testing");
		b.setVisibility("public");
		b.setTags(new ArrayList<String>(Arrays.asList("tag1", "another tag", "yet another tag")));
		
		b.create(defaultContext, new Batch.BatchListener() {
			
			@Override
			public void onSuccess(Batch batch) {
				Log.d(LOG_TAG, String.format("Successfully created batch %s", batch.toString()));
				loadBatch(batch.getId());
			}
			
			@Override
			public void onError(String errorMessage) {
        		Log.d(LOG_TAG, "Failed to create batch: ".concat(errorMessage));
			}
		});
		
	}
	
	private void deleteBlueprint(final Blueprint blueprint) {
		blueprint.delete(defaultContext, new Blueprint.BasicListener() {
			
			@Override
			public void onSuccess() {
				Log.d(LOG_TAG, String.format("Deleted blueprint %s successfully", blueprint.toString()));
			}
			
			@Override
			public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Failed to delete blueprint: ".concat(errorMessage));
			}
		});
	}
	
	private void updateBlueprint(final Blueprint blueprint) {
		blueprint.setTags(new ArrayList<String>(Arrays.asList("updated tag1", "updated tag2")));
		blueprint.update(defaultContext, new Blueprint.BasicListener() {
			
			@Override
			public void onSuccess() {
				Log.d(LOG_TAG, String.format("Updated blueprint %s successfully", blueprint.toString()));
				deleteBlueprint(blueprint);
			}
			
			@Override
			public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Failed to updated blueprint: ".concat(errorMessage));
			}
		});
	}
	
	private void loadBlueprint(String blueprintId) {
		Blueprint.getBlueprint(defaultContext, blueprintId, new Blueprint.BlueprintListener() {
			
			@Override
			public void onSuccess(Blueprint blueprint) {
				Log.d(LOG_TAG, String.format("Found blueprint %s", blueprint.toString()));
				updateBlueprint(blueprint);
			}
			
			@Override
			public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Failed to find blueprint: ".concat(errorMessage));
			}
		});
	}
	
	private void createBlueprint() {
		Blueprint b = new Blueprint();
		b.setName("Test Blueprint");
		b.setDescription("This is just a test");
		b.setVisibility("public");
		b.setTags(new ArrayList<String>(Arrays.asList("tag1", "another tag", "yet another tag")));
		
		b.create(defaultContext, new Blueprint.BlueprintListener() {
			
			@Override
			public void onSuccess(Blueprint blueprint) {
				Log.d(LOG_TAG, String.format("Successfully created blueprint %s", blueprint.toString()));
				loadBlueprint(blueprint.getId());
			}
			
			@Override
			public void onError(String errorMessage) {
        		Log.d(LOG_TAG, "Failed to create blueprint: ".concat(errorMessage));
			}
		});
		
	}
	
	private void loadBlueprints() {

    	Blueprint.getBlueprints(defaultContext, new Blueprint.BlueprintsListener() { 

    		public void onSuccess(ArrayList<Blueprint> blueprints) {
        		Log.d(LOG_TAG, String.format("Obtained %d blueprints", blueprints.size()));
        		for (Blueprint blueprint : blueprints) {
        			Log.d(LOG_TAG, blueprint.toString());
        		}    			
    		}
    		
    		public void onError(String errorMessage) {
        		Log.d(LOG_TAG, "Failed to obtain blueprints: ".concat(errorMessage));        		
    		}

    	});

	}

    private void deleteTrigger(final Trigger trigger) {
    	trigger.delete(defaultContext, TEST_FEED_KEY, TEST_FEED_ID, new Trigger.BasicListener() {
			
			@Override
			public void onSuccess() {
				Log.d(LOG_TAG, "Successfully deleted trigger: ".concat(trigger.toString()));				
			}
			
			@Override
			public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Failed to delete trigger: ".concat(trigger.toString()));				
			}
		});
    }
    
    private void testTrigger(final Trigger trigger) {
    	trigger.test(defaultContext, TEST_FEED_KEY, TEST_FEED_ID, new Trigger.BasicListener() {
			
			@Override
			public void onSuccess() {
				Log.d(LOG_TAG, "Successfully tested trigger: ".concat(trigger.toString()));
				deleteTrigger(trigger);
			}
			
			@Override
			public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Failed to test trigger: ".concat(trigger.toString()));
			}
		});
    	
    }
    
    private void updateTrigger(final Trigger trigger) {
    	
    	Random randomGenerator = new Random(); 
    	trigger.setValue(randomGenerator.nextDouble() * 100);
    	
    	trigger.update(defaultContext, TEST_FEED_KEY, TEST_FEED_ID, new Trigger.BasicListener() {
			
			@Override
			public void onSuccess() {
				Log.d(LOG_TAG, "Updated trigger successfully! ".concat(trigger.toString()));
				testTrigger(trigger);
			}
			
			@Override
			public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Failed to update trigger: ".concat(errorMessage));								
			}
		});
    	
    }
    
    private void loadTrigger(String triggerId) {
    	
    	Trigger.getTrigger(defaultContext, TEST_FEED_KEY, TEST_FEED_ID, triggerId, new Trigger.TriggerListener() {
			
			@Override
			public void onSuccess(Trigger trigger) {
				Log.d(LOG_TAG, "Found trigger: ".concat(trigger.toString()));
				updateTrigger(trigger);
			}
			
			@Override
			public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Failed to get trigger: ".concat(errorMessage));				
			}
			
		});
    	
    }

    private void createTrigger() {
    	
    	final Trigger trigger = new Trigger();
    	trigger.setStream("temperature");
    	trigger.setCondition(">");
    	trigger.setValue(10);
    	trigger.setCallbackUrl("http://www.example.com");
    	trigger.setStatus("enabled");
    	trigger.setName("Test trigger");
    	
    	trigger.create(defaultContext, TEST_FEED_KEY, TEST_FEED_ID, new Trigger.TriggerListener() {

			@Override
			public void onSuccess(Trigger trigger) {
				Log.d(LOG_TAG, String.format(Locale.US, "Trigger created successfully with id %s", trigger.getId()));
				loadTrigger(trigger.getId());
			}

			@Override
			public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Failed to create trigger " + trigger.getName());				
			}
    		
    	});
    	
    }
    
    private void loadTriggers() {
    	
    	Trigger.getTriggers(defaultContext, TEST_FEED_KEY, TEST_FEED_ID, new Trigger.TriggersListener() {

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
    	
    	stream.getValues(defaultContext, TEST_FEED_KEY, TEST_FEED_ID, null, new Stream.ValuesListener() {

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
    	
		feed.setValuesForMultipleStreams(defaultContext, TEST_FEED_KEY, data,  new Feed.BasicListener() {

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
    	
    	stream.setValues(defaultContext, TEST_FEED_KEY, TEST_FEED_ID, readings, new Stream.BasicListener() {

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

    	Stream.getStream(defaultContext, TEST_FEED_KEY, TEST_FEED_ID, TEST_STREAM_NAME, new Stream.StreamListener() {

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
    	
    	stream.delete(defaultContext, TEST_FEED_KEY, TEST_FEED_ID, new Stream.BasicListener() {
			
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
    	
    	stream.update(defaultContext, TEST_FEED_KEY, TEST_FEED_ID, new Stream.BasicListener() {

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
    	
    	Stream.getStreams(defaultContext, TEST_FEED_KEY, TEST_FEED_ID, new Stream.StreamsListener() {

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
    	loc.update(defaultContext, TEST_FEED_KEY, TEST_FEED_ID, new Location.UpdateListener() {

    		public void onSuccess() {
				Log.d(LOG_TAG, "Location update successful!");
    		}
    		
    		public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Location update failed :( ".concat(errorMessage));    			
    		}

    	});
    }
    
    private void loadLocation() { 
    	
    	Location.getLocation(defaultContext, TEST_FEED_KEY, TEST_FEED_ID, new Location.LocationListener()  {
			
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
    	
    	Feed.getFeed(defaultContext, TEST_FEED_KEY, TEST_FEED_ID, new Feed.FeedListener()  {
			
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
    
    private void loadDatasources() {
    	
    	HashMap<String, String> params = new HashMap<String, String>();
    	params.put("type", "blueprint");
    	
    	Feed.getFeeds(defaultContext,  params, new Feed.FeedsListener() { 

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

    private void loadFeeds() {
    	
    	Feed.getFeeds(defaultContext,  null, new Feed.FeedsListener() { 

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

    private void loadRequestLogEntries() {
    	
    	RequestLogEntry.getEntries(defaultContext, TEST_FEED_KEY, TEST_FEED_ID, new RequestLogEntry.Listener() {
			
			@Override
			public void onSuccess(ArrayList<RequestLogEntry> entries) {
        		Log.d(LOG_TAG, String.format("Obtained %d request log entries", entries.size()));
        		for (RequestLogEntry entry : entries) {
        			Log.d(LOG_TAG, entry.toString());
        		}    							
			}
			
			@Override
			public void onError(String errorMessage) {
				Log.d(LOG_TAG, "Failed to obtain request log entries: ".concat(errorMessage));
			}
		});
    }
    
}

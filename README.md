Android M2X API Client
=====================

The AT&T M2X API provides all the needed operations and methods to connect your devices to AT&T's M2X service. This client provides an easy to use interface for your favorite mobile platform, Android.


Getting Started
------------
1. Signup for an M2X Account: https://m2x.att.com/signup
2. Obtain your *Master Key* from the Master Keys tab of your Account Settings: https://m2x.att.com/account
3. Create your first Data Source Blueprint and copy its *Feed ID*: https://m2x.att.com/blueprints
4. Review the M2X API Documentation: https://m2x.att.com/developer/documentation/overview

If you have questions about any M2X specific terms, please consult the M2X glossary: https://m2x.att.com/developer/documentation/glossary

Before using this SDK
------

Please have in mind that the Android M2X API Client is currently in **alpha state**. Its usage in a production environment is highly discouraged. Classes and method signatures are all subject to change.

Using the Android SDK
------------

To start using this SDK you will probably need to set up your Master Key. The Master Key is required for some API features such as the ability to retrieve the full list of your feeds. Some other features, however, can be accessed by using a Feed Key. Feed Keys allow you to access specific feeds and control access permissions (some Feed keys could grant full access to a feed, while other could be restricted to GET operations only).

```
	import com.att.m2x.*;
	
	M2X.getInstance().setMasterKey("Master Key goes here");
```

API Client Examples:
-----

## Notes: 
1. All these examples use the Master Key, so make sure that you have set it.
2. All examples assume that you are performing API calls directly from an activity.
3. Many API calls include an optional feedKey argument. If this value is null the API client will perform the request using the Master Key. If the value is not null, then it will be used instead of the Master Key.

## Feeds
### List feeds

The following code obtains all the feeds available including blueprints, batches and datasources.

Note: this feature requires the Master Key

```
Feed.getFeeds(this, null, new Feed.FeedsListener() { 
	public void onSuccess(ArrayList<Feed> feeds) {
   }
   
	public void onError(String errorMessage) {
	}
});
```

### Find feeds

Finding feeds is just like listing them all, with the exception of the *params* argument that includes optional selection parameters as defined in the [AT&T M2X API Documentation](https://m2x.att.com/developer/documentation/feed#List-Search-Feeds)

For example, we can search for all blueprints (ignoring batches and datasources) with the following code

Note: this feature requires the Master Key

```
HashMap<String, String> params = new HashMap<String, String>();
params.put("type", "blueprint");

Feed.getFeeds(this, params, new Feed.FeedsListener() { 
	public void onSuccess(ArrayList<Feed> feeds) {
   }
   
	public void onError(String errorMessage) {
	}
});
```

### Get individual feed

The following code gets any feed that matches the provided ID.

```
Feed.getFeed(this, null, "Feed ID goes here", new Feed.FeedListener()  {
	public void onSuccess(Feed feed) {
   }
   
	public void onError(String errorMessage) {
	}
}
```

## Location

### Get feed location

The following code gets the current location of *myFeed*.

```
Location.getLocation(this, null, myFeed.getId(), new Location.LocationListener()  {
	public void onSuccess(Location location)  {
	}		
		
	public void onError(String errorMessage) {
	}
});
```

### Set feed location

The following code sets the location of *myFeed* to AT&T's corporate headquarters at Dallas.

```
Location place = new Location();
place.setName("AT&T Corporate HQ");
place.setLatitude(32.779846);
place.setLongitude(-96.799179);
place.setElevation(10);
place.update(this, null, myFeed.getId(), new Location.UpdateListener() {
	public void onSuccess() {
	}
	
	public void onError(String errorMessage) {
	}
});

```

## Data streams
### List data streams in a feed

The following code obtains all the data streams belonging to *myFeed*

```
Stream.getStreams(this, null, myFeed.getId(), new Stream.StreamsListener() {
	public void onSuccess(ArrayList<Stream> streams) {
	}

	public void onError(String errorMessage) {
	}
});
```

### Get individual data stream

The following code obtains the *temperature* data stream belonging to *myFeed*

```
Stream.getStream(this, null, myFeed.getId(), "temperature", new Stream.StreamListener() {
	public void onSuccess(Stream stream) {
	}

	public void onError(String errorMessage) {
	}
});
```

### Create/Update data stream

The same method is used for both creating and updating a stream. If the provided stream name does not exist, a new data stream is created.

```
Unit unit = new Unit();
unit.setLabel("Kelvin");
unit.setSymbol("K"); 

Stream temperature = new Stream();
temperature.setName("temperature");
temperature.setUnit(unit);
    	
temperature.update(this, null, myFeed.getId(), new Stream.BasicListener() {
	public void onSuccess() {
	}
	
	public void onError(String errorMessage) {
	}
});
```

In this case, if the *temperature* stream does not exist, a new one is created and assigned a temperature unit.

### Get data stream values

```
stream.getValues(this, null, "Feed ID goes here", null, new Stream.ValuesListener() {
	public void onSuccess(ArrayList<StreamValue> values) {
	}

	public void onError(String errorMessage) {
	}
});
```

Since a data stream can contain thousands or even more values, you can refine your request by adding an optional *params* argument. Valid parameters are specified in the [AT&T M2X API Documentation](https://m2x.att.com/developer/documentation/feed#List-Data-Stream-Values)

For instance, you can obtain up to 1000 temperature values with the following code:

```
HashMap<String, String> params = new HashMap<String, String>();
params.put("limit", "1000");

temperature.getValues(this, params, myFeed.getId(), null, new Stream.ValuesListener() {
	public void onSuccess(ArrayList<StreamValue> values) {
	}

	public void onError(String errorMessage) {
	}
});
```

### Add values to data stream

You can post one or more values to a stream using a single request. 

Let's assume you have an array of 5 random values (representing readings from a sensor)

```
Random randomGenerator = new Random(); 
ArrayList<StreamValue> readings = new ArrayList<StreamValue>();
for (int i = 0 ; i < 5 ; i++) {
	double reading = randomGenerator.nextDouble() * 100;
	StreamValue value = new StreamValue();
	value.setValue(reading);
	readings.add(value);
}
```

Now we are going to post these values to the *temperature* data stream:

```
temperature.setValues(this, null, myFeed.getId(), readings, new Stream.BasicListener() {
	public void onSuccess() {
	}

	public void onError(String errorMessage) {
	}
});
```


### Add values to multiple data streams

In some occassions a sensor may provide several data values simultaneosly (such as ambient pressure, temperature and wind speed). To improve performance it is possible to post multiple values to different data streams.

```

HashMap<Stream, ArrayList<StreamValue>> data = new HashMap<Stream, ArrayList<StreamValue>>();

// temperature, pressure and windspeed are streams belonging to myFeed

// temperatureValues, pressureValues and windSpeedValues are new values
// that we are adding simultaneously, each in the corresponding stream.

data.put(temperature, temperatureValues);
data.put(pressure, pressureValues);
data.put(windSpeed, windSpeedValues);

myFeed.setValuesForMultipleStreams(this, null, data,  new Feed.BasicListener() {
	public void onSuccess() {
	}

	public void onError(String errorMessage) {
	}			
});

```

### Delete stream

The following code deletes the *temperature* data stream belonging to myFeed.

```
temperature.delete(this, null, myFeed.getId(), new Stream.BasicListener() {
public void onSuccess() {
}
			
public void onError(String errorMessage) {
}
});
```

## Triggers
### List all triggers in a feed
### Get individual trigger
### Create trigger
### Update trigger
### Test trigger
### Delete trigger

## Request log
### Get request log entries
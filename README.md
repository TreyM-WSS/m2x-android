Android M2X API Client
=====================

The AT&T [M2X API](https://m2x.att.com/developer/documentation/overview) provides all the needed operations and methods to connect your devices to AT&T's M2X service. This client provides an easy to use interface for your favorite mobile platform, [Android](http://developer.android.com/index.html).


Getting Started
------------
1. Signup for an M2X Account: https://m2x.att.com/signup
2. Obtain your *Master Key* from the Master Keys tab of your Account Settings: https://m2x.att.com/account
3. Create your first Data Source Blueprint and copy its *Feed ID*: https://m2x.att.com/blueprints
4. Review the M2X API Documentation: https://m2x.att.com/developer/documentation/overview

If you have questions about any M2X specific terms, please consult the M2X glossary: https://m2x.att.com/developer/documentation/glossary

Before using this SDK
------

Please keep in mind that the Android M2X API Client is currently in **Alpha**. Using it in a production environment is highly discouraged. Classes and method signatures are all subject to change.

Using the Android SDK
------------

To start using this SDK you will need to set up your [Master Key](https://m2x.att.com/account#master-keys-tab). The Master Key is required for some API features such as the ability to retrieve the full list of your feeds. Some other features, however, can be accessed by using a Feed Key. Feed Keys allow you to access specific feeds and control access permissions (some Feed keys could grant full access to a feed, while others could be restricted to GET operations only).

```Java
import com.att.m2x.*;
	
M2X.getInstance().setMasterKey("Master Key goes here");
```

API Client Examples
-----

### Notes
1. All of these examples use the Master Key, so make sure that you have set it.
2. All examples assume that you are performing API calls directly from an activity.
3. Many API calls include an optional feedKey argument. If this value is null the API client will perform the request using the Master Key. If the value is not null, then it will be used instead of the Master Key.

## Feeds
### List feeds

The following code obtains all the feeds available including blueprints, batches and datasources.

Note: this feature requires the Master Key

```Java
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

```Java
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

```Java
Feed.getFeed(this, null, "Feed ID goes here", new Feed.FeedListener()  {
	public void onSuccess(Feed feed) {
   }
   
	public void onError(String errorMessage) {
	}
}
```

## Location

### Get feed location

This code gets the current location of *myFeed*.

```Java
Location.getLocation(this, null, myFeed.getId(), new Location.LocationListener()  {
	public void onSuccess(Location location)  {
	}		
		
	public void onError(String errorMessage) {
	}
});
```

### Set feed location

The following code sets the location of *myFeed* to AT&T's corporate headquarters at Dallas.

```Java
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

This is how you can obtain all the data streams belonging to *myFeed*

```Java
Stream.getStreams(this, null, myFeed.getId(), new Stream.StreamsListener() {
	public void onSuccess(ArrayList<Stream> streams) {
	}

	public void onError(String errorMessage) {
	}
});
```

### Get individual data stream

The following code obtains the *temperature* data stream belonging to *myFeed*

```Java
Stream.getStream(this, null, myFeed.getId(), "temperature", new Stream.StreamListener() {
	public void onSuccess(Stream stream) {
	}

	public void onError(String errorMessage) {
	}
});
```

### Create/Update data stream

The same method is used for both creating and updating a stream. If the provided stream name does not exist, a new data stream is created.

```Java
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

```Java
stream.getValues(this, null, "Feed ID goes here", null, new Stream.ValuesListener() {
	public void onSuccess(ArrayList<StreamValue> values) {
	}

	public void onError(String errorMessage) {
	}
});
```

Since a data stream can contain thousands of values (or even millions), you can refine your request by adding an optional *params* argument. Valid parameters are specified in the [AT&T M2X API Documentation](https://m2x.att.com/developer/documentation/feed#List-Data-Stream-Values)

For instance, you can obtain up to 1000 temperature values with the following code:

```Java
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

```Java
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

```Java
temperature.setValues(this, null, myFeed.getId(), readings, new Stream.BasicListener() {
	public void onSuccess() {
	}

	public void onError(String errorMessage) {
	}
});
```


### Add values to multiple data streams

In some occasions a sensor may provide several data values simultaneosly (such as ambient pressure, temperature and wind speed). To improve performance, it is possible to post multiple values to different data streams using a single HTTP request.

```Java
HashMap<Stream, ArrayList<StreamValue>> data = new HashMap<Stream, ArrayList<StreamValue>>();

// temperature, pressure and windspeed are streams that belong to myFeed

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

```Java
temperature.delete(this, null, myFeed.getId(), new Stream.BasicListener(){
	public void onSuccess() {
	}
			
	public void onError(String errorMessage) {
	}
});
```

## Triggers
### List all triggers in a feed


The following code obtains all the triggers of myFeed.

```Java
Trigger.getTriggers(this, null, myFeed.getId(), new Trigger.TriggersListener() {
	public void onSuccess(ArrayList<Trigger> triggers) {
	}

	public void onError(String errorMessage) {
	}
});
```

### Get individual trigger

The following code demonstrates how to obtain an individual trigger from its ID.

```Java
Trigger.getTrigger(this, null, myFeed.getId(), "Trigger ID goes here", new Trigger.TriggerListener() {
	public void onSuccess(Trigger trigger) {
	}

	public void onError(String errorMessage) {
	}
});
```

### Create trigger

The following code creates a new alarm for the *temperature* stream belonging to *myFeed*. If temperature rises above 100 degrees, a request will be made to the provided callback URL.

```Java
Trigger alarm = new Trigger();
alarm.setStream(temperature.getName());
alarm.setCondition(">");
alarm.setValue(100);
alarm.setCallbackUrl("http://my.server/?tempAlarm");
alarm.setStatus("enabled");
alarm.setName("Temperature Alarm");
    	
alarm.create(this, null, myFeed.getId(), new Trigger.TriggerListener() {
	public void onSuccess(Trigger trigger) {
	}

	public void onError(String errorMessage) {
	}
});
```

### Update trigger

This example updates the temperature alarm from the previous example, lowering the temperature to 80 degrees

```Java
alarm.setValue(80);
alarm.update(this, null, myFeed.getId(), new Trigger.BasicListener() 
{
	public void onSuccess() {
	}
	
	public void onError(String errorMessage) {
	}
});
```

### Test trigger

The following code shows how to test the *alarm* trigger, causing M2X to make a post to the provided callback URL.

```Java
alarm.test(this, null, myFeed.getId(), new Trigger.BasicListener() 
{
	public void onSuccess() {
	}
	
	public void onError(String errorMessage) {
	}
});
```

### Delete trigger

The following example deletes the *alarm* trigger for the *temperature* stream belonging to *myFeed*.

```Java
alarm.delete(this, null, myFeed.getId(), new Trigger.BasicListener() {
	public void onSuccess() {
	}
			
	public void onError(String errorMessage) {
	}
});
```

## Request log
### Get request log entries

The following code demonstrates how to obtain up to 100 http request log entries related to *myFeed*.

```Java
RequestLogEntry.getEntries(this, null, myFeed.getId(), new RequestLogEntry.Listener() {
	public void onSuccess(ArrayList<RequestLogEntry> entries) {
	}
			
	public void onError(String errorMessage) {
	}
});
```

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

Please keep in mind that the Android M2X API Client is currently in **Beta**. Please use caution when using it in a production environment. Classes and method signatures are all subject to change.

Using the Android SDK
------------

To start using this SDK you will need to follow the steps below:

1. Add network permissions in your manifest file

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

2. Get the .aar file from the project root and import it in android studio as a new module (File->New Module->From .AAR).
3. Open your project settings and create a dependency between your app and the android sdk module.
4. Your project should now compile. To start using M2X, set up your [Master Key](https://m2x.att.com/account#master-keys-tab). The Master Key is required for some API features such as the ability to retrieve the full list of your feeds. 

```Java
import com.att.m2x.*;
	
M2X.getInstance().setMasterKey("Master Key goes here");
```

Some other features, however, can be accessed by using a Feed Key. Feed Keys allow you to access specific feeds and control access permissions (some Feed keys could grant full access to a feed, while others could be restricted to GET operations only).

API Client Examples
-----

### Notes
1. All of these examples use the Master Key, so make sure that you have set it.
2. All examples assume that you are performing API calls directly from an activity.
3. Many API calls include an optional feedKey argument. If this value is null the API client will perform the request using the Master Key. If the value is not null, then it will be used instead of the Master Key.

# Feed API
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

The following code sets the location of *myFeed* to AT&T's corporate headquarters in Dallas, Texas.

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
	Date date = new Date();
	StreamValue value = new StreamValue();
    value.setDate(date);
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

In some cases, a sensor may provide several data values simultaneosly (such as ambient pressure, temperature and wind speed). To improve performance, it is possible to post multiple values to different data streams using a single HTTP request.

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

### Delete stream values by range

The following code deletes the *temperature* data stream values belonging to myFeed, by range.

```Java
temperature.deleteValuesByRange(this, null, myFeed.getId(), from, end, new Stream.BasicListener() {
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

# Datasource API

### Note
All the methods of the Datasource API require a Master Key.

## Blueprints

### List all blueprints

In order to obtain an arraylist with all available blueprints simply use the following code:

```Java
Blueprint.getBlueprints(this, blueprintId, new Blueprint.BlueprintsListener() {
	public void onSuccess(ArrayList<Blueprint> blueprints) {
	}
			
	public void onError(String errorMessage) {
	}
});
```

### Find blueprint by ID

The following snippet shows how to obtain a blueprint from its ID value (in this case *blueprintId*)

```Java
Blueprint.getBlueprint(this, blueprintId, new Blueprint.BlueprintListener() {
	public void onSuccess(Blueprint blueprint) {
	}
			
	public void onError(String errorMessage) {
	}
});
```

### Create blueprint

The following code will create a new blueprint and set its name, description, visibility and tags.

```Java
Blueprint b = new Blueprint();
b.setName("Test Blueprint");
b.setDescription("This is just a test");
b.setVisibility("public");
b.setTags(new ArrayList<String>(Arrays.asList("tag1", "another tag", "yet another tag")));		
b.create(this, new Blueprint.BlueprintListener() {
	public void onSuccess(Blueprint blueprint) {
	}

	public void onError(String errorMessage) {
	}
});
```

### Update blueprint

This code snippet will update the *blueprint* instance and change its tags.

```Java
blueprint.setTags(new ArrayList<String>(Arrays.asList("a tag", "yet another tag")));
blueprint.update(this, new Blueprint.BasicListener() {
	public void onSuccess() {
	}
			
	public void onError(String errorMessage) {
	}
});
```

### Delete blueprint

Deleting a blueprint simply requires calling the delete instance method.

```Java
blueprint.delete(this, new Blueprint.BasicListener() {
	public void onSuccess() {
	}
			
	public void onError(String errorMessage) {
	}
});
```

## Batches

Accessing and modifying batches is done in a similar same way as blueprints, with the exception of a few methods

### List all batches

Obtaining a list of all batches is fairly trivial:

```Java
Batch.getBatches(this, new Batch.BatchesListener() {
	public void onSuccess(ArrayList<Batch> batches) {
	}

	public void onError(String errorMessage) {
	}
});
```

### Find batch by ID

In order to find a batch you need to provide a Batch ID, in this case provided using the *batchId* variable.

```Java
Batch.getBatch(this, batchId, new Batch.BatchListener() {
	public void onSuccess(Batch batch) {
	}

	public void onError(String errorMessage) {
	}
});
```

### Create batch

To create a batch, first create an instance, set up some of its properties and finally call the *create* method.

```Java
Batch b = new Batch();
b.setName("Test Batch");
b.setDescription("Batch for testing");
b.setVisibility("public");
b.setTags(new ArrayList<String>(Arrays.asList("tag1", "tag2", "tag3")));
b.create(this, new Batch.BatchListener() {
	public void onSuccess(Batch batch) {
	}

	public void onError(String errorMessage) {
	}
});
```

### Update batch

Updating a batch is similar to creating one, with the exception of the method being used. In this example we change a batch's visibility to private.

```Java
batch.setVisibility("private");
batch.update(this, new Batch.BasicListener() {
	public void onSuccess() {
	}

	public void onError(String errorMessage) {
	}
});
```

### Delete batch

To delete a batch simply call the *delete* method.

```Java
batch.delete(this, new Batch.BasicListener() {
	public void onSuccess() {
	}

	public void onError(String errorMessage) {
	}
});
```

### List data sources in a batch

You can obtain an arraylist of all datasources in a batch using the code below. In the example *batch* is a Batch instance previously obtained from its ID.

```Java
batch.getBatchDatasources(this, new Datasource.DatasourcesListener() {
	public void onSuccess(ArrayList<Datasource> datasources) {
	}

	public void onError(String errorMessage) {
	}
});
```

### Add a new data source to a batch

To add a new data source to a batch you must provide a mandatory *serial* attribute that will identify it among all other datasources. This can be a device serial number or simply a descriptive name. 

```Java
String serial = "temperature_sensor_1";
batch.addDatasource(this, serial, new Datasource.DatasourceListener() {
	public void onSuccess(Datasource datasource) {
		// datasource is the newly created instance.
	}

	public void onError(String errorMessage) {
	}
```

## Data Sources

### Get all Data Sources

In the following snippet we are obtaining all available data sources

```Java
Datasource.getDatasources(this, new Datasource.DatasourcesListener() {
		
	public void onSuccess(ArrayList<Datasource> datasources) {
	}

	public void onError(String errorMessage) {
	}
});
```

### Find Data Source from ID

In the following example we are obtaining a data source whose ID is equal to *datasourceId*

```Java
Datasource.getDatasource(this, datasourceId, new Datasource.DatasourceListener() {
		
	public void onSuccess(Datasource datasource) {
	}

	public void onError(String errorMessage) {
	}
});
```

### Create Data Source

In the following example we are creating a new public datasource.

```Java
Datasource d = new Datasource();
d.setName("Test Datasource");
d.setDescription("Datasource for testing");
d.setVisibility("public");
d.setTags(new ArrayList<String>(Arrays.asList("tag1","tag2")));
d.create(this, new Datasource.DatasourceListener() {
	public void onSuccess(Datasource datasource) {
	}
			
	public void onError(String errorMessage) {
	}
});
```

### Update Data Source

In the following example we are changing a data source name.

```Java
datasource.setName("New Name");
datasource.update(this, new Datasource.BasicListener() {
	public void onSuccess() {
	}

	public void onError(String errorMessage) {
	}
});
```

### Delete Data Source

Deleting a data source simply consists of calling the *delete* instance method.

```Java
datasource.update(this, new Datasource.BasicListener() {
	public void onSuccess() {
	}

	public void onError(String errorMessage) {
	}
});
```

# Keys API

### Note
All the methods of the Keys API require a Master Key.

### Get all Master keys

Getting a detailed list of all Master keys is easy, as shown in the following example.

```Java
Key.getKeys(this, new Key.KeysListener() {
	public void onSuccess(ArrayList<Key> keys) {
	}

	public void onError(String errorMessage) {
	}
});
```

### Get Feed keys

You can get a full list of keys associated with a Feed by using the following code. Notice that the only change with respect to the previous example is the presence of a *feedId* argument.

```Java
Key.getKeys(this, feedId, new Key.KeysListener() {
	public void onSuccess(ArrayList<Key> keys) {
	}

	public void onError(String errorMessage) {
	}
});
```

### Obtain key information

In case you've got a key and need to determine whether it is a Feed or Master key, or simply check if you have POST permissions, you can do so by using code similar to the following snippet

```Java
String keyValue = "Key Value Goes Here";
Key.getKey(this, keyValue, new Key.KeyListener() {
	public void onSuccess(Key key) {
		// using the Key object you can now verify permissions (if needed)
	}

	public void onError(String errorMessage) {
	}
});
```

### Create key

Creating a key is very easy. In case you are creating a Master Key you do not need to provide any Feed ID or Stream name. The following code will create a Master Key with GET and PUT permissions only, and will assign to it an expiration date of a year after today.

```Java
Calendar today_plus_year = Calendar.getInstance();  
today_plus_year.add( Calendar.YEAR, 1 ); 

Key masterKey = new Key();
masterKey.setName("My new Master Key");
masterKey.setPermissions(new ArrayList<String>(Arrays.asList("GET", "PUT")));
masterKey.setExpiresAt(today_plus_year.getTime());
masterKey.create(this, new Key.KeyListener() {
	public void onSuccess(Key key) {
	}

	public void onError(String errorMessage) {
	}
});
```

To make a Feed Key simply specify an ID, and if you want to restrict it to a particular Stream, then specify the Stream name too. The following code will create a Feed key with GET permissions over the *temperature* stream of the *boiler* feed.

```Java
Key feedKey = new Key();
feedKey.setName("Temperature read-only key");
feedKey.setFeedId(boiler.getId());
feedKey.setStreamName("temperature");
feedKey.setPermissions(new ArrayList<String>(Arrays.asList("GET")));
feedKey.create(this, new Key.KeyListener() {
	public void onSuccess(Key key) {
	}

	public void onError(String errorMessage) {
	}
});
```

### Update key

In the following example we restrict the permissions of a Master Key to the GET method only.

```Java
masterKey.setPermissions(new ArrayList<String>(Arrays.asList("GET")));
masterKey.update(this, new Key.BasicListener() {
	public void onSuccess() {
		// update was successful
	}

	public void onError(String errorMessage) {
	}	
});
```

### Delete key

The following snippet will delete a key.

```Java
Key key = new Key();
key.setKeyValue("Key to be Deleted Goes Here");
key.delete(this, new Key.BasicListener() {
	public void onSuccess() {
		// key was deleted
	}

	public void onError(String errorMessage) {
	}	
});
```

### Regenerate key

Sometimes you might want to regenerate a key, preserving all of its properties (such as name and permissions) but changing the Key value. You can perform that action using the following code:

```Java
Key oldKey = new Key();
oldKey.setKeyValue("Key to be Regenerated Goes Here");
oldKey.regenerate(this, new Key.KeyListener() {
	public void onSuccess(Key key) {
		// the provided key instance contains the new key value
	}

	public void onError(String errorMessage) {
	}	
```

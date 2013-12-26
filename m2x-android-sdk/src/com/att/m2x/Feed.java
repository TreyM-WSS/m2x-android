package com.att.m2x;

import org.json.*;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.att.m2x.helpers.JSONHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class Feed extends com.att.m2x.model.Feed {

	public interface FeedListener {
		public void onSuccess(Feed feed);
		public void onError(String errorMessage);
	}

	public interface FeedsListener {
		public void onSuccess(ArrayList<Feed> feeds);
		public void onError(String errorMessage);
	}

	public interface BasicListener {
		public void onSuccess();
		public void onError(String errorMessage);		
	}
	
	protected static final String ID = "id";
	protected static final String NAME = "name";
	protected static final String DESCRIPTION = "description";
	protected static final String VISIBILITY = "visibility";
	protected static final String STATUS = "status";
	protected static final String TYPE = "type";
	protected static final String URL = "url";
	protected static final String KEY = "key";
	protected static final String LOCATION = "location";
	protected static final String STREAMS = "streams";
	protected static final String TRIGGERS = "triggers";
	protected static final String TAGS = "tags";
	protected static final String CREATED = "created";
	protected static final String UPDATED = "updated";
	protected static final String PAGE_KEY = "feeds";

	private Location location;
	private ArrayList<Stream> streams;
	private ArrayList<Trigger> triggers;
	private ArrayList<String> tags;
	
	public Feed() {
		
	}
	
	public Feed(Parcel in) {
		super(in);
		streams = new ArrayList<Stream>();
        in.readList(streams, Stream.class.getClassLoader());

		triggers = new ArrayList<Trigger>(); 
        in.readList(triggers, Trigger.class.getClassLoader());

		tags = new ArrayList<String>(); 
        in.readStringList(tags);
	}
	
	public Feed(JSONObject obj) {
		
		this.setId(JSONHelper.stringValue(obj, ID, ""));
		this.setName(JSONHelper.stringValue(obj, NAME, ""));
		this.setDescription(JSONHelper.stringValue(obj, DESCRIPTION, ""));
		this.setVisibility(JSONHelper.stringValue(obj, VISIBILITY, ""));
		this.setStatus(JSONHelper.stringValue(obj, STATUS, ""));
		this.setType(JSONHelper.stringValue(obj, TYPE, ""));
		this.setUrl(JSONHelper.stringValue(obj, URL, ""));
		this.setKey(JSONHelper.stringValue(obj, KEY, ""));
	
		try {
			Location location = new Location(obj.getJSONObject(LOCATION));
			this.setLocation(location);
		} catch (JSONException e) {
		}			
	
		if (obj.has(STREAMS)) {
			try {
				JSONArray items = obj.getJSONArray(STREAMS);
				ArrayList<Stream> streams = new ArrayList<Stream>();
				for (int i = 0; i < items.length(); i++) {
					Stream stream = new Stream(items.getJSONObject(i));
					streams.add(stream);
				}
				this.setStreams(streams);
			} catch (JSONException e1) {
				this.setStreams(null);
			}
		}

		if (obj.has(TRIGGERS)) {
			try {
				JSONArray items = obj.getJSONArray(TRIGGERS);
				ArrayList<Trigger> triggers = new ArrayList<Trigger>();
				for (int i = 0; i < items.length(); i++) {
					Trigger trigger = new Trigger(items.getJSONObject(i));
					triggers.add(trigger);
				}
				this.setTriggers(triggers);
			} catch (JSONException e1) {
				this.setTriggers(null);
			}
		}

		if (obj.has(TAGS)) {
			try {
				JSONArray items = obj.getJSONArray(TAGS);
				ArrayList<String> tags = new ArrayList<String>();
				for (int i = 0; i < items.length(); i++) {
					tags.add(items.getJSONObject(i).toString());
				}
				this.setTags(tags);
			} catch (JSONException e1) {
				this.setTags(null);
			}			
		}
		
		this.setCreated(JSONHelper.dateValue(obj, CREATED, null));
		this.setUpdated(JSONHelper.dateValue(obj, UPDATED, null));
	}	
	
	public static void getFeeds(Context context, HashMap<String, String> params, final FeedsListener callback) {
		
		M2XHttpClient client = M2X.getInstance().getClient();
		String path = "/feeds";
		
		client.get(context, null, path, params, new M2XHttpClient.Handler() {

			@Override
			public void onSuccess(int statusCode, JSONObject object) {

				ArrayList<Feed> array = new ArrayList<Feed>();
				try {
					JSONArray feeds = object.getJSONArray(PAGE_KEY);
					for (int i = 0; i < feeds.length(); i++) {
						Feed feed = new Feed(feeds.getJSONObject(i));
						array.add(feed);
					}
				} catch (JSONException e) {
					Log.d("Failed to parse Feed JSON objects");
				}
				callback.onSuccess(array);
				
			}

			@Override
			public void onFailure(int statusCode, String body) {
				callback.onError(body);
			}
			
		});
	}	

	public static void getFeed(Context context, String feedKey, String feedId, final FeedListener callback) {
		
		M2XHttpClient client = M2X.getInstance().getClient();
		String path = "/feeds/".concat(feedId);
		
		client.get(context, feedKey, path, null, new M2XHttpClient.Handler() {
									
			@Override
			public void onSuccess(int statusCode, JSONObject object) {

				Feed feed = new Feed(object);
				callback.onSuccess(feed);				
			}

			@Override
			public void onFailure(int statusCode, String body) {
				callback.onError(body);
			}
			
		});
		
	}
	
	public void setValuesForMultipleStreams(Context context, String feedKey, HashMap<Stream,ArrayList<StreamValue>> values, final BasicListener callback) {
		
		M2XHttpClient client = M2X.getInstance().getClient();
		String path = "/feeds/" + this.getId();
		
		try {
			
			JSONObject content = new JSONObject();
			JSONObject items = new JSONObject();			
			
			for(Entry<Stream, ArrayList<StreamValue>> entry : values.entrySet()) {
				Stream stream = entry.getKey();
				ArrayList<StreamValue> data = entry.getValue();

				JSONArray array = new JSONArray();			
				for (StreamValue value : data) {
					array.put(value.toJSONObject());
				}

				items.put(stream.getName(), array);
			}
			content.put("values", items);
			
			client.post(context, 
					feedKey,
					path, 
					content,
					new M2XHttpClient.Handler() {

						@Override
						public void onSuccess(int statusCode, JSONObject object) {
							callback.onSuccess();
						}

						@Override
						public void onFailure(int statusCode, String message) {						
							callback.onError(message);
						}
				
			});
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public ArrayList<Stream> getStreams() {
		return streams;
	}

	public void setStreams(ArrayList<Stream> streams) {
		this.streams = streams;
	}

	public ArrayList<Trigger> getTriggers() {
		return triggers;
	}

	public void setTriggers(ArrayList<Trigger> triggers) {
		this.triggers = triggers;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();		
		JSONHelper.put(obj, NAME, this.getName());
		JSONHelper.put(obj, DESCRIPTION, this.getDescription());
		JSONHelper.put(obj, VISIBILITY, this.getVisibility());
		
		StringBuilder sb = new StringBuilder();
		ArrayList<String> tags = this.getTags();
		if (tags != null) {
			for (String tag : this.getTags())
			{
			    sb.append(tag);
			    sb.append(",");
			}
			String joinedString = sb.toString();
			joinedString = joinedString.substring(0, joinedString.length()-1);
			JSONHelper.put(obj, TAGS, joinedString);			
		}
		
		return obj;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeList(streams);
		dest.writeList(triggers);
		dest.writeStringList(tags);
	}
	
	public static final Parcelable.Creator<Feed> CREATOR = new Parcelable.Creator<Feed>() {
	    public Feed createFromParcel(Parcel in) {
	     return new Feed(in);
	    }

	    public Feed[] newArray(int size) {
	     return new Feed[size];
	    }
	};
	
}

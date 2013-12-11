package com.att.m2x;

import java.util.ArrayList;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.att.helpers.JSONHelper;

public class Feed {

	private String id;
	private String name;
	private String description;
	private String visibility;
	private String status;
	private String type;
	private String url;
	private String key;
	private Location location;
	private ArrayList<Stream> streams;
	private ArrayList<Trigger> triggers;
	private ArrayList<String> tags;
	private Date created;
	private Date updated;

	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String DESCRIPTION = "description";
	private static final String VISIBILITY = "visibility";
	private static final String STATUS = "status";
	private static final String TYPE = "type";
	private static final String URL = "url";
	private static final String KEY = "key";
	private static final String LOCATION = "location";
	private static final String STREAMS = "streams";
	private static final String TRIGGERS = "triggers";
	private static final String TAGS = "tags";
	private static final String CREATED = "created";
	private static final String UPDATED = "updated";
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	@Override
	public String toString() {
		return String.format("Feed %s: %s (%s)", type, name, id);
	}

	public static Feed feedFromJSONObject(JSONObject obj) {
		
		Feed f = new Feed();
		f.id = JSONHelper.stringValue(obj, ID, "");
		f.name = JSONHelper.stringValue(obj, NAME, "");
		f.description = JSONHelper.stringValue(obj, DESCRIPTION, "");
		f.visibility = JSONHelper.stringValue(obj, VISIBILITY, "");
		f.status = JSONHelper.stringValue(obj, STATUS, "");
		f.type = JSONHelper.stringValue(obj, TYPE, "");
		f.url = JSONHelper.stringValue(obj, URL, "");
		f.key = JSONHelper.stringValue(obj, KEY, "");
		
		try {
			f.location = Location.locationFromJSONObject(obj.getJSONObject(LOCATION));
		} catch (JSONException e) {
		}			
		
		if (obj.has(STREAMS)) {
			try {
				JSONArray items = obj.getJSONArray(STREAMS);
				f.streams = new ArrayList<Stream>();
				for (int i = 0; i < items.length(); i++) {
					Stream stream = Stream.streamFromJSONObject(items.getJSONObject(i));
					f.streams.add(stream);
				}
			} catch (JSONException e1) {
				f.streams = null;
			}
		}

		if (obj.has(TRIGGERS)) {
			try {
				JSONArray items = obj.getJSONArray(TRIGGERS);
				f.triggers = new ArrayList<Trigger>();
				for (int i = 0; i < items.length(); i++) {
					Trigger trigger = Trigger.triggerFromJSONObject(items.getJSONObject(i));
					f.triggers.add(trigger);
				}
			} catch (JSONException e1) {
				f.triggers = null;
			}			
		}

		if (obj.has(TAGS)) {
			try {
				JSONArray items = obj.getJSONArray(TAGS);
				f.tags = new ArrayList<String>();
				for (int i = 0; i < items.length(); i++) {
					f.tags.add(items.getJSONObject(i).toString());
				}
			} catch (JSONException e1) {
				f.tags = null;
			}			
		}
		
		f.created = JSONHelper.dateValue(obj, CREATED, null);
		f.updated = JSONHelper.dateValue(obj, UPDATED, null);
		return f;
	}

}

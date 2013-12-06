package com.att.m2x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	private Date created;
	private Date updated;
			
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

	public static Feed feedFromJSONObject(JSONObject obj) {
		Feed f = new Feed();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
		
		try {
			f.id = obj.getString("id");
		} catch (JSONException e) {
			f.id = null;
		}

		try {
			f.name = obj.getString("name");
		} catch (JSONException e) {
			f.name = null;
		}

		try {
			f.description = obj.getString("description");
		} catch (JSONException e) {
			f.description = null;
		}

		try {
			f.visibility = obj.getString("visibility");
		} catch (JSONException e) {
			f.visibility = null;
		}

		try {
			f.status = obj.getString("status");
		} catch (JSONException e) {
			f.status = null;
		}

		try {
			f.type = obj.getString("type");
		} catch (JSONException e) {
			f.type = null;
		}

		try {
			f.url = obj.getString("url");
		} catch (JSONException e) {
			f.url = null;
		}

		try {
			f.key = obj.getString("key");
		} catch (JSONException e) {
			f.key = null;
		}

		try {
			f.location = Location.locationFromJSONObject(obj.getJSONObject("location"));
		} catch (JSONException e) {
			f.location = null;
		}
		
		try {
			JSONArray items = obj.getJSONArray("streams");
			f.streams = new ArrayList<Stream>();
			for (int i = 0; i < items.length(); i++) {
				Stream stream = Stream.streamFromJSONObject(items.getJSONObject(i));
				f.streams.add(stream);
			}
		} catch (JSONException e1) {
			f.streams = null;
		}

		try {
			JSONArray items = obj.getJSONArray("triggers");
			f.triggers = new ArrayList<Trigger>();
			for (int i = 0; i < items.length(); i++) {
				Trigger trigger = Trigger.triggerFromJSONObject(items.getJSONObject(i));
				f.triggers.add(trigger);
			}
		} catch (JSONException e1) {
			f.triggers = null;
		}

		try {
			f.created = sdf.parse(obj.getString("created"));
		} catch (JSONException e) {
			f.created = null;
		} catch (ParseException e) {
			f.created = null;
		}

		try {
			f.updated = sdf.parse(obj.getString("updated"));
		} catch (JSONException e) {
			f.updated = null;
		} catch (ParseException e) {
			f.updated = null;
		}

		return f;
	}
	
}

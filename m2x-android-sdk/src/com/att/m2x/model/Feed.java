package com.att.m2x.model;

import java.util.Date;
import java.util.Locale;

import android.os.Parcel;
import android.os.Parcelable;

public class Feed implements Parcelable {

	private String id;
	private String name;
	private String description;
	private String visibility;
	private String status;
	private String type;
	private String url;
	private String key;
	private Date created;
	private Date updated;
	
	public Feed() {
		
	}
	
	public Feed(Parcel in) {
		id = in.readString();
		name = in.readString();
		description = in.readString();
		visibility = in.readString();
		status = in.readString();
		type = in.readString();
		url = in.readString();
		key = in.readString();
		created = new Date(in.readLong());
		updated = new Date(in.readLong());
	}
	
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

	public String toString() {
		return String.format(Locale.US, "M2X Feed - %s %s (type: %s)", 
				this.getId(), 
				this.getName(), 
				this.getType() ); 
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(name);
		dest.writeString(description);
		dest.writeString(visibility);
		dest.writeString(status);
		dest.writeString(type);
		dest.writeString(url);
		dest.writeString(key);
		dest.writeLong(created.getTime());
		dest.writeLong(updated.getTime());
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

package com.att.m2x.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.os.Parcel;
import android.os.Parcelable;

public class Key implements Parcelable {
	
	private String name;
	private String key;
	private String streamUrl;
	private String feedUrl;
	private Boolean isMaster;
	private Boolean isExpired;
	private Date expiresAt;
	private ArrayList<String> permissions;
	
	public Key() {
		
	}
	
	public Key(Parcel in) {
		name = in.readString();
		key = in.readString();
		streamUrl = in.readString();
		feedUrl = in.readString();
		isMaster = in.readByte() != 0;
		isExpired = in.readByte() != 0;
		expiresAt = new Date(in.readLong());
		permissions = new ArrayList<String>(); 
        in.readStringList(permissions);
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getStreamUrl() {
		return streamUrl;
	}
	
	public void setStreamUrl(String streamUrl) {
		this.streamUrl = streamUrl;
	}
	
	public String getFeedUrl() {
		return feedUrl;
	}
	
	public void setFeedUrl(String feedUrl) {
		this.feedUrl = feedUrl;
	}
	
	public Boolean getIsMaster() {
		return isMaster;
	}
	
	public void setIsMaster(Boolean isMaster) {
		this.isMaster = isMaster;
	}
	
	public Boolean getIsExpired() {
		return isExpired;
	}
	
	public void setIsExpired(Boolean isExpired) {
		this.isExpired = isExpired;
	}
	
	public Date getExpiresAt() {
		return expiresAt;
	}
	
	public void setExpiresAt(Date expiresAt) {
		this.expiresAt = expiresAt;
	}
	
	public ArrayList<String> getPermissions() {
		return permissions;
	}
	
	public void setPermissions(ArrayList<String> permissions) {
		this.permissions = permissions;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {		
		dest.writeString(name);		
		dest.writeString(key);
		dest.writeString(streamUrl);
		dest.writeString(feedUrl);
		dest.writeByte((byte) (isMaster ? 1 : 0));
		dest.writeByte((byte) (isExpired ? 1 : 0));
		dest.writeLong(expiresAt.getTime());
		dest.writeStringList(permissions);
	}
	
	public static final Parcelable.Creator<Key> CREATOR = new Parcelable.Creator<Key>() {
	    public Key createFromParcel(Parcel in) {
	     return new Key(in);
	    }

	    public Key[] newArray(int size) {
	     return new Key[size];
	    }
	};
	
	public String toString() {
		return String.format(Locale.US, "M2X Key - %s (isMaster: %s, isExpired: %s)", 
				this.getKey(), 
				(this.getIsMaster()) ? "Yes" : "No", 
				(this.getIsExpired()) ? "Yes" : "No" ); 
	}
	
}

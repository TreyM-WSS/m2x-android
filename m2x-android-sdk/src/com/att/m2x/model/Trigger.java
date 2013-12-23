package com.att.m2x.model;

import java.util.Date;
import java.util.Locale;

import com.att.m2x.helpers.DateHelper;

import android.os.Parcel;
import android.os.Parcelable;

public class Trigger implements Parcelable {

	private String id;
	private String name;
	private String stream;
	private String condition;
	private double value;
	private String callbackUrl;
	private String url;
	private String status;
	private Date created;
	private Date updated;
	
	public Trigger() {
		
	}
	
	public Trigger(Parcel in) {
		id = in.readString();
		name = in.readString();
		stream = in.readString();
		condition = in.readString();
		value = in.readDouble();
		callbackUrl = in.readString();
		url = in.readString();
		status = in.readString();
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
	
	public String getStream() {
		return stream;
	}
	
	public void setStream(String stream) {
		this.stream = stream;
	}
	
	public String getCondition() {
		return condition;
	}
	
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	public String getCallbackUrl() {
		return callbackUrl;
	}
	
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
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
		return String.format(Locale.US, "M2X Trigger - %s %s (triggers when %s value is %s %f)", 
				this.getId(), 
				this.getName(), 
				this.getStream(),
				this.getCondition(), 
				this.getValue() ); 
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {		
		dest.writeString(id);
		dest.writeString(name);
		dest.writeString(stream);
		dest.writeString(condition);
		dest.writeDouble(value);
		dest.writeString(callbackUrl);
		dest.writeString(url);
		dest.writeString(status);
		dest.writeLong(created.getTime());
		dest.writeLong(updated.getTime());
	}
	
	public static final Parcelable.Creator<Trigger> CREATOR = new Parcelable.Creator<Trigger>() {
	    public Trigger createFromParcel(Parcel in) {
	     return new Trigger(in);
	    }

	    public Trigger[] newArray(int size) {
	     return new Trigger[size];
	    }
	};

}

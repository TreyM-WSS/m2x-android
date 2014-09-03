package com.att.m2x.model;

import java.util.Date;

import com.att.m2x.helpers.DateHelper;

import android.os.Parcel;
import android.os.Parcelable;

public class Waypoint implements Parcelable {
	
	private Date timestamp;
	private double latitude;
	private double longitude;
	private double elevation;
		
	public Waypoint() {
		
	}
	
	public Waypoint(Parcel in) {
		timestamp = DateHelper.stringToDate(in.readString());
		latitude = in.readDouble();
		longitude = in.readDouble();
		elevation = in.readDouble();
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public double getElevation() {
		return elevation;
	}
	
	public void setElevation(double elevation) {
		this.elevation = elevation;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(DateHelper.dateToString(timestamp));
		dest.writeDouble(latitude);
		dest.writeDouble(longitude);
		dest.writeDouble(elevation);		
	}	

	public static final Parcelable.Creator<Waypoint> CREATOR = new Parcelable.Creator<Waypoint>() {
	    public Waypoint createFromParcel(Parcel in) {
	     return new Waypoint(in);
	    }

	    public Waypoint[] newArray(int size) {
	     return new Waypoint[size];
	    }
	};

}

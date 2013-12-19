package com.att.m2x.model;

import java.util.Date;
import java.util.Locale;

import com.att.m2x.helpers.DateHelper;

import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable {
	
	private String name;
	private double latitude;
	private double longitude;
	private double elevation;
	private Date timestamp;	
	
	public Location() {
		
	}
	
	public Location(Parcel in) {
		name = in.readString();
		latitude = in.readDouble();
		longitude = in.readDouble();
		elevation = in.readDouble();
		timestamp = DateHelper.stringToDate(in.readString());
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String toString() {
		return String.format(Locale.US, "M2X Location - %s (coordinate: %.5f, %.5f) ", 
				this.getName(), 
				this.getLatitude(), 
				this.getLongitude() ); 
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeDouble(latitude);
		dest.writeDouble(longitude);
		dest.writeDouble(elevation);
		dest.writeString(DateHelper.dateToString(timestamp));
	}

	public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
	    public Location createFromParcel(Parcel in) {
	     return new Location(in);
	    }

	    public Location[] newArray(int size) {
	     return new Location[size];
	    }
	};
	
}

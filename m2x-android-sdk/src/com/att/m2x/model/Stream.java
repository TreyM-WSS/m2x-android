package com.att.m2x.model;

import java.util.Date;
import java.util.Locale;

import com.att.m2x.helpers.DateHelper;

import android.os.Parcel;
import android.os.Parcelable;

public class Stream implements Parcelable {

	private String id;
	private String name;
	private double value;
	private Date latestValueAt;
	private double min;
	private double max;
	private Unit unit;
	private String url;
	private Date created;
	private Date updated;		
	
	public Stream() {
		
	}
	
	public Stream(Parcel in) {
		id = in.readString();
		name = in.readString();
		value = in.readDouble();
		latestValueAt = DateHelper.stringToDate(in.readString());
		min = in.readDouble();
		max = in.readDouble();
		unit = in.readParcelable(Unit.class.getClassLoader());
		url = in.readString();
		created = DateHelper.stringToDate(in.readString());
		updated = DateHelper.stringToDate(in.readString());
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

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Date getLatestValueAt() {
		return latestValueAt;
	}

	public void setLatestValueAt(Date latestValueAt) {
		this.latestValueAt = latestValueAt;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
		return String.format(Locale.US, "M2X Stream - %s (min: %f, max: %f, last value: %f)", 
				this.getName(), 
				this.getMin(), 
				this.getMax(),
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
		dest.writeDouble(value);
		dest.writeString(DateHelper.dateToString(latestValueAt));
		dest.writeDouble(min);
		dest.writeDouble(max);
		dest.writeParcelable(unit, flags);
		dest.writeString(url);
		dest.writeString(DateHelper.dateToString(created));
		dest.writeString(DateHelper.dateToString(updated));		
	}

	public static final Parcelable.Creator<Stream> CREATOR = new Parcelable.Creator<Stream>() {
	    public Stream createFromParcel(Parcel in) {
	     return new Stream(in);
	    }

	    public Stream[] newArray(int size) {
	     return new Stream[size];
	    }
	};

}

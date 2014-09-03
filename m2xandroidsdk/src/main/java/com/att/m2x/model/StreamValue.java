package com.att.m2x.model;

import java.util.Date;
import java.util.Locale;

import android.os.Parcel;
import android.os.Parcelable;

import com.att.m2x.helpers.DateHelper;

public class StreamValue implements Parcelable {

	private Date date;
	private double value;
	
	public StreamValue() {
		
	}
	
	public StreamValue(Parcel in) {
		date = DateHelper.stringToDate(in.readString());
		value = in.readDouble();
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(DateHelper.dateToString(date));
		dest.writeDouble(value);
	}
		
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	public String toString() {
		String dateString = DateHelper.dateToString(this.getDate());
		return String.format(Locale.US, "M2X Stream Value - %f (date: %s)", 
				this.getValue(),
				dateString ); 
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Parcelable.Creator<StreamValue> CREATOR = new Parcelable.Creator<StreamValue>() {
	    public StreamValue createFromParcel(Parcel in) {
	     return new StreamValue(in);
	    }

	    public StreamValue[] newArray(int size) {
	     return new StreamValue[size];
	    }
	};
	
}

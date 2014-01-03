package com.att.m2x.model;

import java.util.Date;
import java.util.Locale;

import android.os.Parcel;
import android.os.Parcelable;

import com.att.m2x.helpers.DateHelper;

public class RequestLogEntry implements Parcelable {

	private Date date;
	private int statusCode;
	private String method;
	private String path;
	
	protected RequestLogEntry() {
		
	}
	
	public RequestLogEntry(Parcel in) {
		date = DateHelper.stringToDate(in.readString());
		statusCode = in.readInt();
		method = in.readString();
		path = in.readString();
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(DateHelper.dateToString(date));
		dest.writeInt(statusCode);
		dest.writeString(method);
		dest.writeString(path);
	}
	
	public Date getDate() {
		return date;
	}
	
	protected void setDate(Date date) {
		this.date = date;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	
	protected void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public String getMethod() {
		return method;
	}
	
	protected void setMethod(String method) {
		this.method = method;
	}
	
	public String getPath() {
		return path;
	}
	
	protected void setPath(String path) {
		this.path = path;
	}

	public String toString() {
		return String.format(Locale.US, "M2X Request Log Entry - %s %s %s (status: %d)", 
				DateHelper.dateToString(this.getDate()), 
				this.getMethod(), 
				this.getPath(),
				this.getStatusCode() ); 
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Parcelable.Creator<RequestLogEntry> CREATOR = new Parcelable.Creator<RequestLogEntry>() {
	    public RequestLogEntry createFromParcel(Parcel in) {
	     return new RequestLogEntry(in);
	    }

	    public RequestLogEntry[] newArray(int size) {
	     return new RequestLogEntry[size];
	    }
	};
	
}

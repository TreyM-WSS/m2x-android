package com.att.m2x.model;

import java.util.Date;
import java.util.Locale;

import com.att.m2x.helpers.DateHelper;

public class RequestLogEntry {

	private Date date;
	private int statusCode;
	private String method;
	private String path;
	
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

}

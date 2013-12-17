package com.att.m2x.model;

import java.util.Date;
import java.util.Locale;
import com.att.m2x.helpers.DateHelper;

public class StreamValue {

	private Date date;
	private double value;
	
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
	
}

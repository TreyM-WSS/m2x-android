package com.att.m2x.model;

import java.util.Date;
import java.util.Locale;

public class Trigger {

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
	
}

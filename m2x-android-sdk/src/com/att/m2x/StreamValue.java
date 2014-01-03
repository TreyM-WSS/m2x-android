package com.att.m2x;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.att.m2x.helpers.JSONHelper;
import com.att.m2x.helpers.JSONSerializable;

public class StreamValue extends com.att.m2x.model.StreamValue implements JSONSerializable {

	private static final String DATE = "at";
	private static final String VALUE = "value";

	public StreamValue() {
		
	}
	
	public StreamValue(JSONObject obj) {
		this.setDate(JSONHelper.dateValue(obj, DATE, null));
		this.setValue(JSONHelper.doubleValue(obj, VALUE, 0));		
	}
	
	public StreamValue(Parcel in) {
		super(in);
	}
	
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		JSONHelper.put(obj, DATE, this.getDate());
		JSONHelper.put(obj, VALUE, this.getValue());
		return obj;
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

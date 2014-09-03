package com.att.m2x;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.att.m2x.helpers.JSONHelper;
import com.att.m2x.helpers.JSONSerializable;

public final class Unit extends com.att.m2x.model.Unit implements JSONSerializable {

	private static final String LABEL = "label";
	private static final String SYMBOL = "symbol";

	public Unit() {
		
	}
	
	public Unit(JSONObject obj) {
		this.setLabel(JSONHelper.stringValue(obj, LABEL, ""));
		this.setSymbol(JSONHelper.stringValue(obj, SYMBOL, ""));		
	}
	
	public Unit(Parcel in) {
		super(in);
	}
	
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		JSONHelper.put(obj, LABEL, this.getLabel());
		JSONHelper.put(obj, SYMBOL, this.getSymbol());
		return obj;
	}
	
	public static final Parcelable.Creator<Unit> CREATOR = new Parcelable.Creator<Unit>() {
	    public Unit createFromParcel(Parcel in) {
	     return new Unit(in);
	    }

	    public Unit[] newArray(int size) {
	     return new Unit[size];
	    }
	};

}

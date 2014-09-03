package com.att.m2x.model;

import java.util.Locale;
import android.os.Parcel;
import android.os.Parcelable;

public class Unit implements Parcelable {

	private String label;
	private String symbol;
	
	public Unit() {
	}

	public Unit(Parcel in) {
		label = in.readString();
		symbol = in.readString();
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(label);
		dest.writeString(symbol);
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String toString() {
		return String.format(Locale.US, "M2X Stream - %s (symbol: %s)", 
				this.getLabel(), 
				this.getSymbol() ); 
	}

	@Override
	public int describeContents() {
		return 0;
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

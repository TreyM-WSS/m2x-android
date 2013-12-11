package com.att.m2x;

import org.json.JSONObject;

import com.att.helpers.JSONHelper;

public class Unit {

	private String label;
	private String symbol;
	
	private static final String LABEL = "label";
	private static final String SYMBOL = "symbol";

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
	
	public static Unit unitFromJSONObject(JSONObject obj) {
		
		Unit u = new Unit();
		u.label = JSONHelper.stringValue(obj, LABEL, "");
		u.symbol = JSONHelper.stringValue(obj, SYMBOL, "");
		return u;
		
	}

}

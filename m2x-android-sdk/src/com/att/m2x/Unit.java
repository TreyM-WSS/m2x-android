package com.att.m2x;

import org.json.JSONException;
import org.json.JSONObject;

public class Unit {

	private String label;
	private String symbol;
	
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

		try {
			u.label = obj.getString("label");
		} catch (JSONException e) {
			u.label = null;
		}

		try {
			u.symbol = obj.getString("symbol");
		} catch (JSONException e) {
			u.symbol = null;
		}

		return u;
		
	}

}

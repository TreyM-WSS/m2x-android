package com.att.m2x;

import org.json.JSONObject;
import com.att.m2x.helpers.JSONHelper;

public final class Unit extends com.att.m2x.model.Unit {

	private static final String LABEL = "label";
	private static final String SYMBOL = "symbol";

	public Unit() {
		
	}
	
	public Unit(JSONObject obj) {
		this.setLabel(JSONHelper.stringValue(obj, LABEL, ""));
		this.setSymbol(JSONHelper.stringValue(obj, SYMBOL, ""));		
	}
	
}

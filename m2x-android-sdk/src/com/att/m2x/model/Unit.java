package com.att.m2x.model;

import java.util.Locale;

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

	public String toString() {
		return String.format(Locale.US, "M2X Stream - %s (symbol: %s)", 
				this.getLabel(), 
				this.getSymbol() ); 
	}

}

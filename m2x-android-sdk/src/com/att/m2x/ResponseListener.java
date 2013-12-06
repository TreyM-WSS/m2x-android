package com.att.m2x;

import java.util.ArrayList;

public interface ResponseListener {

	public void onSuccess(ArrayList<Object> items);
	public void onError(String errorMessage);
	
}

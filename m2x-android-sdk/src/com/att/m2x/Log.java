package com.att.m2x;

public class Log {

	private static String DEFAULT_TAG = "M2X-SDK";
	
	public static void d(String msg) {
		android.util.Log.d(DEFAULT_TAG, msg);
	}
	
}

package com.att.m2x.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelper {
	
	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	public static Date stringToDate(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
		Date value = null;
		if (dateString != null) {
			try {
				value = sdf.parse(dateString);
			} catch (ParseException e) {
			}
		}
		return value;
	}

	public static String dateToString(Date date) {
		String value = null;
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
			value = (date != null) ? sdf.format(date) : null;			
		}
		return value;
	}

}

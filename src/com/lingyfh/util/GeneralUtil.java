package com.lingyfh.util;

import java.util.TimeZone;

/**
 * Created by lingyfh on 15/6/16.
 */
public class GeneralUtil {

	public static String getTimeZone() {
		TimeZone tz = TimeZone.getDefault();
		return tz.getDisplayName(false, TimeZone.SHORT);
	}
}

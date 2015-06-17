package com.lingyfh.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

/**
 * Created by lingyfh on 15/5/21.
 */
public class URLUtil {

	private static final String tag = URLUtil.class.getSimpleName();

	/**
	 * 编码URL
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String createURL(String url, HashMap<String, String> params) {
		List<NameValuePair> urlParams = new LinkedList<NameValuePair>();
		for (ConcurrentHashMap.Entry<String, String> entry : params.entrySet()) {
			urlParams.add(new BasicNameValuePair(entry.getKey(), entry
					.getValue()));
		}
		if (urlParams != null) {
			String paramString = URLEncodedUtils.format(urlParams, "utf-8");
			LogUtil.i(tag, "after encode = " + paramString);
			if (!url.contains("?")) {
				url += "?" + paramString;
			} else {
				url += "&" + paramString;
			}
		}
		return url;
	}
}

package com.youdao.request;

import android.graphics.Bitmap;
import android.text.TextUtils;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.lingyfh.util.LogUtil;

/**
 * Created by lingyfh on 15/6/17.
 */
public class YDReport {

	public static final String tag = YDReport.class.getSimpleName();

	private static final int SHOW_AD_SIZE = 1;
	private static final String CLICK_AD_RESPONSE = "thanks";

	public interface OnFinishListener {
		public void onFinish(boolean success);
	}

	public static ImageRequest reportShow(String url,
			final OnFinishListener listener) {
		ImageRequest imgRequest = new ImageRequest(url,
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap bitmap) {
						LogUtil.i(tag, "bitmap = " + bitmap);
						if (listener == null) {
							return;
						}

						if (bitmap == null) {
							listener.onFinish(false);
							return;
						}

						if (bitmap.getWidth() != SHOW_AD_SIZE
								|| bitmap.getHeight() != SHOW_AD_SIZE) {
							listener.onFinish(false);
						} else {
							listener.onFinish(true);
						}

						LogUtil.i(tag, "width = " + bitmap.getWidth());
						LogUtil.i(tag, "height = " + bitmap.getHeight());
					}
				}, 0, 0, null, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						volleyError.printStackTrace();
					}
				}) {

			@Override
			protected Response<Bitmap> parseNetworkResponse(
					NetworkResponse response) {
				return super.parseNetworkResponse(response);
			}
		};
		return imgRequest;
	}

	public static StringRequest reportClick(String url,
			final OnFinishListener listener) {
		StringRequest stringRequest = new StringRequest(url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						LogUtil.i(tag, "s = " + s);

						if (listener == null) {
							return;
						}

						if (TextUtils.isEmpty(s)) {
							listener.onFinish(false);
							return;
						}

						if (s.equalsIgnoreCase(CLICK_AD_RESPONSE)) {
							listener.onFinish(true);
						} else {
							listener.onFinish(false);
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						volleyError.printStackTrace();
					}
				}) {

			@Override
			protected Response<String> parseNetworkResponse(
					NetworkResponse response) {
				return super.parseNetworkResponse(response);
			}
		};
		return stringRequest;
	}
}

package com.youdao.request;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lingyfh.util.LogUtil;
import com.youdao.bean.YDRespBean;

/**
 * Created by lingyfh on 15/5/26.
 */
public class YDRequest {

	private static final String tag = YDRequest.class.getSimpleName();

	public interface OnSuccessListener {
		public void onSuccess(List<YDRespBean> beans);
	}

	public static StringRequest requestAd(String url, final boolean isList,
			final OnSuccessListener listener) {
		StringRequest strReq = new StringRequest(Request.Method.GET, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						LogUtil.i(tag, "s = " + s);
						if (TextUtils.isEmpty(s)) {
							return;
						}

						List<YDRespBean> list = null;
						Gson gson = new Gson();
						Type type;
						if (isList) {
							type = new TypeToken<List<YDRespBean>>() {
							}.getType();
							list = gson.fromJson(s, type);
							if (listener != null) {
								listener.onSuccess(list);
							}
							LogUtil.i(tag, "list size = " + list.size());
							return;
						}

						type = new TypeToken<YDRespBean>() {
						}.getType();

						if (list == null) {
							list = new ArrayList<YDRespBean>();
						}

						YDRespBean bean = gson.fromJson(s, type);
						if (bean != null) {
							list.add(bean);
						}
						if (listener != null) {
							listener.onSuccess(list);
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
				LogUtil.i(tag, "xadtype = " + response.headers.get("X-Adtype"));
				LogUtil.i(
						tag,
						"x-clickthrough = "
								+ response.headers.get("X-Clickthrough"));
				LogUtil.i(tag, "X-Height = " + response.headers.get("X-Height"));
				LogUtil.i(tag, "X-Width = " + response.headers.get("X-Width"));
				LogUtil.i(
						tag,
						"X-Imptracker = "
								+ response.headers.get("X-Imptracker"));
				LogUtil.i(
						tag,
						"X-Launchpage = "
								+ response.headers.get("X-Launchpage"));
				LogUtil.i(
						tag,
						"X-Creativeid = "
								+ response.headers.get("X-Creativeid"));

				return super.parseNetworkResponse(response);
			}

		};

		return strReq;
	}

}

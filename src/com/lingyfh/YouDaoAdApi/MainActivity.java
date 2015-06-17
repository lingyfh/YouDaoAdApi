package com.lingyfh.YouDaoAdApi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lingyfh.util.LogUtil;
import com.youdao.bean.YDRespBean;
import com.youdao.request.YDReport;
import com.youdao.request.YDRequest;

import java.util.List;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */

	private final static String tag = "MainActivity";

	private YDRespBean mAdBean;
	private RequestQueue mQueue;
	private TextView mDescView;

	private String AD_TEST_URL = "http://gorgon.youdao.com/gorgon/request.s?id=8ccf454bdcf051daa19ef1bce1b10fd2&z=+0800&o=p&sc_a=2.0&ct=2&dct=-1&imei=860308023497320&ran=2";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mDescView = (TextView) findViewById(R.id.ad_desc);

		mQueue = Volley.newRequestQueue(getApplicationContext());
		mQueue.start();

		findViewById(R.id.ad_request_btn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						requestAd();
					}
				});

		findViewById(R.id.ad_show_btn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						reportShow();
					}
				});

		findViewById(R.id.ad_click_btn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						reportClick();
					}
				});
	}

	private void reportShow() {
		for (String url : mAdBean.getImptracker()) {
			ImageRequest request = YDReport.reportShow(url,
					new YDReport.OnFinishListener() {
						@Override
						public void onFinish(boolean success) {
							LogUtil.i(tag, "reportShow success = " + success);
							Toast.makeText(MainActivity.this,
									"report show " + success,
									Toast.LENGTH_SHORT).show();
						}
					});
			mQueue.add(request);
		}
	}

	private void reportClick() {
		StringRequest stringRequest = YDReport.reportClick(
				mAdBean.getClktracker(), new YDReport.OnFinishListener() {
					@Override
					public void onFinish(boolean success) {
						LogUtil.i(tag, "reportClick success = " + success);
						Toast.makeText(MainActivity.this,
								"report click " + success, Toast.LENGTH_SHORT)
								.show();
					}
				});
		mQueue.add(stringRequest);
	}

	private void requestAd() {
		mQueue = Volley.newRequestQueue(getApplicationContext());

		String url = AD_TEST_URL;
		LogUtil.i(tag, "url = " + url);
		StringRequest strReq = YDRequest.requestAd(url, true,
				new YDRequest.OnSuccessListener() {
					@Override
					public void onSuccess(List<YDRespBean> beans) {

						Toast.makeText(MainActivity.this, "request ad success",
								Toast.LENGTH_SHORT).show();

						YDRespBean bean = null;
						if (beans != null && beans.size() > 0) {
							bean = beans.get(0);
						}
						if (bean == null) {
							return;
						}
						mAdBean = bean;

						if (mDescView != null) {
							mDescView.setText(mAdBean.toString());
						}

						LogUtil.i(tag, "clk = " + bean.getClk());
						LogUtil.i(tag, "clktracker = " + bean.getClktracker());
						LogUtil.i(tag, "imptracker = " + bean.getImptracker());
						LogUtil.i(tag,
								"imptracker size = "
										+ bean.getImptracker().length);
						LogUtil.i(tag, "Iconimage = " + bean.getIconimage());
						LogUtil.i(tag, "mainimage = " + bean.getMainimage());
						LogUtil.i(tag, "text = " + bean.getText());
						LogUtil.i(tag, "title = " + bean.getTitle());
					}
				});
		mQueue.add(strReq);
		mQueue.start();
	}


}

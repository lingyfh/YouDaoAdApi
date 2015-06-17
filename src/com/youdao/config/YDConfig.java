package com.youdao.config;

import android.content.Context;
import android.text.TextUtils;
import com.lingyfh.util.DeviceUtil;
import com.lingyfh.util.GeneralUtil;

import java.util.HashMap;

/**
 * Created by lingyfh on 15/6/15.
 */
public class YDConfig {

	public static final String AD_REQUEST_URL = "http://gorgon.youdao.com/gorgon/request.s";

	public static final String YD_ID = "your ad id";

	public static final int ENCRYPT_NONE = 0; // 无加密,此时可省略此参数
	public static final int ENCRYPT_DES = 1; // 使用广告位对应的密钥 DES 加密后 BASE64 编码
	public static final int ENCRYPT_BASE64 = 2; // 字符串反转后 BASE64 编码

	public static final int NET_WORK_UNKNOWN = 0;
	public static final int NET_WORK_ETHERNET = 1;
	public static final int NET_WORK_WIFI = 2;
	public static final int NET_WORK_MOBILE = 3;

	public static HashMap<String, String> requestParams(Context context,
			String adID, String encryptString, int ydet, int ran) {
		HashMap<String, String> params = new HashMap<String, String>();

		// id required String 移动广告位 ID
		params.put("id", adID);

		// udid recommended 设备 ID,如 androidID 或 IDFA
		// params.put("udid", "");

		// imei recommended IMEI(International Mobile Equipment
		// Identity)是移动设备国际身份码的缩写
		params.put("imei", DeviceUtil.getUniqueID(context));

		// ll recommended 位置信息,GPS 或者网络位置,经纬 度逗号分隔
		// params.put("ll", "");

		// ct recommended 网络连接类型,( UNKNOWN, ETHERNET, WIFI, MOBILE;),值可能为
		// 0,1,2,3,分别以上几种广告连接状态
		params.put("ct", DeviceUtil.getNetworkTypeYD(context) + "");

		// dct recommended Integer 子网络连接类型。当 ct 字段为 MOBILE 时添加,为 3G 时值可为
		// 6,3,5,8,9,10,12,14,15 之一,4G 时为 13
		if (DeviceUtil.getNetworkTypeYD(context) == YDConfig.NET_WORK_MOBILE) {
			params.put("dct", DeviceUtil.getMobleNetworkType(context) + "");
		}

		// s recommended String 加密请求时使用此字段,其值为整个 加密后的字段
		if (!TextUtils.isEmpty(encryptString)) {
			params.put("s", encryptString);
		}

		// ydet recommended Integer 加密请求时使用此字段 ,表明具体 使用的加密方法
		if (ydet != ENCRYPT_NONE) {
			params.put("ydet", ydet + "");
		}
		// dn optional String 设备信息,如samsung,GT-S5830,GT-S5830
		params.put("dn", DeviceUtil.getDeviceModel());
		// z optional String 时区,如:+0800
		params.put("z", GeneralUtil.getTimeZone());
		// o optional String 竖屏横屏,可能值分别为:p,l,s,u; u:未
		// 知,p:portrait,l:landscape,s:square
		params.put("o", "p");
		// sc_a￼optional String 屏幕分辨率,值如:1.0
		params.put("sc_a", DeviceUtil.getDisplayDensity(context) + "");
		// mcc optional String 国家类型,如中国 460
		if (!TextUtils.isEmpty(DeviceUtil.getMCC(context))) {
			params.put("mcc", DeviceUtil.getMCC(context));
		}
		// mnc optional String 运营商,如移动 00 联通 01
		if (!TextUtils.isEmpty(DeviceUtil.getMNC(context))) {
			params.put("mnc", DeviceUtil.getMNC(context));
		}
		// iso optional String 国家代号,值如 cn
		if (!TextUtils.isEmpty(DeviceUtil.getSimCountryIso(context))) {
			params.put("iso", DeviceUtil.getSimCountryIso(context));
		}
		// cn￼optional String 运营商名,值可能为‘中国联通’
		if (!TextUtils.isEmpty(DeviceUtil.getSimOperatorName(context))) {
			params.put("cn", DeviceUtil.getSimOperatorName(context));
		}
		// lac optional String 小区码
		if (DeviceUtil.getLac(context) != -1) {
			params.put("lac", DeviceUtil.getLac(context) + "");
		}
		// cid optional String 基站码,更加准确定位位置
		if (DeviceUtil.getCid(context) != -1) {
			params.put("cid", DeviceUtil.getCid(context) + "");
		}
		// wifi optional String wifi 信息,用户将当前连接或者附近 的wifi的ssid和mac传送过来
		// 非当前连接无法获取 mac,格式上
		// 第一个 参数为当前 wifi 的 mac
		// 第二个 为当前 wifi 的 ssid
		// 第三个 以后就是其 他网络的 mac 和 ssid,参数以逗号分隔
		// 如:ac:f7:f3:a4:bc:5a,NetEase, ac:f7:f3:a4:bc:5a,liaofan,
		// ac:f7:f3:a4:bc:5a,outfoxer

		if (ran <= 0) {
			throw new IllegalArgumentException("ran must > 0");
		}
		return params;
	}
}

package com.lingyfh.util;

import java.math.BigInteger;
import java.security.MessageDigest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import com.youdao.config.YDConfig;

/**
 * Created by lingyfh on 15/5/21.
 */
public class DeviceUtil {

	private static final String tag = DeviceUtil.class.getSimpleName();

	public static int getNetworkTypeYD(Context context) {
		NetworkInfo networkInfo = null;
		try {
			networkInfo = ((ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE))
					.getActiveNetworkInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (networkInfo == null) {
			return YDConfig.NET_WORK_UNKNOWN;
		}
		switch (networkInfo.getType()) {
			case ConnectivityManager.TYPE_WIFI :
				return YDConfig.NET_WORK_WIFI;
			case ConnectivityManager.TYPE_ETHERNET :
				return YDConfig.NET_WORK_ETHERNET;
			case ConnectivityManager.TYPE_MOBILE :
				return YDConfig.NET_WORK_MOBILE;
			default :
				return YDConfig.NET_WORK_UNKNOWN;
		}
	}

	public static int getMobleNetworkType(Context context) {
		try {
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			return tm.getNetworkType();
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		}
		return TelephonyManager.NETWORK_TYPE_UNKNOWN;
	}

	public static String getDeviceModel() {
		try {
			String manufacturer = Build.MANUFACTURER;
			String model = Build.MODEL;
			String deviceMode;
			if (model.startsWith(manufacturer)) {
				deviceMode = metaDeviceModel(model);
			} else {
				deviceMode = metaDeviceModel(manufacturer) + " " + model;
			}
			return deviceMode;

		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		}
		return Build.BRAND;
	}

	private static String metaDeviceModel(String string) {
		if (TextUtils.isEmpty(string)) {
			return "";
		}

		char first = string.charAt(0);
		if (Character.isUpperCase(first)) {
			return string;
		} else {
			if (string.length() > 1) {
				return Character.toUpperCase(first) + string.substring(1);
			} else {
				return string;
			}
		}
	}

	/**
	 * 获取DisplayMetricsde的density
	 *
	 * @param context
	 * @return
	 */
	public static float getDisplayDensity(Context context) {
		try {
			if (context == null) {
				LogUtil.w(tag, "getDisplayDensity", "context is null");
				return 1;
			}
			return context.getResources().getDisplayMetrics().density;
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		}
		return 1;
	}

	/**
	 * 获取设备独立DevicesID,平板电脑通过获取MAC地址
	 *
	 * @param ctx
	 * @return
	 */
	public static String getUniqueID(Context ctx) {
		TelephonyManager tm = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);
		String device_id = "";
		try {
			device_id = tm.getDeviceId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (device_id == null) {
			try {
				WifiManager wifi = (WifiManager) ctx
						.getSystemService(Context.WIFI_SERVICE);
				WifiInfo info = wifi.getConnectionInfo();
				MessageDigest md = MessageDigest.getInstance("MD5");
				device_id = new BigInteger(1, md.digest(info.getMacAddress()
						.getBytes())).toString(16).toUpperCase();
			} catch (Exception e) {
				device_id = "";
			}
		}

		return device_id;
	}

	/**
	 * 返回的MCC +跨国公司的注册网络运营商
	 * 
	 * @param context
	 * @return
	 */
	private static String getNetworkOperator(Context context) {
		TelephonyManager tel = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tel.getNetworkOperator();
	}

	/**
	 * 获取mcc
	 * 
	 * @param context
	 * @return
	 */
	public static String getMCC(Context context) {
		try {
			String networkOperator = getNetworkOperator(context);
			if (networkOperator != null) {
				return networkOperator.substring(0, 3);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 获取mnc
	 * 
	 * @param context
	 * @return
	 */
	public static String getMNC(Context context) {
		try {
			String networkOperator = getNetworkOperator(context);
			if (networkOperator != null) {
				return networkOperator.substring(3);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 返回SIM卡运营商的国家代码
	 * 
	 * @param context
	 * @return
	 */
	public static String getSimCountryIso(Context context) {
		try {
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			return tm.getSimCountryIso();
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 返回SIM卡运营商的名字 READ_PHONE_STATE
	 * 
	 * @param context
	 * @return
	 */
	public static String getSimOperatorName(Context context) {
		try {
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			return tm.getSimOperatorName();
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		}
		return "";
	}

	public static int getLac(Context context) {
		try {
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			if (tm.getCellLocation() instanceof GsmCellLocation) {
				return ((GsmCellLocation) tm.getCellLocation()).getLac();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static int getCid(Context context) {
		try {
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			if (tm.getCellLocation() instanceof GsmCellLocation) {
				return ((GsmCellLocation) tm.getCellLocation()).getCid();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		}
		return -1;
	}
}

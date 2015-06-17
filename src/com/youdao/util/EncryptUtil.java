package com.youdao.util;

import android.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by lingyfh on 15/6/15.
 */
public class EncryptUtil {

	public static byte[] encryptDes(String str, SecretKey key)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, InvalidKeySpecException,
			UnsupportedEncodingException {
		SecureRandom sr = new SecureRandom();
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, key, sr);
		byte datas[] = str.getBytes("utf-8");
		byte[] encryptedData = cipher.doFinal(datas);
		return encryptedData;
	}

	public static String encryptBase64(byte[] bts) {
		String base64 = Base64.encodeToString(bts, Base64.DEFAULT);
		return new StringBuffer(base64).toString();
	}
}

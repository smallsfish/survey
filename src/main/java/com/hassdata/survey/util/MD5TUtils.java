package com.hassdata.survey.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class MD5TUtils {
	private static final String PASSSTR="subjlakfjwiofjlaksdkljflakjliuopqawjehflj";

	public static String encode(String str) {
		String pstr = null;
		try {
			MessageDigest md=MessageDigest.getInstance("md5");
			byte[] b=md.digest(str.getBytes());
			pstr=new BASE64Encoder().encode(b);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return pstr;
	}
	public static String threeMD5(String str) {
		return encode(encode(encode(str+PASSSTR)+PASSSTR)+PASSSTR);
	}
}

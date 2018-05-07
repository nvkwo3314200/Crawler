package com.peak.test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64.Encoder;

public class TestString {
	public static void main(String[] args) throws UnsupportedEncodingException {
		String str = "1Ed23WQR";
		String str1 = "Ã¤¾®";
		System.out.println(URLEncoder.encode(str1, "utf-8"));
		System.out.println(URLDecoder.decode(str, "utf-8"));

	}
}

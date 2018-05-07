package com.peak.util.security;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

public class AESCBCSecurity {
	private static Logger log = Logger.getLogger(AESCBCSecurity.class);
	private static String encoding = "UTF-8";

	public static String iv = "0000000000000000"; 

	public static byte[] decrypt(byte[] cipherByte, byte[] keyBytes, byte[] iv) {
		try {
			/**
			 * KeyGenerator keyGenerator=KeyGenerator.getInstance("AES");
			 * keyGenerator.init(128, new
			 * SecureRandom(keyBytes));//key长可设为128，192，256位，这里只能设为128 SecretKey
			 * key=keyGenerator.generateKey();
			 */
			SecretKeySpec skeySpec = new SecretKeySpec(formatKey(keyBytes), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(formatKey(iv)));
			byte[] result = cipher.doFinal(cipherByte);
			return result;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	public static byte[] decrypt(byte[] cipherByte, String keyBytes, String iv) {
		try {
			return decrypt(cipherByte, keyBytes.getBytes(encoding), iv.getBytes(encoding));
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	public static String decrypt(String ciphertext, String keyBytes, String iv) {
		byte[] cipherByte = Base64.decodeBase64(ciphertext);
		byte[] plainByte = decrypt(cipherByte, keyBytes, iv);
		String plainText = null;
		try {
			plainText = new String(plainByte, encoding);
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
		return plainText;
	}

	public static byte[] encrypt(byte[] content, byte[] keyBytes, byte[] iv) {
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(formatKey(keyBytes), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(formatKey(iv)));
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	public static byte[] encrypt(byte[] plainByte, String keyBytes, String iv) {
		try {
			return encrypt(plainByte, keyBytes.getBytes(encoding), iv.getBytes(encoding));
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}
	
	public static String encrypt(String plainText, String keyBytes, String iv) {
		String cipherText = null;
		try {
			byte[] plainByte = plainText.getBytes(encoding);
			byte[] cipherByte = encrypt(plainByte, keyBytes, iv);
			cipherText = Base64.encodeBase64String(cipherByte);
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
		return cipherText;
	}
	
	
	private static byte[] formatKey(byte[] key) {
		byte[] result = new byte[16];
		for(int j = (key.length - 1), i = (result.length - 1); i > -1; i--, j--) {
			if (j > -1) {
				result[i] = key[j];
			} else {
				result[i] = 48;
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		String key = "ABDASDKFLASDKJFasdfasdfasdf";
		String context = "中华<span>hello</span>' == +*&^%$#@!~`人懈共";
		long start = System.currentTimeMillis();
		String cipherText = encrypt(context.toString(), key , iv);
		System.out.println(cipherText);
		long end = System.currentTimeMillis();
		System.out.println("加密用时：" + (end - start)/1000 + "s");
		System.out.println(cipherText.length());
		start = System.currentTimeMillis();
		String plainText = decrypt(cipherText, key, iv);
		end = System.currentTimeMillis();
		System.out.println("解密用时：" + (end - start)/1000 + "s");
		System.out.println(plainText.length());
		System.out.println(plainText);
	}
}

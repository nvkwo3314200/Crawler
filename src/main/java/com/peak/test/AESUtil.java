package com.peak.test;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

/**
 * @version V1.0
 * @desc AES ���ܹ�����
 */
public class AESUtil {

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//Ĭ�ϵļ����㷨

    /**
     * AES ���ܲ���
     *
     * @param content ����������
     * @param password ��������
     * @return ����Base64ת���ļ�������
     */
    public static String encrypt(String content, String password) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// ����������

            byte[] byteContent = content.getBytes("utf-8");

            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password));// ��ʼ��Ϊ����ģʽ��������

            byte[] result = cipher.doFinal(byteContent);// ����

            return Base64.encodeBase64String(result);//ͨ��Base64ת�뷵��
        } catch (Exception ex) {
            Logger.getLogger(AESUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    public static byte[] encrypt(byte[] byteContent, String password) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// ����������

            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password));// ��ʼ��Ϊ����ģʽ��������

            byte[] result = cipher.doFinal(byteContent);// ����

            return result;//ͨ��Base64ת�뷵��
        } catch (Exception ex) {
            Logger.getLogger(AESUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * AES ���ܲ���
     *
     * @param content
     * @param password
     * @return
     */
    public static String decrypt(String content, String password) {

        try {
            //ʵ����
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            //ʹ����Կ��ʼ��������Ϊ����ģʽ
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password));

            //ִ�в���
            byte[] result = cipher.doFinal(Base64.decodeBase64(content));

            return new String(result, "utf-8");
        } catch (Exception ex) {
            Logger.getLogger(AESUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public static byte[] decrypt(byte[] content, String password) {

        try {
            //ʵ����
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            //ʹ����Կ��ʼ��������Ϊ����ģʽ
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password));

            //ִ�в���
            byte[] result = cipher.doFinal(content);

            return result;
        } catch (Exception ex) {
            Logger.getLogger(AESUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    /**
     * ���ɼ�����Կ
     *
     * @return
     */
    private static SecretKeySpec getSecretKey(final String password) {
        //��������ָ���㷨��Կ�������� KeyGenerator ����
        KeyGenerator kg = null;

        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);

            //AES Ҫ����Կ����Ϊ 128
            kg.init(128, new SecureRandom(password.getBytes()));

            //����һ����Կ
            SecretKey secretKey = kg.generateKey();

            return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// ת��ΪAESר����Կ
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AESUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static void main(String[] args) {
    	File f = new File("F:\\ts\\3g3rqw13601000.ts");
    	String key = "725d4e47b98cfdcb";
    	byte[] fileByte = null;
		try {
			fileByte = FileUtils.readFileToByteArray(f);
		} catch (IOException e) {
			e.printStackTrace();
		}

//        byte[] s1 = AESUtil.encrypt(s.getBytes(), key);
        byte[] result = AESUtil.decrypt(fileByte, key);
        try {
			FileUtils.writeByteArrayToFile(new File("F:\\ts\\3g3rqw13601000_new.ts"), result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        System.out.println("end");

    }

}
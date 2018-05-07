package com.peak.test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.io.FileUtils;

public class AESCrptography {  
  
    public static void main(String[] args) throws UnsupportedEncodingException {  
        // TODO Auto-generated method stub  
  
/*        String content="asdfasdfasdfa";  
        String key="725d4e47b98cfdcb";  
        String iv="725d4e47b98cfdcb";  
          
        System.out.println("加密前："+byteToHexString(content.getBytes()));  
        byte[ ] encrypted=AES_CBC_Encrypt(content.getBytes(), key.getBytes(), iv.getBytes());  
        System.out.println("加密后："+byteToHexString(encrypted));  
        byte[ ] decrypted=AES_CBC_Decrypt(encrypted, key.getBytes(), iv.getBytes());  
        System.out.println("解密后："+byteToHexString(decrypted));  */
    	
    	
    	File f = new File("F:\\ts\\3g3rqw13601455.ts");
    	String key = "725d4e47b98cfdcb";
    	String iv="0000000000000000"; 
    	byte[] fileByte = null;
		try {
			fileByte = FileUtils.readFileToByteArray(f);
		} catch (IOException e) {
			e.printStackTrace();
		}

//        byte[] s1 = AESUtil.encrypt(s.getBytes(), key);
        byte[] result = AES_CBC_Decrypt(fileByte, key.getBytes("UTF-8"), iv.getBytes("UTF-8"));
        try {
			FileUtils.writeByteArrayToFile(new File("F:\\ts\\3g3rqw13601455_new.ts"), result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        System.out.println("end");
    }  
      
    public static byte[] AES_CBC_Encrypt(byte[] content, byte[] keyBytes, byte[] iv){  
          
        try{  
            KeyGenerator keyGenerator=KeyGenerator.getInstance("AES");  
            keyGenerator.init(128, new SecureRandom(keyBytes));  
            SecretKey key=keyGenerator.generateKey();  
            Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");  
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));  
            byte[] result=cipher.doFinal(content);  
            return result;  
        }catch (Exception e) {  
            // TODO Auto-generated catch block  
            System.out.println("exception:"+e.toString());  
        }   
        return null;  
    }  
      
    public static byte[] AES_CBC_Decrypt(byte[] content, byte[] keyBytes, byte[] iv){  
          
        try{  
           /* KeyGenerator keyGenerator=KeyGenerator.getInstance("AES");  
            keyGenerator.init(128, new SecureRandom(keyBytes));//key长可设为128，192，256位，这里只能设为128  
            SecretKey key=keyGenerator.generateKey(); */ 
            SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");  
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(iv));  
            byte[] result=cipher.doFinal(content);  
            return result;  
        }catch (Exception e) {  
            // TODO Auto-generated catch block  
            System.out.println("exception:"+e.toString());  
        }   
        return null;  
    }  
          
    public static String byteToHexString(byte[] bytes) {  
        StringBuffer sb = new StringBuffer(bytes.length);  
        String sTemp;  
        for (int i = 0; i < bytes.length; i++) {  
            sTemp = Integer.toHexString(0xFF & bytes[i]);  
            if (sTemp.length() < 2)  
                sb.append(0);  
            sb.append(sTemp.toUpperCase());  
        }  
        return sb.toString();  
    }  
}  
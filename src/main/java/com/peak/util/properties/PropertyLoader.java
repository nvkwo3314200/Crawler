package com.peak.util.properties;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

import com.peak.bean.PropertyBean;

public class PropertyLoader {
	
	private static String defaultFilePath = null;
	
	static {
		defaultFilePath = System.getProperty("user.dir");
	}
	
	// 根据Key读取Value
	public static String getValueByKey(String filePath, String key) {
		Properties pps = new Properties();
		if(null == filePath || "".equals(filePath)) {
			filePath = defaultFilePath;
		}
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			pps.load(in);
			String value = pps.getProperty(key);
			//System.out.println(key + " = " + value);
			return value;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 读取Properties的全部信息
	public static void getPropertiesByFile(String filePath) throws IOException {
		Properties pps = new Properties();
		InputStream in = new BufferedInputStream(new FileInputStream(filePath));
		pps.load(in);
		Enumeration en = pps.propertyNames(); // 得到配置文件的名字
		PropertyBean bean = PropertyBean.newInstance();
		while (en.hasMoreElements()) {
			String strKey = (String) en.nextElement();
			String strValue = pps.getProperty(strKey);
			bean.getMap().put(strKey, strValue);
		}

	}
	
	public static void getAllProperty() throws IOException {
		File file = new File(defaultFilePath);
		if(file.exists()) {
			for(File item : file.listFiles()) {
				String fileName = item.getName();
				if(fileName != null && fileName.indexOf(".properties") > -1) {
					getPropertiesByFile(defaultFilePath + "/" +fileName);
				}
			}
		}
	}

	// 写入Properties信息
	public static void writeProperties(String filePath, String pKey, String pValue) throws IOException {
		Properties pps = new Properties();

		InputStream in = new FileInputStream(filePath);
		// 从输入流中读取属性列表（键和元素对）
		pps.load(in);
		// 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
		// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
		OutputStream out = new FileOutputStream(filePath);
		pps.setProperty(pKey, pValue);
		// 以适合使用 load 方法加载到 Properties 表中的格式，
		// 将此 Properties 表中的属性列表（键和元素对）写入输出流
		pps.store(out, "Update " + pKey + " name");
	}
	
	public static void main(String[] args) throws IOException {
		getAllProperty();
		PropertyBean bean = PropertyBean.newInstance();
		System.out.println(bean.getMap().get("others_access_class"));
	}
}
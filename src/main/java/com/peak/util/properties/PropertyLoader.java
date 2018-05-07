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
	
	// ����Key��ȡValue
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

	// ��ȡProperties��ȫ����Ϣ
	public static void getPropertiesByFile(String filePath) throws IOException {
		Properties pps = new Properties();
		InputStream in = new BufferedInputStream(new FileInputStream(filePath));
		pps.load(in);
		Enumeration en = pps.propertyNames(); // �õ������ļ�������
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

	// д��Properties��Ϣ
	public static void writeProperties(String filePath, String pKey, String pValue) throws IOException {
		Properties pps = new Properties();

		InputStream in = new FileInputStream(filePath);
		// ���������ж�ȡ�����б�����Ԫ�ضԣ�
		pps.load(in);
		// ���� Hashtable �ķ��� put��ʹ�� getProperty �����ṩ�����ԡ�
		// ǿ��Ҫ��Ϊ���Եļ���ֵʹ���ַ���������ֵ�� Hashtable ���� put �Ľ����
		OutputStream out = new FileOutputStream(filePath);
		pps.setProperty(pKey, pValue);
		// ���ʺ�ʹ�� load �������ص� Properties ���еĸ�ʽ��
		// ���� Properties ���е������б�����Ԫ�ضԣ�д�������
		pps.store(out, "Update " + pKey + " name");
	}
	
	public static void main(String[] args) throws IOException {
		getAllProperty();
		PropertyBean bean = PropertyBean.newInstance();
		System.out.println(bean.getMap().get("others_access_class"));
	}
}
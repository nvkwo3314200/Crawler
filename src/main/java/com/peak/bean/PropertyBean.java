package com.peak.bean;

import java.util.HashMap;
import java.util.Map;

public class PropertyBean {
	private static volatile PropertyBean propertyBean;
	private Map<String, String> map = new HashMap<>();
	
	private PropertyBean() { }
	
	public static PropertyBean newInstance() {
		if(propertyBean == null) {
			synchronized (PropertyBean.class) {
				if(propertyBean == null) {
					propertyBean = new PropertyBean();
				}
			}
		}
		return propertyBean;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	
	
}

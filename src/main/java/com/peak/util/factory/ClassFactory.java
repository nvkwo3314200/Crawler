package com.peak.util.factory;

import org.apache.log4j.Logger;

import com.peak.util.filter.IUrlFilter;

public class ClassFactory {
	private final Logger LOG = Logger.getLogger(this.getClass());
	private static volatile ClassFactory classFactory; 
	
	private ClassFactory() {}
	
	public static ClassFactory newInstance() {
		if(null == classFactory) {
			synchronized (ClassFactory.class) {
				if(null == classFactory) {
					classFactory = new ClassFactory();
				}
			}
		}
		return classFactory;
	}
	
	public Object getInstanceByClassName(String className) {
		try {
			Class<?> clazz = Class.forName(className);
			Object object = clazz.newInstance();
			return object;
		} catch (ClassNotFoundException e) {
			LOG.error(e.getMessage());
		} catch (InstantiationException e) {
			LOG.error(e.getMessage());
		} catch (IllegalAccessException e) {
			LOG.error(e.getMessage());
		}
		return null;
	}
}

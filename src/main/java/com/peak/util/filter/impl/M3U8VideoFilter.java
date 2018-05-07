package com.peak.util.filter.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.peak.bean.PropertyBean;
import com.peak.util.constant.MyPropertiesConstant;
import com.peak.util.filter.IUrlFilter;

public class M3U8VideoFilter implements IUrlFilter{
	private String ptn;
	private String subfix;
	private String ts;
	private Map<String, String> propertyMap;
	
	public M3U8VideoFilter() {
		propertyMap = PropertyBean.newInstance().getMap();
		ptn = propertyMap.get(MyPropertiesConstant.DOMAIN_ACCESS_EXPRESSION);
		subfix = propertyMap.get(MyPropertiesConstant.SUFFIX_ACCESS_EXPRESSION);
		ts = propertyMap.get(MyPropertiesConstant.TS_SUBFIX);
	}
	

	@Override
	public boolean access(String url) {
		if(StringUtils.isNotEmpty(ptn)) {
			if(url.indexOf(ptn) > -1) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * only for ts source file
	 */
	@Override
	public boolean accessSource(String url) {
		if(url.indexOf("."+ts) > -1) {
			return true;
		}
		return false;
	}
	
	/**
	 * only for m3u8 source file
	 */
	@Override
	public boolean accessUrlSource(String url) {
		if(url.indexOf("."+subfix) > -1) {
			return true;
		}
		return false;
	}
}

package com.peak.util.filter.impl;

import org.apache.commons.lang3.StringUtils;

import com.peak.bean.PropertyBean;
import com.peak.util.constant.MyPropertiesConstant;
import com.peak.util.filter.IUrlFilter;

public class DomainUrlFilter implements IUrlFilter{
	private String ptn;
	
	public DomainUrlFilter() {
		ptn = PropertyBean.newInstance().getMap().get(MyPropertiesConstant.DOMAIN_ACCESS_EXPRESSION);
	}

	@Override
	public boolean access(String url) {
		if(StringUtils.isEmpty(ptn)) return true;
		if(url.startsWith("http") && url.indexOf(ptn) > -1)  
            return true;  
        else  
            return false; 
	}
	
	@Override
	public boolean accessSource(String url) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean accessUrlSource(String url) {
		// TODO Auto-generated method stub
		return false;
	}
}

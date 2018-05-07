package com.peak.util.url.abs;

import com.peak.bean.PropertyBean;
import com.peak.util.constant.MyPropertiesConstant;
import com.peak.util.url.IUrlParse;

public abstract class AbstractUrlParse implements IUrlParse{
	private static String[] commonPrefixs = {"http:", "https:"};
	
	@Override
	public boolean checkFullUrl(String url) {
		for(String prefix : commonPrefixs) {
			if(url.indexOf(prefix) > -1) {
				return true;
			} 
		}
		return false;
		
	}

	@Override
	public String combineUrl(String url) {
		String rtnUrl = url;
		if(!checkFullUrl(url)) {
			String domain = PropertyBean.newInstance().getMap().get(MyPropertiesConstant.VIDEO_DOMAIN);
			rtnUrl = domain + rtnUrl;
		}
		return rtnUrl;
	}
	
}

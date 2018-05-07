package com.peak.util.filter.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.peak.bean.PropertyBean;
import com.peak.util.constant.MyPropertiesConstant;
import com.peak.util.factory.ClassFactory;
import com.peak.util.filter.IUrlFilter;

public class CommonsFilterImpl implements IUrlFilter{
	private List<IUrlFilter> filterList = new ArrayList<>();
	public CommonsFilterImpl() {
		PropertyBean bean = PropertyBean.newInstance();
		Set<String> keySet = bean.getMap().keySet();
		ClassFactory factory = ClassFactory.newInstance();
		if(keySet != null) {
			for(String key : keySet) {
				if(key != null && key.endsWith(MyPropertiesConstant.ACCESS_CLASS)) {
					IUrlFilter filter = (IUrlFilter) factory.getInstanceByClassName(bean.getMap().get(key));
					if(filter != null) filterList.add(filter);
				}
			}
		}
	}

	@Override
	public boolean access(String url) {
		if(StringUtils.isEmpty(url)) return false;
		if(filterList != null) {
			for(IUrlFilter filter : filterList) {
				if(!filter.access(url)) return false;
			}
		}
		return true;
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

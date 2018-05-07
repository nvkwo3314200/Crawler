package com.peak.util.proxy;

import java.util.List;

import com.peak.bean.ProxyInfo;

public class ProxyUtil {
	private static volatile ProxyUtil proxyUtil;
	private List<ProxyInfo> proxyInfoList;
	
	private ProxyUtil() {
		ProxyCralwerUnusedVPN proxyCrawler = new ProxyCralwerUnusedVPN();
		proxyInfoList = proxyCrawler.startCrawler(2);
	}
	
	public static ProxyUtil newInstance() {
		if(null == proxyUtil) {
			synchronized (ProxyUtil.class) {
				if(null == proxyUtil) {
					proxyUtil = new ProxyUtil();
				}
			}
		}
		return proxyUtil;
	}

	public List<ProxyInfo> getProxyInfoList() {
		return proxyInfoList;
	}

	public void setProxyInfoList(List<ProxyInfo> proxyInfoList) {
		this.proxyInfoList = proxyInfoList;
	}
	
}

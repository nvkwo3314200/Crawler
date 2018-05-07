package com.peak.util.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.peak.bean.PropertyBean;
import com.peak.service.IImgBaseInfoService;
import com.peak.util.SourceMatchUtil;
import com.peak.util.constant.MyConstant;
import com.peak.util.constant.MyPropertiesConstant;
import com.peak.util.download.impl.DownloadPictureImpl;
import com.peak.util.filter.IUrlFilter;
import com.peak.util.proxy.ProxyUtil;
import com.peak.util.timer.ITimer;

public class ConnectPage implements Runnable {
	private static Logger log = Logger.getLogger(ConnectPage.class);
	
	private ITimer iTimer;
	private int count = 1;
	private ExecutorService exec = null;
	private IUrlFilter filter;
	private Map<String , String> imgMap;
	private Map<String , String> aMap;
	private HtmlPage page; 
	private String savePath; 
	private IImgBaseInfoService imgBaseInfoService;
	private List<String> urlList;
	private String urlSavePath;
	
	public ConnectPage(ITimer iTimer, IUrlFilter filter, Map<String , String> imgMap, Map<String , String> aMap
			,HtmlPage page, IImgBaseInfoService imgBaseInfoService) {
		this.iTimer = iTimer;
		this.exec = Executors.newFixedThreadPool(100);
		this.filter = filter;
		this.imgMap = imgMap;
		this.aMap = aMap;
		this.page = page;
		this.imgBaseInfoService = imgBaseInfoService;
		
		PropertyBean ppBean = PropertyBean.newInstance();
		savePath = ppBean.getMap().get(MyPropertiesConstant.SAVE_PATH);
		urlSavePath = ppBean.getMap().get(MyPropertiesConstant.URL_SAVE_PATH);
	}
	
	public void downloadSource(HtmlPage page, String savePath, String title) {
		iTimer.sleep(count);
		ProxyUtil proxyUtil =  ProxyUtil.newInstance();
		if (null != proxyUtil.getProxyInfoList()) { 
			int i = count%2;
            System.setProperty("http.proxySet", "true");  
            System.setProperty("http.proxyHost", proxyUtil.getProxyInfoList().get(i).getIp());  
            System.setProperty("http.proxyPort", proxyUtil.getProxyInfoList().get(i).getPort());  
        }  
		if(page != null) {
			count ++;
			DomNodeList<DomElement> domNodeList = page.getElementsByTagName(MyConstant.TAG_IMG);
			if(domNodeList != null) {
				for(DomElement ele : domNodeList) {
					String src = ele.getAttribute(MyConstant.IMG_SRC);
					try {
						if(StringUtils.isNotEmpty(src) && !imgMap.containsKey(src)) {
							synchronized (ConnectPage.class) {
								saveUrlSource(src);
								imgMap.put(src, null);
							}
							exec.execute(new DownloadPictureImpl(src, savePath, title));
						}
					} catch (Exception e) {
						log.error(e);
					}
				}
			}
			
			DomNodeList<DomElement> aNodeList = page.getElementsByTagName(MyConstant.TAG_A);
			if(domNodeList != null) {
				for(DomElement ele : aNodeList) {
					String href = ele.getAttribute(MyConstant.A_HREF);
					String tempTitle = ele.asText();
					if(StringUtils.isNotBlank(tempTitle)) {
						tempTitle = tempTitle.trim();
						if(SourceMatchUtil.matcher(tempTitle, MyConstant.OLNY_CHN_REG_EXP)) {
							title = tempTitle;
						}
					}
					
					try {
						if(StringUtils.isNotEmpty(href) && !aMap.containsKey(href) && filter.access(href)) {
							synchronized (ConnectPage.class) {
								aMap.put(href, null);
							}
							HtmlPage tPage = ele.click();
							downloadSource(tPage, savePath, title);
						}
					} catch (Exception e) {
						log.error(e);
					}
				}
			}
		}
	}
	
	public void saveUrlSource(String url) {
		if(this.urlList == null) this.urlList = new ArrayList<>();
		this.urlList.add(url);
		if(this.urlList.size() >= 50) {
			imgBaseInfoService.writeInfoToFile(urlSavePath, urlList);
			this.urlList.clear();
		}
	}
	
	@Override
	public void run() {
		downloadSource(this.page, savePath, null);
	}
	
}

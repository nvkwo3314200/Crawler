package com.peak.util;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.peak.bean.PropertyBean;
import com.peak.service.IImgBaseInfoService;
import com.peak.service.impl.NormalImgBaseInfoService;
import com.peak.util.constant.MyConstant;
import com.peak.util.constant.MyPropertiesConstant;
import com.peak.util.filter.IUrlFilter;
import com.peak.util.filter.impl.PrefixFilter;
import com.peak.util.page.ConnectPage;
import com.peak.util.properties.PropertyLoader;
import com.peak.util.timer.ITimer;
import com.peak.util.timer.NormalTimer;

public class GetPictureByHtmlUnit {
	
	private static final Logger log = Logger.getLogger(GetPictureByHtmlUnit.class);
	public static long count = 1;
	
	private ITimer iTimer;
	
	private Map<String, String> imgMap = new HashMap<String, String>();
	private Map<String, String> aMap = new HashMap<String, String>();
	ExecutorService exec = null;
	IUrlFilter filter = null;
	IImgBaseInfoService imgBaseInfoService;
	
	
	public void getSourceByUrl(String url, String savePath) {
		exec = Executors.newFixedThreadPool(2);
		filter = new PrefixFilter();
		iTimer = new NormalTimer();
		imgBaseInfoService = new NormalImgBaseInfoService();
		PropertyBean ppBean = PropertyBean.newInstance();
		imgBaseInfoService.initImageMapByFile(ppBean.getMap().get(MyPropertiesConstant.URL_SAVE_PATH), imgMap);
		
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_52);
		webClient.getOptions().setUseInsecureSSL(true);//支持https
        webClient.getOptions().setJavaScriptEnabled(false); // 启用JS解释器，默认为true
        webClient.getOptions().setCssEnabled(false); // 禁用css支持
        webClient.getOptions().setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常
        webClient.getOptions().setTimeout(20*000); // 设置连接超时时间 ，这里是10S。如果为0，则无限期等待
		getPageByUrl(webClient, url, savePath);
	}
	
	public void getPageByUrl(WebClient webClient,String url, String savePath) {
		HtmlPage page = null;
		try {
			page = webClient.getPage(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DomNodeList<DomElement> aNodeList = page.getElementsByTagName(MyConstant.TAG_A);
		if(aNodeList != null) {
			for(DomElement ele : aNodeList) {
				String href = ele.getAttribute(MyConstant.A_HREF);
				String tempTitle = ele.asText();
				if(StringUtils.isNotBlank(tempTitle)) {
					tempTitle = tempTitle.trim();
					if(SourceMatchUtil.matcher(tempTitle, MyConstant.OLNY_CHN_REG_EXP)) {
//						title = tempTitle;
					}
				}
				
				try {
					if(StringUtils.isNotEmpty(href) && !aMap.containsKey(href) && filter.access(href)) {
						synchronized (aMap) {
							aMap.put(href, null);
						}
						HtmlPage tPage = ele.click();
						ConnectPage connectPage = new ConnectPage(iTimer, filter, imgMap, aMap, tPage, imgBaseInfoService);
						exec.execute(connectPage);
					}
				} catch (Exception e) {
					
				}
			}
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		PropertyLoader.getAllProperty();
		GetPictureByHtmlUnit gpbh = new GetPictureByHtmlUnit();
		PropertyBean ppBean = PropertyBean.newInstance();
		Set<String> keySet = ppBean.getMap().keySet();
		if(keySet != null) {
			for(String key : keySet) {
				String url = ppBean.getMap().get(key);
				if(key != null && StringUtils.isNotEmpty(url) && key.indexOf(MyPropertiesConstant.MAIN_PAGE) > -1) {
					gpbh.getSourceByUrl(ppBean.getMap().get(key), ppBean.getMap().get(MyPropertiesConstant.SAVE_PATH));
				}
			}
		}
	}
}

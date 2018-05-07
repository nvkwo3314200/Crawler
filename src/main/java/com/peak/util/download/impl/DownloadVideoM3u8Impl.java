package com.peak.util.download.impl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.peak.bean.PropertyBean;
import com.peak.util.UrlUtils;
import com.peak.util.constant.MyPropertiesConstant;
import com.peak.util.download.DownloadM3u8ByUrl;
import com.peak.util.download.abs.AbstractDownloadSource;
import com.peak.util.factory.ClassFactory;
import com.peak.util.filter.IUrlFilter;
import com.peak.util.url.IUrlParse;
import com.peak.util.url.ParseUrlUtil;
import com.peak.util.url.impl.CommonUrlParse;

public class DownloadVideoM3u8Impl extends AbstractDownloadSource{
	private IUrlFilter filter;
	private IUrlParse urlParse;
	private Map<String, String> propertyMap;
	private ExecutorService exec = null;
	
	public DownloadVideoM3u8Impl(String url, String path, String title) {
		super(url, path, title);
		propertyMap = PropertyBean.newInstance().getMap();
		filter = (IUrlFilter) ClassFactory.newInstance().getInstanceByClassName(propertyMap.get(MyPropertiesConstant.M3U8_VIDEO_ACCESS_CLASS));
		urlParse = new CommonUrlParse();
		this.exec   = Executors.newFixedThreadPool(100);
	}
	
	public void downloadSource(String urlStr, String path) {
		byte[] fileByte = downloadSource(urlStr);
		String[] strs = null;
		try {
			String file = new String(fileByte, "UTF-8");
			strs = file.split("\n");
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
		String key = null;
		List<String> urlList = new ArrayList<String>();
		
		for(String item : strs) {
			if(StringUtils.isNotEmpty(item)) {
				if(item.indexOf("#") > -1) {
					if(item.indexOf("#EXT-X-KEY") > -1) {
						key = parseKey(item);
					}
				} else {
					if(filter.accessUrlSource(item)) {
						String url = assemblyFullUrl(item);
						downloadSource(url, path);
					} else if(filter.accessSource(item)) {
						String url = assemblyFullUrl(item);
						urlList.add(url);
					}
				}
			}
		}
		
		for(String url : urlList) {
			try { 
				String fileName = getFileNameByUrl(url);
				exec.execute(new DownloadM3u8ByUrl(url, fileName, key));
			} catch (Exception e) {
				log.error(e);
			}
		}
		log.info("download " + title + " end !");
	}
	
	private String assemblyFullUrl(String item) {
		String url = null;
		if(urlParse.checkFullUrl(item)) {
			url = item;
		} else {
			url = propertyMap.get(MyPropertiesConstant.VIDEO_DOMAIN) + "/" + item;
			url = UrlUtils.formatUrl(url);
			StringBuffer sb = new StringBuffer(url);
			sb.insert(6, "/");
			url = sb.toString();
		}
		return url;
	}
	
	@Override
	public String getFileNameByUrl(String urlStr) throws Exception {
		return path +"/"+ title +"/"+ ParseUrlUtil.parseOnlyFileName(urlStr);
	}

	private String parseKey(String str) {
		String url = "";
		String key = null;
		String[] strs = str.split("URI=\"");
		if(strs.length > 1) url = strs[1];
		url = url.substring(0, url.indexOf("\""));
		url = assemblyFullUrl(url);
		byte[] keyByte = downloadSource(url);
		try {
			key = new String(keyByte, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
		return key;
	}
	
	
}

package com.peak.util;

import org.apache.commons.lang3.StringUtils;

public class UrlUtils {
	
	public static String formatUrl(String url) {
		if(StringUtils.isNotEmpty(url)) {
			for(int i = 0 ; i < 3; i++) {
				url = StringUtils.replace(url, "//", "/");
			}
		}
		return url;
	}
	
}

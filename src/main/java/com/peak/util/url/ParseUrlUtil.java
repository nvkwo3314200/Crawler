package com.peak.util.url;

import org.apache.commons.lang3.StringUtils;

public class ParseUrlUtil {
	
	public static String parse(String url) {
		if(url == null) return null;
		String[] strs = url.split("/");
		String filename = null;
		String suffix = null;
		String subName = "";
		int ran = (int)(Math.random() * 1000);
		if(strs.length > 2) {
			filename = strs[strs.length - 1];
			suffix = filename.substring(filename.indexOf("."), filename.length());
			filename = filename.substring(0, filename.indexOf("."));
		} else {
			return null;
		}
		for(int i = strs.length - 2; i > 0 ; i--) {
			if(StringUtils.isNotEmpty(strs[i])) {
				subName = strs[i];
				break;
			}
		}
		filename = subName + "_" + filename + "_" + ran + suffix;
		return filename;	
	}
	
	public static String parseOnlyFileName(String url) {
		if(url == null) return null;
		String[] strs = url.split("/");
		String filename = null;
		String suffix = null;
		if(strs.length > 2) {
			filename = strs[strs.length - 1];
//			suffix = filename.substring(filename.indexOf("."), filename.length());
//			filename = filename.substring(0, filename.indexOf("."));
		} else {
			return null;
		}
		return filename;	
	}
}

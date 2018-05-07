package com.peak.util.download;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class DownloadSource {
	static String source  = "https://v2.438vip.com/share/kjrdnAtnvD0Hdt8l";
	public static void downloadSource(String urlStr) {
		InputStream in = null;
		List<String> strs = null;
		try {
			in = new URL(urlStr).openStream();
			strs = IOUtils.readLines(in, "UTF-8");
			for(String item : strs) {
				if(true || item.indexOf(".m3u8") > -1) {
					System.out.println(item);
				}
			}
		} catch (Exception e) {
		} finally {
			IOUtils.closeQuietly(in);
		}
	}
	
	public static void main(String[] args) {
		downloadSource(source);
	}
}

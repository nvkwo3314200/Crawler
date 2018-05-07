package com.peak.test;

import java.io.IOException;

import com.peak.util.download.abs.AbstractDownloadSource;
import com.peak.util.download.impl.DownloadVideoM3u8Impl;
import com.peak.util.properties.PropertyLoader;

public class TestDownload {
//	http://player.youku.com/player.php/sid/XMzMyMDMxNDc5Ng==/partnerid/8d5ffe2a068f4077/v.swf
	public static void main(String[] args) throws IOException {
		PropertyLoader.getAllProperty();
//		1200kb/hls/index.m3u8
//		
		String url = "http://v.bwzybf.com:80/20170924/COX6jd5M/1200kb/hls/index.m3u8";
		String path = "d:\\test_fjtp";
		String title = "mangjing"; 
		AbstractDownloadSource dl = new DownloadVideoM3u8Impl(url,  path,  title);
		dl.run();
		
	}
}

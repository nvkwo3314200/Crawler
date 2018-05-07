package com.peak.util.download;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.peak.util.security.AESCBCSecurity;

public class DownloadM3u8ByUrl implements Runnable{
	private Logger log = Logger.getLogger(DownloadM3u8ByUrl.class);
	private String url;
	private String fileSavePath;
	private String key;
	
	public DownloadM3u8ByUrl(String url, String fileSavePath, String key) {
		this.url = url;
		this.fileSavePath = fileSavePath;
		this.key = key;
	}
	
	@Override
	public void run() {
		saveSource();
	}
	
	public void saveSource() {
		byte[] tsFileByte = downloadSource(url);
		if(StringUtils.isNotEmpty(key)) {
			tsFileByte = AESCBCSecurity.decrypt(tsFileByte, key, AESCBCSecurity.iv);
		}
		try {
			FileUtils.writeByteArrayToFile(new File(fileSavePath), tsFileByte);
			log.info("download success: " + fileSavePath);
		} catch (Exception e) {
			log.error("download fail: " + fileSavePath);
			log.error(e);
		}
	}
	
	public byte[] downloadSource(String urlStr) {
		InputStream in = null;
		byte[] fileByte = null;
		try {
			in = new URL(urlStr).openStream();
			fileByte = IOUtils.toByteArray(in);
		} catch (Exception e) {
			log.error(e);
		} finally {
			IOUtils.closeQuietly(in);
		}
		return fileByte;

	}
}

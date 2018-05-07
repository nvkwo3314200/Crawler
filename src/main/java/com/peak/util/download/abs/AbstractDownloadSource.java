package com.peak.util.download.abs;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.peak.util.download.IDownloadSource;
import com.peak.util.url.ParseUrlUtil;

public class AbstractDownloadSource implements IDownloadSource,Runnable{
	protected Logger log = Logger.getLogger(this.getClass());
	protected String url;
	protected String path;
	protected String title;

	public AbstractDownloadSource(String url, String path, String title) {
		super();
		this.url = url;
		this.path = path;
		this.title = title;
	}
	
	@Override
	public void run() {
		downloadSource(this.url, this.path);
	}

	public void downloadSource(String urlStr, String path) {
		InputStream in = null;
		try {
			byte[] fileByte = downloadSource(urlStr);
			path = getFileNameByUrl(urlStr);
			FileUtils.writeByteArrayToFile(new File(path), fileByte);
			log.info("下载文件成功：" + path);
		} catch (Exception e) {
			log.error(e);
			log.error("下载文件失败：" + path);
		} finally {
			IOUtils.closeQuietly(in);
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

	public String getFileNameByUrl(String urlStr) throws Exception {
		if (StringUtils.isNotEmpty(title)) {
			path = path + "/" + title;
		}
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		String fileName = ParseUrlUtil.parse(urlStr);

		if (StringUtils.isEmpty(fileName)) {
			throw new Exception("file name is null");
		}
		path = path + "/" + fileName;
		log.info("url:" + urlStr);
		return path;
	}
	
}

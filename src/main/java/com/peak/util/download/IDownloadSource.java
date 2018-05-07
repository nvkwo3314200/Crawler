package com.peak.util.download;

public interface IDownloadSource {
	
	public void downloadSource(String urlStr, String path);
	
	public byte[] downloadSource(String urlStr);
	
	public String getFileNameByUrl(String urlStr) throws Exception;
	
	
}

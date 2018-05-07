package com.peak.service;

import java.util.List;
import java.util.Map;

public interface IImgBaseInfoService {
	
	public void initImageMapByFile(String filePath, Map<String, String> imgMap);
	
	public void initImageMapByDB(Map<String, String> imgMap);
	
	public void writeInfoToFile(String filePath, List<String> urlList);
	
}

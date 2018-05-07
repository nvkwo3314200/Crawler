package com.peak.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.peak.service.IImgBaseInfoService;

public class NormalImgBaseInfoService implements IImgBaseInfoService {
	private static final Logger log = Logger.getLogger(NormalImgBaseInfoService.class);
	
	@Override
	public void initImageMapByFile(String filePath, Map<String, String> imgMap) {
		File file = new File(filePath);
		if (file.isFile() && file.exists()) {
			try {
				List<String> list = FileUtils.readLines(file, "UTF-8");
				if(list != null) {
					for(String key : list) {
						imgMap.put(key, null);
					}
				}
			} catch (IOException e) {
				log.error(e);
			}
		}
	}

	@Override
	public void initImageMapByDB(Map<String, String> imgMap) {

	}

	@Override
	public void writeInfoToFile(String filePath, List<String> urlList) {
		File file = new File(filePath);
		File parentFile = new File(file.getParent());
		if(!parentFile.exists()) {
			parentFile.mkdirs();
		}
		try {
			FileUtils.writeLines(file, "UTF-8", urlList, null, true);
		} catch (IOException e) {
			log.error(e);
		}
	}
	
}

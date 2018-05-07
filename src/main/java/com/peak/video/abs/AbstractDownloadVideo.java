package com.peak.video.abs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.peak.bean.PropertyBean;
import com.peak.util.constant.MyPropertiesConstant;
import com.peak.util.factory.ClassFactory;
import com.peak.util.filter.IUrlFilter;
import com.peak.util.url.IUrlParse;

public abstract class AbstractDownloadVideo{
	protected String fileSavePath = "";
	protected String urlSavePath = "";
	private ExecutorService exec;
	private IUrlFilter filter;
	private IUrlParse urlParse;
	
	public AbstractDownloadVideo() {
		PropertyBean bean = PropertyBean.newInstance();
		fileSavePath = bean.getMap().get(MyPropertiesConstant.VIDEO_SAVE_PATH);
		urlSavePath = bean.getMap().get(MyPropertiesConstant.VIDEO_URL_SAVE_PATH);
		this.exec = Executors.newFixedThreadPool(100);
		filter = (IUrlFilter) ClassFactory.newInstance().getInstanceByClassName(bean.getMap().get(MyPropertiesConstant.M3U8_VIDEO_ACCESS_CLASS));
		urlParse = (IUrlParse)ClassFactory.newInstance().getInstanceByClassName(bean.getMap().get(MyPropertiesConstant.COMMON_URL_PARSE));
	}

	/*@Override
	public void downloadVideoByUrl(String url) {
		exec.execute(new DownloadPictureImpl(url, fileSavePath, null));
	}

	@Override
	public void downloadVideoByFile(String filePath) {
		File file = new File(filePath);
		if(file.exists()) {
			downloadVideoByFile(file);
		}
	}

	@Override
	public void downloadVideoByFile(File file) {
		try {
			List<String> sourceList = FileUtils.readLines(file, "utf-8");
			for(String url : sourceList) {
				if(filter.access(url)) {
					String parseUrl = urlParse.combineUrl(url);
					downloadVideoByUrl(parseUrl);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void downloadVideoByOther(Object others) {
		
	}*/

}

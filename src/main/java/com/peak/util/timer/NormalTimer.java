package com.peak.util.timer;

import org.apache.log4j.Logger;

public class NormalTimer implements ITimer {
	Logger log = Logger.getLogger(this.getClass());
	private int second = 6;

	@Override
	public boolean sleep(long count) {
		if(count % 6 == 0) {
			try {
				Thread.sleep(second * 1000);
				log.info(Thread.currentThread().getName() + " sleep " + second + " second!");
			} catch (InterruptedException e) {
				
			}
		}
		return true;
	}

}

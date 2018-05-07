package com.peak.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SourceMatchUtil {
	
	public static boolean matcher(String source, String regx) {
		Pattern ptn = Pattern.compile(regx);
		Matcher mtc = ptn.matcher(source);
		if(mtc.find()) {return true;}
		return false;
	}
	
}

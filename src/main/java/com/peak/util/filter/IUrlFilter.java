package com.peak.util.filter;

public interface IUrlFilter {
	public boolean access(String url);
	
	public boolean accessSource(String url);
	
	public boolean accessUrlSource(String url);
}

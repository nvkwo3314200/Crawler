package com.peak.bean;

import java.util.Map;

public class ProxyResponse {
    private String success;
    private Map<String, Object> data;
    public String getSuccess() {
        return success;
    }
    public void setSuccess(String success) {
        this.success = success;
    }
    public Map<String, Object> getData() {
        return data;
    }
    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
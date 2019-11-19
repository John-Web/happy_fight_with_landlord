package com.software_pro.common.entity;

public class WebData {
    private String key;
    private Object value;

    public WebData(String key,Object value){
        this.key = key;
        this.value = value;
    }
    public Object getValue() { return value; }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public void setValue(Object value) {
        this.value = value;
    }
}

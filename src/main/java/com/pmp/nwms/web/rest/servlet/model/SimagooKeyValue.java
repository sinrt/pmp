package com.pmp.nwms.web.rest.servlet.model;

/**
 * Created by user1 on 12/24/2017.
 */
public class SimagooKeyValue {

    private String key;
    private String value;

    public SimagooKeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public SimagooKeyValue() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

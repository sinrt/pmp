package com.pmp.nwms.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ApplicationDataStorage {
    private Map<String, Object> dataStorage = new HashMap<>();

    public void add(String key, Object value) {
        dataStorage.put(key, value);
    }

    public Object get(String key) {
        return dataStorage.get(key);
    }

    public void remove(String key) {
        dataStorage.remove(key);
    }
}

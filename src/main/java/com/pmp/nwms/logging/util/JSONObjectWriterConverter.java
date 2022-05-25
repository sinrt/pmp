package com.pmp.nwms.logging.util;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.json.JSONObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.util.Map;

@WritingConverter
public enum JSONObjectWriterConverter implements Converter<JSONObject, DBObject> {
    INSTANCE;

    @Override
    public DBObject convert(JSONObject jsonObject) {
        DBObject dbObject = new BasicDBObject();
        Map<String, Object> map = jsonObject.toMap();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof JSONObject) {
                dbObject.put(entry.getKey(), convert((JSONObject) value));
            } else {
                dbObject.put(entry.getKey(), value);
            }
        }
        return dbObject;
    }
}

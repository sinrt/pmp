package com.pmp.nwms.logging.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.pmp.nwms.logging.IgnoreLoggingValue;
import org.bson.types.ObjectId;

public class IgnoreLoggingExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        IgnoreLoggingValue annotation = fieldAttributes.getAnnotation(IgnoreLoggingValue.class);
        return annotation != null;
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return ObjectId.class.equals(aClass);
    }
}

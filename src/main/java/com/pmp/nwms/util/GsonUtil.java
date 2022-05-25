package com.pmp.nwms.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pmp.nwms.logging.util.IgnoreLoggingExclusionStrategy;

public class GsonUtil {
    private static final Gson GSON = new GsonBuilder()
        .addSerializationExclusionStrategy(new IgnoreLoggingExclusionStrategy())
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
        .create();

    public static String toJson(Object object){
        return GSON.toJson(object);
    }
}

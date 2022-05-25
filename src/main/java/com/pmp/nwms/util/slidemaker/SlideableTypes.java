package com.pmp.nwms.util.slidemaker;

import java.util.Arrays;
import java.util.List;

public enum SlideableTypes {
    pdf("application/pdf"),
//    docx("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
//    pptx("application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    ;

    private List<String> contentTypes;

    SlideableTypes(String... contentTypes) {
        this.contentTypes = Arrays.asList(contentTypes);
    }

    public static SlideableTypes findByContentType(String contentType){
        for (SlideableTypes value : values()) {
            if(value.contentTypes.contains(contentType)){
                return value;
            }
        }
        return null;
    }


}

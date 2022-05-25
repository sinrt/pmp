package com.pmp.nwms.domain.enums;

import java.util.ArrayList;
import java.util.List;

public enum ClassroomStudentAuthority {
    MODERATOR, //مدیر جلسه
    PUBLISHER, //ارائه دهنده
    SUBSCRIBER, //عضو عادی

    ;
    public static List<String> getValidAuthorityNames(){
        ArrayList<String> result = new ArrayList<>();
        for (ClassroomStudentAuthority value : values()) {
            result.add(value.name());
        }
        return result;
    }
}

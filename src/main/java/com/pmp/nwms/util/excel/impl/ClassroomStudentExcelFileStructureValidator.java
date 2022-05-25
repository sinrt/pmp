package com.pmp.nwms.util.excel.impl;

import com.pmp.nwms.util.excel.AbstractExcelFileStructureValidator;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("classroomStudentExcelFileStructureValidator")
public class ClassroomStudentExcelFileStructureValidator extends AbstractExcelFileStructureValidator {

    @Override
    protected String getExpectedFileName() {
        return "rubru.xlsx";
    }

    @Override
    protected Map<Integer, List<String>> getSheetsExpectedColumnNames() {
        Map<Integer, List<String>> map = new HashMap<>();
        map.put(0, Arrays.asList("نام کاربری", "نام", "نام خانوادگی", "پسورد", "نقش", "نیاز به لاگین دارد", "حداکثر ورود همزمان با این کاربر"));
        return map;
    }

    @Override
    protected List<String> getExpectedSheetNames() {
        return Collections.singletonList("کاربران");
    }
}

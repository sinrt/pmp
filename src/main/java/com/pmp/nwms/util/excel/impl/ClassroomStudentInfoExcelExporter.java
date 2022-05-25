package com.pmp.nwms.util.excel.impl;

import com.pmp.nwms.util.MessageUtil;
import com.pmp.nwms.util.excel.AbstractExcelExporter;
import com.pmp.nwms.util.excel.ExportCellData;
import com.pmp.nwms.web.rest.vm.ClassroomStudentInfoVM;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClassroomStudentInfoExcelExporter extends AbstractExcelExporter<ClassroomStudentInfoVM> {
    @Override
    protected List<String> makeHeaderTitles(boolean addRowNumCol) {
        if (headers == null || headers.isEmpty()) {
            headers = new ArrayList<>();
            if (addRowNumCol) {
                headers.add(MessageUtil.getMessage(messageSource, "common.row.num", null, FA));
            }

            headers.add(MessageUtil.getMessage(messageSource, "classroom.student.info.login", null, FA));
            headers.add(MessageUtil.getMessage(messageSource, "classroom.student.info.firstName", null, FA));
            headers.add(MessageUtil.getMessage(messageSource, "classroom.student.info.lastName", null, FA));
            headers.add(MessageUtil.getMessage(messageSource, "classroom.student.info.authorityName", null, FA));
            headers.add(MessageUtil.getMessage(messageSource, "classroom.student.info.needsLogin", null, FA));
            headers.add(MessageUtil.getMessage(messageSource, "classroom.student.info.maxUseCount", null, FA));
            headers.add(MessageUtil.getMessage(messageSource, "classroom.student.info.sessionEnterString", null, FA));


        }
        return headers;
    }

    @Override
    protected List<ExportCellData> makeRowExportData(ClassroomStudentInfoVM vm, Object[] extraData) {
        String appWebUrl = (String)extraData[0];
        ArrayList<ExportCellData> result = new ArrayList<>();
        {
            ExportCellData cellData = new ExportCellData();
            cellData.setValue(vm.getLogin());
            result.add(cellData);
        }
        {
            ExportCellData cellData = new ExportCellData();
            cellData.setValue(vm.getFirstName());
            result.add(cellData);
        }
        {
            ExportCellData cellData = new ExportCellData();
            cellData.setValue(vm.getLastName());
            result.add(cellData);
        }
        {
            ExportCellData cellData = new ExportCellData();

            cellData.setValue(MessageUtil.getMessage(messageSource, "authority.name." + vm.getAuthorityName(), null, FA));
            result.add(cellData);
        }
        {
            ExportCellData cellData = new ExportCellData();
            if (vm.isNeedsLogin()) {
                cellData.setValue(1);
            } else {
                cellData.setValue(0);
            }
            result.add(cellData);
        }
        {
            ExportCellData cellData = new ExportCellData();
            cellData.setValue(vm.getMaxUseCount());
            result.add(cellData);
        }
        {
            ExportCellData cellData = new ExportCellData();
            cellData.setValue(appWebUrl + getSessionEnterString(vm));
            result.add(cellData);
        }
        return result;
    }

    String getSessionEnterString(ClassroomStudentInfoVM vm) {
        String result = "/";
        if (vm.isTeacherPan()) {
            result += vm.getSessionName();
        } else {
            result += vm.getSessionUuidName();
        }
        if (vm.getUseEnterToken() != null && vm.getUseEnterToken()) {
            result += nwmsConfig.getTokenSessionDelimiter() + vm.getToken();
        }
        return result;
    }

    @Override
    protected Object[] getSheetNameArgs() {
        return null;
    }

    @Override
    protected String getSheetNameKey() {
        return "classroom.student.info.sheet.name";
    }
}

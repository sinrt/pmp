package com.pmp.nwms.util.excel.impl;

import com.pmp.nwms.util.MessageUtil;
import com.pmp.nwms.util.date.DateUtil;
import com.pmp.nwms.util.excel.AbstractExcelExporter;
import com.pmp.nwms.util.excel.ExportCellData;
import com.pmp.nwms.web.rest.vm.RubruSessionParticipantsReportVM;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RubruSessionParticipantsReportExcelExporter extends AbstractExcelExporter<RubruSessionParticipantsReportVM> {
    @Override
    protected Object[] getSheetNameArgs() {
        return null;
    }

    @Override
    protected String getSheetNameKey() {
        return "rubru.session.participants.report.sheet.name";
    }

    @Override
    protected List<String> makeHeaderTitles(boolean addRowNumCol) {
        if (headers == null || headers.isEmpty()) {
            headers = new ArrayList<>();
            if (addRowNumCol) {
                headers.add(MessageUtil.getMessage(messageSource, "common.row.num", null, FA));
            }
            headers.add(MessageUtil.getMessage(messageSource, "rubru.session.participants.report.participantName", null, FA));
            headers.add(MessageUtil.getMessage(messageSource, "rubru.session.participants.report.duration", null, FA));
            headers.add(MessageUtil.getMessage(messageSource, "rubru.session.participants.report.joinDateTime", null, FA));
            headers.add(MessageUtil.getMessage(messageSource, "rubru.session.participants.report.leaveDateTime", null, FA));
            headers.add(MessageUtil.getMessage(messageSource, "rubru.session.participants.report.login", null, FA));
            headers.add(MessageUtil.getMessage(messageSource, "rubru.session.participants.report.participantKey", null, FA));
        }
        return headers;
    }

    @Override
    protected List<ExportCellData> makeRowExportData(RubruSessionParticipantsReportVM vm, Object[] extraData) {
        ArrayList<ExportCellData> result = new ArrayList<>();
        {
            ExportCellData cellData = new ExportCellData();
            cellData.setValue(vm.getParticipantName());
            result.add(cellData);
        }
        {
            ExportCellData cellData = new ExportCellData();
            cellData.setValue(vm.getDuration());
            result.add(cellData);
        }
        {
            ExportCellData cellData = new ExportCellData();
            if (vm.getJoinDateTime() != null) {
                cellData.setValue(DateUtil.formatDate(vm.getJoinDateTime(), nwmsConfig.getRubruSessionParticipantsReportExcelDataDatePattern(), true));
            } else {
                cellData.setValue("");
            }
            result.add(cellData);
        }
        {
            ExportCellData cellData = new ExportCellData();
            if (vm.getLeaveDateTime() != null) {
                cellData.setValue(DateUtil.formatDate(vm.getLeaveDateTime(), nwmsConfig.getRubruSessionParticipantsReportExcelDataDatePattern(), true));
            } else {
                cellData.setValue("");
            }
            result.add(cellData);
        }
        {
            ExportCellData cellData = new ExportCellData();
            cellData.setValue(vm.getLogin());
            result.add(cellData);
        }
        {
            ExportCellData cellData = new ExportCellData();
            cellData.setValue(vm.getParticipantKey());
            result.add(cellData);
        }
        return result;
    }
}

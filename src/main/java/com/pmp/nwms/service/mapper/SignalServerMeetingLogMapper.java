package com.pmp.nwms.service.mapper;

import com.pmp.nwms.domain.*;
import com.pmp.nwms.service.dto.SignalServerMeetingLogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SignalServerMeetingLog and its DTO SignalServerMeetingLogDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SignalServerMeetingLogMapper extends EntityMapper<SignalServerMeetingLogDTO, SignalServerMeetingLog> {



    default SignalServerMeetingLog fromId(Long id) {
        if (id == null) {
            return null;
        }
        SignalServerMeetingLog signalServerMeetingLog = new SignalServerMeetingLog();
        signalServerMeetingLog.setId(id);
        return signalServerMeetingLog;
    }
}

package com.pmp.nwms.service.mapper;

import com.pmp.nwms.domain.*;
import com.pmp.nwms.service.dto.SignalServerLogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SignalServerLog and its DTO SignalServerLogDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SignalServerLogMapper extends EntityMapper<SignalServerLogDTO, SignalServerLog> {



    default SignalServerLog fromId(Long id) {
        if (id == null) {
            return null;
        }
        SignalServerLog signalServerLog = new SignalServerLog();
        signalServerLog.setId(id);
        return signalServerLog;
    }
}

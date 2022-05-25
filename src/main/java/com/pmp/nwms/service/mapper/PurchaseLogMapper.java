package com.pmp.nwms.service.mapper;

import com.pmp.nwms.domain.*;
import com.pmp.nwms.service.dto.PurchaseLogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PurchaseLog and its DTO PurchaseLogDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PurchaseLogMapper extends EntityMapper<PurchaseLogDTO, PurchaseLog> {



    default PurchaseLog fromId(Long id) {
        if (id == null) {
            return null;
        }
        PurchaseLog purchaseLog = new PurchaseLog();
        purchaseLog.setId(id);
        return purchaseLog;
    }
}

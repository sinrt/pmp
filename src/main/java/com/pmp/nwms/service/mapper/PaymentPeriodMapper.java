package com.pmp.nwms.service.mapper;

import com.pmp.nwms.domain.*;
import com.pmp.nwms.service.dto.PaymentPeriodDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PaymentPeriod and its DTO PaymentPeriodDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaymentPeriodMapper extends EntityMapper<PaymentPeriodDTO, PaymentPeriod> {



    default PaymentPeriod fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentPeriod paymentPeriod = new PaymentPeriod();
        paymentPeriod.setId(id);
        return paymentPeriod;
    }
}

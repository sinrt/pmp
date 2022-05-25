package com.pmp.nwms.service.mapper;

import com.pmp.nwms.domain.*;
import com.pmp.nwms.service.dto.ServiceTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ServiceType and its DTO ServiceTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServiceTypeMapper extends EntityMapper<ServiceTypeDTO, ServiceType> {



    default ServiceType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceType serviceType = new ServiceType();
        serviceType.setId(id);
        return serviceType;
    }
}

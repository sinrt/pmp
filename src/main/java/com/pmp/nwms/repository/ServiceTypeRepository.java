package com.pmp.nwms.repository;

import com.pmp.nwms.domain.ServiceType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.query.Param;
/**
 * Spring Data  repository for the ServiceType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {

    @Query("select servicetype from ServiceType servicetype where servicetype.activated = true order by servicetype.priority asc ")
    List<ServiceType> findActivedServiceType();

    @Query("select servicetype from ServiceType servicetype where servicetype.code =:code")
    Optional<ServiceType> findServicetypeByCode(@Param("code") String code);
}

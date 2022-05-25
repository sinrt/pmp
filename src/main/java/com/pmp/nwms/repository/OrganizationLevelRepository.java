package com.pmp.nwms.repository;

import com.pmp.nwms.domain.OrganizationLevel;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OrganizationLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizationLevelRepository extends JpaRepository<OrganizationLevel, Long> {
    @Query("select organizationlevel from OrganizationLevel  organizationlevel where organizationlevel.name= :name")
    OrganizationLevel findOrganizationLevelByName(@Param("name") String name);
}

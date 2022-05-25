package com.pmp.nwms.repository;

import com.pmp.nwms.domain.SystemSetting;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SystemSetting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SystemSettingRepository extends JpaRepository<SystemSetting, Long> {

}

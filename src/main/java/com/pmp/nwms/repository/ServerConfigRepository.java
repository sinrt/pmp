package com.pmp.nwms.repository;

import com.pmp.nwms.domain.ServerConfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the ServerConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServerConfigRepository extends JpaRepository<ServerConfig, Long> {

    @Query("select serverConfig from ServerConfig serverConfig  where serverConfig.paramName=:paramName")
    Optional<ServerConfig> findByParamName(@Param("paramName") String paramName);

}

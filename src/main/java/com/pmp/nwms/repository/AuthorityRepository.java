package com.pmp.nwms.repository;

import com.pmp.nwms.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {

    @Query("select authority from Authority authority where authority.name =:authorityName ")
    Optional<Authority> findAuthorityByName(@Param("authorityName") String name);

    @Query("select authority from Authority authority where name=:name ")
    Optional<Authority> findOneAuthorityByName(@Param("name") String name);

    @Query("select authority from Authority authority where name=:name ")
    Authority authorityByName(@Param("name") String name);

    @Query("delete from Authority authority where name=:name ")
    void deleteAuthorityByName(@Param("name") String name);
}

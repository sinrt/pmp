package com.pmp.nwms.repository;

import com.pmp.nwms.domain.DeletedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeletedUserRepository extends JpaRepository<DeletedUser, Long> {
}

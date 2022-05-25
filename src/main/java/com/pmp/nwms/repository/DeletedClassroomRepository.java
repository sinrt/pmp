package com.pmp.nwms.repository;

import com.pmp.nwms.domain.DeletedClassroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeletedClassroomRepository extends JpaRepository<DeletedClassroom, Long> {
}

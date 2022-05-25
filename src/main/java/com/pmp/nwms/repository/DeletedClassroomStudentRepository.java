package com.pmp.nwms.repository;

import com.pmp.nwms.domain.DeletedClassroomStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeletedClassroomStudentRepository extends JpaRepository<DeletedClassroomStudent, Long> {
}

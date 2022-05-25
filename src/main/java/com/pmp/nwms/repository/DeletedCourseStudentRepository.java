package com.pmp.nwms.repository;


import com.pmp.nwms.domain.DeletedCourseStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeletedCourseStudentRepository extends JpaRepository<DeletedCourseStudent, Long> {
}

package com.pmp.nwms.repository;

import com.pmp.nwms.domain.DeletedCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeletedCourseRepository extends JpaRepository<DeletedCourse, Long> {
}

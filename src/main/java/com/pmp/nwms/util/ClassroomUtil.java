package com.pmp.nwms.util;

import com.pmp.nwms.domain.Classroom;
import com.pmp.nwms.domain.ClassroomStudent;
import com.pmp.nwms.domain.ClassroomStudentId;
import com.pmp.nwms.domain.User;
import com.pmp.nwms.domain.enums.ClassroomStudentAuthority;
import com.pmp.nwms.repository.ClassroomRepository;
import com.pmp.nwms.repository.ClassroomStudentRepository;
import com.pmp.nwms.repository.CourseRepository;
import com.pmp.nwms.repository.UserRepository;
import com.pmp.nwms.security.NwmsUser;
import com.pmp.nwms.service.dto.ClassroomInfoDto;
import com.pmp.nwms.service.dto.CourseInfoDto;
import com.pmp.nwms.web.rest.errors.ClassroomDoesNotBelongToCourseException;
import com.pmp.nwms.web.rest.errors.EntityNotAccessibleByUserException;
import com.pmp.nwms.web.rest.errors.EntityNotFountByIdException;

import java.util.Optional;

public class ClassroomUtil {

    public static Classroom findClassroom(Long classroomId, ClassroomRepository classroomRepository, ClassroomStudentRepository classroomStudentRepository) {
        Optional<Classroom> byId = classroomRepository.findById(classroomId);
        if (!byId.isPresent()) {
            throw new EntityNotFountByIdException("Classroom", classroomId);
        }
        Classroom classroom = byId.get();
        checkClassroomModerator(classroom, classroomStudentRepository);
        return classroom;
    }

    public static void checkClassroomModerator(Classroom classroom, ClassroomStudentRepository classroomStudentRepository) {
        NwmsUser user = UserUtil.getCurrentUser();
        if (!classroom.getCreator().getId().equals(user.getId())) {
            Optional<ClassroomStudent> classroomStudentById = classroomStudentRepository.findByClassroomAndStudentId(classroom.getId(), user.getId());
            if (!classroomStudentById.isPresent() || !classroomStudentById.get().getAuthorityName().equalsIgnoreCase(ClassroomStudentAuthority.MODERATOR.name())) {
                throw new EntityNotAccessibleByUserException("Classroom", classroom.getId());
            }
        }
    }

    public static void checkClassroomModerator(Long classroomId, ClassroomRepository classroomRepository, ClassroomStudentRepository classroomStudentRepository) {
        Optional<ClassroomInfoDto> classroom = classroomRepository.findClassroomInfoDto(classroomId);
        if (!classroom.isPresent()) {
            throw new EntityNotFountByIdException("Classroom", classroomId);
        }
        NwmsUser user = UserUtil.getCurrentUser();
        ClassroomInfoDto dto = classroom.get();
        if (!dto.getClassroomCreatorId().equals(user.getId())) {
            Optional<ClassroomStudent> classroomStudentById = classroomStudentRepository.findByClassroomAndStudentId(dto.getId(), user.getId());
            if (!classroomStudentById.isPresent() || !classroomStudentById.get().getAuthorityName().equalsIgnoreCase(ClassroomStudentAuthority.MODERATOR.name())) {
                throw new EntityNotAccessibleByUserException("Classroom", dto.getId());
            }
        }
    }

    public static void checkCourse(Long courseId, CourseRepository courseRepository) {
        Optional<CourseInfoDto> course = courseRepository.findCourseInfoDto(courseId);
        if (!course.isPresent()) {
            throw new EntityNotFountByIdException("Course", courseId);
        }
        NwmsUser user = UserUtil.getCurrentUser();
        if (!course.get().getCreatorId().equals(user.getId())) {
            throw new EntityNotAccessibleByUserException("Course", courseId);
        }
    }


    public static void checkClassroom(Long classroomId, ClassroomRepository classroomRepository) {
        Optional<ClassroomInfoDto> classroom = classroomRepository.findClassroomInfoDto(classroomId);
        if (!classroom.isPresent()) {
            throw new EntityNotFountByIdException("Classroom", classroomId);
        }
        NwmsUser user = UserUtil.getCurrentUser();
        ClassroomInfoDto classroomInfoDto = classroom.get();
        if (!classroomInfoDto.getClassroomCreatorId().equals(user.getId())) {
            throw new EntityNotAccessibleByUserException("Classroom", classroomId);
        }
    }

    public static void checkCourseAndClassroom(Long courseId, Long classroomId, ClassroomRepository classroomRepository) {
        Optional<ClassroomInfoDto> classroom = classroomRepository.findClassroomInfoDto(classroomId);
        if (!classroom.isPresent()) {
            throw new EntityNotFountByIdException("Classroom", classroomId);
        }
        NwmsUser user = UserUtil.getCurrentUser();
        ClassroomInfoDto classroomInfoDto = classroom.get();
        if (!classroomInfoDto.getCourseId().equals(courseId)) {
            throw new ClassroomDoesNotBelongToCourseException(classroomId, courseId);
        }
        if (!classroomInfoDto.getCourseCreatorId().equals(user.getId())) {
            throw new EntityNotAccessibleByUserException("Course", courseId);
        }
        if (!classroomInfoDto.getClassroomCreatorId().equals(user.getId())) {
            throw new EntityNotAccessibleByUserException("Classroom", classroomId);
        }
    }

}

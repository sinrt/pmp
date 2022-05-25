package com.pmp.nwms.service.impl;

import com.pmp.nwms.domain.*;
import com.pmp.nwms.domain.enums.ClassroomStudentAuthority;
import com.pmp.nwms.repository.*;
import com.pmp.nwms.service.ClassroomStudentService;
import com.pmp.nwms.service.dto.WebinarClassroomStudentDTO;
import com.pmp.nwms.service.dto.WebinarClassroomStudentResultDto;
import com.pmp.nwms.service.listmodel.WebinarClassroomStudentListModel;
import com.pmp.nwms.util.ClassroomUtil;
import com.pmp.nwms.util.StringUtil;
import com.pmp.nwms.util.UserUtil;
import com.pmp.nwms.web.rest.errors.ClassroomSecretKeyNotDefinedException;
import com.pmp.nwms.web.rest.errors.EntityNotFountByIdException;
import com.pmp.nwms.web.rest.errors.InvalidClassroomStudentAuthorityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.*;

@Service("classroomStudentService")
public class ClassroomStudentServiceImpl implements ClassroomStudentService {
    private static final Logger log = LoggerFactory.getLogger(ClassroomStudentServiceImpl.class);

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private ClassroomStudentRepository classroomStudentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public WebinarClassroomStudentResultDto saveWebinarClassroomStudent(WebinarClassroomStudentDTO dto) {
        try {
            ClassroomStudentAuthority.valueOf(dto.getAuthorityName());
        } catch (IllegalArgumentException e) {
            throw new InvalidClassroomStudentAuthorityException(dto.getAuthorityName());
        }

        ClassroomUtil.checkClassroom(dto.getClassroomId(), classroomRepository);
        ClassroomStudent classroomStudent = null;
        User classroomStudentUser;
        ClassroomStudentId classroomStudentId = new ClassroomStudentId();
        Classroom classroom = classroomRepository.getOne(dto.getClassroomId());

        if(dto.getDynamicStudent()){
            if(classroom.getSecretKey() == null || classroom.getSecretKey().isEmpty()){
                throw new ClassroomSecretKeyNotDefinedException();
            }
        }

        classroomStudentId.setClassroom(classroom);
        boolean isNewUser = false;
        dto.setLogin(StringUtil.convertPersianNumbers(dto.getLogin()));
        if (dto.getUserId() == null) {
            //find user by login
            Optional<User> userByUserName = userRepository.findUserByUserName(dto.getLogin());
            if (userByUserName.isPresent()) {
                classroomStudentUser = userByUserName.get();
            } else {
                classroomStudentUser = new User();
                isNewUser = true;
            }
            classroomStudentId.setStudent(classroomStudentUser);
            if (!isNewUser) {
                Optional<ClassroomStudent> byId = classroomStudentRepository.findById(classroomStudentId);
                if (byId.isPresent()) {
                    classroomStudent = byId.get();
                }
            }

            if (classroomStudent == null) {
                classroomStudent = new ClassroomStudent();
            }


        } else {
            //find classroomStudent
            classroomStudentId.setClassroom(classroom);
            Optional<User> userOptional = userRepository.findById(dto.getUserId());
            if (!userOptional.isPresent()) {
                throw new EntityNotFountByIdException("user", dto.getUserId());
            }
            classroomStudentUser = userOptional.get();
            classroomStudentId.setStudent(classroomStudentUser);
            Optional<ClassroomStudent> byId = classroomStudentRepository.findById(classroomStudentId);
            if (!byId.isPresent()) {
                throw new EntityNotFountByIdException("classroomStudent", classroomStudentId);
            }
            classroomStudent = byId.get();
        }

        classroomStudentUser.setLogin(dto.getLogin());
        if (isNewUser) {
            classroomStudentUser.setPassword(passwordEncoder.encode(StringUtil.convertPersianNumbers(dto.getPassword())));
            Set<Authority> strings = new HashSet<>();
            strings.add(authorityRepository.authorityByName("ROLE_USER"));
            classroomStudentUser.setAuthorities(strings);
            classroomStudentUser.setGender(true);
            classroomStudentUser.setValidityDate(new Date());
        }
        classroomStudentUser.setFirstName(dto.getFirstName());
        classroomStudentUser.setLastName(dto.getLastName());
        classroomStudentUser.setActivated(true);

        classroomStudentUser.setRecordHashCode(UserUtil.calculateUserRecordHashCode(classroomStudentUser));
        classroomStudentUser = userRepository.save(classroomStudentUser);

        classroomStudentId.setStudent(classroomStudentUser);

        classroomStudent.setPk(classroomStudentId);
        classroomStudent.setAuthorityName(dto.getAuthorityName());
        if (dto.getAuthorityName().equals(ClassroomStudentAuthority.MODERATOR.name())) {
            classroomStudent.setNeedsLogin(true);
        } else {
            classroomStudent.setNeedsLogin(dto.isNeedsLogin());
        }
        classroomStudent.setMaxUseCount(dto.getMaxUseCount());
        classroomStudent.setFullName(dto.getFirstName() + " " + dto.getLastName());
        if (classroomStudent.getToken() == null || classroomStudent.getToken().isEmpty()) {
            classroomStudent.setToken(UUID.randomUUID().toString());
        }
        classroomStudent.setDynamicStudent(dto.getDynamicStudent());
        classroomStudent = classroomStudentRepository.save(classroomStudent);


        Course course = classroom.getCourse();
        if (course.getStudents() == null) {
            course.setStudents(new HashSet<>());
        }
        if (!course.getStudents().contains(classroomStudentUser)) {
            course.getStudents().add(classroomStudentUser);
            course = courseRepository.save(course);
        }

        return new WebinarClassroomStudentResultDto(classroomStudent.getPk().getStudent().getId(), classroomStudent.getToken());
    }

    @Override
    public void delete(Long classroomId, Long studentId) {
        ClassroomUtil.checkClassroom(classroomId, classroomRepository);

        Classroom classroom = classroomRepository.findById(classroomId).get();
        Optional<User> byId = userRepository.findById(studentId);
        if (!byId.isPresent()) {
            throw new EntityNotFountByIdException("User", studentId);
        }
        User student = byId.get();
        ClassroomStudentId classroomStudentId = new ClassroomStudentId(classroom, student);

        long count = classroomStudentRepository.countOtherCourseClassroomStudent(classroom.getCourse().getId(), classroomId, studentId);
        if (count == 0L) {
            Course course = classroom.getCourse();
            course.getStudents().remove(student);
            course = courseRepository.save(course);

        }

        classroomStudentRepository.deleteById(classroomStudentId);

    }

    @Override
    public Page<WebinarClassroomStudentListModel> findWebinarClassroomStudentDTOsByClassroomId(Long classroomId, Pageable pageable) {
        ClassroomUtil.checkClassroom(classroomId, classroomRepository);
        return classroomStudentRepository.findAllByClassroom(classroomId, pageable);
    }

    @Override
    public void fixStudentsData() {
        long t1 = System.currentTimeMillis();
        log.debug("fixStudentsData started");
        List<ClassroomStudent> all = classroomStudentRepository.findAllByEmptyToken();
        if(all != null && !all.isEmpty()){
            log.debug(" going to fix " + all.size() + " students data");
            for (ClassroomStudent classroomStudent : all) {
                classroomStudent.setToken(UUID.randomUUID().toString());
                classroomStudent.setFullName(classroomStudent.getPk().getStudent().getFirstName() + " " + classroomStudent.getPk().getStudent().getLastName());
                classroomStudent = classroomStudentRepository.save(classroomStudent);
            }
        }
        long t2 = System.currentTimeMillis();
        log.debug("fixStudentsData finished in " + (t2-t1) + " milli seconds");
    }
}

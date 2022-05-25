package com.pmp.nwms.service.impl;

import com.pmp.nwms.config.Constants;
import com.pmp.nwms.domain.*;
import com.pmp.nwms.domain.enums.ClassroomStudentAuthority;
import com.pmp.nwms.model.FilePathInfoModel;
import com.pmp.nwms.repository.*;
import com.pmp.nwms.security.NwmsUser;
import com.pmp.nwms.service.ClassroomService;
import com.pmp.nwms.service.CourseService;
import com.pmp.nwms.service.OrganizationLevelService;
import com.pmp.nwms.service.dto.*;
import com.pmp.nwms.service.listmodel.CourseListModel;
import com.pmp.nwms.service.listmodel.CourseStudentListModel;
import com.pmp.nwms.util.*;
import com.pmp.nwms.web.rest.errors.*;
import com.pmp.nwms.web.rest.vm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Service Implementation for managing Course.
 */
@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);

    private final CourseRepository courseRepository;

    private final ClassroomService classroomService;

    private final ClassroomStudentRepository classroomStudentRepository;

    private final ClassroomRepository classroomRepository;

    private final UserRepository userRepository;

    private final OrganizationLevelService organizationLevelService;

    private final AuthorityRepository authorityRepository;

    private final CacheManager cacheManager;

    private final PasswordEncoder passwordEncoder;

    private final ClassroomFileSlideRepository classroomFileSlideRepository;

    private final ClassroomFileRepository classroomFileRepository;

    private final DeletedCourseStudentRepository deletedCourseStudentRepository;

    private final DeletedClassroomStudentRepository deletedClassroomStudentRepository;
    private final DeletedClassroomRepository deletedClassroomRepository;
    private final DeletedUserRepository deletedUserRepository;
    private final DeletedCourseRepository deletedCourseRepository;
    private final JackrabbitRepoUtil jackrabbitRepoUtil;
    private final PurchaseStatusRepository purchaseStatusRepository;

    public CourseServiceImpl(CourseRepository courseRepository, ClassroomService classroomService,
                             UserRepository userRepository, ClassroomStudentRepository classroomStudentRepository,
                             ClassroomRepository classroomRepository,
                             OrganizationLevelService organizationLevelService,
                             AuthorityRepository authorityRepository,
                             CacheManager cacheManager,
                             PasswordEncoder passwordEncoder,
                             ClassroomFileSlideRepository classroomFileSlideRepository,
                             ClassroomFileRepository classroomFileRepository,
                             DeletedCourseStudentRepository deletedCourseStudentRepository,
                             DeletedClassroomStudentRepository deletedClassroomStudentRepository,
                             DeletedClassroomRepository deletedClassroomRepository,
                             DeletedUserRepository deletedUserRepository,
                             DeletedCourseRepository deletedCourseRepository,
                             JackrabbitRepoUtil jackrabbitRepoUtil,
                             PurchaseStatusRepository purchaseStatusRepository
                             ) {
        this.courseRepository = courseRepository;
        this.classroomService = classroomService;
        this.userRepository = userRepository;
        this.classroomStudentRepository = classroomStudentRepository;
        this.classroomRepository = classroomRepository;
        this.organizationLevelService = organizationLevelService;
        this.authorityRepository = authorityRepository;
        this.cacheManager = cacheManager;
        this.passwordEncoder = passwordEncoder;
        this.classroomFileSlideRepository = classroomFileSlideRepository;
        this.classroomFileRepository = classroomFileRepository;
        this.deletedCourseStudentRepository = deletedCourseStudentRepository;
        this.deletedClassroomStudentRepository = deletedClassroomStudentRepository;
        this.deletedClassroomRepository = deletedClassroomRepository;
        this.deletedUserRepository = deletedUserRepository;
        this.deletedCourseRepository = deletedCourseRepository;
        this.jackrabbitRepoUtil = jackrabbitRepoUtil;
        this.purchaseStatusRepository = purchaseStatusRepository;
    }

    /**
     * Save a course.
     *
     * @param course the entity to updateClassroom
     * @return the persisted entity
     */
    @Override
    public Course save(Course course) {
        log.debug("Request to updateClassroom Course : {}", course);
        Set<Classroom> classrooms = new HashSet<>();
        for (Classroom clsroom : course.getClassrooms()) {
            classrooms.add(clsroom);
        }
        course.getClassrooms().clear();
        for (Classroom classroom : classrooms) {
            ClassroomDTO classroomDTO;
            if (classroom.getId() == null) {
                if (classroom.getConnectionType() != null && classroom.getConnectionType().equals("sorushClass")) {
                    for (int i = 0; i < 1000; i++) {
                        if (classroom.getSessionUuidName() == null) {
                            UUID uuid = UUID.randomUUID();
                            String randomUUIDString = uuid.toString();
                            classroom.setSessionUuidName(randomUUIDString);
                        }
                        if (classroom.getMaster() == null) {
                            classroom.setMaster(course.getCreator());
                        }
                        if (classroom.getClassroomStudents().size() == 0) {
                            ClassroomStudent classroomStudent = new ClassroomStudent();
                            classroomStudent.setStudent(course.getCreator());
                            classroomStudent.setClassroom(classroom);
                            classroomStudent.setAuthorityName("Admin");
                            classroom.getClassroomStudents().add(classroomStudent);
                        }
                        if (classroom.getFrameRate() == null) {
                            classroom.setFrameRate(30);
                        }
                        if (classroom.getCreator() == null) {
                            classroom.setCreator(course.getCreator());
                        }
                        if (classroom.getLastModifier() == null) {
                            classroom.setLastModifier(course.getCreator());
                        }
                        if (classroom.getResolution() == null) {
                            classroom.setResolution("High");
                        }
                        if (classroom.getIsGuestWithSubscriberRole() == null) {
                            classroom.setIsGuestWithSubscriberRole(true);
                        }
                        classroom.setGuestSession(true);
                        classroom.setCourse(course);
                        classroomDTO = new ClassroomDTO(classroom);
                        classroomDTO = classroomService.save(classroomDTO);
                        // setTeacherAsStudentOfClass(classroomDTO, course);
                        Classroom classroom1 = classroomRepository.getOne(classroomDTO.getId());
                        course.getClassrooms().add(classroom1);
                    }
                } else {
                    if (classroom.getSessionUuidName() == null) {
                        UUID uuid = UUID.randomUUID();
                        String randomUUIDString = uuid.toString();
                        classroom.setSessionUuidName(randomUUIDString);
                    }
                    if (classroom.getConnectionType() == null) {
                        classroom.setConnectionType("teacherPanel");
                    }
                    if (classroom.getMaster() == null) {
                        classroom.setMaster(course.getCreator());
                    }
                    if (classroom.getClassroomStudents().size() == 0) {
                        ClassroomStudent classroomStudent = new ClassroomStudent();
                        classroomStudent.setStudent(course.getCreator());
                        classroomStudent.setClassroom(classroom);
                        classroomStudent.setAuthorityName("Admin");
                        classroom.getClassroomStudents().add(classroomStudent);
                    }
                    if (classroom.getFrameRate() == null) {
                        classroom.setFrameRate(30);
                    }
                    if (classroom.getCreator() == null) {
                        classroom.setCreator(course.getCreator());
                    }
                    if (classroom.getLastModifier() == null) {
                        classroom.setLastModifier(course.getCreator());
                    }
                    if (classroom.getResolution() == null) {
                        classroom.setResolution("High");
                    }
                    if (classroom.getIsGuestWithSubscriberRole() == null) {
                        classroom.setIsGuestWithSubscriberRole(false);
                    }
                    classroom.setCourse(course);
                    classroomDTO = new ClassroomDTO(classroom);
                    classroomDTO = classroomService.save(classroomDTO);
                  /*  if (classroomDTO.getConnectionType().equals("teacherPanel"))
                        setTeacherAsStudentOfClass(classroomDTO, course);*/
                    Classroom classroom1 = classroomRepository.getOne(classroomDTO.getId());
                    course.getClassrooms().add(classroom1);
                }

            } else {
                for (Classroom classroom1 : classrooms) {
                    if (classroom1.getId() != null) {
                        Set<ClassroomStudent> removedClassroomStudents = classroom1.getClassroomStudents();
                        Iterator<ClassroomStudent> iterator = removedClassroomStudents.iterator();
                        while (iterator.hasNext()) {
                            ClassroomStudent classroomStudent = new ClassroomStudent();
                            ClassroomStudent next = iterator.next();
                            classroomStudent.setAuthorityName(next.getAuthorityName());
                            classroomStudent.setToken(next.getToken());
                            classroomStudent.setMaxUseCount(next.getMaxUseCount());
                            if (next.getAuthorityName().equals(ClassroomStudentAuthority.MODERATOR.name())) {
                                classroomStudent.setNeedsLogin(true);
                            } else {
                                classroomStudent.setNeedsLogin(next.isNeedsLogin());
                            }
                            classroomStudent.setClassroom(classroom1);
                            String fullName;
                            if (next.getPk().getStudent().getId() != null) {
                                User one = userRepository.getOne(next.getPk().getStudent().getId());
                                fullName = one.getFirstName() + " " + one.getLastName();
                                classroomStudent.setStudent(one);
                            } else {
                                User student = userRepository.findOneByLogin(next.getPk().getStudent().getLogin()).get();
                                fullName = student.getFirstName() + " " + student.getLastName();
                                classroomStudent.setStudent(student);
                            }
                            if (classroomStudent.getToken() == null || classroomStudent.getToken().isEmpty()) {
                                classroomStudent.setToken(UUID.randomUUID().toString());
                            }
                            classroomStudent.setFullName(fullName);
                            classroomStudentRepository.save(classroomStudent);
                            iterator.remove();
                        }
                    }
                }
            }
        }
        Set<User> courseStudents = course.getStudents();
        if (course.getId() != null) {
            Course course1 = courseRepository.findById(course.getId()).get();
            Set<User> users = course1.getStudents();
            if (courseStudents.size() < users.size()) {
                Set<User> result = new HashSet<>(courseStudents);
                for (User element : users) {
                    // .add() returns false if element already exists
                    if (!result.add(element)) {
                        result.remove(element);
                    }
                }
                for (User user : result) {
                    classroomStudentRepository.deleteByStudentId(user.getId());
                }
            }
        }
        Iterator<User> iterator = courseStudents.iterator();
        Set<User> userSet = new HashSet<>();
        while (iterator.hasNext()) {
            User next = iterator.next();
            User user = userRepository.findOneByLogin(next.getLogin()).get();
            userSet.add(user);
            iterator.remove();

        }
        course.setStudents(userSet);
        return courseRepository.save(course);
    }
/*

    public void setTeacherAsStudentOfClass(ClassroomDTO classroomDTO, Course course) {
        ClassroomStudent cs = new ClassroomStudent();
        cs.setAuthorityName("PUBLISHER");
        cs.setClassroom(ClassroomDTO.build(classroomDTO));
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User)
            SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByUserName(principal.getUsername()).get();
        cs.setStudent(userRepository.findOneByLogin(user.getLogin()).get());
        classroomStudentRepository.save(cs);
        if (!course.getStudents().contains(user)) {
            course.getStudents().add(user);
            courseRepository.save(course);
        }
    }
*/

    /**
     * Get all the courses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Course> findAll(Pageable pageable) {
        log.debug("Request to get all Courses");
        return courseRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> findByCreatorAndTeacherPan(Long id, boolean teacherPan) {
        return courseRepository.findCourseByCreatorAndTeacherPan(id, teacherPan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> findByCreatorAndGStatus(Long id, boolean teacherPan) {
        return courseRepository.findCourseByCreatorAndTeacherPan(id, teacherPan);
    }


    /**
     * Get one course by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findOne(Long id) {
        log.debug("Request to get Course : {}", id);
        return courseRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findOneByName(String name) {
        log.debug("Request to get Course : {}", name);
        return courseRepository.findByTitle(name);
    }

    /**
     * Delete the course by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        Course course = courseRepository.getOne(id);
        for (Classroom classroom : course.getClassrooms()) {
            Classroom classroom1 = classroomService.findOne(classroom.getId()).get();
            classroomService.delete(classroom1.getId());
        }
        course.getClassrooms().clear();
        courseRepository.deleteCourseById(course.getId());
    }

    @Override
    @Transactional
    public Long saveCourse(CourseVM vm) {
        Course entity;
        User user = UserUtil.getCurrentUserEntity_(userRepository);
        if (vm.getId() == null) {
            entity = new Course();
            entity.setCreator(user);
        } else {
            ClassroomUtil.checkCourse(vm.getId(), courseRepository);
            entity = courseRepository.findById(vm.getId()).get();
        }
        entity.setModifier(user);

        entity.setTitle(vm.getTitle());
        entity.setDescription(vm.getDescription());
        entity.setStartTime(vm.getStartDateTime());
        entity.setFinishTime(vm.getFinishDateTime());

        validateCourse(entity.getTitle(), user.getId(), entity.getId(), entity.getStartTime(), entity.getFinishTime());

        entity = courseRepository.save(entity);

        return entity.getId();
    }

    @Override
    public CourseVM getCourseVM(Long courseId) {
        validateCourse(courseId);
        Course course = courseRepository.findById(courseId).get();
        CourseVM vm = makeCourseVM(course);
        return vm;
    }

    private CourseVM makeCourseVM(Course course) {
        CourseVM vm = new CourseVM();
        vm.setId(course.getId());
        vm.setTitle(course.getTitle());
        vm.setDescription(course.getDescription());
        vm.setStartDateTime(course.getStartTime());
        vm.setFinishDateTime(course.getFinishTime());
        return vm;
    }

    @Override
    public Page<CourseVM> findAllCourseVMs(Pageable pageable) {
        NwmsUser currentUser = UserUtil.getCurrentUser();
        Page<Course> all = courseRepository.findAllByCreator(currentUser.getId(), pageable);
        List<CourseVM> vms = new ArrayList<>();
        for (Course course : all) {
            vms.add(makeCourseVM(course));
        }
        return new PageImpl<>(vms, all.getPageable(), all.getTotalElements());
    }


    @Override
    @Transactional
    public Long createNewCourseUser(CourseUserVM vm) {
        if (vm.getPassword() == null || vm.getPassword().isEmpty()) {
            throw new BadRequestAlertException("Invalid Password", "User", "passwordnull");
        }
        validateCourseClassroomInfo(vm.getCourseId(), vm.getClassrooms());
        User user = createUser(vm);
        Course course = courseRepository.findById(vm.getCourseId()).get();
        course.getStudents().add(user);
        course = courseRepository.save(course);
        Set<Classroom> classrooms = course.getClassrooms();
        for (CourseUserClassroomVM classroomVm : vm.getClassrooms()) {
            Classroom classroom = findClassroom(classroomVm.getClassroomId(), classrooms);
            ClassroomStudent classroomStudent;
            ClassroomStudentId pk = new ClassroomStudentId(classroom, user);
            Optional<ClassroomStudent> byId = classroomStudentRepository.findById(pk);
            if (byId.isPresent()) {
                classroomStudent = byId.get();
            } else {
                classroomStudent = new ClassroomStudent();
                classroomStudent.setToken(UUID.randomUUID().toString());
                classroomStudent.setPk(pk);
            }
            classroomStudent.setAuthorityName(classroomVm.getAuthorityName());
            classroomStudent.setFullName(vm.getFirstName() + " " + vm.getLastName());
            if (classroomVm.getAuthorityName().equals(ClassroomStudentAuthority.MODERATOR.name())) {
                classroomStudent.setNeedsLogin(true);
            } else {
                classroomStudent.setNeedsLogin(classroomVm.getNeedsLogin());
            }
            classroomStudent.setMaxUseCount(classroomVm.getMaxUseCount());

            classroomStudent = classroomStudentRepository.save(classroomStudent);
        }
        return user.getId();
    }

    private void validateCourseClassroomInfo(Long courseId, List<CourseUserClassroomVM> classroomVms) {
        ClassroomUtil.checkCourse(courseId, courseRepository);
        List<Classroom> classrooms = classroomRepository.findByCourseId(courseId);
        if (classrooms.size() != classroomVms.size()) {
            throw new InvalidClassroomCountException();
        }

        List<String> validAuthorityNames = new ArrayList<>();
        validAuthorityNames.add("notexist");
        for (ClassroomStudentAuthority value : ClassroomStudentAuthority.values()) {
            validAuthorityNames.add(value.name());
        }

        for (CourseUserClassroomVM classroomVm : classroomVms) {
            if (!validAuthorityNames.contains(classroomVm.getAuthorityName())) {
                throw new InvalidClassroomStudentAuthorityException(classroomVm.getAuthorityName());
            }
            Classroom classroom = findClassroom(classroomVm.getClassroomId(), classrooms);
            if (classroom == null) {
                throw new EntityNotFountByIdException("classroom", classroomVm.getClassroomId());
            }
        }
    }

    private Classroom findClassroom(Long classroomId, Collection<Classroom> classrooms) {
        for (Classroom classroom : classrooms) {
            if (classroomId.equals(classroom.getId())) {
                return classroom;
            }
        }
        return null;
    }

    private User createUser(CourseUserVM vm) {

        Optional<User> existingUser = userRepository.findOneByLogin(vm.getLogin().toLowerCase());
        UserDTO userDTO = new UserDTO();
        boolean updateExistingUser = false;
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            if(!user.getCreatedBy().equals(UserUtil.getCurrentUser().getUsername())){
                throw new LoginAlreadyUsedException();
            }else{
                userDTO = new UserDTO(user);
                updateExistingUser = true;
            }
        }

        userDTO.setLogin(vm.getLogin());
        userDTO.setFirstName(vm.getFirstName());
        userDTO.setLastName(vm.getLastName());
        userDTO.setPassword(vm.getPassword());
        User user;
        if (updateExistingUser) {
            user = UserUtil.updateUser(userDTO, organizationLevelService, authorityRepository, passwordEncoder, userRepository, cacheManager);
        } else {
            userDTO.setGender(true);
            userDTO.setValidityDate(new Date());
            Set<String> strings = new HashSet<>();
            strings.add("ROLE_USER");
            userDTO.setAuthorities(strings);
            user = UserUtil.createUser(userDTO, organizationLevelService, authorityRepository, passwordEncoder, userRepository);
        }
        UserUtil.clearUserCaches(user, cacheManager);
        return user;
    }

    @Override
    @Transactional
    public Long updateCourseUser(CourseUserVM vm) {
        validateCourseClassroomInfo(vm.getCourseId(), vm.getClassrooms());
        User user = updateUser(vm);

        List<Classroom> classrooms = classroomRepository.findByCourseId(vm.getCourseId());
        for (CourseUserClassroomVM classroomVm : vm.getClassrooms()) {
            Classroom classroom = findClassroom(classroomVm.getClassroomId(), classrooms);
            ClassroomStudentId classroomStudentId = new ClassroomStudentId(classroom, user);
            Optional<ClassroomStudent> byId = classroomStudentRepository.findById(classroomStudentId);
            ClassroomStudent classroomStudent;
            if (byId.isPresent()) {
                classroomStudent = byId.get();
            } else {
                classroomStudent = new ClassroomStudent();
                classroomStudent.setPk(classroomStudentId);
            }
            classroomStudent.setAuthorityName(classroomVm.getAuthorityName());
            classroomStudent.setFullName(vm.getFirstName() + " " + vm.getLastName());
            if (classroomVm.getAuthorityName().equals(ClassroomStudentAuthority.MODERATOR.name())) {
                classroomStudent.setNeedsLogin(true);
            } else {
                classroomStudent.setNeedsLogin(classroomVm.getNeedsLogin());
            }
            classroomStudent.setMaxUseCount(classroomVm.getMaxUseCount());
            if (classroomStudent.getToken() == null || classroomStudent.getToken().isEmpty()) {
                classroomStudent.setToken(UUID.randomUUID().toString());
            }
            classroomStudent = classroomStudentRepository.save(classroomStudent);
        }

        return user.getId();
    }

    @Override
    public List<Long> addBatchUsers(CourseBatchUserVM vm) {
        if (vm.hasClassroomDataPartial()) {
            throw new BadRequestAlertException("Invalid input", "CourseBatchUser", "classroom.data.not.accepted");
        }
        if (vm.hasClassroomDataComplete()) {
            try {
                ClassroomStudentAuthority.valueOf(vm.getAuthorityName());
            } catch (IllegalArgumentException e) {
                throw new InvalidClassroomStudentAuthorityException(vm.getAuthorityName());
            }
        }
        ClassroomUtil.checkCourse(vm.getCourseId(), courseRepository);
        List<User> users = userRepository.findAllById(vm.getUserIds());
        Course course = courseRepository.findById(vm.getCourseId()).get();
        course.getStudents().addAll(users);
        course = courseRepository.save(course);

        if (vm.hasClassroomDataComplete()) {

            ClassroomUtil.checkCourseAndClassroom(vm.getCourseId(), vm.getClassroomId(), classroomRepository);

            List<ClassroomStudent> existingClassroomStudents = classroomStudentRepository.findAllByClassroomAndStudentIds(vm.getClassroomId(), vm.getUserIds());
            List<Long> toAddUserIds = vm.getUserIds();
            if (existingClassroomStudents != null && !existingClassroomStudents.isEmpty()) {
                for (ClassroomStudent classroomStudent : existingClassroomStudents) {
                    classroomStudent.setAuthorityName(vm.getAuthorityName());
                    if (vm.getAuthorityName().equals(ClassroomStudentAuthority.MODERATOR.name())) {
                        classroomStudent.setNeedsLogin(true);
                    } else {
                        classroomStudent.setNeedsLogin(vm.getNeedsLogin());
                    }
                    classroomStudent.setMaxUseCount(vm.getMaxUseCount());
                    toAddUserIds.remove(classroomStudent.getStudent().getId());
                }
                classroomStudentRepository.saveAll(existingClassroomStudents);
            }
            if (!toAddUserIds.isEmpty()) {
                Classroom classroom = classroomRepository.getOne(vm.getClassroomId());
                for (User user : users) {
                    if (toAddUserIds.contains(user.getId())) {
                        ClassroomStudent classroomStudent = new ClassroomStudent();
                        classroomStudent.setPk(new ClassroomStudentId(classroom, user));
                        classroomStudent.setAuthorityName(vm.getAuthorityName());
                        classroomStudent.setFullName(user.getFirstName() + " " + user.getLastName());
                        if (vm.getAuthorityName().equals(ClassroomStudentAuthority.MODERATOR.name())) {
                            classroomStudent.setNeedsLogin(true);
                        } else {
                            classroomStudent.setNeedsLogin(vm.getNeedsLogin());
                        }
                        classroomStudent.setMaxUseCount(vm.getMaxUseCount());
                        classroomStudent.setToken(UUID.randomUUID().toString());
                        classroomStudentRepository.save(classroomStudent);
                    }
                }
            }
        }

        return vm.getUserIds();
    }

    @Override
    public boolean deleteCourseUser(Long courseId, Long userId) {
        validateCourse(courseId);
        boolean foundUser = false;
        Course course = courseRepository.findById(courseId).get();
        Set<User> students = course.getStudents();
        for (User student : students) {
            if (student.getId().equals(userId)) {
                students.remove(student);
                foundUser = true;
                break;
            }
        }
        if (foundUser) {
            course = courseRepository.save(course);
            List<Long> classroomIds = classroomRepository.findIdsByCourseId(courseId);
            classroomStudentRepository.deleteByClassroomIdsAndStudentId(classroomIds, userId);
        }
        return foundUser;
    }

    @Override
    public List<CourseListModel> findCreatorAndTeacherPan(boolean teacherPan) {
        NwmsUser user = UserUtil.getCurrentUser();
        return courseRepository.findCourseListModelByCreatorAndTeacherPan(user.getId(), teacherPan);
    }

    @Override
    public Long simpleSave(CourseSimpleVM vm) {
        Course entity;
        User user = UserUtil.getCurrentUserEntity_(userRepository);
        if (vm.getId() == null) {
            entity = new Course();
            entity.setCreator(user);
            Date now = new Date();
            entity.setStartTime(now);
            entity.setFinishTime(now);
        } else {
            ClassroomUtil.checkCourse(vm.getId(), courseRepository);
            entity = courseRepository.findById(vm.getId()).get();
        }
        validateCourse(vm.getTitle(), user.getId(), vm.getId(), entity.getStartTime(), entity.getFinishTime());

        entity.setModifier(user);

        entity.setTitle(vm.getTitle());
        entity.setDescription(vm.getDescription());
        entity.setTeacherPan(vm.isTeacherPan());


        entity = courseRepository.save(entity);

        return entity.getId();
    }

    @Override
    public List<CourseStudentListModel> getCourseStudents(Long courseId) {
        validateCourse(courseId);
        return courseRepository.getCourseStudents(courseId);
    }

    @Override
    public CourseStudentDTO getCourseStudent(Long courseId, Long studentId) {
        NwmsUser user = UserUtil.getCurrentUser();
        long count = courseRepository.countCourseStudentByCreator(courseId, studentId, user.getId());
        if (count <= 0) {
            throw new EntityNotAccessibleByUserException("course", courseId);
        }
        CourseStudentDataDTO dataDto = userRepository.getCourseStudentDTO(studentId);
        CourseStudentDTO dto = new CourseStudentDTO(dataDto);

        List<ClassroomStudentDTO> dtos = classroomStudentRepository.findClassroomStudentsUsingCourseIdAndStudentId(courseId, studentId);
        ArrayList<ClassroomStudentDataDTO> classroomStudents = new ArrayList<>();
        for (ClassroomStudentDTO studentDTO : dtos) {
            classroomStudents.add(new ClassroomStudentDataDTO(studentDTO));
        }
        PurchaseStatusUtil.setSpecialLinks(classroomStudents, purchaseStatusRepository);
        dto.setClassroomStudents(classroomStudents);
        return dto;
    }

    @Override
    public void fullDeleteCourse(Long courseId) {
        //check course belongs to user or not
        ClassroomUtil.checkCourse(courseId, courseRepository);
        //remove all slides for classrooms in this course
        deleteCourseFileSlides(courseId);
        //remove all files for classrooms in this course
        deleteCourseClassroomFiles(courseId);
        //move all courseStudents of this course to DeletedCourseStudent
        moveCourseStudents(courseId);
        //move all classroomStudents of classrooms in this course to DeletedClassroomStudent
        FullDeleteUtil.moveClassroomStudentsByCourseId(courseId, classroomStudentRepository, deletedClassroomStudentRepository);
        //move all classrooms of this course to DeletedClassroom
        List<Classroom> classrooms = moveCourseClassrooms(courseId);
        //move course to DeletedCourse
        Course course = moveCourse(courseId);
        //check if there is any singleCourse users, move their data to DeletedUser and delete them
        deleteSingleClassUsers(courseId);
        //delete all classroomStudents of classrooms in this course
        classroomStudentRepository.deleteUsingCourseId(courseId);
        //delete all classrooms of this course
        if (classrooms != null && !classrooms.isEmpty()) {
            for (Classroom classroom : classrooms) {
                if(classroom.isSessionActive()){
                    throw new UnableToDeleteClassroomDueToActiveSessionException();
                }
            }
            classroomRepository.deleteByCourseId(courseId);
        }
        //delete the course
        courseRepository.delete(course);
    }

    private Course moveCourse(Long courseId) {
        Course course = courseRepository.findById(courseId).get();
        DeletedCourse deletedCourse = new DeletedCourse();
        deletedCourse.setId(course.getId());
        deletedCourse.setTitle(course.getTitle());
        deletedCourse.setDescription(course.getDescription());
        deletedCourse.setTeacherPan(course.isTeacherPan());
        deletedCourse.setStartTime(ZonedDateTime.ofInstant(course.getStartTime().toInstant(), ZoneId.systemDefault()));
        deletedCourse.setFinishTime(ZonedDateTime.ofInstant(course.getFinishTime().toInstant(), ZoneId.systemDefault()));
        if (course.getCreator() != null) {
            deletedCourse.setCreatorId(course.getCreator().getId());
        }
        if (course.getModifier() != null) {
            deletedCourse.setModifierId(course.getModifier().getId());
        }
        deletedCourse.setOriginalCreatedBy(course.getCreatedBy());
        deletedCourse.setOriginalCreatedDate(course.getCreatedDate());
        deletedCourse.setOriginalLastModifiedBy(course.getLastModifiedBy());
        deletedCourse.setOriginalLastModifiedDate(course.getLastModifiedDate());
        deletedCourse.setOriginalVersion(course.getVersion());
        deletedCourse = deletedCourseRepository.save(deletedCourse);
        return course;
    }

    private void deleteSingleClassUsers(Long courseId) {
        List<Number> singleClassUserIds = courseRepository.findSingleCourseUserIdsUsingCourseId(courseId);
        if(singleClassUserIds != null && !singleClassUserIds.isEmpty()){
            for (Number userId : singleClassUserIds) {
                FullDeleteUtil.moveUser(userId.longValue(), userRepository, deletedUserRepository);
                userRepository.deleteById(userId.longValue());
            }
        }
    }

    private List<Classroom> moveCourseClassrooms(Long courseId) {
        List<Classroom> classrooms = classroomRepository.findAllByCourseId(courseId);
        if(classrooms != null && !classrooms.isEmpty()){
            for (Classroom classroom : classrooms) {
                FullDeleteUtil.moveClassroom(classroom, deletedClassroomRepository);
            }
        }
        return classrooms;
    }

    private void deleteCourseFileSlides(Long courseId) {
        List<FilePathInfoModel> slides = classroomFileSlideRepository.findAllFilePathInfoModelsUsingCourseId(courseId);
        if(slides != null && !slides.isEmpty()){
            jackrabbitRepoUtil.removeBatch(slides);
            classroomFileSlideRepository.deleteAllUsingCourseId(courseId);
        }
    }

    private void deleteCourseClassroomFiles(Long courseId) {
        List<FilePathInfoModel> files = classroomFileRepository.findAllFilePathInfoModelsUsingCourseId(courseId);
        if(files != null && !files.isEmpty()){
            jackrabbitRepoUtil.removeBatch(files);
            classroomFileRepository.deleteAllUsingCourseId(courseId);
        }
    }

    private void moveCourseStudents(Long courseId) {
        List<Number> userIds = courseRepository.findCourseStudentIdsUsingCourseId(courseId);
        if (userIds != null && !userIds.isEmpty()) {
            ArrayList<DeletedCourseStudent> deletedCourseStudents = new ArrayList<>();
            for (Number userId : userIds) {
                DeletedCourseStudent e = new DeletedCourseStudent();
                e.setCourseId(courseId);
                e.setUserId(userId.longValue());
                deletedCourseStudents.add(e);
            }
            deletedCourseStudentRepository.saveAll(deletedCourseStudents);
        }
    }

    private void validateCourse(Long courseId) {
        ClassroomUtil.checkCourse(courseId, courseRepository);
    }

    private User updateUser(CourseUserVM vm) {
        Optional<User> byId = userRepository.findById(vm.getId());
        if (!byId.isPresent()) {
            throw new EntityNotFountByIdException("user", vm.getId());
        }

        Optional<User> existingUser = userRepository.findOneByLogin(vm.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(vm.getId()))) {
            throw new LoginAlreadyUsedException();
        }


        UserDTO userDTO = new UserDTO(byId.get());
        userDTO.setLogin(vm.getLogin());
        userDTO.setFirstName(vm.getFirstName());
        userDTO.setLastName(vm.getLastName());
        if (vm.getPassword() != null && !vm.getPassword().isEmpty()) {
            userDTO.setPassword(vm.getPassword());
        } else {
            userDTO.setPassword("1");
        }
        return UserUtil.updateUser(userDTO, organizationLevelService, authorityRepository, passwordEncoder, userRepository, cacheManager);
    }

    private void validateCourse(String title, Long creatorId, Long courseId, Date startTime, Date finishTime) {
        //check for duplicated name
        long count = courseRepository.countByTitle(title, creatorId, (courseId != null) ? courseId : -1L);
        if (count > 0) {
            throw new DuplicatedCourseTitleException();
        }
        //check the start time is before the end time
        if (finishTime.before(startTime)) {
            throw new InvalidCourseDatePeriodException();
        }
    }
}

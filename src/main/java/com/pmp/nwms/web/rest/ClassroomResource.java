package com.pmp.nwms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pmp.nwms.domain.Classroom;
import com.pmp.nwms.domain.PurchaseStatus;
import com.pmp.nwms.domain.RubruSession;
import com.pmp.nwms.domain.User;
import com.pmp.nwms.logging.IgnoreLoggingValue;
import com.pmp.nwms.repository.ClassroomRepository;
import com.pmp.nwms.repository.PurchaseStatusRepository;
import com.pmp.nwms.repository.UserRepository;
import com.pmp.nwms.security.NwmsUser;
import com.pmp.nwms.service.ClassroomService;
import com.pmp.nwms.service.CourseService;
import com.pmp.nwms.service.KurentoService;
import com.pmp.nwms.service.UserService;
import com.pmp.nwms.service.dto.*;
import com.pmp.nwms.service.listmodel.ClassroomListModel;
import com.pmp.nwms.service.listmodel.RubruSessionListModel;
import com.pmp.nwms.service.util.ClassroomStatusDataHolder;
import com.pmp.nwms.util.UserUtil;
import com.pmp.nwms.web.rest.errors.BadRequestAlertException;
import com.pmp.nwms.web.rest.errors.DuplicatedClassroomSessionUuidNameException;
import com.pmp.nwms.web.rest.errors.UserHasNoActivePlanException;
import com.pmp.nwms.web.rest.errors.UserMaxClassroomCountExceededException;
import com.pmp.nwms.web.rest.util.HeaderUtil;
import com.pmp.nwms.web.rest.util.PaginationUtil;
import com.pmp.nwms.web.rest.vm.ClassroomSettingsVM;
import com.pmp.nwms.web.rest.vm.ClassroomStudentFileDownloadVM;
import com.pmp.nwms.web.rest.vm.DynamicStudentDataVM;
import com.pmp.nwms.web.rest.vm.RubruSessionParticipantsReportVM;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.*;

/**
 * REST controller for managing Classroom.
 */
@RestController
@RequestMapping("/api")
public class ClassroomResource {

    @Autowired
    private UserService userService;

    @Autowired
    private ClassroomStatusDataHolder classroomStatusDataHolder;

    private final Logger log = LoggerFactory.getLogger(ClassroomResource.class);

    private static final String ENTITY_NAME = "classroom";
    private final UserRepository userRepository;
    private final ClassroomService classroomService;
    private final CourseService courseService;
    private final KurentoService kurentoService;
    private final ClassroomRepository classroomrepository;
    private final PurchaseStatusRepository purchaseStatusRepository;


    public ClassroomResource(ClassroomService classroomService, UserRepository userRepository, KurentoService kurentoService, CourseService courseService, ClassroomRepository classroomrepository, PurchaseStatusRepository purchaseStatusRepository) {
        this.userRepository = userRepository;
        this.classroomService = classroomService;
        this.kurentoService = kurentoService;
        this.courseService = courseService;
        this.classroomrepository = classroomrepository;
        this.purchaseStatusRepository = purchaseStatusRepository;
    }

    /**
     * POST  /classrooms : Create a new classroom.
     *
     * @param classroomDTO the classroom to create
     * @return the ResponseEntity with status 201 (Created) and with body the new classroom, or with status 400 (Bad Request) if the classroom has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/classrooms")
    @Timed
    public ResponseEntity<ClassroomDTO> createClassroom(@Valid @RequestBody ClassroomDTO classroomDTO) throws URISyntaxException {
        log.debug("REST request to updateClassroom Classroom : {}", classroomDTO);
        if (classroomDTO.getId() != null) {
            throw new BadRequestAlertException("A new classroom cannot already have an ID", ENTITY_NAME, "idexists");
        }
        classroomService.createNewClassroomDto(classroomDTO);
        return ResponseEntity.created(new URI("/api/classrooms/" + classroomDTO.getName()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, classroomDTO.getName()))
            .body(classroomDTO);
    }

    private void checkUserClassroomCount(ClassroomDTO dto) {
        //check classroom count based on user
        User creator = dto.getCreator();
        Optional<PurchaseStatus> currentPlan = purchaseStatusRepository.findCurrentPlan(creator.getId());
        if (!currentPlan.isPresent()) {
            throw new UserHasNoActivePlanException();
        }
        PurchaseStatus purchaseStatus = currentPlan.get();
        Integer allowedSessionCount = purchaseStatus.getSessionCount();
        long actualSessionCount = classroomrepository.countByCreator(creator.getId());
        if (actualSessionCount >= allowedSessionCount) {
            throw new UserMaxClassroomCountExceededException(allowedSessionCount, (int) actualSessionCount);
        }
    }

    private void checkUniqueClassroomName(String name) {
        //check classroom name to be unique
        long count = classroomService.countClassroomByName(name);
        if (count > 0) {
            throw new DuplicatedClassroomSessionUuidNameException();
        }
    }

    /**
     * PUT  /classrooms : Updates an existing classroom.
     *
     * @param classroom the classroom to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated classroom,
     * or with status 400 (Bad Request) if the classroom is not valid,
     * or with status 500 (Internal Server Error) if the classroom couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/classrooms")
    @Timed
    public ResponseEntity<ClassroomDTO> updateClassroom(@Valid @RequestBody ClassroomDTO classroom) throws URISyntaxException {
        log.debug("REST request to update Classroom : {}", classroom);
        if (classroom.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        User user = UserUtil.getCurrentUserEntity_(userRepository);
        classroom.setLastModifier(user);
        Optional<ClassroomDTO> result = classroomService.updateClassroom(classroom);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.get().getId().toString()))
            .body(result.get());
    }

    /**
     * GET  /classrooms : get all the classrooms.
     *
     * @param pageable  the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of classrooms in body
     */
    @GetMapping("/classrooms")
    @Timed
    public ResponseEntity<List<Classroom>> getAllClassrooms(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Classrooms");
        Page<Classroom> page;
        if (eagerload) {
            page = classroomService.findAllWithEagerRelationships(pageable);
        } else {
            page = classroomService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/classrooms?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/classrooms/locking/{status}/{className}")
    @Timed
    public void lockingOperation(@PathVariable boolean status, @PathVariable String className) {
        Classroom classroom = classroomService.findClassroomByName(className);
        classroom.setLock(status);
        classroom = classroomrepository.save(classroom);
        classroomStatusDataHolder.removeClassroom(classroom.getSessionUuidName());
    }

    @PostMapping("/classrooms/byuuid/{sessionId}")
    @Timed
    public ResponseEntity<ClassroomDTO> hasPermissionToLogInToClass(@PathVariable String sessionId) {
        log.debug("REST request to get Classroom : {}", sessionId);
        try {
            UUID uuid = UUID.fromString(sessionId);
            //If class with uuid is lock return to home page
            Optional<Classroom> tempClass = classroomService.findClassroomByUUID(uuid.toString());
            if (tempClass.isPresent()) {
                if (tempClass.get().getLock()) {
                    tempClass.get().setConnectionType("LockClass");
                    return ResponseUtil.wrapOrNotFound(
                        tempClass.map(ClassroomDTO::new));
                }
                if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
                    if (!tempClass.get().isGuestSession()) {
                        tempClass.get().setConnectionType("anonymousUser");
                    }
                }
                HttpHeaders headers = new HttpHeaders();
                headers.add("Server-Date", new Date().toInstant().toString());
                return ResponseUtil.wrapOrNotFound(
                    tempClass.map(ClassroomDTO::new), headers);
            } else {
                Optional<Classroom> classroom2 = null;
                return ResponseUtil.wrapOrNotFound(
                    classroom2.map(ClassroomDTO::new));
            }
        } catch (IllegalArgumentException exception) {
            //If class with name is lock return to home page
            Classroom tempClass2 = classroomService.findClassroomByName(sessionId);
            Optional<Classroom> temp = Optional.ofNullable(tempClass2);
            if (temp.isPresent()) {
                if (tempClass2.getLock()) {
                    temp.get().setConnectionType("LockClass");
                    return ResponseUtil.wrapOrNotFound(
                        temp.map(ClassroomDTO::new));
                } else {
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("Server-Date", new Date().toInstant().toString());
                    return ResponseUtil.wrapOrNotFound(
                        temp.map(ClassroomDTO::new), headers);
                }
            } else {
                if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
                    Optional<Classroom> classroom2 = Optional.of(new Classroom());
                    classroom2.get().setConnectionType("anonymousUser");
                    return ResponseUtil.wrapOrNotFound(
                        classroom2.map(ClassroomDTO::new));
                } else {
                    Iterator iterator = SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator();
                    while (iterator.hasNext()) {
                        if (iterator.next().toString().equals("ROLE_ADMIN")) {
                            Optional<Classroom> classroom = Optional.of(new Classroom());
                            return ResponseUtil.wrapOrNotFound(
                                classroom.map(ClassroomDTO::new));
                        }
                    }
                    Optional<Classroom> classroom2 = Optional.of(new Classroom());
                    classroom2.get().setConnectionType("anonymousUser");
                    return ResponseUtil.wrapOrNotFound(
                        classroom2.map(ClassroomDTO::new));
                }
            }
        }
    }


    @GetMapping("/classrooms/countClassStudents/{className}")
    @Timed
    public String getAllStudentCount(@PathVariable String className) {
        Classroom classroom = null;
        classroom = this.classroomService.findClassroomByName(className);
        Integer studentCount;
        if (classroom != null) {
            studentCount = classroom.getClassroomStudents().size();
        } else {
            studentCount = 0;
        }
        return studentCount.toString();
    }

    @GetMapping("/classrooms/checkroomname/{roomname}")
    @Timed
    public boolean checkRoomName(@PathVariable String roomname) {
        try {
            checkUniqueClassroomName(roomname);
            return false;
        } catch (DuplicatedClassroomSessionUuidNameException e) {
            return true;
        }
    }

    @GetMapping("/classrooms/user")
    @Timed
    public ResponseEntity<List<ClassroomDTO>> getCurrentUserClasses() {
        log.debug("REST request to get a page of Classrooms");
        NwmsUser user = UserUtil.getCurrentUser();
        List<ClassroomDTO> userClasses;
        userClasses = classroomService.findUserClasses(user.getId());
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("server-date", new Date().toInstant().toString())).body(userClasses);
    }


    @GetMapping("/classrooms/master")
    @Timed
    public ResponseEntity<List<Classroom>> getCurrentMasterClasses() {
        NwmsUser user = UserUtil.getCurrentUser();
        log.debug("REST request to get a page of Classrooms");
        List<Classroom> userClasses;
        userClasses = classroomService.findMasterClassrooms(user.getId());
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("server-date", new Date().toInstant().toString())).body(userClasses);
    }

    /**
     * GET  /classrooms/:id : get the "id" classroom.
     *
     * @param id the id of the classroom to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the classroom, or with status 404 (Not Found)
     */
    @GetMapping("/classrooms/{id}")
    @Timed
    public ResponseEntity<ClassroomDTO> getClassroom(@PathVariable Long id) {
        log.debug("REST request to get Classroom : {}", id);
        Optional<Classroom> classroom = classroomService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classroom.map(ClassroomDTO::new));
    }

    /**
     * DELETE  /classrooms/:id : delete the "id" classroom.
     *
     * @param id the id of the classroom to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/classrooms/{id}")
    @Timed
    public ResponseEntity<Void> deleteClassroom(@PathVariable Long id) {
        log.debug("REST request to delete Classroom : {}", id);
        classroomService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @DeleteMapping("/classrooms/delete-by-name/{name}")
    @Timed
    public ResponseEntity<Void> deleteClassroomByName(@PathVariable String name) {
        classroomService.deleteByName(name);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, name.toString())).build();
    }

    @GetMapping("/classrooms/bylogin/{login}")
    @Timed
    public ResponseEntity<UserDTO> getUser(@PathVariable String login) {

        log.debug("REST request to get User : {}", login);

        Optional<User> userOptional = this.userRepository.findOneByLogin(login);

        User newUser = new User();

        if (userOptional.isPresent()) {

            User user = userOptional.get();

            RegistryContainer registryContainer = this.kurentoService.findRegistryContainerByUserId(user.getId());

            if (registryContainer != null) {

                newUser.setActivated(true);
                newUser.setLogin(registryContainer.getSimagooToken());

                return ResponseUtil.wrapOrNotFound(Optional.of(newUser).map(UserDTO::new));

            }
        }

        newUser.setActivated(false);
        return ResponseUtil.wrapOrNotFound(Optional.of(newUser).map(UserDTO::new));
    }


    @GetMapping("/user/classrooms/{isGuestRoom}")
    public ResponseEntity<List<ClassroomListModel>> getAllUserClassrooms(@PathVariable boolean isGuestRoom) {
        log.debug("REST request to get a page of Courses");
        NwmsUser user = UserUtil.getCurrentUser();
        List<ClassroomListModel> page = classroomService.findByCreatorAndGStatus(user.getId(), isGuestRoom);
        return ResponseEntity.ok().body(page);
    }

    @PostMapping("/new/classroom")
    @Timed
    public ResponseEntity<NewClassroomResultDTO> createNewClassroom(@Valid @RequestBody NewClassroomDTO dto) {
        ClassroomDTO classroomDTO = new ClassroomDTO();
        classroomDTO.setName(dto.getName());
        classroomDTO.setStartDateTime(new Date());
        classroomDTO.setFinishDateTime(DateUtils.addYears(new Date(), 38));//todo this must be a config
        classroomDTO.setConnectionType("teacherroom");//todo this must be an enum of all the valid values
        classroomDTO.setResolution("Low");//todo this must be an enum of all the valid values
        classroomDTO.setFrameRate(18);//todo this must be a config
        classroomDTO.setGuestSession(true);
        classroomDTO.setGenerateRandomName(false);//todo this must be a RequestBody Param

        Long classroomId = classroomService.createNewClassroomDto(classroomDTO);
        NewClassroomResultDTO resultDTO = new NewClassroomResultDTO();
        resultDTO.setId(classroomId);
        resultDTO.setName(dto.getName());
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    /**
     * creates or updates the given Classroom. if the input has id, it will update it, otherwise, it will create a new Classroom.
     * If in the edit mode, the classroom does not belong to the current user, it will throw an exception.
     *
     * @param dto
     * @return
     */
    @PostMapping("/webinar/classroom/save")
    @Timed
    public ResponseEntity<WebinarClassroomResultDto> saveWebinarClassroom(@Valid @RequestBody WebinarClassroomDTO dto) {
        WebinarClassroomResultDto result = classroomService.saveWebinarClassroom(dto);
        return ResponseEntity.ok().body(result);
    }

    /**
     * returns a classroom with the given classroomId.
     * If the classroom does not belong to the current user, it will throw an exception.
     *
     * @param classroomId
     * @return
     */
    @GetMapping("/webinar/classroom/get/{classroomId}")
    @Timed
    public ResponseEntity<WebinarClassroomDTO> getWebinarClassroom(@PathVariable("classroomId") Long classroomId) {
        WebinarClassroomDTO dto = classroomService.getWebinarClassroomDTO(classroomId);
        return ResponseEntity.ok().body(dto);
    }


    /**
     * returns the classroom with the given classroomId.
     * If the classroom does not belong to the current user, it will throw an exception.
     *
     * @param classroomId
     * @return
     */
    @DeleteMapping("/webinar/classroom/delete/{classroomId}")
    @Timed
    public ResponseEntity<Boolean> deleteWebinarClassroom(@PathVariable("classroomId") Long classroomId) {
        classroomService.delete(classroomId);
        return ResponseEntity.ok().body(true);
    }

    /**
     * returns a page of user courses based on the input pageable
     *
     * @param pageable
     * @return
     */
    @PostMapping("/webinar/classroom/list/{courseId}")
    public ResponseEntity<List<WebinarClassroomDTO>> getWebinarClassrooms(@PathVariable("courseId") Long courseId, Pageable pageable) {
        Page<WebinarClassroomDTO> page = classroomService.findWebinarClassroomDTOsByCourseId(courseId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/webinar/classroom/list/" + courseId);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/classrooms/check-status/{clientId}/{name}")
    public ResponseEntity<ClassroomStatusDTO> getClassroomStatusDTO(@PathVariable("clientId") String clientId, @PathVariable("name") String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-Date", new Date().toInstant().toString());
        ClassroomStatusDTO dto = classroomService.getClassroomStatus(clientId, name, "");
        return ResponseEntity.ok().headers(headers).body(dto);
    }

    @PostMapping("/classrooms/check-status/{clientId}/{name}/{userToken}")
    public ResponseEntity<ClassroomStatusDTO> getClassroomStatusDTO(@PathVariable("clientId") String clientId, @PathVariable("name") String name, @PathVariable("userToken") String userToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-Date", new Date().toInstant().toString());
        ClassroomStatusDTO dto = classroomService.getClassroomStatus(clientId, name, userToken);
        return ResponseEntity.ok().headers(headers).body(dto);
    }

    @PostMapping("/classrooms/dynamic-check-status/{clientId}/{name}")
    public ResponseEntity<ClassroomStatusDTO> getClassroomStatusDTOForDynamicStudent(@PathVariable("clientId") String clientId, @PathVariable("name") String name, @Valid @RequestBody DynamicStudentDataVM vm) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-Date", new Date().toInstant().toString());
        ClassroomStatusDTO dto = classroomService.getClassroomStatus(clientId, name, vm);
        return ResponseEntity.ok().headers(headers).body(dto);
    }

    @PostMapping("/classroom/upload/{courseId}/{classroomId}")
    public ResponseEntity<List<Long>> uploadClassroomStudents(@PathVariable("courseId") Long courseId, @PathVariable("classroomId") Long classroomId, @RequestParam("file") MultipartFile file) throws Exception {
        List<Long> result = classroomService.uploadClassroomStudentsFile(courseId, classroomId, file.getOriginalFilename(), file.getContentType(), file.getInputStream());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-Date", new Date().toInstant().toString());
        return ResponseEntity.ok().headers(headers).body(result);
    }

    @PostMapping("/classroom/download/{classroomId}")
    @IgnoreLoggingValue
    public ResponseEntity<byte[]> downloadClassroomStudents(@PathVariable("classroomId") Long classroomId) throws Exception {
        ClassroomStudentFileDownloadVM vm = classroomService.getClassroomStudentFileDownloadVM(classroomId);
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String fileName = URLEncoder.encode(vm.getName(), "UTF-8");
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(mediaType);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        byte[] content = vm.getContent();
        return new ResponseEntity(content, headers, HttpStatus.OK);
    }

    @PostMapping("/classroom/sessions/report/{classroomIdentifier}")
    public ResponseEntity<List<RubruSessionListModel>> getClassroomSessions(@PathVariable("classroomIdentifier") String classroomIdentifier) {
        //todo Hossein : return a dto instead of the entity
        Long classroomId = null;
        try {
            classroomId = Long.valueOf(classroomIdentifier);
        } catch (NumberFormatException e) {
        }
        List<RubruSessionListModel> result;
        if (classroomId != null) {
            result = classroomService.getClassroomSessionsReportData(classroomId);
        } else {
            result = classroomService.getClassroomSessionsReportData(classroomIdentifier);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-Date", new Date().toInstant().toString());
        return ResponseEntity.ok().headers(headers).body(result);
    }

    @PostMapping("/classroom/download/session/participants/report/{rubruSessionId}")
    @IgnoreLoggingValue
    public ResponseEntity<byte[]> downloadClassroomSessionParticipantsReport(@PathVariable("rubruSessionId") Long rubruSessionId) {
        try {
            ClassroomStudentFileDownloadVM vm = classroomService.getRubruSessionParticipantsReportVM(rubruSessionId);
            HttpHeaders headers = new HttpHeaders();
            MediaType mediaType = MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            String fileName = URLEncoder.encode(vm.getName(), "UTF-8");
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentType(mediaType);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            byte[] content = vm.getContent();

            return new ResponseEntity(content, headers, HttpStatus.OK);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/classroom/get/session/participants/report/{rubruSessionId}")
    public ResponseEntity<List<RubruSessionParticipantsReportVM>> getClassroomSessionParticipantsReport(@PathVariable("rubruSessionId") Long rubruSessionId) {
        List<RubruSessionParticipantsReportVM> result = classroomService.getClassroomSessionParticipantsReport(rubruSessionId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-Date", new Date().toInstant().toString());
        return ResponseEntity.ok().headers(headers).body(result);

    }

    @PostMapping("/classroom/update/settings")
    public ResponseEntity<Integer> updateClassroomSettings(@Valid @RequestBody ClassroomSettingsVM vm){
        int updatedSettingsCount = classroomService.updateClassroomSettings(vm);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-Date", new Date().toInstant().toString());
        return ResponseEntity.ok().headers(headers).body(updatedSettingsCount);
    }

}

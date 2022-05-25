package com.pmp.nwms.service.impl;

import com.pmp.nwms.NwmsConfig;
import com.pmp.nwms.config.Constants;
import com.pmp.nwms.domain.*;
import com.pmp.nwms.domain.enums.ClassroomStudentAuthority;
import com.pmp.nwms.model.FilePathInfoModel;
import com.pmp.nwms.repository.*;
import com.pmp.nwms.security.AuthoritiesConstants;
import com.pmp.nwms.security.NwmsUser;
import com.pmp.nwms.security.jwt.TokenProvider;
import com.pmp.nwms.service.ClassroomService;
import com.pmp.nwms.service.OrganizationLevelService;
import com.pmp.nwms.service.dto.*;
import com.pmp.nwms.service.enums.ClassroomEntranceType;
import com.pmp.nwms.service.enums.ClassroomStatusResultCode;
import com.pmp.nwms.service.listmodel.ClassroomListModel;
import com.pmp.nwms.service.listmodel.RubruSessionListModel;
import com.pmp.nwms.service.listmodel.UserClassroomDataListModel;
import com.pmp.nwms.service.listmodel.UserClassroomListModel;
import com.pmp.nwms.service.util.ClassroomStatusDataHolder;
import com.pmp.nwms.service.util.EndSessionTimerHolder;
import com.pmp.nwms.util.*;
import com.pmp.nwms.util.date.DateUtil;
import com.pmp.nwms.util.excel.impl.ClassroomStudentExcelFileStructureValidator;
import com.pmp.nwms.util.excel.impl.ClassroomStudentInfoExcelExporter;
import com.pmp.nwms.util.excel.impl.RubruSessionParticipantsReportExcelExporter;
import com.pmp.nwms.web.rest.errors.*;
import com.pmp.nwms.web.rest.vm.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.*;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Classroom.
 */
@Service("classroomService")
@Transactional
public class ClassroomServiceImpl implements ClassroomService {

    private final Logger log = LoggerFactory.getLogger(ClassroomServiceImpl.class);

    private final ClassroomRepository classroomRepository;
    private final ClassroomStudentRepository classroomStudentRepository;
    private final UserRepository userRepository;
    private final CacheManager cacheManager;
    private final CourseRepository courseRepository;
    private final PurchaseStatusRepository purchaseStatusRepository;
    private final RubruSessionRepository rubruSessionRepository;
    private final RubruSessionParticipantRepository rubruSessionParticipantRepository;
    private final MessageSource messageSource;
    private final ClassroomStudentExcelFileStructureValidator classroomStudentExcelFileStructureValidator;
    private final NwmsConfig nwmsConfig;

    private final OrganizationLevelService organizationLevelService;

    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    private final ClassroomStudentInfoExcelExporter classroomStudentInfoExcelExporter;
    private final RubruSessionParticipantsReportExcelExporter rubruSessionParticipantsReportExcelExporter;
    private final ClassroomBlockedClientRepository classroomBlockedClientRepository;
    private final ClassroomStatusDataHolder classroomStatusDataHolder;
    private final EndSessionTimerHolder endSessionTimerHolder;

    private final ClassroomFileRepository classroomFileRepository;
    private final ClassroomFileSlideRepository classroomFileSlideRepository;
    private final JackrabbitRepoUtil jackrabbitRepoUtil;
    private final DeletedClassroomStudentRepository deletedClassroomStudentRepository;
    private final DeletedClassroomRepository deletedClassroomRepository;
    private final DeletedUserRepository deletedUserRepository;
    private final DeletedCourseStudentRepository deletedCourseStudentRepository;

    private final TokenProvider tokenProvider;
    private final ClassroomRecordingInfoRepository classroomRecordingInfoRepository;

    public ClassroomServiceImpl(ClassroomRepository classroomRepository,
                                CacheManager cacheManager,
                                ClassroomStudentRepository classroomStudentRepository,
                                UserRepository userRepository,
                                CourseRepository courseRepository,
                                PurchaseStatusRepository purchaseStatusRepository,
                                RubruSessionParticipantRepository rubruSessionParticipantRepository,
                                RubruSessionRepository rubruSessionRepository,
                                MessageSource messageSource,
                                ClassroomStudentExcelFileStructureValidator classroomStudentExcelFileStructureValidator,
                                OrganizationLevelService organizationLevelService,
                                AuthorityRepository authorityRepository,
                                PasswordEncoder passwordEncoder,
                                ClassroomStudentInfoExcelExporter classroomStudentInfoExcelExporter,
                                RubruSessionParticipantsReportExcelExporter rubruSessionParticipantsReportExcelExporter,
                                NwmsConfig nwmsConfig,
                                ClassroomBlockedClientRepository classroomBlockedClientRepository,
                                ClassroomStatusDataHolder classroomStatusDataHolder,
                                EndSessionTimerHolder endSessionTimerHolder,
                                ClassroomFileRepository classroomFileRepository,
                                ClassroomFileSlideRepository classroomFileSlideRepository,
                                JackrabbitRepoUtil jackrabbitRepoUtil,
                                DeletedClassroomRepository deletedClassroomRepository,
                                DeletedClassroomStudentRepository deletedClassroomStudentRepository,
                                DeletedUserRepository deletedUserRepository,
                                TokenProvider tokenProvider,
                                ClassroomRecordingInfoRepository classroomRecordingInfoRepository,
                                DeletedCourseStudentRepository deletedCourseStudentRepository
    ) {
        this.classroomRepository = classroomRepository;
        this.cacheManager = cacheManager;
        this.classroomStudentRepository = classroomStudentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.purchaseStatusRepository = purchaseStatusRepository;
        this.rubruSessionParticipantRepository = rubruSessionParticipantRepository;
        this.rubruSessionRepository = rubruSessionRepository;
        this.messageSource = messageSource;
        this.classroomStudentExcelFileStructureValidator = classroomStudentExcelFileStructureValidator;
        this.organizationLevelService = organizationLevelService;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.classroomStudentInfoExcelExporter = classroomStudentInfoExcelExporter;
        this.rubruSessionParticipantsReportExcelExporter = rubruSessionParticipantsReportExcelExporter;
        this.nwmsConfig = nwmsConfig;
        this.classroomBlockedClientRepository = classroomBlockedClientRepository;
        this.classroomStatusDataHolder = classroomStatusDataHolder;
        this.endSessionTimerHolder = endSessionTimerHolder;
        this.classroomFileRepository = classroomFileRepository;
        this.classroomFileSlideRepository = classroomFileSlideRepository;
        this.jackrabbitRepoUtil = jackrabbitRepoUtil;
        this.deletedClassroomRepository = deletedClassroomRepository;
        this.deletedClassroomStudentRepository = deletedClassroomStudentRepository;
        this.deletedUserRepository = deletedUserRepository;
        this.tokenProvider = tokenProvider;
        this.classroomRecordingInfoRepository = classroomRecordingInfoRepository;
        this.deletedCourseStudentRepository = deletedCourseStudentRepository;
    }


    @Override
    public ClassroomDTO save(ClassroomDTO classroomDto) {
        log.debug("Request to updateClassroom Classroom : {}", classroomDto);
        Classroom classroom = new Classroom();
        if (classroomDto.getId() != null) {
            classroom.setId(classroomDto.getId());
        }
        classroom.setName(classroomDto.getName());

        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        classroom.setSessionUuidName(randomUUIDString);
        classroom.setGuestSession(classroomDto.isGuestSession());

        classroom.setGuestSessionReqPass(classroomDto.isGuestSessionReqPass());

        if (classroomDto.isGuestSessionReqPass()) {
            classroom.setGuestPassword(classroomDto.getGuestPassword());
        } else {
            classroom.setGuestPassword(null);
        }

        classroom.setStartDateTime(classroomDto.getStartDateTime());
        classroom.setFinishDateTime(classroomDto.getFinishDateTime());

        classroom.setConnectionType(classroomDto.getConnectionType());
        if (classroomDto.getResolution() == null) {
            classroomDto.setResolution("Low");
        }
        classroom.setResolution(classroomDto.getResolution());
        classroom.setFrameRate(classroomDto.getFrameRate());
        classroom.setCourse(classroomDto.getCourse());
        classroom.setMaster(classroomDto.getMaster());
        classroom.setUseEnterToken(classroomDto.isUseEnterToken());
        classroom.setCreator(classroomDto.getCreator());
        classroom.setLastModifier(classroomDto.getLastModifier());
        classroom.setIsGuestWithSubscriberRole(classroomDto.getIsGuestWithSubscriberRole());
        classroom.setHideGlobalChat(classroomDto.getHideGlobalChat());
        classroom.setHidePrivateChat(classroomDto.getHidePrivateChat());
        classroom.setHideParticipantsList(classroomDto.getHideParticipantsList());
        classroom.setDisableFileTransfer(classroomDto.getDisableFileTransfer());
        classroom.setHideSoundSensitive(classroomDto.getHideSoundSensitive());
        classroom.setHidePublishPermit(classroomDto.getHidePublishPermit());
        classroom.setEnableSubscriberDirectEnter(classroomDto.getEnableSubscriberDirectEnter());
        classroom.setPublisherMustEnterFirst(classroomDto.getPublisherMustEnterFirst());
        validateMaxUserCountBuPurchaseStatus(classroomDto.getMaxUserCount(), classroomDto.getCreator().getId());
        classroom.setMaxUserCount(classroomDto.getMaxUserCount());
        classroom.setLock(false);
        classroom.setSignalSessionEnd(ObjectUtil.getBooleanValue(classroomDto.getSignalSessionEnd()));
        classroom.setReturnUrl(classroomDto.getReturnUrl());
        checkUniqueClassroomName(classroom.getName(), classroom.getSessionUuidName(), classroom.getId(), classroom.getCourse().getId());
        classroom = classroomRepository.save(classroom);
        ClassroomDTO classroomDTO = new ClassroomDTO(classroom);
        classroomStatusDataHolder.removeClassroom(classroom.getSessionUuidName());
        endSessionTimerHolder.updateSessionTimer(classroom.getSessionUuidName(), classroom.getFinishDateTime());
        return classroomDTO;

    }

    @Override
    @Transactional
    public Optional<ClassroomDTO> updateClassroom(ClassroomDTO classroomDTO) {
        return Optional.of(classroomRepository
            .findById(classroomDTO.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(classroom -> {
                if (classroom.isSessionActive() && !classroomDTO.getSessionUuidName().equals(classroom.getSessionUuidName())) {
                    throw new UnableToDeleteClassroomDueToActiveSessionException();
                }
                classroom.setName(classroomDTO.getName());
                classroom.setSessionUuidName(classroomDTO.getSessionUuidName());
                classroom.setGuestSession(classroomDTO.isGuestSession());
                classroom.setUseEnterToken(classroomDTO.isUseEnterToken());
                classroom.setGuestSessionReqPass(classroomDTO.isGuestSessionReqPass());

                if (classroomDTO.isGuestSessionReqPass()) {
                    classroom.setGuestPassword(classroomDTO.getGuestPassword());
                } else {
                    classroom.setGuestPassword(null);
                }

                classroom.setStartDateTime(classroomDTO.getStartDateTime());
                classroom.setFinishDateTime(classroomDTO.getFinishDateTime());

                classroom.setConnectionType(classroomDTO.getConnectionType());
                classroom.setIsGuestWithSubscriberRole(classroomDTO.getIsGuestWithSubscriberRole());
                classroom.setResolution(classroomDTO.getResolution());
                classroom.setFrameRate(classroomDTO.getFrameRate());

                classroom.setMaster(classroomDTO.getMaster());
                classroom.setCreator(classroomDTO.getCreator());
                classroom.setLastModifier(classroomDTO.getLastModifier());

                classroom.setHideGlobalChat(classroomDTO.getHideGlobalChat());
                classroom.setHidePrivateChat(classroomDTO.getHidePrivateChat());
                classroom.setHideParticipantsList(classroomDTO.getHideParticipantsList());
                classroom.setDisableFileTransfer(classroomDTO.getDisableFileTransfer());
                classroom.setHideSoundSensitive(classroomDTO.getHideSoundSensitive());
                classroom.setHidePublishPermit(classroomDTO.getHidePublishPermit());
                classroom.setEnableSubscriberDirectEnter(classroomDTO.getEnableSubscriberDirectEnter());
                classroom.setPublisherMustEnterFirst(classroomDTO.getPublisherMustEnterFirst());
                validateMaxUserCountBuPurchaseStatus(classroomDTO.getMaxUserCount(), classroom.getCreator().getId());
                classroom.setMaxUserCount(classroomDTO.getMaxUserCount());
                classroom.setLock(classroomDTO.getLock());//todo?
                classroom.setSignalSessionEnd(ObjectUtil.getBooleanValue(classroomDTO.getSignalSessionEnd()));
                classroom.setReturnUrl(classroomDTO.getReturnUrl());
                checkUniqueClassroomName(classroom.getName(), classroom.getSessionUuidName(), classroom.getId(), classroom.getCourse().getId());
                Classroom classroomfinal = classroomRepository.save(classroom);
                classroomStatusDataHolder.removeClassroom(classroomfinal.getSessionUuidName());
                endSessionTimerHolder.updateSessionTimer(classroom.getSessionUuidName(), classroom.getFinishDateTime());
                return classroomfinal;
            }).map(ClassroomDTO::new);
    }

    /**
     * Get all the classrooms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Classroom> findAll(Pageable pageable) {
        log.debug("Request to get all Classrooms");
        return classroomRepository.findAll(pageable);
    }

    /**
     * Get all the Classroom with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Classroom> findAllWithEagerRelationships(Pageable pageable) {
        return classroomRepository.findAllWithEagerRelationships(pageable);
    }


    /**
     * Get one classroom by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Classroom> findOne(Long id) {
        log.debug("Request to get Classroom : {}", id);
        classroomRepository.findUserClassroms(id);
        return classroomRepository.findOneWithEagerRelationships(id);
    }


    @Transactional(readOnly = true)
    public List<ClassroomDTO> findUserClasses(Long id) {
        log.debug("Request to get all Current User Classes");
        List<Classroom> classrooms = classroomRepository.findUserClassroms(id);
        List<ClassroomDTO> classroomDTOS = new ArrayList<>();
        for (Classroom classroom : classrooms) {
            ClassroomDTO classroomDTO = new ClassroomDTO(classroom);
            classroomDTOS.add(classroomDTO);
        }
        return classroomDTOS;
    }

    @Transactional(readOnly = true)
    public List<Classroom> findMasterClassrooms(Long id) {
        log.debug("Request to get all Current Master Classes");
        return classroomRepository.findMasterClassrooms(id);
    }

    @Override
    public List<ClassroomListModel> findByCreatorAndGStatus(Long userId, boolean isGuestRoom) {
        return classroomRepository.findByCreatorAndGStatus(userId, isGuestRoom);
    }

    @Transactional(readOnly = true)
    public Classroom findClassroomByName(String name) {
        log.debug("Request to get  Current  Classe");
        Optional<Classroom> classroomByName = classroomRepository.findClassroomByName(name);
        return classroomByName.orElse(null);
    }

    @Override
    public Optional<Classroom> findClassroomByUUID(String uuid) {
        return classroomRepository.findClassroomByUUID(uuid);
    }

    /**
     * Delete the classroom by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Classroom : {}", id);

        ClassroomUtil.checkClassroom(id, classroomRepository);

        NwmsUser user = UserUtil.getCurrentUser();

        long count = classroomRepository.countByIdAndCreator(id, user.getId());
        if (count <= 0) {
            throw new NoRowDeletedException("classroom", id, user.getId());
        }

        Classroom classroom = classroomRepository.findById(id).get();
        if (classroom.isSessionActive()) {
            throw new UnableToDeleteClassroomDueToActiveSessionException();
        }

        FullDeleteUtil.moveClassroomStudentsByClassroomId(id, classroomStudentRepository, deletedClassroomStudentRepository);
        classroomStudentRepository.deleteByClassroomId(id);
        moveClassroom(classroom);
        classroomRepository.deleteClassroomById(id);
        classroomStatusDataHolder.removeClassroomById(id);
    }

    @Override
    public void deleteByName(String name) {
        ClassroomCheckStatusDTO classroom = classroomStatusDataHolder.findByUuid(name);
        if (classroom.isSessionActive()) {
            throw new UnableToDeleteClassroomDueToActiveSessionException();
        }
        classroomRepository.deleteClassroomByName(name);
        classroomStatusDataHolder.removeClassroom(name);
    }

    @Transactional(readOnly = true)
    public Classroom findClassByUserIdAndDate(Long userId) {
        Classroom classroom = classroomRepository.findClassByUserIdAndDate(userId, new Date());
        Date currentDate = new Date();
        if (classroom != null && classroom.getStartDateTime().before(currentDate) && classroom.getFinishDateTime().after(currentDate)) {
            return classroom;
        }

        return null;
    }


    @Override
    public WebinarClassroomResultDto saveWebinarClassroom(WebinarClassroomDTO dto) {
        boolean outerManage = ObjectUtil.getBooleanValue(dto.getOuterManage());
        if(outerManage && !UserUtil.hasAuthority(AuthoritiesConstants.OUTER_MANAGER)){
            throw new AccessDeniedException("AccessDenied in setting outerManage to true.");
        }

        validateWebinarClassroom(dto);

        Classroom entity;
        User user = UserUtil.getCurrentUserEntity_(userRepository);
        if (dto.getId() == null) {
            ClassroomUtil.checkCourse(dto.getCourseId(), courseRepository);
            entity = new Classroom();
            entity.setCreator(user);
            entity.setMaster(user);//todo ask Mr Cheraghi

            Course course = courseRepository.getOne(dto.getCourseId());
            entity.setCourse(course);
            entity.setGenerateRandomName(!dto.getGenerateRandomName());
        } else {
            ClassroomUtil.checkClassroom(dto.getId(), classroomRepository);
            entity = classroomRepository.findById(dto.getId()).get();
        }
        entity.setLastModifier(user);

        String oldSessionUuidName = entity.getSessionUuidName();

        if (!dto.getGenerateRandomName().equals(entity.getGenerateRandomName())) {
            entity.setGenerateRandomName(dto.getGenerateRandomName());
            if (dto.getGenerateRandomName()) {
                UUID uuid = UUID.randomUUID();
                String randomUUIDString = uuid.toString();
                entity.setSessionUuidName(randomUUIDString);
            } else {
                entity.setSessionUuidName(dto.getSessionUuidName());
            }
        } else if (!dto.getGenerateRandomName()) {
            entity.setSessionUuidName(dto.getSessionUuidName());
        }

        if (entity.isSessionActive() && oldSessionUuidName != null && !oldSessionUuidName.equals(entity.getSessionUuidName())) {
            throw new UnableToDeleteClassroomDueToActiveSessionException();
        }

        entity.setName(dto.getName());
        entity.setGuestSession(dto.isGuestSession());
        entity.setIsGuestWithSubscriberRole(dto.isGuestWithSubscriberRole());
        entity.setUseEnterToken(dto.getUseEnterToken());
        entity.setStartDateTime(dto.getStartDateTime());
        entity.setFinishDateTime(dto.getFinishDateTime());
        entity.setConnectionType("teacherroom");
        entity.setResolution("Low");
        entity.setFrameRate(18);

        entity.setHideGlobalChat(dto.getHideGlobalChat());
        entity.setHidePrivateChat(dto.getHidePrivateChat());
        entity.setHideParticipantsList(dto.getHideParticipantsList());
        entity.setDisableFileTransfer(dto.getDisableFileTransfer());
        entity.setHideSoundSensitive(dto.getHideSoundSensitive());
        entity.setHidePublishPermit(dto.getHidePublishPermit());
        entity.setEnableSubscriberDirectEnter(dto.getEnableSubscriberDirectEnter());
        entity.setPublisherMustEnterFirst(dto.getPublisherMustEnterFirst());
        if (dto.getLock() != null) {
            entity.setLock(dto.getLock());
        }
        validateMaxUserCountBuPurchaseStatus(dto.getMaxUserCount(), user.getId());
        entity.setMaxUserCount(dto.getMaxUserCount());
        entity.setHideScreen(ObjectUtil.getBooleanValue(dto.getHideScreen()));
        entity.setHideWhiteboard(ObjectUtil.getBooleanValue(dto.getHideWhiteboard()));
        entity.setHideSlide(ObjectUtil.getBooleanValue(dto.getHideSlide()));
        entity.setRecordingMode(dto.getRecordingMode());
        entity.setModeratorAutoLogin(dto.getModeratorAutoLogin());
        entity.setSecretKey(dto.getSecretKey());
        entity.setSignalSessionEnd(ObjectUtil.getBooleanValue(dto.getSignalSessionEnd()));
        entity.setReturnUrl(dto.getReturnUrl());
        entity.setOuterManage(outerManage);
        entity.setNonManagerEnterSoundOff(ObjectUtil.getBooleanValue(dto.getNonManagerEnterSoundOff()));
        entity.setNonManagerEnterVideoOff(ObjectUtil.getBooleanValue(dto.getNonManagerEnterVideoOff()));


        checkUniqueClassroomName(dto.getName(), entity.getSessionUuidName(), dto.getId(), dto.getCourseId());

        entity = classroomRepository.save(entity);

        classroomStatusDataHolder.removeClassroom(entity.getSessionUuidName());
        endSessionTimerHolder.updateSessionTimer(entity.getSessionUuidName(), entity.getFinishDateTime());
        return new WebinarClassroomResultDto(entity.getId(), entity.getSessionUuidName());
    }

    private void validateMaxUserCountBuPurchaseStatus(Integer maxUserCount, Long userId) {
        if (maxUserCount != null && maxUserCount > 0) {
            Optional<PurchaseStatus> currentPlan = purchaseStatusRepository.findCurrentPlan(userId);
            if (currentPlan.isPresent()) {
                PurchaseStatus purchaseStatus = currentPlan.get();
                if (purchaseStatus.getConcurUsers() != null && purchaseStatus.getConcurUsers() > 0) {
                    if (maxUserCount > purchaseStatus.getConcurUsers()) {
                        throw new InvalidClassroomMaxUserCountException(maxUserCount, purchaseStatus.getConcurUsers());
                    }
                }
            } else {
                throw new UserHasNoActivePlanException();
            }
        }
    }

    private void validateWebinarClassroom(WebinarClassroomDTO dto) {
        if (!dto.getName().matches(Constants.CLASSROOM_NAME_WITH_SPACE_REGEX)) {//todo ask Mr Cheraghi about this
            throw new ClassroomNameInvalidFormatException();
        }
        Long id = dto.getId();
        if (id == null) {
            id = -1L;
        }
        if (!dto.getGenerateRandomName()) {
            if (dto.getSessionUuidName() == null || dto.getSessionUuidName().isEmpty()) {
                throw new ValidationException("because generateRandomName is false, sessionUuidName is required");
            }
            if (!dto.getSessionUuidName().matches(Constants.CLASSROOM_NAME_WITH_SPACE_REGEX)) {
                throw new ClassroomNameInvalidFormatException();
            }
            checkUniqueClassroomName(dto.getName(), dto.getSessionUuidName(), id, dto.getCourseId());
        }
        boolean teacherPan = courseRepository.findTeacherPanValueById(dto.getCourseId());
        if (teacherPan) {
            throw new NotWebinarCourseException();
        }
        if (classroomRepository.countByNameAndCourseAndNotForId(dto.getName(), dto.getCourseId(), id) > 0) {
            throw new DuplicatedClassroomNameException();
        }
    }

    private void checkUniqueClassroomName(String name, String sessionUuidName, Long id, Long courseId) {
        if (id == null) {
            id = -1L;
        }
        long count = classroomRepository.countBySessionUuidNameAndNotForId(sessionUuidName, id);
        if (count > 0) {
            throw new DuplicatedClassroomSessionUuidNameException();
        }

        count = classroomRepository.countByNameAndCourseAndNotForId(name, courseId, id);
        if (count > 0) {
            throw new DuplicatedClassroomNameException();
        }


    }

    @Override
    public WebinarClassroomDTO getWebinarClassroomDTO(Long classroomId) {
        ClassroomUtil.checkClassroom(classroomId, classroomRepository);
        Classroom classroom = classroomRepository.findById(classroomId).get();
        WebinarClassroomDTO dto = makeWebinarClassroomDTO(classroom);
        return dto;
    }

    private WebinarClassroomDTO makeWebinarClassroomDTO(Classroom classroom) {
        WebinarClassroomDTO dto = new WebinarClassroomDTO();
        dto.setId(classroom.getId());
        dto.setName(classroom.getName());
        dto.setSessionUuidName(classroom.getSessionUuidName());
        dto.setGuestSession(ObjectUtil.getBooleanValue(classroom.isGuestSession()));
        dto.setGuestWithSubscriberRole(ObjectUtil.getBooleanValue(classroom.getIsGuestWithSubscriberRole()));
        dto.setUseEnterToken(ObjectUtil.getBooleanValue(classroom.getUseEnterToken()));
        dto.setStartDateTime(classroom.getStartDateTime());
        dto.setFinishDateTime(classroom.getFinishDateTime());
        dto.setCourseId(classroom.getCourse().getId());
        dto.setHideGlobalChat(ObjectUtil.getBooleanValue(classroom.getHideGlobalChat()));
        dto.setHidePrivateChat(ObjectUtil.getBooleanValue(classroom.getHidePrivateChat()));
        dto.setHideParticipantsList(ObjectUtil.getBooleanValue(classroom.getHideParticipantsList()));
        dto.setDisableFileTransfer(ObjectUtil.getBooleanValue(classroom.getDisableFileTransfer()));
        dto.setHideSoundSensitive(ObjectUtil.getBooleanValue(classroom.getHideSoundSensitive()));
        dto.setHidePublishPermit(ObjectUtil.getBooleanValue(classroom.getHidePublishPermit()));
        dto.setEnableSubscriberDirectEnter(ObjectUtil.getBooleanValue(classroom.getEnableSubscriberDirectEnter()));
        dto.setPublisherMustEnterFirst(ObjectUtil.getBooleanValue(classroom.getPublisherMustEnterFirst()));
        dto.setLock(ObjectUtil.getBooleanValue(classroom.getLock()));
        dto.setMaxUserCount(classroom.getMaxUserCount());
        dto.setGenerateRandomName(ObjectUtil.getBooleanValue(classroom.getGenerateRandomName()));
        dto.setHideScreen(ObjectUtil.getBooleanValue(classroom.getHideScreen()));
        dto.setHideWhiteboard(ObjectUtil.getBooleanValue(classroom.getHideWhiteboard()));
        dto.setHideSlide(ObjectUtil.getBooleanValue(classroom.getHideSlide()));
        dto.setRecordingMode(classroom.getRecordingMode());
        dto.setModeratorAutoLogin(classroom.getModeratorAutoLogin());
        dto.setSecretKey(classroom.getSecretKey());
        dto.setSignalSessionEnd(classroom.isSignalSessionEnd());
        dto.setReturnUrl(classroom.getReturnUrl());
        dto.setCreatorId(classroom.getCreator().getId());
        dto.setOuterManage(classroom.isOuterManage());
        dto.setNonManagerEnterSoundOff(classroom.isNonManagerEnterSoundOff());
        dto.setNonManagerEnterVideoOff(classroom.isNonManagerEnterVideoOff());
        return dto;
    }

    @Override
    public Page<WebinarClassroomDTO> findWebinarClassroomDTOsByCourseId(Long courseId, Pageable pageable) {
        ClassroomUtil.checkCourse(courseId, courseRepository);

        if (pageable.getPageSize() == 20 && pageable.getPageNumber() == 0) {
            pageable = Pageable.unpaged();
        }

        Page<Classroom> all = classroomRepository.findAllByCourseId(courseId, pageable);

        List<WebinarClassroomDTO> dtos = new ArrayList<>();
        for (Classroom classroom : all) {
            dtos.add(makeWebinarClassroomDTO(classroom));
        }

        PurchaseStatusUtil.setSpecialLinks(dtos, purchaseStatusRepository);

        return new PageImpl<>(dtos, all.getPageable(), all.getTotalElements());
    }

    @Override
    public ClassroomStatusDTO getClassroomStatus(String clientId, String name, String userToken) {
        ClassroomStatusDTO dto = new ClassroomStatusDTO();
        boolean isWebinar = false;
        ClassroomCheckStatusDTO classroomByName;
        Locale locale = new Locale("fa");//todo
        ClassroomStatusResultCode statusResultCode;
        try {
            classroomByName = classroomStatusDataHolder.findByUuid(name);
            isWebinar = !classroomByName.isTeacherPan();
            statusResultCode = validateClassroomStatusResultCode(dto, isWebinar, classroomByName, userToken, clientId);

        } catch (ClassroomNotFoundByNameException e) {
            statusResultCode = ClassroomStatusResultCode.ClassroomNotFound;
        }

        dto.setResultCode(statusResultCode);
        Object[] objects = null;
        if (statusResultCode.equals(ClassroomStatusResultCode.ClassroomMaxUserCountExceeded)) {
            objects = new Object[1];
            objects[0] = dto.getClassroomName();
        }
        dto.setDisplayMessage(messageSource.getMessage(statusResultCode.getMessageKey(), objects, locale));

        if (!statusResultCode.equals(ClassroomStatusResultCode.Success)) {
            dto.setAuthToken(null);
        }

        return dto;
    }

    @Override
    public ClassroomStatusDTO getClassroomStatus(String clientId, String name, DynamicStudentDataVM vm) {
        ClassroomStatusDTO classroomStatus = getClassroomStatus(clientId, name, vm.getToken());
        if (classroomStatus.getEntranceType().equals(ClassroomEntranceType.EnterWithSoundAndVideo) || classroomStatus.getEntranceType().equals(ClassroomEntranceType.EnterWithoutSoundAndVideo)) {
            Locale locale = new Locale("fa");//todo
            ClassroomCheckStatusDTO classroom = classroomStatusDataHolder.findByUuid(name);
            if (!classroom.isGuestSession() || !ObjectUtil.getBooleanValue(classroom.getUseEnterToken())) {
                classroomStatus.setResultCode(ClassroomStatusResultCode.InvalidClassroomSettings);
                classroomStatus.setDisplayMessage(messageSource.getMessage(ClassroomStatusResultCode.InvalidClassroomSettings.getMessageKey(), null, locale));
            } else if (!classroomStatus.getDynamicStudent()) {
                classroomStatus.setResultCode(ClassroomStatusResultCode.InvalidClassroomStudentSettings);
                classroomStatus.setDisplayMessage(messageSource.getMessage(ClassroomStatusResultCode.InvalidClassroomStudentSettings.getMessageKey(), null, locale));
            } else {

                String checkString = vm.toString() + classroom.getSecretKey();
                String expectedChecksum = DigestUtils.sha1Hex(checkString);
                if (!vm.getChecksum().equals(expectedChecksum)) {
                    classroomStatus.setResultCode(ClassroomStatusResultCode.InvalidChecksumInput);
                    classroomStatus.setDisplayMessage(messageSource.getMessage(ClassroomStatusResultCode.InvalidChecksumInput.getMessageKey(), null, locale));
                    classroomStatus.setUserFullName(null);
                } else {
                    classroomStatus.setUserFullName(vm.getFullName());
                    classroomStatus.setParticipantKey(vm.getParticipantKey());
                }
            }
        }
        return classroomStatus;
    }

    @Override
    public List<Long> uploadClassroomStudentsFile(Long courseId, Long classroomId, String fileName, String fileContentType, InputStream fileInputStream) {
        List<Long> userIds = new ArrayList<>();
        ClassroomUtil.checkCourseAndClassroom(courseId, classroomId, classroomRepository);
        Course course = courseRepository.getOne(courseId);
        NwmsUser user = UserUtil.getCurrentUser();
        Classroom classroom = classroomRepository.findById(classroomId).get();
        Sheet dataSheet = validateFileStructure(fileName, fileContentType, fileInputStream);
        List<ClassroomStudentFileVM> users = convertFileToModel(dataSheet, course, classroom);
        validateUsersInfo(users);
        List<User> savedUsers = saveUsers(users);
        for (User savedUser : savedUsers) {
            userIds.add(savedUser.getId());
        }
        assignUsersToCourse(savedUsers, course);
        assignUsersToClassroom(savedUsers, users, classroom);

        return userIds;
    }

    @Override
    public ClassroomStudentFileDownloadVM getClassroomStudentFileDownloadVM(Long classroomId) {
        ClassroomUtil.checkClassroom(classroomId, classroomRepository);

        List<ClassroomStudentInfoVM> data = classroomRepository.getClassroomStudentInfoVMs(classroomId);


        String appWebUrl = nwmsConfig.getAppWebUrl();
        NwmsUser user = UserUtil.getCurrentUser();
        PurchaseStatus purchaseStatus = classroomStatusDataHolder.findPurchaseStatusByUser(user.getId());
        if (purchaseStatus != null && !purchaseStatus.getId().equals(-1L)) {
            if (purchaseStatus.getAppUrl() != null && !purchaseStatus.getAppUrl().isEmpty()) {
                appWebUrl = purchaseStatus.getAppUrl();
            }
        }


        byte[] export = classroomStudentInfoExcelExporter.export(data, false, appWebUrl);
        ClassroomStudentFileDownloadVM vm = new ClassroomStudentFileDownloadVM();
        Classroom classroom = classroomRepository.findById(classroomId).get();
        vm.setName(classroom.getName() + ".xlsx");
        vm.setContent(export);
        return vm;
    }

    @Override
    public List<RubruSessionListModel> getClassroomSessionsReportData(Long classroomId) {
        ClassroomUtil.checkClassroom(classroomId, classroomRepository);
        return rubruSessionRepository.getRubruSessionListModelsByClassroomId(classroomId);
    }

    @Override
    public List<RubruSessionListModel> getClassroomSessionsReportData(String sessionName) {
        ClassroomCheckStatusDTO classroom = classroomStatusDataHolder.findByUuid(sessionName);
        ClassroomUtil.checkClassroom(classroom.getId(), classroomRepository);

        return rubruSessionRepository.getRubruSessionListModelsByClassroomId(classroom.getId());
    }

    @Override
    public ClassroomStudentFileDownloadVM getRubruSessionParticipantsReportVM(Long rubruSessionId) {
        Optional<RubruSession> byId = rubruSessionRepository.findById(rubruSessionId);
        if (!byId.isPresent()) {
            throw new EntityNotFountByIdException("SessionInfo", rubruSessionId);
        }
        RubruSession rubruSession = byId.get();
        Long classroomId = rubruSession.getClassroomId();

        ClassroomUtil.checkClassroom(classroomId, classroomRepository);
        Classroom classroom = classroomRepository.findById(classroomId).get();
        ClassroomStudentFileDownloadVM vm = new ClassroomStudentFileDownloadVM();
        String fileName = classroom.getName() + DateUtil.formatDate(rubruSession.getStartDate(), nwmsConfig.getRubruSessionParticipantsReportFileNameDatePattern(), true) + ".xlsx";
        vm.setName(fileName);
        vm.setContent(rubruSessionParticipantsReportExcelExporter.export(rubruSessionParticipantRepository.getRubruSessionParticipantsReportVMs(rubruSessionId), false));

        return vm;
    }

    @Override
    public int updateClassroomSettings(ClassroomSettingsVM vm) {
        if (!vm.hasData()) {
            throw new NoClassroomSettingsSentException();
        }
        Classroom classroom = findAndValidateClassroomByModerator(vm.getClassroomId());

        int result = 0;

        if (vm.getHideGlobalChat() != null && !vm.getHideGlobalChat().equals(classroom.getHideGlobalChat())) {
            classroom.setHideGlobalChat(vm.getHideGlobalChat());
            result++;
        }

        if (vm.getHidePrivateChat() != null && !vm.getHidePrivateChat().equals(classroom.getHidePrivateChat())) {
            classroom.setHidePrivateChat(vm.getHidePrivateChat());
            result++;
        }

        if (vm.getHideParticipantsList() != null && !vm.getHideParticipantsList().equals(classroom.getHideParticipantsList())) {
            classroom.setHideParticipantsList(vm.getHideParticipantsList());
            result++;
        }

        if (vm.getDisableFileTransfer() != null && !vm.getDisableFileTransfer().equals(classroom.getDisableFileTransfer())) {
            classroom.setDisableFileTransfer(vm.getDisableFileTransfer());
            result++;
        }

        if (vm.getHideSoundSensitive() != null && !vm.getHideSoundSensitive().equals(classroom.getHideSoundSensitive())) {
            classroom.setHideSoundSensitive(vm.getHideSoundSensitive());
            result++;
        }

        if (vm.getHidePublishPermit() != null && !vm.getHidePublishPermit().equals(classroom.getHidePublishPermit())) {
            classroom.setHidePublishPermit(vm.getHidePublishPermit());
            result++;
        }

        if (vm.getLock() != null && !vm.getLock().equals(classroom.getLock())) {
            classroom.setLock(vm.getLock());
            result++;
        }

        if (vm.getHideScreen() != null && !vm.getHideScreen().equals(classroom.getHideScreen())) {
            classroom.setHideScreen(vm.getHideScreen());
            result++;
        }

        if (vm.getHideWhiteboard() != null && !vm.getHideWhiteboard().equals(classroom.getHideWhiteboard())) {
            classroom.setHideWhiteboard(vm.getHideWhiteboard());
            result++;
        }

        if (vm.getHideSlide() != null && !vm.getHideSlide().equals(classroom.getHideSlide())) {
            classroom.setHideSlide(vm.getHideSlide());
            result++;
        }

        if (result == 0) {
            throw new NoClassroomSettingsChangedException();
        }

        classroom = classroomRepository.save(classroom);

        classroomStatusDataHolder.removeClassroom(classroom.getSessionUuidName());

        return result;
    }

    @Override
    public List<UserClassroomDataListModel> findUserClassroomListModels(boolean active) {
        NwmsUser user = UserUtil.getCurrentUser();
        List<UserClassroomListModel> result;
        if (active) {
            result = classroomStudentRepository.findUserActiveClassroomListModels(user.getId(), new Date());
        } else {
            result = classroomStudentRepository.findUserInactiveClassroomListModels(user.getId(), new Date());
        }

        ArrayList<UserClassroomDataListModel> items = new ArrayList<>();
        if (result != null && !result.isEmpty()) {
            for (UserClassroomListModel model : result) {
                items.add(new UserClassroomDataListModel(model));
            }
            PurchaseStatusUtil.setSpecialLinks(items, purchaseStatusRepository);
        }

        return items;
    }

    @Override
    public long countClassroomByName(String name) {
        return classroomRepository.countBySessionUuidNameAndNotForId(name, -1L);
    }

    @Override
    public List<UserClassroomDataListModel> getCreatorClassrooms(boolean teacherPan) {
        NwmsUser user = UserUtil.getCurrentUser();
        List<UserClassroomListModel> creatorClassrooms = classroomRepository.findCreatorClassrooms(user.getId(), teacherPan);
        ArrayList<UserClassroomDataListModel> result = new ArrayList<>();
        if (creatorClassrooms != null && !creatorClassrooms.isEmpty()) {
            for (UserClassroomListModel model : creatorClassrooms) {
                result.add(new UserClassroomDataListModel(model));
            }
            PurchaseStatusUtil.setSpecialLinks(result, purchaseStatusRepository);
        }
        return result;
    }

    private void assignUsersToClassroom(List<User> users, List<ClassroomStudentFileVM> userVMs, Classroom classroom) {
        Set<ClassroomStudent> classroomStudents = classroom.getClassroomStudents();
        List<User> updatedUsers = new ArrayList<>();
        if (!classroomStudents.isEmpty()) {
            for (ClassroomStudent classroomStudent : classroomStudents) {
                User student = classroomStudent.getStudent();
                if (users.contains(student)) {
                    ClassroomStudentFileVM userVM = findUserVM(userVMs, student);
                    classroomStudent.setAuthorityName(userVM.getAuthorityName());
                    if (userVM.getAuthorityName().equals(ClassroomStudentAuthority.MODERATOR.name())) {
                        classroomStudent.setNeedsLogin(true);
                    } else {
                        classroomStudent.setNeedsLogin(userVM.isNeedsLogin());
                    }
                    classroomStudent.setMaxUseCount(userVM.getMaxUseCount());
                    classroomStudent = classroomStudentRepository.save(classroomStudent);
                    updatedUsers.add(student);
                }
            }
        }
        users.removeAll(updatedUsers);
        if (!users.isEmpty()) {
            for (User user : users) {
                ClassroomStudentFileVM vm = findUserVM(userVMs, user);
                ClassroomStudent classroomStudent = new ClassroomStudent();
                classroomStudent.setPk(new ClassroomStudentId(classroom, user));
                classroomStudent.setAuthorityName(vm.getAuthorityName());
                classroomStudent.setFullName(vm.getFirstName() + " " + vm.getLastName());
                if (vm.getAuthorityName().equals(ClassroomStudentAuthority.MODERATOR.name())) {
                    classroomStudent.setNeedsLogin(true);
                } else {
                    classroomStudent.setNeedsLogin(vm.isNeedsLogin());
                }
                classroomStudent.setMaxUseCount(vm.getMaxUseCount());
                classroomStudent.setToken(UUID.randomUUID().toString());
                classroomStudentRepository.save(classroomStudent);
            }
        }

    }

    private ClassroomStudentFileVM findUserVM(List<ClassroomStudentFileVM> userVMs, User user) {
        for (ClassroomStudentFileVM userVM : userVMs) {
            if (user.getId().equals(userVM.getUser().getId())) {
                return userVM;
            }
        }
        return null;
    }

    private void assignUsersToCourse(List<User> users, Course course) {
        Set<User> students = course.getStudents();
        if (students == null) {
            students = new HashSet<>();
        }
        boolean save = false;
        for (User user : users) {
            if (!students.contains(user)) {
                students.add(user);
                save = true;
            }
        }
        if (save) {
            course = courseRepository.save(course);
        }

    }

    private List<User> saveUsers(List<ClassroomStudentFileVM> users) {
        List<User> savedUsers = new ArrayList<>();
        for (ClassroomStudentFileVM vm : users) {
            if (vm.getUser() != null) {
                savedUsers.add(vm.getUser());
            } else {
                User user = createUser(vm);
                vm.setUser(user);
                savedUsers.add(user);
            }
        }
        return savedUsers;
    }


    private User createUser(ClassroomStudentFileVM vm) {

        /*Optional<User> existingUser = userRepository.findOneByLogin(vm.getLogin().toLowerCase());
        if (existingUser.isPresent()) {
            throw new LoginAlreadyUsedException();
        }*/

        UserDTO userDTO = new UserDTO();
        userDTO.setLogin(vm.getLogin());
        userDTO.setFirstName(vm.getFirstName());
        userDTO.setLastName(vm.getLastName());
        userDTO.setPassword(vm.getPassword());
        userDTO.setGender(true);
        userDTO.setValidityDate(new Date());
        Set<String> strings = new HashSet<>();
        strings.add("ROLE_USER");
        userDTO.setAuthorities(strings);
        User user = UserUtil.createUser(userDTO, organizationLevelService, authorityRepository, passwordEncoder, userRepository);
        UserUtil.clearUserCaches(user, cacheManager);
        return user;
    }


    private void validateUsersInfo(@Valid List<ClassroomStudentFileVM> users) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        for (ClassroomStudentFileVM user : users) {
            Set<ConstraintViolation<ClassroomStudentFileVM>> validate = validator.validate(user);
            if (validate != null && !validate.isEmpty()) {
                throw new ConstraintViolationException(validate);
            }
        }
    }

    private List<ClassroomStudentFileVM> convertFileToModel(Sheet dataSheet, Course course, Classroom classroom) {
        List<ClassroomStudentFileVM> result = new ArrayList<>();
        int rowCounter = 1;
        List<String> logins = new ArrayList<>();
        while (rowCounter < dataSheet.getPhysicalNumberOfRows()) {
            Row row = dataSheet.getRow(rowCounter);
            ClassroomStudentFileVM vm = new ClassroomStudentFileVM();
            vm.setCourse(course);
            vm.setClassroom(classroom);

            String loginCellValue = ExcelUtil.getCellStringValue(row.getCell(0));
            if (loginCellValue != null && !loginCellValue.isEmpty()) {
                String login = StringUtil.convertPersianNumbers(loginCellValue);
                if (login != null && !login.isEmpty()) {
                    vm.setLogin(login.toLowerCase());
                    logins.add(vm.getLogin());
                }
            }

            vm.setFirstName(ExcelUtil.getCellStringValue(row.getCell(1)));
            vm.setLastName(ExcelUtil.getCellStringValue(row.getCell(2)));
            vm.setPassword(StringUtil.convertPersianNumbers(ExcelUtil.getCellStringValue(row.getCell(3))));
            int authority = (int) ExcelUtil.getCellLongValue(row.getCell(4));
            if (authority < 1 || authority > 3) {
                throw new InvalidClassroomStudentAuthorityException(authority + "");
            }
            vm.setAuthorityName(ClassroomStudentAuthority.values()[authority - 1].name());
            int needsLogin = (int) ExcelUtil.getCellLongValue(row.getCell(5));
            if (needsLogin < 0 || needsLogin > 1) {
                throw new InvalidClassroomStudentNeedsLoginException(needsLogin);
            }
            if (vm.getAuthorityName().equalsIgnoreCase(ClassroomStudentAuthority.MODERATOR.name())) {
                vm.setNeedsLogin(true);
            } else {
                vm.setNeedsLogin(needsLogin == 1);
            }
            vm.setMaxUseCount((int) ExcelUtil.getCellLongValue(row.getCell(6)));

            result.add(vm);
            rowCounter++;
        }

        if (logins != null && !logins.isEmpty()) {
            List<User> users = userRepository.findAllByLogins(logins);
            if (users != null && !users.isEmpty()) {
                for (ClassroomStudentFileVM vm : result) {
                    if (vm.getLogin() != null && !vm.getLogin().isEmpty()) {
                        User foundUser = findUserByLogin(vm.getLogin(), users);
                        if (foundUser != null) {
                            vm.setUser(foundUser);
                        }
                    }
                }
            }
        }

        return result;
    }

    private User findUserByLogin(String login, List<User> users) {
        for (User user : users) {
            if (login.equals(user.getLogin())) {
                return user;
            }
        }
        return null;
    }

    private Sheet validateFileStructure(String fileName, String fileContentType, InputStream fileInputStream) {
        Workbook workbook = classroomStudentExcelFileStructureValidator.validateFile(fileName, fileInputStream);
        return workbook.getSheetAt(0);
    }

    private ClassroomStatusResultCode validateClassroomStatusResultCode(ClassroomStatusDTO dto, boolean isWebinar, ClassroomCheckStatusDTO classroom, String userToken, String clientId) {
        ClassroomStatusResultCode statusResultCode = ClassroomStatusResultCode.Success;
        dto.setClassroomName(classroom.getName());
        dto.setClassroomId(classroom.getId());

        //check if clientId is not blocked
        long blockedCount = classroomBlockedClientRepository.countByClientIdAndClassroom(clientId, classroom.getId());
        if (blockedCount > 0) {
            statusResultCode = ClassroomStatusResultCode.BlockedClientId;
            return statusResultCode;
        }

        NwmsUser user = UserUtil.getCurrentUser();
        boolean isLoggedIn = false;
        if (user != null) {
            blockedCount = classroomBlockedClientRepository.countByBlockedUserIdAndClassroom(user.getId(), classroom.getId());
            if (blockedCount > 0) {
                statusResultCode = ClassroomStatusResultCode.BlockedClientId;
                return statusResultCode;
            }


            dto.setUserFullName(user.getFullName());
            isLoggedIn = true;
            dto.setUserId(user.getId());
        }

        if (!classroom.isGuestSession() && !isLoggedIn) {
            dto.setUserFullName(null);
            dto.setUserId(null);
            statusResultCode = ClassroomStatusResultCode.ClassroomAvailableOnlyForMembers;
            return statusResultCode;
        }

        ClassroomStudent tokenClassroomStudent = null;
        Long classroomOwnerId = classroom.getCreatorId();
        PurchaseStatus purchaseStatus;
        try {
            purchaseStatus = classroomStatusDataHolder.findPurchaseStatusByUser(classroomOwnerId);
        } catch (UserHasNoActivePlanException e) {
            statusResultCode = ClassroomStatusResultCode.ClassroomOwnerAccountExpired;
            return statusResultCode;
        }

        boolean useEnterToken = ObjectUtil.getBooleanValue(classroom.getUseEnterToken());
        if (useEnterToken && (userToken == null || userToken.isEmpty())) {
            dto.setUserFullName(null);
            dto.setUserId(null);
            statusResultCode = ClassroomStatusResultCode.ClassroomAvailableOnlyForMembersWithToken;
            return statusResultCode;
        } else if (useEnterToken) {
            Optional<ClassroomStudent> byToken = classroomStudentRepository.findByTokenAndClassroom(userToken, classroom.getId(), ClassroomStudentAuthority.getValidAuthorityNames());
            if (!byToken.isPresent()) {
                statusResultCode = ClassroomStatusResultCode.InvalidClassroomStudentToken;
                return statusResultCode;
            }
            tokenClassroomStudent = byToken.get();
            dto.setDynamicStudent(tokenClassroomStudent.getDynamicStudent());
            if (tokenClassroomStudent.isNeedsLogin() && !isLoggedIn) {
                boolean generateLoginToken = false;
                if (ObjectUtil.getBooleanValue(classroom.getUseEnterToken()) && classroom.isGuestSession() && tokenClassroomStudent.getAuthorityName().equalsIgnoreCase(ClassroomStudentAuthority.MODERATOR.name())) {
                    if (ObjectUtil.getBooleanValue(purchaseStatus.getModeratorAutoLogin()) && ObjectUtil.getBooleanValue(classroom.getModeratorAutoLogin())) {
                        generateLoginToken = true;
                    }
                }
                if (generateLoginToken) {
                    dto.setUserFullName(tokenClassroomStudent.getFullName());
                    User student = tokenClassroomStudent.getPk().getStudent();
                    dto.setUserId(student.getId());
                    String authoritiesStr = student.getAuthorities().stream()
                        .map(Authority::getName)
                        .collect(Collectors.joining(","));

                    String authToken = tokenProvider.makeToken(student.getId(), student.getLogin(), student.getFirstName(), student.getLastName(), authoritiesStr, false);
                    dto.setAuthToken(authToken);
                } else {
                    dto.setUserFullName(null);
                    dto.setUserId(null);
                    statusResultCode = ClassroomStatusResultCode.ClassroomAvailableOnlyForMembers;
                    return statusResultCode;
                }
            }

            if (tokenClassroomStudent.isNeedsLogin() && isLoggedIn && !user.getId().equals(tokenClassroomStudent.getPk().getStudent().getId())) {
                statusResultCode = ClassroomStatusResultCode.InvalidClassroomStudentToken;
                return statusResultCode;
            }

            if (tokenClassroomStudent.getMaxUseCount() != null && tokenClassroomStudent.getMaxUseCount() > 0 && rubruSessionParticipantRepository.countByUserTokenAndNotClientId(userToken, clientId) >= tokenClassroomStudent.getMaxUseCount()) {
                statusResultCode = ClassroomStatusResultCode.TokenMaxUserCountExceeded;
                return statusResultCode;
            }

            if (!isLoggedIn) {
                dto.setUserFullName(tokenClassroomStudent.getFullName());
                dto.setUserId(tokenClassroomStudent.getPk().getStudent().getId());

            }

        } else if (!classroom.isGuestSession() && isLoggedIn) {
            Optional<ClassroomStudent> byId = classroomStudentRepository.findByClassroomAndStudentId(classroom.getId(), user.getId());
            if (!byId.isPresent()) {
                dto.setUserFullName(null);
                dto.setUserId(null);
                statusResultCode = ClassroomStatusResultCode.ClassroomAvailableOnlyForSavedMembers;
                return statusResultCode;
            }
            dto.setDynamicStudent(byId.get().getDynamicStudent());
        }


        Date now = new Date();

        if (classroom.getStartDateTime().after(now)) {
            statusResultCode = ClassroomStatusResultCode.ClassroomStartTimeNotArrived;
            return statusResultCode;
        }

        if (classroom.getFinishDateTime().before(now)) {
            statusResultCode = ClassroomStatusResultCode.ClassroomEndTimePassed;
            return statusResultCode;
        }

        if (purchaseStatus.getPlanFinishDate().toLocalDate().isBefore(LocalDate.now())) {
            statusResultCode = ClassroomStatusResultCode.ClassroomOwnerAccountExpired;
            return statusResultCode;
        }
        Integer concurUsers = purchaseStatus.getConcurUsers();
        Integer totalConcurUsers = purchaseStatus.getTotalConcurUsers();
        if (concurUsers != null && concurUsers > 0) {
            Long classroomParticipantsCount = rubruSessionParticipantRepository.countCurrentByClassroomIdAndNotClientId(classroom.getId(), clientId);
            if (concurUsers <= classroomParticipantsCount) {
                statusResultCode = ClassroomStatusResultCode.ClassroomPurchaseStatusMaxUserCountExceeded;
                return statusResultCode;
            }
            if (classroom.getMaxUserCount() != null && classroom.getMaxUserCount() > 0) {
                if (classroom.getMaxUserCount() <= classroomParticipantsCount) {
                    statusResultCode = ClassroomStatusResultCode.ClassroomMaxUserCountExceeded;
                    return statusResultCode;
                }
            }
        }


        if (totalConcurUsers != null && totalConcurUsers > 0) {
            Long ownerClassroomsParticipantsCount = rubruSessionParticipantRepository.countCurrentByClassroomOwnerIdAndNotClientId(classroomOwnerId, clientId);
            if (totalConcurUsers <= ownerClassroomsParticipantsCount) {
                statusResultCode = ClassroomStatusResultCode.OwnerAccountMaxUserCountExceeded;
                return statusResultCode;
            }
        }

        dto.setFrameRate(purchaseStatus.getFrameRate());
        dto.setResolutionHeight(purchaseStatus.getResolutionHeight());
        dto.setResolutionWidth(purchaseStatus.getResolutionWidth());
        dto.setScreenShareHeight(purchaseStatus.getScreenShareHeight());
        dto.setScreenShareWidth(purchaseStatus.getScreenShareWidth());
        dto.setScreenShareFrameRate(purchaseStatus.getScreenShareFrameRate());
        if (classroom.getReturnUrl() != null && !classroom.getReturnUrl().isEmpty()) {
            dto.setReturnUrl(classroom.getReturnUrl());
        } else {
            dto.setReturnUrl(purchaseStatus.getReturnUrl());
        }
        if (purchaseStatus.getWsUrl() != null && !purchaseStatus.getWsUrl().isEmpty()) {
            dto.setWsUrl(purchaseStatus.getWsUrl());
        } else {
            dto.setWsUrl(nwmsConfig.getRubruWsUrl());
        }
        dto.setSpecialLink(purchaseStatus.getSpecialLink());
        dto.setFileTransfer(purchaseStatus.getFileTransfer());
        dto.setConcurVideos(purchaseStatus.getConcurVideos());
        dto.setQualityVeryLow(purchaseStatus.getQualityVeryLow());
        dto.setQualityLow(purchaseStatus.getQualityLow());
        dto.setQualityMedium(purchaseStatus.getQualityMedium());
        dto.setQualityHigh(purchaseStatus.getQualityHigh());
        dto.setQualityVeryHigh(purchaseStatus.getQualityVeryHigh());
        dto.setRecordingMode(null);
        dto.setRecordingDefaultOutputMode(null);
        if (purchaseStatus.getConcurrentRecordingCount() != null && purchaseStatus.getConcurrentRecordingCount() > 0) {
            if (classroomRecordingInfoRepository.countUsingClassroomCreatorIdAndStatus(classroomOwnerId, RubruWebhookServiceImpl.RecordingStatus.started.name()) < purchaseStatus.getConcurrentRecordingCount()) {
                if (purchaseStatus.getRecordingMode() != null) {
                    if (classroom.getRecordingMode() != null) {
                        dto.setRecordingMode(classroom.getRecordingMode().name());
                    } else {
                        dto.setRecordingMode(purchaseStatus.getRecordingMode().name());
                    }
                } else {
                    dto.setRecordingMode(null);
                }
                dto.setRecordingDefaultOutputMode(purchaseStatus.getRecordingDefaultOutputMode());
            }
        }
        if (purchaseStatus.getAuthorizedHoursInWeek() != null) {
            Date fromDate = DateUtil.getSolarWeekStartDate(now);
            Date toDate = DateUtil.getSolarWeekEndDate(now);
            Long sumClassroomCreatorDuration = rubruSessionRepository.sumClassroomCreatorDurationInTimePeriod(classroomOwnerId, fromDate, toDate, RubruWebhookServiceImpl.NormalSessionEndReasons.names());
            if (sumClassroomCreatorDuration != null && purchaseStatus.getAuthorizedHoursInWeek() <= ((sumClassroomCreatorDuration / 60) / 60)) {
                statusResultCode = ClassroomStatusResultCode.AuthorizedHoursInWeekExceeded;
                return statusResultCode;
            }
        }
        if (purchaseStatus.getAuthorizedHoursInMonth() != null) {
            Date fromDate = DateUtil.getSolarMonthStartDate(now);
            Date toDate = DateUtil.getSolarMonthEndDate(now);
            Long sumClassroomCreatorDuration = rubruSessionRepository.sumClassroomCreatorDurationInTimePeriod(classroomOwnerId, fromDate, toDate, RubruWebhookServiceImpl.NormalSessionEndReasons.names());
            if (sumClassroomCreatorDuration != null && purchaseStatus.getAuthorizedHoursInMonth() <= ((sumClassroomCreatorDuration / 60) / 60)) {
                statusResultCode = ClassroomStatusResultCode.AuthorizedHoursInMonthExceeded;
                return statusResultCode;
            }
        }

        dto.setHideGlobalChat(ObjectUtil.getBooleanValue(classroom.getHideGlobalChat()));
        dto.setHidePrivateChat(ObjectUtil.getBooleanValue(classroom.getHidePrivateChat()));
        dto.setHideParticipantsList(ObjectUtil.getBooleanValue(classroom.getHideParticipantsList()));
        dto.setDisableFileTransfer(ObjectUtil.getBooleanValue(classroom.getDisableFileTransfer()));
        dto.setHideSoundSensitive(ObjectUtil.getBooleanValue(classroom.getHideSoundSensitive()));
        dto.setHidePublishPermit(ObjectUtil.getBooleanValue(classroom.getHidePublishPermit()));
        dto.setEnableSubscriberDirectEnter(ObjectUtil.getBooleanValue(classroom.getEnableSubscriberDirectEnter()));
        dto.setPublisherMustEnterFirst(ObjectUtil.getBooleanValue(classroom.getPublisherMustEnterFirst()));
        dto.setLock(ObjectUtil.getBooleanValue(classroom.getLocked()));
        dto.setHideScreen(ObjectUtil.getBooleanValue(classroom.getHideScreen()));
        dto.setHideWhiteboard(ObjectUtil.getBooleanValue(classroom.getHideWhiteboard()));
        dto.setHideSlide(ObjectUtil.getBooleanValue(classroom.getHideSlide()));
        dto.setOuterManage(classroom.isOuterManage());
        dto.setNonManagerEnterSoundOff(classroom.isNonManagerEnterSoundOff());
        dto.setNonManagerEnterVideoOff(classroom.isNonManagerEnterVideoOff());

        boolean isGuestWithSubscriberRole = ObjectUtil.getBooleanValue(classroom.getIsGuestWithSubscriberRole());
        if (isWebinar) {
            if (isLoggedIn) {
                Optional<ClassroomStudent> byId = classroomStudentRepository.findByClassroomAndStudentId(classroom.getId(), user.getId());
                if (byId.isPresent()) {
                    dto.setUserAuthorityName(byId.get().getAuthorityName());
                } else {
                    if (isGuestWithSubscriberRole && classroom.isGuestSession()) {
                        dto.setUserAuthorityName(ClassroomStudentAuthority.SUBSCRIBER.name());
                    } else if (classroom.isGuestSession()) {
                        dto.setUserAuthorityName(ClassroomStudentAuthority.PUBLISHER.name());
                    } else {
                        throw new RuntimeException("this should never happen! classroomId " + classroom.getId());
                    }
                }
            } else if (tokenClassroomStudent != null) {
                dto.setUserAuthorityName(tokenClassroomStudent.getAuthorityName());
            } else {
                if (isGuestWithSubscriberRole && classroom.isGuestSession()) {
                    dto.setUserAuthorityName(ClassroomStudentAuthority.SUBSCRIBER.name());
                } else if (classroom.isGuestSession()) {
                    dto.setUserAuthorityName(ClassroomStudentAuthority.PUBLISHER.name());
                } else {
                    throw new RuntimeException("this should never happen! classroomId " + classroom.getId());
                }
            }
        } else if (isLoggedIn && classroomOwnerId.equals(user.getId())) {
            dto.setUserAuthorityName(ClassroomStudentAuthority.MODERATOR.name());
        } else if (isGuestWithSubscriberRole && classroom.isGuestSession()) {
            dto.setUserAuthorityName(ClassroomStudentAuthority.SUBSCRIBER.name());
        } else if (classroom.isGuestSession()) {
            dto.setUserAuthorityName(ClassroomStudentAuthority.PUBLISHER.name());
        } else {
            throw new RuntimeException("this should never happen! classroomId " + classroom.getId());
        }

        if (dto.getUserAuthorityName().equals(ClassroomStudentAuthority.SUBSCRIBER.name())) {
            statusResultCode = ClassroomStatusResultCode.EnterAsViewerOnly;
        }

        if (dto.getLock() && !dto.getUserAuthorityName().equals(ClassroomStudentAuthority.MODERATOR.name())) {
            statusResultCode = ClassroomStatusResultCode.ClassroomLocked;
        }

        if (!dto.getLock() && dto.getPublisherMustEnterFirst() && dto.getUserAuthorityName().equals(ClassroomStudentAuthority.SUBSCRIBER.name())) {
            if (rubruSessionParticipantRepository.countActiveParticipants(dto.getClassroomId()) <= 0) {
                statusResultCode = ClassroomStatusResultCode.SessionNotStrated;
            }
        }

        return statusResultCode;
    }

    private Classroom findAndValidateClassroomByModerator(Long classroomId) {
        Optional<Classroom> byId = classroomRepository.findById(classroomId);
        if (!byId.isPresent()) {
            throw new EntityNotFountByIdException("Classroom", classroomId);
        }
        NwmsUser user = UserUtil.getCurrentUser();
        Classroom classroom = byId.get();
        if (!classroom.getCreator().getId().equals(user.getId())) {
            Optional<ClassroomStudent> classroomStudentById = classroomStudentRepository.findByClassroomAndStudentId(classroom.getId(), user.getId());
            if (!classroomStudentById.isPresent() || !classroomStudentById.get().getAuthorityName().equalsIgnoreCase(ClassroomStudentAuthority.MODERATOR.name())) {
                throw new EntityNotAccessibleByUserException("Classroom", classroomId);
            }
        }
        return classroom;
    }


    @Override
    public Long createNewClassroomDto(ClassroomDTO classroomDTO) {
        //todo hossein : clean up this method performance-wise
        User user = UserUtil.getCurrentUserEntity_(userRepository);
        classroomDTO.setCreator(user);
        classroomDTO.setLastModifier(user);


        List<Course> courses = courseRepository.findCourseByCreatorAndTeacherPan(user.getId(), true);
        Classroom classroom = ClassroomDTO.build(classroomDTO);
        classroom.setMaster(user);
        classroom.setLastModifier(user);
        classroom.setCreator(user);
        classroom.setLock(false);
        classroom.setSignalSessionEnd(ObjectUtil.getBooleanValue(classroomDTO.getSignalSessionEnd()));
        classroom.setReturnUrl(classroomDTO.getReturnUrl());
        //todo this is not efficient! change it
        if (courses.size() == 0) {
            Course tempCourse = new Course();
            tempCourse.setTitle(user.getId() + "_teacherroom");
            Date now = new Date();
            tempCourse.setStartTime(now);
            tempCourse.setFinishTime(now);
            tempCourse.setCreator(user);
            tempCourse.setModifier(user);
            tempCourse.setTeacherPan(true);
            tempCourse = courseRepository.save(tempCourse);
            classroom.setCourse(tempCourse);

        } else {
            classroom.setCourse(courses.get(0));
        }
        classroomDTO.setCourse(classroom.getCourse());

        validateClassroom(classroomDTO);
        classroom = classroomRepository.save(classroom);
        classroomStatusDataHolder.removeClassroom(classroom.getSessionUuidName());
        endSessionTimerHolder.updateSessionTimer(classroom.getSessionUuidName(), classroom.getFinishDateTime());
        return classroom.getId();
    }

    @Override
    public void fullDeleteClassroom(Long classroomId) {
        ClassroomUtil.checkClassroom(classroomId, classroomRepository);

        Classroom classroom = classroomRepository.findById(classroomId).get();
        if (classroom.isSessionActive()) {
            throw new UnableToDeleteClassroomDueToActiveSessionException();
        }

        deleteClassroomFileSlides(classroomId);

        deleteClassroomFiles(classroomId);

        FullDeleteUtil.moveClassroomStudentsByClassroomId(classroomId, classroomStudentRepository, deletedClassroomStudentRepository);

        moveClassroom(classroom);

        deleteSingleClassUsers(classroomId);

        classroomStudentRepository.deleteByClassroomId(classroomId);

        classroomRepository.delete(classroom);
    }

    @Override
    public List<RubruSessionParticipantsReportVM> getClassroomSessionParticipantsReport(Long rubruSessionId) {
        Optional<RubruSession> byId = rubruSessionRepository.findById(rubruSessionId);
        if (!byId.isPresent()) {
            throw new EntityNotFountByIdException("SessionInfo", rubruSessionId);
        }
        RubruSession rubruSession = byId.get();
        Long classroomId = rubruSession.getClassroomId();
        ClassroomUtil.checkClassroom(classroomId, classroomRepository);

        return rubruSessionParticipantRepository.getRubruSessionParticipantsReportVMs(rubruSessionId);
    }

    private void deleteSingleClassUsers(Long classroomId) {
        List<Number> singleClassUserIds = classroomStudentRepository.findSingleClassUserIdsUsingClassroomId(classroomId);
        if (singleClassUserIds != null && !singleClassUserIds.isEmpty()) {
            for (Number userId : singleClassUserIds) {
                FullDeleteUtil.moveUser(userId.longValue(), userRepository, deletedUserRepository);
                FullDeleteUtil.moveCourseStudents(userId.longValue(), courseRepository, deletedCourseStudentRepository);
                courseRepository.deleteCourseStudentsUsingStudentId(userId.longValue());
                classroomStudentRepository.deleteByStudentId(userId.longValue());
                userRepository.deleteById(userId.longValue());
            }
        }
    }

    private void deleteClassroomFiles(Long classroomId) {
        List<FilePathInfoModel> files = classroomFileRepository.findAllFilePathInfoModelsUsingClassroomId(classroomId);
        if (files != null && !files.isEmpty()) {
            jackrabbitRepoUtil.removeBatch(files);
            classroomFileRepository.deleteAllUsingClassroomId(classroomId);
        }
    }

    private void deleteClassroomFileSlides(Long classroomId) {
        List<FilePathInfoModel> slides = classroomFileSlideRepository.findAllFilePathInfoModelsUsingClassroomId(classroomId);
        if (slides != null && !slides.isEmpty()) {
            jackrabbitRepoUtil.removeBatch(slides);
            classroomFileSlideRepository.deleteAllUsingClassroomId(classroomId);
        }
    }


    private void moveClassroom(Classroom classroom) {
        FullDeleteUtil.moveClassroom(classroom, deletedClassroomRepository);
    }


    private void validateClassroom(ClassroomDTO dto) {
        //check classroom name format
        if (!dto.getName().matches(Constants.CLASSROOM_NAME_REGEX)) {
            throw new ClassroomNameInvalidFormatException();
        }

        checkUniqueClassroomName(dto.getName(), dto.getSessionUuidName(), dto.getId(), dto.getCourse().getId());

        checkUserClassroomCount(dto);
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
        long actualSessionCount = classroomRepository.countByCreator(creator.getId());
        if (actualSessionCount >= allowedSessionCount) {
            throw new UserMaxClassroomCountExceededException(allowedSessionCount, (int) actualSessionCount);
        }
    }

}

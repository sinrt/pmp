package com.pmp.nwms.service;

import com.pmp.nwms.config.Constants;
import com.pmp.nwms.domain.*;
import com.pmp.nwms.repository.*;
import com.pmp.nwms.domain.Authority;
import com.pmp.nwms.domain.PurchaseStatus;
import com.pmp.nwms.domain.ServiceType;
import com.pmp.nwms.domain.User;
import com.pmp.nwms.repository.AuthorityRepository;
import com.pmp.nwms.repository.PurchaseStatusRepository;
import com.pmp.nwms.repository.ServiceTypeRepository;
import com.pmp.nwms.repository.UserRepository;
import com.pmp.nwms.security.AuthoritiesConstants;
import com.pmp.nwms.security.SecurityUtils;
import com.pmp.nwms.service.dto.CallStatus;
import com.pmp.nwms.service.dto.PurchaseStatusDTO;
import com.pmp.nwms.service.dto.RegistryContainer;
import com.pmp.nwms.service.dto.UserDTO;
import com.pmp.nwms.service.util.ClassroomStatusDataHolder;
import com.pmp.nwms.service.util.RandomUtil;
import com.pmp.nwms.util.FullDeleteUtil;
import com.pmp.nwms.util.StringUtil;
import com.pmp.nwms.util.UserUtil;
import com.pmp.nwms.util.date.DateUtil;
import com.pmp.nwms.web.rest.errors.*;
import com.pmp.nwms.web.rest.errors.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final OrganizationLevelService organizationLevelService;

    private final PurchaseStatusService purchaseStatusService;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    private final ServiceTypeRepository serviceTypeService;

    private final CacheManager cacheManager;

    private final KurentoService kurentoService;

    private final CourseRepository courseRepository;

    private final ClassroomRepository classroomRepository;

    private final PurchaseStatusRepository purchaseStatusRepository;

    private final PurchaseLogRepository purchaseLogRepository;

    private final ClassroomStudentRepository classroomStudentRepository;

    private final DeletedCourseStudentRepository deletedCourseStudentRepository;

    private final DeletedClassroomStudentRepository deletedClassroomStudentRepository;

    private final DeletedUserRepository deletedUserRepository;

    private final ClassroomStatusDataHolder classroomStatusDataHolder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       OrganizationLevelService organizationLevelService,
                       AuthorityRepository authorityRepository,
                       KurentoService kurentoService,
                       ServiceTypeRepository serviceTypeService,
                       PurchaseStatusService purchaseStatusService,
                       CacheManager cacheManager,
                       CourseRepository courseRepository,
                       ClassroomRepository classroomRepository,
                       PurchaseStatusRepository purchaseStatusRepository,
                       PurchaseLogRepository purchaseLogRepository,
                       ClassroomStudentRepository classroomStudentRepository,
                       DeletedCourseStudentRepository deletedCourseStudentRepository,
                       DeletedClassroomStudentRepository deletedClassroomStudentRepository,
                       DeletedUserRepository deletedUserRepository,
                       ClassroomStatusDataHolder classroomStatusDataHolder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.cacheManager = cacheManager;
        this.kurentoService = kurentoService;
        this.purchaseStatusService = purchaseStatusService;
        this.organizationLevelService = organizationLevelService;
        this.serviceTypeService = serviceTypeService;
        this.courseRepository = courseRepository;
        this.classroomRepository = classroomRepository;
        this.purchaseStatusRepository = purchaseStatusRepository;
        this.purchaseLogRepository = purchaseLogRepository;
        this.classroomStudentRepository = classroomStudentRepository;
        this.deletedCourseStudentRepository = deletedCourseStudentRepository;
        this.deletedClassroomStudentRepository = deletedClassroomStudentRepository;
        this.deletedUserRepository = deletedUserRepository;
        this.classroomStatusDataHolder = classroomStatusDataHolder;
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key)
            .map(user -> {
                // activate given user for the registration key.
                user.setActivated(true);
                user.setActivationKey(null);
                UserUtil.clearUserCaches(user, cacheManager);
                log.debug("Activated user: {}", user);
                return user;
            });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);

        return userRepository.findOneByResetKey(key)
            .filter(user -> user.getResetDate().after(DateUtil.convertInstantToDate((new Date()).toInstant().minusSeconds(86400))))
            .map(user -> {
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setResetKey(null);
                user.setResetDate(null);
                UserUtil.clearUserCaches(user, cacheManager);
                return user;
            });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmailIgnoreCase(mail)
            .filter(User::getActivated)
            .map(user -> {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(new Date());
                UserUtil.clearUserCaches(user, cacheManager);
                return user;
            });
    }

    public boolean checkingLogin(UserDTO userDTO) {
        String login = StringUtil.convertPersianNumbers(userDTO.getLogin()).toLowerCase();
        userRepository.findOneByLogin(login).ifPresent(existingUser -> {
            if (!existingUser.getActivated()) {
                userRepository.delete(existingUser);
                UserUtil.clearUserCaches(existingUser, cacheManager);
            } else {
                throw new LoginAlreadyUsedException();
            }
        });
        userRepository.findUserByPhoneNumber(userDTO.getPhoneNumber()).ifPresent(existingPhone -> {
            throw new PhoneAlreadyUsedException();
        });
      /*  userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).ifPresent(existingPhone -> {
            throw new EmailAlreadyUsedException();
        });*/
        return true;
    }

    public User registerUser(UserDTO userDTO, String password) {
        userDTO.setLogin(StringUtil.convertPersianNumbers(userDTO.getLogin()));
        long count = userRepository.countByLogin(userDTO.getLogin());
        if (count > 0) {
            throw new LoginAlreadyUsedException();
        }
        User newUser = new User();
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(userDTO.getLogin());
        if (mat.matches()) {
            newUser.setPhoneNumber(userDTO.getPhoneNumber());
            newUser.setEmail(userDTO.getLogin());
        } else {
            newUser.setEmail(userDTO.getEmail());
            newUser.setPhoneNumber(userDTO.getLogin());
        }
        String encryptedPassword = passwordEncoder.encode(StringUtil.convertPersianNumbers(password));
        newUser.setLogin(userDTO.getLogin().toLowerCase());
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setImageUrl(userDTO.getImageUrl());
        newUser.setLangKey(userDTO.getLangKey());
        newUser.setActivated(true);
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.ROLE_USER_B2C).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        newUser.setRecordHashCode(UserUtil.calculateUserRecordHashCode(newUser));
        User user = userRepository.save(newUser);
        addPurchaseStatusForNewUser(user);
        UserUtil.clearUserCaches(user, cacheManager);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public void addPurchaseStatusForNewUser(User newUser) {
        Optional<ServiceType> free = serviceTypeService.findServicetypeByCode("free");
        if(!free.isPresent()){
            throw new EntityNotFountByCodeException("ServiceType", "free");
        }
        ServiceType serviceType = free.get();
        PurchaseStatusDTO purchaseStatus = new PurchaseStatusDTO();
        purchaseStatus.setTrackingCode("Register" + newUser.getLogin());
        purchaseStatus.setUserId(newUser.getId());
        purchaseStatus.setUserLogin(newUser.getLogin());
        purchaseStatus.setFinalPrice(0);
        purchaseStatus.setScreenShareWidth(serviceType.getScreenShareWidth());
        purchaseStatus.setScreenShareHeight(serviceType.getScreenShareHeight());
        purchaseStatus.setScreenShareFrameRate(serviceType.getScreenShareFrameRate());
        purchaseStatus.setResolutionWidth(serviceType.getResolutionWidth());
        purchaseStatus.setResolutionHeight(serviceType.getResolutionHeight());
        purchaseStatus.setValueAdded((float) 0.09);
        purchaseStatus.setInvoiceNumber("Register" + newUser.getLogin());
        purchaseStatus.setLastPlan(true);
        purchaseStatus.setPaymentTitle("Register");

        purchaseStatus.setFirstName(newUser.getFirstName());
        purchaseStatus.setFamily(newUser.getLastName());
        purchaseStatus.setServiceTitle("free");
        purchaseStatus.setServiceCode("free");
        purchaseStatus.setConcurVideos(serviceType.getConcurVideos());
        purchaseStatus.setSessionCount(serviceType.getSessionCount());
        purchaseStatus.setConcurUsers(serviceType.getConcurUsers());
        purchaseStatus.setNoTimeLimit(serviceType.getNoTimeLimit());
        purchaseStatus.setTeacherPanel(serviceType.getTeacherPanel());
        purchaseStatus.setOptionalTitle(serviceType.getOptionalTitle());
        purchaseStatus.setPublicBrandedSession(serviceType.isPublicBrandedSession());
        purchaseStatus.setPrivateBrandedSession(serviceType.isPrivateBrandedSession());
        purchaseStatus.setConcurUsers(serviceType.getConcurUsers());
        purchaseStatus.setResolution(serviceType.getResolution());
        purchaseStatus.setFrameRate(serviceType.getFrameRate());
        purchaseStatus.setTotalConcurUsers(serviceType.getTotalConcurUsers());
        SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String startDateTemp = timestampFormat.format(new Date());
        Date startDate = null;
        try {
            startDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(startDateTemp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ZoneId z = ZoneId.of(Constants.SYSTEM_TIME_ZONE);
        ZonedDateTime zdtStart = startDate.toInstant().atZone(z);
        purchaseStatus.setPurchaseDate(startDate);
        purchaseStatus.setPurchaseDateTime(startDate);
        purchaseStatus.setPlanFinishDate(DateUtil.plusMonths(startDate, 1));
        purchaseStatus.setSerivcePrice(0);
        purchaseStatusService.save(purchaseStatus);
    }

    public User createUser(UserDTO userDTO) {
        User user = UserUtil.createUser(userDTO, organizationLevelService, authorityRepository, passwordEncoder, userRepository);
        UserUtil.clearUserCaches(user, cacheManager);
        log.debug("Created Information for User: {}", user);
        return user;
    }


    public User kurentoSignup(User user) {

        user.setLangKey(Constants.EN_LANGUAGE);

        Optional<Authority> optionalAuthority = authorityRepository.findAuthorityByName(Authority.ROLE_USER);
        Set<Authority> authorities = new HashSet<>();
        authorities.add(optionalAuthority.get());

        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(new Date());
        user.setActivated(true);
        user.setRecordHashCode(UserUtil.calculateUserRecordHashCode(user));
        user = userRepository.save(user);
        UserUtil.clearUserCaches(user, cacheManager);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName first name of user
     * @param lastName  last name of user
     * @param email     email id of user
     * @param langKey   language key
     * @param imageUrl  image URL of user
     */
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email.toLowerCase());
                user.setLangKey(langKey);
                user.setImageUrl(imageUrl);
                UserUtil.clearUserCaches(user, cacheManager);
                log.debug("Changed Information for User: {}", user);
            });
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update
     * @return updated user
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return Optional.of(userRepository
            .findById(userDTO.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(user -> UserUtil.updateUser(userDTO, organizationLevelService, authorityRepository, passwordEncoder, userRepository, cacheManager))
            .map(UserDTO::new);
    }

    public void deleteUser(String login) {
        userRepository.findOneByLogin(login).ifPresent(user -> {
            userRepository.delete(user);
            UserUtil.clearUserCaches(user, cacheManager);
            log.debug("Deleted User: {}", user);
        });
    }

    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                String currentEncryptedPassword = user.getPassword();
                if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                    throw new InvalidPasswordException();
                }
                String encryptedPassword = passwordEncoder.encode(newPassword);
                user.setPassword(encryptedPassword);
                UserUtil.clearUserCaches(user, cacheManager);
                log.debug("Changed password for User: {}", user);
            });
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllDeactiveUsers(Pageable pageable) {
        return userRepository.findAllByActivated(pageable, false).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> searchUsers(String txt, Pageable pageable) {
        return userRepository.fullUsersSearch(txt, pageable).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(Long id) {
        return userRepository.findOneWithAuthoritiesById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        List<User> users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS));
        log.info("removeNotActivatedUsers:inactivated users count : " + users.size());
        int successCount = 0;
        for (User user : users) {
            try {
                log.debug("Deleting not activated user {}", user.getLogin());
                userRepository.delete(user);
                UserUtil.clearUserCaches(user, cacheManager);
                successCount++;
            } catch (Exception e) {
                log.warn(user.getId() + " : " + user.getLogin(), e);
            }
        }
        log.info("removeNotActivatedUsers:successfully deleted users count " + successCount);
    }

    /**
     * @return a list of all the authorities
     */
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

    public User findUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;

    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public Optional<User> findUserByUserName(String userName) {
        return userRepository.findUserByUserName(userName);
    }


    public String kurentoRegister(String userName, String password) {

        Optional<User> optionalUser = userRepository.findUserByUserName(userName);

        if (!optionalUser.isPresent()) {
            return "INVALID_USERNAME";
        } else {
            User user = optionalUser.get();
            boolean match = passwordEncoder.matches(password, user.getPassword());

            if (match) {

                RegistryContainer registryContainer = new RegistryContainer(CallStatus.ONLINE);
                registryContainer.setUserId(user.getId());
                kurentoService.register(registryContainer, userName);
                String token = Encryptor.encrypt("Bar12345Bar12345", "RandomInitVector", user.getPassword());
                return token;

            } else
                return "INVALID_PASSWORD";
        }

    }

    public User findUserByUserNamePassword(String userName, String password) {

        Optional<User> optionalUser = findUserByUserName(userName);

        User user = optionalUser.get();

        boolean match = passwordEncoder.matches(password, user.getPassword());

        if (match)
            return user;

        return null;

    }

    public Optional<User> findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findUserByPhoneNumber(phoneNumber);
    }

    public void updateKurentoPassword(User user, String password) {

        Optional<User> optionalUser = findUserByUserName(user.getLogin());

        User fetchedUser = optionalUser.get();

        String newPassword = passwordEncoder.encode(password);

        fetchedUser.setPassword(newPassword);

        fetchedUser.setRecordHashCode(UserUtil.calculateUserRecordHashCode(fetchedUser));
        fetchedUser = userRepository.save(fetchedUser);
    }

    @Transactional
    public void updateSpecialLink(String specialLink) {
        User user = UserUtil.getCurrentUserEntity_(userRepository);
        if(specialLink != null){
            specialLink = specialLink.trim();
        }
        if(specialLink != null && !specialLink.isEmpty()){
            if (!specialLink.matches(Constants.SPECIAL_LINK_REGEX)) {
                throw new SpecialLinkInvalidFormatException();
            }
        }else{
            specialLink = null;
        }
        user.setSpecialLink(specialLink);
        user.setRecordHashCode(UserUtil.calculateUserRecordHashCode(user));
        user = userRepository.save(user);
        Optional<PurchaseStatus> currentPlan = purchaseStatusRepository.findCurrentPlan(user.getId());
        if(currentPlan.isPresent()){
            PurchaseStatus purchaseStatus = currentPlan.get();
            purchaseStatus.setSpecialLink(specialLink);
            purchaseStatus = purchaseStatusRepository.save(purchaseStatus);
        }
        classroomStatusDataHolder.removeUserPurchaseStatus(user.getId());
    }

    @Transactional
    public void fullDeleteUser(Long userId) {
        validateUserFullDelete(userId);

        FullDeleteUtil.moveCourseStudents(userId, courseRepository, deletedCourseStudentRepository);

        FullDeleteUtil.moveClassroomStudentsByUserId(userId, classroomStudentRepository, deletedClassroomStudentRepository);

        User user = FullDeleteUtil.moveUser(userId, userRepository, deletedUserRepository);

        courseRepository.deleteCourseStudentsUsingStudentId(userId);

        classroomStudentRepository.deleteClassroomStudentsByStudentId(userId);

        userRepository.delete(user);
    }

    private void validateUserFullDelete(Long userId) {
        long count = userRepository.countById(userId);
        if (count == 0L) {
            throw new EntityNotFountByIdException("User", userId);
        }
        //check if user is not created by current user
        count = userRepository.countByIdAndCreatedBy(userId, UserUtil.getCurrentUser().getUsername());
        if (count == 0L) {
            throw new EntityNotAccessibleByUserException("User", userId);
        }
        //check the user is not creator or modifier of any course
        count = courseRepository.countByCreatorOrModifier(userId);
        if (count > 0) {
            throw new EntityCanNotBeDeletedException("User", userId, "Course");
        }
        //check the user is not master or creator or lastModifier of any classroom
        count = classroomRepository.countByMasterOrCreatorOrLastModifier(userId);
        if (count > 0) {
            throw new EntityCanNotBeDeletedException("User", userId, "Classroom");
        }
        //check the user has no PurchaseLog
        count = purchaseLogRepository.countByUserID(userId);
        if (count > 0) {
            throw new EntityCanNotBeDeletedException("User", userId, "PurchaseLog");
        }
        //check the user has no PurchaseStatus
        count = purchaseStatusRepository.countByUserId(userId);
        if (count > 0) {
            throw new EntityCanNotBeDeletedException("User", userId, "PurchaseStatus");
        }
    }
}

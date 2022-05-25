package com.pmp.nwms.service.impl;


import com.pmp.nwms.config.Constants;
import com.pmp.nwms.domain.DiscountsDef;
import com.pmp.nwms.domain.PurchaseStatus;
import com.pmp.nwms.domain.ServiceType;
import com.pmp.nwms.domain.User;
import com.pmp.nwms.repository.PurchaseStatusRepository;
import com.pmp.nwms.repository.ServiceTypeRepository;
import com.pmp.nwms.repository.UserRepository;
import com.pmp.nwms.security.NwmsUser;
import com.pmp.nwms.service.DiscountsDefService;
import com.pmp.nwms.service.PaymentPeriodService;
import com.pmp.nwms.service.PurchaseLogService;
import com.pmp.nwms.service.PurchaseStatusService;
import com.pmp.nwms.service.dto.PaymentPeriodDTO;
import com.pmp.nwms.service.dto.PurchaseLogDTO;
import com.pmp.nwms.service.dto.PurchaseStatusDTO;
import com.pmp.nwms.service.dto.UserUrlsInfoDto;
import com.pmp.nwms.service.mapper.PurchaseStatusMapper;
import com.pmp.nwms.service.util.ClassroomStatusDataHolder;
import com.pmp.nwms.util.ApplicationDataStorage;
import com.pmp.nwms.util.UserUtil;
import com.pmp.nwms.util.date.DateUtil;
import com.pmp.nwms.web.rest.errors.EntityNotFountByCodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;


/**
 * Service Implementation for managing PurchaseStatus.
 */
@Service
@Transactional
public class PurchaseStatusServiceImpl implements PurchaseStatusService {

    @Autowired
    private PaymentPeriodService paymentperiodservice;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiscountsDefService discountsdefservice;

    @Autowired
    private PurchaseLogService purchaseLogService;
    @Autowired
    private ServiceTypeRepository serviceTypeService;
    @Autowired
    private ApplicationDataStorage applicationDataStorage;

    @Autowired
    private ClassroomStatusDataHolder classroomStatusDataHolder;


    private final Logger log = LoggerFactory.getLogger(PurchaseStatusServiceImpl.class);

    private final PurchaseStatusRepository purchaseStatusRepository;

    private final PurchaseStatusMapper purchaseStatusMapper;

    public PurchaseStatusServiceImpl(PurchaseStatusRepository purchaseStatusRepository, PurchaseStatusMapper purchaseStatusMapper) {
        this.purchaseStatusRepository = purchaseStatusRepository;
        this.purchaseStatusMapper = purchaseStatusMapper;
    }


    /**
     * Save a purchaseStatus.
     *
     * @param purchaseStatusDTO the entity to updateClassroom
     * @return the persisted entity
     */
    @Override
    public PurchaseStatusDTO save(PurchaseStatusDTO purchaseStatusDTO) {
        log.debug("Request to updateClassroom PurchaseStatus : {}", purchaseStatusDTO);
        PurchaseStatus purchaseStatus = purchaseStatusMapper.toEntity(purchaseStatusDTO);
        boolean findServiceType = purchaseStatusDTO.isLastPlan() || (purchaseStatus.getTotalConcurUsers() == null);
        ServiceType serviceType = null;
        if (findServiceType) {
            Optional<ServiceType> serviceTypeByCode = serviceTypeService.findServicetypeByCode(purchaseStatus.getServiceCode());
            if (serviceTypeByCode.isPresent()) {
                serviceType = serviceTypeByCode.get();
            } else {
                throw new EntityNotFountByCodeException("ServiceType", purchaseStatus.getServiceCode());
            }
        }
        if (purchaseStatusDTO.isLastPlan()) {
            UserUrlsInfoDto urlsInfoDto = userRepository.getUserUrlsInfoDtoByUserId(purchaseStatusDTO.getUserId());
            purchaseStatus.setReturnUrl(urlsInfoDto.getReturnUrl());
            purchaseStatus.setWsUrl(urlsInfoDto.getWsUrl());
            purchaseStatus.setSpecialLink(urlsInfoDto.getSpecialLink());
            purchaseStatus.setFileTransfer(serviceType.getFileTransfer());
            purchaseStatus.setAppUrl(urlsInfoDto.getAppUrl());
            purchaseStatus.setQualityVeryLow(urlsInfoDto.getQualityVeryLow());
            purchaseStatus.setQualityLow(urlsInfoDto.getQualityLow());
            purchaseStatus.setQualityMedium(urlsInfoDto.getQualityMedium());
            purchaseStatus.setQualityHigh(urlsInfoDto.getQualityHigh());
            purchaseStatus.setQualityVeryHigh(urlsInfoDto.getQualityVeryHigh());
            purchaseStatus.setModeratorAutoLogin(urlsInfoDto.getModeratorAutoLogin());
            if (findServiceType) {
                purchaseStatus.setFileTransfer(serviceType.getFileTransfer());
                purchaseStatus.setAuthorizedHoursInMonth(serviceType.getAuthorizedHoursInMount());
                purchaseStatus.setAuthorizedHoursInWeek(serviceType.getAuthorizedHoursInWeek());
            }
        }
        if (purchaseStatus.getTotalConcurUsers() == null && findServiceType) {
            purchaseStatus.setTotalConcurUsers(serviceType.getTotalConcurUsers());
        }

        purchaseStatus = purchaseStatusRepository.save(purchaseStatus);
        classroomStatusDataHolder.removeUserPurchaseStatus(purchaseStatus.getUserId());
        return purchaseStatusMapper.toDto(purchaseStatus);
    }

    @Override
    public PurchaseStatusDTO saveUserPurchase(PurchaseStatusDTO purchaseStatusDTO, User user, ServiceType serviceType) {
        log.debug("Request to updateClassroom PurchaseStatus : {}", purchaseStatusDTO);
        PurchaseStatus purchaseStatus = purchaseStatusMapper.toEntity(purchaseStatusDTO);
        if (purchaseStatusDTO.isLastPlan()) {
            UserUrlsInfoDto urlsInfoDto = userRepository.getUserUrlsInfoDtoByUserId(purchaseStatusDTO.getUserId());
            purchaseStatus.setReturnUrl(urlsInfoDto.getReturnUrl());
            purchaseStatus.setWsUrl(urlsInfoDto.getWsUrl());
            purchaseStatus.setSpecialLink(urlsInfoDto.getSpecialLink());
            purchaseStatus.setAppUrl(urlsInfoDto.getAppUrl());
            purchaseStatus.setQualityVeryLow(urlsInfoDto.getQualityVeryLow());
            purchaseStatus.setQualityLow(urlsInfoDto.getQualityLow());
            purchaseStatus.setQualityMedium(urlsInfoDto.getQualityMedium());
            purchaseStatus.setQualityHigh(urlsInfoDto.getQualityHigh());
            purchaseStatus.setQualityVeryHigh(urlsInfoDto.getQualityVeryHigh());
            purchaseStatus.setModeratorAutoLogin(urlsInfoDto.getModeratorAutoLogin());
            if(serviceType != null){
                purchaseStatus.setAuthorizedHoursInMonth(serviceType.getAuthorizedHoursInMount());
                purchaseStatus.setAuthorizedHoursInWeek(serviceType.getAuthorizedHoursInWeek());
            }
        }
        purchaseStatus = purchaseStatusRepository.save(purchaseStatus);
        classroomStatusDataHolder.removeUserPurchaseStatus(purchaseStatus.getUserId());
        return purchaseStatusMapper.toDto(purchaseStatus);
    }

    /**
     * Get all the purchaseStatuses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PurchaseStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PurchaseStatuses");
        return purchaseStatusRepository.findAll(pageable)
            .map(purchaseStatusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PurchaseStatusDTO> searchCommand(String status, Pageable pageable) {
        log.debug("Request to get all PurchaseStatuses");
        return purchaseStatusRepository.findPurchaseStatusSearch(status, pageable)
            .map(purchaseStatusMapper::toDto);
    }

    @Override
    public List<PurchaseStatus> findAllByUserId(Long id) {
        return purchaseStatusRepository.findAllByUserId(id);
    }

    /**
     * Get one purchaseStatus by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PurchaseStatusDTO> findOne(Long id) {
        log.debug("Request to get PurchaseStatus : {}", id);
        return purchaseStatusRepository.findById(id)
            .map(purchaseStatusMapper::toDto);
    }

    @Override
    public Optional<PurchaseStatusDTO> findLastPlanOfCreatorByClassName(String name) {
        return purchaseStatusRepository.findLastPlanOfCreator(name).map(purchaseStatusMapper::toDto);
    }

    @Override
    public Optional<PurchaseStatusDTO> findLastPlanOfCreatorByClassUUid(String name) {
        return purchaseStatusRepository.findLastPlanOfCreatorByUUid(name).map(purchaseStatusMapper::toDto);
    }

    @Override
    public Optional<PurchaseStatusDTO> findCurrentUserPlan(Long id) {
        return purchaseStatusRepository.findCurrentPlan(id).map(purchaseStatusMapper::toDto);
    }

    @Override
    public List<PurchaseStatus> findUserPlans(Long id) {
        return purchaseStatusRepository.findUserPlans(id);
    }

    @Override
    public int userPlanWithDiscount(Long id, String code) {
        return purchaseStatusRepository.userPlanWithDiscount(id, code);
    }

    @Override
    public Integer getFinalPrice(PurchaseStatusDTO purchaseStatusDTO) {
        PurchaseLogDTO log = new PurchaseLogDTO();
        NwmsUser user = UserUtil.getCurrentUser();
        String username = user.getUsername();
        log.setUserID(user.getId());
        log.setUserName(user.getUsername());
        log.setUserFirstName(user.getFirstName());
        log.setUserLastName(user.getLastName());
        log.setPlanCode(purchaseStatusDTO.getServiceCode());
        log.setPlanTitle(purchaseStatusDTO.getServiceTitle());
        log.setScreenShareWidth(purchaseStatusDTO.getScreenShareWidth());
        log.setScreenShareHeight(purchaseStatusDTO.getScreenShareHeight());
        log.setResolutionHeight(purchaseStatusDTO.getResolutionHeight());
        log.setResolutionWidth(purchaseStatusDTO.getResolutionWidth());
        log.setScreenShareFrameRate(purchaseStatusDTO.getScreenShareFrameRate());
        log.setConcurVideos(purchaseStatusDTO.getConcurVideos());
        log.setConcurUsers(purchaseStatusDTO.getConcurUsers());
        log.setPublicBrandedSession(purchaseStatusDTO.isPublicBrandedSession());
        log.setPrivateBrandedSession(purchaseStatusDTO.isPrivateBrandedSession());
        log.setFrameRate(purchaseStatusDTO.getFrameRate());
        log.setResolution(purchaseStatusDTO.getResolution());
        log.setNoTimeLimit(purchaseStatusDTO.isNoTimeLimit());
        log.setTeacherPanel(purchaseStatusDTO.getTeacherPanel());
        log.setOptionalTitle(purchaseStatusDTO.isOptionalTitle());
        log.setTotalConcurUsers(purchaseStatusDTO.getTotalConcurUsers());
        log.setValueAdded((float) 0.09);

        log.setSessionCount(purchaseStatusDTO.getSessionCount());
        Long paymentId = Long.parseLong((String) applicationDataStorage.get(username + "_paymentcode"));
        Optional<PaymentPeriodDTO> paymentPeriod = paymentperiodservice.findOne(paymentId);
        double firstPrice = 0;
        if (paymentPeriod.isPresent()) {
            log.setPaymentCode(String.valueOf(paymentPeriod.get().getCode()));
            log.setPaymentTitle(paymentPeriod.get().getTitle());
            log.setPlanDaysCount(paymentPeriod.get().getPanelDays());
            log.setPaymentDiscount(paymentPeriod.get().getDiscountPercent());
            log.setUnitPrice(purchaseStatusDTO.getSerivcePrice());
            Integer withoutDiscount = paymentPeriod.get().getPanelDays() * (purchaseStatusDTO.getSerivcePrice() / 30);
            double discountVal = withoutDiscount * (((double) paymentPeriod.get().getDiscountPercent() / 100));
            firstPrice = withoutDiscount - discountVal;
            log.setPaymentPrice((int) firstPrice);
        } else {
        }

        String temp = (String) applicationDataStorage.get(username + "_discountsdef");
        if (temp != null) {
            Long discountsdefId = Long.parseLong(temp);
            Optional<DiscountsDef> discountsDef = discountsdefservice.findOne(discountsdefId);
            if (discountsDef.isPresent()) {
                log.setDicountPrice(discountsDef.get().getDiscountPercent());
                log.setDiscountCode(discountsDef.get().getCode());
                log.setDiscountTitle(discountsDef.get().getTitle());
                double specialDiscount = firstPrice * ((double) discountsDef.get().getDiscountPercent() / 100);
                firstPrice = firstPrice - specialDiscount;
            } else {
            }
        }
        log.setBankTitle("pasargad");
        firstPrice += firstPrice * ((double) 9 / 100);
        log.setFinalPrice((int) firstPrice);
        PurchaseLogDTO logTemp = purchaseLogService.save(log);
        applicationDataStorage.remove(username + "_paymentcode");
        applicationDataStorage.remove(username + "_discountsdef");
        applicationDataStorage.add(username + "_purchaselog", logTemp.getId());
        return (int) firstPrice * 10;
    }

    @Override
    public PurchaseStatusDTO setFreePlanToUser() {
        NwmsUser newUser = UserUtil.getCurrentUser();
        Optional<PurchaseStatus> currentPlan = purchaseStatusRepository.findCurrentPlan(newUser.getId());
        PurchaseStatus currentPurchaseStatus = currentPlan.get();
        currentPurchaseStatus.setLastPlan(false);
        currentPurchaseStatus = purchaseStatusRepository.save(currentPurchaseStatus);
        classroomStatusDataHolder.removeUserPurchaseStatus(newUser.getId());
        Optional<ServiceType> free = serviceTypeService.findServicetypeByCode("free");
        if(!free.isPresent()){
            throw new EntityNotFountByCodeException("ServiceType", "free");
        }
        ServiceType serviceType = free.get();
        PurchaseStatusDTO purchaseStatus = new PurchaseStatusDTO();
        purchaseStatus.setTrackingCode("Register" + newUser.getUsername());
        purchaseStatus.setUserId(newUser.getId());
        purchaseStatus.setUserLogin(newUser.getUsername());
        purchaseStatus.setFinalPrice(0);
        purchaseStatus.setValueAdded((float) 0.09);
        purchaseStatus.setInvoiceNumber("Register" + newUser.getUsername());
        purchaseStatus.setLastPlan(true);
        purchaseStatus.setPaymentTitle("Register");
        purchaseStatus.setFirstName(newUser.getFirstName());
        purchaseStatus.setFamily(newUser.getLastName());
        purchaseStatus.setServiceTitle("free");
        purchaseStatus.setServiceCode("free");
        purchaseStatus.setConcurVideos(serviceType.getConcurVideos());
        purchaseStatus.setScreenShareWidth(serviceType.getScreenShareWidth());
        purchaseStatus.setScreenShareHeight(serviceType.getScreenShareHeight());
        purchaseStatus.setScreenShareFrameRate(serviceType.getScreenShareFrameRate());
        purchaseStatus.setResolutionWidth(serviceType.getResolutionWidth());
        purchaseStatus.setResolutionHeight(serviceType.getResolutionHeight());
        purchaseStatus.setSessionCount(serviceType.getSessionCount());
        purchaseStatus.setConcurUsers(serviceType.getConcurUsers());
        purchaseStatus.setNoTimeLimit(serviceType.getNoTimeLimit());
        purchaseStatus.setOptionalTitle(serviceType.getOptionalTitle());
        purchaseStatus.setPublicBrandedSession(serviceType.isPublicBrandedSession());
        purchaseStatus.setPrivateBrandedSession(serviceType.isPrivateBrandedSession());
        purchaseStatus.setConcurUsers(serviceType.getConcurUsers());
        purchaseStatus.setTotalConcurUsers(serviceType.getTotalConcurUsers());
        purchaseStatus.setResolution(serviceType.getResolution());
        purchaseStatus.setFrameRate(serviceType.getFrameRate());
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
        purchaseStatus.setConcurrentRecordingCount(0);//todo ask Mr Cheraghi
        PurchaseStatusDTO purchaseStatusDTO = save(purchaseStatus);
        return purchaseStatusDTO;
    }

    /**
     * Delete the purchaseStatus by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PurchaseStatus : {}", id);
        purchaseStatusRepository.deleteById(id);
    }

    @Override
    public PurchaseStatusDTO insertPurchaseStatus(PurchaseLogDTO log) {
        PurchaseStatusDTO purchaseStatusDTO = new PurchaseStatusDTO();
        purchaseStatusDTO.setPurchaseDate(log.getPurchaseStartTime());
        purchaseStatusDTO.setFirstName(log.getUserFirstName());
        purchaseStatusDTO.setFamily(log.getUserLastName());
        purchaseStatusDTO.setServiceCode(log.getPlanCode());
        purchaseStatusDTO.setServiceTitle(log.getPlanTitle());
        purchaseStatusDTO.setSerivcePrice(log.getUnitPrice());
        if (log.getTransactionReferenceID() != null) {
            purchaseStatusDTO.setTrackingCode(log.getTransactionReferenceID());
        } else {
            purchaseStatusDTO.setTrackingCode(generateRandomIntIntRange());
        }
        purchaseStatusDTO.setPurchaseDateTime(log.getPurchaseStartTime());


        purchaseStatusDTO.setPlanFinishDate(DateUtil.plusDays(log.getPurchaseStartTime(), log.getPlanDaysCount()));
        purchaseStatusDTO.setLastPlan(true);
        purchaseStatusDTO.setTransactionReferenceID(log.getTransactionReferenceID());
        purchaseStatusDTO.setInvoiceNumber(log.getInvoiceNumber());
        purchaseStatusDTO.setPaymentCode(log.getPaymentCode());
        purchaseStatusDTO.setPaymentTitle(log.getPaymentTitle());
        purchaseStatusDTO.setPaymentDiscount(log.getPaymentDiscount());
        purchaseStatusDTO.setPaymentPrice(log.getPaymentPrice());
        purchaseStatusDTO.setPlanPriceWithDiscount(log.getPaymentPrice());
        purchaseStatusDTO.setDiscountCode(log.getDiscountCode());
        purchaseStatusDTO.setScreenShareWidth(log.getScreenShareWidth());
        purchaseStatusDTO.setScreenShareHeight(log.getScreenShareHeight());
        purchaseStatusDTO.setScreenShareFrameRate(log.getScreenShareFrameRate());
        purchaseStatusDTO.setResolutionHeight(log.getResolutionHeight());
        purchaseStatusDTO.setResolutionWidth(log.getResolutionWidth());
        purchaseStatusDTO.setDiscountTitle(log.getDiscountTitle());
        purchaseStatusDTO.setDicountPrice(log.getDicountPrice());
        purchaseStatusDTO.setPriceAfterVIPDiscount(log.getDicountPrice());
        purchaseStatusDTO.setValueAdded(log.getValueAdded());
        purchaseStatusDTO.setFinalPrice(log.getFinalPrice());
        purchaseStatusDTO.setNoTimeLimit(log.isNoTimeLimit());
        purchaseStatusDTO.setTeacherPanel(log.getTeacherPanel());
        purchaseStatusDTO.setOptionalTitle(log.isOptionalTitle());
        purchaseStatusDTO.setSessionCount(log.getSessionCount());
        purchaseStatusDTO.setConcurVideos(log.getConcurVideos());
        purchaseStatusDTO.setTotalConcurUsers(log.getTotalConcurUsers());
        purchaseStatusDTO.setFrameRate(log.getFrameRate());
        purchaseStatusDTO.setResolution(log.getResolution());
        purchaseStatusDTO.setPublicBrandedSession(log.isPublicBrandedSession());
        purchaseStatusDTO.setPrivateBrandedSession(log.isPrivateBrandedSession());
        purchaseStatusDTO.setConcurUsers(log.getConcurUsers());
        purchaseStatusDTO.setUserId(log.getUserID());
        purchaseStatusDTO.setUserLogin(userRepository.findById(log.getUserID()).get().getLogin());
        purchaseStatusRepository.resetLastPlanByUserId(log.getUserID());
        classroomStatusDataHolder.removeUserPurchaseStatus(log.getUserID());
        return save(purchaseStatusDTO);
    }


    public static String generateRandomIntIntRange() {
        int min = 100000;
        int max = 999999;
        Random r = new Random();
        int temp = r.nextInt((max - min) + 1) + min;
        return String.valueOf(temp);
    }


}

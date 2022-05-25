package com.pmp.nwms.service;

import com.pmp.nwms.domain.PurchaseStatus;
import com.pmp.nwms.domain.ServiceType;
import com.pmp.nwms.domain.User;
import com.pmp.nwms.service.dto.PurchaseLogDTO;
import com.pmp.nwms.service.dto.PurchaseStatusDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing PurchaseStatus.
 */
public interface PurchaseStatusService {

    /**
     * Save a purchaseStatus.
     *
     * @param purchaseStatusDTO the entity to updateClassroom
     * @return the persisted entity
     */
    PurchaseStatusDTO save(PurchaseStatusDTO purchaseStatusDTO);

    PurchaseStatusDTO saveUserPurchase(PurchaseStatusDTO purchaseStatusDTO, User user, ServiceType serviceType);

    /**
     * Get all the purchaseStatuses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PurchaseStatusDTO> findAll(Pageable pageable);

    Page<PurchaseStatusDTO> searchCommand(String status,Pageable pageable);

    List<PurchaseStatus> findAllByUserId(Long id);

    /**
     * Get the "id" purchaseStatus.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PurchaseStatusDTO> findOne(Long id);

    Optional<PurchaseStatusDTO> findLastPlanOfCreatorByClassName(String name);

    Optional<PurchaseStatusDTO> findLastPlanOfCreatorByClassUUid(String name);

    Optional<PurchaseStatusDTO> findCurrentUserPlan(Long id);

    List<PurchaseStatus> findUserPlans(Long id);

    int userPlanWithDiscount(Long id, String code);

    Integer getFinalPrice(PurchaseStatusDTO purchaseStatusDTO);

    PurchaseStatusDTO setFreePlanToUser();

    /**
     * Delete the "id" purchaseStatus.
     *
     * @param id the id of the entity
     */
    void delete(Long id);


    @Transactional
    PurchaseStatusDTO insertPurchaseStatus(PurchaseLogDTO log);

}

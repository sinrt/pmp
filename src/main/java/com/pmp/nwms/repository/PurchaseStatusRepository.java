package com.pmp.nwms.repository;

import com.pmp.nwms.domain.PurchaseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the PurchaseStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchaseStatusRepository extends JpaRepository<PurchaseStatus, Long> {
    List<PurchaseStatus> findAllByUserId(Long id);

    @Query("select purchaseStatus from PurchaseStatus purchaseStatus  where purchaseStatus.userId=:id and purchaseStatus.lastPlan=true")
    Optional<PurchaseStatus> findCurrentPlan(@Param("id") Long userId);

    @Query("select purchaseStatus from PurchaseStatus purchaseStatus  where purchaseStatus.userId=:id")
    List<PurchaseStatus> findUserPlans(@Param("id") Long userId);

    @Query("select count(purchaseStatus) from PurchaseStatus purchaseStatus  where purchaseStatus.userId=:id and purchaseStatus.discountCode =:code")
    int userPlanWithDiscount(@Param("id") Long userId, @Param("code") String code);

    @Query("Select purchaseStatus from PurchaseStatus purchaseStatus, Classroom classroom   where purchaseStatus.userId=classroom.creator.id and classroom.name=:name and purchaseStatus.lastPlan=true")
    Optional<PurchaseStatus> findLastPlanOfCreator(@Param("name") String name);

    @Query("Select purchaseStatus from PurchaseStatus purchaseStatus, Classroom classroom   where purchaseStatus.userId=classroom.creator.id and classroom.sessionUuidName=:name and purchaseStatus.lastPlan=true")
    Optional<PurchaseStatus> findLastPlanOfCreatorByUUid(@Param("name") String name);

    @Query(value = "select * from purchase_status where first_name like %?1% or family  like %?1% or tracking_code like %?1% or user_login like %?1%",
        nativeQuery = true)
    Page<PurchaseStatus> findPurchaseStatusSearch(String text, Pageable pageable);

    @Modifying
    @Query("update PurchaseStatus o set o.lastPlan = false where o.userId = :userId")
    void resetLastPlanByUserId(@Param("userId") Long userId);

    long countByUserId(Long userId);

    @Query("select purchaseStatus from PurchaseStatus purchaseStatus  where purchaseStatus.userId in :userIds and purchaseStatus.lastPlan=true")
    List<PurchaseStatus> findCurrentPlanByUserIds(@Param("userIds") List<Long> userIds);
}

package com.pmp.nwms.repository;


import com.pmp.nwms.domain.User;
import com.pmp.nwms.service.dto.CourseStudentDTO;
import com.pmp.nwms.service.dto.CourseStudentDataDTO;
import com.pmp.nwms.service.dto.UserUrlsInfoDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    String USERS_BY_LOGIN_CACHE = "usersByLogin";

    String USERS_BY_EMAIL_CACHE = "usersByEmail";

    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndCreatedDateBefore(Instant dateTime);

    Optional<User> findOneByResetKey(String resetKey);

    Optional<User> findOneByEmailIgnoreCase(String email);

    Optional<User> findOneByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesById(Long id);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
    Optional<User> findOneWithAuthoritiesByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
    Optional<User> findOneWithAuthoritiesByEmail(String email);

    Page<User> findAllByLoginNot(Pageable pageable, String login);

    Page<User> findAllByActivated(Pageable pageable, boolean activated);

    @Query(value = "select * from jhi_user where login like %?1% or first_name  like %?1% or last_name like %?1% or email like %?1% or phone_number like %?1%",
        nativeQuery = true)
    Page<User> fullUsersSearch(String text, Pageable pageable);

    @Query("select user from User user")
    List<User> getAll();

    @Query("select user from User user where user.login =:userName")
    Optional<User> findUserByUserName(@Param("userName") String userName);

    @Query("select user from User user where user.personalCode =:personalCode")
    Optional<User> findUserByPersonalCode(@Param("personalCode") String personalCode);

    @Query("select user from User user where user.login =:userName and user.password =:password ")
    Optional<User> findUserByUserNamePassword(@Param("userName") String userName, @Param("password") String password);

    @Query("select user from User user where user.phoneNumber =:phoneNumber")
    Optional<User> findUserByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query("select user from User user where user.login in :logins")
    List<User> findAllByLogins(@Param("logins") List<String> logins);


    @Query("select o.id as userId, o.login as login, o.firstName as firstName, o.lastName as lastName from User o where o.id = :id")
    CourseStudentDataDTO getCourseStudentDTO(@Param("id")Long id);

    @Query(value = "select count(o.id) from jhi_user o where BINARY o.email = :email and o.id <> :id", nativeQuery = true)
    long countUsingEmailIgnoreCase(@Param("email") String email, @Param("id") Long id);

    @Query("select " +
        " o.returnUrl as returnUrl,  " +
        " o.wsUrl as wsUrl, " +
        " o.appUrl as appUrl, " +
        " o.qualityVeryLow as qualityVeryLow, " +
        " o.qualityLow as qualityLow, " +
        " o.qualityMedium as qualityMedium, " +
        " o.qualityHigh as qualityHigh, " +
        " o.qualityVeryHigh as qualityVeryHigh, " +
        " o.moderatorAutoLogin as moderatorAutoLogin, " +
        " o.specialLink as specialLink " +
        " from User o where o.id = :id")
    UserUrlsInfoDto getUserUrlsInfoDtoByUserId(@Param("id")Long id);

    @Query(value = "select count(o.id) from jhi_user o where BINARY o.login = :login", nativeQuery = true)
    long countByLogin(@Param("login") String login);

    long countById(Long userId);

    long countByIdAndCreatedBy(Long id, String createdBy);

    @Query("select o.login from User o where o.createdBy = :createdBy")
    List<String> findAllLoginsByCreatedBy(@Param("createdBy") String createdBy);

    List<User> findAllByRecordHashCodeIsNull();
}

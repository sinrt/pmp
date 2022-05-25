package com.pmp.nwms.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pmp.nwms.config.Constants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pmp.nwms.logging.IgnoreLoggingValue;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

/**
 * A user.
 */
@Entity
@Table(name = "jhi_user")
public class User extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    @IgnoreLoggingValue
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Size(max = 10)
    @Column(name = "personalCode", length = 10, unique = true)
    private String personalCode;

    @Size(max = 50)
    @Column(name = "phone_number", length = 50, unique = true)
    private String phoneNumber;

    @Column(name = "showy_name", unique = true)
    private String showyName;


    @Column
    private Boolean gender = false;


    @Column(name = "validity_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    private Date validityDate;

    /*@Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true, nullable = true)
    private String email;*/
    @Column(length = 254, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Size(min = 2, max = 6)
    @Column(name = "lang_key", length = 6)
    private String langKey;

    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    private String resetKey;

    @Column(name = "reset_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    private Date resetDate = null;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "jhi_user_authority",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})

    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.student")
    private Set<ClassroomStudent> classroomStudents = new HashSet<>();

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private OrganizationLevel organizationLevel;

    @Column(name = "return_url", length = 255, nullable = true)
    private String returnUrl;

    @Column(name = "ws_url", length = 255, nullable = true)
    private String wsUrl;

    @Column(name = "special_link", length = 255, nullable = true)
    private String specialLink;

    @Column(name = "app_url", length = 255, nullable = true)
    private String appUrl;

    @Column(name = "quality_very_low", nullable = true)
    private Integer qualityVeryLow;
    @Column(name = "quality_low", nullable = true)
    private Integer qualityLow;
    @Column(name = "quality_medium", nullable = true)
    private Integer qualityMedium;
    @Column(name = "quality_high", nullable = true)
    private Integer qualityHigh;
    @Column(name = "quality_very_high", nullable = true)
    private Integer qualityVeryHigh;
    @Column(name = "moderator_auto_login", nullable = true)
    private Boolean moderatorAutoLogin;
    @Column(name = "record_hash_code", nullable = true)
    private Integer recordHashCode;


    public Set<ClassroomStudent> getClassroomStudents() {
        return classroomStudents;
    }


    public void setClassroomStudents(Set<ClassroomStudent> classroomStudents) {
        this.classroomStudents = classroomStudents;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    // lowercase the login before saving it in database

    public void setLogin(String login) {
        this.login = StringUtils.lowerCase(login, Locale.ENGLISH);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean getActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Date getResetDate() {
        return resetDate;
    }

    public void setResetDate(Date resetDate) {
        this.resetDate = resetDate;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public String getPhoneNumber() {
        if("".equals(phoneNumber)){
            phoneNumber = null;
        }
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPersonalCode() {
        return personalCode;
    }

    public void setPersonalCode(String personalCode) {
        this.personalCode = personalCode;
    }

    public OrganizationLevel getOrganizationLevel() {
        return organizationLevel;
    }

    public void setOrganizationLevel(OrganizationLevel organizationLevel) {
        this.organizationLevel = organizationLevel;
    }

    public String getShowyName() {
        return showyName;
    }

    public void setShowyName(String showyName) {
        this.showyName = showyName;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Date getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(Date validityDate) {
        this.validityDate = validityDate;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getWsUrl() {
        return wsUrl;
    }

    public void setWsUrl(String wsUrl) {
        this.wsUrl = wsUrl;
    }

    public String getSpecialLink() {
        return specialLink;
    }

    public void setSpecialLink(String specialLink) {
        this.specialLink = specialLink;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Integer getQualityVeryLow() {
        return qualityVeryLow;
    }

    public void setQualityVeryLow(Integer qualityVeryLow) {
        this.qualityVeryLow = qualityVeryLow;
    }

    public Integer getQualityLow() {
        return qualityLow;
    }

    public void setQualityLow(Integer qualityLow) {
        this.qualityLow = qualityLow;
    }

    public Integer getQualityMedium() {
        return qualityMedium;
    }

    public void setQualityMedium(Integer qualityMedium) {
        this.qualityMedium = qualityMedium;
    }

    public Integer getQualityHigh() {
        return qualityHigh;
    }

    public void setQualityHigh(Integer qualityHigh) {
        this.qualityHigh = qualityHigh;
    }

    public Integer getQualityVeryHigh() {
        return qualityVeryHigh;
    }

    public void setQualityVeryHigh(Integer qualityVeryHigh) {
        this.qualityVeryHigh = qualityVeryHigh;
    }

    public Boolean getModeratorAutoLogin() {
        return moderatorAutoLogin;
    }

    public void setModeratorAutoLogin(Boolean moderatorAutoLogin) {
        this.moderatorAutoLogin = moderatorAutoLogin;
    }

    public Integer getRecordHashCode() {
        return recordHashCode;
    }

    public void setRecordHashCode(Integer recordHashCode) {
        this.recordHashCode = recordHashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof User)) {
            return false;
        }

        User user = (User) o;
        return !(user.getId() == null || getId() == null) && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "User{" +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", activated='" + activated + '\'' +
            ", langKey='" + langKey + '\'' +
            ", activationKey='" + activationKey + '\'' +
            "}";
    }

    @Override
    public Long fetchPrimaryKey() {
        return getId();
    }
}

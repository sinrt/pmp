package com.pmp.nwms.domain;

import com.pmp.nwms.logging.IgnoreLoggingValue;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A user.
 */
@Entity
@Table(name = "del_jhi_user")

public class DeletedUser extends AbstractDeletedEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(length = 50, nullable = false)
    private String login;

    @Column(name = "password_hash", length = 60, nullable = false)
    @IgnoreLoggingValue
    private String password;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "personalCode", length = 10)
    private String personalCode;

    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    @Column(name = "showy_name")
    private String showyName;


    @Column
    private Boolean gender = false;


    @Column(name = "validity_date")
    private ZonedDateTime validityDate;

    @Column(length = 254)
    private String email;

    @Column(nullable = false)
    private boolean activated = false;

    @Column(name = "lang_key", length = 6)
    private String langKey;

    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @Column(name = "activation_key", length = 20)
    private String activationKey;

    @Column(name = "reset_key", length = 20)
    private String resetKey;

    @Column(name = "reset_date")
    private Instant resetDate = null;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "del_jhi_user_authority",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})

    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();


    @Column(name = "organization_level_id")
    private Long organizationLevelId;

    @Column(name = "return_url", length = 255, nullable = true)
    private String returnUrl;

    @Column(name = "ws_url", length = 255, nullable = true)
    private String wsUrl;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getPersonalCode() {
        return personalCode;
    }

    public void setPersonalCode(String personalCode) {
        this.personalCode = personalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public ZonedDateTime getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(ZonedDateTime validityDate) {
        this.validityDate = validityDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public Instant getResetDate() {
        return resetDate;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Long getOrganizationLevelId() {
        return organizationLevelId;
    }

    public void setOrganizationLevelId(Long organizationLevelId) {
        this.organizationLevelId = organizationLevelId;
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

    @Override
    public Long fetchPrimaryKey() {
        return getId();
    }
}

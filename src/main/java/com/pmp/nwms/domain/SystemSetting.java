package com.pmp.nwms.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SystemSetting.
 */
@Entity
@Table(name = "system_setting")
public class SystemSetting implements Serializable, BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_time_out")
    private Integer sessionTimeOut;

    @Column(name = "captcha_counter")
    private Integer captchaCounter;

    @Column(name = "change_pass_renge")
    private Integer changePassRenge;

    @Column(name = "pass_uper_case_word")
    private Integer passUperCaseWord;

    @Column(name = "pass_lower_case_word")
    private Integer passLowerCaseWord;

    @Column(name = "pass_number_count")
    private Integer passNumberCount;

    @Column(name = "max_online_user")
    private Integer maxOnlineUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSessionTimeOut() {
        return sessionTimeOut;
    }

    public SystemSetting sessionTimeOut(Integer sessionTimeOut) {
        this.sessionTimeOut = sessionTimeOut;
        return this;
    }

    public void setSessionTimeOut(Integer sessionTimeOut) {
        this.sessionTimeOut = sessionTimeOut;
    }

    public Integer getCaptchaCounter() {
        return captchaCounter;
    }

    public SystemSetting captchaCounter(Integer captchaCounter) {
        this.captchaCounter = captchaCounter;
        return this;
    }

    public void setCaptchaCounter(Integer captchaCounter) {
        this.captchaCounter = captchaCounter;
    }

    public Integer getChangePassRenge() {
        return changePassRenge;
    }

    public SystemSetting changePassRenge(Integer changePassRenge) {
        this.changePassRenge = changePassRenge;
        return this;
    }

    public void setChangePassRenge(Integer changePassRenge) {
        this.changePassRenge = changePassRenge;
    }

    public Integer getPassUperCaseWord() {
        return passUperCaseWord;
    }

    public SystemSetting passUperCaseWord(Integer passUperCaseWord) {
        this.passUperCaseWord = passUperCaseWord;
        return this;
    }

    public void setPassUperCaseWord(Integer passUperCaseWord) {
        this.passUperCaseWord = passUperCaseWord;
    }

    public Integer getPassLowerCaseWord() {
        return passLowerCaseWord;
    }

    public SystemSetting passLowerCaseWord(Integer passLowerCaseWord) {
        this.passLowerCaseWord = passLowerCaseWord;
        return this;
    }

    public void setPassLowerCaseWord(Integer passLowerCaseWord) {
        this.passLowerCaseWord = passLowerCaseWord;
    }

    public Integer getPassNumberCount() {
        return passNumberCount;
    }

    public SystemSetting passNumberCount(Integer passNumberCount) {
        this.passNumberCount = passNumberCount;
        return this;
    }

    public void setPassNumberCount(Integer passNumberCount) {
        this.passNumberCount = passNumberCount;
    }

    public Integer getMaxOnlineUser() {
        return maxOnlineUser;
    }

    public SystemSetting maxOnlineUser(Integer maxOnlineUser) {
        this.maxOnlineUser = maxOnlineUser;
        return this;
    }

    public void setMaxOnlineUser(Integer maxOnlineUser) {
        this.maxOnlineUser = maxOnlineUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SystemSetting systemSetting = (SystemSetting) o;
        if (systemSetting.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), systemSetting.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SystemSetting{" +
            "id=" + getId() +
            ", sessionTimeOut=" + getSessionTimeOut() +
            ", captchaCounter=" + getCaptchaCounter() +
            ", changePassRenge=" + getChangePassRenge() +
            ", passUperCaseWord=" + getPassUperCaseWord() +
            ", passLowerCaseWord=" + getPassLowerCaseWord() +
            ", passNumberCount=" + getPassNumberCount() +
            ", maxOnlineUser=" + getMaxOnlineUser() +
            "}";
    }

    @Override
    public Long fetchPrimaryKey() {
        return getId();
    }
}

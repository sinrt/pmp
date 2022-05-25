package com.pmp.nwms.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ServiceType.
 */
@Entity
@Table(name = "service_type")
public class ServiceType implements Serializable, BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @NotNull
    @Size(min = 4)
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @NotNull
    @Min(value = 1)
    @Column(name = "concur_users", nullable = false)
    private Integer concurUsers;

    @Column(name = "authorized_hours_in_mount", nullable = true)
    private Integer authorizedHoursInMount;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "authorized_hours_in_week", nullable = true)
    private Integer authorizedHoursInWeek;

    @NotNull
    @Min(value = 1)
    @Max(value = 100)
    @Column(name = "concur_videos", nullable = false)
    private Integer concurVideos;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "description")
    private String description;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "service_def")
    private String serviceDefinition;

    @Column(name = "teacher_panel")
    private Boolean teacherPanel = false;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Column(name = "session_count")
    private Integer sessionCount;

    @Column(name = "public_branded_session")
    private Boolean publicBrandedSession;

    @Column(name = "private_branded_session")
    private Boolean privateBrandedSession;

    @Min(value = 0)
    @Column(name = "frame_rate")
    private Integer frameRate;

    @Column(name = "resolution")
    private String resolution;

    @Column(name = "no_time_limit")
    private Boolean noTimeLimit;

    @Column(name = "optional_title")
    private Boolean optionalTitle;

    @Column(name = "screen_share_width")
    private Integer screenShareWidth;

    @Column(name = "screen_share_height")
    private Integer screenShareHeight;

    @Column(name = "screen_share_frame_rate")
    private Integer screenShareFrameRate;

    @Column(name = "resolution_width")
    private Integer resolutionWidth;

    @Column(name = "resolution_height")
    private Integer resolutionHeight;

    @Column(name = "total_concur_users")
    private Integer totalConcurUsers;

    @Column(name = "file_transfer")
    private Boolean fileTransfer;


    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public ServiceType code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public ServiceType title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getConcurUsers() {
        return concurUsers;
    }

    public ServiceType concurUsers(Integer concurUsers) {
        this.concurUsers = concurUsers;
        return this;
    }

    public Integer getTotalConcurUsers() {
        return totalConcurUsers;
    }

    public void setTotalConcurUsers(Integer totalConcurUsers) {
        this.totalConcurUsers = totalConcurUsers;
    }

    public Boolean getFileTransfer() {
        if(fileTransfer == null){
            fileTransfer = false;
        }
        return fileTransfer;
    }

    public void setFileTransfer(Boolean fileTransfer) {
        this.fileTransfer = fileTransfer;
    }

    public String getServiceDefinition() {
        return serviceDefinition;
    }

    public void setServiceDefinition(String serviceDefinition) {
        this.serviceDefinition = serviceDefinition;
    }

    public void setConcurUsers(Integer concurUsers) {
        this.concurUsers = concurUsers;
    }

    public Integer getAuthorizedHoursInMount() {
        return authorizedHoursInMount;
    }

    public ServiceType authorizedHoursInMount(Integer authorizedHoursInMount) {
        this.authorizedHoursInMount = authorizedHoursInMount;
        return this;
    }

    public void setAuthorizedHoursInMount(Integer authorizedHoursInMount) {
        this.authorizedHoursInMount = authorizedHoursInMount;
    }

    public Integer getAuthorizedHoursInWeek() {
        return authorizedHoursInWeek;
    }

    public ServiceType authorizedHoursInWeek(Integer authorizedHoursInWeek) {
        this.authorizedHoursInWeek = authorizedHoursInWeek;
        return this;
    }

    public void setAuthorizedHoursInWeek(Integer authorizedHoursInWeek) {
        this.authorizedHoursInWeek = authorizedHoursInWeek;
    }

    public Integer getConcurVideos() {
        return concurVideos;
    }

    public ServiceType concurVideos(Integer concurVideos) {
        this.concurVideos = concurVideos;
        return this;
    }

    public void setConcurVideos(Integer concurVideos) {
        this.concurVideos = concurVideos;
    }

    public Integer getAmount() {
        return amount;
    }

    public ServiceType amount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public boolean getActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public ServiceType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSessionCount() {
        return sessionCount;
    }

    public ServiceType sessionCount(Integer sessionCount) {
        this.sessionCount = sessionCount;
        return this;
    }

    public void setSessionCount(Integer sessionCount) {
        this.sessionCount = sessionCount;
    }

    public Boolean isPublicBrandedSession() {
        return publicBrandedSession;
    }

    public ServiceType publicBrandedSession(Boolean publicBrandedSession) {
        this.publicBrandedSession = publicBrandedSession;
        return this;
    }

    public void setPublicBrandedSession(Boolean publicBrandedSession) {
        this.publicBrandedSession = publicBrandedSession;
    }

    public Boolean isPrivateBrandedSession() {
        return privateBrandedSession;
    }

    public ServiceType privateBrandedSession(Boolean privateBrandedSession) {
        this.privateBrandedSession = privateBrandedSession;
        return this;
    }

    public void setPrivateBrandedSession(Boolean privateBrandedSession) {
        this.privateBrandedSession = privateBrandedSession;
    }

    public Integer getFrameRate() {
        return frameRate;
    }

    public ServiceType frameRate(Integer frameRate) {
        this.frameRate = frameRate;
        return this;
    }

    public void setFrameRate(Integer frameRate) {
        this.frameRate = frameRate;
    }

    public String getResolution() {
        return resolution;
    }

    public ServiceType resolution(String resolution) {
        this.resolution = resolution;
        return this;
    }

    public Boolean getTeacherPanel() {
        return teacherPanel;
    }

    public void setTeacherPanel(Boolean teacherPanel) {
        this.teacherPanel = teacherPanel;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public Boolean getNoTimeLimit() {
        return noTimeLimit;
    }

    public ServiceType noTimeLimit(Boolean noTimeLimit) {
        this.noTimeLimit = noTimeLimit;
        return this;
    }

    public ServiceType optionalTitle(Boolean optionalTitle) {
        this.noTimeLimit = noTimeLimit;
        return this;
    }

    public void setNoTimeLimit(Boolean noTimeLimit) {
        this.noTimeLimit = noTimeLimit;
    }


    public Integer getScreenShareWidth() {
        return screenShareWidth;
    }

    public ServiceType screenShareWidth(Integer screenShareWidth) {
        this.screenShareWidth = screenShareWidth;
        return this;
    }

    public void setScreenShareWidth(Integer screenShareWidth) {
        this.screenShareWidth = screenShareWidth;
    }

    public Integer getScreenShareHeight() {
        return screenShareHeight;
    }

    public ServiceType screenShareHeight(Integer screenShareHeight) {
        this.screenShareHeight = screenShareHeight;
        return this;
    }

    public void setScreenShareHeight(Integer screenShareHeight) {
        this.screenShareHeight = screenShareHeight;
    }

    public Integer getScreenShareFrameRate() {
        return screenShareFrameRate;
    }

    public ServiceType screenShareFrameRate(Integer screenShareFrameRate) {
        this.screenShareFrameRate = screenShareFrameRate;
        return this;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setScreenShareFrameRate(Integer screenShareFrameRate) {
        this.screenShareFrameRate = screenShareFrameRate;
    }

    public Integer getResolutionWidth() {
        return resolutionWidth;
    }

    public ServiceType resolutionWidth(Integer resolutionWidth) {
        this.resolutionWidth = resolutionWidth;
        return this;
    }

    public void setResolutionWidth(Integer resolutionWidth) {
        this.resolutionWidth = resolutionWidth;
    }

    public Integer getResolutionHeight() {
        return resolutionHeight;
    }

    public ServiceType resolutionHeight(Integer resolutionHeight) {
        this.resolutionHeight = resolutionHeight;
        return this;
    }

    public void setResolutionHeight(Integer resolutionHeight) {
        this.resolutionHeight = resolutionHeight;
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
        ServiceType serviceType = (ServiceType) o;
        if (serviceType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    public Boolean getOptionalTitle() {
        return optionalTitle;
    }

    public void setOptionalTitle(Boolean optionalTitle) {
        this.optionalTitle = optionalTitle;
    }

    @Override
    public String toString() {
        return "ServiceType{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", title='" + getTitle() + "'" +
            ", concurUsers=" + getConcurUsers() +
            ", authorizedHoursInMount=" + getAuthorizedHoursInMount() +
            ", authorizedHoursInWeek=" + getAuthorizedHoursInWeek() +
            ", concurVideos=" + getConcurVideos() +
            ", amount=" + getAmount() +
            ", description='" + getDescription() + "'" +
            ", sessionCount=" + getSessionCount() +
            ", publicBrandedSession='" + isPublicBrandedSession() + "'" +
            ", privateBrandedSession='" + isPrivateBrandedSession() + "'" +
            ", frameRate=" + getFrameRate() +
            ", resolution='" + getResolution() + "'" +
            ", noTimeLimit='" + getNoTimeLimit() + "'" +
            ", optionalTitle='" + getOptionalTitle() + "'" +
            ", screenShareWidth=" + getScreenShareWidth() +
            ", screenShareHeight=" + getScreenShareHeight() +
            ", screenShareFrameRate=" + getScreenShareFrameRate() +
            ", resolutionWidth=" + getResolutionWidth() +
            ", resolutionHeight=" + getResolutionHeight() +
            "}";
    }

    @Override
    public Long fetchPrimaryKey() {
        return getId();
    }
}

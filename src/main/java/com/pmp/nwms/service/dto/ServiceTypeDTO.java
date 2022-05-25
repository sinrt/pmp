package com.pmp.nwms.service.dto;

import com.pmp.nwms.domain.ServiceType;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ServiceType entity.
 */
public class ServiceTypeDTO implements Serializable {

    private Long id;

    public ServiceTypeDTO() {
    }

    public ServiceTypeDTO(ServiceType serviceType) {
        this.id = serviceType.getId();
        this.code = serviceType.getCode();
        this.title = serviceType.getTitle();
        this.concurUsers = serviceType.getConcurUsers();
        this.authorizedHoursInMount = serviceType.getAuthorizedHoursInMount();
        this.authorizedHoursInWeek = serviceType.getAuthorizedHoursInWeek();
        this.concurVideos = serviceType.getConcurVideos();
        this.amount = serviceType.getAmount();
        this.activated = serviceType.getActivated();
        this.description = serviceType.getDescription();
        this.serviceDefinition = serviceType.getServiceDefinition();
        this.sessionCount = serviceType.getSessionCount();
        this.publicBrandedSession = serviceType.isPublicBrandedSession();
        this.privateBrandedSession = serviceType.isPrivateBrandedSession();
        this.sessionCount = serviceType.getSessionCount();
        this.frameRate = serviceType.getFrameRate();
        this.resolution = serviceType.getResolution();
        this.noTimeLimit = serviceType.getNoTimeLimit();
        this.optionalTitle = serviceType.getOptionalTitle();
        this.screenShareWidth = serviceType.getScreenShareWidth();
        this.screenShareHeight = serviceType.getScreenShareHeight();
        this.screenShareFrameRate = serviceType.getScreenShareFrameRate();
        this.resolutionWidth = serviceType.getResolutionWidth();
        this.resolutionHeight = serviceType.getResolutionHeight();
        this.teacherPanel = serviceType.getTeacherPanel();
        this.priority = serviceType.getPriority();
        this.totalConcurUsers = serviceType.getTotalConcurUsers();
        this.fileTransfer = serviceType.getFileTransfer();
    }

    @NotNull
    private String code;

    @NotNull
    @Size(min = 4)
    private String title;

    @NotNull
    @Min(value = 1)
    private Integer concurUsers;

    private Integer authorizedHoursInMount;

    @Min(value = 0)
    @Max(value = 100)
    private Integer authorizedHoursInWeek;

    @NotNull
    @Min(value = 1)
    @Max(value = 100)
    private Integer concurVideos;

    @Min(value = 1)
    private Integer frameRate;

    @NotNull
    private Integer amount;

    private boolean activated = false;

    private boolean teacherPanel = false;

    private Integer sessionCount;

    private Integer priority;


    private String description;

    private String serviceDefinition;

    private String resolution;

    private Boolean publicBrandedSession;

    private Boolean privateBrandedSession;

    private Integer totalConcurUsers;

    private Boolean noTimeLimit;


    private Boolean optionalTitle;

    private Integer screenShareWidth;

    private Integer screenShareHeight;

    private Integer screenShareFrameRate;

    private Integer resolutionWidth;

    private Integer resolutionHeight;

    @NotNull
    private Boolean fileTransfer;

    public boolean isTeacherPanel() {
        return teacherPanel;
    }

    public void setTeacherPanel(boolean teacherPanel) {
        this.teacherPanel = teacherPanel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getConcurUsers() {
        return concurUsers;
    }

    public void setConcurUsers(Integer concurUsers) {
        this.concurUsers = concurUsers;
    }

    public Integer getAuthorizedHoursInMount() {
        return authorizedHoursInMount;
    }

    public void setAuthorizedHoursInMount(Integer authorizedHoursInMount) {
        this.authorizedHoursInMount = authorizedHoursInMount;
    }

    public Integer getSessionCount() {
        return sessionCount;
    }

    public void setSessionCount(Integer sessionCount) {
        this.sessionCount = sessionCount;
    }

    public Integer getAuthorizedHoursInWeek() {
        return authorizedHoursInWeek;
    }

    public void setAuthorizedHoursInWeek(Integer authorizedHoursInWeek) {
        this.authorizedHoursInWeek = authorizedHoursInWeek;
    }

    public Integer getTotalConcurUsers() {
        return totalConcurUsers;
    }

    public void setTotalConcurUsers(Integer totalConcurUsers) {
        this.totalConcurUsers = totalConcurUsers;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getConcurVideos() {
        return concurVideos;
    }

    public void setConcurVideos(Integer concurVideos) {
        this.concurVideos = concurVideos;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Boolean isPublicBrandedSession() {
        return publicBrandedSession;
    }

    public void setPublicBrandedSession(Boolean publicBrandedSession) {
        this.publicBrandedSession = publicBrandedSession;
    }

    public Boolean isPrivateBrandedSession() {
        return privateBrandedSession;
    }

    public void setPrivateBrandedSession(Boolean privateBrandedSession) {
        this.privateBrandedSession = privateBrandedSession;
    }

    public Integer getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(Integer frameRate) {
        this.frameRate = frameRate;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public Boolean isNoTimeLimit() {
        return noTimeLimit;
    }

    public void setNoTimeLimit(Boolean noTimeLimit) {
        this.noTimeLimit = noTimeLimit;
    }

    public Boolean getOptionalTitle() {
        return optionalTitle;
    }

    public void setOptionalTitle(Boolean optionalTitle) {
        this.optionalTitle = optionalTitle;
    }

    public Integer getScreenShareWidth() {
        return screenShareWidth;
    }

    public void setScreenShareWidth(Integer screenShareWidth) {
        this.screenShareWidth = screenShareWidth;
    }

    public Integer getScreenShareHeight() {
        return screenShareHeight;
    }

    public String getServiceDefinition() {
        return serviceDefinition;
    }

    public void setServiceDefinition(String serviceDefinition) {
        this.serviceDefinition = serviceDefinition;
    }

    public void setScreenShareHeight(Integer screenShareHeight) {
        this.screenShareHeight = screenShareHeight;
    }

    public Integer getScreenShareFrameRate() {
        return screenShareFrameRate;
    }

    public void setScreenShareFrameRate(Integer screenShareFrameRate) {
        this.screenShareFrameRate = screenShareFrameRate;
    }

    public Integer getResolutionWidth() {
        return resolutionWidth;
    }

    public void setResolutionWidth(Integer resolutionWidth) {
        this.resolutionWidth = resolutionWidth;
    }

    public Integer getResolutionHeight() {
        return resolutionHeight;
    }

    public void setResolutionHeight(Integer resolutionHeight) {
        this.resolutionHeight = resolutionHeight;
    }

    public Boolean getFileTransfer() {
        return fileTransfer;
    }

    public void setFileTransfer(Boolean fileTransfer) {
        this.fileTransfer = fileTransfer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceTypeDTO serviceTypeDTO = (ServiceTypeDTO) o;
        if (serviceTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceTypeDTO{" +
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
            ", noTimeLimit='" + isNoTimeLimit() + "'" +
            ", optionalTitle='" + getOptionalTitle() + "'" +
            ", screenShareWidth=" + getScreenShareWidth() +
            ", screenShareHeight=" + getScreenShareHeight() +
            ", screenShareFrameRate=" + getScreenShareFrameRate() +
            ", resolutionWidth=" + getResolutionWidth() +
            ", resolutionHeight=" + getResolutionHeight() +
            "}";
    }
}

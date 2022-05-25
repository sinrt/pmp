package com.pmp.nwms.service.dto;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pmp.nwms.config.Constants;
import com.pmp.nwms.domain.Classroom;
import com.pmp.nwms.domain.ClassroomStudent;
import com.pmp.nwms.domain.Course;
import com.pmp.nwms.domain.User;
import com.pmp.nwms.util.date.DateUtil;

import javax.persistence.Column;
import javax.validation.constraints.Size;


public class ClassroomDTO {

    private Long id;
    private Integer frameRate;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    private Date startDateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    private Date finishDateTime;
    private String connectionType;
    private String resolution;
    @JsonIgnoreProperties("")
    private User master;
    private Set<User> students = new HashSet<>();
    @JsonIgnoreProperties("classrooms")
    private User creator;
    @JsonIgnoreProperties("classrooms")
    private User lastModifier;

    private Course course;

    private String sessionUuidName;
    private boolean guestSession;
    private boolean generateRandomName;
    private boolean guestSessionReqPass;
    private boolean isGuestWithSubscriberRole;
    private boolean useEnterToken;
    private String guestPassword;

    private Boolean hideGlobalChat;
    private Boolean hidePrivateChat;
    private Boolean hideParticipantsList;
    private Boolean disableFileTransfer;
    private Boolean hideSoundSensitive;
    private Boolean hidePublishPermit;
    private Boolean enableSubscriberDirectEnter;
    private Boolean publisherMustEnterFirst;
    private Boolean lock;
    private Integer maxUserCount;
    private Boolean signalSessionEnd;

    @Size(min = 0, max = 255)
    private String returnUrl;


    public ClassroomDTO() {
    }

    public ClassroomDTO(Classroom classroom) {
        this.id = classroom.getId();
        this.frameRate = classroom.getFrameRate();
        this.name = classroom.getName();
        this.startDateTime = classroom.getStartDateTime();
        this.finishDateTime = classroom.getFinishDateTime();
        this.connectionType = classroom.getConnectionType();
        this.master = classroom.getMaster();
        this.resolution = classroom.getResolution();

        Set<User> classroomStudents = new HashSet<>();
        for (ClassroomStudent classroomStudent : classroom.getClassroomStudents()) {
            User user = classroomStudent.getStudent();
            classroomStudents.add(user);
        }
        this.students = classroomStudents;
        this.creator = classroom.getCreator();
        this.lastModifier = classroom.getLastModifier();
        this.course = classroom.getCourse();
        this.sessionUuidName = classroom.getSessionUuidName();
        this.guestSession = classroom.isGuestSession();
        this.generateRandomName = classroom.getGenerateRandomName();
        this.guestSessionReqPass = classroom.isGuestSessionReqPass();
        this.isGuestWithSubscriberRole = classroom.getIsGuestWithSubscriberRole();
        this.guestPassword = classroom.getGuestPassword();
        this.useEnterToken = classroom.getUseEnterToken();
        this.hideGlobalChat = classroom.getHideGlobalChat();
        this.hidePrivateChat = classroom.getHidePrivateChat();
        this.hideParticipantsList = classroom.getHideParticipantsList();
        this.disableFileTransfer = classroom.getDisableFileTransfer();
        this.hideSoundSensitive = classroom.getHideSoundSensitive();
        this.hidePublishPermit = classroom.getHidePublishPermit();
        this.enableSubscriberDirectEnter = classroom.getEnableSubscriberDirectEnter();
        this.publisherMustEnterFirst = classroom.getPublisherMustEnterFirst();
        this.maxUserCount = classroom.getMaxUserCount();
        this.signalSessionEnd = classroom.isSignalSessionEnd();
        this.returnUrl = classroom.getReturnUrl();
        this.lock = classroom.getLock();
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ClassroomDTO name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getFinishDateTime() {
        return finishDateTime;
    }

    public void setFinishDateTime(Date finishDateTime) {
        this.finishDateTime = finishDateTime;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public Instant getStartTime() {
        if (startDateTime != null) {
            return startDateTime.toInstant();
        }
        return null;
    }

    public ClassroomDTO startTime(Instant startTime) {
        if (startTime != null) {
            this.startDateTime = DateUtil.convertInstantToDate(startTime);
        }
        return this;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setStartTime(Instant startTime) {
        this.startDateTime = DateUtil.convertInstantToDate(startTime);
    }

    public Instant getFinishTime() {
        if (finishDateTime != null) {
            return finishDateTime.toInstant();
        }
        return null;
    }

    public ClassroomDTO finishTime(Instant finishTime) {
        if (finishTime != null) {
            this.startDateTime = DateUtil.convertInstantToDate(finishTime);
        }
        return this;
    }

    public void setFinishTime(Instant finishTime) {
        this.finishDateTime = DateUtil.convertInstantToDate(finishTime);
    }

    public String getConnectionType() {
        return connectionType;
    }

    public ClassroomDTO connectionType(String connectionType) {
        this.connectionType = connectionType;
        return this;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public User getMaster() {
        return master;
    }

    public ClassroomDTO master(User user) {
        this.master = user;
        return this;
    }

    public Integer getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(Integer frameRate) {
        this.frameRate = frameRate;
    }

    public void setMaster(User user) {
        this.master = user;
    }

    public Set<User> getStudents() {
        return students;
    }

    public boolean isUseEnterToken() {
        return useEnterToken;
    }

    public void setUseEnterToken(boolean useEnterToken) {
        this.useEnterToken = useEnterToken;
    }

    public Boolean getHideGlobalChat() {
        return hideGlobalChat;
    }

    public void setHideGlobalChat(Boolean hideGlobalChat) {
        this.hideGlobalChat = hideGlobalChat;
    }

    public Boolean getHidePrivateChat() {
        return hidePrivateChat;
    }

    public void setHidePrivateChat(Boolean hidePrivateChat) {
        this.hidePrivateChat = hidePrivateChat;
    }

    public Boolean getHideParticipantsList() {
        return hideParticipantsList;
    }

    public void setHideParticipantsList(Boolean hideParticipantsList) {
        this.hideParticipantsList = hideParticipantsList;
    }

    public Boolean getDisableFileTransfer() {
        return disableFileTransfer;
    }

    public void setDisableFileTransfer(Boolean disableFileTransfer) {
        this.disableFileTransfer = disableFileTransfer;
    }

    public Boolean getHideSoundSensitive() {
        return hideSoundSensitive;
    }

    public void setHideSoundSensitive(Boolean hideSoundSensitive) {
        this.hideSoundSensitive = hideSoundSensitive;
    }

    public Boolean getHidePublishPermit() {
        return hidePublishPermit;
    }

    public void setHidePublishPermit(Boolean hidePublishPermit) {
        this.hidePublishPermit = hidePublishPermit;
    }

    public Boolean getEnableSubscriberDirectEnter() {
        return enableSubscriberDirectEnter;
    }

    public void setEnableSubscriberDirectEnter(Boolean enableSubscriberDirectEnter) {
        this.enableSubscriberDirectEnter = enableSubscriberDirectEnter;
    }

    public Boolean getPublisherMustEnterFirst() {
        return publisherMustEnterFirst;
    }

    public void setPublisherMustEnterFirst(Boolean publisherMustEnterFirst) {
        this.publisherMustEnterFirst = publisherMustEnterFirst;
    }

    public Integer getMaxUserCount() {
        return maxUserCount;
    }

    public void setMaxUserCount(Integer maxUserCount) {
        this.maxUserCount = maxUserCount;
    }

    public Boolean getSignalSessionEnd() {
        return signalSessionEnd;
    }

    public void setSignalSessionEnd(Boolean signalSessionEnd) {
        this.signalSessionEnd = signalSessionEnd;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public ClassroomDTO students(Set<User> users) {
        this.students = users;
        return this;
    }


    public void setStudents(Set<User> users) {
        this.students = users;
    }

    public User getCreator() {
        return creator;
    }

    public ClassroomDTO creator(User user) {
        this.creator = user;
        return this;
    }

    public void setCreator(User user) {
        this.creator = user;
    }

    public User getLastModifier() {
        return lastModifier;
    }

    public ClassroomDTO lastModifier(User lastModifier) {
        this.lastModifier = lastModifier;
        return this;
    }

    public void setLastModifier(User lastModifier) {
        this.lastModifier = lastModifier;
    }

    public String getSessionUuidName() {
        return sessionUuidName;
    }

    public void setSessionUuidName(String sessionUuidName) {
        this.sessionUuidName = sessionUuidName;
    }

    public boolean isGuestSession() {
        return guestSession;
    }

    public void setGuestSession(boolean guestSession) {
        this.guestSession = guestSession;
    }

    public boolean isGenerateRandomName() {
        return generateRandomName;
    }

    public void setGenerateRandomName(boolean generateRandomName) {
        this.generateRandomName = generateRandomName;
    }

    public boolean isGuestSessionReqPass() {
        return guestSessionReqPass;
    }

    public void setGuestSessionReqPass(boolean guestSessionReqPass) {
        this.guestSessionReqPass = guestSessionReqPass;
    }

    public boolean getIsGuestWithSubscriberRole() {
        return isGuestWithSubscriberRole;
    }

    public void setIsGuestWithSubscriberRole(boolean guestWithSubscriberRole) {
        isGuestWithSubscriberRole = guestWithSubscriberRole;
    }


    public String getGuestPassword() {
        return guestPassword;
    }

    public void setGuestPassword(String guestPassword) {
        this.guestPassword = guestPassword;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        com.pmp.nwms.domain.Classroom classroom = (com.pmp.nwms.domain.Classroom) o;
        if (classroom.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classroom.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Classroom{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", finishTime='" + getFinishTime() + "'" +
            "}";
    }

    public static Classroom build(ClassroomDTO dto) {
        Classroom classroom = new Classroom();
        if (dto.getId() != null)
            classroom.setId(dto.getId());
        classroom.setConnectionType(dto.connectionType);
        classroom.setGuestPassword(dto.getGuestPassword());
        classroom.setResolution(dto.getResolution());
        classroom.setFrameRate(dto.getFrameRate());
        classroom.setStartDateTime(dto.getStartDateTime());
        classroom.setFinishDateTime(dto.getFinishDateTime());
        classroom.setName(dto.getName());
        classroom.setSessionUuidName(dto.getName());
        classroom.setGuestSession(dto.isGuestSession());
        classroom.setGenerateRandomName(dto.isGenerateRandomName());
        return classroom;
    }
}

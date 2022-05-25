package com.pmp.nwms.util;

import com.pmp.nwms.domain.*;
import com.pmp.nwms.model.ClassroomStudentModel;
import com.pmp.nwms.repository.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class FullDeleteUtil {
    public static User moveUser(Long userId, UserRepository userRepository, DeletedUserRepository deletedUserRepository) {
        User user = userRepository.findById(userId).get();
        DeletedUser deletedUser = new DeletedUser();
        deletedUser.setId(user.getId());
        deletedUser.setLogin(user.getLogin());
        deletedUser.setPassword(user.getPassword());
        deletedUser.setFirstName(user.getFirstName());
        deletedUser.setLastName(user.getLastName());
        deletedUser.setPersonalCode(user.getPersonalCode());
        deletedUser.setPhoneNumber(user.getPhoneNumber());
        deletedUser.setShowyName(user.getShowyName());
        deletedUser.setGender(user.getGender());
        if (user.getValidityDate() != null) {
            deletedUser.setValidityDate(ZonedDateTime.ofInstant(user.getValidityDate().toInstant(), ZoneId.systemDefault()));
        }
        deletedUser.setEmail(user.getEmail());
        deletedUser.setActivated(user.getActivated());
        deletedUser.setLangKey(user.getLangKey());
        deletedUser.setImageUrl(user.getImageUrl());
        deletedUser.setActivationKey(user.getActivationKey());
        deletedUser.setResetKey(user.getResetKey());
        if (user.getResetDate() != null) {
            deletedUser.setResetDate(user.getResetDate().toInstant());
        }
        deletedUser.setAuthorities(user.getAuthorities());
        if (user.getOrganizationLevel() != null) {
            deletedUser.setOrganizationLevelId(user.getOrganizationLevel().getId());
        }
        deletedUser.setReturnUrl(user.getReturnUrl());
        deletedUser.setWsUrl(user.getWsUrl());
        deletedUser.setOriginalCreatedBy(user.getCreatedBy());
        deletedUser.setOriginalCreatedDate(user.getCreatedDate());
        deletedUser.setOriginalLastModifiedBy(user.getLastModifiedBy());
        deletedUser.setOriginalLastModifiedDate(user.getLastModifiedDate());
        deletedUser.setOriginalVersion(user.getVersion());
        deletedUser = deletedUserRepository.save(deletedUser);
        return user;
    }

    public static void moveClassroomStudentsByClassroomId(Long classroomId, ClassroomStudentRepository classroomStudentRepository, DeletedClassroomStudentRepository deletedClassroomStudentRepository) {
        List<ClassroomStudentModel> classroomStudentModels = classroomStudentRepository.findClassroomStudentModelsUsingClassroomId(classroomId);
        moveClassroomStudents(deletedClassroomStudentRepository, classroomStudentModels);
    }

    public static void moveClassroomStudentsByUserId(Long userId, ClassroomStudentRepository classroomStudentRepository, DeletedClassroomStudentRepository deletedClassroomStudentRepository) {
        List<ClassroomStudentModel> classroomStudentModels = classroomStudentRepository.findClassroomStudentModelsUsingStudentId(userId);
        moveClassroomStudents(deletedClassroomStudentRepository, classroomStudentModels);
    }

    public static void moveClassroom(Classroom classroom, DeletedClassroomRepository deletedClassroomRepository) {
        DeletedClassroom deletedClassroom = new DeletedClassroom();
        deletedClassroom.setId(classroom.getId());
        deletedClassroom.setName(classroom.getName());
        deletedClassroom.setSessionUuidName(classroom.getSessionUuidName());
        deletedClassroom.setGuestSession(classroom.isGuestSession());
        deletedClassroom.setGuestSessionReqPass(classroom.isGuestSessionReqPass());
        deletedClassroom.setGuestPassword(classroom.getGuestPassword());
        deletedClassroom.setStartDateTime(classroom.getStartDateTime());
        deletedClassroom.setFinishDateTime(classroom.getFinishDateTime());
        deletedClassroom.setConnectionType(classroom.getConnectionType());
        deletedClassroom.setResolution(classroom.getResolution());
        deletedClassroom.setFrameRate(classroom.getFrameRate());
        deletedClassroom.setMasterId(classroom.getMaster().getId());
        deletedClassroom.setCreatorId(classroom.getCreator().getId());
        deletedClassroom.setLock(classroom.getLock());
        deletedClassroom.setGuestWithSubscriberRole(classroom.getIsGuestWithSubscriberRole());
        deletedClassroom.setLastModifierId(classroom.getLastModifier().getId());
        deletedClassroom.setCourseId(classroom.getCourse().getId());
        deletedClassroom.setUseEnterToken(classroom.getUseEnterToken());
        deletedClassroom.setHideGlobalChat(classroom.getHideGlobalChat());
        deletedClassroom.setHidePrivateChat(classroom.getHidePrivateChat());
        deletedClassroom.setHideParticipantsList(classroom.getHideParticipantsList());
        deletedClassroom.setDisableFileTransfer(classroom.getDisableFileTransfer());
        deletedClassroom.setHideSoundSensitive(classroom.getHideSoundSensitive());
        deletedClassroom.setHidePublishPermit(classroom.getHidePublishPermit());
        deletedClassroom.setEnableSubscriberDirectEnter(classroom.getEnableSubscriberDirectEnter());
        deletedClassroom.setPublisherMustEnterFirst(classroom.getPublisherMustEnterFirst());
        deletedClassroom.setMaxUserCount(classroom.getMaxUserCount());
        deletedClassroom.setHideScreen(classroom.getHideScreen());
        deletedClassroom.setHideWhiteboard(classroom.getHideWhiteboard());
        deletedClassroom.setHideSlide(classroom.getHideSlide());
        deletedClassroom.setRecordingMode(classroom.getRecordingMode());
        deletedClassroom.setModeratorAutoLogin(classroom.getModeratorAutoLogin());
        deletedClassroom.setSecretKey(classroom.getSecretKey());
        deletedClassroom.setSessionActive(classroom.isSessionActive());
        deletedClassroom.setSignalSessionEnd(classroom.isSignalSessionEnd());
        deletedClassroom.setReturnUrl(classroom.getReturnUrl());
        deletedClassroom.setOuterManage(classroom.isOuterManage());
        deletedClassroom.setNonManagerEnterSoundOff(classroom.isNonManagerEnterSoundOff());
        deletedClassroom.setNonManagerEnterVideoOff(classroom.isNonManagerEnterVideoOff());
        deletedClassroom.setOriginalCreatedBy(classroom.getCreatedBy());
        deletedClassroom.setOriginalCreatedDate(classroom.getCreatedDate());
        deletedClassroom.setOriginalLastModifiedBy(classroom.getLastModifiedBy());
        deletedClassroom.setOriginalLastModifiedDate(classroom.getLastModifiedDate());
        deletedClassroom.setOriginalVersion(classroom.getVersion());
        deletedClassroom = deletedClassroomRepository.save(deletedClassroom);
    }

    private static void moveClassroomStudents(DeletedClassroomStudentRepository deletedClassroomStudentRepository, List<ClassroomStudentModel> classroomStudentModels) {
        if (classroomStudentModels != null && !classroomStudentModels.isEmpty()) {
            ArrayList<DeletedClassroomStudent> deletedClassroomStudents = new ArrayList<>();
            for (ClassroomStudentModel model : classroomStudentModels) {
                DeletedClassroomStudent e = new DeletedClassroomStudent();
                e.setClassroomId(model.getClassroomId());
                e.setStudentId(model.getStudentId());
                e.setAuthorityName(model.getAuthorityName());
                e.setFullName(model.getFullName());
                e.setNeedsLogin(model.isNeedsLogin());
                e.setToken(model.getToken());
                e.setMaxUseCount(model.getMaxUseCount());
                e.setOriginalCreatedBy(model.getCreatedBy());
                e.setOriginalCreatedDate(model.getCreatedDate());
                e.setOriginalLastModifiedBy(model.getLastModifiedBy());
                e.setOriginalLastModifiedDate(model.getLastModifiedDate());
                e.setOriginalVersion(model.getVersion());
                deletedClassroomStudents.add(e);
            }
            deletedClassroomStudentRepository.saveAll(deletedClassroomStudents);
        }
    }

    public static void moveClassroomStudentsByCourseId(Long courseId, ClassroomStudentRepository classroomStudentRepository, DeletedClassroomStudentRepository deletedClassroomStudentRepository) {
        List<ClassroomStudentModel> classroomStudentModels = classroomStudentRepository.findClassroomStudentModelsUsingCourseId(courseId);
        moveClassroomStudents(deletedClassroomStudentRepository, classroomStudentModels);
    }

    public static void moveCourseStudents(Long userId, CourseRepository courseRepository, DeletedCourseStudentRepository deletedCourseStudentRepository) {
        List<Long> courseIds = courseRepository.findCourseStudentIdsByUserId(userId);
        if (courseIds != null && !courseIds.isEmpty()) {
            ArrayList<DeletedCourseStudent> deletedCourseStudents = new ArrayList<>();
            for (Long courseId : courseIds) {
                DeletedCourseStudent e = new DeletedCourseStudent();
                e.setCourseId(courseId);
                e.setUserId(userId);
                deletedCourseStudents.add(e);
            }
            deletedCourseStudentRepository.saveAll(deletedCourseStudents);
        }
    }

}

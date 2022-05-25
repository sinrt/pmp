package com.pmp.nwms.service.enums;

public enum ClassroomStatusResultCode {
    Success("000", "classroom.status.result.message.success", ClassroomEntranceType.EnterWithSoundAndVideo),
    ClassroomNotFound("001", "classroom.status.result.message.classroom.not.found", ClassroomEntranceType.EntranceUnauthorized),
    ClassroomOwnerAccountExpired("002", "classroom.status.result.message.classroom.owner.account.expired", ClassroomEntranceType.EntranceUnauthorized),
    ClassroomStartTimeNotArrived("003", "classroom.status.result.message.classroom.start.time.not.arrived", ClassroomEntranceType.EntranceUnauthorized),
    ClassroomEndTimePassed("004", "classroom.status.result.message.classroom.end.time.passed", ClassroomEntranceType.EntranceUnauthorized),
    ClassroomAvailableOnlyForMembers("005", "classroom.status.result.message.classroom.available.only.for.members", ClassroomEntranceType.EnterAfterLoggingIn),
    ClassroomAvailableOnlyForMembersWithToken("006", "classroom.status.result.message.classroom.available.only.for.members.with.token", ClassroomEntranceType.EntranceUnauthorized),
    InvalidClassroomStudentToken("007", "classroom.status.result.message.invalid.classroom.student.token", ClassroomEntranceType.EntranceUnauthorized),
    ClassroomPurchaseStatusMaxUserCountExceeded("008", "classroom.status.result.message.classroom.purchase.status.max.user.count.exceeded", ClassroomEntranceType.EntranceUnauthorized),
    OwnerAccountMaxUserCountExceeded("009", "classroom.status.result.message.owner.account.max.user.count.exceeded", ClassroomEntranceType.EntranceUnauthorized),
    EnterAsViewerOnly("010", "classroom.status.result.message.enter.as.viewer.only", ClassroomEntranceType.EnterWithoutSoundAndVideo),
    TokenMaxUserCountExceeded("011", "classroom.status.result.message.token.max.user.count.exceeded", ClassroomEntranceType.EntranceUnauthorized),
    ClassroomAvailableOnlyForSavedMembers("012", "classroom.status.result.message.classroom.available.only.for.saved.members", ClassroomEntranceType.EntranceUnauthorized),
    ClassroomLocked("013", "classroom.status.result.message.classroom.locked", ClassroomEntranceType.EntranceUnauthorized),
    SessionNotStrated("014", "classroom.status.result.message.session.not.strated", ClassroomEntranceType.EntranceUnauthorized),
    ClassroomMaxUserCountExceeded("015", "classroom.status.result.message.classroom.max.user.count.exceeded", ClassroomEntranceType.EntranceUnauthorized),
    BlockedClientId("016", "classroom.status.result.message.blocked.client.id", ClassroomEntranceType.EntranceUnauthorized),

    InvalidClassroomSettings("017", "classroom.status.result.message.invalid.classroom.settings", ClassroomEntranceType.EntranceUnauthorized),
    InvalidChecksumInput("018", "classroom.status.result.message.invalid.checksum.input", ClassroomEntranceType.EntranceUnauthorized),
    InvalidClassroomStudentSettings("019", "classroom.status.result.message.invalid.classroom.student.settings", ClassroomEntranceType.EntranceUnauthorized),

    AuthorizedHoursInWeekExceeded("020", "classroom.status.result.message.authorized.hours.in.week.exceeded", ClassroomEntranceType.EntranceUnauthorized),
    AuthorizedHoursInMonthExceeded("021", "classroom.status.result.message.authorized.hours.in.month.exceeded", ClassroomEntranceType.EntranceUnauthorized),
//    MaxValidWebcamCountExceeded("011", ""),
    ;
    private String code;
    private String messageKey;
    private ClassroomEntranceType entranceType;

    ClassroomStatusResultCode(String code, String messageKey, ClassroomEntranceType entranceType) {
        this.code = code;
        this.messageKey = messageKey;
        this.entranceType = entranceType;
    }

    public String getCode() {
        return code;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public ClassroomEntranceType getEntranceType() {
        return entranceType;
    }
}

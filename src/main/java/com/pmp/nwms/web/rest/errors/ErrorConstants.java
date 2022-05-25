package com.pmp.nwms.web.rest.errors;

import java.net.URI;

public final class ErrorConstants {

    public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
    public static final String ERR_VALIDATION = "error.validation";
    public static final String PROBLEM_BASE_URL = "https://www.mehdade.rubru.me/problems";
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
    public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");
    public static final URI PARAMETERIZED_TYPE = URI.create(PROBLEM_BASE_URL + "/parameterized");
    public static final URI ENTITY_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/entity-not-found");
    public static final URI ENTITY_NOT_ACCESSIBLE_TYPE = URI.create(PROBLEM_BASE_URL + "/entity-not-accessible");
    public static final URI INVALID_PASSWORD_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-password");
    public static final URI EMAIL_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/email-already-used");
    public static final URI PERSONALCODE_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/personalCode-already-used");
    public static final URI CLASSROOM_FILE_NAME_LONG = URI.create(PROBLEM_BASE_URL + "/upload");
    public static final URI SENTCODE_INVALID_TYPE = URI.create(PROBLEM_BASE_URL + "/sentcode-invalid-type");
    public static final URI LOGIN_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/login-already-used");
    public static final URI COURSE_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/coursename-already-used");
    public static final URI EMAIL_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/email-not-found");
    public static final URI PHONE_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/phone-already-used");
    public static final URI CLASSROOM_NAME_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/classroom-name-already-used");
    public static final URI CLASSROOM_SESSION_UUID_NAME_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/classroom-session-uuid-name-already-used");
    public static final URI NO_ENTITY_DELETED_TYPE = URI.create(PROBLEM_BASE_URL + "/no-entity-deleted");
    public static final URI USER_HAS_NO_ACTIVE_PLAN = URI.create(PROBLEM_BASE_URL + "/no-active-plan");
    public static final URI CLASSROOM_MAX_COUNT_EXCEEDED_TYPE = URI.create(PROBLEM_BASE_URL + "/classroom-max-count-exceeded");
    public static final URI CLASSROOM_NAME_BAD_FORMAT_TYPE = URI.create(PROBLEM_BASE_URL + "/classroom-name-format");
    public static final URI SESSION_PARTICIPANT_NOT_FOUND_BY_ID_TYPE = URI.create(PROBLEM_BASE_URL + "/participant-not-found");
    public static final URI NO_ACTIVE_SESSION_FOUND_BY_ID_TYPE = URI.create(PROBLEM_BASE_URL + "/session-not-found");
    public static final URI CLASSROOM_NOT_FOUND_BY_NAME_TYPE = URI.create(PROBLEM_BASE_URL + "/classroom-not-found-by-name");
    public static final URI INVALID_FILE_SIZE_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-file-size");
    public static final URI MAX_FILE_COUNT_EXCEEDED_TYPE = URI.create(PROBLEM_BASE_URL + "/max-file-count-exceeded");
    public static final URI CLASSROOM_FILE_ALREADY_IN_STATUS_TYPE = URI.create(PROBLEM_BASE_URL + "/file-already-in-status");
    public static final URI INVALID_DATE_FORMAT_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-date-format");
    public static final URI COURSE_TITLE_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/course-title-already-used");
    public static final URI INVALID_COURSE_DATE_PERIOD_TYPE = URI.create(PROBLEM_BASE_URL + "/course-invalid-date-period");
    public static final URI INVALID_CLASSROOM_STUDENT_AUTHORITY_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-classroom-student-authority");
    public static final URI INVALID_CLASSROOM_COUNT_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-classroom-count");
    public static final URI INVALID_FILE_FORMAT = URI.create(PROBLEM_BASE_URL + "/invalid-file-format");
    public static final URI INVALID_CELL_TYPE_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-excel-cell-type");
    public static final URI INVALID_CELL_VALUE_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-excel-cell-value");
    public static final URI CLASSROOM_DOES_NOT_BELONG_TO_COURSE_TYPE = URI.create(PROBLEM_BASE_URL + "/classroom-does-not-belong-to-course");
    public static final URI INVALID_FILE_NAME_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-file-name");
    public static final URI INVALID_FILE_SHEET_NUMBERS_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-file-sheets-number");
    public static final URI INVALID_SHEET_NAME_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-file-sheets-name");
    public static final URI INVALID_COLUMN_HEADER_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-column-name");
    public static final URI INVALID_SHEET_DIRECTION_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-sheet-direction");
    public static final URI INVALID_CLASSROOM_STUDENT_NEEDS_LOGIN = URI.create(PROBLEM_BASE_URL + "/invalid-classroom-student-needs-login");
    public static final URI INVALID_CLASSROOM_MAX_USER_COUNT_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-classroom-max-user-count");
    public static final URI NO_CLASSROOM_SETTINGS_SENT_TYPE = URI.create(PROBLEM_BASE_URL + "/no-classroom-settings-sent");
    public static final URI NO_CLASSROOM_SETTINGS_CHANGED_TYPE = URI.create(PROBLEM_BASE_URL + "/no-classroom-settings-changed");
    public static final URI UNSUPPORTED_FILE_TYPE_FOR_SLIDING_TYPE = URI.create(PROBLEM_BASE_URL + "/unsupported-file-type-for-sliding");
    public static final URI CLASSROOM_FILE_NOT_SLIDED_TYPE = URI.create(PROBLEM_BASE_URL + "/classroom-file-not-slided-type");
    public static final URI INVALID_SLIDE_NUMBERS_PERIOD_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-slide-numbers-period");;
    public static final URI INVALID_DOCUMENT_PAGE_COUNT_FOR_SLIDING_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-document-page-count-for-sliding");;
    public static final URI ENTITY_CAN_NOT_BE_DELETED_TYPE = URI.create(PROBLEM_BASE_URL + "/entity-can-not-be-deleted-type");;
    public static final URI SMS_SERVICE_NOT_READY_TYPE = URI.create(PROBLEM_BASE_URL + "/sms-service-not-ready-type");
    public static final URI CLASSROOM_SURVEY_OPTION_REQUIRED_TYPE = URI.create(PROBLEM_BASE_URL + "/classroom-survey-option-required-type");
    public static final URI CLASSROOM_SURVEY_OPTION_MIN_COUNT_REQUIRED_TYPE = URI.create(PROBLEM_BASE_URL + "/classroom-survey-option-min-count-required-type");
    public static final URI CLASSROOM_SURVEY_OPTION_MAX_COUNT_REQUIRED_TYPE = URI.create(PROBLEM_BASE_URL + "/classroom-survey-option-max-count-required-type");
    public static final URI INVALID_CLASSROOM_SURVEY_OPTION_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-classroom-survey-option-type");
    public static final URI UNABLE_TO_CHANGE_FIELD_VALUE_TYPE = URI.create(PROBLEM_BASE_URL + "/unable-to-change-field-value-type");
    public static final URI ENTITY_CAN_NOT_BE_EDITED_TYPE = URI.create(PROBLEM_BASE_URL + "/entity-can-not-be-edited-type");
    public static final URI DUPLICATED_CLASSROOM_SURVEY_OPTION_ORDER_TYPE = URI.create(PROBLEM_BASE_URL + "/duplicated-classroom-survey-option-order-type");
    public static final URI DUPLICATED_CLASSROOM_SURVEY_OPTION_TEXT_TYPE = URI.create(PROBLEM_BASE_URL + "/duplicated-classroom-survey-option-text-type");
    public static final URI INVALID_CLASSROOM_SURVEY_STATUS_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-classroom-survey-status-type");
    public static final URI INVALID_CLASSROOM_TOKEN_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-classroom-token-type");
    public static final URI CLASSROOM_SURVEY_CAN_NOT_BE_REANSWERED_TYPE = URI.create(PROBLEM_BASE_URL + "/classroom-survey-can-not-be-reanswered-type");
    public static final URI CLASSROOM_SURVEYS_MUST_BELONG_TO_SAME_CLASSROOM_TYPE = URI.create(PROBLEM_BASE_URL + "/classroom-surveys-must-belong-to-same-classroom-type");
    public static final URI CLASSROOM_SURVEY_CAN_NOT_BE_ANSWERED_TYPE = URI.create(PROBLEM_BASE_URL + "/classroom-survey-can-not-be-answered");
    public static final URI CLASSROOM_SURVEY_ANSWER_TEXT_CAN_NOT_BE_EMPTY_TYPE = URI.create(PROBLEM_BASE_URL + "/classroom-survey-answer-text-can-not-be-empty");
    public static final URI CLASSROOM_SURVEY_ANSWER_OPTION_MUST_BE_SELECTED_TYPE = URI.create(PROBLEM_BASE_URL + "/classroom-survey-answer-option-must-be-selected-type");
    public static final URI CLASSROOM_SURVEY_ANSWER_OPTION_MUST_BELONG_TO_SURVEY_TYPE = URI.create(PROBLEM_BASE_URL + "/classroom-survey-answer-option-must-belong-to-survey");
    public static final URI CLASSROOM_SURVEY_TYPE_NOT_SUPPORTED_TYPE = URI.create(PROBLEM_BASE_URL + "/classroom-survey-type-not-supported");
    public static final URI NOT_WEBINAR_COURSE_TYPE = URI.create(PROBLEM_BASE_URL + "/not-webinar-course");;
    public static final URI SPECIAL_LINK_INVALID_FORMAT_TYPE = URI.create(PROBLEM_BASE_URL + "/special-link-invalid-format");;
    public static final URI CLASSROOM_RECORDING_INFO_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/classroom-recording-info-not-found-type");;
    public static final URI UNABLE_TO_DOWNLOAD_CLASSROOM_RECORDING_DUE_TO_RECORDING_STORAGE_STATUS = URI.create(PROBLEM_BASE_URL + "/unable-to-download-classroom-recording-due-to-recording-storage-status");
    public static final URI CLASSROOM_SECRET_KEY_NOT_DEFINED_TYPE = URI.create(PROBLEM_BASE_URL + "/classroom-secret-key-not-defined-type");
    public static final URI UNABLE_TO_DELETE_CLASSROOM_DUE_TO_ACTIVE_SESSION_TYPE = URI.create(PROBLEM_BASE_URL + "/unable-to-delete-classroom-due-to-active-session-type");
    public static final URI CLASSROOM_NOT_OUTER_MANAGED_TYPE = URI.create(PROBLEM_BASE_URL + "/classroom-not-outer-managed-type");
    public static final URI SESSION_NOT_STARTED_TYPE = URI.create(PROBLEM_BASE_URL + "/session-not-started-type");
    public static final URI PARTICIPANT_NOT_FOUND_IN_SESSION_TYPE = URI.create(PROBLEM_BASE_URL + "/participant-not-found-in-session-type");
    public static final URI MISSING_REQUIRED_PARAM_TYPE = URI.create(PROBLEM_BASE_URL + "/missing-required-param-type");
    public static final URI INVALID_CAPTCHA_VALUE_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-captcha-value-type");
    public static final URI INVALID_CLASSROOM_RECORDING_INFO_CHECKSUM_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-classroom-recording-info-checksum");;
    public static final URI CLASSROOM_FILE_SLIDING_IS_IN_PROGRESS_TYPE = URI.create(PROBLEM_BASE_URL + "/classroom-file-sliding-is-in-progress");;
    public static final URI CLASSROOM_FILE_SLIDING_STARTED_TYPE = URI.create(PROBLEM_BASE_URL + "/classroom-file-sliding-started");;

    private ErrorConstants() {
    }

}

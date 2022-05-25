package com.pmp.nwms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

@Component
public class NwmsConfig {
   /* @Value("${conf.server.address}")
    private String serverAddress;

    @Value("${conf.server.ssl.enabled}")
    private boolean serverSslEnabled;

    @Value("${rubru.recording.version}")
    private String rubruRecordingVersion;

    @Value("${conf.server.port}")
    private int serverPort;

    @Value("${rubru.publicurl}")
    private String rubruPublicUrl;

    @Value("${rubru.cdr}")
    private boolean rubruCdr;

    @Value("${rubru.recording.enabled}")
    private boolean rubruRecording;

    @Value("${rubru.recording.path}")
    private String rubruRecordingPath;

    @Value("${rubru.recording.public-access}")
    private boolean rubruRecordingPublicAccess;

    @Value("${rubru.recording.notification}")
    private String rubruRecordingNotification;

    @Value("${rubru.recording.custom-layout}")
    private String rubruRecordingCustomLayout;

    @Value("${rubru.recording.autostop-timeout}")
    private int rubruRecordingAutoStopTimeout;

    @Value("${rubru.streams.video.max-recv-bandwidth}")
    private int rubruStreamsVideoMaxRecvBandwidth;

    @Value("${rubru.streams.video.min-recv-bandwidth}")
    private int rubruStreamsVideoMinRecvBandwidth;

    @Value("${rubru.streams.video.max-send-bandwidth}")
    private int rubruStreamsVideoMaxSendBandwidth;

    @Value("${rubru.streams.video.min-send-bandwidth}")
    private int rubruStreamsVideoMinSendBandwidth;

    @Value("${kms.uris}")
    private List<String> kmsUris;

    @Value("${kms.stats-enabled}")
    private boolean kmsStatsEnabled;*/

    @Value("${rubru.wsUrl}")
    private String rubruWsUrl;

   /* @Value("${rubru.wsUrl.nginx}")
    private String rubruWsUrlNginx;

    @Value("${free_plan_credit_day}")
    private int freePlanCreditDay;*/

    @Value("${rubru.imageprofile.path}")
    private String rubruImageProfilePath;

    @Value("${rubru.rubruexlspath.path}")
    private String rubrueXlsPath;


    @Value("${app.web.url}")
    private String appWebUrl;

    @Value("${jackrabbit.rmi.root}")
    private String jackrabbitRmiRoot;

    @Value("${jackrabbit.username}")
    private String jackrabbitUsername;

    @Value("${jackrabbit.password}")
    private String jackrabbitPassword;

    @Value("${fileupload.max.file.size}")
    private int maxFileSize;

    @Value("${fileupload.max.file.countPerSession}")
    private int maxFileCountPerSession;


    @Value("${send.sms.service.wsdl-url}")
    private String sendSMSServiceWsdlUrl;
    @Value("${send.sms.service.rest-url}")
    private String sendSMSServiceRestUrl;
    @Value("${send.sms.service.username}")
    private String sendSMSServiceUsername;
    @Value("${send.sms.service.password}")
    private String sendSMSServicePassword;
    @Value("${send.sms.service.from-number}")
    private String sendSMSServiceFromNumber;
    @Value("${send.sms.service.is-flash}")
    private boolean sendSMSServiceIsFlash;
    @Value("${send.sms.service.use-soap}")
    private boolean sendSMSServiceUseSoap;

    @Value("${rubru.session.participants.report.excel.data.date.pattern}")
    private String rubruSessionParticipantsReportExcelDataDatePattern;
    @Value("${rubru.session.participants.report.filename.date.pattern}")
    private String rubruSessionParticipantsReportFileNameDatePattern;


    @Value("${sliding.image.dpi}")
    private int slidingImageDPI;

    @Value("${sliding.max.page.count}")
    private int slidingMaxPageCount;

    @Value("${classroom.survey.option.min.count}")
    private int classroomSurveyOptionMinCount;

    @Value("${classroom.survey.option.max.count}")
    private int classroomSurveyOptionMaxCount;

    @Value("${file.to.slide.convert.temp.folder}")
    private String fileToSlideConvertTempFolder;

    @Value("${converter.core.pool.size}")
    private int converterCorePoolSize;

    @Value("${converter.max.pool.size}")
    private int converterMaxPoolSize;

    @Value("${converter.keep.alive.time}")
    private long converterKeepAliveTime;

    @Value("${store.recording.files.scheduler.enabled}")
    private boolean storeRecordingFilesSchedulerEnabled;

    @Value("${signal.server.username}")
    private String signalServerUsername;

    @Value("${signal.server.password}")
    private String signalServerPassword;

    @Value("${signal.server.instant.delete.recordings}")
    private boolean instantDeleteRecordings;

    @Value("${remove.recording.files.scheduler.enabled}")
    private boolean removeRecordingFilesSchedulerEnabled;

    @Value("${keep.recordings.max.day.count}")
    private int keepRecordingsMaxDayCount;

    @Value("${finish.classroom.inform.signal.type}")
    private String finishClassroomInformSignalType;

    @Value("${finish.classroom.inform.max.time}")
    private int finishClassroomInformMaxTime;

    @Value("${finish.classroom.inform.timer.delay}")
    private long finishClassroomInformTimerDelay;

    @Value("${finish.classroom.inform.timer.period}")
    private long finishClassroomInformTimerPeriod;

    @Value("#{'${finish.classroom.inform.times}'.split(',')}")
    private Integer[] finishClassroomInformTimes;

    @Value("${max.archive.count.per.run}")
    private int maxArchiveCountPerRun;

    @Value("${archive.rubru.session.info.scheduler.enabled}")
    private boolean archiveRubruSessionInfoSchedulerEnabled;

    @Value("${archive.rubru.session.info.after.months}")
    private int archiveRubruSessionInfoAfterMonths;

    @Value("${end.session.close.timer.enabled}")
    private boolean endSessionCloseTimerEnabled;

    @Value("${end.session.send.signal.timer.enabled}")
    private boolean endSessionSendSignalTimerEnabled;

    @Value("${token.session.delimiter}")
    private String tokenSessionDelimiter;

    @Value("${captcha.font.size.min}")
    private int captchaMinFontSize;

    @Value("${captcha.font.size.max}")
    private int captchaMaxFontSize;

    @Value("${captcha.background.color.rgb.first}")
    private String captchaBackgroundFirstColorRGB;

    @Value("${captcha.background.color.rgb.second}")
    private String captchaBackgroundSecondColorRGB;

    @Value("${captcha.image.width}")
    private int captchaImageWidth;

    @Value("${captcha.image.height}")
    private int captchaImageHeight;

    @Value("${captcha.text.color.rgb}")
    private String captchaTextColorRGB;

    @Value("${captcha.accepted.word.length.min}")
    private int captchaMinAcceptedWordLength;

    @Value("${captcha.accepted.word.length.max}")
    private int captchaMaxAcceptedWordLength;

    @Value("${captcha.required.failed.login.valid.minutes}")
    private int captchaRequiredFailedLoginValidMinutes;

    @Value("${captcha.required.failed.login.count}")
    private int captchaRequiredFailedLoginCount;

    @Value("${classroom.file.remover.scheduler.enabled}")
    private boolean classroomFileRemoverSchedulerEnabled;

    @Value("${classroom.file.remover.scheduler.keep.max.day.count}")
    private int classroomFileRemoverSchedulerKeepMaxDayCount;

    @Value("${remove.logs.scheduler.enabled}")
    private boolean removeLogsSchedulerEnabled;

    @Value("${remove.logs.scheduler.keep.max.day.count}")
    private int removeLogsSchedulerKeepMaxDayCount;

    @Value("${rubru.remove.logs.scheduler.enabled}")
    private boolean rubruRemoveLogsSchedulerEnabled;

    @Value("${rubru.remove.logs.scheduler.keep.max.day.count}")
    private int rubruRemoveLogsSchedulerKeepMaxDayCount;

    @Value("${passargad.ipg.rsakeyvalue.modulus}")
    private String passargadIpgRSAKeyValueModulus;
    @Value("${passargad.ipg.rsakeyvalue.exponent}")
    private String passargadIpgRSAKeyValueExponent;
    @Value("${passargad.ipg.rsakeyvalue.p}")
    private String passargadIpgRSAKeyValueP;
    @Value("${passargad.ipg.rsakeyvalue.q}")
    private String passargadIpgRSAKeyValueQ;
    @Value("${passargad.ipg.rsakeyvalue.dp}")
    private String passargadIpgRSAKeyValueDP;
    @Value("${passargad.ipg.rsakeyvalue.dq}")
    private String passargadIpgRSAKeyValueDQ;
    @Value("${passargad.ipg.rsakeyvalue.inverseq}")
    private String passargadIpgRSAKeyValueInverseQ;
    @Value("${passargad.ipg.rsakeyvalue.d}")
    private String passargadIpgRSAKeyValueD;

    @Value("${passargad.ipg.root.address}")
    private String passargadIpgRootAddress;
    @Value("${passargad.ipg.gettoken.address}")
    private String passargadIpgGetTokenAddress;
    @Value("${passargad.ipg.checktransactionresult.address}")
    private String passargadIpgCheckTransactionResultAddress;
    @Value("${passargad.ipg.verifypayment.address}")
    private String passargadIpgVerifyPaymentAddress;
    @Value("${passargad.ipg.terminalcode}")
    private String passargadIpgTerminalCode;
    @Value("${passargad.ipg.merchantcode}")
    private String passargadIpgMerchantCode;



    /*public String getServerAddress() {
        return serverAddress;
    }

    public boolean isServerSslEnabled() {
        return serverSslEnabled;
    }

    public String getRubruRecordingVersion() {
        return rubruRecordingVersion;
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getRubruPublicUrl() {
        return rubruPublicUrl;
    }

    public boolean isRubruCdr() {
        return rubruCdr;
    }

    public boolean isRubruRecording() {
        return rubruRecording;
    }

    public String getRubruRecordingPath() {
        return rubruRecordingPath;
    }

    public boolean isRubruRecordingPublicAccess() {
        return rubruRecordingPublicAccess;
    }

    public String getRubruRecordingNotification() {
        return rubruRecordingNotification;
    }

    public String getRubruRecordingCustomLayout() {
        return rubruRecordingCustomLayout;
    }

    public int getRubruRecordingAutoStopTimeout() {
        return rubruRecordingAutoStopTimeout;
    }

    public int getRubruStreamsVideoMaxRecvBandwidth() {
        return rubruStreamsVideoMaxRecvBandwidth;
    }

    public int getRubruStreamsVideoMinRecvBandwidth() {
        return rubruStreamsVideoMinRecvBandwidth;
    }

    public int getRubruStreamsVideoMaxSendBandwidth() {
        return rubruStreamsVideoMaxSendBandwidth;
    }

    public int getRubruStreamsVideoMinSendBandwidth() {
        return rubruStreamsVideoMinSendBandwidth;
    }

    public List<String> getKmsUris() {
        return kmsUris;
    }

    public boolean isKmsStatsEnabled() {
        return kmsStatsEnabled;
    }*/

    public String getRubruWsUrl() {
        return rubruWsUrl;
    }

    /*public String getRubruWsUrlNginx() {
        return rubruWsUrlNginx;
    }

    public int getFreePlanCreditDay() {
        return freePlanCreditDay;
    }*/

    public String getRubruImageProfilePath() {
        return rubruImageProfilePath;
    }

    public String getRubrueXlsPath() {
        return rubrueXlsPath;
    }

    public String getAppWebUrl() {
        return appWebUrl;
    }

    public String getJackrabbitRmiRoot() {
        return jackrabbitRmiRoot;
    }

    public String getJackrabbitUsername() {
        return jackrabbitUsername;
    }

    public char[] getJackrabbitPassword() {
        return jackrabbitPassword.toCharArray();
    }

    public int getMaxFileSize() {
        return maxFileSize;
    }

    public int getMaxFileCountPerSession() {
        return maxFileCountPerSession;
    }

    public String getSendSMSServiceWsdlUrl() {
        return sendSMSServiceWsdlUrl;
    }

    public String getSendSMSServiceRestUrl() {
        return sendSMSServiceRestUrl;
    }

    public String getSendSMSServiceUsername() {
        return sendSMSServiceUsername;
    }

    public String getSendSMSServicePassword() {
        return sendSMSServicePassword;
    }

    public String getSendSMSServiceFromNumber() {
        return sendSMSServiceFromNumber;
    }

    public boolean getSendSMSServiceIsFlash() {
        return sendSMSServiceIsFlash;
    }

    public boolean isSendSMSServiceUseSoap() {
        return sendSMSServiceUseSoap;
    }

    public String getRubruSessionParticipantsReportExcelDataDatePattern() {
        return rubruSessionParticipantsReportExcelDataDatePattern;
    }

    public String getRubruSessionParticipantsReportFileNameDatePattern() {
        return rubruSessionParticipantsReportFileNameDatePattern;
    }

    public int getSlidingImageDPI() {
        return slidingImageDPI;
    }

    public int getSlidingMaxPageCount() {
        return slidingMaxPageCount;
    }

    public int getClassroomSurveyOptionMinCount() {
        return classroomSurveyOptionMinCount;
    }

    public int getClassroomSurveyOptionMaxCount() {
        return classroomSurveyOptionMaxCount;
    }

    public String getFileToSlideConvertTempFolder() {
        return fileToSlideConvertTempFolder;
    }

    public int getConverterCorePoolSize() {
        return converterCorePoolSize;
    }

    public int getConverterMaxPoolSize() {
        return converterMaxPoolSize;
    }

    public long getConverterKeepAliveTime() {
        return converterKeepAliveTime;
    }

    public boolean isStoreRecordingFilesSchedulerEnabled() {
        return storeRecordingFilesSchedulerEnabled;
    }

    public String getSignalServerUsername() {
        return signalServerUsername;
    }

    public String getSignalServerPassword() {
        return signalServerPassword;
    }

    public boolean isInstantDeleteRecordings() {
        return instantDeleteRecordings;
    }

    public boolean isRemoveRecordingFilesSchedulerEnabled() {
        return removeRecordingFilesSchedulerEnabled;
    }

    public int getKeepRecordingsMaxDayCount() {
        return keepRecordingsMaxDayCount;
    }

    public List<Integer> getFinishClassroomInformTimes() {
        return Arrays.asList(finishClassroomInformTimes);
    }

    public String getFinishClassroomInformSignalType() {
        return finishClassroomInformSignalType;
    }

    public int getFinishClassroomInformMaxTime() {
        return finishClassroomInformMaxTime;
    }

    public long getFinishClassroomInformTimerDelay() {
        return finishClassroomInformTimerDelay;
    }

    public long getFinishClassroomInformTimerPeriod() {
        return finishClassroomInformTimerPeriod;
    }

    public int getMaxArchiveCountPerRun() {
        return maxArchiveCountPerRun;
    }

    public boolean isArchiveRubruSessionInfoSchedulerEnabled() {
        return archiveRubruSessionInfoSchedulerEnabled;
    }

    public int getArchiveRubruSessionInfoAfterMonths() {
        return archiveRubruSessionInfoAfterMonths;
    }

    public boolean isEndSessionCloseTimerEnabled() {
        return endSessionCloseTimerEnabled;
    }

    public boolean isEndSessionSendSignalTimerEnabled() {
        return endSessionSendSignalTimerEnabled;
    }

    public String getTokenSessionDelimiter() {
        return tokenSessionDelimiter;
    }

    public int getCaptchaMinFontSize() {
        return captchaMinFontSize;
    }

    public int getCaptchaMaxFontSize() {
        return captchaMaxFontSize;
    }

    public String getCaptchaBackgroundFirstColorRGB() {
        return captchaBackgroundFirstColorRGB;
    }

    public String getCaptchaBackgroundSecondColorRGB() {
        return captchaBackgroundSecondColorRGB;
    }

    public int getCaptchaImageWidth() {
        return captchaImageWidth;
    }

    public int getCaptchaImageHeight() {
        return captchaImageHeight;
    }

    public String getCaptchaTextColorRGB() {
        return captchaTextColorRGB;
    }

    public int getCaptchaMinAcceptedWordLength() {
        return captchaMinAcceptedWordLength;
    }

    public int getCaptchaMaxAcceptedWordLength() {
        return captchaMaxAcceptedWordLength;
    }

    public int getCaptchaRequiredFailedLoginValidMinutes() {
        return captchaRequiredFailedLoginValidMinutes;
    }

    public int getCaptchaRequiredFailedLoginCount() {
        return captchaRequiredFailedLoginCount;
    }

    public boolean isClassroomFileRemoverSchedulerEnabled() {
        return classroomFileRemoverSchedulerEnabled;
    }

    public int getClassroomFileRemoverSchedulerKeepMaxDayCount() {
        return classroomFileRemoverSchedulerKeepMaxDayCount;
    }

    public boolean isRemoveLogsSchedulerEnabled() {
        return removeLogsSchedulerEnabled;
    }

    public int getRemoveLogsSchedulerKeepMaxDayCount() {
        return removeLogsSchedulerKeepMaxDayCount;
    }

    public boolean isRubruRemoveLogsSchedulerEnabled() {
        return rubruRemoveLogsSchedulerEnabled;
    }

    public int getRubruRemoveLogsSchedulerKeepMaxDayCount() {
        return rubruRemoveLogsSchedulerKeepMaxDayCount;
    }

    public String getPassargadIpgRSAKeyValueModulus() {
        return passargadIpgRSAKeyValueModulus;
    }

    public String getPassargadIpgRSAKeyValueExponent() {
        return passargadIpgRSAKeyValueExponent;
    }

    public String getPassargadIpgRSAKeyValueP() {
        return passargadIpgRSAKeyValueP;
    }

    public String getPassargadIpgRSAKeyValueQ() {
        return passargadIpgRSAKeyValueQ;
    }

    public String getPassargadIpgRSAKeyValueDP() {
        return passargadIpgRSAKeyValueDP;
    }

    public String getPassargadIpgRSAKeyValueDQ() {
        return passargadIpgRSAKeyValueDQ;
    }

    public String getPassargadIpgRSAKeyValueInverseQ() {
        return passargadIpgRSAKeyValueInverseQ;
    }

    public String getPassargadIpgRSAKeyValueD() {
        return passargadIpgRSAKeyValueD;
    }

    public String getPassargadIpgRootAddress() {
        return passargadIpgRootAddress;
    }

    public String getPassargadIpgGetTokenAddress() {
        return passargadIpgRootAddress + passargadIpgGetTokenAddress;
    }

    public String getPassargadIpgCheckTransactionResultAddress() {
        return passargadIpgRootAddress + passargadIpgCheckTransactionResultAddress;
    }

    public String getPassargadIpgVerifyPaymentAddress() {
        return passargadIpgRootAddress + passargadIpgVerifyPaymentAddress;
    }

    public String getPassargadIpgTerminalCode() {
        return passargadIpgTerminalCode;
    }

    public String getPassargadIpgMerchantCode() {
        return passargadIpgMerchantCode;
    }
}

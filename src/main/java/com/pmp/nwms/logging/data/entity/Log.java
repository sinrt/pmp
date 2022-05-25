package com.pmp.nwms.logging.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pmp.nwms.config.Constants;
import com.pmp.nwms.util.GsonUtil;
import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@QueryEntity
@Document(collection = "nwms-logs")
@CompoundIndexes({
    @CompoundIndex(name = "info_idx1", def = "{'callDateTime' : 1}"),
    @CompoundIndex(name = "info_idx2", def = "{'callDateTime' : 1, 'userName': 1}"),
    @CompoundIndex(name = "info_idx3", def = "{'callDateTime' : 1, 'userName': 1, 'ipAddress' : 1}"),
    @CompoundIndex(name = "info_idx4", def = "{'callDateTime' : 1, 'appWebUrl': 1}")
})
public class Log {
    @Id
    public String id;

    private String appWebUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    private Date callDateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    private Date endCallDateTime;
    private String userName;
    private String ipAddress;
    private Long duration;
    private Long responseSize;


    private String userAgent;
    private String browserName;
    private String browserType;
    private String browserVersion;
    private String browserMajorVersion;
    private String deviceType;
    private String platformName;
    private String platformVersion;


    private String logSource;
    private String logUri;

    private List<LogDetail> inputLogDetails;
    private Object response;
    private boolean success;
    private Integer hashCodeValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppWebUrl() {
        return appWebUrl;
    }

    public void setAppWebUrl(String appWebUrl) {
        this.appWebUrl = appWebUrl;
    }

    public Date getCallDateTime() {
        return callDateTime;
    }

    public void setCallDateTime(Date callDateTime) {
        this.callDateTime = callDateTime;
    }

    public Date getEndCallDateTime() {
        return endCallDateTime;
    }

    public void setEndCallDateTime(Date endCallDateTime) {
        this.endCallDateTime = endCallDateTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getResponseSize() {
        return responseSize;
    }

    public void setResponseSize(Long responseSize) {
        this.responseSize = responseSize;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public String getBrowserType() {
        return browserType;
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public String getBrowserMajorVersion() {
        return browserMajorVersion;
    }

    public void setBrowserMajorVersion(String browserMajorVersion) {
        this.browserMajorVersion = browserMajorVersion;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getPlatformVersion() {
        return platformVersion;
    }

    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }

    public String getLogSource() {
        return logSource;
    }

    public void setLogSource(String logSource) {
        this.logSource = logSource;
    }

    public String getLogUri() {
        return logUri;
    }

    public void setLogUri(String logUri) {
        this.logUri = logUri;
    }

    public List<LogDetail> getInputLogDetails() {
        return inputLogDetails;
    }

    public void setInputLogDetails(List<LogDetail> inputLogDetails) {
        this.inputLogDetails = inputLogDetails;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getHashCodeValue() {
        return hashCodeValue;
    }

    public void setHashCodeValue(Integer hashCodeValue) {
        this.hashCodeValue = hashCodeValue;
    }

    @Transient
    public Integer getHashCodeCalculate() {
        return hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Log log = (Log) o;

        if (success != log.success) return false;
        if (appWebUrl != null ? !appWebUrl.equals(log.appWebUrl) : log.appWebUrl != null) return false;
        if (callDateTime != null ? !callDateTime.equals(log.callDateTime) : log.callDateTime != null) return false;
        if (endCallDateTime != null ? !endCallDateTime.equals(log.endCallDateTime) : log.endCallDateTime != null)
            return false;
        if (userName != null ? !userName.equals(log.userName) : log.userName != null) return false;
        if (ipAddress != null ? !ipAddress.equals(log.ipAddress) : log.ipAddress != null) return false;
        if (duration != null ? !duration.equals(log.duration) : log.duration != null) return false;
        if (responseSize != null ? !responseSize.equals(log.responseSize) : log.responseSize != null) return false;
        if (userAgent != null ? !userAgent.equals(log.userAgent) : log.userAgent != null) return false;
        if (browserName != null ? !browserName.equals(log.browserName) : log.browserName != null) return false;
        if (browserType != null ? !browserType.equals(log.browserType) : log.browserType != null) return false;
        if (browserVersion != null ? !browserVersion.equals(log.browserVersion) : log.browserVersion != null)
            return false;
        if (browserMajorVersion != null ? !browserMajorVersion.equals(log.browserMajorVersion) : log.browserMajorVersion != null)
            return false;
        if (deviceType != null ? !deviceType.equals(log.deviceType) : log.deviceType != null) return false;
        if (platformName != null ? !platformName.equals(log.platformName) : log.platformName != null) return false;
        if (platformVersion != null ? !platformVersion.equals(log.platformVersion) : log.platformVersion != null)
            return false;
        if (logSource != null ? !logSource.equals(log.logSource) : log.logSource != null) return false;
        if (logUri != null ? !logUri.equals(log.logUri) : log.logUri != null) return false;
        if (inputLogDetails != null ? !inputLogDetails.equals(log.inputLogDetails) : log.inputLogDetails != null)
            return false;
        return response != null ? response.equals(log.response) : log.response == null;
    }

    @Override
    public int hashCode() {
        int result = appWebUrl != null ? appWebUrl.hashCode() : 0;
        result = 31 * result + (callDateTime != null ? callDateTime.hashCode() : 0);
        result = 31 * result + (endCallDateTime != null ? endCallDateTime.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (ipAddress != null ? ipAddress.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (responseSize != null ? responseSize.hashCode() : 0);
        result = 31 * result + (userAgent != null ? userAgent.hashCode() : 0);
        result = 31 * result + (browserName != null ? browserName.hashCode() : 0);
        result = 31 * result + (browserType != null ? browserType.hashCode() : 0);
        result = 31 * result + (browserVersion != null ? browserVersion.hashCode() : 0);
        result = 31 * result + (browserMajorVersion != null ? browserMajorVersion.hashCode() : 0);
        result = 31 * result + (deviceType != null ? deviceType.hashCode() : 0);
        result = 31 * result + (platformName != null ? platformName.hashCode() : 0);
        result = 31 * result + (platformVersion != null ? platformVersion.hashCode() : 0);
        result = 31 * result + (logSource != null ? logSource.hashCode() : 0);
        result = 31 * result + (logUri != null ? logUri.hashCode() : 0);
//        result = 31 * result + calculateInputLogDetailsHashCode();
//        result = 31 * result + (response != null ? calculateResponseHashCode() : 0);
        result = 31 * result + (success ? 1 : 0);
        return result;
    }

    private int calculateResponseHashCode() {
        if(response != null){
            Throwable throwable = new Throwable();
            System.out.println("\n\n\n");
            int counter = 0;
            for (StackTraceElement stackTraceElement : throwable.getStackTrace()) {
                if(stackTraceElement.getClassName().startsWith("com.pmp.nwms.")){
                    System.out.println(stackTraceElement.getClassName());
                    counter++;
                }
                if(counter == 5){
                    break;
                }
            }
            System.out.println("response name = " + response.getClass().getName());
            System.out.println("\n\n\n");
            String s = GsonUtil.toJson(response);
            System.out.println("\n\n\nresponse = " + s);
            return s.hashCode();
        }
        return 0;
    }

    private int calculateInputLogDetailsHashCode() {
        if (inputLogDetails != null) {
            int hashCode = 1;
            for (LogDetail e : inputLogDetails) {
                hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
            }
            return hashCode;
        }
        return 0;
    }
}

package com.pmp.nwms.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pmp.nwms.config.Constants;
import com.pmp.nwms.domain.enums.RecordingMode;
import com.pmp.nwms.domain.enums.RecordingOutputMode;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A DTO for the PurchaseStatus entity.
 */
public class PurchaseStatusDTO implements Serializable {

    private Long id;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    private Date purchaseDate;

    private String firstName;

    private String family;

    @NotNull
    private String serviceCode;

    @NotNull
    private String serviceTitle;

    @NotNull
    private Integer serivcePrice;

    @NotNull
    private String trackingCode;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    private Date purchaseDateTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    private Date planFinishDate;

    private Integer totalConcurUsers;

    private Boolean lastPlan;
    private Boolean moderatorAutoLogin;

    private Boolean teacherPanel;

    private Boolean fileTransfer;

    private String transactionReferenceID;

    private String invoiceNumber;

    private String paymentCode;

    private String specialLink;

    private String paymentTitle;

    private Integer paymentDiscount;

    private Integer paymentPrice;
    private Integer authorizedHoursInWeek;
    private Integer authorizedHoursInMonth;

    private Integer planPriceWithDiscount;

    private String discountCode;

    private String discountTitle;

    private Integer dicountPrice;

    private Integer priceAfterVIPDiscount;

    private Float valueAdded;

    private Integer finalPrice;

    private Long userId;

    private String userLogin;

    public Boolean getTeacherPanel() {
        return teacherPanel;
    }

    public void setTeacherPanel(Boolean teacherPanel) {
        this.teacherPanel = teacherPanel;
    }

    public Boolean getFileTransfer() {
        return fileTransfer;
    }

    public void setFileTransfer(Boolean fileTransfer) {
        this.fileTransfer = fileTransfer;
    }

    private Integer sessionCount;

    private Integer concurVideos;

    private Integer concurUsers;

    private Boolean publicBrandedSession;


    private Boolean privateBrandedSession;

    private String resolution;

    private String returnUrl;
    private String wsUrl;
    private String appUrl;

    private Integer frameRate;
    private Integer qualityHigh;
    private Integer qualityLow;

    private Integer qualityMedium;
    private Integer qualityVeryHigh;

    private Integer qualityVeryLow;


    private Boolean noTimeLimit;

    private Boolean optionalTitle;

    private Integer screenShareWidth;

    private Integer screenShareHeight;

    private Integer screenShareFrameRate;

    private Integer resolutionWidth;

    private Integer resolutionHeight;

    private RecordingMode recordingMode;

    private RecordingOutputMode recordingDefaultOutputMode;

    private Integer concurrentRecordingCount;

    public Integer getAuthorizedHoursInWeek() {
        return authorizedHoursInWeek;
    }

    public void setAuthorizedHoursInWeek(Integer authorizedHoursInWeek) {
        this.authorizedHoursInWeek = authorizedHoursInWeek;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public Integer getAuthorizedHoursInMonth() {
        return authorizedHoursInMonth;
    }

    public void setAuthorizedHoursInMonth(Integer authorizedHoursInMonth) {
        this.authorizedHoursInMonth = authorizedHoursInMonth;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceTitle() {
        return serviceTitle;
    }

    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

    public Integer getSerivcePrice() {
        return serivcePrice;
    }

    public Integer getTotalConcurUsers() {
        return totalConcurUsers;
    }

    public void setTotalConcurUsers(Integer totalConcurUsers) {
        this.totalConcurUsers = totalConcurUsers;
    }

    public String getWsUrl() {
        return wsUrl;
    }

    public void setWsUrl(String wsUrl) {
        this.wsUrl = wsUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Integer getQualityHigh() {
        return qualityHigh;
    }

    public String getSpecialLink() {
        return specialLink;
    }

    public void setSpecialLink(String specialLink) {
        this.specialLink = specialLink;
    }

    public void setQualityHigh(Integer qualityHigh) {
        this.qualityHigh = qualityHigh;
    }

    public Integer getQualityLow() {
        return qualityLow;
    }

    public void setQualityLow(Integer qualityLow) {
        this.qualityLow = qualityLow;
    }

    public Integer getQualityMedium() {
        return qualityMedium;
    }

    public void setQualityMedium(Integer qualityMedium) {
        this.qualityMedium = qualityMedium;
    }

    public Integer getQualityVeryHigh() {
        return qualityVeryHigh;
    }

    public Boolean getModeratorAutoLogin() {
        return moderatorAutoLogin;
    }

    public void setModeratorAutoLogin(Boolean moderatorAutoLogin) {
        this.moderatorAutoLogin = moderatorAutoLogin;
    }

    public void setQualityVeryHigh(Integer qualityVeryHigh) {
        this.qualityVeryHigh = qualityVeryHigh;
    }

    public Integer getQualityVeryLow() {
        return qualityVeryLow;
    }

    public void setQualityVeryLow(Integer qualityVeryLow) {
        this.qualityVeryLow = qualityVeryLow;
    }

    public void setSerivcePrice(Integer serivcePrice) {
        this.serivcePrice = serivcePrice;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public Date getPurchaseDateTime() {
        return purchaseDateTime;
    }

    public void setPurchaseDateTime(Date purchaseDateTime) {
        this.purchaseDateTime = purchaseDateTime;
    }

    public Date getPlanFinishDate() {
        return planFinishDate;
    }

    public void setPlanFinishDate(Date planFinishDate) {
        this.planFinishDate = planFinishDate;
    }

    public Boolean isLastPlan() {
        return lastPlan;
    }

    public void setLastPlan(Boolean lastPlan) {
        this.lastPlan = lastPlan;
    }

    public String getTransactionReferenceID() {
        return transactionReferenceID;
    }

    public void setTransactionReferenceID(String transactionReferenceID) {
        this.transactionReferenceID = transactionReferenceID;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getPaymentTitle() {
        return paymentTitle;
    }

    public void setPaymentTitle(String paymentTitle) {
        this.paymentTitle = paymentTitle;
    }

    public Integer getPaymentDiscount() {
        return paymentDiscount;
    }

    public void setPaymentDiscount(Integer paymentDiscount) {
        this.paymentDiscount = paymentDiscount;
    }

    public Integer getPaymentPrice() {
        return paymentPrice;
    }

    public void setPaymentPrice(Integer paymentPrice) {
        this.paymentPrice = paymentPrice;
    }

    public Integer getPlanPriceWithDiscount() {
        return planPriceWithDiscount;
    }

    public void setPlanPriceWithDiscount(Integer planPriceWithDiscount) {
        this.planPriceWithDiscount = planPriceWithDiscount;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getDiscountTitle() {
        return discountTitle;
    }

    public void setDiscountTitle(String discountTitle) {
        this.discountTitle = discountTitle;
    }

    public Integer getDicountPrice() {
        return dicountPrice;
    }

    public void setDicountPrice(Integer dicountPrice) {
        this.dicountPrice = dicountPrice;
    }

    public Integer getPriceAfterVIPDiscount() {
        return priceAfterVIPDiscount;
    }

    public void setPriceAfterVIPDiscount(Integer priceAfterVIPDiscount) {
        this.priceAfterVIPDiscount = priceAfterVIPDiscount;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public Float getValueAdded() {
        return valueAdded;
    }

    public void setValueAdded(Float valueAdded) {
        this.valueAdded = valueAdded;
    }

    public Integer getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Integer finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Integer getSessionCount() {
        return sessionCount;
    }

    public void setSessionCount(Integer sessionCount) {
        this.sessionCount = sessionCount;
    }

    public Integer getConcurVideos() {
        return concurVideos;
    }

    public void setConcurVideos(Integer concurVideos) {
        this.concurVideos = concurVideos;
    }

    public Integer getConcurUsers() {
        return concurUsers;
    }

    public void setConcurUsers(Integer concurUsers) {
        this.concurUsers = concurUsers;
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

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public Integer getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(Integer frameRate) {
        this.frameRate = frameRate;
    }

    public Boolean isNoTimeLimit() {
        return noTimeLimit;
    }

    public void setNoTimeLimit(Boolean noTimeLimit) {
        this.noTimeLimit = noTimeLimit;
    }

    public Boolean isOptionalTitle() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PurchaseStatusDTO purchaseStatusDTO = (PurchaseStatusDTO) o;
        if (purchaseStatusDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), purchaseStatusDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PurchaseStatusDTO{" +
            "id=" + id +
            ", purchaseDate=" + purchaseDate +
            ", firstName='" + firstName + '\'' +
            ", family='" + family + '\'' +
            ", serviceCode='" + serviceCode + '\'' +
            ", serviceTitle='" + serviceTitle + '\'' +
            ", serivcePrice=" + serivcePrice +
            ", trackingCode='" + trackingCode + '\'' +
            ", purchaseDateTime=" + purchaseDateTime +
            ", planFinishDate=" + planFinishDate +
            ", totalConcurUsers=" + totalConcurUsers +
            ", lastPlan=" + lastPlan +
            ", moderatorAutoLogin=" + moderatorAutoLogin +
            ", teacherPanel=" + teacherPanel +
            ", fileTransfer=" + fileTransfer +
            ", transactionReferenceID='" + transactionReferenceID + '\'' +
            ", invoiceNumber='" + invoiceNumber + '\'' +
            ", paymentCode='" + paymentCode + '\'' +
            ", specialLink='" + specialLink + '\'' +
            ", paymentTitle='" + paymentTitle + '\'' +
            ", paymentDiscount=" + paymentDiscount +
            ", paymentPrice=" + paymentPrice +
            ", authorizedHoursInWeek=" + authorizedHoursInWeek +
            ", authorizedHoursInMonth=" + authorizedHoursInMonth +
            ", planPriceWithDiscount=" + planPriceWithDiscount +
            ", discountCode='" + discountCode + '\'' +
            ", discountTitle='" + discountTitle + '\'' +
            ", dicountPrice=" + dicountPrice +
            ", priceAfterVIPDiscount=" + priceAfterVIPDiscount +
            ", valueAdded=" + valueAdded +
            ", finalPrice=" + finalPrice +
            ", userId=" + userId +
            ", userLogin='" + userLogin + '\'' +
            ", sessionCount=" + sessionCount +
            ", concurVideos=" + concurVideos +
            ", concurUsers=" + concurUsers +
            ", publicBrandedSession=" + publicBrandedSession +
            ", privateBrandedSession=" + privateBrandedSession +
            ", resolution='" + resolution + '\'' +
            ", returnUrl='" + returnUrl + '\'' +
            ", wsUrl='" + wsUrl + '\'' +
            ", appUrl='" + appUrl + '\'' +
            ", frameRate=" + frameRate +
            ", qualityHigh=" + qualityHigh +
            ", qualityLow=" + qualityLow +
            ", qualityMedium=" + qualityMedium +
            ", qualityVeryHigh=" + qualityVeryHigh +
            ", qualityVeryLow=" + qualityVeryLow +
            ", noTimeLimit=" + noTimeLimit +
            ", optionalTitle=" + optionalTitle +
            ", screenShareWidth=" + screenShareWidth +
            ", screenShareHeight=" + screenShareHeight +
            ", screenShareFrameRate=" + screenShareFrameRate +
            ", resolutionWidth=" + resolutionWidth +
            ", resolutionHeight=" + resolutionHeight +
            ", recordingMode=" + recordingMode +
            ", recordingDefaultOutputMode=" + recordingDefaultOutputMode +
            ", concurrentRecordingCount=" + concurrentRecordingCount +
            '}';
    }

    public RecordingMode getRecordingMode() {
        return recordingMode;
    }

    public void setRecordingMode(RecordingMode recordingMode) {
        this.recordingMode = recordingMode;
    }

    public RecordingOutputMode getRecordingDefaultOutputMode() {
        return recordingDefaultOutputMode;
    }

    public void setRecordingDefaultOutputMode(RecordingOutputMode recordingDefaultOutputMode) {
        this.recordingDefaultOutputMode = recordingDefaultOutputMode;
    }

    public Integer getConcurrentRecordingCount() {
        return concurrentRecordingCount;
    }

    public void setConcurrentRecordingCount(Integer concurrentRecordingCount) {
        this.concurrentRecordingCount = concurrentRecordingCount;
    }

}

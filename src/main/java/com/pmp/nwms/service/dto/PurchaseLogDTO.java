package com.pmp.nwms.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pmp.nwms.config.Constants;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A DTO for the PurchaseLog entity.
 */
public class PurchaseLogDTO implements Serializable {

    private Long id;

    private Long userID;

    private String userName;

    private String userFirstName;

    private String userLastName;

    private String planCode;

    private String planTitle;

    private String paymentCode;

    private String paymentTitle;

    private Integer totalConcurUsers;

    private Integer paymentDiscount;

    private Integer paymentPrice;

    private String discountCode;

    private String discountTitle;

    private Integer dicountPrice;

    private Float valueAdded;

    private Integer finalPrice;

    private String bankTitle;

    private String merchantCode;

    private String terminalCode;

    private String transactionReferenceID;

    private String invoiceNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    private Date purchaseStartTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    private Date purchaseFinishTime;

    private Boolean gettingToken;

    private Boolean redirectToPort;

    private Boolean cancelPurchase;

    private Boolean verify;

    private Boolean teacherPanel;

    private Boolean finishProcess;

    private Integer unitPrice;

    private Integer sessionCount;

    private Integer planDaysCount;

    private Integer concurVideos;

    private Integer concurUsers;

    private Boolean publicBrandedSession;

    private Boolean privateBrandedSession;

    private String resolution;

    @Min(value = 0)
    private Integer frameRate;

    private Boolean noTimeLimit;

    private Boolean optionalTitle;

    private Integer screenShareWidth;

    private Integer screenShareHeight;

    private Integer screenShareFrameRate;

    private Integer resolutionWidth;

    private Integer resolutionHeight;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public Boolean getTeacherPanel() {
        return teacherPanel;
    }

    public void setTeacherPanel(Boolean teacherPanel) {
        this.teacherPanel = teacherPanel;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getPlanTitle() {
        return planTitle;
    }

    public void setPlanTitle(String planTitle) {
        this.planTitle = planTitle;
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

    public String getBankTitle() {
        return bankTitle;
    }

    public void setBankTitle(String bankTitle) {
        this.bankTitle = bankTitle;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getTerminalCode() {
        return terminalCode;
    }

    public void setTerminalCode(String terminalCode) {
        this.terminalCode = terminalCode;
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

    public Date getPurchaseStartTime() {
        return purchaseStartTime;
    }

    public void setPurchaseStartTime(Date purchaseStartTime) {
        this.purchaseStartTime = purchaseStartTime;
    }

    public Date getPurchaseFinishTime() {
        return purchaseFinishTime;
    }

    public void setPurchaseFinishTime(Date purchaseFinishTime) {
        this.purchaseFinishTime = purchaseFinishTime;
    }

    public Boolean isGettingToken() {
        return gettingToken;
    }

    public void setGettingToken(Boolean gettingToken) {
        this.gettingToken = gettingToken;
    }

    public Boolean isRedirectToPort() {
        return redirectToPort;
    }

    public void setRedirectToPort(Boolean redirectToPort) {
        this.redirectToPort = redirectToPort;
    }

    public Boolean isCancelPurchase() {
        return cancelPurchase;
    }

    public void setCancelPurchase(Boolean cancelPurchase) {
        this.cancelPurchase = cancelPurchase;
    }

    public Boolean isVerify() {
        return verify;
    }

    public void setVerify(Boolean verify) {
        this.verify = verify;
    }

    public Boolean isFinishProcess() {
        return finishProcess;
    }

    public void setFinishProcess(Boolean finishProcess) {
        this.finishProcess = finishProcess;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getSessionCount() {
        return sessionCount;
    }

    public void setSessionCount(Integer sessionCount) {
        this.sessionCount = sessionCount;
    }

    public Integer getPlanDaysCount() {
        return planDaysCount;
    }

    public void setPlanDaysCount(Integer planDaysCount) {
        this.planDaysCount = planDaysCount;
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

    public Integer getTotalConcurUsers() {
        return totalConcurUsers;
    }

    public void setTotalConcurUsers(Integer totalConcurUsers) {
        this.totalConcurUsers = totalConcurUsers;
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

        PurchaseLogDTO purchaseLogDTO = (PurchaseLogDTO) o;
        if (purchaseLogDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), purchaseLogDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PurchaseLogDTO{" +
            "id=" + getId() +
            ", userID=" + getUserID() +
            ", userName='" + getUserName() + "'" +
            ", userFirstName='" + getUserFirstName() + "'" +
            ", userLastName='" + getUserLastName() + "'" +
            ", planCode='" + getPlanCode() + "'" +
            ", planTitle='" + getPlanTitle() + "'" +
            ", paymentCode='" + getPaymentCode() + "'" +
            ", paymentTitle='" + getPaymentTitle() + "'" +
            ", paymentDiscount=" + getPaymentDiscount() +
            ", paymentPrice=" + getPaymentPrice() +
            ", discountCode='" + getDiscountCode() + "'" +
            ", discountTitle='" + getDiscountTitle() + "'" +
            ", dicountPrice=" + getDicountPrice() +
            ", valueAdded=" + getValueAdded() +
            ", finalPrice=" + getFinalPrice() +
            ", bankTitle='" + getBankTitle() + "'" +
            ", merchantCode='" + getMerchantCode() + "'" +
            ", terminalCode='" + getTerminalCode() + "'" +
            ", transactionReferenceID='" + getTransactionReferenceID() + "'" +
            ", invoiceNumber='" + getInvoiceNumber() + "'" +
            ", purchaseStartTime='" + getPurchaseStartTime() + "'" +
            ", purchaseFinishTime='" + getPurchaseFinishTime() + "'" +
            ", gettingToken='" + isGettingToken() + "'" +
            ", redirectToPort='" + isRedirectToPort() + "'" +
            ", cancelPurchase='" + isCancelPurchase() + "'" +
            ", verify='" + isVerify() + "'" +
            ", finishProcess='" + isFinishProcess() + "'" +
            ", unitPrice=" + getUnitPrice() +
            ", sessionCount=" + getSessionCount() +
            ", planDaysCount=" + getPlanDaysCount() +
            ", concurVideos=" + getConcurVideos() +
            ", concurUsers=" + getConcurUsers() +
            ", publicBrandedSession='" + isPublicBrandedSession() + "'" +
            ", privateBrandedSession='" + isPrivateBrandedSession() + "'" +
            ", resolution='" + getResolution() + "'" +
            ", frameRate=" + getFrameRate() +
            ", noTimeLimit='" + isNoTimeLimit() + "'" +
            ", optionalTitle='" + isOptionalTitle() + "'" +
            ", screenShareWidth=" + getScreenShareWidth() +
            ", screenShareHeight=" + getScreenShareHeight() +
            ", screenShareFrameRate=" + getScreenShareFrameRate() +
            ", resolutionWidth=" + getResolutionWidth() +
            ", resolutionHeight=" + getResolutionHeight() +
            "}";
    }
}

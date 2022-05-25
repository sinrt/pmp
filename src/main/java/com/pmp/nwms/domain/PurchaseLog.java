package com.pmp.nwms.domain;



import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A PurchaseLog.
 */
@Entity
@Table(name = "purchase_log")
public class PurchaseLog implements Serializable, BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userID;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_first_name")
    private String userFirstName;

    @Column(name = "user_last_name")
    private String userLastName;

    @Column(name = "plan_code")
    private String planCode;

    @Column(name = "plan_title")
    private String planTitle;

    @Column(name = "payment_code")
    private String paymentCode;

    @Column(name = "payment_title")
    private String paymentTitle;

    @Column(name = "payment_discount")
    private Integer paymentDiscount;

    @Column(name = "payment_price")
    private Integer paymentPrice;

    @Column(name = "discount_code")
    private String discountCode;

    @Column(name = "teacher_panel")
    private Boolean teacherPanel = false;

    @Column(name = "discount_title")
    private String discountTitle;

    @Column(name = "dicount_price")
    private Integer dicountPrice;

    @Column(name = "value_added")
    private Float valueAdded;

    @Column(name = "final_price")
    private Integer finalPrice;

    @Column(name = "bank_title")
    private String bankTitle;

    @Column(name = "merchant_code")
    private String merchantCode;

    @Column(name = "terminal_code")
    private String terminalCode;

    @Column(name = "transaction_reference_id")
    private String transactionReferenceID;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "purchase_start_time")
    private ZonedDateTime purchaseStartTime;

    @Column(name = "purchase_finish_time")
    private ZonedDateTime purchaseFinishTime;

    @Column(name = "getting_token")
    private Boolean gettingToken;

    @Column(name = "redirect_to_port")
    private Boolean redirectToPort;

    @Column(name = "cancel_purchase")
    private Boolean cancelPurchase;

    @Column(name = "verify")
    private Boolean verify;

    @Column(name = "finish_process")
    private Boolean finishProcess;

    @Column(name = "unit_price")
    private Integer unitPrice;

    @Column(name = "session_count")
    private Integer sessionCount;

    @Column(name = "plan_days_count")
    private Integer planDaysCount;

    @Column(name = "concur_videos")
    private Integer concurVideos;

    @Column(name = "concur_users")
    private Integer concurUsers;

    @Column(name = "public_branded_session")
    private Boolean publicBrandedSession;

    @Column(name = "private_branded_session")
    private Boolean privateBrandedSession;

    @Column(name = "resolution")
    private String resolution;

    @Min(value = 0)
    @Column(name = "frame_rate")
    private Integer frameRate;

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


    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID() {
        return userID;
    }

    public PurchaseLog userID(Long userID) {
        this.userID = userID;
        return this;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public PurchaseLog userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public PurchaseLog userFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
        return this;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public PurchaseLog userLastName(String userLastName) {
        this.userLastName = userLastName;
        return this;
    }

    public Integer getTotalConcurUsers() {
        return totalConcurUsers;
    }

    public void setTotalConcurUsers(Integer totalConcurUsers) {
        this.totalConcurUsers = totalConcurUsers;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getPlanCode() {
        return planCode;
    }

    public PurchaseLog planCode(String planCode) {
        this.planCode = planCode;
        return this;
    }

    public Boolean getTeacherPanel() {
        return teacherPanel;
    }

    public void setTeacherPanel(Boolean teacherPanel) {
        this.teacherPanel = teacherPanel;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getPlanTitle() {
        return planTitle;
    }

    public PurchaseLog planTitle(String planTitle) {
        this.planTitle = planTitle;
        return this;
    }

    public void setPlanTitle(String planTitle) {
        this.planTitle = planTitle;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public PurchaseLog paymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
        return this;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getPaymentTitle() {
        return paymentTitle;
    }

    public PurchaseLog paymentTitle(String paymentTitle) {
        this.paymentTitle = paymentTitle;
        return this;
    }

    public void setPaymentTitle(String paymentTitle) {
        this.paymentTitle = paymentTitle;
    }

    public Integer getPaymentDiscount() {
        return paymentDiscount;
    }

    public PurchaseLog paymentDiscount(Integer paymentDiscount) {
        this.paymentDiscount = paymentDiscount;
        return this;
    }

    public void setPaymentDiscount(Integer paymentDiscount) {
        this.paymentDiscount = paymentDiscount;
    }

    public Integer getPaymentPrice() {
        return paymentPrice;
    }

    public PurchaseLog paymentPrice(Integer paymentPrice) {
        this.paymentPrice = paymentPrice;
        return this;
    }

    public void setPaymentPrice(Integer paymentPrice) {
        this.paymentPrice = paymentPrice;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public PurchaseLog discountCode(String discountCode) {
        this.discountCode = discountCode;
        return this;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getDiscountTitle() {
        return discountTitle;
    }

    public PurchaseLog discountTitle(String discountTitle) {
        this.discountTitle = discountTitle;
        return this;
    }

    public void setDiscountTitle(String discountTitle) {
        this.discountTitle = discountTitle;
    }

    public Integer getDicountPrice() {
        return dicountPrice;
    }

    public PurchaseLog dicountPrice(Integer dicountPrice) {
        this.dicountPrice = dicountPrice;
        return this;
    }

    public void setDicountPrice(Integer dicountPrice) {
        this.dicountPrice = dicountPrice;
    }

    public Float getValueAdded() {
        return valueAdded;
    }

    public PurchaseLog valueAdded(Float valueAdded) {
        this.valueAdded = valueAdded;
        return this;
    }

    public void setValueAdded(Float valueAdded) {
        this.valueAdded = valueAdded;
    }

    public Integer getFinalPrice() {
        return finalPrice;
    }

    public PurchaseLog finalPrice(Integer finalPrice) {
        this.finalPrice = finalPrice;
        return this;
    }

    public void setFinalPrice(Integer finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getBankTitle() {
        return bankTitle;
    }

    public PurchaseLog bankTitle(String bankTitle) {
        this.bankTitle = bankTitle;
        return this;
    }

    public void setBankTitle(String bankTitle) {
        this.bankTitle = bankTitle;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public PurchaseLog merchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
        return this;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getTerminalCode() {
        return terminalCode;
    }

    public PurchaseLog terminalCode(String terminalCode) {
        this.terminalCode = terminalCode;
        return this;
    }

    public void setTerminalCode(String terminalCode) {
        this.terminalCode = terminalCode;
    }

    public String getTransactionReferenceID() {
        return transactionReferenceID;
    }

    public PurchaseLog transactionReferenceID(String transactionReferenceID) {
        this.transactionReferenceID = transactionReferenceID;
        return this;
    }

    public void setTransactionReferenceID(String transactionReferenceID) {
        this.transactionReferenceID = transactionReferenceID;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public PurchaseLog invoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        return this;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public ZonedDateTime getPurchaseStartTime() {
        return purchaseStartTime;
    }

    public PurchaseLog purchaseStartTime(ZonedDateTime purchaseStartTime) {
        this.purchaseStartTime = purchaseStartTime;
        return this;
    }

    public void setPurchaseStartTime(ZonedDateTime purchaseStartTime) {
        this.purchaseStartTime = purchaseStartTime;
    }

    public ZonedDateTime getPurchaseFinishTime() {
        return purchaseFinishTime;
    }

    public PurchaseLog purchaseFinishTime(ZonedDateTime purchaseFinishTime) {
        this.purchaseFinishTime = purchaseFinishTime;
        return this;
    }

    public void setPurchaseFinishTime(ZonedDateTime purchaseFinishTime) {
        this.purchaseFinishTime = purchaseFinishTime;
    }

    public Boolean isGettingToken() {
        return gettingToken;
    }

    public PurchaseLog gettingToken(Boolean gettingToken) {
        this.gettingToken = gettingToken;
        return this;
    }

    public void setGettingToken(Boolean gettingToken) {
        this.gettingToken = gettingToken;
    }

    public Boolean isRedirectToPort() {
        return redirectToPort;
    }

    public PurchaseLog redirectToPort(Boolean redirectToPort) {
        this.redirectToPort = redirectToPort;
        return this;
    }

    public void setRedirectToPort(Boolean redirectToPort) {
        this.redirectToPort = redirectToPort;
    }

    public Boolean isCancelPurchase() {
        return cancelPurchase;
    }

    public PurchaseLog cancelPurchase(Boolean cancelPurchase) {
        this.cancelPurchase = cancelPurchase;
        return this;
    }

    public void setCancelPurchase(Boolean cancelPurchase) {
        this.cancelPurchase = cancelPurchase;
    }

    public Boolean isVerify() {
        return verify;
    }

    public PurchaseLog verify(Boolean verify) {
        this.verify = verify;
        return this;
    }

    public void setVerify(Boolean verify) {
        this.verify = verify;
    }

    public Boolean isFinishProcess() {
        return finishProcess;
    }

    public PurchaseLog finishProcess(Boolean finishProcess) {
        this.finishProcess = finishProcess;
        return this;
    }

    public void setFinishProcess(Boolean finishProcess) {
        this.finishProcess = finishProcess;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public PurchaseLog unitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getSessionCount() {
        return sessionCount;
    }

    public PurchaseLog sessionCount(Integer sessionCount) {
        this.sessionCount = sessionCount;
        return this;
    }

    public void setSessionCount(Integer sessionCount) {
        this.sessionCount = sessionCount;
    }

    public Integer getPlanDaysCount() {
        return planDaysCount;
    }

    public PurchaseLog planDaysCount(Integer planDaysCount) {
        this.planDaysCount = planDaysCount;
        return this;
    }

    public void setPlanDaysCount(Integer planDaysCount) {
        this.planDaysCount = planDaysCount;
    }

    public Integer getConcurVideos() {
        return concurVideos;
    }

    public PurchaseLog concurVideos(Integer concurVideos) {
        this.concurVideos = concurVideos;
        return this;
    }

    public void setConcurVideos(Integer concurVideos) {
        this.concurVideos = concurVideos;
    }

    public Integer getConcurUsers() {
        return concurUsers;
    }

    public PurchaseLog concurUsers(Integer concurUsers) {
        this.concurUsers = concurUsers;
        return this;
    }

    public void setConcurUsers(Integer concurUsers) {
        this.concurUsers = concurUsers;
    }

    public Boolean isPublicBrandedSession() {
        return publicBrandedSession;
    }

    public PurchaseLog publicBrandedSession(Boolean publicBrandedSession) {
        this.publicBrandedSession = publicBrandedSession;
        return this;
    }

    public void setPublicBrandedSession(Boolean publicBrandedSession) {
        this.publicBrandedSession = publicBrandedSession;
    }

    public Boolean isPrivateBrandedSession() {
        return privateBrandedSession;
    }

    public PurchaseLog privateBrandedSession(Boolean privateBrandedSession) {
        this.privateBrandedSession = privateBrandedSession;
        return this;
    }

    public void setPrivateBrandedSession(Boolean privateBrandedSession) {
        this.privateBrandedSession = privateBrandedSession;
    }

    public String getResolution() {
        return resolution;
    }

    public PurchaseLog resolution(String resolution) {
        this.resolution = resolution;
        return this;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public Integer getFrameRate() {
        return frameRate;
    }

    public PurchaseLog frameRate(Integer frameRate) {
        this.frameRate = frameRate;
        return this;
    }

    public void setFrameRate(Integer frameRate) {
        this.frameRate = frameRate;
    }

    public Boolean isNoTimeLimit() {
        return noTimeLimit;
    }

    public PurchaseLog noTimeLimit(Boolean noTimeLimit) {
        this.noTimeLimit = noTimeLimit;
        return this;
    }

    public void setNoTimeLimit(Boolean noTimeLimit) {
        this.noTimeLimit = noTimeLimit;
    }

    public Boolean isOptionalTitle() {
        return optionalTitle;
    }

    public PurchaseLog optionalTitle(Boolean optionalTitle) {
        this.optionalTitle = optionalTitle;
        return this;
    }

    public void setOptionalTitle(Boolean optionalTitle) {
        this.optionalTitle = optionalTitle;
    }

    public Integer getScreenShareWidth() {
        return screenShareWidth;
    }

    public PurchaseLog screenShareWidth(Integer screenShareWidth) {
        this.screenShareWidth = screenShareWidth;
        return this;
    }

    public void setScreenShareWidth(Integer screenShareWidth) {
        this.screenShareWidth = screenShareWidth;
    }

    public Integer getScreenShareHeight() {
        return screenShareHeight;
    }

    public PurchaseLog screenShareHeight(Integer screenShareHeight) {
        this.screenShareHeight = screenShareHeight;
        return this;
    }

    public void setScreenShareHeight(Integer screenShareHeight) {
        this.screenShareHeight = screenShareHeight;
    }

    public Integer getScreenShareFrameRate() {
        return screenShareFrameRate;
    }

    public PurchaseLog screenShareFrameRate(Integer screenShareFrameRate) {
        this.screenShareFrameRate = screenShareFrameRate;
        return this;
    }

    public void setScreenShareFrameRate(Integer screenShareFrameRate) {
        this.screenShareFrameRate = screenShareFrameRate;
    }

    public Integer getResolutionWidth() {
        return resolutionWidth;
    }

    public PurchaseLog resolutionWidth(Integer resolutionWidth) {
        this.resolutionWidth = resolutionWidth;
        return this;
    }

    public void setResolutionWidth(Integer resolutionWidth) {
        this.resolutionWidth = resolutionWidth;
    }

    public Integer getResolutionHeight() {
        return resolutionHeight;
    }

    public PurchaseLog resolutionHeight(Integer resolutionHeight) {
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
        PurchaseLog purchaseLog = (PurchaseLog) o;
        if (purchaseLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), purchaseLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PurchaseLog{" +
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

    @Override
    public Long fetchPrimaryKey() {
        return getId();
    }
}

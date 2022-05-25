package com.pmp.nwms.domain;


import com.pmp.nwms.domain.enums.RecordingMode;
import com.pmp.nwms.domain.enums.RecordingOutputMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A PurchaseStatus.
 */
@Entity
@Table(name = "purchase_status",
    indexes = {
    @Index(name = "idx_usr_last_plan", columnList = "user_id, last_plan")
    }
)
public class PurchaseStatus implements Serializable, BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "family")
    private String family;

    @NotNull
    @Column(name = "service_code", nullable = false)
    private String serviceCode;

    @NotNull
    @Column(name = "service_title", nullable = false)
    private String serviceTitle;

    @NotNull
    @Column(name = "serivce_price", nullable = false)
    private Integer serivcePrice;

    @NotNull
    @Column(name = "tracking_code", nullable = false, unique = true)
    private String trackingCode;

    @NotNull
    @Column(name = "purchase_date_time", nullable = false)
    private ZonedDateTime purchaseDateTime;

    @Column(name = "plan_finish_date")
    private ZonedDateTime planFinishDate;

    @Column(name = "last_plan")
    private Boolean lastPlan;

    @Column(name = "transaction_reference_id")
    private String transactionReferenceID;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "payment_code")
    private String paymentCode;

    @Column(name = "payment_title")
    private String paymentTitle;

    @Column(name = "payment_discount")
    private Integer paymentDiscount;

    @Column(name = "payment_price")
    private Integer paymentPrice;

    @Column(name = "plan_price_with_discount")
    private Integer planPriceWithDiscount;

    @Column(name = "discount_code")
    private String discountCode;

    @Column(name = "discount_title")
    private String discountTitle;

    @Column(name = "dicount_price")
    private Integer dicountPrice;

    @Column(name = "price_after_vip_discount")
    private Integer priceAfterVIPDiscount;

    @Column(name = "value_added")
    private Float valueAdded;

    @Column(name = "final_price")
    private Integer finalPrice;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "session_count")
    private Integer sessionCount;

    @Column(name = "concur_videos")
    private Integer concurVideos;

    @Column(name = "teacher_panel")
    private Boolean teacherPanel = false;

    @Column(name = "concur_users")
    private Integer concurUsers;

    @Column(name = "total_concur_users")
    private Integer totalConcurUsers;

    @Column(name = "public_branded_session")
    private Boolean publicBrandedSession;

    @Column(name = "private_branded_session")
    private Boolean privateBrandedSession;

    @Column(name = "resolution")
    private String resolution;

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

    @Column(name = "file_transfer")
    private Boolean fileTransfer;

    @Column(name = "return_url", length = 255, nullable = true)
    private String returnUrl;

    @Column(name = "ws_url", length = 255, nullable = true)
    private String wsUrl;

    @Column(name = "special_link", length = 255, nullable = true)
    private String specialLink;
    @Column(name = "app_url", length = 255, nullable = true)
    private String appUrl;

    @Column(name = "quality_very_low", nullable = true)
    private Integer qualityVeryLow;

    @Column(name = "quality_low", nullable = true)
    private Integer qualityLow;

    @Column(name = "quality_medium", nullable = true)
    private Integer qualityMedium;

    @Column(name = "quality_high", nullable = true)
    private Integer qualityHigh;

    @Column(name = "quality_very_high", nullable = true)
    private Integer qualityVeryHigh;

    @Enumerated(EnumType.STRING)
    @Column(name = "recording_mode", nullable = true, length = 6)
    private RecordingMode recordingMode;

    @Enumerated(EnumType.STRING)
    @Column(name = "recording_default_output_mode", nullable = true, length = 20)
    private RecordingOutputMode recordingDefaultOutputMode;

    @Column(name = "moderator_auto_login", nullable = true)
    private Boolean moderatorAutoLogin;

    @Column(name = "concurrent_recording_count", nullable = true)
    private Integer concurrentRecordingCount;

    @Column(name = "authorized_hours_in_month", nullable = true)
    private Integer authorizedHoursInMonth;

    @Column(name = "authorized_hours_in_week", nullable = true)
    private Integer authorizedHoursInWeek;

    @Column(name = "user_login", nullable = true, length = 50)
    private String userLogin;




    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public PurchaseStatus purchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public PurchaseStatus firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFamily() {
        return family;
    }

    public PurchaseStatus family(String family) {
        this.family = family;
        return this;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public PurchaseStatus serviceCode(String serviceCode) {
        this.serviceCode = serviceCode;
        return this;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceTitle() {
        return serviceTitle;
    }

    public PurchaseStatus serviceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
        return this;
    }

    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

    public Integer getSerivcePrice() {
        return serivcePrice;
    }

    public PurchaseStatus serivcePrice(Integer serivcePrice) {
        this.serivcePrice = serivcePrice;
        return this;
    }

    public void setSerivcePrice(Integer serivcePrice) {
        this.serivcePrice = serivcePrice;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public PurchaseStatus trackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
        return this;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public ZonedDateTime getPurchaseDateTime() {
        return purchaseDateTime;
    }

    public PurchaseStatus purchaseDateTime(ZonedDateTime purchaseDateTime) {
        this.purchaseDateTime = purchaseDateTime;
        return this;
    }

    public void setPurchaseDateTime(ZonedDateTime purchaseDateTime) {
        this.purchaseDateTime = purchaseDateTime;
    }

    public ZonedDateTime getPlanFinishDate() {
        return planFinishDate;
    }

    public PurchaseStatus planFinishDate(ZonedDateTime planFinishDate) {
        this.planFinishDate = planFinishDate;
        return this;
    }

    public void setPlanFinishDate(ZonedDateTime planFinishDate) {
        this.planFinishDate = planFinishDate;
    }

    public Boolean isLastPlan() {
        return lastPlan;
    }

    public PurchaseStatus lastPlan(Boolean lastPlan) {
        this.lastPlan = lastPlan;
        return this;
    }

    public Boolean getTeacherPanel() {
        return teacherPanel;
    }

    public void setTeacherPanel(Boolean teacherPanel) {
        this.teacherPanel = teacherPanel;
    }

    public void setLastPlan(Boolean lastPlan) {
        this.lastPlan = lastPlan;
    }

    public String getTransactionReferenceID() {
        return transactionReferenceID;
    }

    public PurchaseStatus transactionReferenceID(String transactionReferenceID) {
        this.transactionReferenceID = transactionReferenceID;
        return this;
    }

    public void setTransactionReferenceID(String transactionReferenceID) {
        this.transactionReferenceID = transactionReferenceID;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public PurchaseStatus invoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        return this;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public PurchaseStatus paymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
        return this;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getPaymentTitle() {
        return paymentTitle;
    }

    public PurchaseStatus paymentTitle(String paymentTitle) {
        this.paymentTitle = paymentTitle;
        return this;
    }

    public void setPaymentTitle(String paymentTitle) {
        this.paymentTitle = paymentTitle;
    }

    public Integer getPaymentDiscount() {
        return paymentDiscount;
    }

    public PurchaseStatus paymentDiscount(Integer paymentDiscount) {
        this.paymentDiscount = paymentDiscount;
        return this;
    }

    public void setPaymentDiscount(Integer paymentDiscount) {
        this.paymentDiscount = paymentDiscount;
    }

    public Integer getPaymentPrice() {
        return paymentPrice;
    }

    public PurchaseStatus paymentPrice(Integer paymentPrice) {
        this.paymentPrice = paymentPrice;
        return this;
    }

    public void setPaymentPrice(Integer paymentPrice) {
        this.paymentPrice = paymentPrice;
    }

    public Integer getPlanPriceWithDiscount() {
        return planPriceWithDiscount;
    }

    public PurchaseStatus planPriceWithDiscount(Integer planPriceWithDiscount) {
        this.planPriceWithDiscount = planPriceWithDiscount;
        return this;
    }

    public void setPlanPriceWithDiscount(Integer planPriceWithDiscount) {
        this.planPriceWithDiscount = planPriceWithDiscount;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public PurchaseStatus discountCode(String discountCode) {
        this.discountCode = discountCode;
        return this;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getDiscountTitle() {
        return discountTitle;
    }

    public PurchaseStatus discountTitle(String discountTitle) {
        this.discountTitle = discountTitle;
        return this;
    }

    public void setDiscountTitle(String discountTitle) {
        this.discountTitle = discountTitle;
    }

    public Integer getDicountPrice() {
        return dicountPrice;
    }

    public PurchaseStatus dicountPrice(Integer dicountPrice) {
        this.dicountPrice = dicountPrice;
        return this;
    }

    public void setDicountPrice(Integer dicountPrice) {
        this.dicountPrice = dicountPrice;
    }

    public Integer getPriceAfterVIPDiscount() {
        return priceAfterVIPDiscount;
    }

    public PurchaseStatus priceAfterVIPDiscount(Integer priceAfterVIPDiscount) {
        this.priceAfterVIPDiscount = priceAfterVIPDiscount;
        return this;
    }

    public void setPriceAfterVIPDiscount(Integer priceAfterVIPDiscount) {
        this.priceAfterVIPDiscount = priceAfterVIPDiscount;
    }

    public Float getValueAdded() {
        return valueAdded;
    }

    public PurchaseStatus valueAdded(Float valueAdded) {
        this.valueAdded = valueAdded;
        return this;
    }

    public void setValueAdded(Float valueAdded) {
        this.valueAdded = valueAdded;
    }

    public Integer getFinalPrice() {
        return finalPrice;
    }

    public PurchaseStatus finalPrice(Integer finalPrice) {
        this.finalPrice = finalPrice;
        return this;
    }

    public void setFinalPrice(Integer finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Long getUserId() {
        return userId;
    }

    public PurchaseStatus userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getSessionCount() {
        return sessionCount;
    }

    public PurchaseStatus sessionCount(Integer sessionCount) {
        this.sessionCount = sessionCount;
        return this;
    }

    public void setSessionCount(Integer sessionCount) {
        this.sessionCount = sessionCount;
    }

    public Integer getConcurVideos() {
        return concurVideos;
    }

    public PurchaseStatus concurVideos(Integer concurVideos) {
        this.concurVideos = concurVideos;
        return this;
    }

    public void setConcurVideos(Integer concurVideos) {
        this.concurVideos = concurVideos;
    }

    public Integer getConcurUsers() {
        return concurUsers;
    }

    public PurchaseStatus concurUsers(Integer concurUsers) {
        this.concurUsers = concurUsers;
        return this;
    }

    public void setConcurUsers(Integer concurUsers) {
        this.concurUsers = concurUsers;
    }

    public Integer getTotalConcurUsers() {
        return totalConcurUsers;
    }

    public PurchaseStatus totalConcurUsers(Integer totalConcurUsers) {
        this.totalConcurUsers = totalConcurUsers;
        return this;
    }


    public void setTotalConcurUsers(Integer totalConcurUsers) {
        this.totalConcurUsers = totalConcurUsers;
    }

    public Boolean isPublicBrandedSession() {
        return publicBrandedSession;
    }

    public PurchaseStatus publicBrandedSession(Boolean publicBrandedSession) {
        this.publicBrandedSession = publicBrandedSession;
        return this;
    }

    public void setPublicBrandedSession(Boolean publicBrandedSession) {
        this.publicBrandedSession = publicBrandedSession;
    }

    public Boolean isPrivateBrandedSession() {
        return privateBrandedSession;
    }

    public PurchaseStatus privateBrandedSession(Boolean privateBrandedSession) {
        this.privateBrandedSession = privateBrandedSession;
        return this;
    }

    public void setPrivateBrandedSession(Boolean privateBrandedSession) {
        this.privateBrandedSession = privateBrandedSession;
    }

    public String getResolution() {
        return resolution;
    }

    public PurchaseStatus resolution(String resolution) {
        this.resolution = resolution;
        return this;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public Integer getFrameRate() {
        return frameRate;
    }

    public PurchaseStatus frameRate(Integer frameRate) {
        this.frameRate = frameRate;
        return this;
    }

    public void setFrameRate(Integer frameRate) {
        this.frameRate = frameRate;
    }

    public Boolean isNoTimeLimit() {
        return noTimeLimit;
    }

    public PurchaseStatus noTimeLimit(Boolean noTimeLimit) {
        this.noTimeLimit = noTimeLimit;
        return this;
    }

    public void setNoTimeLimit(Boolean noTimeLimit) {
        this.noTimeLimit = noTimeLimit;
    }

    public Boolean isOptionalTitle() {
        return optionalTitle;
    }

    public PurchaseStatus optionalTitle(Boolean optionalTitle) {
        this.optionalTitle = optionalTitle;
        return this;
    }

    public void setOptionalTitle(Boolean optionalTitle) {
        this.optionalTitle = optionalTitle;
    }

    public Integer getScreenShareWidth() {
        return screenShareWidth;
    }

    public PurchaseStatus screenShareWidth(Integer screenShareWidth) {
        this.screenShareWidth = screenShareWidth;
        return this;
    }

    public void setScreenShareWidth(Integer screenShareWidth) {
        this.screenShareWidth = screenShareWidth;
    }

    public Integer getScreenShareHeight() {
        return screenShareHeight;
    }

    public PurchaseStatus screenShareHeight(Integer screenShareHeight) {
        this.screenShareHeight = screenShareHeight;
        return this;
    }

    public void setScreenShareHeight(Integer screenShareHeight) {
        this.screenShareHeight = screenShareHeight;
    }

    public Integer getScreenShareFrameRate() {
        return screenShareFrameRate;
    }

    public PurchaseStatus screenShareFrameRate(Integer screenShareFrameRate) {
        this.screenShareFrameRate = screenShareFrameRate;
        return this;
    }

    public void setScreenShareFrameRate(Integer screenShareFrameRate) {
        this.screenShareFrameRate = screenShareFrameRate;
    }

    public Integer getResolutionWidth() {
        return resolutionWidth;
    }

    public PurchaseStatus resolutionWidth(Integer resolutionWidth) {
        this.resolutionWidth = resolutionWidth;
        return this;
    }

    public void setResolutionWidth(Integer resolutionWidth) {
        this.resolutionWidth = resolutionWidth;
    }

    public Integer getResolutionHeight() {
        return resolutionHeight;
    }

    public PurchaseStatus resolutionHeight(Integer resolutionHeight) {
        this.resolutionHeight = resolutionHeight;
        return this;
    }

    public void setResolutionHeight(Integer resolutionHeight) {
        this.resolutionHeight = resolutionHeight;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    public Boolean getFileTransfer() {
        if (fileTransfer == null) {
            fileTransfer = false;
        }
        return fileTransfer;
    }

    public void setFileTransfer(Boolean fileTransfer) {
        this.fileTransfer = fileTransfer;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getWsUrl() {
        return wsUrl;
    }

    public void setWsUrl(String wsUrl) {
        this.wsUrl = wsUrl;
    }

    public String getSpecialLink() {
        return specialLink;
    }

    public void setSpecialLink(String specialLink) {
        this.specialLink = specialLink;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Integer getQualityVeryLow() {
        return qualityVeryLow;
    }

    public void setQualityVeryLow(Integer qualityVeryLow) {
        this.qualityVeryLow = qualityVeryLow;
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

    public Integer getQualityHigh() {
        return qualityHigh;
    }

    public void setQualityHigh(Integer qualityHigh) {
        this.qualityHigh = qualityHigh;
    }

    public Integer getQualityVeryHigh() {
        return qualityVeryHigh;
    }

    public void setQualityVeryHigh(Integer qualityVeryHigh) {
        this.qualityVeryHigh = qualityVeryHigh;
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

    public Boolean getModeratorAutoLogin() {
        return moderatorAutoLogin;
    }

    public void setModeratorAutoLogin(Boolean moderatorAutoLogin) {
        this.moderatorAutoLogin = moderatorAutoLogin;
    }

    public Integer getConcurrentRecordingCount() {
        return concurrentRecordingCount;
    }

    public void setConcurrentRecordingCount(Integer concurrentRecordingCount) {
        this.concurrentRecordingCount = concurrentRecordingCount;
    }

    public Integer getAuthorizedHoursInMonth() {
        return authorizedHoursInMonth;
    }

    public void setAuthorizedHoursInMonth(Integer authorizedHoursInMonth) {
        this.authorizedHoursInMonth = authorizedHoursInMonth;
    }

    public Integer getAuthorizedHoursInWeek() {
        return authorizedHoursInWeek;
    }

    public void setAuthorizedHoursInWeek(Integer authorizedHoursInWeek) {
        this.authorizedHoursInWeek = authorizedHoursInWeek;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PurchaseStatus purchaseStatus = (PurchaseStatus) o;
        if (purchaseStatus.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), purchaseStatus.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PurchaseStatus{" +
            "id=" + getId() +
            ", purchaseDate='" + getPurchaseDate() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", family='" + getFamily() + "'" +
            ", serviceCode='" + getServiceCode() + "'" +
            ", serviceTitle='" + getServiceTitle() + "'" +
            ", serivcePrice=" + getSerivcePrice() +
            ", trackingCode='" + getTrackingCode() + "'" +
            ", purchaseDateTime='" + getPurchaseDateTime() + "'" +
            ", planFinishDate='" + getPlanFinishDate() + "'" +
            ", lastPlan='" + isLastPlan() + "'" +
            ", transactionReferenceID='" + getTransactionReferenceID() + "'" +
            ", invoiceNumber='" + getInvoiceNumber() + "'" +
            ", paymentCode='" + getPaymentCode() + "'" +
            ", paymentTitle='" + getPaymentTitle() + "'" +
            ", paymentDiscount=" + getPaymentDiscount() +
            ", paymentPrice=" + getPaymentPrice() +
            ", planPriceWithDiscount=" + getPlanPriceWithDiscount() +
            ", discountCode='" + getDiscountCode() + "'" +
            ", discountTitle='" + getDiscountTitle() + "'" +
            ", dicountPrice=" + getDicountPrice() +
            ", priceAfterVIPDiscount=" + getPriceAfterVIPDiscount() +
            ", valueAdded=" + getValueAdded() +
            ", finalPrice=" + getFinalPrice() +
            ", userId=" + getUserId() +
            ", sessionCount=" + getSessionCount() +
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

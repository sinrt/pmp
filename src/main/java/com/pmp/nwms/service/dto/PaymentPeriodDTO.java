package com.pmp.nwms.service.dto;

import com.pmp.nwms.domain.PaymentPeriod;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PaymentPeriod entity.
 */
public class PaymentPeriodDTO implements Serializable {
    public PaymentPeriodDTO(PaymentPeriod paymentPeriod) {
        this.id = paymentPeriod.getId();
        this.code = paymentPeriod.getCode();
        this.title = paymentPeriod.getTitle();
        this.description = paymentPeriod.getDescription();
        this.panelDays = paymentPeriod.getPanelDays();
        this.discountPercent = paymentPeriod.getDiscountPercent();
        this.active = paymentPeriod.isActive();
    }

    public PaymentPeriodDTO() {
    }

    private Long id;

    @NotNull
    @Min(value = 0)
    private Integer code;

    @NotNull
    private String title;

    private String description;

    @NotNull
    private Integer panelDays;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer discountPercent;

    private Boolean active;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPanelDays() {
        return panelDays;
    }

    public void setPanelDays(Integer panelDays) {
        this.panelDays = panelDays;
    }

    public Integer getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PaymentPeriodDTO paymentPeriodDTO = (PaymentPeriodDTO) o;
        if (paymentPeriodDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paymentPeriodDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaymentPeriodDTO{" +
            "id=" + getId() +
            ", code=" + getCode() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", panelDays=" + getPanelDays() +
            ", discountPercent=" + getDiscountPercent() +
            ", active='" + isActive() + "'" +
            "}";
    }
}

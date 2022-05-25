package com.pmp.nwms.domain;



import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PaymentPeriod.
 */
@Entity
@Table(name = "payment_period")
public class PaymentPeriod implements Serializable, BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "code", nullable = false, unique = true)
    private Integer code;

    @NotNull
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "panel_days", nullable = false)
    private Integer panelDays;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "discount_percent", nullable = false)
    private Integer discountPercent;

    @Column(name = "active")
    private Boolean active;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public PaymentPeriod code(Integer code) {
        this.code = code;
        return this;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public PaymentPeriod title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public PaymentPeriod description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPanelDays() {
        return panelDays;
    }

    public PaymentPeriod panelDays(Integer panelDays) {
        this.panelDays = panelDays;
        return this;
    }

    public void setPanelDays(Integer panelDays) {
        this.panelDays = panelDays;
    }

    public Integer getDiscountPercent() {
        return discountPercent;
    }

    public PaymentPeriod discountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
        return this;
    }

    public void setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Boolean isActive() {
        return active;
    }

    public PaymentPeriod active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
        PaymentPeriod paymentPeriod = (PaymentPeriod) o;
        if (paymentPeriod.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paymentPeriod.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaymentPeriod{" +
            "id=" + getId() +
            ", code=" + getCode() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", panelDays=" + getPanelDays() +
            ", discountPercent=" + getDiscountPercent() +
            ", active='" + isActive() + "'" +
            "}";
    }

    @Override
    public Long fetchPrimaryKey() {
        return getId();
    }
}

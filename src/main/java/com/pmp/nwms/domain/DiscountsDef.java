package com.pmp.nwms.domain;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.pmp.nwms.config.Constants;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A DiscountsDef.
 */
@Entity
@Table(name = "discounts_def")
public class DiscountsDef implements Serializable, BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "discount_percent", nullable = false)
    private Integer discountPercent;

    @NotNull
    @Column(name = "start_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    private Date startDate;

    @NotNull
    @Column(name = "finsih_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    private Date finsihDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public DiscountsDef code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public DiscountsDef title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public DiscountsDef description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDiscountPercent() {
        return discountPercent;
    }

    public DiscountsDef discountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
        return this;
    }

    public void setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Date getStartDate() {
        return startDate;
    }

    public DiscountsDef startDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinsihDate() {
        return finsihDate;
    }

    public DiscountsDef finsihDate(Date finsihDate) {
        this.finsihDate = finsihDate;
        return this;
    }

    public void setFinsihDate(Date finsihDate) {
        this.finsihDate = finsihDate;
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
        DiscountsDef discountsDef = (DiscountsDef) o;
        if (discountsDef.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), discountsDef.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DiscountsDef{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", discountPercent=" + getDiscountPercent() +
            ", startDate='" + getStartDate() + "'" +
            ", finsihDate='" + getFinsihDate() + "'" +
            "}";
    }

    @Override
    public Long fetchPrimaryKey() {
        return getId();
    }
}

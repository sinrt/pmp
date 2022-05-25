package com.pmp.nwms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@MappedSuperclass
public abstract class AbstractDeletedEntity<PK> extends AbstractAuditingEntity<PK> {

    @Basic
    @Column(name = "org_created_by", nullable = false, length = 50, updatable = false)
    @JsonIgnore
    private String originalCreatedBy;

    @Basic
    @Column(name = "org_created_date", nullable = false, updatable = false)
    @JsonIgnore
    private Instant originalCreatedDate = Instant.now();

    @Basic
    @Column(name = "org_last_modified_by", length = 50)
    @JsonIgnore
    private String originalLastModifiedBy;

    @Basic
    @Column(name = "org_last_modified_date")
    @JsonIgnore
    private Instant originalLastModifiedDate = Instant.now();

    @Basic
    @Column(name = "org_version", nullable = false)
    private Long originalVersion = 0L;

    public String getOriginalCreatedBy() {
        return originalCreatedBy;
    }

    public void setOriginalCreatedBy(String originalCreatedBy) {
        this.originalCreatedBy = originalCreatedBy;
    }

    public Instant getOriginalCreatedDate() {
        return originalCreatedDate;
    }

    public void setOriginalCreatedDate(Instant originalCreatedDate) {
        this.originalCreatedDate = originalCreatedDate;
    }

    public String getOriginalLastModifiedBy() {
        return originalLastModifiedBy;
    }

    public void setOriginalLastModifiedBy(String originalLastModifiedBy) {
        this.originalLastModifiedBy = originalLastModifiedBy;
    }

    public Instant getOriginalLastModifiedDate() {
        return originalLastModifiedDate;
    }

    public void setOriginalLastModifiedDate(Instant originalLastModifiedDate) {
        this.originalLastModifiedDate = originalLastModifiedDate;
    }

    public Long getOriginalVersion() {
        return originalVersion;
    }

    public void setOriginalVersion(Long originalVersion) {
        this.originalVersion = originalVersion;
    }
}

package com.pmp.nwms.domain;



import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SignalServerLog.
 */
@Entity
@Table(name = "signal_server_log")
public class SignalServerLog implements Serializable, BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "action")
    private String action;

    @Column(name = "jhi_check")
    private Boolean check;

    @Size(max = 2500)
    @Column(name = "description", length = 2500)
    private String description;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public SignalServerLog action(String action) {
        this.action = action;
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Boolean isCheck() {
        return check;
    }

    public SignalServerLog check(Boolean check) {
        this.check = check;
        return this;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public String getDescription() {
        return description;
    }

    public SignalServerLog description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
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
        SignalServerLog signalServerLog = (SignalServerLog) o;
        if (signalServerLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), signalServerLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SignalServerLog{" +
            "id=" + getId() +
            ", action='" + getAction() + "'" +
            ", check='" + isCheck() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }

    @Override
    public Long fetchPrimaryKey() {
        return getId();
    }
}

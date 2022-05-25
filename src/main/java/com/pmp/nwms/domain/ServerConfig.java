package com.pmp.nwms.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ServerConfig.
 */
@Entity
@Table(name = "server_config")
public class ServerConfig implements Serializable, BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "param_name")
    private String paramName;

    @Column(name = "param_value")
    private String paramValue;

    @Column(name = "param_desc")
    private String paramDesc;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParamName() {
        return paramName;
    }

    public ServerConfig paramName(String paramName) {
        this.paramName = paramName;
        return this;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public ServerConfig paramValue(String paramValue) {
        this.paramValue = paramValue;
        return this;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getParamDesc() {
        return paramDesc;
    }

    public ServerConfig paramDesc(String paramDesc) {
        this.paramDesc = paramDesc;
        return this;
    }

    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
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
        ServerConfig serverConfig = (ServerConfig) o;
        if (serverConfig.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serverConfig.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
            "id=" + getId() +
            ", paramName='" + getParamName() + "'" +
            ", paramValue='" + getParamValue() + "'" +
            ", paramDesc='" + getParamDesc() + "'" +
            "}";
    }

    @Override
    public Long fetchPrimaryKey() {
        return getId();
    }
}

package com.pmp.nwms.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SignalServerLog entity.
 */
public class SignalServerLogDTO implements Serializable {

    private Long id;

    private String action;

    private Boolean check;

    @Size(max = 2500)
    private String description;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Boolean isCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SignalServerLogDTO signalServerLogDTO = (SignalServerLogDTO) o;
        if (signalServerLogDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), signalServerLogDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SignalServerLogDTO{" +
            "id=" + getId() +
            ", action='" + getAction() + "'" +
            ", check='" + isCheck() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}

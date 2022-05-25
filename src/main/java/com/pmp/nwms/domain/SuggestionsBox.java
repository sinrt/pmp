package com.pmp.nwms.domain;



import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SuggestionsBox.
 */
@Entity
@Table(name = "suggestions_box")
public class SuggestionsBox implements Serializable, BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "family")
    private String family;

    @Column(name = "phone_or_email")
    private String phoneOrEmail;

    @NotNull
    @Size(min = 5, max = 200)
    @Column(name = "title", length = 200, nullable = false)
    private String title;

    @NotNull
    @Size(min = 10, max = 300)
    @Column(name = "description", length = 300, nullable = false)
    private String description;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public SuggestionsBox name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public SuggestionsBox family(String family) {
        this.family = family;
        return this;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getPhoneOrEmail() {
        return phoneOrEmail;
    }

    public SuggestionsBox phoneOrEmail(String phoneOrEmail) {
        this.phoneOrEmail = phoneOrEmail;
        return this;
    }

    public void setPhoneOrEmail(String phoneOrEmail) {
        this.phoneOrEmail = phoneOrEmail;
    }

    public String getTitle() {
        return title;
    }

    public SuggestionsBox title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public SuggestionsBox description(String description) {
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
        SuggestionsBox suggestionsBox = (SuggestionsBox) o;
        if (suggestionsBox.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), suggestionsBox.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SuggestionsBox{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", family='" + getFamily() + "'" +
            ", phoneOrEmail='" + getPhoneOrEmail() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }

    @Override
    public Long fetchPrimaryKey() {
        return getId();
    }
}

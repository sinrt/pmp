package com.pmp.nwms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pmp.nwms.domain.enums.VisibilityStatus;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "classroom_file")
public class ClassroomFile extends AbstractAuditingEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "filename", nullable = false, length = 150)
    private String filename;
    @Column(name = "content_type", nullable = false, length = 150)
    private String contentType;
    @Column(name = "saved_id", nullable = false, length = 50)
    private String savedId;
    @Column(name = "sub_path", nullable = false, length = 50)
    private String subPath;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private VisibilityStatus status;
    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getSavedId() {
        return savedId;
    }

    public void setSavedId(String savedId) {
        this.savedId = savedId;
    }

    public String getSubPath() {
        return subPath;
    }

    public void setSubPath(String subPath) {
        this.subPath = subPath;
    }

    public VisibilityStatus getStatus() {
        return status;
    }

    public void setStatus(VisibilityStatus status) {
        this.status = status;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    @Override
    public Long fetchPrimaryKey() {
        return getId();
    }
}

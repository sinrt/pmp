package com.pmp.nwms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "classroom_file_slide")
public class ClassroomFileSlide extends AbstractAuditingEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "slide_number", nullable = false)
    private Integer slideNumber;

    @Column(name = "saved_id", nullable = false, length = 50)
    private String savedId;

    @Column(name = "sub_path", nullable = false, length = 255)
    private String subPath;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_file_id")
    private ClassroomFile classroomFile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSlideNumber() {
        return slideNumber;
    }

    public void setSlideNumber(Integer slideNumber) {
        this.slideNumber = slideNumber;
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

    public ClassroomFile getClassroomFile() {
        return classroomFile;
    }

    public void setClassroomFile(ClassroomFile classroomFile) {
        this.classroomFile = classroomFile;
    }

    @Override
    public Long fetchPrimaryKey() {
        return getId();
    }
}

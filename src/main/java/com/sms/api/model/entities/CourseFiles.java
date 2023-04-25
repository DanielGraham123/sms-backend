package com.sms.api.model.entities;

import com.sms.api.model.entities.base.BaseEntityWithId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course_files")
@EqualsAndHashCode(callSuper = true)
public class CourseFiles extends BaseEntityWithId {
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "path", nullable = false, length = 2048)
    private String path;
    @Column(name = "file_type")
    private String fileType;
    @Column(name = "size")
    private long size;
    @ManyToOne
    private Course course;
    @ManyToOne
    private UserEntity user;
}

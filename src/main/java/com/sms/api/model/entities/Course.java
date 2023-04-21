package com.sms.api.model.entities;

import com.sms.api.model.entities.base.BaseEntityWithId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courses")
@EqualsAndHashCode(callSuper = true)
public class Course extends BaseEntityWithId {
    @Column(name = "course_name", nullable = false)
    private String name;
    @Column
    private String description;
    @ManyToOne
    private Programme programme;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "grade_id")
    private Set<Grade> grades;
}

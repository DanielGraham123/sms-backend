package com.sms.api.model.entities;

import com.sms.api.model.entities.base.BasePersonEntity;
import com.sms.api.model.entities.enums.Level;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
@EqualsAndHashCode(callSuper = true)
public class Student extends BasePersonEntity {
    @ManyToOne
    private Parent parent;
    @Column(name = "enroll_date")
    private LocalDate enrollDate;
    @OneToOne
    private Programme programme;
    @Column(name = "date_of_birth")
    private String dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Level level;
    @OneToOne
    @Enumerated(EnumType.STRING)
    private Grade grade;
}

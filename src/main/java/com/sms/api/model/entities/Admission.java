package com.sms.api.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sms.api.model.entities.base.BaseEntityWithId;
import com.sms.api.model.entities.enums.Gender;
import com.sms.api.model.entities.enums.Level;
import com.sms.api.model.entities.enums.PersonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GeneratorType;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admissions")
@EqualsAndHashCode(callSuper = true)
public class Admission extends BaseEntityWithId {
    @Column(name = "adm_form_number", nullable = false, unique = true)
    @GeneratorType(type = AdmissionFormNumberGenerator.class, when = GenerationTime.INSERT)
    private String formNumber;
    @Column(name = "adm_year", nullable = false)
    private LocalDate admissionYear;
    @Column(name = "adm_status")
    private boolean admissionStatus = false;
    @Enumerated(EnumType.STRING)
    private Level level;
//    @OneToOne(fetch= FetchType.EAGER)
//    @JsonBackReference
//    @JoinColumn(name = "programme_id", referencedColumnName="id", nullable = false)
//    private Programme programme;
//    @OneToOne(fetch= FetchType.EAGER)
//    @JsonBackReference
//    @JoinColumn(name = "grade_id",referencedColumnName="id", nullable = false)
//    private Grade grade;
    @ManyToOne
    private Parent parent;
    @ManyToOne
    private Student student;
}


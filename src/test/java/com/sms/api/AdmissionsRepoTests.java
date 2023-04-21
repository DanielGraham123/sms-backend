package com.sms.api;

import com.sms.api.model.dtos.AdmissionDTO;
import com.sms.api.model.entities.Admission;
import com.sms.api.model.entities.Grade;
import com.sms.api.model.entities.Parent;
import com.sms.api.model.entities.Student;
import com.sms.api.model.entities.enums.*;
import com.sms.api.repositories.*;
import com.sms.api.service.AdmissionService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
@Rollback(false)
public class AdmissionsRepoTests {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private AdmissionsRepository admissionsRepo;
    @Autowired
    private ParentRepository parentRepo;
    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private GradeRepository gradeRepo;

    @Autowired
    private ProgrammeRepository programmeRepo;

    @Test
    public void testCreateAdmission() {
        Admission admission = new Admission();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Parent parent = new Parent();

        Set<Role> pRoles = new HashSet<>();
        pRoles.add(Role.PARENT);
        Set<Role> sRoles = new HashSet<>();
        sRoles.add(Role.STUDENT);

        parent.setEmail("janedoe@gmail.com");
        parent.setFirstName("Jane");
        parent.setLastName("Doe");
        parent.setParentType(ParentType.MOTHER);
        parent.setPhoneNumber("+1234579820");
        parent.setAddress("123 Main Street, New York, NY 10030");
        parent.setGender(Gender.FEMALE);
        parent.setUserName(parent.getFirstName() + "" + parent.getLastName());
        parent.setPassword(passwordEncoder.encode(parent.getFirstName() + "" + parent.getLastName()));
        parent.setRole(pRoles);

        var parentSaved = parentRepo.save(parent);

        Student student = new Student();

        student.setFirstName("Junior");
        student.setLastName("Doe");
        student.setEmail("xxxxxx@mail.com");
        student.setDateOfBirth("2000-01-01");
        student.setGender(Gender.MALE);
        student.setGrade(gradeRepo.findById(4L).get());
        student.setProgramme(programmeRepo.findById(1L).get());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        student.setEnrollDate(LocalDate.parse("2021-01-15", formatter));
        student.setParent(parentSaved);
        student.setUserName(student.getFirstName() + "" + student.getLastName());
        student.setPassword(passwordEncoder.encode(student.getFirstName() + "" + student.getLastName()));
        student.setRole(sRoles);

        var studentSaved = studentRepo.save(student);

        admission.setStudent(studentSaved);
        admission.setParent(parentSaved);
        admission.setLevel(studentSaved.getGrade().getLevel());
        admission.setAdmissionYear(student.getEnrollDate());

        Admission savedAdmission = admissionsRepo.save(admission);

        log.info("Admission: " + savedAdmission);

        Admission existingAdmission = entityManager.find(Admission.class, savedAdmission.getId());

        assertThat(admission.getFormNumber()).isEqualTo(existingAdmission.getFormNumber());

    }
}

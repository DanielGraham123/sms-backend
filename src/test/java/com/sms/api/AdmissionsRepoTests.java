package com.sms.api;

import com.sms.api.model.entities.Admission;
import com.sms.api.model.entities.Parent;
import com.sms.api.model.entities.Student;
import com.sms.api.model.entities.enums.Level;
import com.sms.api.model.entities.enums.ParentType;
import com.sms.api.model.entities.enums.PersonType;
import com.sms.api.repositories.AdmissionsRepository;
import com.sms.api.repositories.ParentRepository;
import com.sms.api.repositories.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

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

    @Test
    public void testCreateAdmission() {
//        Admission admission = new Admission();
//        admission.setAdmissionYear("2023");
//        admission.setPersonType(PersonType.PARENT);
//        admission.setLevel(Level.HIGH_SCHOOL);
//
//        Student student = new Student();
//        student.setFirstName("John");
//        student.setLastName("Doe");
//        student.setDateOfBirth("01/01/2013");
//        student.setPassword("password");
////        student.setUserName();
//
//        Parent parent = new Parent();
//        parent.setFirstName("Mary");
//        parent.setLastName("Doe");
//        parent.setPhoneNumber("1234567890");
//        parent.setEmail("marydoe@gmail.com");
//        parent.setAddress("123 Main Street");
//        parent.setParentType(ParentType.MOTHER);
//
//        student.setEmail(parent.getEmail());
//        student.setAddress(parent.getAddress());
//        student.setParent(parent);
//
//        admission.setStudent(student);
//
//        parentRepo.save(parent);
//        studentRepo.save(student);
//
//        log.info("Admission: " + admission);
//
//        Admission savedAdmission = admissionsRepo.save(admission);
//
//        Admission existingAdmission = entityManager.find(Admission.class, savedAdmission.getId());
//
//        assertThat(admission.getAdmissionNumber()).isEqualTo(existingAdmission.getAdmissionNumber());

    }
}

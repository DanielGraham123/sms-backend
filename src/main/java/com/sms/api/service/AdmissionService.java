package com.sms.api.service;

import com.sms.api.model.dtos.AdmissionDTO;
import com.sms.api.model.dtos.ParentDTO;
import com.sms.api.model.dtos.UserRegisterDTO;
import com.sms.api.model.entities.Admission;
import com.sms.api.model.entities.Parent;
import com.sms.api.model.entities.Student;
import com.sms.api.model.entities.UserEntity;
import com.sms.api.model.entities.enums.PersonType;
import com.sms.api.model.entities.enums.Role;
import com.sms.api.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdmissionService {
    private final ParentRepository parentRepo;
    private final StudentRepository studentRepo;
    private final CourseRepository courseRepo;
    private final GradeRepository gradeRepo;
    private final ProgrammeRepository programmeRepo;
    private final UserRepository userRepo;
    private final AdmissionsRepository admissionsRepo;
    private final PasswordEncoder passwordEncoder;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Object admitStudent(AdmissionDTO admissionDTO) throws Exception {
        Parent parent = new Parent();

        Set<Role> pRoles = new HashSet<>();
        pRoles.add(Role.PARENT);

        parent.setEmail(admissionDTO.getParent().getEmail());
        parent.setFirstName(admissionDTO.getParent().getFirstName());
        parent.setLastName(admissionDTO.getParent().getLastName());
        parent.setPhoneNumber(admissionDTO.getParent().getPhoneNumber());
        parent.setGender(admissionDTO.getParent().getGender());
        parent.setAddress(admissionDTO.getParent().getAddress());
        parent.setParentType(admissionDTO.getParent().getParentType());
        parent.setRole(pRoles);

        parent.setUserName(parent.getFirstName() + "" + parent.getLastName());
        parent.setPassword(passwordEncoder.encode(parent.getFirstName() + "" + parent.getLastName()));

        log.info("Parent: {}", parent);

        var savedStudent = createStudent(admissionDTO, parent);
        var savedAdmission = saveAdmission(admissionDTO, (Student) savedStudent);

        return Map.of("message", "Admission Saved!", "admission", savedAdmission);
    }

    private Object createStudent(AdmissionDTO admissionDTO, Parent parent) {
        try {
            System.out.println("AdmissionDTO: " + admissionDTO);
            Student student = new Student();
            Set<Role> sRoles = new HashSet<>();
            sRoles.add(Role.STUDENT);
            student.setFirstName(admissionDTO.getStudent().getFirstName());
            student.setLastName(admissionDTO.getStudent().getLastName());
            student.setRole(sRoles);
            student.setGender(admissionDTO.getStudent().getGender());
            student.setDateOfBirth(admissionDTO.getStudent().getDateOfBirth());

            System.out.println("Stopping here: "+student);

            student.setEnrollDate(LocalDate.parse(admissionDTO.getStudent().getEnrollDate(), formatter));
            System.out.println("date: "+ admissionDTO.getStudent().getEnrollDate());

            student.setGrade(gradeRepo.findById(admissionDTO.getStudent().getGrade_id()).orElse(null));
            student.setProgramme(programmeRepo.findById(admissionDTO.getStudent().getProgramme_id()).orElse(null));
            student.setLevel(admissionDTO.getStudent().getLevel());

            student.setParent(parent);

            student.setUserName(student.getFirstName() + "" + student.getLastName());
            student.setPassword(passwordEncoder.encode(student.getFirstName() + "" + student.getLastName()));
            student.setEmail(admissionDTO.getStudent().getEmail());

            log.info("Student: {}", student);

//        parent.setStudents(Set.of(student));

            System.out.println("students: "+parent.getStudents());

//            Set<Student> students = parent.getStudents();
//            students.add(student);
//            parent.setStudents(students);

            var parentSaved = parentRepo.save(parent);

            log.info("Parent Saved: {}", parentSaved);

            var studentSaved = studentRepo.save(student);

            log.info("Student Saved: {}", studentSaved);

            return studentSaved;
        } catch (Exception error) {
            System.err.println("Error: " + error.getMessage());
            return Map.of("message", "Error: " + error.getMessage());
        }
    }

    private Object saveAdmission(AdmissionDTO admissionDTO, Student student) {
        try {
            Admission admission = new Admission();

            admission.setAdmissionYear(LocalDate.parse(admissionDTO.getAdmissionYear(), formatter));
            admission.setStudent(student);
            admission.setParent(student.getParent());
            admission.setLevel(student.getLevel());

            log.info("Admission: {}", admission);

            return admissionsRepo.save(admission);
        } catch (Exception error) {
            System.err.println("Error: " + error.getMessage());
            return Map.of("message", "Error: " + error.getMessage());
        }
    }

    public Object getAdmissions() {
        return admissionsRepo.findAll();
    }

    public Object getAdmissionById(Long id) {
        return admissionsRepo.findById(id);
    }



}

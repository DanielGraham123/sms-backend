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
import java.util.HashSet;
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

    public Object admitStudent(AdmissionDTO admissionDTO) throws Exception {
        Admission admission = new Admission();

        Parent parent = new Parent();

        Set<Role> pRoles = new HashSet<>();
        pRoles.add(Role.PARENT);
        Set<Role> sRoles = new HashSet<>();
        sRoles.add(Role.STUDENT);

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

        var parentSaved = parentRepo.save(parent);

        Student student = new Student();

        student.setRole(sRoles);
        student.setFirstName(admissionDTO.getStudent().getFirstName());
        student.setLastName(admissionDTO.getStudent().getLastName());
        student.setGender(admissionDTO.getStudent().getGender());
        student.setDateOfBirth(admissionDTO.getStudent().getDateOfBirth());
        student.setEnrollDate(admissionDTO.getAdmissionYear());
        student.setGrade(gradeRepo.findById(admissionDTO.getStudent().getGrade_id()).orElse(null));
        student.setProgramme(programmeRepo.findById(admissionDTO.getStudent().getProgramme_id()).orElse(null));
        student.setLevel(admissionDTO.getLevel());
        student.setParent(parentSaved);

        student.setUserName(student.getFirstName() + "" + student.getLastName());
        student.setPassword(passwordEncoder.encode(student.getFirstName() + "" + student.getLastName()));

        var studentSaved = studentRepo.save(student);

        admission.setAdmissionYear(admissionDTO.getAdmissionYear());
        admission.setStudent(studentSaved);
        admission.setParent(parentSaved);

        return admissionsRepo.save(admission);
    }

    public Object getAdmissions() {
        return admissionsRepo.findAll();
    }

    public Object getAdmissionById(Long id) {
        return admissionsRepo.findById(id);
    }



}

package com.sms.api.service;

import com.sms.api.model.dtos.AdmissionDTO;
import com.sms.api.model.dtos.ParentDTO;
import com.sms.api.model.dtos.UserRegisterDTO;
import com.sms.api.model.entities.Admission;
import com.sms.api.model.entities.Parent;
import com.sms.api.model.entities.Student;
import com.sms.api.model.entities.UserEntity;
import com.sms.api.model.entities.enums.PersonType;
import com.sms.api.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.nio.file.attribute.UserPrincipalNotFoundException;

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

    public Object admitStudent(Long parentId, AdmissionDTO admissionDTO) throws Exception {
        var person = admissionDTO.getPersonType();

        if (person == PersonType.STUDENT) {
            return submitAsStudent(admissionDTO);
        } else if (person == PersonType.PARENT) {
            var parent = parentRepo.findById(parentId).orElseThrow(() -> new UserPrincipalNotFoundException("Parent not found!"));
            return submitAsParent(parent, admissionDTO);
        } else {
            return null;
        }
    }

    private Object submitAsStudent(AdmissionDTO admissionDTO) {
        Admission admission = new Admission();
        BeanUtils.copyProperties(admissionDTO, admission);
        System.out.println("Admission: " + admission);
        return admissionsRepo.save(admission);
    }

    private Object submitAsParent(Parent parent, AdmissionDTO admissionDTO) {
        Admission admission = new Admission();
        BeanUtils.copyProperties(admissionDTO, admission);
        admission.setParent(parent);
        System.out.println("Admission: " + admission);
        return admissionsRepo.save(admission);

    }


}

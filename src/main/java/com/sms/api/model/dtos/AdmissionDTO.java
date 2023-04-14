package com.sms.api.model.dtos;

import com.sms.api.model.entities.Grade;
import com.sms.api.model.entities.Programme;
import com.sms.api.model.entities.Student;
import com.sms.api.model.entities.enums.Gender;
import com.sms.api.model.entities.enums.Level;
import com.sms.api.model.entities.enums.PersonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionDTO {
    private PersonType personType;
    private String studentFName;
    private String studentLName;
    private String studentDOB;
    private Gender gender;
    private String admissionYear;
    private Programme programme;
    private Grade grade;
    private Level level;
}

package com.sms.api.model.dtos;

import com.sms.api.model.entities.Grade;
import com.sms.api.model.entities.Parent;
import com.sms.api.model.entities.Programme;
import com.sms.api.model.entities.Student;
import com.sms.api.model.entities.enums.Gender;
import com.sms.api.model.entities.enums.Level;
import com.sms.api.model.entities.enums.PersonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionDTO {
    private String admissionYear;
    private ParentDTO parent;
    private StudentDTO student;
    private boolean admissionStatus = false;
}

package com.sms.api.model.dtos;

import com.sms.api.model.dtos.base.PersonEntityDTO;
import com.sms.api.model.entities.Grade;
import com.sms.api.model.entities.Parent;
import com.sms.api.model.entities.Programme;
import com.sms.api.model.entities.enums.Gender;
import com.sms.api.model.entities.enums.Level;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private Gender gender;
    private String address;
    private String email;
    private Long programme_id;
    private Long grade_id;
    private Level level;
    private String enrollDate;
}

package com.sms.api.model.dtos.base;

import com.sms.api.model.entities.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonEntityDTO {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private Gender gender;
    private String address;
    private String email;
}

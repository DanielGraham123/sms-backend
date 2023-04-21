package com.sms.api.model.dtos;

import com.sms.api.model.dtos.base.PersonEntityDTO;
import com.sms.api.model.entities.Parent;
import com.sms.api.model.entities.enums.Gender;
import com.sms.api.model.entities.enums.ParentType;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParentDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phoneNumber;
    private Gender gender;
    private ParentType parentType;
}

package com.sms.api.model.dtos;

import com.sms.api.model.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffLoginDTO {
    private String usernameOrEmail;
    private String password;
    private Role role;
}

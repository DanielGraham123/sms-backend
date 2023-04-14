package com.sms.api.model.dtos;

import com.sms.api.model.entities.enums.Level;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradeDTO {
    private String name;
    private Level level;
}

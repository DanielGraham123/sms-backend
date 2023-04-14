package com.sms.api.model.dtos;

import com.sms.api.model.dtos.base.PersonEntityDTO;
import com.sms.api.model.entities.Parent;
import com.sms.api.model.entities.Programme;
import com.sms.api.model.entities.enums.Level;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StudentDTO extends PersonEntityDTO {
    private ParentDTO parentDTO;
    private ProgrammeDTO programmeDTO;
    private GradeDTO gradeDTO;
    private Level level;
    private LocalDate enrollDate;
}

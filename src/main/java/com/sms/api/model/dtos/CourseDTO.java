package com.sms.api.model.dtos;

import com.sms.api.model.entities.Grade;
import com.sms.api.model.entities.Programme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private String name;
    private String description;
    private Long programme_id;
    private List<Long> grades;
}

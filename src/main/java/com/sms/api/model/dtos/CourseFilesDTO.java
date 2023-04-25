package com.sms.api.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseFilesDTO {
    private String name;
    private String path;
    private String fileType;
    private int size;
    private int course_id;
    private int user_id;
}

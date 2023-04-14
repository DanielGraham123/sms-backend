package com.sms.api.model.entities;

import com.sms.api.model.entities.base.BaseEntityWithId;
import com.sms.api.model.entities.enums.Level;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "grades")
@EqualsAndHashCode(callSuper = true)
public class Grade extends BaseEntityWithId {
    @Column(name = "grade_name", unique = true, nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    private Level level;

}

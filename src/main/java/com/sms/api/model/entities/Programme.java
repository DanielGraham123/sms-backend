package com.sms.api.model.entities;

import com.sms.api.model.entities.base.BaseEntityWithId;
import com.sms.api.model.entities.enums.Level;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "programmes")
@EqualsAndHashCode(callSuper = true)
public class Programme extends BaseEntityWithId {
    @Column(name = "programme_name", nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    private Level level;
}

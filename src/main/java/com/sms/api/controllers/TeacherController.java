package com.sms.api.controllers;

import com.sms.api.model.dtos.TeacherDTO;
import com.sms.api.model.entities.Course;
import com.sms.api.model.entities.Teacher;
import com.sms.api.model.entities.enums.Role;
import com.sms.api.repositories.CourseRepository;
import com.sms.api.repositories.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
@Slf4j
public class TeacherController {
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;

    @GetMapping("")
    public ResponseEntity<?> getTeachers() {
        return ResponseEntity.ok(teacherRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTeacher(@PathVariable Long id) {
        Teacher teacher = teacherRepository.findById(id).orElse(null);

        if (teacher == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(teacher);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTeacher(@RequestBody TeacherDTO teacherDTO) {
        try {
            Teacher teacher = teacherRepository.findByUsernameOrEmail(teacherDTO.getFirstName() + "" + teacherDTO.getLastName(), teacherDTO.getEmail()).orElse(null);

            if (teacher != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Teacher already exists");
            }

            teacher = new Teacher();
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            teacher.setFirstName(teacherDTO.getFirstName());
            teacher.setLastName(teacherDTO.getLastName());
            teacher.setGender(teacherDTO.getGender());
            teacher.setEmail(teacherDTO.getEmail());
            teacher.setAddress(teacherDTO.getAddress());
            teacher.setPhoneNumber(teacherDTO.getPhoneNumber());

            teacher.setUserName(teacherDTO.getFirstName() + "" + teacherDTO.getLastName());
            teacher.setPassword(passwordEncoder.encode(teacher.getLastName()));

            Set<Role> roles = new HashSet<>();
            roles.add(Role.TEACHER);
            teacher.setRole(roles);

            Course course = courseRepository.findById((long) teacherDTO.getCourse_id()).orElse(null);

            if (course == null) {
                return ResponseEntity.badRequest().body("Course can't be null");
            }

            teacher.setCourse(course);

            return ResponseEntity.ok(teacherRepository.save(teacher));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

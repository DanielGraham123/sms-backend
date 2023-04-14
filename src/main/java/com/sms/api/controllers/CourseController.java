package com.sms.api.controllers;

import com.sms.api.exceptions.CourseNotFoundException;
import com.sms.api.exceptions.GradeNotFoundException;
import com.sms.api.exceptions.ProgrammeNotFoundException;
import com.sms.api.model.dtos.CourseDTO;
import com.sms.api.model.entities.Course;
import com.sms.api.model.entities.Grade;
import com.sms.api.repositories.CourseRepository;
import com.sms.api.repositories.GradeRepository;
import com.sms.api.repositories.ProgrammeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseRepository courseRepository;
    private final GradeRepository gradeRepository;
    private final ProgrammeRepository programmeRepository;

    @GetMapping("")
    public ResponseEntity<?> getCourses() {
        return ResponseEntity.ok(courseRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable(value = "id") String id) throws CourseNotFoundException {
        Course course = courseRepository.findById(Long.parseLong(id)).orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + id));

        return ResponseEntity.ok(course);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCourse(@RequestBody CourseDTO courseDTO) throws Exception {
        var courseExists = courseRepository.existsByName(courseDTO.getName());

        if (courseExists) {
            return ResponseEntity.badRequest().body("Course '" + courseDTO.getName() + "' already exists");
        }

        if (courseDTO.getGrades().isEmpty()) {
            return ResponseEntity.badRequest().body("Course must have at least one grade");
        }

        Course course = new Course();

        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());

        course.setProgramme(programmeRepository.findById(courseDTO.getProgramme_id()).orElseThrow(() -> new ProgrammeNotFoundException("Programme not found with id: " + courseDTO.getProgramme_id())));

        var grades = courseDTO.getGrades();
        Set<Grade> gradeSet = new HashSet<>();

        grades.forEach(grade -> {
            try {
                gradeSet.add(gradeRepository.findById(grade).orElseThrow(() -> new GradeNotFoundException("Grade not found with id: " + grade)));
            } catch (GradeNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        course.setGrades(gradeSet);

        return ResponseEntity.ok(courseRepository.save(course));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable(value = "id") String id, @RequestBody CourseDTO courseDTO) throws CourseNotFoundException {
        Course course = courseRepository.findById(Long.parseLong(id)).orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + id));

        BeanUtils.copyProperties(courseDTO, course);

        return ResponseEntity.ok(courseRepository.save(course));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable(value = "id") String id) throws CourseNotFoundException {
        Course course = courseRepository.findById(Long.parseLong(id)).orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + id));

        courseRepository.delete(course);

        return ResponseEntity.ok("Course deleted successfully");
    }
}

package com.sms.api.controllers;

import com.sms.api.exceptions.GradeNotFoundException;
import com.sms.api.model.dtos.GradeDTO;
import com.sms.api.model.entities.Grade;
import com.sms.api.repositories.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/grades")
public class GradesController {
    private final GradeRepository gradeRepository;

    @GetMapping("")
    public ResponseEntity<?> getGrades() {
        var grades = gradeRepository.findAll();

        return new ResponseEntity<>(grades, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGradeById(@PathVariable(value="id") String id) throws GradeNotFoundException {
        Grade grade = gradeRepository.findById(Long.parseLong(id)).orElseThrow(() -> new GradeNotFoundException("Grade with Id: " + id + " not found!"));

        return new ResponseEntity<>(grade, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addGrade(@RequestBody GradeDTO gradeDTO) {
        var gradeExists = gradeRepository.existsByName(gradeDTO.getName());

        if (gradeExists) {
            return ResponseEntity.badRequest().body("Grade with name: " + gradeDTO.getName() + " already exists!");
        }

        Grade grade = new Grade();

        BeanUtils.copyProperties(gradeDTO, grade);

        return new ResponseEntity<>(gradeRepository.save(grade), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateGrade(@PathVariable(value="id") String id, @RequestBody GradeDTO gradeDTO) throws GradeNotFoundException {
        Grade grade = gradeRepository.findById(Long.parseLong(id)).orElseThrow(() -> new GradeNotFoundException("Grade with Id: " + id + " not found!"));


        BeanUtils.copyProperties(gradeDTO, grade);

        return new ResponseEntity<>(gradeRepository.save(grade), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGrade(@PathVariable(value="id") String id) throws GradeNotFoundException {
        Grade grade = gradeRepository.findById(Long.parseLong(id)).orElseThrow(() -> new GradeNotFoundException("Grade with Id: " + id + " not found!"));

        gradeRepository.delete(grade);

        return new ResponseEntity<>("Grade with Id: " + id + " deleted!", HttpStatus.OK);
    }
}

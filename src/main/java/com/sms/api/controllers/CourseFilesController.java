package com.sms.api.controllers;

import com.sms.api.exceptions.CourseNotFoundException;
import com.sms.api.exceptions.UserNotFoundException;
import com.sms.api.model.dtos.CourseFilesDTO;
import com.sms.api.model.entities.CourseFiles;
import com.sms.api.repositories.CourseFilesRepository;
import com.sms.api.repositories.CourseRepository;
import com.sms.api.repositories.TeacherRepository;
import com.sms.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class CourseFilesController {

    private final CourseFilesRepository courseFilesRepo;
    private final TeacherRepository teacherRepo;
    private final UserRepository userRepo;
    private final CourseRepository courseRepo;

    @GetMapping("")
    public ResponseEntity<?> getAllFiles() {
        return ResponseEntity.ok(courseFilesRepo.findAll());
    }

    @GetMapping("/exists")
    public ResponseEntity<?> checkFileExists(@RequestParam String name) {
        var courseFiles_ = courseFilesRepo.findByName(name);

        if (courseFiles_.isPresent()) {
            return ResponseEntity.ok(true);
        }

        return ResponseEntity.ok(false);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCourseFile(@RequestBody CourseFilesDTO courseFilesDTO) throws Exception {
        var courseFiles_ = courseFilesRepo.findByName(courseFilesDTO.getName());

        if (courseFiles_.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("File '" + courseFilesDTO.getName() + "' already exists");
        }

        CourseFiles courseFiles = new CourseFiles();

        courseFiles.setName(courseFilesDTO.getName());
        courseFiles.setPath(courseFilesDTO.getPath());
        courseFiles.setFileType(courseFilesDTO.getFileType());
        courseFiles.setSize(courseFilesDTO.getSize());
        courseFiles.setCourse(courseRepo.findById((long) courseFilesDTO.getCourse_id()).orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseFilesDTO.getCourse_id())));


        courseFiles.setUser(teacherRepo.findById((long) courseFilesDTO.getUser_id()).orElseThrow(() -> new UserNotFoundException("User not found with id: " + courseFilesDTO.getUser_id())));

        return ResponseEntity.ok(courseFilesRepo.save(courseFiles));

    }
}

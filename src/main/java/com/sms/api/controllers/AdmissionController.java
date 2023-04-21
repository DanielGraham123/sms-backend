package com.sms.api.controllers;

import com.sms.api.model.dtos.AdmissionDTO;
import com.sms.api.model.entities.UserEntity;
import com.sms.api.model.entities.enums.Role;
import com.sms.api.repositories.UserRepository;
import com.sms.api.service.AdmissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admission")
@RequiredArgsConstructor
@Slf4j
public class AdmissionController {
    private final AdmissionService admissionService;
    private final UserRepository userRepo;

    @PostMapping("/admit")
    public ResponseEntity<?> admitStudent(@RequestBody AdmissionDTO admissionDTO) throws Exception {
        var user_ = userRepo.findByUsername(admissionDTO.getStudent().getFirstName() + "" + admissionDTO.getStudent().getLastName());
        UserEntity user = user_.orElse(null);

        if (user != null) {
            return new ResponseEntity<>("Student already exists", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(admissionService.admitStudent(admissionDTO), HttpStatus.OK);

    }

    @GetMapping("/all")
    public ResponseEntity<?> getAdmissions() {
        return new ResponseEntity<>(admissionService.getAdmissions(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAdmission(@PathVariable Long id) {
        return new ResponseEntity<>(admissionService.getAdmissionById(id), HttpStatus.OK);
    }
}

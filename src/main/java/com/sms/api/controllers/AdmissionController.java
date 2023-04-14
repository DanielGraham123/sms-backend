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
    public ResponseEntity<?> admitStudent(@RequestParam(value = "usernameOrEmail") String usernameOrEmail,  @RequestBody AdmissionDTO admissionDTO) throws Exception {
        UserEntity userEntity = userRepo.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).isPresent() ? userRepo.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).get() : null;

        if (userEntity != null) {
            log.info("Admission: " + admissionDTO);

            log.info("User: " + userEntity);
            if (userEntity.getRole().contains(Role.PARENT) || userEntity.getRole().contains(Role.STUDENT)) {
                return new ResponseEntity<>(admissionService.admitStudent(userEntity.getId(), admissionDTO), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("User Not found!", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Invalid User!", HttpStatus.BAD_REQUEST);
    }


}

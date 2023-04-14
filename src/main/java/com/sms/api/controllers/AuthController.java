package com.sms.api.controllers;

import com.sms.api.exceptions.UserNotFoundException;
import com.sms.api.model.dtos.StaffLoginDTO;
import com.sms.api.model.dtos.UserLoginDTO;
import com.sms.api.model.dtos.UserRegisterDTO;
import com.sms.api.model.entities.Parent;
import com.sms.api.model.entities.Student;
import com.sms.api.model.entities.UserEntity;
import com.sms.api.model.entities.enums.Role;
import com.sms.api.repositories.ParentRepository;
import com.sms.api.repositories.StudentRepository;
import com.sms.api.repositories.UserRepository;
import com.sms.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepo;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final ParentRepository parentRepo;
    private final StudentRepository studentRepo;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO loginDTO) throws UserNotFoundException {
        log.info("Login: " + loginDTO);
        UserEntity user = userRepo.findByUsernameOrEmail(loginDTO.getUsernameOrEmail(), loginDTO.getUsernameOrEmail()).orElseThrow(() -> new UserNotFoundException("User "  + loginDTO.getUsernameOrEmail()+ " not found!"));

        if (user.getRole().contains(Role.ADMIN) || user.getRole().contains(Role.TEACHER) || user.getRole().contains(Role.STAFF)) {
            return new ResponseEntity<>("Unauthorized User!", HttpStatus.UNAUTHORIZED);
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>(Map.of( "message", "User Logged in successfully!", "user", user), HttpStatus.OK);
    }

    @PostMapping("/staff-login")
    public ResponseEntity<?> staffLogin(@RequestBody StaffLoginDTO staffLoginDTO) throws UserNotFoundException {
        log.info("Staff Login: " + staffLoginDTO);

        UserEntity user = userRepo.findByUsernameOrEmail(staffLoginDTO.getUsernameOrEmail(), staffLoginDTO.getUsernameOrEmail())
                .orElseThrow(() -> new UserNotFoundException("User "  + staffLoginDTO.getUsernameOrEmail()+ " not found!"));

        System.out.println("trying to login " + user);

        if (user == null) {
            return new ResponseEntity<>("User Not found!", HttpStatus.NOT_FOUND);
        }

        if (user.getRole().contains(Role.STUDENT) || user.getRole().contains(Role.PARENT)) {
            return new ResponseEntity<>("Invalid Role!", HttpStatus.BAD_REQUEST);
        }

        if (!user.getRole().contains(staffLoginDTO.getRole())) {
            return new ResponseEntity<>("Invalid Role!", HttpStatus.BAD_REQUEST);
        }

        switch (staffLoginDTO.getRole()) {
            case ADMIN, TEACHER, STAFF -> {
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(staffLoginDTO.getUsernameOrEmail(), staffLoginDTO.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                return new ResponseEntity<>(staffLoginDTO, HttpStatus.OK);
            }
            default -> {
                return new ResponseEntity<>("Invalid Role!", HttpStatus.BAD_REQUEST);
            }
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO registerDTO) {
        log.info("ParentRegister: " + registerDTO);

//        check if username exists
        if (userRepo.existsByUsername(registerDTO.getUsername())) {
            return new ResponseEntity<>("Username already taken!", HttpStatus.BAD_REQUEST);
        }

//        check if email exists
        if (userRepo.existsByEmail(registerDTO.getEmail())) {
            return new ResponseEntity<>("Email already taken!", HttpStatus.BAD_REQUEST);
        }

//        create user object
//        UserEntity user = new UserEntity();

        if (registerDTO.getRole().contains(Role.PARENT)) {
            UserEntity parent = new Parent();

            parent.setUserName(registerDTO.getUsername());
            parent.setEmail(registerDTO.getEmail());
            parent.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            parent.setRole(registerDTO.getRole());

            userRepo.save(parent);

            return new ResponseEntity<>("Parent registered successfully!", HttpStatus.OK);
        } else if (registerDTO.getRole().contains(Role.STUDENT)) {
            UserEntity student = new Student();

            student.setUserName(registerDTO.getUsername());
            student.setEmail(registerDTO.getEmail());
            student.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            student.setRole(registerDTO.getRole());

            userRepo.save(student);

            return new ResponseEntity<>("Student registered successfully!", HttpStatus.OK);
        }

        return new ResponseEntity<>("Invalid User!", HttpStatus.BAD_REQUEST);
    }
}

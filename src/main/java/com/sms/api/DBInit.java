package com.sms.api;

import com.sms.api.model.entities.UserEntity;
import com.sms.api.model.entities.enums.Role;
import com.sms.api.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;


@Component
@Slf4j
public class DBInit {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void postConstruct() {
        log.info("DBInit: postConstruct");
        UserEntity userEntity;

        UserEntity adminUser = userRepository.findByUsernameOrEmail("admin", "admin@scolar.com").orElse(null);

        if (adminUser == null) {
            userEntity = UserEntity.builder()
                    .username("admin")
                    .email("admin@scolar.com")
                    .password(passwordEncoder.encode("admin"))
                    .role(Collections.singleton(Role.ADMIN))
                    .build();

            log.info("DBInit: userEntity: " + userEntity);
            userRepository.save(userEntity);
        } else {
            log.info("DBInit: Admin User Exists!" + adminUser.getEmail());
        }



    }
}

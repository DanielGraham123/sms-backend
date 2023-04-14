package com.sms.api.service;

import com.sms.api.model.dtos.UserRegisterDTO;
import com.sms.api.model.entities.UserEntity;
import com.sms.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService implements UserDetailsService {
    private final UserRepository userRepo;
    public UserEntity registerUser(UserRegisterDTO registerDTO) {
        UserEntity userEntity = registerDTO.toEntity();
        log.info("UserEntity: " + userEntity);
        return userRepo.save(userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        UserEntity userEntity = userRepo.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(() -> new UsernameNotFoundException("User not found with email or username: " + usernameOrEmail));

        Set<GrantedAuthority> authorities = userEntity.getRole().stream().map((role) -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toSet());

        return new User(userEntity.getEmail(), userEntity.getPassword(), authorities);
    }
}

package com.sms.api.repositories;

import com.sms.api.model.entities.Programme;
import com.sms.api.model.entities.Student;
import com.sms.api.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByParentId(Long parentId);
    Optional<Student> findByProgramme(Programme programme);

    Optional<Student> findByUsernameOrEmail(String username, String email);

    Optional<Student> findByUsername(String username);
}

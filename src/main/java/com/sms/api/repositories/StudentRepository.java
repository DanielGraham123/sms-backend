package com.sms.api.repositories;

import com.sms.api.model.entities.Programme;
import com.sms.api.model.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByParentId(Long parentId);
    Optional<Student> findByProgramme(Programme programme);
}

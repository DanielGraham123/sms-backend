package com.sms.api.repositories;

import com.sms.api.model.entities.CourseFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseFilesRepository extends JpaRepository<CourseFiles, Long> {
    Optional<CourseFiles> findByName(String name);
    List<CourseFiles> findAllByCourseId(Long id);
//
//    List<CourseFiles> findAllByUserId(Long id);
}

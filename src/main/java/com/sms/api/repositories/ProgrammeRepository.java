package com.sms.api.repositories;

import com.sms.api.model.entities.Programme;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgrammeRepository extends CrudRepository<Programme, Long> {
    Optional<Programme> findByName(String name);
    boolean existsByName(String name);
}

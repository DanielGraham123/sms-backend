package com.sms.api.repositories;

import com.sms.api.model.entities.Admission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdmissionsRepository extends JpaRepository<Admission, Long> {
    List<Admission> findAllByAdmissionStatus(boolean admissionStatus);
}

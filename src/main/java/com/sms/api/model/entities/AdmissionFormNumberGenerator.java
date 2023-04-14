package com.sms.api.model.entities;

import org.hibernate.Session;
import org.hibernate.tuple.ValueGenerator;

import java.util.UUID;

public class AdmissionFormNumberGenerator implements ValueGenerator<String> {
    public String generateValue(Session session, Object owner) {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }
}

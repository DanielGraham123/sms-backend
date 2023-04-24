package com.sms.api.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalDTO {
    private int admission_id;
    private boolean admissionStatus;

    public boolean getAdmissionStatus() {
        return admissionStatus;
    }
}

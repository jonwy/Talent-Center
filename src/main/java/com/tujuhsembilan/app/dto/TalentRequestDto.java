package com.tujuhsembilan.app.dto;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TalentRequestDto {
    
    private UUID talentRequestId;
    private TalentRequestStatusDto talentRequestStatus;
    private Date requestDate;
    private String talentName;
    private String requestRejectReason;
    private String agencyName;
}

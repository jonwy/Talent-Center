package com.tujuhsembilan.app.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TalentRequestStatusDto {
    
    private UUID talentRequestStatusId;
    private String talentRequestStatusName;
}

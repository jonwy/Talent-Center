package com.tujuhsembilan.app.dto;

import org.hibernate.validator.constraints.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TalentSkillsetDto {
    
    private UUID talentId;
    private UUID skillsetId;
}

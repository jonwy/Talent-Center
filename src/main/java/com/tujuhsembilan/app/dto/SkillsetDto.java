package com.tujuhsembilan.app.dto;

import java.util.UUID;

import com.tujuhsembilan.app.model.Skillset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SkillsetDto {
    
    private UUID skillsetId;
    private String skillsetName; 

    public static SkillsetDto map(Skillset skillset) {
        if (skillset == null) return null;
        return new SkillsetDto(skillset.getSkillsetId(), skillset.getSkillsetName());
    }
}

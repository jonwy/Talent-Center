package com.tujuhsembilan.app.dto;

import java.util.UUID;

import com.tujuhsembilan.app.model.TalentLevel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TalentLevelDto {
    private UUID talentLevelId;
    private String talentLevelName;

    public static TalentLevelDto map(TalentLevel talentLevel) {
        if (talentLevel == null ) return null;
        return new TalentLevelDto(talentLevel.getTalentLevelId(), talentLevel.getTalentLevelName());
    }
}

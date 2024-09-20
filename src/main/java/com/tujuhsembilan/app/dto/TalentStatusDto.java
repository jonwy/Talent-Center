package com.tujuhsembilan.app.dto;

import java.util.UUID;

import com.tujuhsembilan.app.model.TalentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TalentStatusDto {

    private UUID talentStatusId;
    private String talentStatusName;
    
    public static TalentStatusDto map(TalentStatus talentStatus) {
        if (talentStatus == null) {
            return null;
        }
        return new TalentStatusDto(talentStatus.getTalentStatusId(), talentStatus.getTalentStatusName());
    }
}

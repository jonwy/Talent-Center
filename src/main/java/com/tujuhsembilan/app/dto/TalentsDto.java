package com.tujuhsembilan.app.dto;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.tujuhsembilan.app.model.Talent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TalentsDto {
    private UUID talentId;
    private String talentName;
    private String talentPhotoFilename;
    private TalentStatusDto talentStatus;
    private Boolean talentAvailibility;
   
    private Integer experience;
    private EmployeeStatusDto employeeStatus;
    private TalentLevelDto talentLevel;
    private Set<SkillsetDto> skillsets;
    private Set<PositionDto> positions;
    private String cellphone;
    private String email;

    public static TalentsDto map(Talent talent) {
        if (talent == null) return null;
        return new TalentsDto(
            talent.getTalentId(), talent.getTalentName(), talent.getTalentPhotoFilename(),
            TalentStatusDto.map(talent.getTalentStatus()), talent.getTalentAvailibility(),
            talent.getExperience(), EmployeeStatusDto.map(talent.getEmployeeStatus()),
            TalentLevelDto.map(talent.getTalentLevel()), 
            talent.getSkillsets() == null ? null : talent.getSkillsets().stream().map(item -> SkillsetDto.map(item)).collect(Collectors.toSet()),
            talent.getPositions() == null ? null : talent.getPositions().stream().map(item -> PositionDto.map(item)).collect(Collectors.toSet()),
            talent.getCellphone(), talent.getEmail()
        );
    }
}

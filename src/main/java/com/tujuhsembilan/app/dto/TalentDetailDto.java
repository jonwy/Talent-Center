package com.tujuhsembilan.app.dto;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.tujuhsembilan.app.model.Talent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TalentDetailDto {
    
    private UUID talentId;
    private String talentName;
    private String talentPhotoFilename;
    private String talentPhotoUrl;
    private String employeeNumber;
    private Integer experience;
    private Character gender;
    private String cellphone;
    private Date birthDate;
    private String talentDescription;
    private String talentCvFilename;
    private String talentCvUrl;
    private Boolean talentAvailibility;
    private TalentLevelDto talentLevel;
    private Set<PositionDto> positions;
    private Set<SkillsetDto> skillsets; 
    private EmployeeStatusDto employeeStatus;
    private TalentStatusDto talentStatus;
    private String email;
    private String biographyVideoUrl;
    private Boolean isActive;
    private Integer totalProjectCompleted;

    // private String agencyName;
    // private String agencyAddress;

    public static TalentDetailDto mapToDto(Talent talent) {
        return TalentDetailDto.builder()
            .talentId(talent.getTalentId())
            .talentName(talent.getTalentName())
            .talentPhotoFilename(talent.getTalentPhotoFilename())
            .talentCvFilename(talent.getTalentCvFilename())
            .experience(talent.getExperience())
            .gender(talent.getGender())
            .cellphone(talent.getCellphone())
            .email(talent.getEmail())
            .birthDate(talent.getBirthDate())
            .employeeNumber(talent.getEmployeeNumber())
            .talentDescription(talent.getTalentDescription())
            .talentAvailibility(talent.getTalentAvailibility())
            .talentStatus(TalentStatusDto.map(talent.getTalentStatus()))
            .talentLevel(TalentLevelDto.map(talent.getTalentLevel()))
            .employeeStatus(EmployeeStatusDto.map(talent.getEmployeeStatus()))
            .skillsets(talent.getSkillsets() == null ? null : talent.getSkillsets().stream().map(item -> SkillsetDto.map(item)).collect(Collectors.toSet()))
            .positions(talent.getPositions() == null ? null : talent.getPositions().stream().map(item -> PositionDto.map(item)).collect(Collectors.toSet()))
            .totalProjectCompleted(talent.getTotalProjectCompleted())
            .isActive(talent.getIsActive())
            .build();
    }

}

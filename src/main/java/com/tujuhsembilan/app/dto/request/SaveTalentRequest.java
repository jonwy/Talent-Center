package com.tujuhsembilan.app.dto.request;

import java.util.Date;
import java.util.UUID;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tujuhsembilan.app.dto.PositionDto;
import com.tujuhsembilan.app.dto.SkillsetDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveTalentRequest {

    private UUID talentId;
    private UUID employeeStatusId;
    private UUID talentLevelId;
    private UUID talentStatusId;
    private String employeeNumber;
    @NotBlank
    private String talentName;
    
    private Character gender;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date birthdate;
    
    private String talentDescription;
    
    private Integer experience;
    
    private Integer totalProjectCompleted;
    
    private Set<PositionDto> positions;
    
    private Set<SkillsetDto> skillsets;
    
    private String email;
    
    private String cellphone;
    
    private Boolean talentAvailibility;
    
    private String videoUrl;
}

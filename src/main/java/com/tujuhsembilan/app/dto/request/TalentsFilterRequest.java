package com.tujuhsembilan.app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TalentsFilterRequest {
    private String query;
    private String talentLevel;
    private String talentStatus;
    private String employeeStatus;
    private Integer lowerExperience;
    private Integer upperExperience;
}

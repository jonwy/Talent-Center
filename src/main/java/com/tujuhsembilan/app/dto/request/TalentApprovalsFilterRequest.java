package com.tujuhsembilan.app.dto.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TalentApprovalsFilterRequest {
    private String query;
    private String searchFor;
    private String status;
    private Date requestDate;
}

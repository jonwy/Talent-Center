package com.tujuhsembilan.app.dto;

import java.util.UUID;

import com.tujuhsembilan.app.model.EmployeeStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeStatusDto {
    private UUID employeeStatusId;
    private String employeeStatusName;

    public static EmployeeStatusDto map(EmployeeStatus employeeStatus) {
        if (employeeStatus == null) return null;
        return new EmployeeStatusDto(employeeStatus.getEmployeeStatusId(), employeeStatus.getEmployeeStatusName());
    }
}

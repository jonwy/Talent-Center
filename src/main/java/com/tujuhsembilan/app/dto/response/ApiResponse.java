package com.tujuhsembilan.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {
    
    private String message;
    private String status;
    private Integer statusCode;
    private Integer total;
    private Long totalRows;
    private Object data;
}

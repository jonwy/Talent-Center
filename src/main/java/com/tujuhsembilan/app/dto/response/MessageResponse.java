package com.tujuhsembilan.app.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageResponse {
    private String message;
    private String status;
    private Integer statusCode;
    private Integer total;
    private Object data;

    private Meta meta;

    public MessageResponse() {}

    public MessageResponse(String message, String status, Integer statusCode) {
        this.message = message;
        this.status = status;
        this.statusCode = statusCode;
    }

    public MessageResponse(String message, String status, Integer statusCode, Object data) {
        this.message = message;
        this.status = status;
        this.statusCode = statusCode;
        this.data = data;
    }

    public MessageResponse(String message, String status, Integer statusCode, Object data, Integer total) {
        this.message = message;
        this.status = status;
        this.statusCode = statusCode;
        this.data = data;
        this.total = total;
    }

    public MessageResponse(String message, String status, Integer statusCode, Object data, Meta meta) {
        this.message = message;
        this.status = status;
        this.statusCode = statusCode;
        this.data = data;
        this.meta = meta;
    }

    public MessageResponse(String message, String status, Integer statusCode, Integer total, Object data, Meta meta) {
        this.message = message;
        this.status = status;
        this.statusCode = statusCode;
        this.data = data;
        this.meta = meta;
        this.total = total;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Meta {
        private Integer totalPage;
        private Integer pageSize;
        private Integer currentPage;
    }
}

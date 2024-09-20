package com.tujuhsembilan.app.dto;

import java.util.UUID;

import com.tujuhsembilan.app.model.Position;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionDto {
    
    private UUID positionId;
    private String positionName;

    public static PositionDto map(Position position) {
        if (position == null) return null;
        return new PositionDto(position.getPositionId(), position.getPositionName());
    }
}

package com.tujuhsembilan.app.model;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class TalentPositionId implements Serializable{
    
    @Column(name = "talent_id")
    private UUID talentId;

    @Column(name = "position_id")
    private UUID positionId;

    public TalentPositionId() {}

    public TalentPositionId(UUID talentId, UUID positionId) {
        this.talentId = talentId;
        this.positionId = positionId;
    }
}

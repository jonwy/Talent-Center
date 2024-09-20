package com.tujuhsembilan.app.model;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TalentSkillsetId implements Serializable{
    
    @Column(name = "talent_id")
    private UUID talentId;

    @Column(name = "skillset_id")
    private UUID skillsetId;
}

package com.tujuhsembilan.app.model;

import java.io.Serializable;
import java.util.UUID;

import com.google.common.base.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class TalentMetadataId implements Serializable{

    @Column(name = "talent_id")
    private UUID talentId;

    public TalentMetadataId() {}

    public TalentMetadataId(UUID talentId) {
        this.talentId = talentId;
    }

    public UUID getTalentId() {
        return talentId;
    }

    public void setTalentId(UUID talentId) {
        this.talentId = talentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TalentMetadataId that = (TalentMetadataId) o;
        return Objects.equal(talentId, that.talentId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.talentId);
    }
}
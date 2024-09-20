package com.tujuhsembilan.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tujuhsembilan.app.model.TalentMetadata;
import com.tujuhsembilan.app.model.TalentMetadataId;

public interface TalentMetadataRepository extends JpaRepository<TalentMetadata, TalentMetadataId> {
    
}

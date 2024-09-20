package com.tujuhsembilan.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tujuhsembilan.app.model.TalentPosition;
import com.tujuhsembilan.app.model.TalentPositionId;

public interface TalentPositionRepository extends JpaRepository<TalentPosition, TalentPositionId>{
    
}

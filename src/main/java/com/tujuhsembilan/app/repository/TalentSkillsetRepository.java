package com.tujuhsembilan.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tujuhsembilan.app.model.TalentSkillset;
import com.tujuhsembilan.app.model.TalentSkillsetId;

public interface TalentSkillsetRepository extends JpaRepository<TalentSkillset, TalentSkillsetId>{
    
}

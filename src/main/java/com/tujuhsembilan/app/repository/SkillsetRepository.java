package com.tujuhsembilan.app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tujuhsembilan.app.model.Skillset;

public interface SkillsetRepository extends JpaRepository<Skillset, UUID>{
    
}

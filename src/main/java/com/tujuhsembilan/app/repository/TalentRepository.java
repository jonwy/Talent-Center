package com.tujuhsembilan.app.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.tujuhsembilan.app.model.Talent;

public interface TalentRepository extends JpaRepository<Talent, UUID>, JpaSpecificationExecutor<Talent>{
    
    // @Query("""
    //         SELECT t FROM Talent t
    //         """)
    Page<Talent> findAll(Specification<Talent> specification, Pageable pageable);

    Page<Talent> findAll(Pageable pageable);
    // @Query("SELECT t FROM Talent t " +
    //     "JOIN t.talentLevel tl " +
    //     "JOIN t.employeeStatus es " +
    //     "JOIN t.talentStatus ts " +
    //     "LEFT JOIN t.talentSkillsets tls " +
    //     "LEFT JOIN tls.skillset s")
    // Page<Talent> findAllCustomQuery(Pageable pageable);

    // @Query("""
    //     SELECT t FROM Talent t
    // """)
    // Page<Talent> findAllCustomQuery(Pageable pageable);

    // @Query("SELECT DISTINCT t FROM Talent t " +
    //        "JOIN FETCH t.talentLevel tl " +
    //        "JOIN FETCH t.employeeStatus es " +
    //        "JOIN FETCH t.talentStatus ts " +
    //        "LEFT JOIN FETCH t.skillsets     " +
    //        "LEFT JOIN FETCH t.positions")
    // Page<Talent> findAllCustomQuery(Pageable pageable);

    @Query("SELECT t FROM Talent t " +
           "JOIN t.talentLevel tl " +
           "JOIN t.employeeStatus es " +
           "JOIN t.talentStatus ts " +
           "LEFT JOIN t.skillsets " +
           "LEFT JOIN t.positions")
    Page<Talent> findAllCustomQuery(Pageable pageable);
}

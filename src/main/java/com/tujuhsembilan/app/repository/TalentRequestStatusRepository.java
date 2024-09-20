package com.tujuhsembilan.app.repository;

import java.util.UUID;
import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tujuhsembilan.app.model.TalentRequestStatus;


public interface TalentRequestStatusRepository extends JpaRepository<TalentRequestStatus, UUID> {

    List<TalentRequestStatus> findAllByCreatedByIgnoreCase(String createdBy);
    Optional<TalentRequestStatus> findByTalentRequestStatusNameAndCreatedByAllIgnoreCase(String talentRequestStatusName, String createdBy);
}

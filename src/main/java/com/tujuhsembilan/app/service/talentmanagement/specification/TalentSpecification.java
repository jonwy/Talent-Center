package com.tujuhsembilan.app.service.talentmanagement.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.tujuhsembilan.app.dto.request.TalentsFilterRequest;
import com.tujuhsembilan.app.model.EmployeeStatus;
import com.tujuhsembilan.app.model.Talent;
import com.tujuhsembilan.app.model.TalentLevel;
import com.tujuhsembilan.app.model.TalentStatus;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TalentSpecification {

    /**
     * TODO untuk method ini:
     * jika data talent tidak bagus,
     * salah satu dari fk, talent_level_id, talent_level_id, employee_status_id null
     * maka ketika JOIN talent_level, etc, maka data tidak akan muncul karena....
     * @param filter
     * @return
     */
    public static Specification<Talent> filterTalents(TalentsFilterRequest filter) {
        return (root, query, criteriaBuilder) -> {
            
            // root.fetch("skillsets", JoinType.LEFT);
            // root.fetch("positions", JoinType.LEFT);

            Join<Talent, TalentLevel> talentLevel = root.join("talentLevel", JoinType.INNER);
            Join<Talent, TalentStatus> talentStatus = root.join("talentStatus", JoinType.INNER);
            Join<Talent, EmployeeStatus> employeeStatus = root.join("employeeStatus", JoinType.INNER);
            List<Predicate> predicates = new ArrayList<>();
            
            if(filter.getQuery() != null) {
                String queryPattern = "%" + filter.getQuery().toLowerCase() + "%";
                Predicate queryPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("talentName")), queryPattern);
                predicates.add(queryPredicate);
            }
            if (filter.getEmployeeStatus() != null) {
                log.info("employee status is not null: " + filter.getEmployeeStatus());
                String employeeStatusId = filter.getEmployeeStatus();
                Predicate employeeStatusPredicate = criteriaBuilder
                                        .equal(employeeStatus.get("employeeStatusId"), UUID.fromString(employeeStatusId));
                predicates.add(employeeStatusPredicate);
            }
            if (filter.getTalentStatus() != null) {
                log.info("talent status is not null: " + filter.getTalentStatus());
                String talentStatusId = filter.getTalentStatus();
                Predicate talentStatusPredicate = criteriaBuilder
                                        .equal(talentStatus.get("talentStatusId"), UUID.fromString(talentStatusId));
                predicates.add(talentStatusPredicate);
            }
            if (filter.getTalentLevel() != null) {
                log.info("talent level is not null: " + filter.getTalentLevel());
                // String talentLevelName = "%" + filter.getTalentLevel().toLowerCase() + "%";
                String talentLevelId = filter.getTalentLevel();
                Predicate talentLevelPredicate = criteriaBuilder
                                        .equal(talentLevel.get("talentLevelId"), UUID.fromString(talentLevelId));
                predicates.add(talentLevelPredicate);
            }
            if (filter.getLowerExperience() != null && filter.getUpperExperience() != null) {
                log.info("experience status is not null");
                Integer lower = filter.getLowerExperience();
                Integer upper = filter.getUpperExperience();
                Predicate experiencePredicate = null;
                if (lower != upper) {
                    experiencePredicate = criteriaBuilder.between(root.get("experience"), lower, upper);
                }
                else if (lower == upper) {
                    experiencePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("experience"), lower);
                }
                predicates.add(experiencePredicate);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
         };
    }
    
}

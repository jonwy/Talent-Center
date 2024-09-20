package com.tujuhsembilan.app.service.talentmanagement.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.tujuhsembilan.app.dto.request.TalentApprovalsFilterRequest;
import com.tujuhsembilan.app.model.TalentRequest;
import com.tujuhsembilan.app.model.TalentWishlist;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TalentRequestSpecification {
    
    public static Specification<TalentRequest> getTalentRequestFilter(TalentApprovalsFilterRequest filterRequest) {
        return (root, query, criteriaBuilder) -> {
            Join<TalentWishlist, TalentRequest> talentWishList = root.join("talentWishlist", JoinType.INNER);
            List<Predicate> predicates = new ArrayList<>();
            if (filterRequest.getQuery() != null) {
                String searchQuery ="%" + filterRequest.getQuery().toLowerCase() + "%";
                Predicate searchPredicate = null;
                if (filterRequest.getSearchFor().equalsIgnoreCase("talent")) {
                    searchPredicate = criteriaBuilder.like(
                                        criteriaBuilder.lower(
                                            talentWishList.get("talent").get("talentName")),
                                        searchQuery);
                }
                else {
                    log.info("query berdasarkan agency");
                    searchPredicate = criteriaBuilder.like(
                                        criteriaBuilder.lower(
                                            talentWishList.get("client").get("clientName")),
                                        searchQuery);
                }
                predicates.add(searchPredicate);
            }
            if (filterRequest.getStatus() != null) {
                String requestStatusId = filterRequest.getStatus();
                Predicate statusPredicate = criteriaBuilder.equal(root.get("talentRequestStatus").get("talentRequestStatusId"), UUID.fromString(requestStatusId));
                predicates.add(statusPredicate);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

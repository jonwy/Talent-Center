package com.tujuhsembilan.app.repository;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tujuhsembilan.app.model.TalentRequest;
import com.tujuhsembilan.app.repository.projection.TalentRequestProjection;

public interface TalentRequestRepository extends JpaRepository<TalentRequest, UUID>, JpaSpecificationExecutor<TalentRequest> {
    
    Page<TalentRequest> findAll(Specification<TalentRequest> specification, Pageable pageable);

    @Query("""
        SELECT 
            tr.talentRequestId AS talentRequestId, 
            tr.requestDate AS requestDate,
            tw.client.agencyName AS agencyName, 
            tw.talent.talentName AS talentName,
            tr.talentRequestStatus.talentRequestStatusName AS approvalStatus
        FROM 
            TalentRequest tr
        JOIN TalentWishlist tw ON tr.talentWishlist.talentWishlistId = tw.talentWishlistId
    """)
    Page<TalentRequestProjection> findAllProjected(Pageable pageable);

    // @Query(value = """
    //     SELECT
    //         tr.talent_request_id AS talentRequestId,
    //         tr.request_date AS requestDate,
    //         c.agency_name AS agencyName,
    //         t.talent_name AS talentName,
    //         trs.talent_request_status_name AS approvalStatus
    //     FROM
    //         public.talent_request tr
    //     JOIN
    //         public.talent_wishlist tw ON tr.talent_wishlist_id = tw.talent_wishlist_id
    //     JOIN
    //         public.client c ON tw.client_id = c.client_id
    //     JOIN
    //         public.talent t ON tw.talent_id = t.talent_id
    //     JOIN
    //         public.talent_request_status trs ON tr.talent_request_status_id = trs.talent_request_status_id
    //     WHERE
    //         (:requestDate IS NOT NULL AND tr.request_date = :requestDate)
    //         AND (:requestStatusId IS NOT NULL AND tr.talent_request_status_id = :requestStatusId)
    //         AND (:talentName IS NOT NULL AND t.talent_name = :talentName)
    //         AND (:agencyName IS NOT NULL AND c.agency_name = :agencyName)
    // """, nativeQuery = true)
    @Query(value = """
        SELECT
            tr.talent_request_id AS talentRequestId,
            tr.request_date AS requestDate,
            c.agency_name AS agencyName,
            t.talent_name AS talentName,
            trs.talent_request_status_name AS approvalStatus
        FROM
            public.talent_request tr
        JOIN
            (SELECT tw.talent_wishlist_id, tw.client_id, tw.talent_id
            FROM public.talent_wishlist tw
            JOIN public.client c ON tw.client_id = c.client_id
            WHERE (:agencyName IS NULL OR c.agency_name = :agencyName)) AS tw ON tr.talent_wishlist_id = tw.talent_wishlist_id
        JOIN
            public.client c ON tw.client_id = c.client_id
        JOIN
            public.talent t ON tw.talent_id = t.talent_id
        JOIN
            public.talent_request_status trs ON tr.talent_request_status_id = trs.talent_request_status_id
        WHERE
            (:requestDate IS NULL OR tr.request_date = :requestDate)
            AND (:requestStatusId IS NULL OR tr.talent_request_status_id = :requestStatusId)
            AND (:talentName IS NULL OR t.talent_name = :talentName)

    """, nativeQuery = true)
    Page<TalentRequestProjection> findAllProjectedWhere(
        Pageable pageable, 
        @Param(value = "requestDate") Date requestDate, 
        @Param("requestStatusId") UUID requestStatusId,
        @Param("talentName") String talentName,
        @Param("agencyName") String agencyName);

    @Query(value = """
        SELECT COUNT (*) FROM (
        tr.talent_request_id AS talentRequestId,
            tr.request_date AS requestDate,
            c.agency_name AS agencyName,
            t.talent_name AS talentName,
            trs.talent_request_status_name AS approvalStatus
        FROM
            public.talent_request tr
        JOIN
            public.talent_wishlist tw ON tr.talent_wishlist_id = tw.talent_wishlist_id
        JOIN
            public.client c ON tw.client_id = c.client_id
        JOIN
            public.talent t ON tw.talent_id = t.talent_id
        JOIN
            public.talent_request_status trs ON tr.talent_request_status_id = trs.talent_request_status_id
        WHERE
            (:requestDate IS NOT NULL AND tr.request_date = :requestDate)
            AND (:requestStatusId IS NOT NULL AND tr.talent_request_status_id = :requestStatusId)
            AND (:talentName IS NOT NULL AND t.talent_name = :talentName)
            AND (:agencyName IS NOT NULL AND c.agency_name = :agencyName)
        )
    """, nativeQuery = true)
    Long countAllProjectedWhere(
        @Param(value = "requestDate") Date requestDate, 
        @Param("requestStatusId") UUID requestStatusId,
        @Param("talentName") String talentName,
        @Param("agencyName") String agencyName);

    @Query(value = """
            SELECT COUNT (*) FROM (
                SELECT tr.talent_request_id, tw.client_id
                from public.talent_request tr
                JOIN public.talent_wishlist tw on tr.talent_wishlist_id = tw.talent_wishlist_id
            ) sb
            """, nativeQuery = true)
    Long countTotalApprovals();
}

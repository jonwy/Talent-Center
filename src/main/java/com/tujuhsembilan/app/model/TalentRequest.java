package com.tujuhsembilan.app.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "talent_request", schema = "public")
public class TalentRequest implements Serializable{
    
    @Id
    @Column(name = "talent_request_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID talentRequestId;
    
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    @JoinColumn(name = "talent_request_status_id")
    private TalentRequestStatus talentRequestStatus;
    
    @Fetch(FetchMode.JOIN)
    @ManyToOne
    @JoinColumn(name = "talent_wishlist_id")
    private TalentWishlist talentWishlist;

    @Column(name = "request_date")
    private Date requestDate;

    @Column(name = "request_reject_reason")
    private String requestRejectReason;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false)
    private Date createdTime;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;
}

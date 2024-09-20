package com.tujuhsembilan.app.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client", schema = "public")
public class Client implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "client_id", updatable = false, nullable = false)
    private UUID clientId;

    @ManyToOne
    @JoinColumn(name = "client_position_id")
    private ClientPosition clientPosition;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birth_date")
    private Date birthDate;
    
    @Column(name = "email")
    private String email;

    @Column(name = "agency_name")
    private String agencyName;

    @Column(name = "agency_address")
    private String agencyAddress;

    @Column(name = "is_active")
    private Boolean isActive;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time")
    private Date createTime;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedTime;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

}

package com.tujuhsembilan.app.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client_position", schema = "public")
public class ClientPosition implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "client_position_id", updatable = false, nullable = false)
    private UUID clientPositionId;

    @Column(name = "client_position_name")
    private String clientPositionName;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_time", updatable = false)
    private Date createdTime;

    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;
    

}

package com.tujuhsembilan.app.model;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "talent_metadata", schema = "public")
public class TalentMetadata implements Serializable{
    
    @EmbeddedId
    private TalentMetadataId id;
    
    @Fetch(FetchMode.JOIN)
    @OneToOne
    @JoinColumn(name = "talent_id")
    private Talent talent;

    @Column(name = "cv_counter")
    private Integer cvCounter;

    @Column(name = "profile_counter")
    private Integer profileCounter;

    @Column(name = "total_project_completed")
    private Integer totalProjectCompleted;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false)
    private Date createdTime;

    @Column(name = "last_modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date lastModifiedTime;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;
}

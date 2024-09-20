package com.tujuhsembilan.app.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
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
@EntityListeners(EntityListeners.class)
@SecondaryTable(name = "talent_metadata", pkJoinColumns = @PrimaryKeyJoinColumn(name = "talent_id"))
@Table(name = "talent", schema = "public")
public class Talent implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "talent_id", updatable = false, nullable = false)
    private UUID talentId;

    @Fetch(FetchMode.JOIN)
    @OneToMany
    @JoinTable(name = "talent_skillset", joinColumns = @JoinColumn(name = "talent_id"), inverseJoinColumns = @JoinColumn(name = "skillset_id"))
    private Set<Skillset> skillsets;

    @Fetch(FetchMode.JOIN)
    @OneToMany
    @JoinTable(name = "talent_position", joinColumns = @JoinColumn(name = "talent_id"), inverseJoinColumns = @JoinColumn(name = "position_id"))
    private Set<Position> positions;

    @Column(table = "talent_metadata", name = "total_project_completed")
    private Integer totalProjectCompleted;

    @Fetch(FetchMode.JOIN)
    @ManyToOne
    @JoinColumn(name = "talent_level_id")
    private TalentLevel talentLevel;

    @Fetch(FetchMode.JOIN)
    @ManyToOne
    @JoinColumn(name = "talent_status_id")
    private TalentStatus talentStatus;

    @Fetch(FetchMode.JOIN)
    @ManyToOne
    @JoinColumn(name = "employee_status_id")
    private EmployeeStatus employeeStatus;

    @Column(name = "talent_name")
    private String talentName;

    @Column(name = "talent_photo_filename")
    private String talentPhotoFilename;

    @Column(name = "employee_number")
    private String employeeNumber;

    @Column(name = "gender")
    private Character gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MMM-dd")
    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "talent_description")
    private String talentDescription;

    @Column(name = "talent_cv_filename")
    private String talentCvFilename;

    @Column(name = "experience")
    private Integer experience;

    @Column(name = "email")
    private String email;

    @Column(name = "cellphone")
    private String cellphone;

    @Column(name = "biography_video_url")
    private String biographyVideoUrl;

    @Column(name = "is_add_to_list_enable")
    private Boolean isAddToListEnable;

    @Column(name = "talent_availability")
    private Boolean talentAvailibility;

    @Column(name = "is_active")
    private Boolean isActive;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false)
    private Date createdTime;

    // @LastModifiedDate
    // @Temporal(TemporalType.TIMESTAMP)
    // @Column(name = "modified_time")
    // private Date modifiedTime;  

    @Column(name = "created_by")
    private String createdBy;

    // @Column(name = "modified_by")
    // private String modifiedBy;
}

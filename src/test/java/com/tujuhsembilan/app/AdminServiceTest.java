package com.tujuhsembilan.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tujuhsembilan.app.dto.request.SaveTalentRequest;
import com.tujuhsembilan.app.dto.response.ApiResponse;
import com.tujuhsembilan.app.model.EmployeeStatus;
import com.tujuhsembilan.app.model.Talent;
import com.tujuhsembilan.app.model.TalentLevel;
import com.tujuhsembilan.app.model.TalentStatus;
import com.tujuhsembilan.app.repository.EmployeeStatusRepository;
import com.tujuhsembilan.app.repository.TalentLevelRepository;
import com.tujuhsembilan.app.repository.TalentMetadataRepository;
import com.tujuhsembilan.app.repository.TalentPositionRepository;
import com.tujuhsembilan.app.repository.TalentRepository;
import com.tujuhsembilan.app.repository.TalentSkillsetRepository;
import com.tujuhsembilan.app.repository.TalentStatusRepository;
import com.tujuhsembilan.app.service.mastermanagement.AdminService;

import lib.minio.MinioSrvc;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
    
    @Mock
    private MinioSrvc minioSrvc;

    @Mock
    private EmployeeStatusRepository employeeStatusRepository;
    @Mock
    private TalentRepository talentRepository;
    @Mock
    private TalentLevelRepository talentLevelRepository;
    @Mock
    private TalentMetadataRepository talentMetadataRepository;
    @Mock
    private TalentPositionRepository talentPositionRepository;
    @Mock
    private TalentSkillsetRepository talentSkillsetRepository;
    @Mock
    private TalentStatusRepository talentStatusRepository;

    @InjectMocks
    private AdminService adminService;

    @Test
    public void saveTalent_AddNewTalent() {
        UUID talentLevelId = UUID.randomUUID();
        UUID employeeStatusId = UUID.randomUUID();
        UUID talentStatusId = UUID.randomUUID();
        TalentLevel talentLevel = new TalentLevel();
        talentLevel.setTalentLevelId(talentLevelId);
        EmployeeStatus employeeStatus = new EmployeeStatus();
        employeeStatus.setEmployeeStatusId(employeeStatusId);
        TalentStatus talentStatus = new TalentStatus();
        talentStatus.setTalentStatusId(talentStatusId);

        SaveTalentRequest request = new SaveTalentRequest();
        // request.setTalentName("John Doe");
        request.setTalentLevelId(talentLevelId);
        request.setEmployeeStatusId(employeeStatusId);
        request.setTalentStatusId(talentStatusId);

        when(talentLevelRepository.findById(talentLevelId)).thenReturn(Optional.of(talentLevel));
        when(employeeStatusRepository.findById(employeeStatusId)).thenReturn(Optional.of(employeeStatus));
        when(talentStatusRepository.findById(talentStatusId)).thenReturn(Optional.of(talentStatus));

        Talent savedTalent = new Talent();
        savedTalent.setTalentName("NOthing");
        when(talentRepository.save(any(Talent.class))).thenReturn(savedTalent);

        ResponseEntity<ApiResponse> responseEntity = adminService.saveTalent(null, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ApiResponse responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals("Data talent saved", responseBody.getMessage());
        assertEquals(HttpStatus.OK.toString(), responseBody.getStatus());
        assertEquals(HttpStatus.OK.value(), responseBody.getStatusCode());
        Talent result = (Talent)responseBody.getData();
        verify(talentRepository, times(1)).save(any(Talent.class));
    }

    @Test
    public void updateTalent_ShouldChangeTalentName_WhenTalentNameIsEdited() {
        UUID talentId = UUID.fromString("0190de8c-1ed9-7115-8ae5-e0a2d06fb866");
        Talent talent = Talent.builder()
                        .talentId(talentId)
                        .talentName("John Doe")
                        .talentCvFilename("null")
                        .talentPhotoFilename("null")
                        .experience(5)
                        .build();

        UUID talentLevelId = UUID.randomUUID();
        UUID employeeStatusId = UUID.randomUUID();
        UUID talentStatusId = UUID.randomUUID();

        TalentLevel talentLevel = new TalentLevel();
        talentLevel.setTalentLevelId(talentLevelId);
        EmployeeStatus employeeStatus = new EmployeeStatus();
        employeeStatus.setEmployeeStatusId(employeeStatusId);
        TalentStatus talentStatus = new TalentStatus();
        talentStatus.setTalentStatusId(talentStatusId);

        SaveTalentRequest request = new SaveTalentRequest();
        request.setTalentName("James Doe");
        request.setTalentId(talentId);
        request.setTalentLevelId(talentLevelId);
        request.setEmployeeStatusId(employeeStatusId);
        request.setTalentStatusId(talentStatusId);

        when(talentLevelRepository.findById(talentLevelId)).thenReturn(Optional.of(talentLevel));
        when(employeeStatusRepository.findById(employeeStatusId)).thenReturn(Optional.of(employeeStatus));

        Talent savedTalent = new Talent();
        
        when(talentRepository.findById(talentId)).thenReturn(Optional.of(talent));
        when(talentRepository.save(any(Talent.class))).thenReturn(savedTalent);

        Talent editedTalent = Talent.builder()
                    .talentId(talentId)
                    .talentName("John Doe")
                    .talentCvFilename("null")
                    .talentPhotoFilename("null")
                    .experience(5)
                    .build();

        ResponseEntity<ApiResponse> responseEntity = adminService.updateDataTalent(null, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ApiResponse responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals("Data talent updated", responseBody.getMessage());
        assertEquals(HttpStatus.OK.toString(), responseBody.getStatus());
        assertEquals(HttpStatus.OK.value(), responseBody.getStatusCode());

        verify(talentRepository, times(1)).save(any(Talent.class));
    }
} 

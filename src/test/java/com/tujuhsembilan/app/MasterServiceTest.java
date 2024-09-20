package com.tujuhsembilan.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tujuhsembilan.app.dto.response.ApiResponse;
import com.tujuhsembilan.app.model.EmployeeStatus;
import com.tujuhsembilan.app.model.Skillset;
import com.tujuhsembilan.app.model.SkillsetType;
import com.tujuhsembilan.app.model.TalentLevel;
import com.tujuhsembilan.app.repository.EmployeeStatusRepository;
import com.tujuhsembilan.app.repository.PositionRepository;
import com.tujuhsembilan.app.repository.SkillsetRepository;
import com.tujuhsembilan.app.repository.TalentLevelRepository;
import com.tujuhsembilan.app.repository.TalentRepository;
import com.tujuhsembilan.app.repository.TalentStatusRepository;
import com.tujuhsembilan.app.service.mastermanagement.MasterService;

@ExtendWith(MockitoExtension.class)
public class MasterServiceTest {
    
    @Mock
    private PositionRepository positionRepository;
    @Mock
    private TalentStatusRepository talentStatusRepository;
    @Mock
    private TalentLevelRepository talentLevelRepository;
    @Mock
    private EmployeeStatusRepository employeeStatusRepository;
    @Mock
    private SkillsetRepository skillsetRepository;
    @Mock
    private TalentRepository talentRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private MasterService masterService;

    private List<SkillsetType> skillsetTypes;
    @BeforeEach
    void init() {
        skillsetTypes = Arrays.asList(
            new SkillsetType(UUID.randomUUID(), "Programming Language", true, true, null, null, "admin", ""),
            new SkillsetType(UUID.randomUUID(), "Hardware", true, true, null, null, "admin", ""),
            new SkillsetType(UUID.randomUUID(), "UI/ UX", true, true, null, null, "admin", ""),
            new SkillsetType(UUID.randomUUID(), "Documentation", true, true, null, null, "admin", ""),
            new SkillsetType(UUID.randomUUID(), "Framework", true, true, null, null, "admin", "")
        );
    }

    @Test
    public void getLevelOptionList_Test() {
        List<TalentLevel> talentList = Arrays.asList(
            new TalentLevel(UUID.randomUUID(), "Junior", true, null, null, "admin", ""),
            new TalentLevel(UUID.randomUUID(), "Middle", true, null, null, "admin", ""),
            new TalentLevel(UUID.randomUUID(), "Senior", true, null, null, "admin", "")
        );
        when(talentLevelRepository.findAll()).thenReturn(talentList);

        ResponseEntity<ApiResponse>response = masterService.getLevelOptionLists();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ApiResponse body = response.getBody();
        assertEquals(3, body.getTotal());  
    }

    @Test
    public void getEmployeeStatusOptionLists_Test() {
        List<EmployeeStatus> employeeStatusList = Arrays.asList(
            new EmployeeStatus(UUID.randomUUID(), "Active", null, null, "admin", ""),
            new EmployeeStatus(UUID.randomUUID(), "Not Active", null, null, "admin", "")
        );
        when(employeeStatusRepository.findAll()).thenReturn(employeeStatusList);

        ResponseEntity<ApiResponse>response = masterService.getEmployeeStatusOptionLists();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ApiResponse body = response.getBody();
        assertEquals(2, body.getTotal());  
    }

    @Test
    public void getSkillsetOptionLists_Test() {
        List<Skillset> skillsets = Arrays.asList(
            new Skillset(UUID.randomUUID(), skillsetTypes.get(0), "Java", true, null, null, "admin", ""),
            new Skillset(UUID.randomUUID(), skillsetTypes.get(0), "Python", true, null, null, "admin", ""),
            new Skillset(UUID.randomUUID(), skillsetTypes.get(0), "Javascript", true, null, null, "admin", ""),
            new Skillset(UUID.randomUUID(), skillsetTypes.get(0), "C++", true, null, null, "admin", ""),
            new Skillset(UUID.randomUUID(), skillsetTypes.get(0), "C", true, null, null, "admin", "")
        );
        when(skillsetRepository.findAll()).thenReturn(skillsets);

        ResponseEntity<ApiResponse>response = masterService.getSkillsetOptionLists();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ApiResponse body = response.getBody();
        assertEquals(5, body.getTotal());  
    }
}

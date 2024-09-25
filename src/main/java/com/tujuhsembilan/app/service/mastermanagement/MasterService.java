package com.tujuhsembilan.app.service.mastermanagement;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tujuhsembilan.app.dto.EmployeeStatusDto;
import com.tujuhsembilan.app.dto.PositionDto;
import com.tujuhsembilan.app.dto.SkillsetDto;
import com.tujuhsembilan.app.dto.TalentLevelDto;
import com.tujuhsembilan.app.dto.TalentRequestStatusDto;
import com.tujuhsembilan.app.dto.TalentStatusDto;
import com.tujuhsembilan.app.dto.response.MessageResponse;
import com.tujuhsembilan.app.exception.ResourceNotFoundException;
import com.tujuhsembilan.app.model.EmployeeStatus;
import com.tujuhsembilan.app.model.Position;
import com.tujuhsembilan.app.model.Skillset;
import com.tujuhsembilan.app.model.TalentLevel;
import com.tujuhsembilan.app.model.TalentRequestStatus;
import com.tujuhsembilan.app.model.TalentStatus;
import com.tujuhsembilan.app.repository.EmployeeStatusRepository;
import com.tujuhsembilan.app.repository.PositionRepository;
import com.tujuhsembilan.app.repository.SkillsetRepository;
import com.tujuhsembilan.app.repository.TalentLevelRepository;
import com.tujuhsembilan.app.repository.TalentRequestStatusRepository;
import com.tujuhsembilan.app.repository.TalentStatusRepository;

@Service
public class MasterService {

    private final ModelMapper modelMapper;
    private final PositionRepository positionRepository;
    private final SkillsetRepository skillsetRepository;
    private final EmployeeStatusRepository employeeStatusRepository;
    private final TalentLevelRepository talentLevelRepository;
    private final TalentRequestStatusRepository talentRequestStatusRepository;
    private final TalentStatusRepository talentStatusRepository;
    
    public MasterService(
        final ModelMapper modelMapper,
        final EmployeeStatusRepository employeeStatusRepository,
        final PositionRepository positionRepository,
        final SkillsetRepository skillsetRepository,
        final TalentLevelRepository talentLevelRepository,
        final TalentRequestStatusRepository talentRequestStatusRepository,
        final TalentStatusRepository talentStatusRepository
    ) {
        this.modelMapper = modelMapper;
        
        this.employeeStatusRepository = employeeStatusRepository;
        this.positionRepository = positionRepository;
        this.skillsetRepository = skillsetRepository;
        this.talentLevelRepository = talentLevelRepository;
        this.talentRequestStatusRepository = talentRequestStatusRepository;
        this.talentStatusRepository = talentStatusRepository;   
    }

    public ResponseEntity<MessageResponse> getLevelOptionLists() {
        List<TalentLevel> talentLevels = talentLevelRepository.findAll();
        if (talentLevels == null || talentLevels.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<TalentLevelDto> talentLevelDtos = talentLevels
                                        .stream()
                                        .map(talentLevel -> modelMapper.map(talentLevel, TalentLevelDto.class))
                                        .toList();
        return ResponseEntity.ok().body(
            MessageResponse.builder()
                .message("Data level option lists")
                .total(talentLevelDtos.size())
                .data(talentLevelDtos)
                .status(HttpStatus.OK.toString())
                .statusCode(HttpStatus.OK.value())
                .build()
        );
    }

    public ResponseEntity<MessageResponse> getTalentStatusOptionLists() {
        List<TalentStatus> talentStatuses = talentStatusRepository.findAll();
        if (talentStatuses == null || talentStatuses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<TalentStatusDto> talentStatusDtos = talentStatuses
                                        .stream()
                                        .map(talentStatus -> TalentStatusDto.map(talentStatus))
                                        .toList();
        return ResponseEntity.ok().body(
            MessageResponse.builder()
                .message("Data level option lists")
                .total(talentStatusDtos.size())
                .data(talentStatusDtos)
                .status(HttpStatus.OK.toString())
                .statusCode(HttpStatus.OK.value())
                .build()
        );
    }

    public ResponseEntity<MessageResponse> getEmployeeStatusOptionLists() {
        List<EmployeeStatus> employeeStatuses = employeeStatusRepository.findAll();
        if (employeeStatuses == null || employeeStatuses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<EmployeeStatusDto> employeeStatusDtos = employeeStatuses
                                            .stream()
                                            .map(employeeStatus -> EmployeeStatusDto.map(employeeStatus))
                                            .toList();
        return ResponseEntity.ok().body(
            MessageResponse.builder()
                .message("Data Employee Status Option Lists")
                .total(employeeStatusDtos.size())
                .data(employeeStatusDtos)
                .status(HttpStatus.OK.toString())
                .statusCode(HttpStatus.OK.value())
                .build()
        );
    }

    public ResponseEntity<MessageResponse> getSkillsetOptionLists() {
        List<Skillset> skillsets = skillsetRepository.findAll();
        if (skillsets == null || skillsets.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<SkillsetDto> skillsetDtos = skillsets.stream()
                                        .map(skillset -> SkillsetDto.map(skillset))
                                        .toList();

        return ResponseEntity.ok().body(
            MessageResponse.builder()
                .message("Data Skillsets option lists")
                .total(skillsetDtos.size())
                .data(skillsetDtos)
                .status(HttpStatus.OK.toString())
                .statusCode(HttpStatus.OK.value())
                .build()
        );
    }

    public ResponseEntity<MessageResponse> getTalentPositionOptionLists() {
        List<Position> positions = positionRepository.findAll();
        if (positions == null || positions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<PositionDto> positionDtos = positions
                                        .stream()
                                        .map(item -> modelMapper.map(item, PositionDto.class))
                                        .toList();
        return ResponseEntity.ok().body(
            MessageResponse.builder()
                .message("Data Position option lists")
                .total(positionDtos.size())
                .data(positionDtos)
                .status(HttpStatus.OK.toString())
                .statusCode(HttpStatus.OK.value())
                .build()
        );
    }

    public ResponseEntity<MessageResponse> getTalentRequestStatusOptionLists() {
        List<TalentRequestStatus> talentRequestStatus = talentRequestStatusRepository.findAll();
        if (talentRequestStatus == null || talentRequestStatus.isEmpty()) {
            throw new ResourceNotFoundException("Talent Request Status Tidak Ditemukan");
        }
        List<TalentRequestStatusDto> talentRequestStatusDtos = talentRequestStatus
                                        .stream()
                                        .map(item -> modelMapper.map(item, TalentRequestStatusDto.class))
                                        .toList();
        return ResponseEntity.ok().body(
            MessageResponse.builder()
                .message("Data Talent Request option lists")
                .total(talentRequestStatusDtos.size())
                .data(talentRequestStatusDtos)
                .status(HttpStatus.OK.toString())
                .statusCode(HttpStatus.OK.value())
                .build()
        );
    }
}

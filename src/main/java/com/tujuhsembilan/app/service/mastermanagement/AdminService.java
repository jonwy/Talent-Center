package com.tujuhsembilan.app.service.mastermanagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tujuhsembilan.app.dto.request.SaveTalentRequest;
import com.tujuhsembilan.app.dto.response.ApiResponse;
import com.tujuhsembilan.app.exception.DataNotFoundException;
import com.tujuhsembilan.app.exception.ResourceNotFoundException;
import com.tujuhsembilan.app.model.EmployeeStatus;
import com.tujuhsembilan.app.model.Position;
import com.tujuhsembilan.app.model.Skillset;
import com.tujuhsembilan.app.model.Talent;
import com.tujuhsembilan.app.model.TalentLevel;
import com.tujuhsembilan.app.model.TalentMetadata;
import com.tujuhsembilan.app.model.TalentMetadataId;
import com.tujuhsembilan.app.model.TalentPosition;
import com.tujuhsembilan.app.model.TalentPositionId;
import com.tujuhsembilan.app.model.TalentSkillset;
import com.tujuhsembilan.app.model.TalentSkillsetId;
import com.tujuhsembilan.app.model.TalentStatus;
import com.tujuhsembilan.app.repository.EmployeeStatusRepository;
import com.tujuhsembilan.app.repository.PositionRepository;
import com.tujuhsembilan.app.repository.SkillsetRepository;
import com.tujuhsembilan.app.repository.TalentLevelRepository;
import com.tujuhsembilan.app.repository.TalentMetadataRepository;
import com.tujuhsembilan.app.repository.TalentPositionRepository;
import com.tujuhsembilan.app.repository.TalentRepository;
import com.tujuhsembilan.app.repository.TalentSkillsetRepository;
import com.tujuhsembilan.app.repository.TalentStatusRepository;

import jakarta.transaction.Transactional;
import lib.minio.MinioSrvc;
import lib.minio.MinioSrvc.UploadOption;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AdminService {
    
    private static final String[] ALLOWED_CONTENT_TYPES = 
            { "image/png", "image/jpeg", "image/jpg",
                "application/msword",
                "application/pdf",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            };

    private final MinioSrvc minioSrvc;

    private final EmployeeStatusRepository employeeStatusRepository;
    private final TalentRepository talentRepository;
    private final TalentLevelRepository talentLevelRepository;
    private final TalentMetadataRepository talentMetadataRepository;
    private final TalentPositionRepository talentPositionRepository;
    private final TalentSkillsetRepository talentSkillsetRepository;
    private final TalentStatusRepository talentStatusRepository;

    // To Be Deleted
    private final PositionRepository positionRepository;
    private final SkillsetRepository skillsetRepository;

    public AdminService(
            final ModelMapper modelMapper,
            final MinioSrvc minioSrvc,
            final EmployeeStatusRepository employeeStatusRepository,
            final TalentRepository talentRepository,
            final TalentLevelRepository talentLevelRepository, 
            final TalentMetadataRepository talentMetadataRepository,
            final TalentPositionRepository talentPositionRepository,
            final TalentSkillsetRepository talentSkillsetRepository,
            final TalentStatusRepository talentStatusRepository,
             /* to be deleted */
             final PositionRepository positionRepository,
            final SkillsetRepository skillsetRepository
    ) {
        this.minioSrvc = minioSrvc;

        this.employeeStatusRepository = employeeStatusRepository;
        this.talentRepository = talentRepository;
        this.talentLevelRepository = talentLevelRepository;
        this.talentMetadataRepository = talentMetadataRepository;
        this.talentPositionRepository = talentPositionRepository;
        this.talentSkillsetRepository = talentSkillsetRepository;
        this.talentStatusRepository = talentStatusRepository;
        // to be deleted
        this.positionRepository = positionRepository;
        this.skillsetRepository = skillsetRepository;
    }

    @Transactional
    public ResponseEntity<ApiResponse> saveTalent(MultipartFile[] fileRequests, SaveTalentRequest request) {

        TalentLevel talentLevel = talentLevelRepository.findById(request.getTalentLevelId()).get();
        EmployeeStatus employeeStatus = employeeStatusRepository.findById(request.getEmployeeStatusId()).get();
        TalentStatus talentStatus = talentStatusRepository.findById(request.getTalentStatusId()).get();
        Talent talent = createTalent(request);
        talent.setTalentStatus(talentStatus);
        talent.setTalentLevel(talentLevel);
        talent.setEmployeeStatus(employeeStatus);
        if (fileRequests != null && fileRequests.length != 0) {
            uploadToMinio(fileRequests, talent);
        }
        Talent saved = talentRepository.save(talent);

        
        Set<UUID> requestSkillsetsId = new HashSet<>();
        if (request.getSkillsets() != null && !request.getSkillsets().isEmpty()) {
            requestSkillsetsId = request.getSkillsets().stream().map(item -> item.getSkillsetId()).collect(Collectors.toSet());
        }
        Set<UUID> requestPositionsId = new HashSet<>();
        if (request.getPositions() != null && !request.getPositions().isEmpty()) {
            requestPositionsId = request.getPositions().stream().map(item -> item.getPositionId()).collect(Collectors.toSet());
        }
        saveTalentPosition(saved, requestPositionsId);
        saveTalentSkillset(saved, requestSkillsetsId); 
        saveTalentMetadata(saved.getTalentId(), request.getTotalProjectCompleted());

        return ResponseEntity.ok()
            .body(
                ApiResponse.builder()
                    .message("Data talent saved")
                    .data(saved)
                    .status(HttpStatus.OK.toString())
                    .statusCode(HttpStatus.OK.value())
                    .build()    
            );
    }

    @Transactional
    public ResponseEntity<ApiResponse> updateDataTalent(MultipartFile[] files, SaveTalentRequest request) {
        // check if there's a file to be uploaded
        Talent talent = talentRepository.findById(request.getTalentId()).orElseThrow(() -> new DataNotFoundException("Talent Tidak ditemukan"));
        TalentLevel talentLevel = talentLevelRepository.findById(request.getTalentLevelId()).orElseThrow(() -> new ResourceNotFoundException("Talent Level not found"));
        EmployeeStatus employeeStatus = employeeStatusRepository.findById(request.getEmployeeStatusId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee Status not found"));
        
        talent.setTalentAvailibility(request.getTalentAvailibility());
        talent.setTalentDescription(request.getTalentDescription());
        talent.setTalentName(request.getTalentName());
        talent.setTalentLevel(talentLevel);
        talent.setEmployeeStatus(employeeStatus);
        talent.setBiographyVideoUrl(request.getVideoUrl());
        talent.setExperience(request.getExperience());
        talent.setBirthDate(request.getBirthdate());
        talent.setEmail(request.getEmail());
        talent.setCellphone(request.getCellphone());
        talent.setGender(request.getGender());
        if (files != null && files.length != 0) {
            log.info("files is not null");
            uploadToMinio(files, talent);
        }
        else {
            log.info("file is null");
        }
        Talent saved = talentRepository.save(talent);
        Set<UUID> requestSkillsetsId = new HashSet<>();
        if (request.getSkillsets() != null && !request.getSkillsets().isEmpty()) {
            requestSkillsetsId = request.getSkillsets().stream().map(item -> item.getSkillsetId()).collect(Collectors.toSet());
        }
        Set<UUID> requestPositionsId = new HashSet<>();
        if (request.getPositions() != null && !request.getPositions().isEmpty()) {
            requestPositionsId = request.getPositions().stream().map(item -> item.getPositionId()).collect(Collectors.toSet());
        }
        saveTalentSkillset(talent, requestSkillsetsId);
        saveTalentMetadata(talent.getTalentId(), request.getTotalProjectCompleted());
        return ResponseEntity.ok()
            .body(
                ApiResponse.builder()
                    .message("Data talent updated")
                    .data(saved)
                    .status(HttpStatus.OK.toString())
                    .statusCode(HttpStatus.OK.value())
                    .build()
            );
    }

    private void uploadToMinio(MultipartFile[] files, Talent talent) {
        // TODO: add validation for the files
        log.info("logging the upload to Minio");
        Boolean isPhotoUploaded = false;
        Boolean isCvUploaded = false;
        for (MultipartFile file: files) {
            log.info("logging the upload to Minio: inside the for loop for file type: " + file.getOriginalFilename());
            if (Arrays.asList(ALLOWED_CONTENT_TYPES).contains(file.getContentType())) {
                Boolean isImage = file.getContentType().equals("image/png")
                                || file.getContentType().equals("image/jpeg")
                                || file.getContentType().equals("image/jpg");
                String originalFilename = file.getOriginalFilename();
                String fileType = originalFilename.substring(originalFilename.lastIndexOf('.'));

                final String filename = talent.getTalentId() != null
                                ? (isImage ? talent.getTalentPhotoFilename() : talent.getTalentCvFilename()) :
                                String.format(
                                    "%s_%s_%s_%s%s", 
                                    talent.getTalentName().replace(' ', '_'),
                                    talent.getExperience(),
                                    talent.getTalentLevel().getTalentLevelName(),
                                    System.currentTimeMillis(),
                                    fileType
                                );
                log.info("logging upload to Minio: " + filename);
                if (isImage) {
                    if (!isPhotoUploaded) isPhotoUploaded = true;
                    else return; // throw an exception;
                    talent.setTalentPhotoFilename(filename);
                }
                if (!isImage) {
                    if (!isCvUploaded) isCvUploaded = true;
                    else return; // throw an exception;
                    talent.setTalentCvFilename(filename);
                }
                if (talent.getTalentId() == null) {
                    minioSrvc.removeObject(filename, "talent-center-app");
                }
                minioSrvc
                    .upload(file, "talent-center-app",
                        opt -> UploadOption.builder().filename(
                            filename
                        ).build()
                    );
            }
        }
    }

    private Talent createTalent(SaveTalentRequest request) {
        return Talent.builder()
            .talentName(request.getTalentName())
            .experience(request.getExperience())
            .email(request.getEmail())
            .talentDescription(request.getTalentDescription())
            .cellphone(request.getCellphone())
            .talentAvailibility(true)
            .isActive(true)
            .birthDate(request.getBirthdate())
            .biographyVideoUrl(request.getVideoUrl())
            .gender(request.getGender())
            .employeeNumber("10" + request.getExperience() + System.currentTimeMillis())
            .build();
    }

    @Transactional
    private void saveTalentSkillset(Talent talent, Set<UUID> skillsetIds) {
        Set<Skillset> skillsets = talent.getSkillsets();
        if (skillsets != null) {
            skillsets.forEach(item -> talentSkillsetRepository.deleteById(new TalentSkillsetId(talent.getTalentId(), item.getSkillsetId())));
        }
        Set<TalentSkillset> talentSkillsets = new HashSet<>();
        skillsetIds.forEach(skillsetId -> {
            TalentSkillset talentSkillset = new TalentSkillset();
            talentSkillset.setId(new TalentSkillsetId(talent.getTalentId(), skillsetId));
            talentSkillsets.add(talentSkillset);
        });
        talentSkillsetRepository.saveAll(talentSkillsets);
    }
    
    @Transactional
    private void saveTalentPosition(Talent talent, Set<UUID> positionIds) {
        Set<UUID> talentPositionsId = talent.getPositions() != null ? 
                                    talent.getPositions()
                                        .stream()
                                        .map(item -> item.getPositionId())
                                        .collect(Collectors.toSet()) : 
                                        null;
        Boolean shouldSaveTheData = talentPositionsId == null || !talentPositionsId.containsAll(talentPositionsId);
        if (shouldSaveTheData) {
            Set<TalentPosition> talentPositions = new HashSet<>();
            positionIds.forEach(positionId -> {
                TalentPosition talentPosition = new TalentPosition();
                talentPosition.setId(new TalentPositionId(talent.getTalentId(), positionId));
                talentPositions.add(talentPosition);
            });
            talentPositionRepository.saveAll(talentPositions);
        }
    }

    @Transactional
    private void saveTalentMetadata(UUID talentId, Integer totalProjectCompleted) {
        TalentMetadata talentMetadata = talentMetadataRepository.findById(new TalentMetadataId(talentId))
                .orElseGet(() -> TalentMetadata.builder()
                        .id(new TalentMetadataId(talentId))
                        .cvCounter(0)
                        .profileCounter(0)
                        .build());
        talentMetadata.setTotalProjectCompleted(totalProjectCompleted);
        talentMetadataRepository.save(talentMetadata);
    }

    public ResponseEntity<ApiResponse> saveTalent2(MultipartFile[] fileRequests, SaveTalentRequest request) {

        List<Position> positions = positionRepository.findAll();
        List<Skillset> skillsets = skillsetRepository.findAll();

        int max = 5;
        int min = 1;

        // Random SKillsets, with min of 1 and max of 5
        Integer randomInteger = (int) (Math.random() * (max - min + 1)) + min;
        List<Integer> numbersForTotalSkillsetOrPosition = new ArrayList<>();
        for (int i = 0; i < skillsets.size(); i++) {
            numbersForTotalSkillsetOrPosition.add(i);
        }
        Set<UUID> requestSkillsetsId = new HashSet<>();
        Collections.shuffle(numbersForTotalSkillsetOrPosition);
        for (int i = 0 ; i < randomInteger; i++) {
            requestSkillsetsId.add(skillsets.get(numbersForTotalSkillsetOrPosition.get(i)).getSkillsetId());
        }

        // clear the collection
        numbersForTotalSkillsetOrPosition.clear();

        // Do random again for the position, with min of 1 and max of 5
        randomInteger = (int) (Math.random() * (max - min + 1)) + min;
        for (int i = 0; i < positions.size(); i++) {
            numbersForTotalSkillsetOrPosition.add(i);
        }
        Set<UUID> requestPositionsId = new HashSet<>();
        Collections.shuffle(numbersForTotalSkillsetOrPosition);
        for (int i = 0 ; i < randomInteger; i++) {
            requestPositionsId.add(positions.get(numbersForTotalSkillsetOrPosition.get(i)).getPositionId());
        }

        // clear the collection
        // free the memory
        numbersForTotalSkillsetOrPosition.clear();
        numbersForTotalSkillsetOrPosition = null;

        TalentLevel talentLevel = talentLevelRepository.findById(request.getTalentLevelId()).get();
        EmployeeStatus employeeStatus = employeeStatusRepository.findById(request.getEmployeeStatusId()).get();
        Talent talent = createTalent(request);
        talent.setTalentLevel(talentLevel);
        talent.setEmployeeStatus(employeeStatus);
        if (fileRequests != null && fileRequests.length != 0) {
            uploadToMinio(fileRequests, talent);
        }
        Talent saved = talentRepository.save(talent);

        saveTalentPosition(saved, requestPositionsId);
        saveTalentSkillset(saved, requestSkillsetsId); 
        saveTalentMetadata(saved.getTalentId(), request.getTotalProjectCompleted());

        return ResponseEntity.ok()
            .body(
                ApiResponse.builder()
                    .message("Data talent saved")
                    .data(talent)
                    .status(HttpStatus.OK.toString())
                    .statusCode(HttpStatus.OK.value())
                    .build()
            );
    }

}

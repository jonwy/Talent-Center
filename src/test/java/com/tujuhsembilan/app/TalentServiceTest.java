package com.tujuhsembilan.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tujuhsembilan.app.dto.TalentsDto;
import com.tujuhsembilan.app.dto.request.TalentsFilterRequest;
import com.tujuhsembilan.app.dto.response.MessageResponse;
import com.tujuhsembilan.app.model.Talent;
import com.tujuhsembilan.app.repository.EmployeeStatusRepository;
import com.tujuhsembilan.app.repository.PositionRepository;
import com.tujuhsembilan.app.repository.SkillsetRepository;
import com.tujuhsembilan.app.repository.TalentLevelRepository;
import com.tujuhsembilan.app.repository.TalentMetadataRepository;
import com.tujuhsembilan.app.repository.TalentRepository;
import com.tujuhsembilan.app.repository.TalentStatusRepository;
import com.tujuhsembilan.app.service.talentmanagement.TalentService;
import com.tujuhsembilan.app.service.talentmanagement.specification.TalentSpecification;

import lib.minio.MinioSrvc;

@ExtendWith(MockitoExtension.class)
public class TalentServiceTest {
    
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
    private MinioSrvc minioSrvc;

    @InjectMocks
    private TalentService talentService;
    @Mock
    private TalentMetadataRepository talentMetadataRepository;

    private final static List<Talent> talents = new ArrayList<>();

    @BeforeEach
    public void init() {
        Talent fullTalent = new Talent();
        talents.addAll(Arrays.asList(
            Talent.builder().talentId(UUID.fromString("0190de8c-1ed9-7115-8ae5-e0a2d06fb866")).talentName("John Doe").talentCvFilename("null").talentPhotoFilename("null").experience(5).build(),
            Talent.builder().talentId(UUID.fromString("0190de8c-7c79-7079-808e-3f6e48ddc42f")).talentName("Phil Phoden").experience(3).talentCvFilename("null").talentPhotoFilename("null").build(),
            Talent.builder().talentId(UUID.fromString("0190de8e-83c0-7cfe-a0be-f827961a2f42")).talentName("Wayne Rooney").experience(2).talentCvFilename("null").talentPhotoFilename("null").build(),
            Talent.builder().talentId(UUID.fromString("0190de8e-b79c-7b08-b248-60896477ac96")).talentName("Lionel Messi").experience(2).talentCvFilename("null").talentPhotoFilename("null").build(),
            Talent.builder().talentId(UUID.fromString("0190de8e-b79c-7c37-b8d9-5374a6f40b89")).talentName("Cristiano Ronaldo").experience(1).talentCvFilename("null").talentPhotoFilename("null").build(),
            Talent.builder().talentId(UUID.fromString("0190de8e-b79c-7397-a3c6-6e275ef5afff")).talentName("Kevin De Bruyne").experience(7).talentCvFilename("null").talentPhotoFilename("null").build(),
            Talent.builder().talentId(UUID.fromString("0190de8e-b79c-7b10-9922-5ca8d0b5b474")).talentName("Thomas Muller").experience(11).talentCvFilename("null").talentPhotoFilename("null").build(),
            Talent.builder().talentId(UUID.fromString("0190de8e-b79c-7c4e-857c-fdc1c7f5fcf6")).talentName("Robert Lewandowski").experience(8).talentCvFilename("null").talentPhotoFilename("null").build(),
            Talent.builder().talentId(UUID.fromString("0190de8e-b79c-74b4-a9b4-ab7d6f27614e")).talentName("Luis Suarez").experience(2).talentCvFilename("null").talentPhotoFilename("null").build(),
            Talent.builder().talentId(UUID.fromString("0190de8e-b79c-7412-b873-3b07bef05b90")).talentName("Gareth Bale").experience(3).talentCvFilename("null").talentPhotoFilename("null").build(),
            Talent.builder().talentId(UUID.fromString("0190de8e-b79c-741b-9229-8704a421db0f")).talentName("Toni Kroos").experience(4).talentCvFilename("null").talentPhotoFilename("null").build(),
            Talent.builder().talentId(UUID.fromString("0190de8e-b79c-7155-95e9-1697bcaf61bb")).talentName("Luka Modric").experience(4).talentCvFilename("null").talentPhotoFilename("null").build(),
            Talent.builder().talentId(UUID.fromString("0190de8e-b79c-71e0-bca3-42b242314bc9")).talentName("Eden Hazard").experience(6).talentCvFilename("null").talentPhotoFilename("null").build(),
            Talent.builder().talentId(UUID.fromString("0190de8e-b79c-701d-8ba9-4cf9344186bd")).talentName("Erling Haaland").experience(6).talentCvFilename("null").talentPhotoFilename("null").build(),
            Talent.builder().talentId(UUID.fromString("0190de8e-b79c-7316-9ae8-9baa08ba5679")).talentName("Kylian Mbamppe").experience(10).talentCvFilename("null").talentPhotoFilename("null").build(),
            Talent.builder().talentId(UUID.fromString("0190de8e-b79c-7f66-9d79-8c6ed2503769")).talentName("Son Heung Min").experience(4).talentCvFilename("null").talentPhotoFilename("null").build(),
            Talent.builder().talentId(UUID.fromString("0190de8e-b79c-7ea1-9440-9b484e5f617e")).talentName("Keisuke Honda").experience(15).talentCvFilename("null").talentPhotoFilename("null").build(),
            Talent.builder().talentId(UUID.fromString("0190de8e-b79c-76aa-92d5-cc5fd7f4bdb2")).talentName("Ricardo Kaka").experience(20).talentCvFilename("null").talentPhotoFilename("null").build()
        ));
    }

    @Test
    public void getTalents_WithDefaultPagination_ReturnTalentsWithSizeAndPage() {
        List<Talent> subTalentList = talents.subList(0, 8);
        
        // pageable stub
        Pageable pageable = PageRequest.of(0, 8);
        Page<Talent> talentPage = new PageImpl<>(subTalentList, pageable, talents.size());
        // when(talentRepository.findAll(any(Pageable.class))).thenReturn(talentPage);
        when(talentRepository.findAll(pageable)).thenReturn(talentPage);

        ResponseEntity<MessageResponse> response = talentService.getTalents(PageRequest.of(0, 8), null);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        MessageResponse success = (MessageResponse) response.getBody();
        List<TalentsDto> talentResult = (List<TalentsDto>) success.getData();
        ModelMapper mapper = new ModelMapper();
        assertNotNull(talentResult);
        List<TalentsDto> talentDtos = talents.stream().map(item -> mapper.map(item, TalentsDto.class)).toList();
        assertEquals(8, talentResult.size());
    }

    @Test
    public void getTalents_WithExperienceFilterAndDefaultPagination_ReturnTalentsFilteredWithAgeAndPagination() {
        Pageable pageable = PageRequest.of(0, 8);
        TalentsFilterRequest filterRequest = new TalentsFilterRequest("", "", "", "", 0, 1);
        Specification<Talent> specification = TalentSpecification.filterTalents(filterRequest);
        List<Talent> filteredTalents = talents.stream()
                                        .filter(talent -> talent.getExperience() >= 0 && talent.getExperience() <= 1)
                                        .toList();
        System.out.println("filteredTalents size: " + filteredTalents.size());
        List<Talent> subTalentList = filteredTalents.subList(
                                        0, 
                                        pageable.getPageSize() >= filteredTalents.size() 
                                            ? filteredTalents.size() <= 1 ? filteredTalents.size() : filteredTalents.size()-1 : pageable.getPageSize());
        System.out.println("subTalent size: " + subTalentList.size());         
        Page<Talent> talentPage = new PageImpl<>(subTalentList, pageable, filteredTalents.size());

        System.out.println("talentPage: " + talentPage.toList().size());
        when(talentRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(talentPage);

        ResponseEntity<MessageResponse> response = talentService.getTalents(PageRequest.of(0, 8), filterRequest);
        System.out.println(response.getStatusCode().toString());
        
        verify(talentRepository).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    public void getTalent_WithCorrectTalentId_ReturnTalent() {
        UUID talentUuid = UUID.fromString("0190de8c-1ef8-7115-8ae5-2132306fb866");
        String talentUuidString = "0190de8c-1ef8-7115-8ae5-2132306fb866";
        Optional<Talent> talent = Optional.of(
                                talents.stream()
                                        .filter(item -> item.getTalentId().equals(talentUuid)).findFirst().get());
        when(talentRepository.findById(talentUuid)).thenReturn(talent);
        ResponseEntity<MessageResponse> response = talentService.getTalent(talentUuidString);
    }

    @Test
    public void getTalent_WithIncorrectTalentId_ReturnNull() {
        UUID talentUuid = UUID.fromString("0190de8c-1ef8-7115-8ae5-2132306fb866");
        String talentUuidString = "0190de8c-1ef8-7115-8ae5-2132306fb866";
        Optional<Talent> talent = talents.stream()
                                    .filter(item -> item.getTalentId().equals(talentUuid)).findFirst();
        when(talentRepository.findById(talentUuid)).thenReturn(talent);
        ResponseEntity<MessageResponse> response = talentService.getTalent(talentUuidString);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}

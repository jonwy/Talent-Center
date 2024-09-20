package com.tujuhsembilan.app.service.talentmanagement;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tujuhsembilan.app.dto.TalentDetailDto;
import com.tujuhsembilan.app.dto.TalentsDto;
import com.tujuhsembilan.app.dto.request.TalentsFilterRequest;
import com.tujuhsembilan.app.dto.response.ApiResponse;
import com.tujuhsembilan.app.model.Talent;
import com.tujuhsembilan.app.repository.TalentRepository;
import com.tujuhsembilan.app.service.talentmanagement.specification.TalentSpecification;

import lib.minio.MinioSrvc;

@Service
public class TalentService {
    
    private final TalentRepository talentRepository;;
    private final MinioSrvc minioSrvc;

    public TalentService(final TalentRepository talentRepository,
        final MinioSrvc minioSrvc
    ) {
        this.talentRepository = talentRepository;
        this.minioSrvc = minioSrvc;
    }

    public ResponseEntity<Object> getTalents(Pageable pageable, TalentsFilterRequest filterRequest) {
        Integer pageSize = pageable.getPageSize();
        Integer pageNumber = pageable.getPageNumber();
        System.out.println("pagesize: " + pageSize);
        System.out.println("pageNumber: " + pageNumber);
        Specification<Talent> specification = TalentSpecification.filterTalents(filterRequest);

        List<Talent> talents = null;
        Long totalRows = 0L;

        if (filterRequest != null) {
            System.out.println("filterRequest tidak null");
            talents = talentRepository.findAll(specification, PageRequest.of(pageNumber, pageSize, pageable.getSort())).toList();
            totalRows = talentRepository.count(specification);
        }
        else {
            System.out.println("filterRequet is nnull");
            talents = talentRepository.findAll(PageRequest.of(pageNumber, pageSize, pageable.getSort())).toList();
            totalRows = talentRepository.count();
        }
        
        if (talents == null || talents.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<TalentsDto> talentDtos = talents
                    .stream()
                    .map(talent -> {
                        String imageUrl = minioSrvc.getUrl(talent.getTalentPhotoFilename(),"talent-center-app");
                        talent.setTalentPhotoFilename(imageUrl);
                        String cvUrl = minioSrvc.getUrl(talent.getTalentCvFilename(), "talent-center-app");
                        talent.setTalentCvFilename(cvUrl);
                        return TalentsDto.map(talent);
                    })
                    .toList();
        return ResponseEntity.ok().body(
            ApiResponse.builder()
                .total(talentDtos.size())
                .data(talentDtos)
                .message("Berhasil mengambil list talent")
                .status(HttpStatus.OK.toString())
                .statusCode(HttpStatus.OK.value())
                .totalRows(totalRows)
                .build()
        );
    }

    public ResponseEntity<Object> getTalent(UUID talentId) {
        Talent talent = talentRepository.findById(talentId).orElse(null);
        if (talent == null) {
            return ResponseEntity.notFound().build();
        }
        String imageUrl = minioSrvc.getUrl(talent.getTalentPhotoFilename(),"talent-center-app");
        String cvUrl = minioSrvc.getUrl(talent.getTalentCvFilename(), "talent-center-app");
        
        TalentDetailDto talentDto = TalentDetailDto.mapToDto(talent);
        talentDto.setTalentCvUrl(cvUrl);
        talentDto.setTalentPhotoUrl(imageUrl);
        return ResponseEntity.ok().body(
            ApiResponse.builder()
                .message("Berhasil mengambil data talent: " + talentDto.getTalentName())
                .data(talentDto)
                .status(HttpStatus.OK.toString())
                .statusCode(HttpStatus.OK.value())
                .build()
        );
    }
}

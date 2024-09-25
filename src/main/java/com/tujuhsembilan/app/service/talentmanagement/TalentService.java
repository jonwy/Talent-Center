package com.tujuhsembilan.app.service.talentmanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tujuhsembilan.app.dto.TalentDetailDto;
import com.tujuhsembilan.app.dto.TalentsDto;
import com.tujuhsembilan.app.dto.request.TalentsFilterRequest;
import com.tujuhsembilan.app.dto.response.MessageResponse;
import com.tujuhsembilan.app.dto.response.MessageResponse.Meta;
import com.tujuhsembilan.app.exception.DataNotFoundException;
import com.tujuhsembilan.app.model.Talent;
import com.tujuhsembilan.app.repository.TalentRepository;
import com.tujuhsembilan.app.service.talentmanagement.specification.TalentSpecification;
import com.tujuhsembilan.app.util.ResponseUtil;

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

    public ResponseEntity<MessageResponse> getTalents(Pageable pageable, TalentsFilterRequest filterRequest) {
        Integer pageSize = pageable.getPageSize();
        Integer pageNumber = pageable.getPageNumber();
        System.out.println("pagesize: " + pageSize);
        System.out.println("pageNumber: " + pageNumber);
        Specification<Talent> specification = TalentSpecification.filterTalents(filterRequest);
        Page<Talent> talentPage = null;

        if (filterRequest != null) {
            System.out.println("filterRequest tidak null");
            talentPage = talentRepository.findAll(specification, PageRequest.of(pageNumber, pageSize, pageable.getSort()));
        }
        else {
            System.out.println("filterRequet is nnull");
            talentPage = talentRepository.findAll(PageRequest.of(pageNumber, pageSize, pageable.getSort()));
        }
        
        List<TalentsDto> talentDtos = new ArrayList<>();
        talentDtos = talentPage.toList()
                    .stream()
                    .map(talent -> {
                        String imageUrl = minioSrvc.getUrl(talent.getTalentPhotoFilename(),"talent-dev");
                        talent.setTalentPhotoFilename(imageUrl);
                        String cvUrl = minioSrvc.getUrl(talent.getTalentCvFilename(), "talent-dev");
                        talent.setTalentCvFilename(cvUrl);
                        return TalentsDto.map(talent);
                    }).toList();
        return ResponseUtil.createResponse(HttpStatus.OK, "Daftar talent berhasil dimuat.", talentDtos, new Meta(talentPage.getTotalPages(), pageable.getPageSize(), pageable.getPageNumber()));
    }

    public ResponseEntity<MessageResponse> getTalent(String talentId) {
        UUID talentUuid = UUID.fromString(talentId);
        Talent talent = talentRepository.findById(talentUuid).orElseThrow(() -> new DataNotFoundException("Data talent tidak ditemukan."));
        String imageUrl = minioSrvc.getUrl(talent.getTalentPhotoFilename(),"talent-dev");
        String cvUrl = minioSrvc.getUrl(talent.getTalentCvFilename(), "talent-dev");
        TalentDetailDto talentDto = TalentDetailDto.mapToDto(talent);
        talentDto.setTalentCvUrl(cvUrl);
        talentDto.setTalentPhotoUrl(imageUrl);
        return ResponseUtil.createResponse(HttpStatus.OK, "Data detail talent berhasil dimuat.", talentDto);
    }
}

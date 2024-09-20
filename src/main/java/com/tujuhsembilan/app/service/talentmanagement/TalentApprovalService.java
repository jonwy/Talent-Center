package com.tujuhsembilan.app.service.talentmanagement;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tujuhsembilan.app.dto.TalentRequestDto;
import com.tujuhsembilan.app.dto.TalentRequestStatusDto;
import com.tujuhsembilan.app.dto.request.TalentApprovalRequest;
import com.tujuhsembilan.app.dto.request.TalentApprovalsFilterRequest;
import com.tujuhsembilan.app.dto.response.ApiResponse;
import com.tujuhsembilan.app.exception.ResourceNotFoundException;
import com.tujuhsembilan.app.model.TalentRequest;
import com.tujuhsembilan.app.model.TalentRequestStatus;
import com.tujuhsembilan.app.model.TalentWishlist;
import com.tujuhsembilan.app.repository.TalentRequestRepository;
import com.tujuhsembilan.app.repository.TalentRequestStatusRepository;
import com.tujuhsembilan.app.service.talentmanagement.specification.TalentRequestSpecification;

@Service
public class TalentApprovalService {
    
    private final TalentRequestRepository talentRequestRepository;
    private final TalentRequestStatusRepository talentRequestStatusRepository;

    private final ModelMapper modelMapper;

    public TalentApprovalService(
        final TalentRequestRepository talentRequestRepository, 
        final TalentRequestStatusRepository talentRequestStatusRepository,
        final ModelMapper modelMapper
    ) {
        this.talentRequestRepository = talentRequestRepository;
        this.talentRequestStatusRepository = talentRequestStatusRepository;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<ApiResponse> getAllTalentApprovals(Pageable pageable, TalentApprovalsFilterRequest filter) {
        Specification<TalentRequest> specification = TalentRequestSpecification.getTalentRequestFilter(filter);
        List<TalentRequest> talentRequests = talentRequestRepository.findAll(specification, pageable).toList();
        Long totalRows = talentRequestRepository.count(specification);
        
        if (talentRequests == null || talentRequests.isEmpty()) {
            throw new ResourceNotFoundException("Data Talent Request is Empty or not found");
        }

        List<TalentRequestDto> talentRequestDtos = talentRequests.stream().map(talentRequest -> {
            TalentWishlist talentWishlist = talentRequest.getTalentWishlist();
            TalentRequestDto dto = TalentRequestDto.builder()
                                .talentRequestId(talentRequest.getTalentRequestId())
                                .talentRequestStatus(modelMapper.map(talentRequest.getTalentRequestStatus(), TalentRequestStatusDto.class))
                                .talentName(talentWishlist.getTalent().getTalentName())
                                .requestDate(talentRequest.getRequestDate())
                                .agencyName(talentWishlist.getClient().getAgencyName()).build();
            return dto;
        }).toList();

        return ResponseEntity.ok().body(
            ApiResponse.builder()
                .total(talentRequestDtos.size())
                .data(talentRequestDtos)
                .totalRows(totalRows)
                .message("Berhasil mengambil data talent request")
                .status(HttpStatus.OK.toString())
                .statusCode(HttpStatus.OK.value())
                .build()
        );
    }

    public ResponseEntity<ApiResponse> approveTalentRequest(TalentApprovalRequest talentApprovalRequest) {
        TalentRequest talentRequest = talentRequestRepository.findById(talentApprovalRequest.getTalentRequestId()).orElse(null);
        if (talentRequest == null) {
            return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                    .data(talentRequest)
                    .message("Talent request not found")
                    .status(HttpStatus.NOT_FOUND.toString())
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .build()
            );
        }
        TalentRequestStatus talentRequestStatus = 
                        talentRequestStatusRepository
                            .findByTalentRequestStatusNameAndCreatedByAllIgnoreCase(
                                talentApprovalRequest.getAction(), "admin"
                            ).orElseThrow(() -> new ResourceNotFoundException("talent request status tidak ditemukan"));
            talentRequest.setTalentRequestStatus(talentRequestStatus);
        if (talentApprovalRequest.getAction().equalsIgnoreCase("rejected")) {
            talentRequest.setRequestRejectReason(talentApprovalRequest.getRejectReason());
        }
        talentRequestRepository.save(talentRequest);
        return ResponseEntity.ok().body(
            ApiResponse.builder()
                .data(talentRequest)
                .message("Talent request is " + talentRequestStatus.getTalentRequestStatusName())
                .status(HttpStatus.OK.toString())
                .statusCode(HttpStatus.OK.value())
                .build()
        );
    }
}

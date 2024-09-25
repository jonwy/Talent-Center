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
import com.tujuhsembilan.app.dto.response.MessageResponse;
import com.tujuhsembilan.app.exception.ResourceNotFoundException;
import com.tujuhsembilan.app.model.TalentRequest;
import com.tujuhsembilan.app.model.TalentRequestStatus;
import com.tujuhsembilan.app.model.TalentWishlist;
import com.tujuhsembilan.app.repository.TalentRequestRepository;
import com.tujuhsembilan.app.repository.TalentRequestStatusRepository;
import com.tujuhsembilan.app.service.talentmanagement.specification.TalentRequestSpecification;
import com.tujuhsembilan.app.util.ResponseUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TalentApprovalService {
    
    private final TalentRequestRepository talentRequestRepository;
    private final TalentRequestStatusRepository talentRequestStatusRepository;

    private final ModelMapper modelMapper;

    public ResponseEntity<MessageResponse> getAllTalentApprovals(Pageable pageable, TalentApprovalsFilterRequest filter) {
        Specification<TalentRequest> specification = TalentRequestSpecification.getTalentRequestFilter(filter);
        List<TalentRequest> talentRequests = talentRequestRepository.findAll(specification, pageable).toList();

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
        return ResponseUtil.createResponse(HttpStatus.OK, "Berhasil memuat daftar talent request.", talentRequestDtos);
    }

    @Transactional
    public ResponseEntity<MessageResponse> approveTalentRequest(TalentApprovalRequest talentApprovalRequest) {
        TalentRequest talentRequest = talentRequestRepository.findById(talentApprovalRequest.getTalentRequestId()).orElse(null);
        if (talentRequest == null) {
            return ResponseUtil.createResponse(HttpStatus.NOT_FOUND, "Talent request tidak ditemukan.");
        }
        TalentRequestStatus talentRequestStatus = 
                        talentRequestStatusRepository
                            .findByTalentRequestStatusNameIgnoreCase(
                                talentApprovalRequest.getAction()
                            ).orElseThrow(() -> new ResourceNotFoundException("talent request status tidak ditemukan"));
            talentRequest.setTalentRequestStatus(talentRequestStatus);
        if (talentApprovalRequest.getAction().equalsIgnoreCase("rejected")) {
            talentRequest.setRequestRejectReason(talentApprovalRequest.getRejectReason());
        } else {
            talentRequest.setRequestRejectReason(null);
        }
        talentRequest.setTalentRequestStatus(talentRequestStatus);
        talentRequestRepository.save(talentRequest);
        return ResponseUtil.createResponse(HttpStatus.OK, "Talent request is " + talentRequestStatus.getTalentRequestStatusName());
    }
}

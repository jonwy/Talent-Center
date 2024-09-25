package com.tujuhsembilan.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

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

import com.tujuhsembilan.app.dto.TalentRequestDto;
import com.tujuhsembilan.app.dto.request.TalentApprovalRequest;
import com.tujuhsembilan.app.dto.response.MessageResponse;
import com.tujuhsembilan.app.model.Client;
import com.tujuhsembilan.app.model.EmployeeStatus;
import com.tujuhsembilan.app.model.Position;
import com.tujuhsembilan.app.model.Skillset;
import com.tujuhsembilan.app.model.Talent;
import com.tujuhsembilan.app.model.TalentLevel;
import com.tujuhsembilan.app.model.TalentRequest;
import com.tujuhsembilan.app.model.TalentRequestStatus;
import com.tujuhsembilan.app.model.TalentStatus;
import com.tujuhsembilan.app.model.TalentWishlist;
import com.tujuhsembilan.app.model.User;
import com.tujuhsembilan.app.repository.TalentRequestRepository;
import com.tujuhsembilan.app.repository.TalentRequestStatusRepository;
import com.tujuhsembilan.app.service.talentmanagement.TalentApprovalService;
import com.tujuhsembilan.app.utility.DummyGenerator;

@ExtendWith(MockitoExtension.class)
public class TalentApprovalServiceTest {

    @Mock
    private TalentRequestRepository talentRequestRepository;
    @Mock
    private TalentRequestStatusRepository talentRequestStatusRepository;
    @InjectMocks
    private TalentApprovalService talentApprovalService;
    @Mock
    private ModelMapper modelMapper;
    
    private List<TalentStatus> talentStatuses;
    private List<TalentLevel> talentLevels;
    private List<EmployeeStatus> employeeStatuses;
    private List<Skillset> skillsets;
    private List<Position> positions;
    private List<TalentRequestStatus> talentRequestStatuses;

    private List<Client> clientList;
    private List<TalentWishlist> talentWishlists;
    private List<TalentRequest> talentRequests;
    private List<Talent> talents;
    private List<User> users;

    @BeforeEach
    void init() {
        DummyGenerator dummyGenerator = new DummyGenerator();
        
        
        employeeStatuses = dummyGenerator.getEmployeeStatuses();
        
        positions = dummyGenerator.getPositions();
        skillsets = dummyGenerator.getSkillsets();
        talentLevels = dummyGenerator.getTalentLevels();
        talentStatuses = dummyGenerator.getTalentStatuses();
        talentRequestStatuses = dummyGenerator.getTalentRequestStatuses();
        
        clientList = dummyGenerator.getClientList();
        talents = dummyGenerator.getTalents();
        talentWishlists = dummyGenerator.getTalentWishlists();
        talentRequests = dummyGenerator.getTalentRequests();
        users = dummyGenerator.getUsers();
    }

    @Test
    public void getAllTalentApprovals_ShouldReturnTalentApprovalList_WhenFilterIsNull() {
        // talentWishlists.forEach(item -> {
        //     Talent talent = item.getTalent();
        //     System.out.println(talent.getTalentName());
        // });
        Pageable pageable = PageRequest.of(0, 8); 
        List<TalentRequest> subList = talentRequests.subList(
            0, talentRequests.size() <= pageable.getPageSize() 
                    ? talentRequests.size()-1 : pageable.getPageSize()
        );
        Page<TalentRequest> talentRequestPage = new PageImpl<>(subList, pageable, talentRequests.size());
        when(talentRequestRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(talentRequestPage);
        talentApprovalService.getAllTalentApprovals(PageRequest.of(0, 8), null);
        ResponseEntity<MessageResponse> response = talentApprovalService.getAllTalentApprovals(PageRequest.of(0, 8), null);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        MessageResponse success = response.getBody();
        assertNotNull(success);
        assertEquals(HttpStatus.OK.value(), success.getStatusCode());
        List<TalentRequestDto> talentRequestDtos = (List<TalentRequestDto>) success.getData();
        assertNotNull(talentRequestDtos);

    }   

    @Test
    public void approvedTalentRequest_ShouldReturnOk_WhenApproved() {
        TalentRequest talentToApprove = talentRequests.get(0);
        when(talentRequestRepository.findById(talentToApprove.getTalentRequestId())).thenReturn(Optional.of(talentToApprove));
        
        
        TalentRequestStatus newRequestStatus = talentRequestStatuses.stream().filter(item -> item.getTalentRequestStatusName().equalsIgnoreCase("approve")).findFirst().get();
        
        TalentRequest talentApproved = TalentRequest.builder()
                                        .talentRequestId(talentToApprove.getTalentRequestId())
                                        .talentRequestStatus(newRequestStatus)
                                        .talentWishlist(talentToApprove.getTalentWishlist())
                                        .requestDate(talentToApprove.getRequestDate())
                                        .requestRejectReason(talentToApprove.getRequestRejectReason())
                                        .build();

        TalentApprovalRequest request = new TalentApprovalRequest(talentToApprove.getTalentRequestId(), "approve", "");

        when(talentRequestStatusRepository.findByTalentRequestStatusNameAndCreatedByAllIgnoreCase(request.getAction(), "admin")).thenReturn(Optional.of(newRequestStatus));

        when(talentRequestRepository.save(talentToApprove)).thenReturn(talentApproved);
        ResponseEntity<MessageResponse> response = talentApprovalService.approveTalentRequest(request);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        MessageResponse success = response.getBody();
        assertNotNull(success);
        assertEquals("Talent request is " + newRequestStatus.getTalentRequestStatusName(), success.getMessage());
    }

    @Test
    public void approvedTalentRequest_ShouldReturnOk_WhenRejected() {
        TalentRequest talentToApprove = talentRequests.get(0);
        when(talentRequestRepository.findById(talentToApprove.getTalentRequestId())).thenReturn(Optional.of(talentToApprove));
        
        
        TalentRequestStatus newRequestStatus = talentRequestStatuses.stream().filter(item -> item.getTalentRequestStatusName().equalsIgnoreCase("rejected")).findFirst().get();
        
        TalentApprovalRequest request = new TalentApprovalRequest(talentToApprove.getTalentRequestId(), "rejected", "Does not meet the criteria");

        TalentRequest talentApproved = TalentRequest.builder()
                                        .talentRequestId(talentToApprove.getTalentRequestId())
                                        .talentRequestStatus(newRequestStatus)
                                        .talentWishlist(talentToApprove.getTalentWishlist())
                                        .requestDate(talentToApprove.getRequestDate())
                                        .requestRejectReason(request.getRejectReason())
                                        .build();

        when(talentRequestStatusRepository.findByTalentRequestStatusNameAndCreatedByAllIgnoreCase(request.getAction(), "admin")).thenReturn(Optional.of(newRequestStatus));

        when(talentRequestRepository.save(talentToApprove)).thenReturn(talentApproved);
        ResponseEntity<MessageResponse> response = talentApprovalService.approveTalentRequest(request);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        MessageResponse success = response.getBody();
        assertNotNull(success);
        assertEquals("Talent request is " + newRequestStatus.getTalentRequestStatusName(), success.getMessage());
    }
}

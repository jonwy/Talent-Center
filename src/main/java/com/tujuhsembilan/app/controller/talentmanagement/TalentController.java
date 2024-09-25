package com.tujuhsembilan.app.controller.talentmanagement;

import java.io.IOException;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tujuhsembilan.app.dto.request.SaveTalentRequest;
import com.tujuhsembilan.app.dto.request.TalentApprovalRequest;
import com.tujuhsembilan.app.dto.request.TalentApprovalsFilterRequest;
import com.tujuhsembilan.app.dto.request.TalentsFilterRequest;
import com.tujuhsembilan.app.dto.response.MessageResponse;
import com.tujuhsembilan.app.service.mastermanagement.AdminService;
import com.tujuhsembilan.app.service.talentmanagement.TalentApprovalService;
import com.tujuhsembilan.app.service.talentmanagement.TalentService;

@RestController
@RequestMapping(path = "/api/talent-management")
public class TalentController {
    
    private final TalentService talentService;
    private final AdminService adminService;
    private final TalentApprovalService talentApprovalService;

    public TalentController(
            final AdminService adminService, 
            final TalentApprovalService talentApprovalService,
            final TalentService talentService
    ) {
        this.adminService = adminService;
        this.talentApprovalService = talentApprovalService;
        this.talentService = talentService;
    }
    
    @GetMapping("/talents")
    public ResponseEntity<MessageResponse> getTalents(
        @PageableDefault(
            page = 0, 
            size = 8,
            sort = { "experience", "talentLevel.talentLevelName" }, 
            direction = Sort.Direction.DESC) Pageable pageable,
        @ModelAttribute TalentsFilterRequest filterRequest
            ) {
        return talentService.getTalents(pageable, filterRequest);
    }

    @GetMapping("/talents/{talentId}")
    public ResponseEntity<MessageResponse> getTalentDetail(@PathVariable String talentId) {
        return talentService.getTalent(talentId);
    }
    
    @PostMapping(path = "/talents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponse> saveTalent(@RequestParam MultipartFile[] files, @RequestParam(name = "requestFile") MultipartFile requestFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        SaveTalentRequest request = objectMapper.readValue(requestFile.getInputStream(), SaveTalentRequest.class);
        return adminService.saveTalent(files, request);
    }

    @PutMapping(path = "/talents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponse> updateDataTalent(@RequestParam(name = "files", required = false) MultipartFile[] files, @RequestParam(name = "requestFile") MultipartFile requestFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        SaveTalentRequest request = objectMapper.readValue(requestFile.getInputStream(), SaveTalentRequest.class);
        return adminService.updateDataTalent(files, request);
    }

    @GetMapping("/talent-approvals")
    public ResponseEntity<MessageResponse> getTalentAprovalList(
                @PageableDefault(
                    page = 0, 
                    size = 8,
                    sort = {"talentWishlist.client.agencyName"}) Pageable pageable, 
                @ModelAttribute(name = "filterRequest") TalentApprovalsFilterRequest filterReqeRequest) {
        return talentApprovalService.getAllTalentApprovals(pageable, filterReqeRequest);
    }

    @PutMapping("/talent-approvals")
    public ResponseEntity<MessageResponse> approveTalentRequest(@RequestBody TalentApprovalRequest request) {
        return talentApprovalService.approveTalentRequest(request);
    }

    @PostMapping(path = "/bulk-insert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponse> bulkInserts( @RequestParam("files") MultipartFile[] files, @RequestParam(name = "requestFile") MultipartFile requestFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        SaveTalentRequest request = objectMapper.readValue(requestFile.getInputStream(), SaveTalentRequest.class);
        return adminService.saveTalent2(files, request);
    }
    
}

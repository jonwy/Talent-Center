package com.tujuhsembilan.app.controller.mastermanagement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tujuhsembilan.app.dto.response.ApiResponse;
import com.tujuhsembilan.app.service.mastermanagement.MasterService;

@RestController
@RequestMapping("/api/master-management")
public class MasterController {
    
    private final MasterService masterService;

    public MasterController(final MasterService masterService) {
        this.masterService = masterService;
    }

    @GetMapping("/talent-level-option-lists")
    public ResponseEntity<ApiResponse> getLevelOptionLists() {
        return masterService.getLevelOptionLists();
    }

    @GetMapping("/talent-status-option-lists")
    public ResponseEntity<ApiResponse> getTalentStatusOptionLists() {   
        return masterService.getTalentStatusOptionLists();
    }

    @GetMapping("/employee-status-option-lists")
    public ResponseEntity<ApiResponse> getEmployeeStatusOptionLists() {
        return masterService.getEmployeeStatusOptionLists();
    }

    @GetMapping("/skill-set-option-lists")
    public ResponseEntity<ApiResponse> getSkillSetOptionLists() {
        return masterService.getSkillsetOptionLists();
    }

    @GetMapping("/talent-position-option-lists")
    public ResponseEntity<ApiResponse> getTalentPositionOptionLists() {
        return masterService.getTalentPositionOptionLists();
    }

    @GetMapping("/talent-request-status-option-lists")
    public ResponseEntity<ApiResponse> getTalentRequestStatusOptionLists() {
        return masterService.getTalentRequestStatusOptionLists();
    }
}

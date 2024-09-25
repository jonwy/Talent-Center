package com.tujuhsembilan.app.controller.mastermanagement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tujuhsembilan.app.dto.response.MessageResponse;
import com.tujuhsembilan.app.service.mastermanagement.MasterService;

@RestController
@RequestMapping("/api/master-management")
public class MasterController {
    
    private final MasterService masterService;

    public MasterController(final MasterService masterService) {
        this.masterService = masterService;
    }

    @GetMapping("/talent-level-option-list")
    public ResponseEntity<MessageResponse> getLevelOptionLists() {
        return masterService.getLevelOptionLists();
    }

    @GetMapping("/talent-status-option-list")
    public ResponseEntity<MessageResponse> getTalentStatusOptionLists() {   
        return masterService.getTalentStatusOptionLists();
    }

    @GetMapping("/employee-status-option-list")
    public ResponseEntity<MessageResponse> getEmployeeStatusOptionLists() {
        return masterService.getEmployeeStatusOptionLists();
    }

    @GetMapping("/skillset-option-list")
    public ResponseEntity<MessageResponse> getSkillSetOptionLists() {
        return masterService.getSkillsetOptionLists();
    }

    @GetMapping("/talent-position-option-list")
    public ResponseEntity<MessageResponse> getTalentPositionOptionLists() {
        return masterService.getTalentPositionOptionLists();
    }

    @GetMapping("/talent-request-status-option-list")
    public ResponseEntity<MessageResponse> getTalentRequestStatusOptionLists() {
        return masterService.getTalentRequestStatusOptionLists();
    }
}

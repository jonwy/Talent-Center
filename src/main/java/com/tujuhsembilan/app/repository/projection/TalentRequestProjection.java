package com.tujuhsembilan.app.repository.projection;

import java.util.Date;
import java.util.UUID;

public interface TalentRequestProjection {

    UUID getTalentRequestId();
    Date getRequestDate();
    String getAgencyName();
    String getTalentName();
    String getApprovalStatus();
}

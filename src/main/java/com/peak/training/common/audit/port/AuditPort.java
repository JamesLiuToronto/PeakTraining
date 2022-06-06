package com.peak.training.common.audit.port;

import com.peak.training.common.audit.domain.AuditLog;
import com.peak.training.common.audit.dto.AuditLogDTO;

public interface AuditPort {

    public AuditLogDTO getAuditLogById(int id);
    public AuditLog persistAuditLog(Integer auditId, int userId) ;


}

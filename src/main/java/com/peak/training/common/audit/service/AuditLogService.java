package com.peak.training.common.audit.service;


import com.peak.training.common.audit.domain.AuditLog;
import com.peak.training.common.audit.dto.AuditLogDTO;
import com.peak.training.common.audit.dto.AuditLogDTOMapper;
import com.peak.training.common.audit.port.AuditPort;
import com.peak.training.common.audit.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
//@ComponentScan({"com.peak.training.common.audit.repository"})
public class AuditLogService implements AuditPort {
    @Autowired
    AuditLogRepository repository ;

    public AuditLogDTO getAuditLogById(int id){
        return AuditLogDTOMapper.INSTANCE.entityToDTO(repository.getById(id));
    }

    public AuditLog persistAuditLog(int auditId, int userId){

        AuditLog entity = null ;
        if (auditId == 0) {
            entity = getNewAuditLog(userId) ;
        }
        else {
            entity = getUpdateAuditLog(auditId, userId) ;
        }
        repository.save(entity) ;
        return entity ;
    }

    private AuditLog getNewAuditLog(int userId){
        return  AuditLog.builder()
                .createDate(LocalDateTime.now())
                .createUserId(userId)
                .updateDate(LocalDateTime.now())
                .updateUserId(userId)
                .build();
    }

    private AuditLog getUpdateAuditLog(int auditId, int userId){
        AuditLog entity = repository.getById(auditId);
        entity.setUpdateDate(LocalDateTime.now());
        entity.setUpdateUserId(userId);
        return entity ;
    }


}

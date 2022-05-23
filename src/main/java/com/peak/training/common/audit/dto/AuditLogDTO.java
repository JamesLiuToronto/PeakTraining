package com.peak.training.common.audit.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor

public class AuditLogDTO implements java.io.Serializable{
    int auditID ;
    int createUserId ;
    int updateUserId ;
    LocalDateTime createDate ;
    LocalDateTime updateDate ;
}

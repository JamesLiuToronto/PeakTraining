package com.peak.training.common.audit.dto;

import com.peak.training.common.audit.domain.AuditLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuditLogDTOMapper {
    AuditLogDTOMapper INSTANCE = Mappers.getMapper(AuditLogDTOMapper.class);

    AuditLogDTO entityToDTO(final AuditLog entity);
    AuditLog dtoToEntity(AuditLogDTO dto);

}

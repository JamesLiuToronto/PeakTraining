package com.peak.training.transactionlog.dto;

import com.peak.training.transactionlog.domain.TransactionLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionLogDTOMapper {
    TransactionLogDTOMapper INSTANCE = Mappers.getMapper(TransactionLogDTOMapper.class);

    TransactionLogDTO entityToDTO(final TransactionLog entity);
    TransactionLog dtoToEntity(TransactionLogDTO dto);

}

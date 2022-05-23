package com.peak.training.transactionlog.service;

import com.peak.training.transactionlog.domain.TransactionLog;
import com.peak.training.transactionlog.dto.TransactionLogDTO;
import com.peak.training.transactionlog.dto.TransactionLogDTOMapper;
import com.peak.training.transactionlog.repository.TransactionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@ComponentScan({"com.peak.training.common.audit"})
public class TransactionLogService {
    @Autowired
    TransactionLogRepository repository ;


    public List<TransactionLogDTO> getTransactionlogsByUuid(String uuid){
        List<TransactionLog> list = repository.findByUUID(uuid) ;
        if ((list == null)||(list.size() ==0)) return null ;
        List<TransactionLogDTO> retList = new ArrayList<TransactionLogDTO>();
        list.stream().forEach(x-> retList.add(getTransactionLogDTOFromEntity(x)));
        return retList ;
    }

    private TransactionLogDTO getTransactionLogDTOFromEntity(TransactionLog entity){
        return TransactionLogDTOMapper.INSTANCE.entityToDTO(entity) ;

    }

    public void persistTransactionLog(String uuid, String typeCode, String message,
                                      String statusCode, int userId){
        TransactionLog entity = TransactionLog.builder()
                .userId(userId)
                .message(message)
                .statusCode(statusCode)
                .uuid(uuid)
                .transactionTypeCode(typeCode)
                .utimestamp(LocalDateTime.now())
                .build();

        repository.save(entity) ;
    }

}


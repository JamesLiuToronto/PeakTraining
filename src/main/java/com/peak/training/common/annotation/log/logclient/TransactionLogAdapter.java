package com.peak.training.common.annotation.log.logclient;

import com.peak.training.transactionlog.service.TransactionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class TransactionLogAdapter implements TransactionLogPort{

    @Autowired
    TransactionLogService logService ;

    @Override
    public String persistTransactionLog(String uuid, String typeCode, String message, String statusCode, int userId) {
        logService.persistTransactionLog(uuid,typeCode,message,statusCode,userId);
        return "1" ;
    }
}

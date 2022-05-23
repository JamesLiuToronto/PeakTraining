package com.peak.training.common.transactionlog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TransactionLogAdapter {

    @Value("${transactionlog.enable}")
    private String logEnable;

    @Autowired
    TransactionLogClient logClient ;

    public void persistTransactionLog(String uuid,
                                        String typeCode,
                                        String message,
                                        String statusCode,
                                        int updateUserId) {
        boolean enable = Boolean.parseBoolean(logEnable);
        if (!enable) return ;
        logClient.persistTransactionLog(uuid, typeCode, message, statusCode, updateUserId) ;
    }
}

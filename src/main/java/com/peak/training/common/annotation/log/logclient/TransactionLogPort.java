package com.peak.training.common.annotation.log.logclient;

public interface TransactionLogPort {

    public String persistTransactionLog(String uuid, String typeCode, String message,
                                      String statusCode, int userId);
}

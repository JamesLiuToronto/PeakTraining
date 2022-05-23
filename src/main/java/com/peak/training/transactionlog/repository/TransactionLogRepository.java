package com.peak.training.transactionlog.repository;

import com.peak.training.transactionlog.domain.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog,Integer> {

    List<TransactionLog> findByUUID(String uuid) ;
}

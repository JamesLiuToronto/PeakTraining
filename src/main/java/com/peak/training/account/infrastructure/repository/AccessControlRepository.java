package com.peak.training.account.infrastructure.repository;

import com.peak.training.account.infrastructure.entity.AccessControlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessControlRepository extends JpaRepository<AccessControlEntity,Integer> {

    List<AccessControlEntity> findMethodCodeByGroupCode(String groupCode) ;
}

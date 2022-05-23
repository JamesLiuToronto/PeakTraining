package com.peak.training.account.infrastructure.repository;

import com.peak.training.account.infrastructure.entity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAccountEntityRepository extends JpaRepository<UserAccountEntity,Integer> {

    List<UserAccountEntity> findByemailAddress(String emailAddress);
    List<UserAccountEntity> findByUuid(String uuid);

}
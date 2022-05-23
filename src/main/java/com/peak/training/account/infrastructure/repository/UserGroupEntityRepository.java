package com.peak.training.account.infrastructure.repository;

import com.peak.training.account.infrastructure.entity.UserGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupEntityRepository extends JpaRepository<UserGroupEntity,Integer> {



}
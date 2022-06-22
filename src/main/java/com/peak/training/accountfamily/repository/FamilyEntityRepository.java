package com.peak.training.accountfamily.repository;

import com.peak.training.accountfamily.domain.FamilyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyEntityRepository extends JpaRepository<FamilyEntity,Integer> {


}
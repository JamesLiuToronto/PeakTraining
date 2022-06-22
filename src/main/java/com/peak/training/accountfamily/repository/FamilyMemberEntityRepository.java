package com.peak.training.accountfamily.repository;

import com.peak.training.accountfamily.domain.FamilyMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyMemberEntityRepository extends JpaRepository<FamilyMemberEntity,Integer> {

    List<FamilyMemberEntity> findByFamilyId(int userId);
}
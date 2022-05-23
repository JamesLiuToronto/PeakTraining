package com.peak.training.account.infrastructure.repository;

import com.peak.training.account.infrastructure.entity.UserLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserLoginEntityRepository extends JpaRepository<UserLoginEntity,Integer> {


    //List<UserLoginEntity> findByemailAddress(String emailAddress);

}
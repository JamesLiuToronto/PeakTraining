package com.peak.training.account.infrastructure.adapter;

import com.peak.training.account.domain.model.LoginSourceType;
import com.peak.training.account.domain.model.UserLogin;
import com.peak.training.account.infrastructure.entity.UserLoginEntity;
import com.peak.training.account.infrastructure.mapper.UserLoginMapper;
import com.peak.training.account.infrastructure.repository.UserLoginEntityRepository;
import com.peak.training.common.audit.domain.AuditLog;
import com.peak.training.common.audit.service.AuditLogService;
import com.peak.training.common.exception.AppMessageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class UserLoginAdapter {

    @Autowired
    UserLoginEntityRepository userLoginEntityRepository;

    @Autowired
    AuditLogService auditLogService ;


    public UserLogin getUserLoginById(int userId){
        UserLoginEntity entity = userLoginEntityRepository.getById(userId);
        if (entity== null)
            throw new AppMessageException("ID_NOT_FOUND") ;
        UserLogin model = UserLoginMapper.INSTANCE.entityToModel(entity);
        return model ;
    }

    public void persistNewLogin(int userId, String password, LoginSourceType sourceType, int updateUserId){
        AuditLog auditLog = auditLogService.persistAuditLog(0, updateUserId) ;
        UserLoginEntity entity = UserLoginEntity.builder()
                .userId(userId)
                .authenticationSource(sourceType.name())
                .utimestamp(LocalDateTime.now())
                .password(password)
                .auditId(auditLog.getAuditID())
                .build();
        userLoginEntityRepository.save(entity) ;
    }

    public void persistChangePassword(int userId, String password, int updateUserId){
        UserLoginEntity entity = userLoginEntityRepository.getById(userId);
        AuditLog auditLog = auditLogService.persistAuditLog(entity.getAuditId(), updateUserId) ;
        entity.setPassword(password);
        entity.setUtimestamp(LocalDateTime.now());
        userLoginEntityRepository.save(entity) ;
    }

    public void persistChangeSourceLogin(int userId, LoginSourceType sourceType, int updateUserId){
        UserLoginEntity entity = userLoginEntityRepository.getById(userId);
        AuditLog auditLog = auditLogService.persistAuditLog(entity.getAuditId(), updateUserId) ;
        entity.setAuthenticationSource(sourceType.name());
        entity.setUtimestamp(LocalDateTime.now());
        userLoginEntityRepository.save(entity) ;
    }






}

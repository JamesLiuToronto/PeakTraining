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
import java.util.Optional;


@Component
public class UserLoginAdapter {

    @Autowired
    UserLoginEntityRepository userLoginEntityRepository;


    public UserLogin getUserLoginById(int userId){
        Optional<UserLoginEntity> optional = userLoginEntityRepository.findById(userId) ;
        if (!optional.isPresent())
            throw new AppMessageException("ID_NOT_FOUND") ;
        UserLogin model = UserLoginMapper.INSTANCE.entityToModel(optional.get());
        return model ;
    }

    public UserLogin persistUserLogin(UserLogin model, int updateUserId){
        UserLoginEntity entity = UserLoginMapper.INSTANCE.modelToEntity(model);
        entity.setAudit(updateUserId);
        userLoginEntityRepository.saveAndFlush(entity);
        return UserLoginMapper.INSTANCE.entityToModel(entity) ;
    }


    /*
    public void persistChangePassword(UserLogin login, int updateUserId){
        UserLoginEntity entity = userLoginEntityRepository.getById(login.getUserId());
        AuditLog auditLog = auditLogService.persistAuditLog(entity.getAuditId(), updateUserId) ;
        entity.setPassword(login.getPassword());
        entity.setUtimestamp(LocalDateTime.now());
        userLoginEntityRepository.save(entity) ;
    }

    public void persistChangeSourceLogin(UserLogin login, int updateUserId){
        UserLoginEntity entity = userLoginEntityRepository.getById(login.getUserId());
        AuditLog auditLog = auditLogService.persistAuditLog(entity.getAuditId(), updateUserId) ;
        entity.setAuthenticationSource(login.getAuthenticationSource());
        entity.setUtimestamp(LocalDateTime.now());
        userLoginEntityRepository.save(entity) ;
    }


*/



}

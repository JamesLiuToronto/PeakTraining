package com.peak.training.account.infrastructure.adapter;

import com.peak.training.account.domain.model.*;
import com.peak.training.account.infrastructure.entity.AccessControlEntity;
import com.peak.training.account.infrastructure.entity.UserAccountEntity;
import com.peak.training.account.infrastructure.entity.UserGroupEntity;
import com.peak.training.account.infrastructure.mapper.AccountMapper;
import com.peak.training.account.infrastructure.mapper.UserGroupMapper;
import com.peak.training.account.infrastructure.repository.AccessControlRepository;
import com.peak.training.account.infrastructure.repository.UserAccountEntityRepository;
import com.peak.training.account.infrastructure.repository.UserGroupEntityRepository;
import com.peak.training.common.audit.domain.AuditLog;
import com.peak.training.common.audit.service.AuditLogService;
import com.peak.training.common.exception.AppMessageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AccountAdapter {
    @Autowired
    UserAccountEntityRepository userAccountEntityRepository;

    @Autowired
    UserGroupEntityRepository userGroupEntityRepository;

    @Autowired
    AccessControlRepository accessControlRepository ;

    @Autowired
    AuditLogService auditLogService ;


    public Account getAccountById(int id){
        Optional<UserAccountEntity> entity = userAccountEntityRepository.findById(id) ;
        if (!entity.isPresent())
            throw new AppMessageException("ID_NOT_FOUND") ;
        Account account = AccountMapper.entityToModel(entity.get());
        account.setMethodACLs(getAclList(entity.get()));
        return account ;
    }

    public Account getAccountByEmailAddress(String emailAddress){
        List<UserAccountEntity> entities = userAccountEntityRepository.findByemailAddress(emailAddress);
        if ((entities == null)||(entities.size()==0))
            throw new AppMessageException("userAccount.validation.invalid.emailAddress") ;
        Account account = AccountMapper.entityToModel(entities.get(0));
        account.setMethodACLs(getAclList(entities.get(0)));
        return account ;
    }

    public Account persistAccount(Account account, int updateUserId){
        AuditLog auditLog = auditLogService.persistAuditLog(account.getAuditId(), updateUserId) ;
        UserAccountEntity entity = AccountMapper.INSTANCE.modelToEntity(account,
                account.getPersonInfo(),
                account.getEmailAddress().toString(),
                String.valueOf(account.getPersonInfo().getGenderChar()));
        entity.setAuditId(auditLog.getAuditID());
        userAccountEntityRepository.save(entity);
        return AccountMapper.entityToModel(entity);
    }

    public void persistNewUsergroup(int userAccountId, UserGroup model, int updateUserId){
        UserAccountEntity account = userAccountEntityRepository.findById(userAccountId).get();
        UserGroupEntity entity = UserGroupMapper.INSTANCE.modelToEntity(model) ;
        entity.setUserAccountEntity(account);
        AuditLog auditLog = auditLogService.persistAuditLog(entity.getAuditId(), updateUserId) ;
        entity.setAuditId(auditLog.getAuditID());
        userGroupEntityRepository.save(entity) ;
    }

    public void persistUsergroup(UserGroup model, int updateUserId){
        UserGroupEntity entity = UserGroupMapper.INSTANCE.modelToEntity(model) ;
        AuditLog auditLog = auditLogService.persistAuditLog(entity.getAuditId(), updateUserId) ;
        entity.setAuditId(auditLog.getAuditID());
        userGroupEntityRepository.save(entity) ;
    }

    public UserGroup getUserGroupById(int id){
        UserGroupEntity entity = userGroupEntityRepository.findById(id).get();
        return UserGroupMapper.entityToModel(entity) ;
    }

    public Set<MethodACL> getAclList(int userAccountId ){
        UserAccountEntity account = userAccountEntityRepository.findById(userAccountId).get();
        return getAclList(account) ;
    }

    private Set<MethodACL> getAclList(UserAccountEntity account) {
        List<UserGroupEntity> ulist = account.getUserGroups();
        if (ulist == null) return null;
        Set<MethodACL> rlist = new HashSet<MethodACL>();
        ulist.stream().forEach(x -> setMethodCodeList(x.getGroupCode(), rlist));
        return rlist;
    }

    private void  setMethodCodeList(String userGroupCode, Set<MethodACL> rlist){
        List<AccessControlEntity> list = accessControlRepository.findMethodCodeByGroupCode(userGroupCode)
                .stream()
                .collect(Collectors.toList());
        if (list == null) return ;
        list.stream().filter(x-> rlist.add(MethodACL.builder()
                        .methodcode(x.getMethodCode())
                        .allowed(x.isAllow()).build()));
    }



}

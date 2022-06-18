package com.peak.training.account.infrastructure.adapter;

import com.peak.training.account.domain.model.*;
import com.peak.training.account.infrastructure.entity.UserAccountEntity;
import com.peak.training.account.infrastructure.entity.UserGroupEntity;
import com.peak.training.account.infrastructure.mapper.AccountMapper;
import com.peak.training.account.infrastructure.mapper.UserGroupMapper;
import com.peak.training.account.infrastructure.repository.AccessControlRepository;
import com.peak.training.account.infrastructure.repository.UserAccountEntityRepository;
import com.peak.training.account.infrastructure.repository.UserGroupEntityRepository;
import com.peak.training.common.audit.domain.AuditLog;
import com.peak.training.common.exception.AppMessageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AccountAdapter {
    @Autowired
    UserAccountEntityRepository userAccountEntityRepository;

    @Autowired
    UserGroupEntityRepository userGroupEntityRepository;

    @Autowired
    AccessControlRepository accessControlRepository ;

    public Account getAccountById(int id){
        Optional<UserAccountEntity> entity = userAccountEntityRepository.findById(id) ;
        if (!entity.isPresent())
            throw new AppMessageException("ID_NOT_FOUND") ;
        Account account = AccountMapper.entityToModel(entity.get());
        return account ;
    }

    public Account getAccountByEmailAddress(String emailAddress){
        List<UserAccountEntity> entities = userAccountEntityRepository.findByemailAddress(emailAddress);
        if ((entities == null)||(entities.size()==0))
            throw new AppMessageException("userAccount.validation.invalid.emailAddress") ;
        Account account = AccountMapper.entityToModel(entities.get(0));
        return account ;
    }

    public Account persistAccount(Account account, int updateUserId){
        UserAccountEntity entity = AccountMapper.INSTANCE.modelToEntity(account,
                account.getPersonInfo(),
                account.getEmailAddress().toString(),
                String.valueOf(account.getPersonInfo().getGenderChar()));
        entity.setAudit(updateUserId);
        for (UserGroupEntity rec: entity.getUserGroups()){
            rec.setUserAccountEntity(entity);
        }
        userAccountEntityRepository.saveAndFlush(entity);
        return AccountMapper.entityToModel(entity);
    }

    public void persistNewUsergroup(int userAccountId, UserGroup model, int updateUserId){
        UserAccountEntity account = userAccountEntityRepository.findById(userAccountId).get();
        UserGroupEntity entity = UserGroupMapper.INSTANCE.modelToEntity(model) ;
        entity.setUserAccountEntity(account);
        entity.setAudit(updateUserId);
        //userGroupEntityRepository.saveAndFlush(entity) ;
        account.getUserGroups().add(entity) ;
        userAccountEntityRepository.saveAndFlush(account) ;
    }

    public void persistUsergroup(UserGroup model, int updateUserId){
        UserGroupEntity entity = UserGroupMapper.INSTANCE.modelToEntity(model) ;
        entity.setAudit(updateUserId);
        userGroupEntityRepository.saveAndFlush(entity) ;
    }

    public UserGroup getUserGroupById(int id){
        UserGroupEntity entity = userGroupEntityRepository.findById(id).get();
        return UserGroupMapper.entityToModel(entity) ;
    }

    /*
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
*/


}

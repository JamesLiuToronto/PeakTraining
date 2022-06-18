package com.peak.training.account.service;

import com.peak.training.account.domain.model.*;
import com.peak.training.account.infrastructure.adapter.AccountAdapter;
import com.peak.training.account.port.AccountPort;
import com.peak.training.account.port.LoginPort;
import com.peak.training.common.exception.AppMessageException;
import com.peak.training.common.domain.valueobject.EmailAddress;
import lombok.extern.slf4j.Slf4j;
import com.peak.training.common.annotation.log.LogMethodData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;


@Slf4j
@Service
@Transactional
public class AccountService implements AccountPort {
    @Autowired
    AccountAdapter accountAdapter;


    @Autowired
    LoginPort loginPort;

    public Account getAccountById(int id){
        return accountAdapter.getAccountById(id) ;
    }

    @LogMethodData
    public Account newAccount(String emailAddress, String password, String loginSourceType, String firstName, String lastName,
                              LocalDate birthDate, String gender, int updateUserId, List<GroupType> groupTypeList){

        Account request = new Account();
        request.setNewAccount(emailAddress, firstName, lastName, birthDate, gender, groupTypeList) ;
        Account account = accountAdapter.persistAccount(request, updateUserId);
        loginPort.registerNewLogin(account.getUserId(), password,
                LoginSourceType.valueOf(loginSourceType), updateUserId);
       /* groupTypeList.stream().forEach(x->{
            AssignUserGroup(account.getUserId(), x, updateUserId);
        });*/
        return accountAdapter.getAccountById(account.getUserId()) ;

    }

    private Account registerNewUserGroup(int userId, GroupType type, int updateUserId) {
        adminValidator(updateUserId);

        Account account = accountAdapter.getAccountById(userId) ;
        UserGroup model = account.assignUserGroup(type) ;
        accountAdapter.persistNewUsergroup(userId, model, updateUserId);

        return accountAdapter.getAccountById(userId) ;
    }

    @LogMethodData
    @Override
    public Account changePersonInfo(int id, Person person, int updateUserId) {
        adminOrSelfValidator(id, updateUserId);

        Account account = accountAdapter.getAccountById(id) ;
        account.changePersonInfo(person);
        accountAdapter.persistAccount(account, updateUserId) ;
        return accountAdapter.getAccountById(id) ;
    }

    @LogMethodData
    @Override
    public Account changeEmailAddress(int id, EmailAddress emailAddress, int updateUserId) {
        adminOrSelfValidator(id, updateUserId);

        Account account = accountAdapter.getAccountById(id) ;
        account.changeEmailAddress(emailAddress);
        accountAdapter.persistAccount(account, updateUserId) ;

        return accountAdapter.getAccountById(id) ;

    }

    @LogMethodData
    @Override
    public Account activateAccount(int userId, int updateUserId) {
        adminOrSelfValidator(userId,updateUserId);

        Account account = accountAdapter.getAccountById(userId) ;
        account.activateAccount();
        accountAdapter.persistAccount(account, updateUserId) ;
        return accountAdapter.getAccountById(userId) ;
    }

    @LogMethodData
    @Override
    public Account disableAccount(int id, int updateUserId) {
        adminValidator(updateUserId);

        Account account = accountAdapter.getAccountById(id) ;
        account.disAbleAccount();
        accountAdapter.persistAccount(account, updateUserId) ;
        return accountAdapter.getAccountById(id) ;
    }

    @LogMethodData
    @Override
    public Account AssignUserGroup(int userId, GroupType type, int updateUserId) {
        adminValidator(updateUserId);

        Account account = accountAdapter.getAccountById(userId) ;
        UserGroup model = account.assignUserGroup(type) ;
        accountAdapter.persistNewUsergroup(userId, model, updateUserId);

        return accountAdapter.getAccountById(userId) ;
    }


    @LogMethodData
    @Override
    public Account disableUserGroup(int id, int updateUserId){

        adminValidator(updateUserId);

        UserGroup model = accountAdapter.getUserGroupById(id) ;
        model.disableGroup();
        accountAdapter.persistUsergroup(model, updateUserId);
        return accountAdapter.getAccountById(id) ;
    }


    private String getMessage(int id, String message){
        return "id=" + Integer.toString(id) + ", " + message ;
    }

    private void adminOrSelfValidator(int userId, int updateUserId){
        if (userId == updateUserId)
            return ;
        if (isAdminRole(updateUserId))
            return ;
        throw new AppMessageException("userAccount.validation.admin.role.or.self.error") ;
    }

    private void adminValidator(int updateUserId){
        if (isAdminRole(updateUserId))
            return ;
        throw new AppMessageException("userAccount.validation.admin.role.error") ;
    }

    static int SYSTEM_ID = -1;
    private boolean isAdminRole(int userId){
        if (userId == -1) return true;
        Account account = getAccountById(userId) ;
        if (account.getRoleList().contains(GroupType.ADMIN.name()))
            return true ;
        return false ;
    }

}

package com.peak.training.account.port;

import com.peak.training.account.model.Account;
import com.peak.training.account.model.GroupType;
import com.peak.training.account.model.Person;
import com.peak.training.common.domain.valueobject.EmailAddress;

import java.time.LocalDate;
import java.util.List;

public interface AccountPort {
    public Account getAccountById(int id) ;
    public Account newAccount(String emailAddress, String password, String loginSourceType,
                              String firstName, String lastName,
                              LocalDate birthDate, String gender, int updateUserId,
                              List<GroupType> groupTypeList);
    public Account changePersonInfo (int userId, Person person, int updateUserId) ;
    public Account changeEmailAddress(int userId, EmailAddress emailAddress, int updateUserId) ;
    public Account activateAccount(int userId, int updateUserId);
    public Account disableAccount(int userId, int updateUserId) ;
    public Account AssignUserGroup(int userGroupId, GroupType type, int updateUserId);
    public Account disableUserGroup(int userGroupId, int updateUserId);

}

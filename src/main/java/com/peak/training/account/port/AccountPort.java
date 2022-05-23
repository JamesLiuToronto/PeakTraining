package com.peak.training.account.port;

import com.peak.training.account.domain.model.*;
import com.peak.training.common.domain.valueobject.EmailAddress;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface AccountPort {
    public Account getAccountById(int id) ;
    public Account newAccount(String emailAddress, String password, String loginSourceType,
                              String firstName, String lastName,
                              LocalDate birthDate, String gender, int updateUserId,
                              List<GroupType> groupTypeList);
    public void changePersonInfo (int id, Person person, int updateUserId) ;
    public void changeEmailAddress(int id, EmailAddress emailAddress, int updateUserId) ;
    public void activateAccount(int id, int updateUserId);
    public void disableAccount(int id, int updateUserId) ;
    public void AssignUserGroup(int userGroupId, GroupType type, int updateUserId);
    public void disableUserGroup(int id, int updateUserId);
    public Set<MethodACL> getAclList(int userAccountId ) ;

}

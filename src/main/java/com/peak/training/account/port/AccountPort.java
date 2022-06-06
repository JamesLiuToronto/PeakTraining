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
    public Account changePersonInfo (int id, Person person, int updateUserId) ;
    public Account changeEmailAddress(int id, EmailAddress emailAddress, int updateUserId) ;
    public Account activateAccount(int id, int updateUserId);
    public Account disableAccount(int id, int updateUserId) ;
    public Account AssignUserGroup(int userGroupId, GroupType type, int updateUserId);
    public Account disableUserGroup(int id, int updateUserId);

}

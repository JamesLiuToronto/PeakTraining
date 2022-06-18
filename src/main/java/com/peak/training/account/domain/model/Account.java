package com.peak.training.account.domain.model;

import com.peak.training.common.domain.base.BaseEntity;
import com.peak.training.common.domain.valueobject.EmailAddress;
import com.peak.training.common.enumtype.Gender;
import com.peak.training.common.exception.AppMessageException;
import com.peak.training.common.exception.AppNotNullException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
public class Account extends BaseEntity {


    int userId ;
    EmailAddress emailAddress;
    Person personInfo ;
    UserStatus userStatus ;

    List<UserGroup> userGroups = new ArrayList<UserGroup>();


    public Account(String uuid,
                   Integer userId, EmailAddress emailAddress,
                   Person personInfo, UserStatus userStatus,
                   List<UserGroup> userGroups) {
        super(uuid) ;
        this.userId = userId;
        this.emailAddress = emailAddress;
        this.personInfo = personInfo;
        this.userStatus = userStatus;
        this.userGroups = userGroups;

    }

    public void setNewAccount(String emailAddress, String firstName,
                              String lastName, LocalDate birthDate,
                              String gender, List<GroupType> groupTypeList){
        this.setUuid(UUID.randomUUID().toString());
        this.personInfo =  new Person(
                firstName, lastName, birthDate,
                Gender.TYPE.getTypeByChar(gender.charAt(0)));

        this.emailAddress = new EmailAddress(emailAddress) ;
        this.userStatus = UserStatus.PENDING ;
        groupTypeList.stream().forEach(x-> assignUserGroup(x));

    }

    public void changePersonInfo(Person person){
        this.personInfo = person ;
    }

    public void changeEmailAddress(EmailAddress emailAddress){
        this.emailAddress = emailAddress ;
    }

    public UserGroup assignUserGroup(GroupType type){
        if (isDisable())
            throw new AppMessageException("Account_DISABLED") ;
        UserGroup find = getUsergroup(type) ;
        if (find != null) {
            find.activateGroup();
            return find;
        }
        UserGroup ug = new UserGroup() ;
        ug.AssignNewUserGroup(type);
        userGroups.add(ug) ;
        return ug ;
    }

    public UserGroup disableUserGroup(UserGroup group){
        UserGroup find = getUsergroup(group.getGroupType()) ;
        if (find == null)
            throw new AppNotNullException("USERGROUP_NOTFOUND") ;
        if (!find.isActive())
            throw new AppNotNullException("USERGROUP_ISNOTACTIVE") ;
        find.disableGroup();
        return find ;
    }

    private UserGroup getUsergroup(GroupType type){
        for (UserGroup rec:userGroups){
            if (rec.getGroupType() == type)
                return rec;
        }
        return null ;
    }

    public void activateAccount(){

        this.userStatus = UserStatus.ACTIVE ;
    }

    public void disAbleAccount(){

        this.userStatus = UserStatus.DISABLE ;
    }


    private boolean isActive(){
        if (userStatus != UserStatus.ACTIVE)
            return false ;
        if ((userGroups == null)||(userGroups.size() == 0))
            return false ;
        return true ;
    }

    private boolean isDisable(){
        if (userStatus == UserStatus.DISABLE)
            return true ;
        return false ;
    }

    private boolean isPending(){
        if (userStatus == UserStatus.PENDING)
            return true ;
        return false ;
    }


    public String getRoleList(){
        return userGroups.stream()
                .filter(rec->rec.isActive())
                .map(a -> String.valueOf(a.getGroupType().toString()))
                .collect(Collectors.joining(","));
    }


}

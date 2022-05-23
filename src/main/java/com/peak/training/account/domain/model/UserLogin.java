package com.peak.training.account.domain.model;

import com.peak.training.common.domain.valueobject.EmailAddress;
import com.peak.training.common.exception.AppMessageException;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class UserLogin {

    Integer userId ;
    LocalDateTime utimestamp ;
    String password ;
    String authenticationSource ;
    int auditId ;

    @Builder
    public UserLogin(Integer userId, LocalDateTime utimestamp, String password,
                     String authenticationSource, int auditId ) {
        this.userId = userId ;
        this.utimestamp = utimestamp ;
        this.password = password ;
        this.authenticationSource = authenticationSource ;
        this.auditId = auditId ;
    }


    public void newUserLogin(Account account, String password, String authenticationSource){
        this.userId = account.getUserId() ;
        this.password = password;
        this.authenticationSource = authenticationSource ;
        this.utimestamp = LocalDateTime.now() ;
    }

    public void changePassword(String roleList, String oldPassword, String newPassword, int  updateUserId){
        if (this.getAuthenticationSource() != null)
            throw new AppMessageException("userAccount.validation.third_party.password_change") ;
        if (! this.password.equals(oldPassword))
            throw new AppMessageException("userAccount.validation.old_password_mismatch") ;
        validatePasswordByACL(roleList, updateUserId); ;
        password = newPassword ;
        this.utimestamp = LocalDateTime.now();
    }

    private void validatePasswordByACL(String roleList,  int updateUserId){
        if (updateUserId == this.userId) return ;
        if (roleList.contains(GroupType.ADMIN.name())) return ;
        throw new AppMessageException("userAccount.validation.no_password_change_right") ;

    }

    public boolean validatePassword(String password){
        if (LoginSourceType.valueOf(this.getAuthenticationSource()) != LoginSourceType.NULL)  //means login from third party
            return true;
        if ((!getPassword().isEmpty())&&(this.password.equalsIgnoreCase(password)))
           return true ;
        return false ;

    }
}

package com.peak.training.account.domain.model;

import com.peak.training.common.exception.AppMessageException;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class UserLogin {

    Integer userId ;
    String password ;
    String authenticationSource ;
    boolean locked ;
    LocalDateTime lastSuccessLogin ;
    LocalDateTime lastFaliedLogin ;
    int faliedLoginAttemp ;


    @Builder
    public UserLogin(Integer userId, String password,
                     String authenticationSource) {
        this.userId = userId ;
        this.password = password ;
        this.authenticationSource = authenticationSource ;
    }


    public UserLogin(int userId, String password, LoginSourceType sourceType){
        this.userId = userId ;
        this.password = password;
        this.authenticationSource = sourceType.name() ;
        locked = false;
        faliedLoginAttemp = 0 ;
    }

    public void changePassword(String oldPassword, String newPassword, Account updateUserAccount){
        if (this.getAuthenticationSource() != null)
            throw new AppMessageException("userAccount.validation.third_party.password_change") ;
        if (! this.password.equals(oldPassword))
            throw new AppMessageException("userAccount.validation.old_password_mismatch") ;
        validatePasswordByACL(updateUserAccount.getRoleList(), updateUserAccount); ;
        password = newPassword ;

    }

    private void validatePasswordByACL(String roleList,  Account updateUserAccount){
        if (updateUserAccount.getUserId()== this.userId) return ;
        if (!hasAdminRight(updateUserAccount))
        throw new AppMessageException("userAccount.validation.need_admin_right") ;

    }

    public boolean validatePassword(String password){
        if (LoginSourceType.valueOf(this.getAuthenticationSource()) != LoginSourceType.NULL)  //means login from third party
            return true;
        if ((!getPassword().isEmpty())&&(this.password.equalsIgnoreCase(password)))
           return true ;
        return false ;

    }

    public void changeAuthenticationSource(String authenticationSource, Account updateUserAccount){
        if (!hasAdminRight(updateUserAccount))
            throw new AppMessageException("userAccount.validation.need_admin_right") ;
        this.authenticationSource = authenticationSource ;
    }

    public void registerSuccessLogin(){
        this.lastSuccessLogin = LocalDateTime.now();
        locked = false ;
        faliedLoginAttemp = 0 ;
    }

    public void registerFailedLogin(){
        this.lastFaliedLogin = LocalDateTime.now();
        this.faliedLoginAttemp ++ ;
        if (faliedLoginAttemp > 5)
            locked = true ;
    }

    public void UnlockAccount(Account updateUserAccount){
        if (!locked) return ;
        if (!hasAdminRight(updateUserAccount))
            throw new AppMessageException("userAccount.validation.need_admin_right") ;

        locked = false ;
    }

    private boolean hasAdminRight(Account updateUserAccount){
        if (updateUserAccount.getRoleList().contains(GroupType.ADMIN.name()))
            return true ;
        return false ;
    }
}

package com.peak.training.account.service;

import com.peak.training.account.domain.model.*;
import com.peak.training.account.dto.LoginDTO;
import com.peak.training.account.infrastructure.adapter.AccountAdapter;
import com.peak.training.account.infrastructure.adapter.UserLoginAdapter;
import com.peak.training.account.port.LoginPort;
import com.peak.training.common.exception.AppMessageException;
import com.peak.training.common.annotation.log.LogMethodData;
import com.peak.training.common.annotation.token.dto.UserDTO;
import com.peak.training.common.annotation.token.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class LoginService implements LoginPort {
    @Autowired
    AccountAdapter accountAdapter;

    @Autowired
    UserLoginAdapter loginAdapter;

    @Autowired
    JWTUtility jwtUtility ;


    @Override
    public UserLogin registerNewLogin(int userId, String password, LoginSourceType sourceType, int updateUserId) {
        UserLogin model= new UserLogin(userId, password, sourceType);
        return loginAdapter.persistUserLogin(model, updateUserId);

    }

    @LogMethodData
    @Override
    public LoginDTO login(String emailAddress, String password) {
        Account account = accountAdapter.getAccountByEmailAddress(emailAddress);


        UserLogin login = loginAdapter.getUserLoginById(account.getUserId());
        boolean validate = login.validatePassword(password);
        if (!validate){
            throw new AppMessageException("userlogin.validation.password_mismatch") ;
        }
        return getLoginDTO(account);

    }

    public LoginDTO getLoginDTO(Account account){
        UserDTO dto = UserDTOMapper.modelToDto(account) ;
        return LoginDTO.builder()
                .userId(account.getUserId())
                .token(jwtUtility.createJwtSignedHMAC(dto))
                .build();
    }

    @LogMethodData
    @Override
    public void changePassword(int userId, String oldpassword, String newPassword, int updateUserId){

        UserLogin login = loginAdapter.getUserLoginById(userId) ;
        Account account = accountAdapter.getAccountById(userId);
        Account updateUserAccount = accountAdapter.getAccountById(updateUserId);

        login.changePassword(oldpassword, newPassword, updateUserAccount);
        loginAdapter.persistUserLogin(login, updateUserId);
    }

    private String getLoignMessage(String emailAddress, int updateUserId){
        return "emailAddress=" + emailAddress + ", by UserID" + Integer.toString(updateUserId) ;
    }

    @LogMethodData
    @Override
    public void changeLoginSource(int userId, String sourceType, int updateUserId){


        UserLogin login = loginAdapter.getUserLoginById(userId) ;
        Account updateUserAccount = accountAdapter.getAccountById(updateUserId);
        login.changeAuthenticationSource(sourceType, updateUserAccount);
        loginAdapter.persistUserLogin(login, updateUserId);
    }

    @LogMethodData
    @Override
    public void unlockUser(int userId,  int updateUserId) {

        UserLogin login = loginAdapter.getUserLoginById(userId) ;
        Account updateUserAccount = accountAdapter.getAccountById(updateUserId);
        login.UnlockAccount(updateUserAccount);
        loginAdapter.persistUserLogin(login, updateUserId);
    }

}

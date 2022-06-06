package com.peak.training.account.port;


import com.peak.training.account.domain.model.Account;
import com.peak.training.account.domain.model.LoginSourceType;
import com.peak.training.account.domain.model.UserLogin;
import com.peak.training.account.dto.LoginDTO;

public interface LoginPort {

    public UserLogin registerNewLogin(int userId, String password, LoginSourceType sourceType, int updateUserId) ;
    public LoginDTO login(String emailAddress, String password) ;
    public LoginDTO getLoginDTO(Account account);
    public void changePassword(int userId, String oldPassword, String password, int updateUserId);
    public void changeLoginSource(int userId, String sourceType,  int updateUserId) ;
    public void unlockUser(int userId,  int updateUserId) ;

}

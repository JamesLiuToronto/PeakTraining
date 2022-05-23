package com.peak.training.account.port;


import com.peak.training.account.domain.model.Account;
import com.peak.training.account.dto.LoginDTO;

public interface LoginPort {
    public LoginDTO login(String emailAddress, String password) ;
    public LoginDTO getLoginDTO(Account account);
    public void changePassword(int userId, String oldPassword, String password, int updateUserId);
    public void persistChangeSourceLogin(int userId, String sourceType,  int updateUserId) ;

}

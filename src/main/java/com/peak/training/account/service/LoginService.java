package com.peak.training.account.service;

import com.peak.training.account.domain.model.*;
import com.peak.training.account.dto.LoginDTO;
import com.peak.training.account.infrastructure.adapter.AccountAdapter;
import com.peak.training.account.infrastructure.adapter.UserLoginAdapter;
import com.peak.training.account.port.LoginPort;
import com.peak.training.common.transactionlog.TransactionLogAdapter;
import com.peak.training.common.transactionlog.TransactionLogClient;
import com.peak.training.common.exception.AppMessageException;
import com.peak.training.common.transactionlog.TransactionLogType;
import org.peak.common.token.dto.UserDTO;
import org.peak.common.token.utility.JWTUtility;
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
    TransactionLogAdapter logClient ;

    @Autowired
    JWTUtility jwtUtility ;

    @Override
    public LoginDTO login(String emailAddress, String password) {
        Account account = accountAdapter.getAccountByEmailAddress(emailAddress);

        logClient.persistTransactionLog(account.getUuid(),
                TransactionLogType.TX_LOGIN.LOGIN.name(),
                emailAddress,
                TransactionLogType.STATUS.REQUEST.name(),
                account.getUserId()) ;

        UserLogin login = loginAdapter.getUserLoginById(account.getUserId());
        boolean validate = login.validatePassword(password);
        if (!validate){
            logClient.persistTransactionLog(account.getUuid(),
                    TransactionLogType.TX_LOGIN.LOGIN.name(),
                    emailAddress,
                    TransactionLogType.STATUS.FAIL.name(),
                    account.getUserId()) ;
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

    public void changePassword(int userId, String oldpassword, String newPassword, int updateUserId){

        UserLogin login = loginAdapter.getUserLoginById(userId) ;
        Account account = accountAdapter.getAccountById(userId);

        logClient.persistTransactionLog(account.getUuid(),
                TransactionLogType.TX_LOGIN.CHANGE_PASSWORD.name(),
                Integer.toString(userId) + ", by updateUserid=" + Integer.toString(updateUserId),
                TransactionLogType.STATUS.REQUEST.name(),
                account.getUserId()) ;


        login.changePassword(account.getRoleList(), oldpassword, newPassword, updateUserId);
        loginAdapter.persistChangePassword(userId, newPassword, updateUserId);
    }

    private String getLoignMessage(String emailAddress, int updateUserId){
        return "emailAddress=" + emailAddress + ", by UserID" + Integer.toString(updateUserId) ;
    }

    @Override
    public void persistChangeSourceLogin(int userId, String sourceType, int updateUserId){

        logClient.persistTransactionLog(Integer.toString(userId),
                TransactionLogType.TX_LOGIN.CHANGE_PASSWORD.name(),
                Integer.toString(userId) + ", source type=" + sourceType,
                TransactionLogType.STATUS.REQUEST.name(),
                updateUserId) ;

        loginAdapter.persistChangeSourceLogin(userId, LoginSourceType.valueOf(sourceType), updateUserId);
    }

}

package com.peak.training.tokenprocess;


import com.peak.training.common.annotation.token.dto.TokenDTO;
import com.peak.training.common.annotation.token.utility.TokenUtility;
import com.peak.training.common.exception.AppMessageException;
import com.peak.training.tokenprocess.process.AccountProcess;
import com.peak.training.tokenprocess.process.TokenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class TokenProcessService  {
    @Autowired
    AccountProcess accountProcess;

    @Autowired
    TokenUtility tokenUtility ;

    @Autowired
    private PasswordEncoder passwordEncoder;


    //@LogMethodData
    public void process(String token) {
        TokenDTO dto = tokenUtility.getTokenDTOFromToken(token) ;
        if (dto.isExpired()){
            throw new AppMessageException("process.toke.expired") ;
        }
        if (TokenType.Process.valueOf(dto.getType())==TokenType.Process.ACTIVATE)
        {
            accountProcess.activateAccount(dto);
            return ;
        }
    }

    public String getActivateAccountToken(int userId) {
            TokenDTO dto = accountProcess.getActivateAccountToken(userId) ;
            return tokenUtility.createJwtSignedHMAC(dto) ;
    }

    public String getEncrptedMessage(String message) {
        return passwordEncoder.encode(message) ;
    }




}

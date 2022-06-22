package com.peak.training.tokenprocess.process;


import com.peak.training.account.service.AccountService;
import com.peak.training.common.annotation.token.dto.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountProcess {

    @Autowired
    AccountService service;

    public void activateAccount(TokenDTO dto){
        int userId = Integer.valueOf(dto.getKeyValue()) ;
        service.activateAccount(userId, -1);
    }

    public TokenDTO getActivateAccountToken(int userId){
        return TokenDTO.builder()
                .type(TokenType.Process.ACTIVATE.name())
                .keyValue(Integer.toString(userId))
                .keyName("UserId").build() ;
    }

    public TokenDTO getFamiltSetupToken(int askedForUserId, int requestedByUserId){
        return TokenDTO.builder()
                .type(TokenType.Process.FAMILY_SETUP.name())
                .keyValue(Integer.toString(askedForUserId))
                .keyName(Integer.toString(requestedByUserId)).build() ;
    }
}

package com.peak.training.tokenprocess.process;


import com.peak.training.account.infrastructure.adapter.AccountAdapter;
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
}

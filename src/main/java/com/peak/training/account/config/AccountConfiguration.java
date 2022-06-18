package com.peak.training.account.config;

import com.peak.training.common.annotation.token.utility.JWTUtility;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfiguration {

    @Bean
    JWTUtility jwtUtility(){
        return new JWTUtility();
    }


}

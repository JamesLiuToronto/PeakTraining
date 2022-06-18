package com.peak.training.common.annotation.token.config;

import com.peak.training.common.annotation.token.AuthorizeUserAspect;
import com.peak.training.common.annotation.token.config.AuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(AuthorizeUserAspect.class)
@ConditionalOnProperty(prefix = "peak.security.token", name="enableTokenCheck", havingValue = "true")
@EnableConfigurationProperties(com.peak.training.common.annotation.token.config.AuthProperties.class)
public class AuthConfiguration {

    @Autowired
    AuthProperties authProperties ;

    //@ConditionalOnMissingBean

    /*@Bean
    AuthorizeUserAspect authorizeUserAspect(){

        return new AuthorizeUserAspect(authProperties.getTokenName());
    }*/


}

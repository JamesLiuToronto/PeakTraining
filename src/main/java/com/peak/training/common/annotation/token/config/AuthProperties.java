package com.peak.training.common.annotation.token.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "peak.security.token")
@Data
@Configuration
public class AuthProperties {
    String enableTokenCheck = "false" ;
    String tokenName ="access_token";
}


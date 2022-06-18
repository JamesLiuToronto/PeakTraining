package com.peak.training.common.annotation.log.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "peak.method.log")
@Data
@Configuration
public class MylogProperties {
    String enable = "false" ;

}


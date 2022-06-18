package com.peak.training.common.annotation.validation.config;

import com.peak.training.common.annotation.validation.errormessage.ErrorMessage;
import com.peak.training.common.annotation.validation.exception.AuthorizeExceptionHandler;
import com.peak.training.common.annotation.validation.exception.BusinessExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class ErrorMessageConfiguration {

    @Value("${application.translation.properties.baseName}")
    private String propertiesBasename;
/*
    @Value("${application.translation.properties.defaultLocale}")
    private String defaultLocale;

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
        acceptHeaderLocaleResolver.setDefaultLocale(new Locale(defaultLocale));
        return acceptHeaderLocaleResolver;
    }
*/
    @Bean(name = "errorResourceBundleMessageSource")
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
        rs.setBasename(propertiesBasename);
        rs.setDefaultEncoding("UTF-8");
        rs.setUseCodeAsDefaultMessage(true);
        return rs;
    }

    @Bean
    public ErrorMessage getErrorMessage(){
        return new ErrorMessage(messageSource());
    }

    @Bean
    public BusinessExceptionHandler getBusinessExceptionHandler(){
        return new BusinessExceptionHandler();
    }

    @Bean
    public AuthorizeExceptionHandler getAuthorizeExceptionHandler(){
        return new AuthorizeExceptionHandler();
    }
}

package com.peak.training.account.validation;

import com.peak.training.account.domain.model.LoginSourceType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class LoginSourceTypeValidator implements ConstraintValidator<LoginSourceTypeValidation, String>
{
    public boolean isValid(String loginSourceType, ConstraintValidatorContext cxt) {
        try {
            LoginSourceType.valueOf(loginSourceType);
        } catch (Exception e){
            return false ;
        }
        return true ;

    }
}

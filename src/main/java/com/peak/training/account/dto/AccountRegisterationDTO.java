package com.peak.training.account.dto;
import com.peak.training.account.domain.model.GroupType;
import com.peak.training.account.validation.LoginSourceTypeValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.peak.common.myvalidation.validation.EmailValidation;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRegisterationDTO implements java.io.Serializable{

    @EmailValidation()
    @NotNull(message = "validation.email.code")
    String emailAddress ;
    String password ;

    @NotNull(message = "login.validation.invalid_data.login_suorce_type")
    @LoginSourceTypeValidation
    String loginSourceType;

    String firstName ;

    @NotNull(message = "login.validation.notnull.lastname")
    String lastName ;
    String gender ;
    LocalDate birthDate;
    List<GroupType> groupTypeList ;
}

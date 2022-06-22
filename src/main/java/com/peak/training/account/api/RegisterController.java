package com.peak.training.account.api;


import com.peak.training.account.model.Account;
import com.peak.training.account.dto.AccountRegisterationDTO;
import com.peak.training.account.service.AccountService;
import com.peak.training.account.validation.GroupTypeRegisterValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("registeration")
@Tag(name = "Peak Training User Account Interface", description = "the API with documentation annotations")
public class RegisterController {

    @Autowired
    AccountService service;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Register new UserAccount for ",
            description = "This service is for registering student and parent, no authentication required")
    public Account registerNewAccount(@Valid @RequestBody AccountRegisterationDTO dto) {
        GroupTypeRegisterValidator.validateStudentGroupType(dto.getGroupTypeList());
        return registerNewAccount( dto, -1);

    }

    private Account registerNewAccount(AccountRegisterationDTO dto, int updateuserId) {
        Account account = service.newAccount(dto.getEmailAddress(),
                dto.getPassword(),
                dto.getLoginSourceType(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getBirthDate(),
                dto.getGender(),
                updateuserId,
                dto.getGroupTypeList());
        return account ;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/activate",
            //consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Activate account", description = "This operation will user information")
    public Account activate(
            @Parameter(description="token", required=true)
            @RequestHeader("registeration_token") int updateUserId){
        return service.activateAccount(updateUserId, updateUserId);
    }

    @GetMapping(value = "successful",
            //consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "login Success inforamtion", description = "login success ")
    public String loginSuccess() {

        return "Login success" ;
    }

}


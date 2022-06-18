package com.peak.training.account.api;


import com.peak.training.account.domain.model.*;
import com.peak.training.account.dto.AccountRegisterationDTO;
import com.peak.training.account.service.AccountService;
import com.peak.training.account.validation.GroupTypeRegisterValidator;
import com.peak.training.common.annotation.token.AuthorizeUser;
import com.peak.training.common.domain.valueobject.EmailAddress;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@RequestMapping("api/account")
@Tag(name = "Peak Training User Account Interface", description = "the API with documentation annotations")
public class AccountController  {

    @Autowired
    AccountService service;

    //@AuthorizeUser(requiredRoles = "ACCOUNT.GET_USER")
    @GetMapping(value = "/{user-accountid}",
            //consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Get UserAccount inforamtion", description = "This will get user account information by userAccountId ")
    public Account getByid(
            @Parameter(description="userId",
                    required=true)
            @PathVariable("user-accountid") int userAccountId
            //@Parameter(description="token", required=true)
            //@RequestHeader("access_token") String access_token,
            //@Parameter(description="update userID", required=true)
            //@RequestHeader("update-userid") int updateUserID
            ){

        return service.getAccountById(userAccountId) ;
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
    @AuthorizeUser(requiredRoles = "ADMIN")
    @PostMapping(value = "/by-admin",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Register new UserAccount By Admin ",
            description = "This service is for registering All type users, authentication required")
    public Account registerNewAccountByAdmin(@Valid @RequestBody AccountRegisterationDTO dto,
                                          @Parameter(description="update userID", required=true)
                                              @RequestHeader("update-userid") int updateUserId) {
        GroupTypeRegisterValidator.validatenNullGroupType(dto.getGroupTypeList());
        return registerNewAccount( dto, updateUserId);

    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{user-accountid}/activate",
            //consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Activate account", description = "This operation will user information")
    public Account activate(
            @Parameter(description="User Account ID", required=true)
            @PathVariable("user-accountid") int userAccountId ,
            @Parameter(description="update userID", required=true)
            @RequestHeader("update-userid") int updateUserId){
        return service.activateAccount(userAccountId, updateUserId);
    }

    @ResponseStatus(HttpStatus.OK)
    //@AuthorizeUser(requiredRoles = "ACCOUNT.DEACTIVATE_USER")
    @PutMapping(value = "/{user-accountid}/deactivate",
           //consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "deactive account", description = "This operation will user information")
    public Account deactivate(
            @Parameter(description="User Account ID", required=true)
            @PathVariable("user-accountid") int userAccountId ,
           // @Parameter(description="token", required=true)
            //@RequestHeader("access_token") String access_token,
            @Parameter(description="update userID", required=true)
            @RequestHeader("update-userid") int updateUserId) {

        return service.activateAccount(userAccountId, updateUserId);

    }


    @AuthorizeUser(requiredRoles = "SELF,ADMIN")
    @PutMapping(value = "/{user-accountid}/personinfo",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Update User Information", description = "This operation will user information")
    public Account updateUserInfo(
            @Parameter(description="User Account ID", required=true)
            @PathVariable("user-accountid") int userAccountId ,
            @Parameter(description="Person information",
                    required=true, schema=@Schema(implementation = Person.class))
            @RequestBody Person person ,
            @Parameter(description="update userID", required=true)
            @RequestHeader("update-userid") int updateUserId) {

        return service.changePersonInfo(userAccountId, person, updateUserId);
    }

    @AuthorizeUser(requiredRoles = "SELF,ADMIN")
    @PutMapping(value = "/{user-accountid}/emailaddress",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Update User Information", description = "This operation will user information")
    public Account updateEmailAddress(
            @Parameter(description="User Account ID", required=true)
            @PathVariable("user-accountid") int userAccountId ,
            @Parameter(description="Email information",
                    required=true, schema=@Schema(implementation = EmailAddress.class))
            @RequestBody EmailAddress emailAddress ,
            @Parameter(description="update userID", required=true)
            @RequestHeader("update-userid") int updateUserId ) {

        return service.changeEmailAddress(userAccountId, emailAddress, updateUserId);
    }

    @AuthorizeUser(requiredRoles = "ADMIN")
    @PostMapping(value = "/{user-accountid}/usergroup",
            //consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Assign group type", description = "This operation will assign group type to user")
    public Account assignUsergroup(
            @Parameter(description="User Account ID", required=true)
            @PathVariable("user-accountid") int userAccountId ,
            @RequestParam("group-type") String groupType ,
            @Parameter(description="update userID", required=true)
            @RequestHeader("update-userid") int updateUserId) {

        return service.AssignUserGroup(userAccountId, GroupType.valueOf(groupType), updateUserId);
    }

/*
    @AuthorizeUser(requiredRoles = "ACCOUNT.GET_ACL_LIST")
    //@ExceptionHandler(AppNoAccessException.class)
    @GetMapping(value = "/{group-code}/groupcode",
            //consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Set<MethodACL> getAccess(
            @Parameter(description="token", required=true)
            @RequestHeader("access_token") String access_token,
            @Parameter(description="update userID", required=true)
            @RequestHeader("update-userid") int updateUserId
    ) {
        Set<MethodACL> set = service.getAclList(updateUserId) ;
        return set ;
    }
*/

}


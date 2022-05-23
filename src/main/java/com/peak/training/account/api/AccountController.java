package com.peak.training.account.api;


import com.peak.training.account.domain.model.*;
import com.peak.training.account.dto.AccountRegisterationDTO;
import com.peak.training.account.dto.LoginDTO;
import com.peak.training.account.service.AccountService;
import com.peak.training.account.validation.GroupTypeRegisterValidator;
import com.peak.training.common.domain.valueobject.EmailAddress;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.peak.common.token.AuthorizeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("peaktraining/account")
@Tag(name = "Peak Training User Account Interface", description = "the API with documentation annotations")
public class AccountController  {

    @Autowired
    AccountService service;

    @AuthorizeUser(pageKey = "ACCOUNT.GET_USER")
    @GetMapping(value = "/{user-accountid}",
            //consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Get UserAccount inforamtion", description = "This will get user account information by userAccountId ")
    public Account getByid(
            @Parameter(description="userId",
                    required=true)
            @PathVariable("user-accountid") int userAccountId ,
            @Parameter(description="token", required=true)
            @RequestHeader("access_token") String access_token,
            @Parameter(description="update userID", required=true)
            @RequestHeader("update-userid") int updateUserID){

        return service.getAccountById(userAccountId) ;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "",
            //consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Register new UserAccount for ",
            description = "This service is for registering student and parent, no authentication required")
    public void registerNewAccount(@Valid @RequestBody AccountRegisterationDTO dto) {
        GroupTypeRegisterValidator.validateStudentGroupType(dto.getGroupTypeList());
        registerNewAccount( dto, -1);

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
    @AuthorizeUser(pageKey = "ACCOUNT.NEW_USER")
    @PostMapping(value = "/by-admin",
            //consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Register new UserAccount By Admin ",
            description = "This service is for registering All type users, authentication required")
    public void registerNewAccountByAdmin(@Valid @RequestBody AccountRegisterationDTO dto,
                                          @Parameter(description="token", required=true)
                                          @RequestHeader("access_token") String access_token,
                                          @Parameter(description="update userID", required=true)
                                              @RequestHeader("update-userid") int updateUserId) {
        GroupTypeRegisterValidator.validatenNullGroupType(dto.getGroupTypeList());
        registerNewAccount( dto, updateUserId);

    }

    @AuthorizeUser(pageKey = "ACCOUNT.ACTIVATE_USER")
    @PutMapping(value = "/{user-accountid}/activate",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Activate account", description = "This operation will user information")
    public void activate(
            @Parameter(description="User Account ID", required=true)
            @PathVariable("user-accountid") int userAccountId ,
            @Parameter(description="token", required=true)
            @RequestHeader("access_token") String access_token,
            @Parameter(description="update userID", required=true)
            @RequestHeader("update-userid") int updateUserId){
        service.activateAccount(userAccountId, updateUserId);
    }

    @AuthorizeUser(pageKey = "ACCOUNT.DEACTIVATE_USER")
    @PutMapping(value = "/{user-accountid}/deactivate",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "deactive account", description = "This operation will user information")
    public void deactivate(
            @Parameter(description="User Account ID", required=true)
            @PathVariable("user-accountid") int userAccountId ,
            @Parameter(description="token", required=true)
            @RequestHeader("access_token") String access_token,
            @Parameter(description="update userID", required=true)
            @RequestHeader("update-userid") int updateUserId) {

        service.activateAccount(userAccountId, updateUserId);
    }


    @AuthorizeUser(pageKey = "ACCOUNT.UPDATE_USERINFO")
    @PutMapping(value = "/{user-accountid}/personinfo",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Update User Information", description = "This operation will user information")
    public void updateUserInfo(
            @Parameter(description="User Account ID", required=true)
            @PathVariable("user-accountid") int userAccountId ,
            @Parameter(description="Person information",
                    required=true, schema=@Schema(implementation = Person.class))
            @RequestBody Person person ,
            @Parameter(description="token", required=true)
            @RequestHeader("access_token") String access_token,
            @Parameter(description="update userID", required=true)
            @RequestHeader("update-userid") int updateUserId) {

        service.changePersonInfo(userAccountId, person, updateUserId);
    }

    @AuthorizeUser(pageKey = "ACCOUNT.UPDATE_EMAILADDRESS")
    @PutMapping(value = "/{user-accountid}/emailaddress",
            //consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Update User Information", description = "This operation will user information")
    public void updateEmailAddress(
            @Parameter(description="User Account ID", required=true)
            @PathVariable("user-accountid") int userAccountId ,
            @Parameter(description="Email information",
                    required=true, schema=@Schema(implementation = EmailAddress.class))
            @RequestBody EmailAddress emailAddress ,
            @Parameter(description="token", required=true)
            @RequestHeader("access_token") String access_token,
            @Parameter(description="update userID", required=true)
            @RequestHeader("update-userid") int updateUserId ) {

        service.changeEmailAddress(userAccountId, emailAddress, updateUserId);
    }

    @AuthorizeUser(pageKey = "ACCOUNT.ASSIGN_USERGROUP")
    @PostMapping(value = "/{user-accountid}/usergroup",
            //consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Assign group type", description = "This operation will assign group type to user")
    public void assignUsergroup(
            @Parameter(description="User Account ID", required=true)
            @PathVariable("user-accountid") int userAccountId ,
            @RequestParam("group-type") String groupType ,
            @Parameter(description="token", required=true)
            @RequestHeader("access_token") String access_token,
            @Parameter(description="update userID", required=true)
            @RequestHeader("update-userid") int updateUserId) {

        service.AssignUserGroup(userAccountId, GroupType.valueOf(groupType), updateUserId);
    }

/*
    @AuthorizeUser(pageKey = "ACCOUNT.GET_ACL_LIST")
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


package com.peak.training.account.api;


import com.peak.training.account.dto.LoginDTO;
import com.peak.training.account.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.peak.training.common.annotation.token.AuthorizeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/login")
@Tag(name = "Peak Training User login Interface", description = "the API with login ")
public class LoginController {

    @Autowired
    LoginService service;

    @PostMapping(value = "",
            //consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "login inforamtion", description = "This will generate token String by login ")
    public LoginDTO login(
            @Parameter(description="emailAddress",
                    required=true)
            @RequestParam ("email-address") String emailAddress ,
            @Parameter(description="password",
                    required=true)
            @RequestParam("password") String password
    ){

        LoginDTO dto = service.login(emailAddress, password) ;
        return dto ;
    }

    @GetMapping(value = "successful",
            //consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "login Success inforamtion", description = "login success ")
    public String loginSuccess() {

        return "Login success" ;
    }


    @AuthorizeUser(requiredRoles = "LOGIN.CHANGE_PASSWORD")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/change-password",
            //consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "login inforamtion", description = "This will generate token String by login ")
    public void changePassword(
            @Parameter(description="userID",
                    required=true)
            @RequestParam ("userID") int userID ,
            @Parameter(description="oldPassword",
                    required=true)
            @RequestParam("old-password") String oldPassword,
            @Parameter(description="new-password",
                    required=true)
            @RequestParam("newPassword") String newPassword,
            @RequestHeader("access_token") String access_token,
            @RequestHeader("update-userid") int updateuserID
    ){

        service.changePassword(userID,oldPassword, newPassword,updateuserID);
    }

    @AuthorizeUser(requiredRoles = "LOGIN.CHANGE_LOGIN_SOURCE")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/change-login-source",
            //consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "login inforamtion", description = "This will generate token String by login ")
    public void changeLoginSource(
            @Parameter(description="userID",
                    required=true)
            @RequestParam ("userID") int userID ,
            @Parameter(description="LoginSource",
                    required=true)
            @RequestParam("login-source-type") String loginSourceType,
            @RequestHeader("access_token") String access_token,
            @RequestHeader("update-userid") int updateuserID
    ){

        service.changeLoginSource(userID, loginSourceType, updateuserID);
    }

    @AuthorizeUser(requiredRoles = "LOGIN.UNLOCK_USER")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/unlock_user",
            //consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "login inforamtion", description = "This will generate token String by login ")
    public void unlockUser(
            @Parameter(description="userID",
                    required=true)
            @RequestParam ("userID") int userID ,
            @RequestHeader("access_token") String access_token,
            @RequestHeader("update-userid") int updateuserID
    ){

        service.unlockUser(userID, updateuserID);
    }

}


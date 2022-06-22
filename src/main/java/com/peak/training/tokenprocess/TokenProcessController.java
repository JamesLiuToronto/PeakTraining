package com.peak.training.tokenprocess;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("process")
@Tag(name = "This is token process general interface", description = "the API with token process general interface")
public class TokenProcessController {

    @Autowired
    TokenProcessPort service;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{token}")
    @Operation(summary = "the API with token process general",
            description = "This service is the API with token process general")
    public void tokenProcess(@Parameter(description="token", required=true)
                                          @PathVariable("token") String token ) {
        service.process(token);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/activated-token/{userid}")
    @Operation(summary = "the API with generate activate token",
            description = "This service is the API with token process general")
    public String getActivateToken(@Parameter(description="userId", required=true)
                             @PathVariable("userid") Integer userId ) {
        return service.getActivateAccountToken(userId) ;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/encrpt-message/{message}")
    @Operation(summary = "the API with generate encrpted message",
            description = "This service is the API encrpting ,essage")
    public String getEncrptedMessage(@Parameter(description="message", required=true)
                                   @PathVariable("message") String message ) {
        return service.getEncrptedMessage(message) ;
    }


}


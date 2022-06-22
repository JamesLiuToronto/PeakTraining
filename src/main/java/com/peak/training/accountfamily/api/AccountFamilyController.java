package com.peak.training.accountfamily.api;


import com.peak.training.accountfamily.dto.FamilyDTO;
import com.peak.training.accountfamily.service.FamilyPort;
import com.peak.training.tokenprocess.TokenProcessPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/account-family")
@Tag(name = "This is Account family interface", description = "the API with family interface")
public class AccountFamilyController {

    @Autowired
    FamilyPort service;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{userid}")
    @Operation(summary = "the API with token process general",
            description = "This service is the API with token process general")
    public List<FamilyDTO> tokenProcess(@Parameter(description="userid", required=true)
                                          @PathVariable("userid") int userId ) {
        return service.getFamilyAccountList(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "")
    @Operation(summary = "the API with generate activate token",
            description = "This service is the API with token process general")
    public String setFamily(@Parameter(description="userId", required=true)
                                 @RequestParam("aksed-userid") Integer askedForUserId ,
                             @Parameter(description="update userID", required=true)
                             @RequestHeader("update-userid") int updateUserId) {
        return service.setFamilyMember(askedForUserId, updateUserId) ;
    }


}


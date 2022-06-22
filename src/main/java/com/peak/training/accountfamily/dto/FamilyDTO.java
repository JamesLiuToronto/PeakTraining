package com.peak.training.accountfamily.dto;

import com.peak.training.account.model.Account;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class FamilyDTO implements java.io.Serializable{
    Account userAccount ;
    boolean active ;

}

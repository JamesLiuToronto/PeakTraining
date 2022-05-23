package com.peak.training.account.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginDTO implements java.io.Serializable{
    int userId;
    String token;
}

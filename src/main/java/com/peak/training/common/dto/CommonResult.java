package com.peak.training.common.dto;


import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CommonResult<T> implements java.io.Serializable {

    boolean successful;
    T data ;
    String message ;

    public CommonResult(boolean successful, T data, String message) {
        this.successful = successful;
        this.data = data;
        this.message = message;
    }
}

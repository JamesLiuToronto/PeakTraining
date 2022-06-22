package com.peak.training.account.model;

import com.peak.training.common.domain.base.ValueObject;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MethodACL implements ValueObject {

    String methodcode ;
    boolean allowed ;

}

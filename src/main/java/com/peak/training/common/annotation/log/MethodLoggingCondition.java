package com.peak.training.common.annotation.log;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class MethodLoggingCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        //return false ;
        String logEnabled = context.getEnvironment().getProperty("peak.method.log.enable");
        return Boolean.valueOf(logEnabled);
    }
}
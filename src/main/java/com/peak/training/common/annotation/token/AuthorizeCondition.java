package com.peak.training.common.annotation.token;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class  AuthorizeCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String tokenCheckingEnabled = context.getEnvironment().getProperty("peak.security.token.enableTokenCheck");
        return Boolean.valueOf(tokenCheckingEnabled);
    }
}
package com.peak.training.account.model;

import com.peak.training.common.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

public class UserGroupKey extends DomainObjectId {
    public UserGroupKey(@NonNull String uuid) {
        super(uuid);
    }
}
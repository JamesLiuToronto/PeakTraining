package com.peak.training.account.domain.model;

import com.peak.training.common.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

public class AccountKey extends DomainObjectId {
    public AccountKey(@NonNull String uuid) {
        super(uuid);
    }
}

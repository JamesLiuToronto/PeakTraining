package com.peak.training.account.domain.model;

import com.peak.training.common.domain.base.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class UserGroup extends BaseEntity {
    private Integer userGroupId ;
    private GroupType groupType;
    private boolean active ;
    private Integer auditId;

    @Builder
    public UserGroup(String uuid, LocalDateTime utimestamp,
                     Integer userGroupId, GroupType groupType,
                     boolean active, Integer auditId) {
        super(uuid,utimestamp) ;
        this.userGroupId = userGroupId;
        this.groupType = groupType;
        this.active = active;
        this.auditId = auditId ;
    }

    public UserGroup() {
        super(UUID.randomUUID().toString(),LocalDateTime.now()) ;
    }

    public void AssignNewUserGroup(GroupType groupType) {
        this.groupType = groupType;
        this.active = true;
    }

    public void disableGroup(){
        this.active = false ;
    }

    public void activateGroup(){
        this.active = true ;
    }
}

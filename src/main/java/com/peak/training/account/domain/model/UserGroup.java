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


    @Builder
    public UserGroup(String uuid,
                     Integer userGroupId, GroupType groupType,
                     boolean active) {
        super(uuid) ;
        this.userGroupId = userGroupId;
        this.groupType = groupType;
        this.active = active;

    }

    @Builder
    public UserGroup(GroupType groupType) {

        super(UUID.randomUUID().toString()) ;
        this.groupType = groupType;
        this.active = true;

    }

    public UserGroup() {
        super(UUID.randomUUID().toString()) ;
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

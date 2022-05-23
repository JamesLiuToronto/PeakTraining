package com.peak.training.account.infrastructure.mapper;

import com.peak.training.account.domain.model.GroupType;
import com.peak.training.account.domain.model.UserGroup;
import com.peak.training.account.infrastructure.entity.UserGroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;


@Mapper
public interface UserGroupMapper {

    UserGroupMapper INSTANCE = Mappers.getMapper(UserGroupMapper.class);

    //@Mapping(source = "entity.groupCode", target = "groupType")
    //@Mapping(source = "entity.uuid", target = "id")
    @Named("entityToModel")
    public static UserGroup entityToModel(final UserGroupEntity entity){

        return UserGroup.builder()
                .uuid(entity.getUuid())
                .utimestamp(entity.getUtimestamp())
                .groupType(GroupType.valueOf(entity.getGroupCode()))
                .active(entity.isActive())
                .userGroupId(entity.getUserGroupId())
                .auditId(entity.getAuditId())
                .build();
    }

    @Mapping(source = "model.groupType", target = "groupCode")
    UserGroupEntity modelToEntity(final UserGroup model);
}

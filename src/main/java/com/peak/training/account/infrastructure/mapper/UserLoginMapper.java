package com.peak.training.account.infrastructure.mapper;

import com.peak.training.account.domain.model.UserLogin;
import com.peak.training.account.infrastructure.entity.UserLoginEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;


@Mapper
public interface UserLoginMapper {

    UserLoginMapper INSTANCE = Mappers.getMapper(UserLoginMapper.class);

    @Named("entityToModel")
    public UserLogin entityToModel(final UserLoginEntity entity);

    public UserLoginEntity modelToEntity(final UserLogin model);
}

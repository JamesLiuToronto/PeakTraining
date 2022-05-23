package com.peak.training.account.service;

import com.peak.training.account.domain.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.peak.common.token.dto.UserDTO;

@Mapper
public interface UserDTOMapper {

    UserDTOMapper INSTANCE = Mappers.getMapper(UserDTOMapper.class);


    @Named("modelToDto")
    public static UserDTO modelToDto(final Account model) {
        return UserDTO.builder()
                .firstName(model.getPersonInfo().getFirstName())
                .lastName(model.getPersonInfo().getLastName())
                .emailAddress(model.getEmailAddress().getEmail())
                .userID(model.getUserId())
                .uuid(model.getUuid())
                .roleList(model.getRoleList())
                .disallowMethodList(model.getDisAllowMethodList())
                .allowMethodList(model.getAllowMethodList())
                .build();


    }
}




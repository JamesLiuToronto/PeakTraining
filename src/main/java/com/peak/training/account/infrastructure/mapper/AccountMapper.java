package com.peak.training.account.infrastructure.mapper;

import com.peak.training.account.domain.model.Account;
import com.peak.training.account.domain.model.Person;
import com.peak.training.account.domain.model.UserGroup;
import com.peak.training.account.domain.model.UserStatus;
import com.peak.training.account.infrastructure.entity.UserAccountEntity;
import com.peak.training.common.domain.valueobject.EmailAddress;
import com.peak.training.common.enumtype.Gender;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(uses = {UserGroupMapper.class})
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);


    @Named("entityToModel")
    public static Account entityToModel(final UserAccountEntity entity) {
        Person person = Person.builder()
                .birthDate(entity.getBirthDate())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .sex(Gender.TYPE.getTypeByChar(entity.getSex().charAt(0)))
                .build();
        List<UserGroup> list = new ArrayList<>();
        entity.getUserGroups().stream().forEach(x-> list.add(UserGroupMapper.entityToModel(x)));
        return new Account(entity.getUuid(),
                entity.getUtimestamp(),
                entity.getUserId(),
                new EmailAddress(entity.getEmailAddress()),
                person, UserStatus.valueOf(entity.getStatusCode()),
                list, entity.getAuditId()) ;

    }

    @Mapping(source = "emailAddress", target = "emailAddress")
    @Mapping(source = "person.firstName", target = "firstName")
    @Mapping(source = "person.lastName", target = "lastName")
    @Mapping(source = "person.birthDate", target = "birthDate")
    @Mapping(source = "sex", target = "sex")
    @Mapping(source = "model.userStatus", target = "statusCode")
    public UserAccountEntity modelToEntity(final Account model, Person person,
                                    String emailAddress, String sex);
}




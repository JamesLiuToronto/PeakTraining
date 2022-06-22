package com.peak.training.account.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.peak.training.common.domain.base.ValueObject;
import com.peak.training.common.enumtype.Gender;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Value
@Builder
@Jacksonized
public class Person implements ValueObject {

    @NotNull
    String firstName ;
    @NotNull
    String lastName ;
    LocalDate birthDate;
    Gender.TYPE sex ;


    @JsonIgnore
    public char getGenderChar(){
        return sex.getValue() ;
    }
}

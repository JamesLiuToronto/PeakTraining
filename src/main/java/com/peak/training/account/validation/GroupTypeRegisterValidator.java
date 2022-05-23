package com.peak.training.account.validation;

import com.peak.training.account.domain.model.GroupType;
import com.peak.training.common.exception.AppMessageException;

import java.util.List;
import java.util.stream.Collectors;

public class GroupTypeRegisterValidator {

    public static void validateStudentGroupType(List<GroupType> list){
        validatenNullGroupType(list);
        List<GroupType> valids = list.stream().filter(x-> (x==GroupType.STUDENT)||(x==GroupType.PARENT))
                .collect(Collectors.toList());
        if ((valids == null)||(valids.size()==0))
            throw new AppMessageException("userAccount.validation.register.student_group_type") ;
    }

    public static void validatenNullGroupType(List<GroupType> list){
        if ((list==null)||(list.size()==0))
            throw new AppMessageException("userAccount.validation.register.notnull_group_type") ;

    }
}

package com.peak.training.accountfamily.service;

import com.peak.training.accountfamily.dto.FamilyDTO;

import java.util.List;

public interface FamilyPort {

    public List<FamilyDTO> getFamilyAccountList(int userId) ;
    String setFamilyMember(int askedForUserId, int requestedByUserId) ;
}

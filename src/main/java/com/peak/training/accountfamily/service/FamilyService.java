package com.peak.training.accountfamily.service;

import com.peak.training.account.port.AccountPort;
import com.peak.training.accountfamily.domain.FamilyEntity;
import com.peak.training.accountfamily.domain.FamilyMemberEntity;
import com.peak.training.accountfamily.dto.FamilyDTO;
import com.peak.training.accountfamily.repository.FamilyEntityRepository;
import com.peak.training.accountfamily.repository.FamilyMemberEntityRepository;
import com.peak.training.common.annotation.log.LogMethodData;
import com.peak.training.common.exception.AppMessageException;
import com.peak.training.tokenprocess.TokenProcessPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class FamilyService implements FamilyPort {

    @Autowired
    FamilyMemberEntityRepository memberRepository ;

    @Autowired
    FamilyEntityRepository familyRepository ;

    @Autowired
    AccountPort accountService ;

    @Autowired
    TokenProcessPort tokenProcessService ;


    public List<FamilyDTO> getFamilyAccountList(int userId){

        FamilyMemberEntity member = memberRepository.getById(userId);
        if (member == null) throw new AppMessageException("family.userid.noexist") ;

        List<FamilyDTO> results = new ArrayList<FamilyDTO>();
        List<FamilyMemberEntity> members = memberRepository.findByFamilyId(member.getFamilyId()) ;
        if ((members == null)||(members.size()==0))
            return results ;
        members.stream().forEach(x->{
            results.add(mapFamilyDTO(x)) ;
        });
        return results;
    }

    @Transactional
    @LogMethodData
    public String setFamilyMember(int askedForUserId, int requestedByUserId){
        FamilyMemberEntity rmember = memberRepository.getById(requestedByUserId);
        FamilyMemberEntity amember = memberRepository.getById(askedForUserId);

        int familyId = getFamilyId(rmember, amember, requestedByUserId);
        if (rmember == null)
            persistFamilyMember(familyId, requestedByUserId, requestedByUserId);
        if (amember == null)
            persistFamilyMember(familyId, askedForUserId, requestedByUserId);
        return tokenProcessService.setFamilyMember(askedForUserId,requestedByUserId) ;

    }


    private void persistFamilyMember(int familyId, int userId, int requestedByUserId){
        FamilyMemberEntity entity = FamilyMemberEntity.builder()
                .familyId(familyId)
                .userId(userId).build();
        entity.setAudit(requestedByUserId);
    }

    private int getFamilyId(FamilyMemberEntity rmember, FamilyMemberEntity amember, int requestedByUserId){
        if ((rmember == null)&&(amember==null))
            return getNewFamilyEntity(requestedByUserId).getFamilyId() ;
        if ((rmember != null)&&(amember !=null)){
            throw new AppMessageException("family.userid.exist.family") ;
        }

        if (rmember != null) return rmember.getFamilyId() ;
        return amember.getFamilyId() ;

    }

    private FamilyEntity getNewFamilyEntity(int requestedByUserId){
        FamilyEntity entity = FamilyEntity.builder()
                .familyName(Integer.toString(requestedByUserId))
                .build();
        entity.setAudit(requestedByUserId);
        familyRepository.saveAndFlush(entity) ;
        return entity ;
    }


    private FamilyDTO mapFamilyDTO(FamilyMemberEntity entity){
        return FamilyDTO.builder()
                .active(entity.isActive())
                .userAccount(accountService.getAccountById(entity.getUserId()))
                .build();
    }

}

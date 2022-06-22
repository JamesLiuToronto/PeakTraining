package com.peak.training.accountfamily.domain;

import com.peak.training.common.infractructure.InfraEntityBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="FamilyMemberEntity", schema="peaktraining")
@NamedQuery(name = "FamilyMemberEntity.findByFamilyId", query = "select u from FamilyMemberEntity u where u.familyId = ?1")

public class FamilyMemberEntity extends InfraEntityBase {

    @Id
    @Column(name="userId")
    int userId ;

    @Column(name = "familyId")
    private int familyId ;

    @Column(name="active")
    boolean active ;


}

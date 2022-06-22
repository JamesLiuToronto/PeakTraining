package com.peak.training.accountfamily.domain;

import com.peak.training.account.infrastructure.entity.UserGroupEntity;
import com.peak.training.common.infractructure.InfraEntityBase;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Family", schema="peaktraining")
public class FamilyEntity extends InfraEntityBase {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="familyid")
    int familyId ;

    @Column(name="familyname")
    String familyName ;

    @Column(name="note")
    String note ;

}

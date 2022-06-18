package com.peak.training.account.infrastructure.entity;

import com.peak.training.common.infractructure.InfraEntityBase;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Usergroup", schema="peaktraining")
public class UserGroupEntity extends InfraEntityBase {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="usergroupid")
    int userGroupId ;

    @Column(name="uuid")
    String uuid;

    @Column(name="groupcode")
    String groupCode;

    @Column(name="active")
    boolean active ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private UserAccountEntity userAccountEntity;



    @Override
    public String toString() {
        return "UserGroupEntity{" +
                "userGroupId=" + userGroupId +
                ", uuid='" + uuid + '\'' +
                ", groupCode='" + groupCode + '\'' +
                ", active=" + active +
                '}';
    }
}




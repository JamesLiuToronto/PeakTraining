package com.peak.training.account.infrastructure.entity;

import com.peak.training.common.infractructure.InfraEntityBase;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Useraccount", schema="peaktraining")
@NamedQuery(name = "UserAccountEntity.findByemailAddress", query = "select u from UserAccountEntity u where u.emailAddress = ?1")
@NamedQuery(name = "UserAccountEntity.findByUuid", query = "select u from UserAccountEntity u where u.uuid = ?1")
public class UserAccountEntity extends InfraEntityBase {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="userid")
    int userId ;

    @Column(name="uuid")
    String uuid ;

    @Column(name="emailaddress")
    String emailAddress ;

    @Column(name="firstname")
    String firstName ;

    @Column(name="lastname")
    String lastName ;

    @Column(name="birthdate")
    LocalDate birthDate ;

    @Column(name="sex")
    String sex ;

    @Column(name="statuscode")
    String statusCode ;

    @OneToMany(mappedBy = "userAccountEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //@OrderBy("lineNum")
    private List<UserGroupEntity> userGroups = new ArrayList<>();


    @Override
    public String toString() {
        return "UserAccountEntity{" +
                "userId=" + userId +
                ", uuid='" + uuid + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", sex='" + sex + '\'' +
                ", statusCode='" + statusCode + '\'' +
                '}';
    }


}

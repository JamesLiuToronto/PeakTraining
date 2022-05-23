package com.peak.training.account.infrastructure.entity;

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
@Table(name="UserLogin", schema="peaktraining")
//@NamedQuery(name = "UserLoginEntity.findByemailAddress", query = "select u from UserLoginEntity u where u.emailAddress = ?1")
public class UserLoginEntity {

    @Id
    @Column(name="userid")
    int userId ;

    @Column(name="utimestamp")
    LocalDateTime utimestamp ;

    @Column(name="password")
    String password ;

    @Column(name="authenticationSource")
    String authenticationSource ;

    @Column(name="auditId")
    int auditId ;

}

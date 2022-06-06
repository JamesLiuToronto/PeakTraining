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

    @Column(name="authenticationsource")
    String authenticationSource ;

    @Column(name="auditId")
    Integer auditId ;

    @Column(name="locked")
    boolean locked ;

    @Column(name="lastsuccesslogin")
    LocalDateTime lastSuccessLogin ;

    @Column(name="lastfaliedlogin")
    LocalDateTime lastFaliedLogin ;

    @Column(name="faliedloginattemp")
    int faliedLoginAttemp ;

    @Override
    public String toString() {
        return "UserLoginEntity{" +
                "userId=" + userId +
                ", password='" + password + '\'' +
                ", authenticationSource='" + authenticationSource + '\'' +
                ", auditId=" + auditId +
                ", locked=" + locked +
                ", faliedLoginAttemp=" + faliedLoginAttemp +
                '}';
    }
}

package com.peak.training.account.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="accesscontrol", schema="peaktraining")
@NamedQuery(name = "AccessControl.findMethodCodeByGroupCode", query = "select u from AccessControlEntity u where u.groupCode = ?1")

public class AccessControlEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="accesscontrolid")
    int accessControlID ;

    @NotNull
    //@Size(min = 2, message="Street Name is at least 2 characters long!")
    @Column(name="groupcode")
    String groupCode ;

    @NotNull
    @Column(name="methodcode")
    String methodCode ;

    @NotNull
    @Column(name="allow")
    boolean allow ;

    @NotNull
    @Column(name="utimestamp")
    LocalDateTime utimestamp ;

}

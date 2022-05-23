package com.peak.training.common.audit.domain;

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
@Table(name="auditlog", schema="peaktraining")
//@NamedQuery(name = "Address.findAddressByline", query = "select u from Address u where u.line = ?1 AND u.postalCode = ?2")

public class AuditLog {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="auditid")
    int auditID ;

    @NotNull
    //@Size(min = 2, message="Street Name is at least 2 characters long!")
    @Column(name="createuserid")
    int createUserId ;

    @NotNull
    @Column(name="updateuserid")
    int updateUserId ;

    @NotNull
    @Column(name="createdate")
    LocalDateTime createDate ;

    @NotNull
    @Column(name="updatedate")
    LocalDateTime updateDate ;

}

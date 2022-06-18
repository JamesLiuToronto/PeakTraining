package com.peak.training.common.infractructure;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@MappedSuperclass
public class InfraEntityBase {


    @Column(name="createDate")
    LocalDateTime createDate ;

    @Column(name="updateDate")
    LocalDateTime updateDate ;

    @Column(name="createUserId")
    int createUserId ;

    @Column(name="updateUserId")
    int updateUserId ;

    public void setAudit(int userId){
        if (createUserId > 0){
            this.updateUserId = userId ;
            this.updateDate = LocalDateTime.now() ;
            return ;
        }

        this.createUserId = userId ;
        this.updateUserId = userId ;
        this.createDate = LocalDateTime.now() ;
        this.updateDate = LocalDateTime.now() ;
    }

}

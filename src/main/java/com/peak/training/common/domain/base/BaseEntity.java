package com.peak.training.common.domain.base;


import javax.persistence.Id;
import java.time.LocalDateTime;


public class BaseEntity implements java.io.Serializable {

    @Id
    String uuid ;

    public BaseEntity() {
    }

    public BaseEntity(String uuid) {
        this.uuid = uuid;

    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}

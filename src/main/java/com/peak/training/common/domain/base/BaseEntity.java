package com.peak.training.common.domain.base;


import javax.persistence.Id;
import java.time.LocalDateTime;


public class BaseEntity implements java.io.Serializable {

    @Id
    String uuid ;

    LocalDateTime utimestamp ;

    public BaseEntity() {
    }

    public BaseEntity(String uuid, LocalDateTime utimestamp) {
        this.uuid = uuid;
        this.utimestamp = utimestamp;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getUtimestamp() {
        return utimestamp;
    }

    public void setUtimestamp(LocalDateTime utimestamp) {
        this.utimestamp = utimestamp;
    }
}

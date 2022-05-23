package com.peak.training.common.infractructure;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class InfraEntityBase {


    @NotNull
    @Column(name="utimestamp")
    LocalDateTime utimestamp ;

}

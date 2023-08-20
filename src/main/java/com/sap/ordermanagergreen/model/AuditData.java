package com.sap.ordermanagergreen.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@SuperBuilder
public class AuditData {

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public AuditData() {
        this.createDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }

}

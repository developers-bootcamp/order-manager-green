package com.sap.ordermanagergreen.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@SuperBuilder
public class AuditData {
  
  private LocalDateTime createDate;
  private LocalDateTime updateDate;
  public AuditData(){
    this.createDate = LocalDateTime.now();
    this.updateDate = LocalDateTime.now();
  }
}

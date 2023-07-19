package com.sap.ordermanagergreen.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditData {
  
  private LocalDateTime createDate;
  
  private LocalDateTime updateDate;
  public AuditData(LocalDateTime createDate){
    this.createDate=createDate;
  }


}

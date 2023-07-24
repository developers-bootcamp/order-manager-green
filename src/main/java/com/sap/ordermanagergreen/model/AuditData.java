package com.sap.ordermanagergreen.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AuditData {
  
  private LocalDateTime createDate;
  
  private LocalDateTime updateDate;
  
}

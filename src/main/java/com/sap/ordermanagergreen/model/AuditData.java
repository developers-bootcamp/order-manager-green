package com.sap.ordermanagergreen.model;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditData {
  
  private Date createDate;
  
  private Date updateDate;
  
}

package com.sap.ordermanagergreen.model;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditDate {
  
  private Date createDate;
  
  private Date updateDate;
  
}

package com.sap.ordermanegergreen.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditData {
  private Date createDate;
  private Date updateDate;
  public AuditData(Date createDate){
    this.createDate=createDate;
  }
}

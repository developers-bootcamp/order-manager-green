package com.sap.ordermanegergreen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditDate {
  private Date createDate;
  private Date updateDate;
}

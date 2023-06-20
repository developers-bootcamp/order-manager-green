package com.sap.ordermanegergreen.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditData {
  private Date Create_Date;
  private Date Update_Date;
}

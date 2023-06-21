package com.sap.ordermanegergreen.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Users")
public class User {
  @Id private long Id;
  private String FullName;
  private String Password;
  @DBRef
  private List<Address> Addresses;
  private long RoleId;
  private long CompanyId;
  @DBRef
  private List<AuditData> Audit_Data;
}

package com.sap.ordermanegergreen.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Users")
public class User {
  
  @Id private String Id;
  private String FullName;
  private String Password;
  @DBRef
  private Address Addresses;
  private Roles RoleId;
  private Company CompanyId;
  @DBRef
  private AuditData AuditData;
}
